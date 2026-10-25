USE Filmz
GO

-- Salt je sada dio CREATE TABLE (prije se dodavao ALTER-om NA KRAJU skripte,
-- pa se registerUser nije mogao kreirati jer kolona Salt jos nije postojala).
IF OBJECT_ID('AppUser', 'U') IS NULL
CREATE TABLE AppUser
(
    IDUser INT PRIMARY KEY IDENTITY,
    Username NVARCHAR(150) NOT NULL UNIQUE,
    UserPassword NVARCHAR(150) NOT NULL,
    Salt NVARCHAR(150) NOT NULL DEFAULT '',
    UserRole NVARCHAR(20) NOT NULL
)
GO

-- Ako tablica vec postoji (stara verzija bez Salt kolone), dodaj kolonu.
IF COL_LENGTH('AppUser', 'Salt') IS NULL
    ALTER TABLE AppUser ADD Salt NVARCHAR(150) NOT NULL DEFAULT '';
GO

CREATE OR ALTER PROCEDURE registerUser
    @Username NVARCHAR(150),
    @UserPassword NVARCHAR(150),
    @Salt NVARCHAR(150),
    @UserRole NVARCHAR(20),
    @IDUser INT OUTPUT
AS
BEGIN
    INSERT INTO AppUser (Username, UserPassword, Salt, UserRole)
    VALUES (@Username, @UserPassword, @Salt, @UserRole)

    SET @IDUser = SCOPE_IDENTITY()
END
GO

-- Lozinka se NE provjerava u bazi (BCrypt hash se provjerava u Javi),
-- procedura samo dohvaca korisnika po korisnickom imenu.
-- (Prije je koristila nedeklariran @UserPassword pa se procedura nije ni kreirala.)
CREATE OR ALTER PROCEDURE loginUser
    @Username NVARCHAR(150)
AS
BEGIN
    SELECT * FROM AppUser
    WHERE Username = @Username
END
GO

-- Pocetni administrator (username: admin, password: admin).
-- Registracija kroz aplikaciju uvijek kreira obicnog USER-a, pa bez ovoga
-- nitko ne bi mogao uci u admin dio. Promijenite lozinku nakon prvog logina.
IF NOT EXISTS (SELECT 1 FROM AppUser WHERE Username = 'admin')
    INSERT INTO AppUser (Username, UserPassword, Salt, UserRole)
    VALUES ('admin',
            '$2a$10$o/HNWtip5fagXe.iW.p6OesNsJLaC1mhBp3tegBB4p0nOzQYkGQaK',
            '$2a$10$o/HNWtip5fagXe.iW.p6Oe',
            'ADMIN')
GO
