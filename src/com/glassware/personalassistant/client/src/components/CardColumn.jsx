import React from 'react';
import CardContainer from './CardContainer';
import { updateItem } from '../actions/actions';
import { store } from '../reducers/reducers'
import DraggingContainer from './DraggingContainer';

const PRIMARY_MOUSEBUTTON=1

class CardColumn extends React.Component {
  selectForDragging(e){
    console.log("selected")
      if(e.button==PRIMARY_MOUSEBUTTON){
        //todo 
        /*
        - change pointer value
        - if drag then add draggable class
        - drag = mouse movment
        */
       this.dragFunction=function(){
         console.log("updated drag function called")
       }

      }
  }

  engageDragging(){

  }


  render() {
    // console.log(this.props.contentList)
    return (
      //todo refactor idx into item key
      
      this.props.contentList.map((it, idx) => {
       
        return (<div key={idx} className={"cardCol" + this.props.numColumns}>
          <CardContainer itemId={it.id} />
        </div>)
      })
    )
  }
}

export class CardBlock extends React.Component {
 // todo make column container??

  render() {
    //todo smart render
    // console.log("rendering card block")
    const numCols = +this.props.numCols||1
    this.columnStructure=[]
    this.props.items.map((it, idx) => {
      this.columnStructure[idx % numCols] = this.columnStructure[idx % numCols] || []
      this.columnStructure[idx % numCols].push(it)
      // return it
    })
    // column structure should be a list of lists so "it" is a list
    return (
      <div className="cardBlock">
            <DraggingContainer/>
        {this.columnStructure.map((it, idx) => {
          // if(this.props.dragId && it.id==this.props.dragId){
          //   return(<div className="cardShadow"/>)
          // }
          return (
          <CardColumn id={it.id} key={idx} numColumns={this.props.numCols} columnNumber={idx + 1} contentList={it} />)
        })}

      </div>
    )
  }
}
