/*
Create a database

https://github.com/ozkary/modern-data-warehouse-spark

Oscar D. Garcia
twitter: @ozkary
ozkary.com

*/

//shows the database catalog
//Catalog is new API in spark 2.0 which allows us to interact with metadata of spark sql.
spark.catalog.listDatabases.show(false)
spark.catalog.listDatabases().select("name").show()
spark.catalog.listTables().select("name").show()
spark.sql("create database if not exists telemetry comment 'telemetry demo by ozkary'")
spark.catalog.listDatabases().select("name").show()
spark.catalog.listTables("telemetry").select("name").show()

//spark.sql("DROP TABLE IF EXISTS telemetry.dl_bot2")