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
var Button =ReactBootstrap.Button
var socket;






//var socket = io.connect('104.131.42.153');
//if(socket = io.connect('10.0.0.183:30')){
//	console.log('connect')
//}

// socket.on("connect",function(){
// 	console.log("client connect")
// })

// socket.on("error",function(){
// 	console.log("ERROR")
// })

// socket.on("init",function(){
// 	console.log("logging in");
// 	socket.emit("login",{username:"MASTER", pwd:"TEMPLOGIN"});
// })

// socket.on("login_accept",function(){
// 	console.log('login confirmed')
// })


// socket.emit("info",{});

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
	      <NavItem eventKey={1} href="#">[symbol for preferences]</NavItem>
	      <NavItem eventKey={2} href="#">[symbol for alt view]</NavItem>
	      <NavDropdown eventKey={3} title="Menu" id="basic-nav-dropdown">
	        <MenuItem eventKey={3.1}>Action</MenuItem>
	        <MenuItem eventKey={3.2}>Another action</MenuItem>
	        <MenuItem eventKey={3.3}>Something else here</MenuItem>
	        <MenuItem divider />
	        <MenuItem eventKey={3.3}>Separated link</MenuItem>
	      </NavDropdown>
	    </Nav>
  	</Navbar>

	)




var ItemBarComponent= React.createClass({
	defaultText:"write here...",
	defaultTitle:"Title",
	getInitialState:function(){
		return({
			show:false,
			value:this.defaultText,
			title:""
		})
	},
	addHandler:function(e){
		e.preventDefault();
		var data={title:"",content:""}
		$(e.target).children().each(
			function(){
				data[this.name]=this.value;
			})
	},
	handleClick:function(event){
		this.setState({show:true})
	},
	clearDefault:function(event,defaultVal){

		if(event.target.textContent==defaultVal || event.target.value==defaultVal){
			console.log("default found")
			//this.setState({value:""})
			event.target.textContent=""	
			event.target.value=""	
		}
	},
	render:function(){
		if(this.state.value==""){
			this.setState({value:this.defaultText});
		}

		if( this.state.show){
			return(
			<div className="item_input">
				<form id="item_form" className="new_item" onSubmit={this.addHandler}>
					<input name="title" className="title_input"  type="text" onClick={(e)=>this.clearDefault(e,this.defaultTitle)} defaultValue="Title"/>
					<div name="content" contentEditable={true} className="content_input" onClick={(e)=>this.clearDefault(e,this.defaultText)} onChange={this.handleChange} >{this.state.value}</div>
				</form>
				<div className="BottomBar">
					<Button>[task]</Button>
					<Button>[img]</Button>
					<Button>[list]</Button>
					<Button type="submit" className="right" form="item_form">+</Button>
				</div>
			</div>
		
			)
		}else{
			return(
				<div>
					<input type="text" className="title_input" onClick={this.handleClick.bind(this)} value="create an item"/>
				</div>
				)
		}
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
				<ItemBarComponent  />
			</div>
			)
	}
});

ReactDOM.render(
	<Main/>,
	document.getElementById('mainDiv')
	);



var Item_Card=React.createClass({
	getInitialstate:function(){
		return(
			data:{}
			)
	},
	render:function(){
		return(
			<div className="Card">
				<div className="item_title">{this.props.data.title}</div>
				<div className="item_description">{this.props.data.description}</div>
				<div className="item_list">
					<ul>
					{this.props.data.list.map(function(item){
						return(
							<li>{item}</li>
							)
					})}
					</ul>
				</div>
			</div>
			)

	}
});

var Cards=React.createClass({
	getInitialstate:function(){
		return(
		{
				
		})
	},
	render:function(){
		return(
				<div className="Card_display">
				{
					this.props.items.map(function(item){
						return(<Item_Card data={item} />)
					})
				}
				</div>				
			)
	}
})
 
ReactDOM.render(
  <Cards items={[]} />,
  document.getElementById('app')
);




/*THINGS TO LOOK UP

-nested classes?



*/