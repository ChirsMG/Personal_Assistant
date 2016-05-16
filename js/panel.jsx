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

var socket= new io.Socket();


var NewItemComponent=React.createClass({
	handleKeyPress: function(e){
		// add data to DB
	}

	render:function(){
		return(
			<div>
				<form className="new_item">
					<input type="text"/>
					<textarea></textarea>
				</form>
			</div>
		)
	}
});

var ItemBarComponent= React.createClass({
	handleClick: function(event){

		ReactDOM.render(
			<NewItemComponent />,
			document.getElementById('mainDiv')
			);
	},

	render:function(){
		return(
			<div>
				<input type="text" onClick={this.handleClick} value="create an item"/>
			</div>
		)
	}
});

ReactDOM.render(
	<ItemBarComponent />,
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