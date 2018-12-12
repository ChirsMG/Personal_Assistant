import {ENABLED,DISABLED,DRAGGING} from "../utils/constants"


// function screenResize(width) {
//     return {
//         type: SCREEN_RESIZE,
//         screenWidth: width
//     };
// }
export const UI="ui"
export const ENABLE_DRAG="enable_drag"
export const DISABLE_DRAG="disable_drag"
export const DRAG="drag"

export function enableDrag(id,el){
    let elementbox=el.getBoundingClientRect();
    let dragAction = {
        type: UI,
        event: ENABLE_DRAG,
        detail:{
            id:id,
            xPos:elementbox.x,
            yPos:elementbox.y
        }
    }
    return dragAction
}

export function disableDrag(id){ //todo: amke clear drag instead?
    let dragAction = {
        type: UI,
        event: DISABLE_DRAG,
        detail:{
            id:id
            
        }
    }
    return dragAction
}

export function startDrag(id){
    let dragAction = {
        type: UI,
        event: DRAG,
        detail:{
            id:id
            
        }
    }
    return dragAction
}