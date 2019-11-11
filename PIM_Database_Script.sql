DROP DATABASE IF EXISTS PIM_Database;
    CREATE DATABASE PIM_Database;
    USE PIM_Database;
    
    CREATE TABLE Categories(
	Category_ID int unique not null,
	Catagory_Name varchar(255) not null,
	Catagory_Description varchar(2550) not null,
	primary key(Category_ID)
	);
    
	CREATE TABLE Attributes(
    Attribute_ID int unique not null,
	Attribute_Name varchar(255) not null,
    Attribute_Description varchar(2550) not null,
	primary key(Attribute_ID)
	);
    
    CREATE TABLE Category_Attributes(
    Category_ID int not null,
    Attribute_ID int not null,
    foreign key(Category_ID) references Categories(Category_ID),
    foreign key(Attribute_ID) references Attributes(Attribute_ID),
    primary key(Category_ID, Attribute_ID)
    );
    
    CREATE TABLE Product(
    Product_ID int unique not null auto_increment,
	Product_Name varchar(255) not null,
    Product_Description varchar(2550) not null,
    picturePath varchar(100),
    primary key(Product_ID)
    );
    
    CREATE TABLE Distributor(
    Distributor_ID int unique not null,
    Distributor_Name varchar(255) not null,
    Distributor_Description varchar(2550) not null,
    primary key(Distributor_ID)
    );
    
    CREATE TABLE Product_Distributor(
    Product_ID int unique not null,
    Distributor_ID int unique not null,
    Product_Distributor_Name varchar(255) not null,
    foreign key(Product_ID) references Product(Product_ID),
    foreign key(Distributor_ID) references Distributor(Distributor_ID),
    primary key(Product_ID, Distributor_ID)
    );
    
    CREATE TABLE Product_Categories(
    Product_ID int not null,
    Category_ID int not null,
    foreign key(Product_ID) references Product(Product_ID),
    foreign key(Category_ID) references Categories(Category_ID),
    primary key(Product_ID, Category_ID)
    );
    
    CREATE TABLE Product_Attributes(
    Product_ID int not null,
    Attribute_ID int not null,
    Attribute_Name varchar (255) not null,
    Attribute_Info varchar (2550) not null,
    foreign key(Product_ID) references Product(Product_ID),
    foreign key(Attribute_ID) references Attributes(Attribute_ID),
    primary key(Product_ID, Attribute_ID)
    );