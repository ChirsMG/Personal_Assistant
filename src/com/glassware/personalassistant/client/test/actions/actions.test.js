import * as actions from "../../src/actions/actions"

describe("action creators", function () {
    const TEST_DESCRIPTION = "a block of text to test the description parameter of an object"
    const TEST_NAME = " a test Item"
    const TEST_ITEMS = [{ id: "0001", name: "test item 001", description: "a testing item" },
    { id: "0002", name: "test item 002", description: "another testing item" },
    { id: "0003", name: "test item 003", description: "a third testing item" },
    { id: "0004", name: "test item 004", description: "a fourth testing item" }]

    it("should render", function () {
        let testActions = actions
    })
    it("Should create an action to  update an item", function () {
        const id = "TEST_ID"
        const expctdAction = {
            type: actions.UPDATE_ITEM,
            id: id,
            item: {}
        }
        let actionObj = actions.updateItem(id, {})
        expect(actionObj).toEqual(expctdAction)
    })
    it("Should create an action to create an item", function () {
        let actionObj = actions.createItemAction(TEST_NAME, TEST_DESCRIPTION)
        expect(actionObj.item.id.length).toBe(actions.ID_LENGTH)
        expect(actionObj.item.name).toBe(TEST_NAME)
        expect(actionObj.type).toBe(actions.CREATE_ITEM)
        expect(actionObj.item.description).toBe(TEST_DESCRIPTION)

    })
    it("Should create an action to  remove an item", function () {
        const id = "TEST_ID"
        const expctdAction = {
            type: actions.REMOVE_ITEM,
            id: id
        }
        let actionObj = actions.removeItem(id)
        expect(actionObj).toEqual(expctdAction)

    })
    it("Should create an action to refersh the items list", function () {
        const id = "TEST_ID"
        const expctdAction = {
            type: actions.REFRESH_ITEMS,
            payload: TEST_ITEMS
        }
        let actionObj = actions.refreshAllItems()
        expect(actionObj).toEqual(expctdAction)

    })
})