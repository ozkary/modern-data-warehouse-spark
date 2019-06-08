
import java.util.Properties
package com.ozkary.telemetry{

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

case class dim_date(      
    date_id: Long,
    date: java.sql.Date,
    created : java.sql.Timestamp
)

case class dim_location(      
    location_id: Long,
    location_name: String,     
    created: java.sql.Timestamp
)

case class dim_device(      
    device_id: Long,
    device_name: String,     
    created: java.sql.Timestamp
)

case class fact_measure(      
    measure_id: Long,
    device_id: Long,   
    date_id: Long,  
    location_id: Long,    
    ip: String,
    measure_date: java.sql.Timestamp,
    created: java.sql.Timestamp
)

case class fact_measurement(         
    measure_id: Long,
    name: String,   
    value : Long       
)


}