USE Filmz
GO

CREATE OR ALTER PROCEDURE createActor
    @FirstName NVARCHAR(150),
    @LastName NVARCHAR(150),
    @IDActor INT OUTPUT
AS
BEGIN
    INSERT INTO Actor (FirstName, LastName) VALUES (@FirstName, @LastName);
    SET @IDActor = SCOPE_IDENTITY();
END
GO

CREATE OR ALTER PROCEDURE updateActor
    @IDActor INT,
    @FirstName NVARCHAR(150),
    @LastName NVARCHAR(150)
AS
BEGIN
    UPDATE Actor
    SET FirstName = @FirstName, LastName = @LastName
    WHERE IDActor = @IDActor;
END
GO

CREATE OR ALTER PROCEDURE deleteActor
    @IDActor INT
AS
BEGIN
    DELETE FROM Actor WHERE IDActor = @IDActor;
END
GO

CREATE OR ALTER PROCEDURE selectActors
AS
BEGIN
    SELECT * FROM Actor;
END
GO

CREATE OR ALTER PROCEDURE selectActor
    @IDActor INT
AS
BEGIN
    SELECT * FROM Actor WHERE IDActor = @IDActor;
END
GO

CREATE OR ALTER PROC deleteAllActors
AS
BEGIN
	DELETE FROM Actor
END
GO
