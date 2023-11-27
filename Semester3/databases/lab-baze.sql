USE [databases-lab];

create table Users(   --many-one
	UserId int Identity(1,1) not null PRIMARY KEY,
	Email nvarchar(55) not null,
	FirstName nvarchar(55) not null,
	LastName nvarchar(55) not null,
	Phone nvarchar(20) not null,
	DepartmentID int not null,
	CONSTRAINT FK_Users_DepartmentID FOREIGN KEY (DepartmentID) REFERENCES Departments(DepartmentId),
);
INSERT INTO Users (Email, FirstName, LastName, Phone, DepartmentID)
VALUES ('john.doe@example.com', 'John', 'Doe', '1234567890', 1);
INSERT INTO Users (Email, FirstName, LastName, Phone, DepartmentID)
VALUES ('jane.smith@example.com', 'Jane', 'Smith', '9876543210', 2);
INSERT INTO Users (Email, FirstName, LastName, Phone, DepartmentID)
VALUES ('michael.johnson@example.com', 'Michael', 'Johnson', '1112223333', 1);
INSERT INTO Users (Email, FirstName, LastName, Phone, DepartmentID)
VALUES ('susan.white@example.com', 'Susan', 'White', '4445556666', 3);
INSERT INTO Users (Email, FirstName, LastName, Phone, DepartmentID)
VALUES ('david.miller@example.com', 'David', 'Miller', '7778889999', 2);
select * from Users

UPDATE Users
SET Phone = '1235467890'
WHERE UserId = 1;

create table Departments(    --one-many
	DepartmentId int Identity(1,1) not null PRIMARY KEY,
	DepartmentName nvarchar(55) not null,
);
INSERT INTO Departments (DepartmentName)
VALUES ('Software Development');
INSERT INTO Departments (DepartmentName)
VALUES ('Network Administration');
INSERT INTO Departments (DepartmentName)
VALUES ('Quality Assurance');
INSERT INTO Departments (DepartmentName)
VALUES ('IT Support');
INSERT INTO Departments (DepartmentName)
VALUES ('Database Administration');
select * from Departments

create table Assets( --one-one
	AssetId int Identity(1,1) not null PRIMARY KEY,
	AssetsName nvarchar(55) not null,
	PurchaseDate date,
	PurchaseCost int not null,
);
INSERT INTO Assets (AssetsName, PurchaseDate, PurchaseCost)
VALUES ('Laptop', '2023-01-15', 800);
INSERT INTO Assets (AssetsName, PurchaseDate, PurchaseCost)
VALUES ('Printer', '2023-03-10', 300);
INSERT INTO Assets (AssetsName, PurchaseDate, PurchaseCost)
VALUES ('Projector', '2022-11-28', 1200);
INSERT INTO Assets (AssetsName, PurchaseDate, PurchaseCost)
VALUES ('Desk Chair', '2023-02-05', 150);
INSERT INTO Assets (AssetsName, PurchaseDate, PurchaseCost)
VALUES ('Monitor', '2023-04-02', 250);
select * from Assets

UPDATE Assets
SET PurchaseCost = 900
WHERE AssetId = 1;

DELETE FROM Assets
WHERE PurchaseDate < '2023-01-01'
AND PurchaseCost > 800;

DELETE FROM Assets
WHERE PurchaseDate BETWEEN '2023-01-01' AND '2023-03-01';

create table UserAssets(
	UserAssetId int Identity(1,1) not null PRIMARY KEY,
	UserID int not null,
	AssetID int not null,
	CONSTRAINT FK_UserAssets_UserID FOREIGN KEY (UserID) REFERENCES Users(UserId),
	CONSTRAINT FK_UserAssets_AssetID FOREIGN KEY (AssetID) REFERENCES Assets(AssetId),
	AssignedDate date,
);
INSERT INTO UserAssets (UserID, AssetID, AssignedDate)
VALUES (1, 1, '2023-01-20');
INSERT INTO UserAssets (UserID, AssetID, AssignedDate)
VALUES (2, 3, '2023-02-10');
INSERT INTO UserAssets (UserID, AssetID, AssignedDate)
VALUES (3, 2, '2023-03-05');
INSERT INTO UserAssets (UserID, AssetID, AssignedDate)
VALUES (4, 4, '2023-04-15');
select * from UserAssets

