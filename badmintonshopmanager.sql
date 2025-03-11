-- phpMyAdmin SQL Dump
-- version 5.1.3
-- https://www.phpmyadmin.net/
--

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

-- database : badmintonshopmangager

-- Tạo bảng Employee
CREATE TABLE employee (
    EmployeeID VARCHAR(10) PRIMARY KEY,
    FullName VARCHAR(50) NOT NULL,
    Age INT NOT NULL,
    Phone VARCHAR(15) NOT NULL,
    Email VARCHAR(50) NOT NULL,
    Address VARCHAR(256) NOT NULL,
    Gender ENUM('Nam', 'Nữ') NOT NULL,
    Salary DOUBLE NOT NULL,
    StartDate DATE NOT NULL
);

-- Tạo dữ liệu cho Employee
INSERT INTO employee(`EmployeeID`,`FullName`,`Age`,`Phone`,`Email`,`Address`,`Gender`,`Salary`,`StartDate`) VALUES
('E00', 'Admin', 30, '0987654321', 'admin@gmail.com', 'abc', 'Nam', 0, '2022-08-20');

-- Tạo bảng Employee Rank
CREATE TABLE employee_rank (
    RankID VARCHAR(10) PRIMARY KEY,
    RankName VARCHAR(50) NOT NULL,
    BaseSalary DOUBLE NOT NULL
);

-- Tạo dữ liệu cho Employee Rank
INSERT INTO employee_rank(`RankID`,`RankName`,`BaseSalary`) VALUES
('R00','Admin','0');

-- Tạo bảng Account
CREATE TABLE account (
    Username VARCHAR(50) PRIMARY KEY,
    Password VARCHAR(50) NOT NULL,
    EmployeeID VARCHAR(10) NOT NULL,
    RankID VARCHAR(10) NOT NULL
);

-- Tạo dữ liệu cho Accoutn
INSERT INTO account(`Username`,`Password`,`EmployeeID`,`RankID`) VALUES
('A00','Admin','E00','R00');


-- Tạo bảng Customer
CREATE TABLE customer (
    CustomerID VARCHAR(10) PRIMARY KEY,
    FullName VARCHAR(50) NOT NULL,
    Phone VARCHAR(15) NOT NULL,
    TotalSpending DOUBLE NOT NULL
);

-- Tạo bảng Sales Invoice
CREATE TABLE sales_invoice (
    SalesID VARCHAR(10) PRIMARY KEY,
    EmployeeID VARCHAR(10) NOT NULL,
    CustomerID VARCHAR(10) NOT NULL
);

-- Tạo bảng Type Product
CREATE TABLE type_product (
    TypeID VARCHAR(10) PRIMARY KEY,
    TypeName VARCHAR(50) NOT NULL
);

-- Tạo bảng Supplier
CREATE TABLE supplier (
    SupplierID VARCHAR(10) PRIMARY KEY,
    SupplierName VARCHAR(50) NOT NULL,
    Phone VARCHAR(15) NOT NULL,
    Email VARCHAR(256),
    Address VARCHAR(256)
);

-- Tạo bảng Product
CREATE TABLE product (
    ProductID VARCHAR(10) PRIMARY KEY,
    ProductName VARCHAR(50),
    ProductImg VARCHAR(256),
    Quantity INT NOT NULL,
    SupplierID VARCHAR(10) NOT NULL,
    TypeID VARCHAR(10) NOT NULL
);

-- Tạo bảng Detail of Sales Invoice
CREATE TABLE detail_sales_invoice (
    DetailSalesID VARCHAR(10) PRIMARY KEY,
    SalesID VARCHAR(10) NOT NULL,
    ProductID VARCHAR(10) NOT NULL,
    Quantity INT NOT NULL,
    Price DOUBLE NOT NULL,
    TotalPrice DOUBLE NOT NULL
);

-- Tạo bảng Import Invoice
CREATE TABLE import_invoice (
    ImportID VARCHAR(10) PRIMARY KEY,
    EmployeeID VARCHAR(10) NOT NULL,
    SupplierID VARCHAR(10) NOT NULL
);

-- Tạo bảng Detail of Import Invoice
CREATE TABLE detail_import_invoice (
    DetailImportID VARCHAR(10) PRIMARY KEY,
    ImportID VARCHAR(10) NOT NULL,
    ProductID VARCHAR(10) NOT NULL,
    Quantity INT NOT NULL,
    Price DOUBLE NOT NULL,
    TotalPrice DOUBLE NOT NULL
);

-- Cấp lại các foreign keys
ALTER TABLE account ADD CONSTRAINT fk_employee FOREIGN KEY (EmployeeID) REFERENCES employee(EmployeeID);
ALTER TABLE account ADD CONSTRAINT fk_rank FOREIGN KEY (RankID) REFERENCES employee_rank(RankID);
ALTER TABLE sales_invoice ADD CONSTRAINT fk_employee_sales FOREIGN KEY (EmployeeID) REFERENCES employee(EmployeeID);
ALTER TABLE sales_invoice ADD CONSTRAINT fk_customer_sales FOREIGN KEY (CustomerID) REFERENCES customer(CustomerID);
ALTER TABLE product ADD CONSTRAINT fk_supplier FOREIGN KEY (SupplierID) REFERENCES supplier(SupplierID);
ALTER TABLE product ADD CONSTRAINT fk_type FOREIGN KEY (TypeID) REFERENCES type_product(TypeID);
ALTER TABLE detail_sales_invoice ADD CONSTRAINT fk_sales FOREIGN KEY (SalesID) REFERENCES sales_invoice(SalesID);
ALTER TABLE detail_sales_invoice ADD CONSTRAINT fk_product_sales FOREIGN KEY (ProductID) REFERENCES product(ProductID);
ALTER TABLE import_invoice ADD CONSTRAINT fk_employee_import FOREIGN KEY (EmployeeID) REFERENCES employee(EmployeeID);
ALTER TABLE import_invoice ADD CONSTRAINT fk_supplier_import FOREIGN KEY (SupplierID) REFERENCES supplier(SupplierID);
ALTER TABLE detail_import_invoice ADD CONSTRAINT fk_import FOREIGN KEY (ImportID) REFERENCES import_invoice(ImportID);
ALTER TABLE detail_import_invoice ADD CONSTRAINT fk_product_import FOREIGN KEY (ProductID) REFERENCES product(ProductID);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;


