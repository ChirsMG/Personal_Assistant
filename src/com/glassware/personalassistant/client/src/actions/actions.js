import { RandomAlphaNumeric } from "../utils/Util"

import ItemApi from "../interface/ItemApi"
export const UPDATE_ITEM = "update"
export const CREATE_ITEM = "create"
export const ADD_ITEM = "add"
export const REMOVE_ITEM = "remove"
export const REFRESH_ITEMS = "refresh"
export const ID_LENGTH = 10

export function createItemAction(name, description) {
    console.log(RandomAlphaNumeric)
    let newId = RandomAlphaNumeric(ID_LENGTH)
    return {
        type: CREATE_ITEM,
        id: newId,
        item: {
            id: newId,
            name: name,
            description: description
        }
    }
}

export function updateItem(id, updateMap) {
    console.log("Update item:", id, updateMap)
    let stateBuilder = {
        type: UPDATE_ITEM,
        id: id,
        // item:Object.assign({id:id},updateMap)
        item: updateMap //not sure why Object.assign was used above
    }
    return stateBuilder
}

export function removeItem(id) {
    console.log("Delete item:", id)
    let stateBuilder = {
        type: REMOVE_ITEM,
        id: id
        // item:Object.assign({id:id},updateMap)
    }
    return stateBuilder
}

export function refreshAllItems() {
    let api = new ItemApi("http://demo7211462.mockable.io");
    let Items = api.getItems();
    let refresh = {
        type: REFRESH_ITEMS,
        payload: Items
    }
    return refresh
}

