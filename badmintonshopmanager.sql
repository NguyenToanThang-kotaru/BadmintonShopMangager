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
    StartDate DATE NOT NULL,
    `Status` varchar(10) NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Tạo dữ liệu cho Employee
INSERT INTO employee(EmployeeID, FullName, Age, Phone, Email, Address, Gender, Salary, StartDate, `Status`) 
VALUES
('E01', 'Nguyen Van A', 28, '0123456789', 'a@gmail.com', 'Hanoi', 'Nam', 7000000, '2024-03-14','Hiện'),
('E02', 'Tran Thi B', 25, '0987654321', 'b@gmail.com', 'Ho Chi Minh', 'Nữ', 8000000, '2024-03-14','Hiện');

-- Tạo bảng Employee Rank
CREATE TABLE employee_rank (
    RankID VARCHAR(10) PRIMARY KEY,
    RankName VARCHAR(50) NOT NULL,
    BaseSalary DOUBLE NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Thêm dữ liệu mẫu cho bảng employee_rank
INSERT INTO employee_rank(RankID, RankName, BaseSalary) 
VALUES
('R01', 'Nhân viên', 5000000),
('R02', 'Quản lý', 10000000);

-- Tạo bảng Account
CREATE TABLE account (
    Username VARCHAR(50) PRIMARY KEY,
    `Password` VARCHAR(50) NOT NULL,
    EmployeeID VARCHAR(10) NOT NULL,
    RankID VARCHAR(10) NOT NULL,
    `Status` varchar(10) NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO account(Username, `Password`, EmployeeID, RankID, `Status`) 
VALUES
('admin', 'admin123', 'E01', 'R02', 'Hiện'),
('staff1', 'staff123', 'E02', 'R01', 'Hiện');


-- Tạo bảng Customer
CREATE TABLE customer (
    CustomerID VARCHAR(10) PRIMARY KEY,
    FullName VARCHAR(50) NOT NULL,
    Phone VARCHAR(15) NOT NULL,
    TotalSpending DOUBLE NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Thêm dữ liệu mẫu cho bảng customer
INSERT INTO customer(CustomerID, FullName, Phone, TotalSpending) 
VALUES
('C01', 'Le Van C', '0912345678', 500000),
('C02', 'Pham Thi D', '0934567890', 1200000);

-- Tạo bảng Sales Invoice
CREATE TABLE sales_invoice (
    SalesID VARCHAR(10) PRIMARY KEY,
    EmployeeID VARCHAR(10) NOT NULL,
    CustomerID VARCHAR(10) NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Thêm dữ liệu mẫu cho bảng sales_invoice
INSERT INTO sales_invoice(SalesID, EmployeeID, CustomerID) 
VALUES
('SI01', 'E01', 'C01'),
('SI02', 'E02', 'C02');

-- Tạo bảng Type Product
CREATE TABLE type_product (
    TypeID VARCHAR(10) PRIMARY KEY,
    TypeName VARCHAR(50) NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO type_product(TypeID, TypeName) 
VALUES
('T01', 'Vợt cầu lông'),
('T02', 'Giày cầu lông');

-- Tạo bảng Supplier
CREATE TABLE supplier (
    SupplierID VARCHAR(10) PRIMARY KEY,
    SupplierName VARCHAR(50) NOT NULL,
    Phone VARCHAR(15) NOT NULL,
    Email VARCHAR(256),
    Address VARCHAR(256)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO supplier(SupplierID, SupplierName, Phone, Email, Address) 
VALUES
('S01', 'Yonex', '0988123456', 'contact@yonex.com', 'Nhật Bản'),
('S02', 'Lining', '0977123456', 'support@lining.com', 'Trung Quốc');

-- Tạo bảng Product
CREATE TABLE product (
    ProductID VARCHAR(10) PRIMARY KEY,
    ProductName VARCHAR(50),
    ProductImg VARCHAR(256),
    Quantity INT NOT NULL,
    SupplierID VARCHAR(10) NOT NULL,
    TypeID VARCHAR(10) NOT NULL,
    Price double NOT NULL,
    `Status` varchar(50) NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Thêm dữ liệu mẫu cho bảng product
INSERT INTO product(ProductID, ProductName, ProductImg, Quantity, SupplierID, TypeID, Price, `Status`) 
VALUES
('P01', 'Vợt Yonex Astrox', 'astrox.jpg', 5, 'S01', 'T01', 4000000, 'Hiện'),
('P02', 'Giày Lining Turbo', 'lining_turbo.jpg', 3, 'S02', 'T02', 1620000, 'Hiện');


-- Tạo bảng Product Detail
CREATE TABLE product_detail (
	Series BIGINT PRIMARY KEY AUTO_INCREMENT,
    ProductID VARCHAR(10) NOT NULL,
    ImportDate DATE NOT NULL,
    `Status` varchar(50) NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Thêm dữ liệu mẫu cho bảng product
INSERT INTO product_detail(Series, ProductID, `Status`) 
VALUES
(1,'P01','Hiện'),
(2,'P01','Hiện'),
(3,'P01','Hiện'),
(4,'P01','Hiện'),
(5,'P01','Hiện'),
(6,'P02','Hiện'),
(7,'P02','Hiện'),
(8,'P02','Hiện');

-- Tạo bảng Detail of Sales Invoice
CREATE TABLE sales_invoice_detail (
    SalesDetailID VARCHAR(10) PRIMARY KEY,
    SalesID VARCHAR(10) NOT NULL,
    ProductID VARCHAR(10) NOT NULL,
    Quantity INT NOT NULL,
    Price DOUBLE NOT NULL,
    TotalPrice DOUBLE NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Thêm dữ liệu mẫu cho bảng sales_invoice_detail
INSERT INTO sales_invoice_detail(SalesDetailID, SalesID, ProductID, Quantity, Price, TotalPrice) 
VALUES
('DSI01', 'SI01', 'P01', 1, 2000000, 2000000),
('DSI02', 'SI02', 'P02', 2, 1500000, 3000000);

-- Tạo bảng Import Invoice
CREATE TABLE import_invoice (
    ImportID VARCHAR(10) PRIMARY KEY,
    EmployeeID VARCHAR(10) NOT NULL,
    SupplierID VARCHAR(10) NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO import_invoice(ImportID, EmployeeID, SupplierID) 
VALUES
('I01', 'E01', 'S01'),
('I02', 'E02', 'S02');

-- Tạo bảng Detail of Import Invoice
CREATE TABLE import_invoice_detail (
    ImportDetailID VARCHAR(10) PRIMARY KEY,
    ImportID VARCHAR(10) NOT NULL,
    ProductID VARCHAR(10) NOT NULL,
    Quantity INT NOT NULL,
    Price DOUBLE NOT NULL,
    TotalPrice DOUBLE NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Thêm dữ liệu mẫu cho bảng import_invoice_detail
INSERT INTO import_invoice_detail(ImportDetailID, ImportID, ProductID, Quantity, Price, TotalPrice) 
VALUES
('DI01', 'I01', 'P01', 10, 1500000, 15000000),
('DI02', 'I02', 'P02', 8, 1200000, 9600000);


-- Cấp lại các foreign keys
ALTER TABLE account ADD CONSTRAINT fk_employee FOREIGN KEY (EmployeeID) REFERENCES employee(EmployeeID);
ALTER TABLE account ADD CONSTRAINT fk_rank FOREIGN KEY (RankID) REFERENCES employee_rank(RankID);
ALTER TABLE sales_invoice ADD CONSTRAINT fk_employee_sales FOREIGN KEY (EmployeeID) REFERENCES employee(EmployeeID);
ALTER TABLE sales_invoice ADD CONSTRAINT fk_customer_sales FOREIGN KEY (CustomerID) REFERENCES customer(CustomerID);
ALTER TABLE product ADD CONSTRAINT fk_supplier FOREIGN KEY (SupplierID) REFERENCES supplier(SupplierID);
ALTER TABLE product ADD CONSTRAINT fk_type FOREIGN KEY (TypeID) REFERENCES type_product(TypeID);
ALTER TABLE product_detail ADD CONSTRAINT fk_product_detail FOREIGN KEY (ProductID) REFERENCES product(ProductID);
ALTER TABLE sales_invoice_detail ADD CONSTRAINT fk_sales FOREIGN KEY (SalesID) REFERENCES sales_invoice(SalesID);
ALTER TABLE sales_invoice_detail ADD CONSTRAINT fk_product_sales FOREIGN KEY (ProductID) REFERENCES product(ProductID);
ALTER TABLE import_invoice ADD CONSTRAINT fk_employee_import FOREIGN KEY (EmployeeID) REFERENCES employee(EmployeeID);
ALTER TABLE import_invoice ADD CONSTRAINT fk_supplier_import FOREIGN KEY (SupplierID) REFERENCES supplier(SupplierID);
ALTER TABLE import_invoice_detail ADD CONSTRAINT fk_import FOREIGN KEY (ImportID) REFERENCES import_invoice(ImportID);
ALTER TABLE import_invoice_detail ADD CONSTRAINT fk_product_import FOREIGN KEY (ProductID) REFERENCES product(ProductID);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;


