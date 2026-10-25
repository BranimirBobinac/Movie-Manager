# Movie Manager

A Java Swing desktop application for managing a movie catalogue: movies, actors, directors and genres. It includes user login and registration, separate admin and user views, a Microsoft SQL Server database, and movie import from an RSS feed.

## Features

- **Login and registration.** Passwords are hashed with BCrypt and are never stored in plain text.
- **Role-based access**
  - **Admin:** full access to manage actors, directors, genres and movies, and to import movies.
  - **User:** read-only view of movies, actors, directors and genres.
- **Movie management (CRUD).** Add, edit and delete movies with title, link, description, publish date and poster image.
- **Actor, director and genre management (CRUD)**
- **RSS import.** Downloads the latest movies from an RSS feed, including poster images.
- **Poster images.** Choose a local image for a movie; it is copied into the application's `assets` folder.
- **Logout.** Return to the login screen from the *App* menu.

## Technologies

| Technology | Purpose |
|---|---|
| **Java 20** | Programming language |
| **Java Swing** | Desktop user interface (built with the NetBeans GUI Builder) |
| **Apache Maven** | Build tool and dependency management (multi-module project) |
| **Microsoft SQL Server** | Relational database |
| **T-SQL stored procedures** | All database access goes through stored procedures |
| **JDBC** (`mssql-jdbc` 8.4.1) | Database connection from Java |
| **jBCrypt** 0.4 | Password hashing |
| **StAX** (`javax.xml.stream`) | RSS/XML parsing |
| **JAXB** (`jaxb-impl` 2.3.4) | XML serialization utilities |

### Design patterns used

- **Repository pattern:** the data access layer is defined through interfaces (`ActorRepository`, `UserRepository`, ...) with SQL Server implementations.
- **Factory:** `RepositoryFactory` creates the repository instances; the main repository class is configured in `repository.properties`.
- **Singleton:** `DataSourceSingleton` provides a single shared `DataSource`.
- **Table models:** custom `AbstractTableModel` implementations for the Swing `JTable` views.

## Project structure

```
BBobinacJava1/
├── pom.xml                  # Parent Maven project
├── Utilities/               # Shared helpers (files, icons, messages, XML, HTTP)
├── Dao/                     # Data access layer
│   └── src/main/
│       ├── java/hr/algebra/
│       │   ├── model/       # Entities: Movie, Actor, Director, Genre, User, Role
│       │   └── dal/         # Repository interfaces, factory and SQL implementations
│       └── resources/config/
│           ├── db.properties          # Database connection settings
│           └── repository.properties  # Repository implementation class
├── MovieManager/            # Swing application (entry point)
│   └── src/main/java/hr/algebra/
│       ├── MovieManager.java          # Main window
│       ├── parsers/rss/               # RSS feed parser
│       └── view/                      # Panels: auth, entity, movies, user, table models
└── db/                      # SQL scripts (database, tables, stored procedures)
```

## Getting started

### Prerequisites

- **JDK 20** or newer
- **Apache NetBeans** (recommended) or another IDE with Maven support
- **Microsoft SQL Server** (Express edition is fine) and **SQL Server Management Studio**

### 1. Set up the database

Open SQL Server Management Studio and run the scripts from the `db` folder **in this order**:

1. `BBobinac_Filmz.sql`: creates the `Filmz` database, the tables and the movie procedures
2. `user_procs.sql`: creates the user table, the login/register procedures and the default admin account
3. `actor_procs.sql`
4. `director_procs.sql`
5. `genre_procs.sql`
6. `mtm_procs.sql`: procedures for the movie–actor, movie–director and movie–genre relationships

SQL Server must allow **SQL Server Authentication**:

1. Right-click the server → **Properties** → **Security** → select *SQL Server and Windows Authentication mode*.
2. Restart the server.
3. Make sure the SQL login you plan to use (for example `sa`) is enabled and has a password.

If you use a named instance such as `SQLEXPRESS`, enable **TCP/IP** in *SQL Server Configuration Manager* and make sure the **SQL Server Browser** service is running.

### 2. Configure the connection

Edit `Dao/src/main/resources/config/db.properties`:

```properties
SERVER_NAME = YOUR-PC-NAME\\SQLEXPRESS
DATABASE_NAME = Filmz
USER = sa
PASSWORD = your_password
```

> **Note:** in `.properties` files the backslash must be doubled (`\\`).

### 3. Build and run

**In NetBeans**

1. **File → Open Project**, select the `BBobinacJava1` folder and check *Open Required Projects*.
2. Right-click **BBobinacJava1** → **Clean and Build**.
3. Right-click **MovieManager** → **Run**.

**From the command line**

```bash
mvn clean install
cd MovieManager
mvn exec:java
```

### 4. Log in

A default administrator account is created by `user_procs.sql`:

| Username | Password |
|---|---|
| `admin` | `admin` |

Change this password after your first login. New accounts created from the **Register** tab get the regular **USER** role.

## Usage

1. Log in as **admin**.
2. Open the **UPLOAD_MOVIES** tab and click **Upload Movies** to import movies from the RSS feed.
3. Use the **ACTOR**, **DIRECTOR** and **GENRE** tabs to manage those records.
4. Use the **EDIT_MOVIES** tab to add, edit or delete movies and change their poster images.
5. Log in as a regular user to see the read-only overview.

## Author

Branimir Bobinac
