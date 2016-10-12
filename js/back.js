var crypto=require('crypto')
var SHA-hash= crypto.createHash('SHA-3')

function login(socket,username, password){
	socket.emit("login",{name:username,pwd:password});

};

function create_item(socket,title,description){
	socket.emit("add_item",{title:title,descript:descritpion})
};

function tag_item(socket,tag, item_id){
	socekt.emit("add_tag",{ID:tag_id,tag_name:tag})
};

