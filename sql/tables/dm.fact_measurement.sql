CREATE TABLE [dm].[fact_measurement]
(
	[measurement_id] INT IDENTITY NOT NULL, 	
    [measure_id] INT NOT NULL, 
    [name] NVARCHAR(255) NOT NULL, 
    [value] INT NOT NULL, 
    [scale] NVARCHAR(50) NULL,    
    CONSTRAINT [PK_fact_measurement] PRIMARY KEY ([measurement_id]),     
	CONSTRAINT [FK_measurement_measure] FOREIGN KEY ([measure_id]) REFERENCES [dm].[fact_measure]([measure_id])
)

GO

CREATE INDEX [IX_fact_measurement_measure] ON [dm].[fact_measurement] ([measure_id])
INCLUDE ([name],[value],[scale]); 
GO
