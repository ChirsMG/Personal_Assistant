import React from 'react'
import {connect} from 'react-redux'
 import {CardBlock} from './CardColumn'
import { DRAGGING } from '../utils/constants';
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
export const getCardListContainer = (items) => {
return items
}

const mapStateToProps = (state, ownProps) => {
    //  console.log("containter state: ",state)
        let updatedState={items: state.displayedItems.map(id => state.items[id] ) ,
            numCols: ownProps.numCols,
        dragId:state.uiState.drag.id}
    return updatedState
}

// const mapCardDispatchToProps = (dispatch, ownProps) => {
//     return { 
//         :  () => dispatch(updateItem(ownProps.id, { title: ownProps.title + " updated" }))
//         }
// }

export default connect(mapStateToProps)(CardBlock)