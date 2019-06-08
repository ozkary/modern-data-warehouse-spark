CREATE TABLE [dm].[dim_location]
(
	[location_id] INT NOT NULL, 
    [location_name] NVARCHAR(255) NOT NULL, 
    [created] DATETIME NULL DEFAULT getdate(), 
    CONSTRAINT [PK_dim_location] PRIMARY KEY ([location_id])
)

GO

CREATE INDEX [IX_dim_location_name] ON [dm].[dim_location] ([location_name])
