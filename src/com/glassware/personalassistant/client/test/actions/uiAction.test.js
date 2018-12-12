import {UI, enableDrag, disableDrag,ENABLE_DRAG, DISABLE_DRAG,DRAG, startDrag } from "../../src/actions/uiActions";
import {ENABLED,DISABLED,DRAGGING} from "../../src/utils/constants"

class mockElement{
    constructor(xPos,yPos){
        this.x=xPos
        this.y=yPos
    }
    getBoundingClientRect(){
        return{x:this.x,y:this.y}
    }

}

describe("UI actions",function(){
    describe("Drag actions",function(){
        it("should create enable drag action",function(){
            let ID="test_id";
            let dragAction = {
                type: UI,
                event: ENABLE_DRAG,
                detail:{
                    id:ID,
                    xPos:1,
                    yPos:2
                }
            }            
            expect(enableDrag(ID, new mockElement(1,2))).toEqual(dragAction)
        })
        it("should create dragging action",function(){
            let ID="test_id";
            let dragAction = {
                type: UI,
                event: DRAG,
                detail:{
                    id:ID
                }
            }            
            expect(startDrag(ID)).toEqual(dragAction)
        })
        it("should create disable drag action",function(){
            let ID="test_id";
            let disableDragAction = {
                type: UI,
                event: DISABLE_DRAG,
                detail:{
                    id:ID
                }
            }            
            expect(disableDrag(ID)).toEqual(disableDragAction)
        })
    })
})