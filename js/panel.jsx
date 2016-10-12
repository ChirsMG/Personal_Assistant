// panel to pull and display information from server

/* NAMING CONVENTIONTS
 keywords at the start of a class or variable - all lower case

 test 	- indicates that object has no production features or 
 db		- indicates for debugging purposes

other flages
 G_/{start uppercase} 	- indicates global scoped variable
 {all caps}				- indicates static definition


 Variables use underscore and are lowercase unless global
 function use uppercase and no underscore


*/

var Modal=ReactBootstrap.Modal;
var Navbar=ReactBootstrap.Navbar;
var Nav=ReactBootstrap.Nav;
var NavItem=ReactBootstrap.NavItem;
var NavDropdown=ReactBootstrap.NavDropdown;
var MenuItem=ReactBootstrap.MenuItem;
var socket;




//var socket = io.connect('104.131.42.153');
if(socket = io.connect('10.0.0.183:30')){
	console.log('connect')
}

socket.on("connect",function(){
	console.log("client connect")
})

socket.on("error",function(){
	console.log("ERROR")
})

socket.on("init",function(){
	console.log("logging in");
	socket.emit("login",{username:"MASTER", pwd:"TEMPLOGIN"});
})

socket.on("login_accept",function(){
	console.log('login confirmed')
})


socket.emit("info",{});

document.body.addEventListener("unload",Unload);

function Unload(){
	io.close();
}
// TO DO:  
/*
	- login info
	- retreieve data


*/
var Bar=(
	<Navbar>
		<Navbar.Header>
	      <Navbar.Brand>
	        <a href="#">Personal Assistant</a>
	      </Navbar.Brand>
	    </Navbar.Header>
	    <Nav>
	      <NavItem eventKey={1} href="#">[symbol for view type 1]</NavItem>
	      <NavItem eventKey={2} href="#">[symbol for view type 2]</NavItem>
	      <NavDropdown eventKey={3} title="Dropdown" id="basic-nav-dropdown">
	        <MenuItem eventKey={3.1}>Action</MenuItem>
	        <MenuItem eventKey={3.2}>Another action</MenuItem>
	        <MenuItem eventKey={3.3}>Something else here</MenuItem>
	        <MenuItem divider />
	        <MenuItem eventKey={3.3}>Separated link</MenuItem>
	      </NavDropdown>
	    </Nav>
  	</Navbar>

	)


var NewItemComponent=React.createClass({
	handleKeyPress: function(e){
		// add data to DB
	},

	render:function(){
		return(
			<div className="item_input">
				<form className="new_item">
					<input className="title_input" type="text"/>
					<textarea className="content_input"></textarea>
				</form>
			</div>
		)
	}
});

var ItemBarComponent= React.createClass({
	getIntitalState:function(){
		return({
			show:false,
		})
	},
	handleClick:function(event){
		ReactDOM.render(
			<NewItemComponent />,
			document.getElementById('mainDiv')
			);
	},

	render:function(){
		return(
			<div>
				<input type="text" className="title_input" onClick={this.handleClick} value="create an item"/>
			</div>
		)
	}
});

// TO BE USED LATER
////////////////////////////////////
var ModalForm= (
	<div className="static-modal">
		<Modal.Dialog>
			<Modal.Body>
			</Modal.Body>
			<Modal.Footer>
			</Modal.Footer>
		</Modal.Dialog>
	</div>	
	)
//////////////////////////////////////


var Main=React.createClass({
	render:function(){
		return(
			<div>
				{Bar}
				<ItemBarComponent />
			</div>
			)
	}
});

ReactDOM.render(
	<Main/>,
	document.getElementById('mainDiv')
	);

var HelloWorld = React.createClass({
  	render: function() {
	    return (
	      <div>
	        Hello World!
	      </div>
		)
	}
});
 
ReactDOM.render(
  <HelloWorld />,
  document.getElementById('app')
);




/*THINGS TO LOOK UP

-nested classes?



*/