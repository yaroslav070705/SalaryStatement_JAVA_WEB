CREATE DATABASE salary_statement_db
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.UTF-8'
    LC_CTYPE = 'en_US.UTF-8'
    LOCALE_PROVIDER = 'libc'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

CREATE TABLE Posts (
	post_id UUID PRIMARY KEY,
	post_name VARCHAR(50),
	payout_value DOUBLE
);

CREATE TABLE Employees (
	employee_id UUID PRIMARY KEY,
	"name" VARCHAR(50),
	surname VARCHAR(50),
	middle_name VARCHAR(50),
	birth_date DATE,
	work_experience INTERVAL,
	post_id UUID REFERENCES Posts(post_id),
	fired BOOL
);

CREATE TABLE Employees_Posts_History (
	employee_id UUID REFERENCES Employee(employee_id),
	post_id UUID REFERENCES Posts(post_id),
	start_date DATE,
	PRIMARY KEY (employee_id, post_id)
);

CREATE TABLE Payout_Types (
	payout_type_id UUID PRIMARY KEY,
	payout_type varchar(50)
);

CREATE TABLE Payouts (
	employee_id UUID REFERENCES Employee(employee_id),
	payout_type_id UUID REFERENCES Payout_Types(payout_type_id),
	"date" DATE,
	"value" DOUBLE,
	PRIMARY KEY(employee_id, payout_type_id)
);

CREATE TABLE Roles (
	role_id UUID PRIMARY KEY,
	role_name VARCHAR(50)
);

CREATE TABLE Bonus_Payouts_Values (
	payout_type_id UUID REFERENCES Payout_Types(payout_type_id),
	"value" INTEGER,
	PRIMARY KEY(payout_type_id)
);

CREATE TABLE Projects (
	project_id UUID PRIMARY KEY,
	project_name VARCHAR(50),
	start_date DATE,
	end_date DATE
);

CREATE TABLE Projects_Setup (
	employee_id UUID REFERENCES Employee(employee_id),
	project_id UUID REFERENCES Projects(project_id),
	role_id UUID REFERENCES Roles(role_id),
	PRIMARY KEY(employee_id, project_id)
);

CREATE TABLE Role_Payout_Value (
	project_id UUID REFERENCES Projects(project_id),
	role_id UUID REFERENCES Roles(role_id),
	"value" DOUBLE,
	PRIMARY KEY(project_id, role_id)
);

CREATE TABLE Employees_Roles_History (
	employee_id UUID REFERENCES Employee(employee_id),
	project_id UUID REFERENCES Projects(project_id),
	role_id UUID REFERENCES Roles(role_id),
	start_date DATE,
	end_date DATE,
	PRIMARY KEY(employee_id, project_id, role_id)
);

CREATE TABLE Work_Experience_Payout_Value (
	experience_id UUID PRIMARY KEY,
	work_experience INTERVAL,
	"value" DOUBLE
);
