USE [arq_ref_demodb]
GO

IF OBJECT_ID('dbo.task_tag', 'U') IS NOT NULL
  DROP TABLE dbo.task_tag;

IF OBJECT_ID('dbo.tag', 'U') IS NOT NULL
  DROP TABLE dbo.tag;

IF OBJECT_ID('dbo.task', 'U') IS NOT NULL
  DROP TABLE dbo.task;

/****** Object:  Table [dbo].[tag]    Script Date: 8/28/2017 11:40:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tag](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [varchar](45) NOT NULL,
 CONSTRAINT [PK_tag] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

/****** Object:  Table [dbo].[task]    Script Date: 8/28/2017 11:40:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[task](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [varchar](65) NOT NULL,
	[description] [varchar](125) NULL,
	[date] [datetime] NULL,
	[is_done] [bit] NULL,
 CONSTRAINT [PK_task] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO

/****** Object:  Table [dbo].[task_tag]    Script Date: 8/28/2017 11:40:03 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[task_tag](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[task_id] [int] NOT NULL,
	[tag_id] [int] NOT NULL,
 CONSTRAINT [PK_task_tag] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
ALTER TABLE [dbo].[task] ADD  DEFAULT ((0)) FOR [is_done]
GO
ALTER TABLE [dbo].[task_tag]  WITH CHECK ADD  CONSTRAINT [FK_tag] FOREIGN KEY([tag_id])
REFERENCES [dbo].[tag] ([id])
GO
ALTER TABLE [dbo].[task_tag] CHECK CONSTRAINT [FK_tag]
GO
ALTER TABLE [dbo].[task_tag]  WITH CHECK ADD  CONSTRAINT [FK_task] FOREIGN KEY([task_id])
REFERENCES [dbo].[task] ([id])
GO
ALTER TABLE [dbo].[task_tag] CHECK CONSTRAINT [FK_task]
GO
