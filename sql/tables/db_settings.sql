USE [master]
GO

/****** Object:  Database [telemetry]    Script Date: 6/4/2019 4:49:24 PM ******/
CREATE DATABASE [telemetry]
GO

USE [telemetry]
GO

CREATE SCHEMA [dm]
    AUTHORIZATION [dbo];
GO

/* create the service account */
CREATE LOGIN [svcSpark] WITH PASSWORD=N'Sparky@1234', DEFAULT_DATABASE=[telemetry], DEFAULT_LANGUAGE=[us_english], CHECK_EXPIRATION=OFF, CHECK_POLICY=OFF
GO

CREATE USER [svcSpark] FOR LOGIN [svcSpark] WITH DEFAULT_SCHEMA=[dm]
GO

GRANT SELECT ON SCHEMA::dm TO svcSpark
GO

GRANT INSERT ON SCHEMA::dm TO svcSpark
GO