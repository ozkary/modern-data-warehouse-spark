CREATE TABLE [dm].[dim_date]
(
	[date_id] INT NOT NULL, 
    [date] DATE NOT NULL, 	
    [week_no] AS datepart(wk, [date]),
	[month_no] AS month([date]),
	[month_name] AS datename(MM,[date]),
	[quarter] AS datename(q,[date]),
	[year] AS year([date]) ,
	[created] DATETIME DEFAULT(getdate())
    CONSTRAINT [PK_dim_date] PRIMARY KEY ([date_id])
)

GO

CREATE INDEX [IX_dim_date_Column] ON [dm].[dim_date] ([date])
 