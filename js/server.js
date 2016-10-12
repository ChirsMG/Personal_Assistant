var app= require('express')();
var server= app.listen(30);
var io = require('socket.io').listen(server);
var users=["MASTER"];
var PWDs=["TEMPLOGIN"]; //TODO: hash function
var MYSQL =require('mysql')

var con = mysql.createConnection({
	host:"localhost",
	user:"app",
	password:"Ma1nApp";
	databse:
})

function

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
		)};
		socekt.on("add_tag", function(){
			//look for tag by name
			// add tag id item id relationship
		});
		socket.on("add_item", function(){
			//look for tag by name
			// add tag id item id relationship
			
		});
	});

//TO DO: handle login