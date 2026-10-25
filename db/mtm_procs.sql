USE Filmz
GO

CREATE OR ALTER PROCEDURE addActorToMovie
    @IDMovie INT,
    @IDActor INT
AS
BEGIN
    INSERT INTO MovieActor (IDMovie, IDActor)
    VALUES (@IDMovie, @IDActor);
END
GO

CREATE OR ALTER PROCEDURE getActorsForMovie
    @IDMovie INT
AS
BEGIN
    SELECT A.* FROM Actor A
    JOIN MovieActor MA ON A.IDActor = MA.IDActor
    WHERE MA.IDMovie = @IDMovie;
END
GO

CREATE OR ALTER PROCEDURE clearActorsForMovie
    @IDMovie INT
AS
BEGIN
    DELETE FROM MovieActor WHERE IDMovie = @IDMovie;
END
GO



CREATE OR ALTER PROCEDURE addGenreToMovie
    @IDMovie INT,
    @IDGenre INT
AS
BEGIN
    INSERT INTO MovieGenre (IDMovie, IDGenre)
    VALUES (@IDMovie, @IDGenre);
END
GO

CREATE OR ALTER PROCEDURE getGenresForMovie
    @IDMovie INT
AS
BEGIN
    SELECT G.* FROM Genre G
    JOIN MovieGenre MG ON G.IDGenre = MG.IDGenre
    WHERE MG.IDMovie = @IDMovie;
END
GO

CREATE OR ALTER PROCEDURE clearGenresForMovie
    @IDMovie INT
AS
BEGIN
    DELETE FROM MovieGenre WHERE IDMovie = @IDMovie;
END
GO


CREATE OR ALTER PROCEDURE addDirectorToMovie
    @IDMovie INT,
    @IDDirector INT
AS
BEGIN
    INSERT INTO MovieDirector (IDMovie, IDDirector)
    VALUES (@IDMovie, @IDDirector);
END
GO

CREATE OR ALTER PROCEDURE getDirectorsForMovie
    @IDMovie INT
AS
BEGIN
    SELECT D.* FROM Director D
    JOIN MovieDirector MD ON D.IDDirector = MD.IDDirector
    WHERE MD.IDMovie = @IDMovie;
END
GO

CREATE OR ALTER PROCEDURE clearDirectorsForMovie
    @IDMovie INT
AS
BEGIN
    DELETE FROM MovieDirector WHERE IDMovie = @IDMovie;
END
GO
