import { combineReducers, createStore } from 'redux'
import {
    refreshAllItems,
    UPDATE_ITEM,
    CREATE_ITEM,
    ADD_ITEM,
    REFRESH_ITEMS,
    REMOVE_ITEM
} from '../actions/actions'

import { filterReducer, filterItems } from "./itemFilters"
import { FILTER } from "../actions/filterActions"
import {uiReducer} from "../reducers/uiReducers"
import {UI} from "../actions/uiActions"

// const initialState = {
//     items: {
//         "001": { id: "001", title: "test 001", content: "some content" },
//         "002": { id: "002", title: "test 002", content: "some more content" }
//     },
//     displayedItems: ["001", "002"]
// };
let initialState = {
    items:
        itemReducer({},
            refreshAllItems()
        ),
    appliedfilters: [],
    uiState:{drag:{}}
}
initialState.displayedItems = filterItems(initialState.items, initialState.appliedfilters)

export const store = createStore(personalAssistantApp)
export function personalAssistantApp(state = initialState, action) {
    // console.log("appstate:", state)
    //  console.log("duplicate result:",personalAssistantAppDup(state,action))
    if (!action) {
        return state
    }
    switch (action.type) {
        case UPDATE_ITEM:
        case CREATE_ITEM:
        case REFRESH_ITEMS:
        case REMOVE_ITEM:
            //todo nested actions????
            //TODO each Item loop

            console.log("item state before", state)
            var stateResult = itemReducer(state.items, action)
            // console.log("item Result", stateResult)
            // console.log("item state", state)
            var displayedIds = filterItems(state.items, state.appliedfilters)
            return Object.assign({}, state, { items: stateResult, displayedItems: displayedIds })
        case FILTER:
            return Object.assign({}, state,filterReducer(state.appliedfilters, action))
        case UI:
            return Object.assign({}, state, {uiState:uiReducer(state.uiState, action)})
        default:
            return state

    }

}
function personalAssistantAppDup(state = initialState, action) {
    // console.log("appstate:", state)
    switch (action.type) {
        case UPDATE_ITEM:
        case CREATE_ITEM:
        case REMOVE_ITEM:// may need to change how items are updated so as to not update all items
        case ADD_ITEM://TODO pick one create or add
            //todo nested actions????
            //TODO each Item loop

            // console.log("item state before", state)
            var stateResult = itemReducer(state.items, action)
            // console.log("item Result", stateResult)
            // console.log("item state", state)
            return Object.assign({}, state, { items: stateResult })

        // return Object.assign({},state)//
        default:
            return state
    }

}


export function itemReducer(state = {}, action) {
    // console.log("item reducer state: ", state)
    // console.log("item reducer action: ", action)
    console.log("action:", action)
    switch (action.type) {
        case REFRESH_ITEMS:
            return refreshItems(state, action)
        case CREATE_ITEM:
        case ADD_ITEM:
        case UPDATE_ITEM:
            return updateItemReducer(state, action)
        case REMOVE_ITEM:
            return removeItem(state, action)
        default:
            console.error("impotent action")
            return state
    }
}

// broken function
function addItem(state = {}, action) {
    var itemId = action.id
    return Object.assign({}, state, { id: action })

}

//state: state.items
function updateItemReducer(state = {}, action) {
    let tempObj
    if (state.hasOwnProperty(action.id)) {
        tempObj = Object.assign({}, state[action.id], action.item)//merge update with exisitn item
    } else {
        tempObj = action.item
    }
    return Object.assign({}, state, { [action.id]: tempObj })
}

function removeItem(state = {}, action) {
    if (state.hasOwnProperty(action.id)) {
        delete state[action.id]  // Do i need to make a temeporary object??
    }
    return Object.assign({}, state)
}

function refreshItems(state, action) {
    let stateItems = {}
    for (let i = 0; i < action.payload.length; i++) {
        stateItems[action.payload[i].id] = action.payload[i]
    }
    return Object.assign({}, state, stateItems)
}
