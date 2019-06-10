/*
check the data in all the tables

https://github.com/ozkary/modern-data-warehouse-spark

Oscar D. Garcia
twitter: @ozkary
ozkary.com

*/

use [telemetry]
GO

SELECT *  FROM [dm].[dim_date]
GO


SELECT top 100 *  FROM [dm].[dim_device]
GO

SELECT top 100 *  FROM [dm].[dim_location]
GO


SELECT top 100 *  FROM  [dm].[fact_measure]
GO

SELECT top 100 *  FROM [dm].[fact_measurement]
GO


