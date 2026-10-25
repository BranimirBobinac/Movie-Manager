-- Glavna skripta: baza, tablice i procedure za filmove.
-- Redoslijed pokretanja: 1) BBobinac_Filmz.sql  2) user_procs.sql
--                        3) actor_procs.sql, director_procs.sql, genre_procs.sql, mtm_procs.sql

-- Ime baze mora odgovarati DATABASE_NAME u Dao/src/main/resources/config/db.properties
CREATE DATABASE Filmz
GO
USE Filmz
GO

CREATE TABLE Movie
(
	IDMovie INT PRIMARY KEY IDENTITY,
	Title NVARCHAR(300),
	Link NVARCHAR(500),
	Description NVARCHAR(MAX),
	PicturePath NVARCHAR(260),
	PublishedDate NVARCHAR(90)
)
GO

CREATE TABLE Actor
(
	IDActor INT PRIMARY KEY IDENTITY,
	FirstName NVARCHAR(150),
	LastName NVARCHAR(150)
)
GO

CREATE TABLE Director
(
	IDDirector INT PRIMARY KEY IDENTITY,
	FirstName NVARCHAR(150),
	LastName NVARCHAR(150)
)
GO

CREATE TABLE Genre
(
	IDGenre INT PRIMARY KEY IDENTITY,
	Name NVARCHAR(150)
)
GO

-- Vezne tablice: ON DELETE CASCADE da brisanje filma/glumca/redatelja/zanra
-- ne pukne na foreign key constraintu.

-- Movie-Actor relationship (mtm)
CREATE TABLE MovieActor
(
	IDMovie INT FOREIGN KEY REFERENCES Movie(IDMovie) ON DELETE CASCADE,
	IDActor INT FOREIGN KEY REFERENCES Actor(IDActor) ON DELETE CASCADE,
	PRIMARY KEY(IDMovie, IDActor)
)
GO

-- Movie-Director relationship (otm || mtm)
CREATE TABLE MovieDirector
(
	IDMovie INT FOREIGN KEY REFERENCES Movie(IDMovie) ON DELETE CASCADE,
	IDDirector INT FOREIGN KEY REFERENCES Director(IDDirector) ON DELETE CASCADE,
	PRIMARY KEY(IDMovie, IDDirector)
)
GO

-- Movie-Genre relationship (mtm)
CREATE TABLE MovieGenre
(
	IDMovie INT FOREIGN KEY REFERENCES Movie(IDMovie) ON DELETE CASCADE,
	IDGenre INT FOREIGN KEY REFERENCES Genre(IDGenre) ON DELETE CASCADE,
	PRIMARY KEY(IDMovie, IDGenre)
)
GO



-- stored procs:

CREATE OR ALTER PROCEDURE createMovie
	@Title NVARCHAR(300),
	@Link NVARCHAR(500),
	@Description NVARCHAR(MAX),
	@PicturePath NVARCHAR(260),
	@PublishedDate NVARCHAR(90),
	@IDMovie INT OUTPUT
AS
BEGIN
	INSERT INTO Movie (Title, Link, Description, PicturePath, PublishedDate)
	VALUES (@Title, @Link, @Description, @PicturePath, @PublishedDate)

	SET @IDMovie = SCOPE_IDENTITY()
END
GO

CREATE OR ALTER PROCEDURE updateMovie
	@IDMovie INT,
	@Title NVARCHAR(300),
	@Link NVARCHAR(500),
	@Description NVARCHAR(MAX),
	@PicturePath NVARCHAR(260),
	@PublishedDate NVARCHAR(90)
AS
BEGIN
	UPDATE Movie SET
		Title = @Title,
		Link = @Link,
		Description = @Description,
		PicturePath = @PicturePath,
		PublishedDate = @PublishedDate
	WHERE
		IDMovie = @IDMovie
END
GO


CREATE OR ALTER PROCEDURE deleteMovie
	@IDMovie INT
AS
BEGIN
	DELETE
	FROM
			Movie
	WHERE
		IDMovie = @IDMovie
END
GO

CREATE OR ALTER PROCEDURE selectMovie
	@IDMovie INT
AS
BEGIN
	SELECT
		*
	FROM
		Movie
	WHERE
		IDMovie = @IDMovie
END
GO

CREATE OR ALTER PROCEDURE selectMovies
AS
BEGIN
	SELECT * FROM Movie
END
GO
