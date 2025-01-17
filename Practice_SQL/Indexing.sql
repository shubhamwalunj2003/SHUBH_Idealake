create database company;

use company;

create table employees
(
	Id INT primary key identity,
	[Name] nvarchar(50),
	Email nvarchar(50),
	Department nvarchar(50)
)

-- Insert 1 million record in a table --
--  Indexing -> Pehle O(n) -> O(nlogn)
--				Separate will be created. and that column will stored inside in sorted order in it.
--				B-Trees data structure is used to store as it is a multi-level format of tree-based indexing, which has balanced binary search trees.
--		Use-case -> Use only inside in read-intensive database. if you use inside write intensive databases u need update record in index column as well as sort it will take more time.



SET NOCOUNT ON

DECLARE @counter int = 1

WHILE(@counter <= 1000000)
BEGIN
	DECLARE @Name nvarchar(50) = 'ABC' + RTRIM(@counter)
	DECLARE @Email nvarchar(50) = 'abc' + RTRIM(@counter) + '@gmail.com'
	DECLARE @Dept nvarchar(50) = 'Dept' + RTRIM(@counter)

	INSERT into employees values (@Name, @Email, @Dept)

	Set @counter = @counter+1

	If(@counter%100000=0)
		Print RTRIM(@counter)+ ' row inserted'
END

select * from employees WHERE Id=93200

-- create a non-cluster index on name column -- 

create nonclustered index IX_employee
ON [dbo].[employees]([Name])

select * from employees WHERE Name = 'ABC32000' 

-- create an index
create index MyIndex
on employees(Name);

--create an index using combination of column
create index MyIndex3
on employees(Name, Email);

--drop an index
drop index employees.MyIndex3;


