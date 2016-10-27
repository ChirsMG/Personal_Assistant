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
var MongoClient=Mongo.Client;



//CRUD functions (to be used in asynchonous function)
// each function perfoms the opperation and attempr to handle error
function add_item(db,userID,item){
	collection=db.collection('items');
	collection.insert(item,function(err,result){
		if(err){
			console.log(err);
			socket.emit("error",{err})
		} else{
			console.log("successfuly inserted %d documents into 'item' colleciton")
			socket.emit("ADD_successful")
		}
	});	
}

function update_item(db,userID,itemID,update){
	collection=db.collection('items');
	collection.update({"_id":itemID},{$set:{update}},function(err,result){
		if(err){
			console.log(err);
		} else if (numUpdated){
			console.log("successfuly inserted %d documents into 'item' colleciton")
		}else{
			//uh.... Not sure what I was intending here... possibly an auto retry?
		 	//add_item(db,userID,item)  //get item by id?
		}
	});
};

function deleteItem(db,userID,itemID){
	collection=db.collection('items');
	collection.remove({"_id":itemID},{$set:{update}},function(err,result){
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
	var curosr;
	if (itemID){
		cursor=collection.find({"userID":userID,"_ID":itemID});
	}else{
		cursor=collection.find({"userID":userID});
	}
	cursor.toArray(function(err,docs){
		if(err){
			console.log("error retreiving docs: ",err)
		}else{
		    var intCount = docs.length;
            if (intCount > 0) {
            	return docs
            }else{
            	console.log("no records found")
            }
		}
		
	})
};


//function to enable asynchronous operaiton
function asyncDB(db,userID,callback,items){
	for(var item in items){
		callback(db,userID,item);
	}
}

console.log("running");
app.get('/',function(req,res){
	console.log("http connect");
	res.sendfile(__dirname + '/index.html');
});

io.on('connection',function(socket){
		console.log("connection recieved");
		// con.connect(function(err){
		// 	if(err){
		// 		console.log('Errpr connecting to DB');
		// 		return
		// 	}
		// 	consol.log("DB conneciton established");
		// })
		socket.emit("init",{});
		socket.on('disconnect', function(){
			console.log("connection closed");
		});
		socket.on("login",function(login_info){
			console.log("login info recieved")
			socket.emit("login_accept");
		});
		socekt.on("add_tag", function(){
			//look for tag by name
			// add tag id item id relationship
		});
		socket.on("addItem", function(items){
			MongoClient.Connect("mongodb:localost:2017/personal_assistant", function(err,db){
				//TO DO: add asyncronous add item
				if(err){
					throw err;
				}else{
					console.log("database connnected");
					asyncDB(db,00,add_item,items);
				}
			});
			//look for tag by name
			// add tag id item id relationship
			
		});
		socket.on("getItem", function(items){
			Mongo.Client.Connect("mongodb:localost:2017/personal_assistant", function(err,db){
				asyncDB(db,00,getItems,items)
			});
		});
	});

//TO DO: handle login