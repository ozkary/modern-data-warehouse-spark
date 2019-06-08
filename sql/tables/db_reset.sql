use [telemetry]
GO


TRUNCATE TABLE [dm].[fact_measurement]
GO

DELETE FROM  [dm].[fact_measure]
GO

DELETE FROM [dm].[dim_location]
GO

DELETE FROM [dm].[dim_device]
GO

DELETE FROM [dm].[dim_date]
GO