
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
val query = "(select top 100 * from dm.fact_measure) as fact_measure"
val sqlDf = spark.read.jdbc(url=jdbcUrl, table=query, properties=connectionProperties)
sqlDf.show

case class fact_measure(      
    measure_id: Long,
    device_id: Long,   
    date_id: Long,  
    location_id: Long,    
    ip: String,
    measure_date: java.sql.Timestamp,
    created: java.sql.Timestamp
)

val tableName = "dl_device"

//join with the dim tables to get the FKs
// workout a better hashkey
val query = {s"""select abs(hash(device_id || cn || timestamp)) as measure_id,ip,device_id,dt.date_id, loc.location_id, 
    from_unixtime(timestamp/1000) as measure_date,
    CURRENT_TIMESTAMP as created  
from telemetry.${tableName} dev 
inner join telemetry.dim_date dt on to_date(from_unixtime(dev.timestamp/1000)) = dt.date  
inner join telemetry.dim_location loc on dev.cn = loc.location_name"""}

val dfData = spark.sql(query).distinct.as[fact_measure]

dfData.show

val dsData2 = dfData.coalesce(8)

val source = "telemetry.fact_measure"
val target = "telemetry.dm.fact_measure"

#spark.sql(s"truncate table ${source}")

dsData2.write.mode("append").format("hive").saveAsTable(source)

//save to datamart in data warehouse
dsData2.write.mode("append").jdbc(jdbcUrl, target, connectionProperties)