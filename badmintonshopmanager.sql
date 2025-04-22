-- phpMyAdmin SQL Dump
<<<<<<< HEAD
-- version 5.1.3
-- https://www.phpmyadmin.net/
--
=======
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th4 16, 2025 lúc 05:57 AM
-- Phiên bản máy phục vụ: 10.4.32-MariaDB
-- Phiên bản PHP: 8.2.12
>>>>>>> 13b18628894d89da73a7f6a0ded739b9f993b363

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

<<<<<<< HEAD
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
=======
--
-- Cơ sở dữ liệu: `badmintonshopmanager`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `account`
--

CREATE TABLE `account` (
  `Username` varchar(50) NOT NULL,
  `Password` varchar(50) NOT NULL,
  `EmployeeID` varchar(10) NOT NULL,
  `RankID` varchar(10) NOT NULL,
  `IsDeleted` tinyint(10) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `account`
--

INSERT INTO `account` (`Username`, `Password`, `EmployeeID`, `RankID`, `IsDeleted`) VALUES
('admin', 'admin123', 'E01', 'R01', 0),
('staff1', 'staff123', 'E02', 'R02', 0);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `action`
--

CREATE TABLE `action` (
  `ActionID` varchar(10) NOT NULL,
  `ActionName` varchar(50) DEFAULT NULL,
  `ActionNameVN` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `action`
--

INSERT INTO `action` (`ActionID`, `ActionName`, `ActionNameVN`) VALUES
('AC1', 'Watch', 'Xem'),
('AC2', 'Add', 'Thêm'),
('AC3', 'Edit', 'Sửa'),
('AC4', 'Delete', 'Xoá');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `customer`
--

CREATE TABLE `customer` (
  `CustomerID` varchar(10) NOT NULL,
  `FullName` varchar(50) NOT NULL,
  `Phone` varchar(15) NOT NULL,
  `TotalSpending` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `customer`
--

INSERT INTO `customer` (`CustomerID`, `FullName`, `Phone`, `TotalSpending`) VALUES
('C01', 'Le Van C', '0912345678', 500000),
('C02', 'Pham Thi D', '0934567890', 1200000);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `employee`
--

CREATE TABLE `employee` (
  `EmployeeID` varchar(10) NOT NULL,
  `FullName` varchar(50) NOT NULL,
  `Age` int(11) NOT NULL,
  `Phone` varchar(15) NOT NULL,
  `Email` varchar(50) NOT NULL,
  `Address` varchar(255) NOT NULL,
  `Gender` enum('Nam','Nữ') NOT NULL,
  `Salary` double NOT NULL,
  `StartDate` date NOT NULL,
  `IsDeleted` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `employee`
--

INSERT INTO `employee` (`EmployeeID`, `FullName`, `Age`, `Phone`, `Email`, `Address`, `Gender`, `Salary`, `StartDate`, `IsDeleted`) VALUES
('E01', 'Nguyen Van A', 28, '0123456789', 'a@gmail.com', 'Hanoi', 'Nam', 7000000, '2024-03-14', 0),
('E02', 'Tran Thi B', 25, '0987654321', 'b@gmail.com', 'Ho Chi Minh', 'Nữ', 8000000, '2024-03-14', 0);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `employee_rank`
--

CREATE TABLE `employee_rank` (
  `RankID` varchar(10) NOT NULL,
  `RankName` varchar(50) NOT NULL,
  `BaseSalary` double NOT NULL,
  `RankNameUnsigned` varchar(255) DEFAULT NULL,
  `IsDeleted` tinyint(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `employee_rank`
--

INSERT INTO `employee_rank` (`RankID`, `RankName`, `BaseSalary`, `RankNameUnsigned`, `IsDeleted`) VALUES
('R01', 'Admin', 5000000, 'Admin', 0),
('R02', 'Quản lý', 10000000, 'Quan ly', 0);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `function`
--

CREATE TABLE `function` (
  `FunctionID` varchar(10) NOT NULL,
  `FunctionName` varchar(50) NOT NULL,
  `FunctionNameUnsigned` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `function`
--

INSERT INTO `function` (`FunctionID`, `FunctionName`, `FunctionNameUnsigned`) VALUES
('F01', 'Quản lý nhân viên', 'Quan ly nhan vien'),
('F02', 'Quản lý tài khoản', 'Quan ly tai khoan'),
('F03', 'Quản lý phân quyền', 'Quan ly phan quyen');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `function_detail`
--

CREATE TABLE `function_detail` (
  `RankID` varchar(10) DEFAULT NULL,
  `FunctionID` varchar(10) DEFAULT NULL,
  `ActionID` varchar(10) DEFAULT NULL,
  `IsDeleted` tinyint(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `function_detail`
--

INSERT INTO `function_detail` (`RankID`, `FunctionID`, `ActionID`, `IsDeleted`) VALUES
('R01', 'F01', 'AC2', 0),
('R01', 'F01', 'AC3', 0),
('R01', 'F01', 'AC4', 0),
('R01', 'F02', 'AC2', 0),
('R01', 'F02', 'AC3', 0),
('R01', 'F02', 'AC4', 0),
('R01', 'F03', 'AC2', 0),
('R01', 'F03', 'AC3', 0),
('R01', 'F03', 'AC4', 0),
('R02', 'F01', 'AC2', 0),
('R02', 'F01', 'AC3', 0),
('R02', 'F01', 'AC4', 0),
('R02', 'F02', 'AC2', 0),
('R02', 'F02', 'AC3', 0),
('R02', 'F02', 'AC4', 0),
('R02', 'F03', 'AC2', 0),
('R02', 'F03', 'AC3', 0),
('R02', 'F03', 'AC4', 0),
('R01', 'F01', 'AC1', 0);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `import_invoice`
--

CREATE TABLE `import_invoice` (
  `ImportID` varchar(10) NOT NULL,
  `EmployeeID` varchar(10) NOT NULL,
  `SupplierID` varchar(10) NOT NULL,
  `Date` date NOT NULL,
  `TotalPrice` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `import_invoice`
--

INSERT INTO `import_invoice` (`ImportID`, `EmployeeID`, `SupplierID`, `Date`, `TotalPrice`) VALUES
('I01', 'E01', 'S01', '2025-03-14', 15000000),
('I02', 'E02', 'S02', '2025-03-14', 9600000);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `import_invoice_detail`
--

CREATE TABLE `import_invoice_detail` (
  `ImportDetailID` varchar(10) NOT NULL,
  `ImportID` varchar(10) NOT NULL,
  `ProductID` varchar(10) NOT NULL,
  `Quantity` int(11) NOT NULL,
  `Price` double NOT NULL,
  `TotalPrice` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `import_invoice_detail`
--

INSERT INTO `import_invoice_detail` (`ImportDetailID`, `ImportID`, `ProductID`, `Quantity`, `Price`, `TotalPrice`) VALUES
('DI01', 'I01', 'P01', 10, 1500000, 15000000),
('DI02', 'I02', 'P02', 8, 1200000, 9600000);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `product`
--

CREATE TABLE `product` (
  `ProductID` varchar(10) NOT NULL,
  `ProductName` varchar(50) DEFAULT NULL,
  `ProductImg` varchar(255) DEFAULT NULL,
  `Quantity` int(11) NOT NULL,
  `SupplierID` varchar(10) NOT NULL,
  `TypeID` varchar(10) NOT NULL,
  `Price` double NOT NULL,
  `IsDeleted` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `product`
--

INSERT INTO `product` (`ProductID`, `ProductName`, `ProductImg`, `Quantity`, `SupplierID`, `TypeID`, `Price`, `IsDeleted`) VALUES
('P01', 'Vợt Yonex Astrox', 'astrox.jpg', 5, 'S01', 'T01', 4000000, '0'),
('P02', 'Giày Lining Turbo', 'lining_turbo.jpg', 3, 'S02', 'T02', 1620000, '0');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `product_detail`
--

CREATE TABLE `product_detail` (
  `Series` bigint(20) NOT NULL,
  `ProductID` varchar(10) NOT NULL,
  `ImportDate` date NOT NULL,
  `Status` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `product_detail`
--

INSERT INTO `product_detail` (`Series`, `ProductID`, `ImportDate`, `Status`) VALUES
(1, 'P01', '0000-00-00', 'Hiện'),
(2, 'P01', '0000-00-00', 'Hiện'),
(3, 'P01', '0000-00-00', 'Hiện'),
(4, 'P01', '0000-00-00', 'Hiện'),
(5, 'P01', '0000-00-00', 'Hiện'),
(6, 'P02', '0000-00-00', 'Hiện'),
(7, 'P02', '0000-00-00', 'Hiện'),
(8, 'P02', '0000-00-00', 'Hiện');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `sales_invoice`
--

CREATE TABLE `sales_invoice` (
  `SalesID` varchar(10) NOT NULL,
  `EmployeeID` varchar(10) NOT NULL,
  `CustomerID` varchar(10) NOT NULL,
  `Date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `sales_invoice`
--

INSERT INTO `sales_invoice` (`SalesID`, `EmployeeID`, `CustomerID`, `Date`) VALUES
('SI01', 'E01', 'C01', '2025-03-14'),
('SI02', 'E02', 'C02', '2025-03-14');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `sales_invoice_detail`
--

CREATE TABLE `sales_invoice_detail` (
  `SalesDetailID` varchar(10) NOT NULL,
  `SalesID` varchar(10) NOT NULL,
  `ProductID` varchar(10) NOT NULL,
  `Quantity` int(11) NOT NULL,
  `Price` double NOT NULL,
  `TotalPrice` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `sales_invoice_detail`
--

INSERT INTO `sales_invoice_detail` (`SalesDetailID`, `SalesID`, `ProductID`, `Quantity`, `Price`, `TotalPrice`) VALUES
('SID01', 'SI01', 'P01', 1, 2000000, 2000000),
('SID02', 'SI02', 'P02', 2, 1500000, 3000000);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `supplier`
--

CREATE TABLE `supplier` (
  `SupplierID` varchar(10) NOT NULL,
  `SupplierName` varchar(50) NOT NULL,
  `Phone` varchar(15) NOT NULL,
  `Email` varchar(255) DEFAULT NULL,
  `Address` varchar(255) DEFAULT NULL,
  `IsDeleted` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `supplier`
--

INSERT INTO `supplier` (`SupplierID`, `SupplierName`, `Phone`, `Email`, `Address`, `IsDeleted`) VALUES
('S01', 'Yonex', '0988123456', 'contact@yonex.com', 'Nhật Bản', '0'),
('S02', 'Lining', '0977123456', 'support@lining.com', 'Trung Quốc', '0'),
('S03', 'Victor', '0987654321', 'victor@gmail.com', 'Trung Quốc', '1'),
('S04', 'VNB', '0999999999', 'vnb@gmail.com', 'Việt Nam', '1');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `type_product`
--

CREATE TABLE `type_product` (
  `TypeID` varchar(10) NOT NULL,
  `TypeName` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `type_product`
--

INSERT INTO `type_product` (`TypeID`, `TypeName`) VALUES
('T01', 'Vợt cầu lông'),
('T02', 'Giày cầu lông');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `account`
--
ALTER TABLE `account`
  ADD PRIMARY KEY (`Username`),
  ADD KEY `fk_employee` (`EmployeeID`),
  ADD KEY `fk_rank` (`RankID`);

--
-- Chỉ mục cho bảng `action`
--
ALTER TABLE `action`
  ADD PRIMARY KEY (`ActionID`);

--
-- Chỉ mục cho bảng `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`CustomerID`);

--
-- Chỉ mục cho bảng `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`EmployeeID`);

--
-- Chỉ mục cho bảng `employee_rank`
--
ALTER TABLE `employee_rank`
  ADD PRIMARY KEY (`RankID`);

--
-- Chỉ mục cho bảng `function`
--
ALTER TABLE `function`
  ADD PRIMARY KEY (`FunctionID`);

--
-- Chỉ mục cho bảng `function_detail`
--
ALTER TABLE `function_detail`
  ADD KEY `RankID` (`RankID`),
  ADD KEY `FunctionID` (`FunctionID`);

--
-- Chỉ mục cho bảng `import_invoice`
--
ALTER TABLE `import_invoice`
  ADD PRIMARY KEY (`ImportID`),
  ADD KEY `fk_employee_import` (`EmployeeID`),
  ADD KEY `fk_supplier_import` (`SupplierID`);

--
-- Chỉ mục cho bảng `import_invoice_detail`
--
ALTER TABLE `import_invoice_detail`
  ADD PRIMARY KEY (`ImportDetailID`),
  ADD KEY `fk_import` (`ImportID`),
  ADD KEY `fk_product_import` (`ProductID`);

--
-- Chỉ mục cho bảng `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`ProductID`),
  ADD KEY `fk_supplier` (`SupplierID`),
  ADD KEY `fk_type` (`TypeID`);

--
-- Chỉ mục cho bảng `product_detail`
--
ALTER TABLE `product_detail`
  ADD PRIMARY KEY (`Series`),
  ADD KEY `fk_product_detail` (`ProductID`);

--
-- Chỉ mục cho bảng `sales_invoice`
--
ALTER TABLE `sales_invoice`
  ADD PRIMARY KEY (`SalesID`),
  ADD KEY `fk_employee_sales` (`EmployeeID`),
  ADD KEY `fk_customer_sales` (`CustomerID`);

--
-- Chỉ mục cho bảng `sales_invoice_detail`
--
ALTER TABLE `sales_invoice_detail`
  ADD PRIMARY KEY (`SalesDetailID`),
  ADD KEY `fk_sales` (`SalesID`),
  ADD KEY `fk_product_sales` (`ProductID`);

--
-- Chỉ mục cho bảng `supplier`
--
ALTER TABLE `supplier`
  ADD PRIMARY KEY (`SupplierID`);

--
-- Chỉ mục cho bảng `type_product`
--
ALTER TABLE `type_product`
  ADD PRIMARY KEY (`TypeID`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `product_detail`
--
ALTER TABLE `product_detail`
  MODIFY `Series` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `account`
--
ALTER TABLE `account`
  ADD CONSTRAINT `fk_employee` FOREIGN KEY (`EmployeeID`) REFERENCES `employee` (`EmployeeID`),
  ADD CONSTRAINT `fk_rank` FOREIGN KEY (`RankID`) REFERENCES `employee_rank` (`RankID`);

--
-- Các ràng buộc cho bảng `function_detail`
--
ALTER TABLE `function_detail`
  ADD CONSTRAINT `function_detail_ibfk_1` FOREIGN KEY (`RankID`) REFERENCES `employee_rank` (`RankID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `function_detail_ibfk_2` FOREIGN KEY (`FunctionID`) REFERENCES `function` (`FunctionID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `import_invoice`
--
ALTER TABLE `import_invoice`
  ADD CONSTRAINT `fk_employee_import` FOREIGN KEY (`EmployeeID`) REFERENCES `employee` (`EmployeeID`),
  ADD CONSTRAINT `fk_supplier_import` FOREIGN KEY (`SupplierID`) REFERENCES `supplier` (`SupplierID`);

--
-- Các ràng buộc cho bảng `import_invoice_detail`
--
ALTER TABLE `import_invoice_detail`
  ADD CONSTRAINT `fk_import` FOREIGN KEY (`ImportID`) REFERENCES `import_invoice` (`ImportID`),
  ADD CONSTRAINT `fk_product_import` FOREIGN KEY (`ProductID`) REFERENCES `product` (`ProductID`);

--
-- Các ràng buộc cho bảng `product`
--
ALTER TABLE `product`
  ADD CONSTRAINT `fk_supplier` FOREIGN KEY (`SupplierID`) REFERENCES `supplier` (`SupplierID`),
  ADD CONSTRAINT `fk_type` FOREIGN KEY (`TypeID`) REFERENCES `type_product` (`TypeID`);

--
-- Các ràng buộc cho bảng `product_detail`
--
ALTER TABLE `product_detail`
  ADD CONSTRAINT `fk_product_detail` FOREIGN KEY (`ProductID`) REFERENCES `product` (`ProductID`);

--
-- Các ràng buộc cho bảng `sales_invoice`
--
ALTER TABLE `sales_invoice`
  ADD CONSTRAINT `fk_customer_sales` FOREIGN KEY (`CustomerID`) REFERENCES `customer` (`CustomerID`),
  ADD CONSTRAINT `fk_employee_sales` FOREIGN KEY (`EmployeeID`) REFERENCES `employee` (`EmployeeID`);

--
-- Các ràng buộc cho bảng `sales_invoice_detail`
--
ALTER TABLE `sales_invoice_detail`
  ADD CONSTRAINT `fk_product_sales` FOREIGN KEY (`ProductID`) REFERENCES `product` (`ProductID`),
  ADD CONSTRAINT `fk_sales` FOREIGN KEY (`SalesID`) REFERENCES `sales_invoice` (`SalesID`);
COMMIT;
>>>>>>> 13b18628894d89da73a7f6a0ded739b9f993b363

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
<<<<<<< HEAD


=======
>>>>>>> 13b18628894d89da73a7f6a0ded739b9f993b363
