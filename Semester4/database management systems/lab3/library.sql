use [library];
CREATE TABLE Authors (
    author_id INT PRIMARY KEY,
    author_name VARCHAR(100),
    author_bio TEXT
);

CREATE TABLE Books (
    book_id INT PRIMARY KEY,
    title VARCHAR(255),
    isbn VARCHAR(20),
    publication_year INT,
    author_id INT,
    FOREIGN KEY (author_id) REFERENCES Authors(author_id)
);
select * from Books;
-- Populate Authors table
INSERT INTO Authors (author_id, author_name, author_bio)
VALUES 
    (1, 'J.K. Rowling', 'Author of the Harry Potter series'),
    (2, 'George Orwell', 'Author of 1984 and Animal Farm'),
    (3, 'Harper Lee', 'Author of To Kill a Mockingbird');

-- Populate Books table
INSERT INTO Books (book_id, title, isbn, publication_year, author_id)
VALUES
    (1, 'Harry Potter and the Philosopher''s Stone', '9780545069670', 1997, 1),
    (2, '1984', '9780451524935', 1949, 2),
    (3, 'Animal Farm', '9780451526342', 1945, 2),
    (4, 'To Kill a Mockingbird', '9780061120084', 1960, 3);
select * from Books

CREATE TABLE Genres (
    genre_id INT PRIMARY KEY,
    genre_name VARCHAR(50)
);

CREATE TABLE Books_Genres (
    book_id INT,
    genre_id INT,
    FOREIGN KEY (book_id) REFERENCES Books(book_id),
    FOREIGN KEY (genre_id) REFERENCES Genres(genre_id)
);

CREATE TABLE Publishers (
    publisher_id INT PRIMARY KEY,
    publisher_name VARCHAR(100),
    headquarters_location VARCHAR(255)
);

CREATE TABLE Book_Publishers (
    book_id INT,
    publisher_id INT,
    FOREIGN KEY (book_id) REFERENCES Books(book_id),
    FOREIGN KEY (publisher_id) REFERENCES Publishers(publisher_id)
);

CREATE TABLE Users (
    user_id INT PRIMARY KEY,
    username VARCHAR(50),
    email VARCHAR(100),
    password VARCHAR(255),
    registration_date DATE
);

CREATE TABLE Borrowings (
    borrowing_id INT PRIMARY KEY,
    user_id INT,
    book_id INT,
    borrow_date DATE,
    return_date DATE,
    FOREIGN KEY (user_id) REFERENCES Users(user_id),
    FOREIGN KEY (book_id) REFERENCES Books(book_id)
);

CREATE TABLE Librarians (
    librarian_id INT PRIMARY KEY,
    librarian_name VARCHAR(100),
    department VARCHAR(100)
);
CREATE TABLE Libraries (
    library_id INT PRIMARY KEY,
    library_name VARCHAR(100),
    location VARCHAR(100)
);

CREATE TABLE LibrariansL (
    librarian_id INT PRIMARY KEY,
    librarian_name VARCHAR(100),
    library_id INT,
    FOREIGN KEY (library_id) REFERENCES Libraries(library_id)
);

-- Insert values into Libraries table
INSERT INTO Libraries (library_id, library_name, location)
VALUES
    (1, 'Main Library', '123 Main St'),
    (2, 'Branch Library', '456 Elm St');

-- Insert values into LibrariansL table
INSERT INTO LibrariansL (librarian_id, librarian_name, library_id)
VALUES
    (1, 'John Doe', 1),
    (2, 'Jane Smith', 1),
    (3, 'Michael Johnson', 2);

select * from LibrariansL
select * from Libraries


CREATE TABLE Library_Transactions (
    transaction_id INT PRIMARY KEY,
    librarian_id INT,
    borrowing_id INT,
    transaction_date DATE,
    transaction_type VARCHAR(50),
    FOREIGN KEY (librarian_id) REFERENCES Librarians(librarian_id),
    FOREIGN KEY (borrowing_id) REFERENCES Borrowings(borrowing_id)
);


-- Populate Borrowings table
INSERT INTO Borrowings (borrowing_id, user_id, book_id, borrow_date, return_date)
VALUES
    (1, 1, 1, '2022-01-05', '2022-01-15'),
    (2, 2, 2, '2022-01-10', '2022-01-20'),
    (3, 1, 3, '2022-01-15', '2022-01-25'),
    (4, 3, 4, '2022-02-01', '2022-02-10');


