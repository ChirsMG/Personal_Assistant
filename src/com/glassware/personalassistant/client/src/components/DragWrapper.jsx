import React from 'react';
import CardContainer from './CardContainer';
import { updateItem } from '../actions/actions';
import { store } from '../reducers/reducers'

const PRIMARY_MOUSEBUTTON = 1

export class DragWrapper extends React.Component {
    constructor(props){
        super(props)
        // console.log("props",this.props)
        this.style={position:"absolute"}
    }

    render() {
        console.log("dragwrapper id:",this.props.drag.id)
        if(!this.props.drag.id){
            console.log("returning null")
            return null
        }
        // console.log("render")
        // console.log(this.props.drag)
        return(
                <CardContainer mode={"float"}itemId={this.props.drag.id}/>
        ) 
    }
}
