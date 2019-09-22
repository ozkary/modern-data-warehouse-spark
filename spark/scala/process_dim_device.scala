
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
val query = "(select * from dm.dim_device) as dim_device"
val sqlDf = spark.read.jdbc(url=jdbcUrl, table=query, properties=connectionProperties)
sqlDf.show()

//step 1 - import devices
case class dim_device(      
    device_id: Long,
    device_name: String,     
    created: java.sql.Timestamp
)

val tableName = "dl_device"
val query = s"select device_id, device_name, CURRENT_TIMESTAMP as created from telemetry.${tableName}"
val dsData = spark.sql(query).distinct.as[dim_device]
dsData.show

val source = "telemetry.dim_device"
val target = "telemetry.dm.dim_device"

//spark.sql(s"truncate table ${target}")

 //save to datalake table partition to 8 files defaults to 4
val dsData2 = dsData.coalesce(8)

//TODO check devices that already exists
dsData2.write.mode("append").format("hive").saveAsTable(source)
//spark.sql(s"select count(*) from ${source}").show

//save to datamart in data warehouse
dsData2.write.mode("append").jdbc(jdbcUrl, target, connectionProperties)
