import React from 'react'
import {connect} from 'react-redux'
import { DragWrapper } from './DragWrapper';




const mapStateToProps=function (state,ownProps){
    // console.log(state.uiState)
    return( Object.assign({},state.uiState))
}
const mapDispatchToProps=function(dispatch,ownProps){
    return {}
}

export default connect(mapStateToProps,mapDispatchToProps)(DragWrapper)