-- Populate Users table
INSERT INTO Users (user_id, username, email, password, registration_date)
VALUES 
    (1, 'user1', 'user1@example.com', 'password', '2022-01-01'),
    (2, 'user2', 'user2@example.com', 'password', '2022-01-15'),
    (3, 'user3', 'user3@example.com', 'password', '2022-02-03');

-- Populate Books table
INSERT INTO Books (book_id, title, isbn, publication_year, author_id)
VALUES
    (1, 'Harry Potter and the Philosopher''s Stone', '9780545069670', 1997, 1),
    (2, '1984', '9780451524935', 1949, 2),
    (3, 'Animal Farm', '9780451526342', 1945, 2),
    (4, 'To Kill a Mockingbird', '9780061120084', 1960
	, 3);

		-- Populate Publishers table
INSERT INTO Publishers (publisher_id, publisher_name, headquarters_location)
VALUES
    (1, 'Bloomsbury Publishing', 'London, UK'),
    (2, 'Penguin Books', 'New York, USA'),
    (3, 'HarperCollins Publishers', 'New York, USA'),
    (4, 'Random House', 'New York, USA');

INSERT INTO Book_Publishers (book_id, publisher_id)
VALUES
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 4);

use [library]
select * from Publishers
select * from Book_Publishers
select * from Books

CREATE FUNCTION dbo.ValidateBookTitle(@title NVARCHAR(255))
RETURNS BIT
BEGIN
    RETURN CASE WHEN EXISTS (SELECT 1 FROM Books WHERE title = @title) THEN 1 ELSE 0 END
END;
GO

CREATE FUNCTION dbo.ValidatePublisherName(@publisher_name NVARCHAR(255))
RETURNS BIT
AS
BEGIN
    RETURN CASE WHEN EXISTS (SELECT 1 FROM Publishers WHERE publisher_name = @publisher_name) THEN 1 ELSE 0 END
END;
GO

--create a stored procedure that inserts data in tables that are in a m:n
--relationship; if one insert fails, all the operations performed by the
--procedure must be rolled back (grade 3);

CREATE TABLE ID_Tracker (
    TableName NVARCHAR(255) PRIMARY KEY,
    NextID INT
);

-- Initialize the table with the starting IDs for Books and Publishers
INSERT INTO ID_Tracker (TableName, NextID) VALUES ('Books', 6);
INSERT INTO ID_Tracker (TableName, NextID) VALUES ('Publishers', 5);
GO
drop table ID_Tracker
select * from ID_Tracker

-- Stored Procedure to add Book and Publisher
CREATE OR ALTER PROCEDURE dbo.AddBookAndPublisher
    @title NVARCHAR(255),
    @isbn NVARCHAR(13),
    @publication_year INT,
    @publisher_name NVARCHAR(255),
    @headquarters_location NVARCHAR(255),
    @Author_id INT
AS
BEGIN
    SET NOCOUNT ON;
    DECLARE @book_id INT, @publisher_id INT;

    BEGIN TRY
        BEGIN TRANSACTION;

        -- Check if book title already exists
        IF (dbo.ValidateBookTitle(@title) = 1)
        BEGIN
            RAISERROR('Book title already exists.', 14, 1);
            ROLLBACK TRANSACTION;
            RETURN;
        END

        -- Check if the publisher already exists
        IF (dbo.ValidatePublisherName(@publisher_name) = 1)
        BEGIN
            RAISERROR('Publisher name already exists.', 14, 1);
            ROLLBACK TRANSACTION;
            RETURN;
        END
		-- Get the next available book ID
        SELECT @book_id = NextID FROM ID_Tracker WHERE TableName = 'Books';
        -- Increment the book ID in the tracker table
        UPDATE ID_Tracker SET NextID = NextID + 1 WHERE TableName = 'Books';

        -- Get the next available publisher ID
        SELECT @publisher_id = NextID FROM ID_Tracker WHERE TableName = 'Publishers';
        -- Increment the publisher ID in the tracker table
        UPDATE ID_Tracker SET NextID = NextID + 1 WHERE TableName = 'Publishers';


        -- Insert into Books table
        INSERT INTO Books (book_id,title, isbn, publication_year, author_id)
        VALUES (@book_id,@title, @isbn, @publication_year, @Author_id);

        
        -- Insert into Publishers table
        INSERT INTO Publishers (publisher_id,publisher_name, headquarters_location)
        VALUES (@publisher_id,@publisher_name, @headquarters_location);

        -- Insert into Book_Publishers table
        INSERT INTO Book_Publishers (book_id, publisher_id)
        VALUES (@book_id, @publisher_id);

        -- Commit the transaction
        COMMIT TRANSACTION;
        SELECT 'Transaction committed' AS Result;
    END TRY
    BEGIN CATCH
        -- Rollback the transaction if any error occurs
        ROLLBACK TRANSACTION;
        -- Return the error message
        SELECT ERROR_MESSAGE() AS ErrorMessage, 'Transaction rollbacked' AS Result;
    END CATCH;