create table Roles(  --one-one
	RoleId int Identity(1,1) not null PRIMARY KEY,
	RoleName nvarchar(55) not null,
);
INSERT INTO Roles (RoleName)
VALUES ('Administrator');
INSERT INTO Roles (RoleName)
VALUES ('Developer');
INSERT INTO Roles (RoleName)
VALUES ('Designer');
INSERT INTO Roles (RoleName)
VALUES ('Project Manager');
INSERT INTO Roles (RoleName)
VALUES ('Support Specialist');

DELETE FROM Roles
WHERE RoleID NOT IN (1, 3);

create table Locations( --one-one
	LocationId int Identity(1,1) not null PRIMARY KEY,
	LocationName nvarchar(55) not null,
);
INSERT INTO Locations (LocationName)
VALUES ('Los Angeles');
INSERT INTO Locations (LocationName)
VALUES ('London');
INSERT INTO Locations (LocationName)
VALUES ('Tokyo');
INSERT INTO Locations (LocationName)
VALUES ('Sydney');
INSERT INTO Locations (LocationName)
VALUES ('San Francisco');

UPDATE Locations
SET LocationName = 'New York'
WHERE LocationID = 1;

create table AssetCategories( --one-one
	CategoryId int Identity(1,1) not null PRIMARY KEY,
	CategoryName nvarchar(55) not null,
);
INSERT INTO AssetCategories (CategoryName)
VALUES ('Computers');
INSERT INTO AssetCategories (CategoryName)
VALUES ('Office Furniture');
INSERT INTO AssetCategories (CategoryName)
VALUES ('Networking Equipment');
INSERT INTO AssetCategories (CategoryName)
VALUES ('Printers and Scanners');
INSERT INTO AssetCategories (CategoryName)
VALUES ('Audio-Visual Equipment');

DELETE FROM AssetCategories
WHERE CategoryName LIKE '%Computers%';

create table DepartmentsAssets(   --one-many
	DepartmentsAssetId int Identity(1,1) not null PRIMARY KEY,
	DepartmentID int not null,
	AssetsID int not null,
	CONSTRAINT FK_DepartmentsAssets_DepartmentID FOREIGN KEY (DepartmentID) REFERENCES Departments(DepartmentId),
	CONSTRAINT FK_DepartmentsAssets_AssetsID FOREIGN KEY (AssetsID) REFERENCES Assets(AssetId),
	Quantity int,
);
INSERT INTO DepartmentsAssets (DepartmentID, AssetsID, Quantity)
VALUES (1, 1, 10);
INSERT INTO DepartmentsAssets (DepartmentID, AssetsID, Quantity)
VALUES (2, 2, 15);
INSERT INTO DepartmentsAssets (DepartmentID, AssetsID, Quantity)
VALUES (3, 3, 5);
INSERT INTO DepartmentsAssets (DepartmentID, AssetsID, Quantity)
VALUES (4, 4, 8);

create table UserRolesMapping(  --many-many 
	UserRolesMappingId int Identity(1,1) not null PRIMARY KEY,
	UserID int not null,
	RoleID int not null,
	CONSTRAINT FK_UserRolesMapping_UserID FOREIGN KEY (UserID) REFERENCES Users(UserId),
	CONSTRAINT FK_UserRolesMapping_RoleID FOREIGN KEY (RoleID) REFERENCES Roles(RoleId),
);
INSERT INTO UserRolesMapping (UserID, RoleID)
VALUES (2, 2); -- User has Developer role
INSERT INTO UserRolesMapping (UserID, RoleID)
VALUES (4, 5); -- User has Support Specialist role

