//Global data structures
var CURRENTUSER={id:0,name:"admin"};
var LoadedItems=[];
var AUTHTOKEN=0
baseURI="http://localhost:3000/";

class RESThandler{
	 static request(METHOD,URI,callback,SEND){
	 	console.log("request")
		var xmlHttp = new XMLHttpRequest();
		xmlHttp.open(METHOD,URI,true);
		xmlHttp.setRequestHeader("Accept","application/json");
    	xmlHttp.setRequestHeader('Access-Control-Allow-Origin', '*');
    	xmlHttp.onreadystatechange = function(){
    		//console.log("state change:", xmlHttp.readyState) 
    		if(xmlHttp.readyState == xmlHttp.HEADERS_RECEIVED){
   				
			}
	        if (xmlHttp.readyState == 4 && xmlHttp.status != 404){
	        	// LOGIN  and AUTH check
				callback(xmlHttp.response,xmlHttp.getResponseHeader("Content-Type"),xmlHttp.status)
	        	//console.log("response:",xmlHttp.response);
	        	//console.log("response type:",xmlHttp.responseType)
	        	//console.log("response xml:",xmlHttp.responseXML)
	        	//console.log("status:", this.status)
	        }
		}
		//console.log("readyState established")
		xmlHttp.send(SEND);
	}
}

function createItem(item){
	console.log(baseURI)
	var URI= baseURI  + "&Auth_token="+AUTHTOKEN //+"users/items/"//To DO do add auth token before users
	console.log(URI)
	RESThandler.request("GET",URI,confirm,null)
	console.log("create item")
}

function requestItems(Params){
	var URI=baseURI//+ "users/items/"//To DO do add auth token before users
	console.log(baseURI)
	RESThandler.request("POST",URI,loadItems,Params)
	console.log("requestingItems")
	console.log(Params)
}

function deleteItem(id){
	var URI=baseURI//+ "users/items/"+id.toString(); //To DO do add auth token before users
	RESThandler.request("DELETE",URI,confirm,null)
	console.log("deleting item")
}

function updateItem(item){
	var URI=baseURI//+ "users/items/";
	RESThandler.request("PUT","URI",confirm,item);
	console.log("updating Item")
}


function loadItems(source,type,status){
	console.log("data:",source)
	console.log("loadingItems status:",status)
	if(status & 400 || status & 500){ // checks for error codes
		console.log("ERROR")
		return;
	}
	// loads items from databse into Items global variable asynchronously
	for (var i = source.length - 1; i >= 0; i--) {
		if(LoadedItems.length>10){ // for debugging, remove for tests
			break;
		}
		LoadedItems=source
	};
	//console.log(Items[0])
	console.log("items loaded current items:", LoadedItems.length)
	
}

function LoginTest(){
	var password="password"
	var URI=baseURI+"login/admin/password"
	console.log(URI);
	RESThandler.request("PUT",URI,getAuthToken,null)
	console.log("loggging in")
}

function getAuthToken(res,type,status){
	console.log("getting token")
	console.log(res)
	
	if(res){
		res=JSON.parse(res)
		console.log(res.user)
	}
 	if(type!="application/json"){
 		//ERROR
 		console.log("type error:",type)
 	}
 	if(res["user"]!=CURRENTUSER.name){
 		//ERROR
 		console.log("User error:",res["user"])
 		return 0
 	}else{
 		AUTHTOKEN=res["Auth_token"];
 		console.log(AUTHTOKEN);
 		return 1;
 	}
}

function confirm(source,type,status){
	console.log("data:",source)
	console.log("confirmed")
	if(status & 200){ //bitwise check for 20X code
		return 1;
	}else{
		return 0;
	}
}