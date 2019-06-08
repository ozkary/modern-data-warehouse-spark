CREATE TABLE [dm].[dim_device]
(
	[device_id] INT NOT NULL, 
    [device_name] NVARCHAR(255) NOT NULL, 
    [created] DATETIME NULL DEFAULT getdate(), 
    CONSTRAINT [PK_dim_device] PRIMARY KEY ([device_id])
)

GO

CREATE INDEX [IX_dim_device_name] ON [dm].[dim_device] ([device_name])
