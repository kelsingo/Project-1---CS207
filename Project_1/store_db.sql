CREATE TABLE Products (
    productID INTEGER PRIMARY KEY,
    productName VARCHAR(255),
    price DECIMAL(10,2),
    quantity INT
);

CREATE TABLE Orders (
    orderID INTEGER PRIMARY KEY,
    buyerID INT,
    totalCost DECIMAL(10,2),
    totalTax DECIMAL(10,2),
    orderDate DATE,
    paymentStatus TEXT NOT NULL DEFAULT 'NOT PAID' CHECK(paymentStatus IN ('NOT PAID', 'PAID')),
    FOREIGN KEY (buyerID) REFERENCES Buyers(buyerID)
);

CREATE TABLE OrderDetails (
    orderID INT,
    productID INT,
    productQuantity INT,
    cost DECIMAL(10,2),
    PRIMARY KEY (orderID, productID),
    FOREIGN KEY (orderID) REFERENCES Orders(orderID),
    FOREIGN KEY (productID) REFERENCES Products(productID)
);

CREATE TABLE Buyers (
    buyerID INTEGER PRIMARY KEY,
    buyerName VARCHAR(255),
    phoneNumber VARCHAR(20),
    email VARCHAR(255),
    address VARCHAR(255)
); 

CREATE TABLE Accounts (
    username VARCHAR(255) PRIMARY KEY,
    password VARCHAR(255),
    buyerID INT, 
    accountType TEXT NOT NULL DEFAULT 'BUYER' CHECK(accountType IN ('STORE_MANAGER', 'BUYER')), 
    FOREIGN KEY (buyerID) REFERENCES Buyers(buyerID)
);


INSERT INTO Buyers (buyerName, phoneNumber, email, address) VALUES
("John Johnson", "1234123412", "john@gmail.com", "105 Ton Dat Tien, Tan Phu Ward, District 7, HCMC"),
("Teddy Bear", "1234123412", "ted@gmail.com", "106 Ton Dat Tien, Tan Phu Ward, District 7, HCMC"),
("Thanh Thanh", "1234123412", "thanh@gmail.com", "107 Ton Dat Tien, Tan Phu Ward, District 7, HCMC"),
("Linh Mai", "1234123412", "linh@gmail.com", "108 Ton Dat Tien, Tan Phu Ward, District 7, HCMC"),
("Katy Kate", "1212343412", "kate@gmail.com", "109 Ton Dat Tien, Tan Phu Ward, District 7, HCMC");

INSERT INTO Accounts (username, password, buyerID, accountType) VALUES 
("john", "john", 1, "BUYER"),
("ted", "ted", 2, "BUYER"),
("kate", "kate", 2, "BUYER"),
("admin", "pass", NULL, "STORE_MANAGER"); 

INSERT INTO Products (productName, price, quantity) VALUES 
("Milk", 3, 25),
("Rice", 5, 30),
("Cafe", 10, 50),
("Bottled water", 5, 50),
("Sugar", 2, 15); 


