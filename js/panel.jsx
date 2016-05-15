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

var TestComponent= React.createClass({
	render:function(){
		return(
			<div>
				Hello fuckheads!
			</div>
		)
	}
});

ReactDOM.render(
	<TestComponent />,
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