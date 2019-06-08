case class DeviceIoTData (
    battery_level: Long,
    c02_level: Long,
    cca2: String,
    cca3: String,
    cn: String,
    device_id: Long,
    device_name: String,
    humidity: Long,
    ip: String,
    latitude: Double,
    longitude: Double,
    scale: String,
    temp: Long,
    timestamp: Long
)

case class RoboIoTDataSmall (  
    location: String,
    id: Long,
    name: String,
    humidity: Long,      
    temperature: Long,
    sound: Long,
    date: java.sql.Timestamp
)
import org.apache.spark.sql.DataFrame

val dsDevices = spark.read.json("/mnt/c/repos/dw-spark/data/iot_devices.json").as[DeviceIoTData];
val dsRobDev = spark.read.json("/mnt/c/repos/dw-spark/data/bot_devices.json").as[RoboIoTDataSmall];
dsDevices.show
dsRobDev.show

dsDevices.write.mode("append").format("hive").saveAsTable("telemetry.dl_device")
dsRobDev.write.mode("append").format("hive").saveAsTable("telemetry.dl_bot")

//other APIs to save in other formats
//ds.write.mode("append").format("hive").saveAsTable("telemetry.dl_device")
//ds.write.mode("append").json("/mnt/c/repos/dw-spark/data/")
//ds.write.mode("append").csv("/mnt/c/repos/dw-spark/data/")
//spark.sql("desc formatted telemetry.dl_device").show()
