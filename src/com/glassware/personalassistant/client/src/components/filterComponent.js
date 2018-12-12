
import React from 'react'
import { connect } from 'react-redux'
import { Card } from './presentation';


const mapFilterStateToProps = (state, ownProps) => {


    return state.displayedItems.map(item =>{if(item.id==ownProps.id){return item }} )
    }
 
 const mapFilterDispatchToProps = (dispatch, ownProps) => {
     return { 
         onHandleClick:  () => dispatch(updateItem(ownProps.id, { title: ownProps.name + " updated" }))
         }
 }
 
 export default connect(mapFilterStateToProps,mapFilterDispatchToProps)(Card)