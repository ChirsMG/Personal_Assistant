CREATE DATABASE IF NOT EXISTS PersonalAssistant;


CREATE TABLE IF NOT EXISTS Category(
	ID int NOT NULL AUTO_INCREMENT,
	name varchar(256),
	PRIMARY KEY (ID)
);

CREATE TABLE IF NOT EXISTS Users(
	ID int NOT NULL AUTO_INCREMENT,
	Name varchar(256),
	pwd VARCHAR(256),
	PRIMARY KEY (ID)
);


CREATE TABLE IF NOT EXISTS Tags(
	ID int NOT NULL AUTO_INCREMENT,
	name VARCHAR(256),
	PRIMARY KEY (ID)
);


CREATE TABLE IF NOT EXISTS ListItem(
	ID int NOT NULL AUTO_INCREMENT,
	ListID INT NOT NULL,
	value TEXT,
	PRIMARY KEY (ID)
	FOREIGN KEY (ListID) REFERENCES Lists(ID)
);

CREATE TABLE IF NOT EXISTS Lists(
	ID INT NOT NULL AUTO_INCREMENT,
	Node_ID INT NOT NULL,
	PRIMARY KEY(ID),
	FOREIGN KEY(Node_ID) REFENCES Nodes(ID)
);

CREATE TABLE IF NOT EXISTS ListObj(
	List_ID int NOT NULL AUTO_INCREMENT,
	Node_ID INT NOT NULL,
	PRIMARY KEY (List_ID,Node_ID)
	FOREIGN KEY (Node_ID) REFERENCES Nodes(ID),
	FOREIGN KEY (List_ID) REFERENCES Lists(ID)
);

CREATE TABLE IF NOT EXISTS Data(
	ID int NOT NULL AUTO_INCREMENT,
	type varchar(32) NOT NULL,
	data TEXT,
	PRIMARY KEY (ID)
);

CREATE TABLE IF NOT EXISTS Nodes(
	ID int NOT NULL AUTO_INCREMENT,
	Title varchar(255),
	Data_ID int,
	Catagory_ID int,
	User_ID int,
	PRIMARY KEY (ID),
	FOREIGN KEY (Data_ID) references Data(ID),
	FOREIGN KEY (User_ID) references Users(ID),
	FOREIGN KEY (Catagory_ID) references Category(ID)
);

CREATE TABLE IF NOT EXISTS Tagging(
	Tag_ID INT,
	Node_ID INT,
	PRIMARY KEY (Tag_ID, Node_ID),
	FOREIGN KEY (Tag_ID) REFERENCES Tags(ID),
	FOREIGN KEY (Node_ID) REFERENCES Nodes(ID)

);


CREATE TABLE IF NOT EXISTS Tasks(
	ID INT,
	Node_ID INT NOT NULL,
	Expiration DATETIME,
	PRIMARY KEY (ID)
	FOREIGN KEY (Node_ID) REFERENCES Nodes(ID)

);

CREATE TABLE IF NOT EXISTS Depenecencies(
	Child_ID INT,
	Parent_ID INT,
	PRIMARY KEY (Child_ID, Parent_ID),
	FOREIGN KEY (Child_ID) REFERENCES Task(ID),
	FOREIGN KEY (Parent_ID) REFERENCES Task(ID)

);


