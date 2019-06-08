/*
reset the data in the spark tables   

usage:  
scala> :load initialize_tables.scala

Oscar D. Garcia
twitter: @ozkary
ozkary.com

*/

val path = "/mnt/c/repos/dw-spark/scala/"

//data lake tables
spark.sql("truncate table telemetry.dl_device")

spark.sql("truncate table telemetry.dl_bot")

//fact tables
spark.sql("truncate table telemetry.fact_measurement")

spark.sql("truncate table telemetry.fact_measure")


//dimension tables
spark.sql("truncate table telemetry.dim_location")

spark.sql("truncate table telemetry.dim_device")

spark.sql("truncate table telemetry.dim_date")