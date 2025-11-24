-- Adding foreign keys to homeproducts database

-- Product foreign key 
ALTER TABLE tblProduct
ADD CONSTRAINT FK_Product_Warehouse
FOREIGN KEY (WarehouseID) REFERENCES tblWarehouse(WarehouseID);

-- Sales rep foreign key
ALTER TABLE tblSalesRep
ADD CONSTRAINT FK_SalesRep_Manager
FOREIGN KEY (ManagerID) REFERENCES tblSalesRep(SalesRepID);

-- Customer foreign key
ALTER TABLE tblCustomer
ADD CONSTRAINT FK_Customer_SalesRep
FOREIGN KEY (SalesRepID) REFERENCES tblSalesRep(SalesRepID);

-- Order foreign key
ALTER TABLE tblOrder
ADD CONSTRAINT FK_Order_Customer
FOREIGN KEY (CustomerID) REFERENCES tblCustomer(CustomerID);

-- Payment foreign keys
ALTER TABLE tblPayment
ADD CONSTRAINT FK_Payment_Customer
FOREIGN KEY (CustomerID) REFERENCES tblCustomer(CustomerID);
ALTER TABLE tblPayment
ADD CONSTRAINT FK_Payment_Order
FOREIGN KEY (OrderID) REFERENCES tblOrder(OrderID);

-- OrderProduct foreign keys
ALTER TABLE tblProductsOrder
ADD CONSTRAINT FK_OrdersProduct_Order
FOREIGN KEY (OrderID) REFERENCES tblOrder(OrderID);
ALTER TABLE tblProductsOrder
ADD CONSTRAINT FK_OrdersProduct_Product
FOREIGN KEY (ProductID) REFERENCES tblProduct(ProductID);
