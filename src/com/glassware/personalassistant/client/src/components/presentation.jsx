import React from 'react';
import { updateItem } from '../actions/actions';
import { store } from '../reducers/reducers'
import ReactDOM from 'react-dom'

//TODO look into proptypes
const DRAGGING_STYLE={

}


export class Card extends React.Component {
  constructor(props) {
    super(props);
    this.isDragging=false
    this.selected=false
    this.style=props.style||{}
    this.element;
    // this.handleOnClick = this.handleOnClick.bind(this);
  }
  
  onMouseDown(e){
    console.log("mouse down")
    this.props.enableDrag(this.element)
    this.onMouseUp = this.onMouseUp.bind(this);
    document.addEventListener("mouseup",this.onMouseUp)
  }
  
  onMouseUp(e){
    console.log("mouse UP")
    this.isDragging=false
    document.removeEventListener("mouseup",this.onMouseUp)
    this.props.disableDrag()
  }

  onMouseMove(e){
    if(this.props.dragState){
      console.log("dragging")
      this.isDragging=true
      this.props.drag()
      e.stopPropagation()
    }
  }

componentDidMount(){
  
    this.element=ReactDOM.findDOMNode(this)
    // console.log(this.element)
}

  render() {
    // console.log(this.props.style)
    if (this.isDragging) {
      console.log("shawdow item")
      return (
        <div className="cardShadow" >

        </div>)
    }
    if(this.props.mode=="float"){
      this.style={position:"absolute",
    top:200,
    left:100}
    }
    return (<div onMouseDown={this.onMouseDown.bind(this)} style={this.style} onMouseMove={this.onMouseMove.bind(this)} className="card" >
      <h1>{this.props.name}</h1>
      <div>{this.props.description}</div>
    </div>)
  }
}

