import * as reducers from "../../src/reducers/uiReducers"
import * as actions from "../../src/actions/uiActions"
import { ENABLED, DISABLED, DRAGGING } from "../../src/utils/constants"



describe("reducers", function () {
    describe("reducer integration", function () {
    });
    describe("drag reducer", function () {

        it("Should enable drag", function () {
            const updateId = "test id"
            const action = {
                type: actions.UI,
                event: actions.ENABLE_DRAG,
                detail: {
                    id: updateId,
                    xPos: 1,
                    yPos: 2
                }
            }
            const state = {
                drag: {
                    id: updateId,
                    status: ENABLED,
                    xPos: 1,
                    yPos: 2
                }
            }
            let result = reducers.uiReducer({}, action)
            expect(result).toEqual(
                state
            )
        })
        it("Should disable drag", function () {
            const updateId = "test id"
            const action = {
                type: actions.UI,
                event: actions.DISABLE_DRAG,
                detail: {
                    id: updateId
                }
            }
            const state = {
                drag: {
                }
            }

            let result = reducers.uiReducer({}, action)
            expect(result).toEqual(
                state
            )
        })
        it("Should start drag", function () {
            const initState = {
                drag: {
                    id: updateId,
                    status: ENABLED,
                    xPos: 1,
                    yPos: 2
                }
            }

            const updateId = "test id"
            const action = {
                type: actions.UI,
                event: actions.DRAG,
                detail: {
                    id: updateId
                }
            }
            const state = {
                drag: {
                    id: updateId,
                    status: DRAGGING,
                    xPos: 1,
                    yPos: 2
                }
            }
            let result = reducers.uiReducer(initState, action)
            expect(result).toEqual(
                state
            )
        })
    })
})