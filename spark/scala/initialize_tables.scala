/*
reset the data in the spark tables   

usage:  
scala> :load initialize_tables.scala

Oscar D. Garcia
twitter: @ozkary
ozkary.com

*/

import org.apache.spark.sql.SparkSession

// checks if the table exists
// use the fullname space databasename.tablename
//
def truncateTable(table: String, spark: SparkSession) : Boolean = {

   var exists = spark.catalog.tableExists(table)

   if (exists){
        spark.sql(s"truncate table ${table}")                    
    } else
    {
        println(s"${table} not found")    
    } 
   
    true
}

//datalake tables
truncateTable("telemetry.dl_device", spark)

truncateTable("telemetry.dl_bot", spark)

//fact tables
truncateTable("telemetry.fact_measurement", spark)
truncateTable("telemetry.fact_measure", spark)

//dimension tables
truncateTable("telemetry.dim_location", spark)

truncateTable("telemetry.dim_device", spark)

truncateTable("telemetry.dim_date", spark)

println("done...")