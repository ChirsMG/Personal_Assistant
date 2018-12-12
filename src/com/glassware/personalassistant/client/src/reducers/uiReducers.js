import { combineReducers } from 'redux'
import { DRAGGING, ENABLED, DISABLED } from "../utils/constants"
import { ENABLE_DRAG, DISABLE_DRAG, DRAG } from '../actions/uiActions';

// const initialState = {
//     screenWidth: typeof window === 'object' ? window.innerWidth : null,
//     columns: getColumnNum()
// };

export function uiReducer(state = {}, action) {
    switch (action.event) {
        case ENABLE_DRAG:
        //this may not be necessary
        case DRAG:
        case DISABLE_DRAG:
            return dragReducer(state, action)
        default:
            return state;
    }
}

function dragReducer(state, action) {
    switch (action.event) {
        case ENABLE_DRAG:
            return Object.assign({}, state, {
                drag: {
                    id: action.detail.id,
                    status: ENABLED,
                    xPos: action.detail.xPos,
                    yPos: action.detail.yPos
                }
            });
        //this may not be necessary
        case DRAG:
            //merge existing drag object with update
            let dragObj = Object.assign({}, state.drag, {
                id: action.detail.id,
                status: DRAGGING,
            })
            //merge drag object with state
            return Object.assign({}, state,{drag: dragObj});
        case DISABLE_DRAG:
            return Object.assign({}, state, {
                drag: {}
            });
    }
}