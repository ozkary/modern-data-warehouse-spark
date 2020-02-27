/*
load all the measurements transposing cols to rows

usage:  scala> :load process_fact_measurement.scala

*/

//import com.ozkary.telemetry
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
val query = "(select top 100 * from dm.fact_measurement) as fact_measurement"
val sqlDf = spark.read.jdbc(url=jdbcUrl, table=query, properties=connectionProperties)
sqlDf.show

case class fact_measurement(         
    measure_id: Long,
    name: String,   
    value : Long       
)

val tableName = "dl_device"
val fields = "humidity,temp,c02_level,battery_level"
//TODO workout a better hashkey
val hashKey = "device_name || timestamp || cn || humidity || temp"

//join with the dim tables to get the FKs
val query = {s"select abs(hash(device_id || cn || timestamp)) as measure_id,${fields} from telemetry.${tableName}"}

val dfData = spark.sql(query).distinct
val stackExp = "'humidity',humidity,'temperature',temp,'c02_level',c02_level,'battery_level',battery_level"

//transpose from cols to rows using stack function
val dsMeasurements = dfData.select($"measure_id",
expr(s"stack(4,${stackExp}) as (name,value)")).as[fact_measurement]

val dsData2 = dsMeasurements.coalesce(8)
dsData2.show

val source = "telemetry.fact_measurement"
val target = "telemetry.dm.fact_measurement"

//spark.sql(s"truncate table ${source}")
dsData2.write.mode("append").format("hive").saveAsTable(source)

//save to datamart in data warehouse
dsData2.write.mode("append").jdbc(jdbcUrl, target, connectionProperties)