create table AssetCategoryMapping(  --many-many  
	AssetCategoryID int Identity(1,1) not null PRIMARY KEY,
	AssetID int not null,
	CategoryID int not null,
	CONSTRAINT FK_AssetCategoryMapping_AssetID FOREIGN KEY (AssetID) REFERENCES Assets(AssetID),
	CONSTRAINT FK_AssetCategoryMapping_CategoryID FOREIGN KEY (CategoryID) REFERENCES AssetCategories(CategoryID),
);
INSERT INTO AssetCategoryMapping (AssetID, CategoryID)
VALUES (1, 1); -- Laptop is in the Computers category
INSERT INTO AssetCategoryMapping (AssetID, CategoryID)
VALUES (2, 2); -- Printer is in the Office Furniture category
INSERT INTO AssetCategoryMapping (AssetID, CategoryID)
VALUES (3, 3); -- Projector is in the Networking Equipment category
INSERT INTO AssetCategoryMapping (AssetID, CategoryID)
VALUES (4, 2); -- Desk Chair is in the Office Furniture category
INSERT INTO AssetCategoryMapping (AssetID, CategoryID)
VALUES (5, 5); -- Monitor is in the Audio-Visual Equipment category

create table UserLocationMapping(  --many-many  
	UserLocationID int Identity(1,1) not null PRIMARY KEY,
	UserID int not null,
	LocationID int not null,
	CONSTRAINT FK_UserLocationMapping_UserID FOREIGN KEY (UserID) REFERENCES Users(UserID),
	CONSTRAINT FK_UserLocationMapping_LocationID FOREIGN KEY (LocationID) REFERENCES Locations(LocationID),
);
INSERT INTO UserLocationMapping (UserID, LocationID)
VALUES (1, 1); -- John Doe is in Los Angeles
INSERT INTO UserLocationMapping (UserID, LocationID)
VALUES (2, 3); -- Jane Smith is in Tokyo
INSERT INTO UserLocationMapping (UserID, LocationID)
VALUES (3, 2); -- Michael Johnson is in London
INSERT INTO UserLocationMapping (UserID, LocationID)
VALUES (4, 4); -- Susan White is in Sydney



---UNION

---a.
---query with UNION ALL
SELECT FirstName, LastName, Email
FROM Users
WHERE DepartmentID = 1

UNION ALL

SELECT FirstName, LastName, Email
FROM Users
WHERE DepartmentID = 2;

---OR
SELECT AssetsName, PurchaseDate, PurchaseCost
FROM Assets
WHERE PurchaseDate < '2023-01-01'
OR PurchaseCost > 200;

---b.
---INTERSECTION
SELECT FirstName, LastName
FROM Users
WHERE DepartmentID = 1

INTERSECT

SELECT FirstName, LastName
FROM Users
WHERE DepartmentID = 2;

---IN
SELECT AssetsName, PurchaseDate, PurchaseCost
FROM Assets
WHERE PurchaseDate > '2023-01-01'
AND PurchaseCost IN (800, 900, 1000);



---c.
---EXCEPT
SELECT FirstName, LastName
FROM Users
WHERE DepartmentID = 1

EXCEPT

SELECT FirstName, LastName
FROM Users
WHERE DepartmentID = 2;

---NOT IN
SELECT AssetsName, PurchaseDate, PurchaseCost
FROM Assets
WHERE PurchaseCost NOT IN (300, 1000)



---f.
---EXISTS
--all users that have assigned assets
SELECT FirstName, LastName
FROM Users AS U
WHERE EXISTS (
    SELECT 1
    FROM UserAssets AS UA
    WHERE UA.UserID = U.UserID
);

SELECT A.AssetsName, A.PurchaseDate, A.PurchaseCost
FROM Assets AS A
WHERE EXISTS (
    SELECT 1
    FROM UserLocationMapping AS UL
    WHERE UL.UserID IN (
        SELECT UserID
        FROM Users
        WHERE UL.LocationID = 1 
    )
);

