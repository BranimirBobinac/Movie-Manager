USE Filmz
GO

CREATE OR ALTER PROCEDURE createDirector
    @FirstName NVARCHAR(150),
    @LastName NVARCHAR(150),
    @IDDirector INT OUTPUT
AS
BEGIN
    INSERT INTO Director (FirstName, LastName)
    VALUES (@FirstName, @LastName);

    SET @IDDirector = SCOPE_IDENTITY();
END
GO


CREATE OR ALTER PROCEDURE updateDirector
    @IDDirector INT,
    @FirstName NVARCHAR(150),
    @LastName NVARCHAR(150)
AS
BEGIN
    UPDATE Director
    SET FirstName = @FirstName,
        LastName = @LastName
    WHERE IDDirector = @IDDirector;
END
GO


CREATE OR ALTER PROCEDURE deleteDirector
    @IDDirector INT
AS
BEGIN
    DELETE FROM Director WHERE IDDirector = @IDDirector;
END
GO


CREATE OR ALTER PROCEDURE selectDirectors
AS
BEGIN
    SELECT * FROM Director;
END
GO

CREATE OR ALTER PROCEDURE selectDirector
    @IDDirector INT
AS
BEGIN
    SELECT * FROM Director WHERE IDDirector = @IDDirector;
END
GO

CREATE OR ALTER PROC deleteAllDirectors
AS
BEGIN
	DELETE FROM Director
END
GO