END;
GO

-- ok case
-- Before the operation
SELECT * FROM Books;
SELECT * FROM Publishers;
SELECT * FROM Book_Publishers;
select * from authors
-- Execution with expected commit
EXEC dbo.AddBookAndPublisher 
    @title = 'The Book', 
    @isbn = '9780743273562', 
    @publication_year = 1929, 
    @publisher_name = 'B', 
    @headquarters_location = 'Romania',
    @Author_id = 2;

-- After the operation
SELECT * FROM Books;
SELECT * FROM Publishers;
SELECT * FROM Book_Publishers;


--duplicate titles
-- Before the operation
SELECT * FROM Books;
SELECT * FROM Publishers;
SELECT * FROM Book_Publishers;

-- Execution with expected rollback
EXEC dbo.AddBookAndPublisher 
    @title = '1984', -- Duplicate title
    @isbn = '9780451524935', 
    @publication_year = 1949, 
    @publisher_name = 'Penguin Books', 
    @headquarters_location = 'New York, USA',
    @Author_id = 2;

-- After the operation
SELECT * FROM Books;
SELECT * FROM Publishers;
SELECT * FROM Book_Publishers;


--duplicate name
-- Before the operation
SELECT * FROM Books;
SELECT * FROM Publishers;
SELECT * FROM Book_Publishers;

-- Execution with expected rollback
EXEC dbo.AddBookAndPublisher 
    @title = 'The Catcher in the Rye', 
    @isbn = '9780316769488', 
    @publication_year = 1951, 
    @publisher_name = 'Penguin Books', -- Duplicate publisher
    @headquarters_location = 'New York, USA',
    @Author_id = 3;

-- After the operation
SELECT * FROM Books;
SELECT * FROM Publishers;
SELECT * FROM Book_Publishers;


--create a stored procedure that inserts data in tables that are in a m:n
--relationship; if an insert fails, try to recover as much as possible from the
--entire operation: for example, if the user wants to add a book and its
--authors, succeeds creating the authors, but fails with the book, the authors
--should remain in the database (grade 5);

CREATE OR ALTER PROCEDURE InsertBookPublisher
    @book_title NVARCHAR(255),
    @isbn NVARCHAR(13),
    @publication_year INT,
    @publisher_name NVARCHAR(255),
    @headquarters_location NVARCHAR(255),
	@Author_id INT
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @BookID INT = -1;
    DECLARE @PublisherID INT = -1;
	-- Validate book title
    IF dbo.ValidateBookTitle(@book_title) = 1
    BEGIN
        PRINT 'Book with the same title already exists.';
    END;
    ELSE
    BEGIN
        -- Attempt to insert book
        BEGIN TRY
            -- Get the next available book ID
            SELECT @BookID = NextID FROM ID_Tracker WHERE TableName = 'Books';
            -- Increment the book ID in the tracker table
            UPDATE ID_Tracker SET NextID = NextID + 1 WHERE TableName = 'Books';

            INSERT INTO Books (book_id, title, isbn, publication_year, author_id)
            VALUES (@BookID, @book_title, @isbn, @publication_year, @Author_id);
        END TRY
        BEGIN CATCH
            PRINT 'Book insertion failed. Rolling back...';
            RETURN;
        END CATCH;
    END;

    -- Validate publisher name
    IF dbo.ValidatePublisherName(@publisher_name) = 1
    BEGIN
        PRINT 'Publisher with the same name already exists.';
    END;
    ELSE
    BEGIN
        -- Attempt to insert publisher
        BEGIN TRY
            -- Get the next available publisher ID
            SELECT @PublisherID = NextID FROM ID_Tracker WHERE TableName = 'Publishers';
            -- Increment the publisher ID in the tracker table
            UPDATE ID_Tracker SET NextID = NextID + 1 WHERE TableName = 'Publishers';

            INSERT INTO Publishers (publisher_id, publisher_name, headquarters_location)
            VALUES (@PublisherID, @publisher_name, @headquarters_location);
        END TRY
        BEGIN CATCH
            PRINT 'Publisher insertion failed. Rolling back...';
            RETURN;
        END CATCH;
    END;

    -- Attempt to insert book-publisher relationship
    -- Insert book-publisher relationship only if both book and publisher were successfully inserted
    IF @BookID IS DISTINCT FROM -1 AND @PublisherID IS DISTINCT FROM -1
    BEGIN
        BEGIN TRY
            INSERT INTO Book_Publishers (book_id, publisher_id)
            VALUES (@BookID, @PublisherID);
        END TRY
        BEGIN CATCH
            PRINT 'Book-Publisher relationship insertion failed. Rolling back...';
            RETURN;
        END CATCH;
    END;

    PRINT 'Data insertion successful!';
