'''
Create a database
https://github.com/ozkary/modern-data-warehouse-spark

Oscar D. Garcia
twitter: @ozkary
ozkary.com

run with:
   /opt/spark/bin/spark-submit /mnt/c/repos/dw-spark/spark/python/initialize_db.py

'''

from pyspark import SparkContext
from pyspark.sql import SparkSession
from pyspark.sql.types import *

#initialize the database
def initialize_db(spark):

    result = spark.catalog.listDatabases()
    df = spark.createDataFrame(result)
    df.select("name").show()
    result = spark.catalog.listTables()
       
    if (result):   
        df = spark.createDataFrame(result)     
        df.select("name").show()
    
    spark.sql("create database if not exists telemetry comment 'telemetry demo by ozkary'")    
    result = spark.catalog.listDatabases()
    df = spark.createDataFrame(result)
    df.select("name").show()


def main():       
    spark = SparkSession.builder.appName("telemetry demo by ozkary").getOrCreate()    
    sc = spark.sparkContext
    sc.setLogLevel("ERROR")  
    initialize_db(spark)
    spark.stop()

#load the script
main()




