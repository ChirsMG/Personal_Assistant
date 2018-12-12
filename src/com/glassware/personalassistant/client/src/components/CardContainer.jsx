import React from 'react'
import { connect } from 'react-redux'
import { updateItem } from '../actions/actions'
import { Card } from './presentation';
import { enableDrag,startDrag,disableDrag } from '../actions/uiActions';
import { DRAGGING } from '../utils/constants';
import { isDraggable } from '../styles/dragStyles';
// import {store} from '../reducers/reducers'
// export class CardListContainter extends React.Component {
//     constructor(props){
//         super(props)
//         this.state=this.props
//         console.log(this.state)
//         console.log(this.props)

//     }

//     componentDidMount() {
//         console.log(this.state)
//         // getList(items => this.setState({ items: items }));
//     }

//     render() {
//         console.log("contianer- CardListContainer rendered")
//         return (<CardBlock numCols="3" className="cardContent" items={this.state.items} />)
//     }
// }
const getCardContainer = (id) => {

}

const mapCardStateToProps = (state, ownProps) => {
    // console.log("Card state to props")
    // console.log("state:", state.items)
    // console.log("ownProps:", ownProps)
    // console.log("result:", state.items[ownProps.itemId])

    let dragState = 0
    let dragStyle={}
    if (state.uiState.drag != {} && state.uiState.drag.id == ownProps.itemId) {
        dragState = state.uiState.drag.status
        dragStyle=isDraggable
    
    }
    // console.log("mapping state: ",state)
    // console.log("ownProps: ",ownProps)
    // console.log("ownProps is equal: ",ownProps.id== state.items[0].id)
    // console.log("map state to props: ",state.items.filter(item =>item.id==ownProps.id )[0])
    let returnObject = Object.assign({}, state.items[ownProps.itemId], { dragState: dragState,style:dragStyle, mode:ownProps.mode })
    return returnObject

}

const mapCardDispatchToProps = (dispatch, ownProps) => {
    // console.log("ownProps: ",ownProps)
    // console.log("dispatch: ",dispatch)
    return {
        handleClick: (itemProps) => {

            console.log("itemProps", itemProps)
            dispatch(updateItem(ownProps.itemId, { name: itemProps.name + " updated" }))
        },
        enableDrag: (element) => {
            if(!element){
                throw "must pass an element to enable drag"
            }
            console.log("dispatching enable drag")
            dispatch(enableDrag(ownProps.itemId,element))
        },
        disableDrag: () => {
            console.log("dispatching disable drag")
            dispatch(disableDrag(ownProps.itemId))
        },
        drag: () => {
            console.log("dispatching drag")
            dispatch(startDrag(ownProps.itemId))
        }
    }
}

export default connect(mapCardStateToProps, mapCardDispatchToProps)(Card)