---e.
---IN
---users with assets purchase cost>average cost for department
SELECT FirstName, LastName
FROM Users
WHERE UserID IN (
    SELECT UA.UserID
    FROM UserAssets AS UA
    JOIN Assets AS A ON UA.AssetID = A.AssetID
    WHERE A.PurchaseCost > (
        SELECT MAX(PurchaseCost)
        FROM Assets
        WHERE AssetID IN (
            SELECT AssetID
            FROM UserAssets
            WHERE UserID = UA.UserID
        )
    )
);


SELECT AssetsName, PurchaseCost
FROM Assets
WHERE PurchaseCost IN (200, 250);



---d.
---INNER JOIN
SELECT Users.FirstName, Users.LastName, Assets.AssetsName, UserAssets.AssignedDate
FROM Users
INNER JOIN UserAssets ON Users.UserID = UserAssets.UserID
INNER JOIN Assets ON UserAssets.AssetID = Assets.AssetID
INNER JOIN Departments ON Users.DepartmentID = Departments.DepartmentID;

---LEFT JOIN
SELECT Users.FirstName, Users.LastName, Locations.LocationName, Roles.RoleName
FROM Users
LEFT JOIN UserLocationMapping ON Users.UserID = UserLocationMapping.UserID
LEFT JOIN Locations ON UserLocationMapping.LocationID = Locations.LocationID
LEFT JOIN UserRolesMapping ON Users.UserID = UserRolesMapping.UserID
LEFT JOIN Roles ON UserRolesMapping.RoleID = Roles.RoleID;

---RIGHT JOIN
SELECT Users.FirstName, Users.LastName, Assets.AssetsName, UserAssets.AssignedDate
FROM Users
RIGHT JOIN UserAssets ON Users.UserID = UserAssets.UserID
RIGHT JOIN Assets ON UserAssets.AssetID = Assets.AssetID;


---FULL JOIN
SELECT Users.FirstName, Users.LastName, Assets.AssetsName, UserAssets.AssignedDate
FROM Users
FULL JOIN UserAssets ON Users.UserID = UserAssets.UserID
FULL JOIN Assets ON UserAssets.AssetID = Assets.AssetID;


---h.
SELECT DepartmentID
FROM Users
GROUP BY DepartmentID;


SELECT PurchaseDate
FROM Assets
GROUP BY PurchaseDate
HAVING SUM(PurchaseCost) > 1000;


SELECT PurchaseDate
FROM Assets
GROUP BY PurchaseDate
HAVING AVG(PurchaseCost) > (
    SELECT AVG(PurchaseCost)
    FROM Assets
);

SELECT PurchaseDate, MIN(PurchaseCost) AS MinCost, MAX(PurchaseCost) AS MaxCost
FROM Assets
GROUP BY PurchaseDate
HAVING MAX(PurchaseCost) - MIN(PurchaseCost) > (
    SELECT MAX(PurchaseCost) - MIN(PurchaseCost)
    FROM Assets
);

---i.
SELECT FirstName, LastName
FROM Users
WHERE DepartmentID = ANY (
    SELECT DepartmentID
    FROM Departments
    WHERE DepartmentName = 'Software Development'
);

SELECT FirstName, LastName
FROM Users
WHERE DepartmentID = ALL (
    SELECT DepartmentID
    FROM Departments
    WHERE DepartmentName IN ('Software Development', 'Network Administration')
);

SELECT FirstName, LastName
FROM Users
WHERE DepartmentID IN (
    SELECT DepartmentID
    FROM Departments
    WHERE DepartmentName = 'Software Development'
);

SELECT FirstName, LastName
FROM Users
WHERE DepartmentID NOT IN (
    SELECT DepartmentID
    FROM Departments
    WHERE DepartmentName = 'Software Development'
);


---PurchaseCost of assets with any other asset's PurchaseCost.
SELECT FirstName, LastName
FROM Users
WHERE UserID = ANY (
    SELECT UserID
    FROM UserAssets AS UA
    JOIN Assets AS A ON UA.AssetID = A.AssetID
    WHERE A.PurchaseCost > ANY (
        SELECT PurchaseCost
        FROM Assets
    )
);
--PurchaseCost of assets is lower than all other asset's PurchaseCost values
SELECT FirstName, LastName
FROM Users
WHERE UserID = ALL (
    SELECT UserID
    FROM UserAssets AS UA
    JOIN Assets AS A ON UA.AssetID = A.AssetID
    WHERE A.PurchaseCost < ALL (
        SELECT PurchaseCost
        FROM Assets
    )
);


