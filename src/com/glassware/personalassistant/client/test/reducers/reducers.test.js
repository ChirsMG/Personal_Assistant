import * as reducers from "../../src/reducers/reducers"
import * as actions from "../../src/actions/actions"

const TEST_ITEMS_STATE = {
    "0001": { id: "0001", name: "test item 001", description: "a testing item" },
    "0002": { id: "0002", name: "test item 002", description: "another testing item" },
    "0003": { id: "0003", name: "test item 003", description: "a third testing item" },
    "0004": { id: "0004", name: "test item 004", description: "a fourth testing item" }
}
const TEST_ITEMS = [{ id: "0001", name: "test item 001", description: "a testing item" },
{ id: "0002", name: "test item 002", description: "another testing item" },
{ id: "0003", name: "test item 003", description: "a third testing item" },
{ id: "0004", name: "test item 004", description: "a fourth testing item" }]

describe("reducers", function () {
    describe("reducer integration", function () {

        it("Should create an action to  update an item", function () {
            let result = reducers.personalAssistantApp()
        })
        it("Should create an action to create an item", function () {
            let result = reducers.personalAssistantApp()

        })
        it("Should create an action to  remove an item", function () {
            let result = reducers.personalAssistantApp()

        })
        it("Should create an action to refersh the items list", function () {
            let result = reducers.personalAssistantApp()

        })
    });
    describe("item reducer", function () {

        it("Should update an item", function () {
            const updateId = "test id"
            const action = {
                type: actions.UPDATE_ITEM,
                id: updateId,
                item: { id: updateId }
            }
            let result = reducers.itemReducer({}, action)
            expect(result).toEqual({
                [updateId]: {
                    id: updateId
                }
            })
        })
        it("Should create an item", function () {
            const action = {
                type: actions.CREATE_ITEM,
                id: "12345767889abcdef",
                item: {
                    id: "12345767889abcdef",
                    name: "name",
                    description: "description"
                }
            }
            let result = reducers.itemReducer([], action)
            expect(result).toEqual({"12345767889abcdef":
                {
                    id: "12345767889abcdef",
                    name: "name",
                    description: "description"
                }
            })

        })
        it("Should remove an item", function () {
            let action = {
                type: actions.REMOVE_ITEM,
                id: "12345767889abcdef"
            }
            let item = {
                id: "12345767889abcdef",
                name: "name",
                description: "description"
            }
            let result = reducers.itemReducer({[item.id]:item}, action)
            expect(result).toEqual({}
            )

        })
        it("Should refersh the items list", function () {
            let action = {
                type: actions.REFRESH_ITEMS,
                payload: TEST_ITEMS
            }
            let result = reducers.itemReducer({}, action)
            expect(result).toEqual(TEST_ITEMS_STATE)
        })
    })
})