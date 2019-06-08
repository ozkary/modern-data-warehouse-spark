CREATE TABLE [dm].[fact_measure]
(
	[measure_id] INT NOT NULL ,    
    [device_id] INT NOT NULL, 
    [date_id] INT NOT NULL,      
	[location_id] INT NULL ,
	[ip] NVARCHAR(25) NULL, 
	[latitude] decimal(6,3) NULL, 
	[longitude] decimal(6,3) NULL, 
    [measure_date] DATETIME NULL,    
    [created] DATETIME NULL DEFAULT getdate(), 
	[ref_id] INT NULL,
    CONSTRAINT [PK_fact_measure] PRIMARY KEY ([measure_id]),
	CONSTRAINT [FK_measure_device] FOREIGN KEY ([device_id]) REFERENCES [dm].[dim_device]([device_id]) ,
	CONSTRAINT [FK_measure_date] FOREIGN KEY ([date_id]) REFERENCES [dm].[dim_date]([date_id]), 
	CONSTRAINT [FK_measure_location] FOREIGN KEY ([location_id]) REFERENCES [dm].[dim_location]([location_id])
)
GO

CREATE INDEX [IX_fact_measure_ref_id] ON [dm].[fact_measure] ([ref_id])
GO

CREATE INDEX [IX_fact_measure_location_date] ON [dm].[fact_entry] ([location_id],[date_id])
INCLUDE ([ip],[measure_date],[latitude],[longitude]);
GO

CREATE INDEX [IX_fact_measure_device] ON [dm].[fact_entry] ([device_id])
INCLUDE ([ip],[measure_date],[latitude],[longitude]);
GO