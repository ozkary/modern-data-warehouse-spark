# Modern Data Warehouse with Apache Spark

A data warehouse (DW) is a core component of business intelligence and a central data repository for different sources. This poses many challenges as the schema definition for those sources may be completely different from one another. To illustrate this, we use telemetry data from devices which have a different model.

We use this project to discuss and show how to create a data warehouse model that can support data for disparate data sources. We look at how to build a dimensional model which can enable us to import the data with different shapes into a data warehouse. We then create processes to transform and load the data using Apache Spark. We finally use PowerBI to visualize the data from our data warehouse.

The article associated to this repo can be found at this location:

https://www.ozkary.com/2019/04/building-modern-data-warehouse-different-sources.html

## Directories

### Data Folder
This folder has some sample JSON data that we use to illustrate the data transformation and load process.

### Spark Folder

This folder contains all the spark scripts that we use to load and process the JSON data into our SQL data warehouse

### SQL Folder

This folder has all the SQL scripts to build the dimension and fact tables.