END;
SELECT * FROM Books;
SELECT * FROM Publishers;
SELECT * FROM Book_Publishers;
---good
EXEC InsertBookPublisher 
    @book_title = 'Gs',
    @isbn = '9780743273568',
    @publication_year = 1921,
    @publisher_name = 'Sdd',
    @headquarters_location = 'New York',
	@Author_id = 2
SELECT * FROM Books;
SELECT * FROM Publishers;
SELECT * FROM Book_Publishers;

-- Book Title Already Exists
EXEC InsertBookPublisher 
    @book_title = 'The Great Gatsby',  -- Existing book title
    @isbn = '9780743273565',
    @publication_year = 1925,
    @publisher_name = 'P B',  -- New publisher name
    @headquarters_location = 'London, UK',
	@Author_id = 2
SELECT * FROM Books;
SELECT * FROM Publishers;
SELECT * FROM Book_Publishers;
delete from Book_Publishers where publisher_id = 6

-- Publisher Name Already Exists
EXEC InsertBookPublisher 
    @book_title = 'The Cat',  -- New book title
    @isbn = '9780316769488',
    @publication_year = 1951,
    @publisher_name = 'Scribner',  -- Existing publisher name
    @headquarters_location = 'New York, USA',
	@Author_id = 2






















--reproduce the following concurrency issues under pessimistic isolation
--levels: dirty read, non-repeatable read, phantom read, deadlock; you can
--use stored procedures and / or stand-alone queries; find solutions to solve
--/ workaround the concurrency issues (grade 9)

--Dirty Read
--Solution: T1: 1 update + delay + rollback, T2: select + delay + select 
-- Transaction 1: Update record but do not commit
BEGIN TRANSACTION;
UPDATE Books SET isbn = 2004 
WHERE book_id = 2;
WAITFOR DELAY '00:00:10'; -- Introduce delay
ROLLBACK TRANSACTION

select * from Books



--NON-REPEATABLE READS – T1: insert + delay + update + commit, T2: select + delay +select 
--> see the insert in first select of T2 + update in the second select of T2, 
--T1 finish first
INSERT INTO Books(book_id,title,isbn,publication_year,author_id) VALUES (2001,'here1','123456789',2006,1)
select * from Books
BEGIN TRAN
WAITFOR DELAY '00:00:05'
UPDATE Books SET isbn='11' WHERE
title = 'here1'
COMMIT TRAN

--PHANTOM READS – T1: delay + insert + commit, T2: select + delay + select 
--> see the inserted
--value only at the second select from T2, T1 finish first. 
--The result will contain the previous row
--version; the same number of rows

BEGIN TRAN
WAITFOR DELAY '00:00:05'
INSERT INTO Books(book_id,title,isbn,publication_year,author_id) VALUES
(209,'here18','123456780',2009,1)
COMMIT TRAN

--DEADLOCK – T1: update on table A + delay + update on table B, 
--T2: update on table B + delay + update on table A

-- transaction 1
select * from Books
select * from Publishers
begin tran
update Books set title='deadlock Books Transaction 1' where book_id=1
-- this transaction has exclusively lock on table Books
waitfor delay '00:00:10'
update Publishers set publisher_name='deadlock Publishers Transaction 1' where publisher_id=1
commit tran