---g.
-- to calculate the count of assigned assets for each user (GROUP BY UserID)
SELECT FirstName, LastName
FROM Users
WHERE UserID IN (
    SELECT UserID
    FROM UserAssets
    GROUP BY UserID
    HAVING COUNT(AssetID) > 0
);


SELECT U.FirstName, U.LastName, D.DepartmentName
FROM Users U
JOIN Departments D ON U.DepartmentID = D.DepartmentID
WHERE U.DepartmentID = (---filters the Departments table based on the condition that the DepartmentName is 'Software Development
    SELECT DepartmentID
    FROM Departments
    WHERE DepartmentName = 'Software Development'
);



---lab3--

-- a. modify the type of a column
USE [databases-lab]
GO

CREATE PROCEDURE setPurchaseCostFloatForAssetsTable 
AS
	ALTER TABLE Assets
		ALTER COLUMN PurchaseCost FLOAT
GO

CREATE PROCEDURE setPurchaseCostIntForAssetsTable 
AS
	ALTER TABLE Assets
		ALTER COLUMN PurchaseCost INT
GO

--EXEC setPurchaseCostFloatForAssetsTable
--select * from Assets
--EXEC setPurchaseCostIntForAssetsTable

--- add/remove a column
CREATE PROCEDURE addUserAgeToUsersTable
AS
	ALTER TABLE Users
		ADD UserAge INT
GO

CREATE PROCEDURE removeUserAgeToUsersTable
AS
	ALTER TABLE Users
		DROP COLUMN UserAge
GO

--EXEC addUserAgeToUsersTable
--select * from Users
--Exec removeUserAgeToUsersTable


---add/remove a DEFAULT constraint
CREATE PROCEDURE DefaultQuantity
AS
	ALTER TABLE DepartmentsAssets
		ADD CONSTRAINT DefaultQuantityConstraint DEFAULT (20) FOR Quantity
GO

CREATE PROCEDURE DefaultQuantityRemoved
AS
	ALTER TABLE DepartmentsAssets
		DROP CONSTRAINT DefaultQuantityConstraint
GO

--EXEC DefaultQuantity
--SELECT * FROM DepartmentsAssets
--EXEC DefaultQuantityRemoved

---create/drop a table
CREATE PROCEDURE AddProjectTable
AS
    CREATE TABLE Project (
        ProjectID INT IDENTITY(1,1) PRIMARY KEY,
        ProjectName NVARCHAR(100) NOT NULL,
        StartDate DATE,
        EndDate DATE,
        Budget DECIMAL(18, 2)
    )
GO
--EXEC AddProjectTable
CREATE PROCEDURE RemoveProjectTable
AS
	DROP TABLE Project
GO
--EXEC RemoveProjectTable


---add/remove primary key
CREATE PROCEDURE AddNamePrimaryKeyProject
AS
    ALTER TABLE Project
        DROP CONSTRAINT ProjectID
    ALTER TABLE Project
        ADD CONSTRAINT IdNameProjectPrimaryKey PRIMARY KEY (ProjectID,ProjectName)
GO

CREATE PROCEDURE RemoveNamePrimaryKeyProject
AS
    ALTER TABLE Project
        DROP CONSTRAINT IdNameProjectPrimaryKey
    ALTER TABLE AlienShip
        ADD CONSTRAINT ProjectID PRIMARY KEY (ProjectID)
GO
--INSERT INTO Project (ProjectName, StartDate, EndDate, Budget)
--VALUES ('Software Upgrade', '2023-01-15', '2023-05-30', 50000.00)

--select * from Project
--EXEC AddNamePrimaryKeyProject
--EXEC RemoveNamePrimaryKeyProject

