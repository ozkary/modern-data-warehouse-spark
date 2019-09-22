/*
check the data in all the tables

https://github.com/ozkary/modern-data-warehouse-spark

Oscar D. Garcia
twitter: @ozkary
ozkary.com

*/

use [telemetry]
GO

SELECT *  FROM [dm].[dim_date](nolock)
GO


SELECT top 100 *  FROM [dm].[dim_device](nolock)
GO

SELECT top 100 *  FROM [dm].[dim_location](nolock)
GO


SELECT top 100 *  FROM  [dm].[fact_measure](nolock)
GO

SELECT top 100 *  FROM [dm].[fact_measurement](nolock)
GO


