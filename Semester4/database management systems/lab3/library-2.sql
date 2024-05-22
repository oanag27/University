use [library]
--dirty read
-- Transaction 2: Read the record before Transaction 1 commits
SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED
BEGIN TRAN
SELECT * FROM Books
WAITFOR DELAY '00:00:15'
SELECT * FROM Books
COMMIT TRAN

SET TRANSACTION ISOLATION LEVEL READ COMMITTED
BEGIN TRAN
SELECT * FROM Books
WAITFOR DELAY '00:00:15'
SELECT * FROM Books
COMMIT TRAN


--non-repeatable reads:see only the final result in
--both of the select of T2, T1 finish first
SET TRANSACTION ISOLATION LEVEL READ COMMITTED
BEGIN TRAN
SELECT * FROM Books
WAITFOR DELAY '00:00:05'
SELECT * FROM Books
COMMIT TRAN

SET TRANSACTION ISOLATION LEVEL REPEATABLE READ
BEGIN TRAN
SELECT * FROM Books
WAITFOR DELAY '00:00:05'
SELECT * FROM Books
COMMIT TRAN


--phantom reads
SET TRANSACTION ISOLATION LEVEL REPEATABLE
READ
BEGIN TRAN
SELECT * FROM Books
WAITFOR DELAY '00:00:05'
SELECT * FROM Books
COMMIT TRAN

SET TRANSACTION ISOLATION LEVEL SERIALIZABLE
BEGIN TRAN
SELECT * FROM Books
WAITFOR DELAY '00:00:05'
SELECT * FROM Books
COMMIT TRAN--deadlock-- transaction 2
begin tran
update Books set title='deadlock Books Transaction 2' where book_id=1
-- this transaction has exclusively lock on table Books
waitfor delay '00:00:10'
update Publishers set publisher_name='deadlock Publishers Transaction 2' where publisher_id=1
commit tran

select * from Books
select * from Publishers