Use HomeProductsIncSmall;

-- Creating warehouse table
CREATE TABLE `tblWarehouse` (
  `WarehouseID` INT NOT NULL AUTO_INCREMENT COMMENT 'Autonumber primary key',
  `Address1` varchar(100)  NOT NULL COMMENT 'Primary street address',
  `Address2` varchar(100),
  `City` varchar(50)  NOT NULL,
  `State` varchar(2)  NOT NULL,
  `ZipCode` varchar(10) NOT NULL,
  `GeneralNumber` varchar(15) COMMENT 'main telephone number to warehouse',
  `SupervisorName` varchar(100) COMMENT 'Optional supervisor of the warehouse',
  `SupervisorNumber` varchar(15) COMMENT 'Transitive dependency with the supervisor',
  `Capacity` INT NOT NULL COMMENT 'Maximum capacity',
  `Employees` SmallInt COMMENT 'How many employees working',
  PRIMARY KEY (`WarehouseID`) COMMENT 'We set the autonumber to the primary key'
);

-- Creating product table
CREATE TABLE `tblProduct` (
  `ProductID` varchar(4) COMMENT 'primary key',
  `Description` varchar(100)  NOT NULL,
  `UnitPrice` Decimal(10,2) COMMENT 'Optional price per unit',
  `UnitsOnHand` SmallInt  NOT NULL COMMENT 'How many products in stock',
  `Class` varchar(2)  NOT NULL,
  `WarehouseID` int NOT NULL COMMENT 'Where the products are kept',
  PRIMARY KEY (`ProductID`) COMMENT 'We set the autonumber to the primary key'
  -- FOREIGN KEY (`WarehouseNumber`) REFERENCES `tblWarehouse`(`WarehouseNumber`)
);

-- Creating sales reps table
CREATE TABLE `tblSalesRep` (
  `SalesRepID` INT NOT NULL AUTO_INCREMENT COMMENT 'Autonumber primary key',
  `FirstName` varchar(50) NOT NULL,
  `LastName` varchar(50) NOT NULL,
  `Title` varchar(20) NOT NULL,
  `BusinessNumber` varchar(10),
  `HomeNumber` varchar(10),
  `CellNumber` varchar(10) NOT NULL,
  `FaxNumber` varchar(10) NOT NULL,
  `Street` varchar(50) NOT NULL,
  `City` varchar(50) NOT NULL,
  `State` varchar(2)  NOT NULL,
  `ZipCode` varchar(10)  NOT NULL,
  `Commission` Decimal(10,2)  NOT NULL COMMENT 'Total comission of the sales rep',
  `ManagerID` int NOT NULL COMMENT 'ID of sales rep manager',
  PRIMARY KEY (`SalesRepID`) COMMENT 'We set the autonumber to the primary key'
  -- FOREIGN KEY (`ManagerID`) REFERENCES `tblSalesRepresentative`(`SalesRepID`)
);

-- Creating customer table
CREATE TABLE `tblCustomer` (
  `CustomerID` INT NOT NULL AUTO_INCREMENT COMMENT 'Autonumber primary key',
  `FirstName` varchar(50) NOT NULL,
  `LastName` varchar(50) NOT NULL,
  `Street` varchar(200) NOT NULL,
  `City` varchar(100) NOT NULL,
  `State` varchar(2) NOT NULL,
  `ZipCode` varchar(10)  NOT NULL,
  `Credit` Decimal(10,2) NOT NULL COMMENT 'Credit amount of the customer',
  `Company` varchar(150),
  `Website` varchar(150) NOT NULL,
  `Email` varchar(150) NOT NULL,
  `BusinessNumber` varchar(10),
  `CellNumber` varchar(10),
  `Title` varchar(50),
  `Status` varchar(20) NOT NULL COMMENT 'Whether the customer is actively buying or not',
  `Notes` varchar(1000) COMMENT 'Informal notes on the costumer',
  `SalesRepID` int NOT NULL COMMENT 'ID of the sales rep',
  PRIMARY KEY (`CustomerID`) COMMENT 'We set the autonumber to the primary key'
  -- FOREIGN KEY (`SalesRepID`) REFERENCES `tblSalesRepresentative`(`SalesRepID`)
);

-- Creating order table
CREATE TABLE `tblOrder` (
  `OrderID` SmallInt NOT NULL COMMENT 'Primary key',
  `Date` Date NOT NULL,
  `ShippingDate` Date,
  `Status` varchar(15)  COMMENT 'Status of the order payment',
  `ShippingMethod` varchar(30)  NOT NULL COMMENT 'How the order was shipped',
  `SalesTax` Decimal(10,2)  COMMENT 'Tax on the order depending on state',
  `CustomerID` int NOT NULL COMMENT 'ID of orderer',
  PRIMARY KEY (`OrderID`) COMMENT 'We set the OrderID to the primary key'
  -- FOREIGN KEY (`CustomerID`) REFERENCES `tblCustomer`(`CustomerID`)
);

-- Creating payment table
CREATE TABLE `tblPayment` (
  `PaymentID` INT NOT NULL AUTO_INCREMENT COMMENT 'Autonumber primary key',
  `CardExpirationDate` Date,
  `CardNumber` varchar(20),
  `Method` varchar(10) NOT NULL COMMENT 'Payment method',
  `Amount` Decimal(10,2) NOT NULL,
  `CreditCard` Boolean  COMMENT 'If credit card was used or not',
  `CardOwner` varchar(50),
  `Date` Date  NOT NULL COMMENT 'Date the payment was made',
  `CustomerID` int NOT NULL COMMENT 'ID of payer',
  `OrderID` SmallInt NOT NULL COMMENT 'ID of order',
  PRIMARY KEY (`PaymentID`) COMMENT 'We set the autonumber to the primary key'
  -- FOREIGN KEY (`CustomerID`) REFERENCES `tblCustomer`(`CustomerID`),
  -- FOREIGN KEY (`OrderID`) REFERENCES `tblOrder`(`OrderID`)
);

-- Creating order's products table
CREATE TABLE `tblProductsOrder` (
  `OrderID` SmallInt NOT NULL COMMENT 'ID of the order (both primary and foreign key)',
  `ProductID` varchar(4) NOT NULL COMMENT 'ID of the product (both primary and foreign key)',
  `QuantityOrdered` INT NOT NULL,
  `QuotedPrice` Decimal(10,2),
  PRIMARY KEY (`OrderID`, `ProductID`) COMMENT 'composite primary key'
  -- FOREIGN KEY (`OrderID`) REFERENCES `tblOrder`(`OrderID`),
  -- FOREIGN KEY (`ProductID`) REFERENCES `tblProduct`(`ProductID`)
);
