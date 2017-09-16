var app= require('express')();
var server= app.listen(30);
var io = require('socket.io').listen(server);
/*var users=["MASTER"];
var PWDs=["TEMPLOGIN"]; //TODO: hash function
var MYSQL =require('mysql')

var con = mysql.createConnection({
	host:"localhost",
	user:"app",
	password:"Ma1nApp";
	databse:
})*/


// load DB conneciton
var Mongo=require("mongodb");
var MongoClient=Mongo.MongoClient;

// global variables
var ItemsPendingLoad=[];
var LoadedItems=[];


//CRUD functions (to be used in asynchonous function)
// each function perfoms the opperation and attempr to handle error
function add_item(db,userID,item,data,socket){
	collection=db.collection('items');
	console.log("item: ",item)
	collection.insert(item,function(err,result){
		console.log("item to be inserted: ", item)
		if(err){
			console.log(err);
		} else{
			console.log("successfuly inserted documents into 'item' colleciton")
			console.log(result)
		}
	});
		
}

function update_item(db,userID,itemID,update){
	collection=db.collection('items');
	collection.update({"_id":itemID},{$set:{update}},function(err,result){
		if(err){
			console.log(err);
		} else if (numUpdated){
			console.log("successfuly updated %d documents into 'items' colleciton")
		}else{
			//uh.... Not sure what I was intending here... possibly an auto retry?
		 	//add_item(db,userID,item)  //get item by id?
		}
	});
	
};

function deleteItem(db,userID,itemID,data){
	collection=db.collection('items');
	collection.remove({"_id":Mongo.ObjectId(itemID)},{$set:{update}},function(err,result){
		if(err){
			console.log(err);
		}else{
		 	console.log("record deleted successfuly")
		}

	});
	
};

function getItem(db,userID,itemID){
	var i=1
	var returnval=0
	collection=db.collection('items');
	var cursor;
	console.log("get")
	itemID=itemID.map(function(idString){ //convert from a string to hex ID value
		return(Mongo.ObjectId(idString))
	})
	if (itemID.length){
		console.log("ID restricted")
		cursor=collection.find({"user":userID,"_id":{ $nin: itemID}});
	}else{
		cursor=collection.find({"user":userID});
	}
	cursor.toArray(function(err,docs){
		if(err){
			console.log("error retreiving docs: ",err)
		}else{
		    var intCount = docs.length;
            if (intCount > 0) {
             	//console.log("items retrieved:",docs.length)
            	ItemsPendingLoad=ItemsPendingLoad.concat(docs)
            }else{
            	console.log("no records found")
            }
		}
		
	});
};


//function to enable asynchronous operaiton
function asyncDB(db,userID,callback,items,socket){
	//console.log(items)
	  	socket.emit("sendItems",docs)
	callback(db,userID,items,socket)
	
}
///////////////////////////////////

function LoadItems(userID){

	MongoClient.connect("mongodb://localhost:27017/personal_assistant", function(Merr,db){
		var i=1
		var returnval=0
		collection=db.collection('items');
		var cursor;
		console.log("get")
		itemID=LoadedItems.map(function(item){ //convert from a string to hex ID value
			return(Mongo.ObjectId(item._id))
		})
		if (itemID.length){
			console.log("ID restricted")
			cursor=collection.find({"user":userID,"_id":{ $nin: itemID}});
		}else{
			cursor=collection.find({"user":userID});
		}
		cursor.toArray(function(err,docs){
			if(err){
				console.log("error retreiving docs: ",err)
			}else{
			    var intCount = docs.length;
	            if (intCount > 0) {
	             	//console.log("items retrieved:",docs.length)
	            	ItemsPendingLoad=ItemsPendingLoad.concat(docs)
	            }else{
	            	console.log("no records found")
	            }
			}
			
	});
};
///////////////////////////////////
console.log("running");
setInterval(function() {
		console.log("running")
	
	}, 15000);



app.get('/',function(req,res){
	console.log("http connect");
	res.sendfile(__dirname + '/index.html');
});
io.on("error",function(err){
	console.log("error",err)
})

io.on('connection',function(socket){
		console.log("connection recieved");
		// con.connect(function(err){
		// 	if(err){
		// 		console.log('Error connecting to DB');
		// 		return
		// 	}
		// 	consol.log("DB conneciton established");
		// })
				if(Merr){
				console.log(Merr)
			}else{
				socket.emit("connect");
				socket.on("error",function(err){
					console.log("error",err)
				})
				socket.on("confirmConn",function() {
					console.log("connection confirmed")		})
				socket.on('disconnect', function(){
					console.log("connection closed");
				});
				socket.on("login",function(login_info){
					console.log("login info recieved")
					socket.emit("login_accept");
				});
				socket.on("deleteItem",function(item){
						if(err){
							console.log("error: ",err)
						}else{
							asyncDB(db,00,deleteItem,item,socket)

							
						}
					});
				socket.on("add_tag", function(){
					console.log("taggin")
					//look for tag by name
					// add tag id item id relationship
				});
				socket.on("addItem", function(items){
						if(err){
							throw err;

						}else{
							console.log("database connnected");
							console.log("adding items:",items)
							if(asyncDB(db,00,add_item,items)){
								socket.emit("ADD_success")
							}
						}

					});
					//look for tag by name
					// add tag id item id relationship
					

				socket.on("getItem", function(items){
							
						console.log("getting Items")
						socket.emit("sendItems",ItemsPendingLoad)
					});
				}
});


//TO DO: handle login