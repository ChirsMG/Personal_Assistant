#  insert examples

#################################################
#		Variables for Insert					#
#################################################
#  DESCRIPTION TEXT: (string)					#
# 	the descriptionf variable for an 			#
# ITEM_TITLE: 	(string) 						#
#	variabel holding the item title 			#
# USER: (Obj)	 								#
#		hold user info							#
#		.ID (int): Users table id value			#
#		.name (string) : username				#	
#		.pwd (string) : password				#
#												#
# TAG_NAME: (string)							#
#	name value of a TAG_NAME					#
# DUEDATE: (DATE)								#
#	time expiration for a tasks 				#
#################################################

# 0) iniial user verification done before any other queries

SELECT ID FROM Users WHERE Users.name=USER.NAME AND Users.pwd=USER.PWD; # store in USER.ID


# 1) insert basic text item

INSERT INTO Data(type,data) VALUES ("text", DESCRIPTION_TEXT);

INSERT INTO Nodes( Title,Data_ID,User_ID) VALUES (ITEM_TITLE,LAST_INSERT_ID(), USER_ID);


#2) insert text based list

INSERT INTO Data(type,data) VALUES ("List", NULL);

INSERT INTO Nodes( Title,Data_ID,User_ID) VALUES (ITEM_TITLE,LAST_INSERT_ID(), USER_ID);
 
INSERT INTO Lists( Node_ID) VALUES(LAST_INSERT_ID());
	
	# for each item in list (assume LISTID stores LAST_INSERT_ID() )

	INSERT INTO Lists( ListID, value) VALUES(LISTID, ITEM_VALUE);

#3) insert text based task

INSERT INTO Data(type,data) VALUES ("text", DESCRIPTION_TEXT);

INSERT INTO Nodes( Title,Data_ID,User_ID) VALUES (ITEM_TITLE,LAST_INSERT_ID(), USER_ID);

INSERT INTO Tasks (Node_ID,Expiration) VALUES (LAST_INSERT_ID(),DUEDATE);

#4) insert list of items

INSERT INTO Data(type,data) VALUES ("List", NULL);

INSERT INTO Nodes( Title,Data_ID,User_ID) VALUES (ITEM_TITLE,LAST_INSERT_ID(), USER_ID);
 
INSERT INTO Lists( Node_ID) VALUES(LAST_INSERT_ID());
	
	# for each item in list (assume LISTID stores LAST_INSERT_ID() )

	INSERT INTO Data(type,data) VALUES ("Text", LIST_ITEM_VALUE);

	INSERT INTO Nodes( Title,Data_ID,User_ID) VALUES (LIST_ITEM_TITLE,LAST_INSERT_ID(), USER_ID);

	INSERT INTO ListObj( ListID, Node_ID) VALUES(LISTID,LAST_INSERT_ID());

#5) create tag
	INSERT INTO Tags (Name) VALUES(TAG_NAME);

#6) tag item

	INSERT INTO Tagging (Tag_ID,Node_ID) VALUES(SELECT ID FROM Tags WHERE Tags.Name=TAG_NAME, NODEID);