
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
val query = "(select * from dm.dim_location) as dim_location"
val sqlDf = spark.read.jdbc(url=jdbcUrl, table=query, properties=connectionProperties)
sqlDf.show()

//step 1 - import location
case class dim_location(      
    location_id: Long,
    location_name: String,     
    created: java.sql.Timestamp
)

val tableName = "dl_device"
val query = s"select abs(hash(cn)) as location_id,cn as location_name,CURRENT_TIMESTAMP as created from telemetry.${tableName}"

val dsData = spark.sql(query).distinct.as[dim_location]
dsData.show

val source = "telemetry.dim_location"
val target = "telemetry.dm.dim_location"

 //save to datalake table
 dsData.write.mode("append").format("hive")
.saveAsTable(source)
    
  //save to datamart in data warehouse
dsData.write.mode("append")
.jdbc(jdbcUrl, target, connectionProperties);


