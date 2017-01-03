//Global data structures
var CURRENTUSER=0;
var LoadedItems=[];
baseURI="http:/localhost:30";

class RESThandler{
	 static request(METHOD,URI,callback,SEND){
	 	console.log("request")
		var xmlHttp = new XMLHttpRequest();
    	xmlHttp.onreadystatechange = function() { 
	        if (xmlHttp.readyState == 4 && xmlHttp.status == 200)
				callback(XMLHttpRequest.response,XMLHttpRequest.responseType,XMLHttpRequest.status)
		}
		//console.log("readyState established")
		xmlHttp.open(METHOD,URI,true);
		xmlHttp.send(SEND);
	}
}

function createItem(item){
	console.log(baseURI)
	var URI= baseURI//+"users/items/"//To DO do add auth token before users
	RESThandler.request("GET",URI,confirm,item)
	console.log("create item")
}

function requestItems(Params){
	var URI=baseURI//+ "users/items/"//To DO do add auth token before users
	console.log(baseURI)
	RESThandler.request("POST",URI,loadItems,Params)
	console.log("requestingItems")
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


function confirm(source,type,status){
	console.log("confirmed")
	if(status & 200){ //bitwise check for 20X code
		return 1;
	}else{
		return 0;
	}
}