CREATE PROCEDURE AddNameCandidateKeyProject
AS
    ALTER TABLE Project
        ADD CONSTRAINT NameCandidateKey UNIQUE (ProjectName)
GO

CREATE PROCEDURE RemoveNameCandidateKeyProject
AS
    ALTER TABLE Project
        DROP CONSTRAINT NameCandidateKey
GO
--EXEC AddNameCandidateKeyProject
--EXEC RemoveNameCandidateKeyProject

CREATE PROCEDURE AddProjectNrForeignKey
AS
    ALTER TABLE Project
        ADD CONSTRAINT ProjectForeignKey FOREIGN KEY (ProjectNr) REFERENCES Departments(DepartmentId)
GO

CREATE PROCEDURE RemoveProjectNrForeignKey
AS
    ALTER TABLE Project
        DROP CONSTRAINT ProjectForeignKey
GO
--alter table Project
--add ProjectNr INT
--EXEC AddProjectNrForeignKey
--EXEC RemoveProjectNrForeignKey


CREATE TABLE VersionTable(
	v int,
	PRIMARY KEY(v)
);
--INSERT INTO VersionTable VALUES(1);
--select * from VersionTable
CREATE TABLE ProcedureTable(
	fromVersion INT,
	intoVersion INT,
	nameProcedure VARCHAR(255),
	PRIMARY KEY(fromVersion, intoVersion)
);
--INSERT INTO ProcedureTable VALUES (1, 2, 'setPurchaseCostFloatForAssetsTable');
--INSERT INTO ProcedureTable VALUES (2, 1, 'setPurchaseCostIntForAssetsTable');
--INSERT INTO ProcedureTable VALUES (2, 3, 'addUserAgeToUsersTable');
--INSERT INTO ProcedureTable VALUES (3, 2, 'removeUserAgeToUsersTable');
--INSERT INTO ProcedureTable VALUES (3, 4, 'DefaultQuantity');
--INSERT INTO ProcedureTable VALUES (4, 3, 'DefaultQuantityRemoved');
--INSERT INTO ProcedureTable VALUES (4, 5, 'AddProjectTable');
--INSERT INTO ProcedureTable VALUES (5, 4, 'RemoveProjectTable');
--INSERT INTO ProcedureTable VALUES (5, 6, 'AddNamePrimaryKeyProject');
--INSERT INTO ProcedureTable VALUES (6, 5, 'RemoveNamePrimaryKeyProject');
--INSERT INTO ProcedureTable VALUES (6, 7, 'AddNameCandidateKeyProject');
--INSERT INTO ProcedureTable VALUES (7, 6, 'RemoveNameCandidateKeyProject');
--INSERT INTO ProcedureTable VALUES (7, 8, 'AddProjectNrForeignKey');
--INSERT INTO ProcedureTable VALUES (8, 7, 'RemoveProjectNrForeignKey');

CREATE PROCEDURE moveToAVersion(@newVersion INT) AS
	DECLARE @curr int
	DECLARE @procedureName varchar(255)
	SELECT @curr=v FROM VersionTable

	IF @newVersion > (SELECT max(intoVersion) FROM ProcedureTable)
		RAISERROR ('Version does not exist', 10, 1)
	
	IF @newVersion < (SELECT min(fromVersion) FROM ProcedureTable)
		RAISERROR ('Version does not exist', 10, 1)
	
	WHILE @curr < @newVersion BEGIN
		SELECT @procedureName=nameProcedure FROM ProcedureTable WHERE @curr=fromVersion AND @curr+1=intoVersion
		EXEC (@procedureName)
		SET @curr=@curr+1
		UPDATE VersionTable SET v=@curr
	END
	
	WHILE @curr > @newVersion BEGIN
		SELECT @procedureName=nameProcedure FROM ProcedureTable WHERE @curr=fromVersion AND @curr-1=intoVersion
		EXEC (@procedureName)
		SET @curr=@curr-1
		UPDATE VersionTable SET v=@curr
	END; 

EXEC moveToAVersion 3

select * from VersionTable