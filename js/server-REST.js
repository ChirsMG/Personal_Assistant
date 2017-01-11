var express= require('express')
var app=express();
var bodyParser = require('body-parser');
var json=require('express-json')

var crypto = require('crypto')

var sessions={}

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
	});
};
///////////////////////////////////
console.log("starting");
setInterval(function() {
		for (var element in sessions){
			element["timeleft"]=-15;
			if(element.timeleft<=0){
				sessions.splice(index)
			}
		}
	}, 10000);



//app.get('/',function(req,res){
//	console.log("http connect");
	//res.sendfile(__dirname + '/index.html');
//});
//app.all("*",function(req,res){
//	console.log("all");
//	console.log(req.originalUrl)
//})

function updateToken(user,token){
	if(sessions[user][token]==token){
		sessions[user][timeleft]=300;
	}else{
		return false;
	}
	return true;
}

function saltAndHash(input){
	var date= new Date().getTime();
	var hash=crypto.createHash("sha512")
	console.log(hash.update(input+date))
	console.log("hash message:",input+date)
	var digest=hash.digest("hex")
	return {digest:digest, stamp:date}
}	

app.use(json());

app.use(bodyParser.urlencoded({extended : false}));

console.log("using json and urlencoded");
// var logger=function(req,res,next){
// 	console.log("request:",req)
// 	console.log("base URL", req.baseUrl)
// 	res.json({message:"request recieved"})
// 	next();
// }

//headers


app.use("/",function(req,res,next){
	//console.log("all")
    res.setHeader('Access-Control-Allow-Origin', '*');
    res.setHeader('Access-Control-Allow-Headers', 'access-control-allow-origin, Access-Control-Allow-Method');
    res.setHeader("Content-Type",'application/json')
    res.setHeader('Access-Control-Allow-Methods','PUT')
	console.log(res.get("Content-Type"))
	//console.log(res)
	next();
})




// //Init Routs (login, etc)
app.put("/login/:username/:pass",function(req,res,next){
	var pass=req.params.pass
	var username= req.params.username
	console.log("bod test:",req.body)
	console.log("logging in", username, "w/ ",pass)
	// TO DO verify pass with DB and get ID
	var userId=0

	//HASH FUNCTIONALITY- returns a hashed authtoken unique to user and session
	var authHash = saltAndHash(username)
	res.json({user:username,Auth_token:authHash["digest"]});
	sessions[userId]={"user":username,"token":authHash["digest"], "start":authHash["stamp"],timeleft:300}
	console.log(sessions)
	//res.send({success:1}); //TO DO add auth token functionaility
	next();
});


var TESTrouter=express.Router();
var router=express.Router("users");
//TO DO: create login hash before for route


//TEST REST FUNCTIONS
// TESTrouter.all('*',function(req,res,next){
// 	console.log("all");
// 	//console.log(req.originalUrl)
// 	//console.log(req.params);
// 	res.json({message:"message routed"});
// 	next();
// })
// TESTrouter.post("",function(req,res,next){
// 	console.log("POST");
// 	console.log(res.params)
// 	res.status(200).send("post");
// 	next();
// })
// TESTrouter.put("",function(req,res,next){
// 	console.log("PUT");
// 	res.status(200).send("Put");
// 	next();
// })
// TESTrouter.delete("",function(req,res,next){
// 	console.log("DELETE");
// 	res.status(200).send("deleted");
// 	next();
// })

//app.use(TESTrouter);



//Basic User Routes
router.all(function(req,res,next){
    res.setHeader('Access-Control-Allow-Origin', 'http://localhost:3000z');
    res.send("auth token")
	var user= req.params.user;
	var token= req.params.token
	if(updateToken(user,token)){
		next();
	}else{
		//TO DO send log out notificaiton
	}
})

router.get("/",function(req,res){
	//TO DO send list of users by ID
	console.log("USER LIST");
	console.log("request: ",req);
	console.log("result: ",res);
	res.json({message:"um... huh"})
});


router.get("/:id",function(req,res){
	var ID= req.params.id;
	// get User info
	// TO DO get auth token
	updateToken(id,token)
	console.log("get user:",ID)
});

router.put("/:id",function(req,res){
	var ID= req.params.id;
	updateToken(ID,token);
	console.log("add user:",ID)
});

router.delete("/:id",function(req,res){
	var ID= req.params.id;
	console.log("remove",ID)
});


//ItemRoutes
//LIST
router.get("/items",function(req,res){
	//TO DO send list of users by ID
	console.log("ITEMS LIST for USER")
});

//CREATE //UPDATE
router.post("/items",function(req,res){
	// add item
	var IDs= req.body.ids;
	console.log("get items",IDs)
});
//READ
router.get("/items/:id",function(req,res){
	//get specific item
	var ID= req.params.id;
	console.log("get item",ID)
});
//DELETE
router.delete("/items/:id",function(req,res){
	var ID= req.params.id;
	console.log("remove item:",ID)
});




app.listen(3000,function(err) {
	console.log("listening")
    if(err){
       console.log(err);
     } else {
       console.log("listen:3000");
    }
});
//TO DO: handle login