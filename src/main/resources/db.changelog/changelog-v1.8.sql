--liquibase formatted sql

--changeset author:alvi
IF OBJECT_ID('dbo.taxesbarassoc') IS NULL
CREATE TABLE [dbo].[taxesbarassoc](
	[barid] [bigint] IDENTITY(1,1) NOT NULL,
	[name] [varchar](255) NULL,
PRIMARY KEY CLUSTERED
(
	[barid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY];

INSERT INTO taxesbarassoc (name) VALUES ('Dhaka Taxes Bar Association');
INSERT INTO taxesbarassoc (name) VALUES ('DHAKA Taxes Bar Association');
INSERT INTO taxesbarassoc (name) VALUES ('CHATTOGRAM Taxes Bar Association');
INSERT INTO taxesbarassoc (name) VALUES ('SYLHET Taxes Bar Association');
INSERT INTO taxesbarassoc (name) VALUES ('BARISAL Taxes Bar Association');
INSERT INTO taxesbarassoc (name) VALUES ('KHULNA Taxes Bar Association');
INSERT INTO taxesbarassoc (name) VALUES ('RAJSHAHI Taxes Bar Association');
INSERT INTO taxesbarassoc (name) VALUES ('GAZIPUR Taxes Bar Association');
INSERT INTO taxesbarassoc (name) VALUES ('NARAYANGANJ Taxes Bar Association');
INSERT INTO taxesbarassoc (name) VALUES ('RANGPUR Taxes Bar Association');
INSERT INTO taxesbarassoc (name) VALUES ('CUMILLA Taxes Bar Association');
INSERT INTO taxesbarassoc (name) VALUES ('MYMENSINGH Taxes Bar Association');