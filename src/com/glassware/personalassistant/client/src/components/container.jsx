import React from 'react'
import { connect } from 'react-redux'
import { Card } from './presentation';



// export class CardContainer extends React.Component {
//     state = this.props.initState

//     render() {
//         return (<Card title={this.state.title} content={this.state.content} />)
//     }
// }

const mapCardStateToProps = (state, ownProps) => {
   return state.displayedItems.map(displayedId =>{if(state.items[displayedId].id==ownProps.id){return state.items[displayedId] }} )
   }

const mapCardDispatchToProps = (dispatch, ownProps) => {
    return { 
        onHandleClick:  () => dispatch(updateItem(ownProps.id, { title: ownProps.name + " updated" }))
        }
}

export default connect(mapCardStateToProps,mapCardDispatchToProps)(Card)