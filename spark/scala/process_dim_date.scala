
//SPARK_CLASSPATH="/usr/share/java/sqljdbc42.jar"
//set the class path
//opt/spark/bin/spark-shell --driver-class-path /usr/share/java/sqljdbc42.jar

import java.util.Properties
val jdbcHostname = "localhost\\express"
val jdbcPort = 1433
val jdbcDatabase = "telemetry"

val connectionProperties = new Properties()
connectionProperties.put("user", "svcSpark")
connectionProperties.put("password", "Sparky@1234")
connectionProperties.setProperty("Driver", "com.microsoft.sqlserver.jdbc.SQLServerDriver")
connectionProperties.setProperty("isolationLevel", "READ_UNCOMMITTED")

val jdbcUrl = s"jdbc:sqlserver://${jdbcHostname}:${jdbcPort};database=${jdbcDatabase}"

val query = "(select * from dm.dim_date) dim_date"
val sqlDf = spark.read.jdbc(url=jdbcUrl, table=query, properties=connectionProperties)
sqlDf.show()

case class dim_date(      
    date_id: Long,
    date: java.sql.Date,
    created : java.sql.Timestamp
)

//step 1 - import dates data
val tableName = "dl_device"
val dfDates = spark.sql(s"""select to_date(from_unixtime(timestamp/1000)) as date, 
CURRENT_TIMESTAMP as created from telemetry.${tableName}""").distinct

val impDates = dfDates.withColumn("date_id",abs(hash(dfDates("date")))).as[dim_date]
var newDates = impDates

val source = "telemetry.dim_date"
val target = "telemetry.dm.dim_date"

var exists = spark.catalog.tableExists(source)
if (exists){

  val dimDates = spark.sql("select max(date) as date from telemetry.dim_date")
  dimDates.show
  newDates = impDates.join(dimDates, impDates("date") > dimDates("date"),"left").select(impDates("date_id"),impDates("date"), impDates("created")).as[dim_date]          
}
newDates.show

if (newDates.head(1).isEmpty == false){
  //save to datalake table
  newDates.write.mode("append").format("hive").saveAsTable(source)
    
  //save to datamart in data warehouse
  newDates.write.mode("append").jdbc(jdbcUrl, target, connectionProperties)
}