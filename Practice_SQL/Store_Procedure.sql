USE myDB;

GO

-- Drop the procedure if it already exists
IF OBJECT_ID('AddEmployees', 'P') IS NOT NULL
    DROP PROCEDURE AddEmployees;
GO

-- Create the table with proper data types
CREATE TABLE employee (
    empno INT PRIMARY KEY,
    ename VARCHAR(50),
    esalary INT,
    manager VARCHAR(50),
    [name] VARCHAR(50), -- Enclosed name in square brackets
    ecity VARCHAR(50)
);
GO

-- Create the AddEmployees procedure
CREATE PROCEDURE AddEmployees
AS
BEGIN
    DECLARE @i INT = 1;

    WHILE @i <= 100
    BEGIN
        INSERT INTO employee (empno, ename, esalary, manager, [name], ecity)
        VALUES (@i, CONCAT('Employee', @i), 40000 + (@i * 100), 
                CONCAT('Manager', (@i % 5) + 1), CONCAT('Name', @i), CONCAT('City', (@i % 10) + 1));
        
        SET @i = @i + 1;
    END
END;
GO

-- Execute the procedure
EXEC AddEmployees;


select * from employee;

create procedure AllRecords
AS 
select * from employee

exec AllRecords;

create procedure AllRecords2  @ecity varchar(20)
AS 
select * from employee where ecity = @ecity;

exec AllRecords2 @ecity = 'City4'

create procedure AllRecords3  @ecity varchar(20), @empno int
AS 
select * from employee where ecity = @ecity AND empno = @empno;

exec AllRecords3 @ecity = 'City4', @empno=43

-- find the highest salary for an employee with the manager named Manager4 in the employee table.

create procedure GetHighestSalaryByManager 
AS
BEGIN 
	select TOP 1 empno , ename, esalary, manager, [name], ecity
	from employee
	where manager = 'Manager4'
	order by esalary desc;
END;

exec GetHighestSalaryByManager;

--  Find the Total Number of Employees in Each City

create procedure TotalNoOfEmployeeInCity
AS
BEGIN
	select ecity, COUNT(*) AS total_employees
	from employee
	GROUP BY ecity;
END;

exec TotalNoOfEmployeeInCity;

-- Find Employees with Salary Greater Than a Specified Amount

create procedure GetEmployeeAboveSalary
	@esalary INT
AS
BEGIN
	select empno, ename, esalary
	from employee
	where esalary > @esalary;
END;

exec GetEmployeeAboveSalary @esalary = 40000;

-- Find the Average Salary of Employees

create procedure AvgSalaryOfEmployee
AS
BEGIN
	select AVG(esalary) as avg_salary
	from employee;
END;

EXEC AvgSalaryOfEmployee;

-- List All Employees Managed by a Specific Manager

create procedure ListAllEmployeeManagedByManager
	@manager varchar(20)
AS
BEGIN
	select empno , ename, esalary, manager, [name], ecity
	from employee
	where manager = @manager;
END;

EXEC ListAllEmployeeManagedByManager @manager = 'Manager4'


-- Count the Number of Employees with Salary Above the Average

create procedure NoOfemployessWithSalaryAboveAvg
AS
BEGIN
	DECLARE @avgsalary INT;
	SELECT @avgsalary = AVG(esalary) FROM employee;

	select COUNT(*) AS above_avg_count_salary
	FROM employee
	WHERE esalary > @avgsalary
END;

exec NoOfemployessWithSalaryAboveAvg;

-- Update Salary by a Percentage for a Given Manager
create procedure GetEmployeeSalaryHavingManager1
	@managerName varchar(20)
AS
BEGIN
	select empno, ename, esalary, manager
	from employee
	WHERE manager = @managerName
END;

exec GetEmployeeSalaryHavingManager1 @managerName = 'manager4';

create procedure UpdateSalaryByPercentage
	@managerName varchar(20), @percentage FLOAT
AS
BEGIN
	UPDATE employee
	SET esalary = esalary + (esalary*@percentage/100)
	WHERE manager = @managerName
END;

exec UpdateSalaryByPercentage @managerName = 'Manager4', @percentage = 10;

-- nth maximum salary and take n from user

create procedure GetNthHighestSalary
	@nth INT
AS
BEGIN
	select distinct esalary
	from employee
	order by esalary DESC
	offset @nth-1 rows fetch next 1 rows only;
END;

exec GetNthHighestSalary @nth=100;