import PaItems from '../src/interface/ItemApi'
import mockXHR from './MockHttp'

const testItem1={"name":"test created item 1","description":"an item created during unit testing"}
const INVALID_QUERY_TEXT="invalid query"
const ITEM_NOT_FOUND_TEXT="no item found"
const SUCCESS="success"

describe("test item Api interface", function () {
    const oldXMLHttpRequest = window.XMLHttpRequest;
    const PA_URL = "http://test.test"
    window.XMLHttpRequest = jest.fn(() => mockXHR);
    describe("retrieveing items", function () {

        it("retrieve all items", function () {
            const api = new PaItems(PA_URL);
            expect(api.getItems().length).toBeGreaterThan(1)
        })

        it("retrieve an Item", function () {
            const api = new PaItems(PA_URL);
            const id = "0001"
            const result = api.getItem(id)
            // console.log(result)
            expect(result instanceof Object)
            
            expect(result.id).toBe(id)
        })

        //TODO
        it("fail invalid query structure", function () {
           //not sure how to trigger this
        }) //TODO
        it("fail item not found", function () {

            const api = new PaItems(PA_URL);
            const id = "INVALID ID VALUE"
            const result = api.getItem(id)
            // console.log(result)
            expect(result instanceof Object)
            
            expect(result.error).toBe(ITEM_NOT_FOUND_TEXT)
        }) //TODO
        it("fail null/empty", function () {
            const api = new PaItems(PA_URL);
            const result = api.getItem(null)
            // console.log(result)
            expect(result instanceof Object)
            
            expect(result.error).toBe(ITEM_NOT_FOUND_TEXT)//TODO refactor test
        })
    })
    describe("create items", function () {
        it("create one item", function () {
            const api = new PaItems(PA_URL);
            let result=api.createItem(testItem1)
            expect(result.id).toBe("TEST-CREATED")

        })
    })
    //todo
    describe("delete items", function () {
        it("delete one item", function () {
            const api = new PaItems(PA_URL);
            const id = "0001"
            let result=api.deleteItem(id)
            expect(result instanceof Object)
            expect(result.status).toBe(SUCCESS)
        })
        //todo
        it("delete all items", function () {
            //is this functionality I want??
        })
        
        it("delete some items", function () {
          
            const api = new PaItems(PA_URL);
            const query = {}
            let result=api.deleteItems({})
            expect(result instanceof Object)
            expect(result.status).toBe(SUCCESS) //if all items matching that wuery were deleted
        }) 
        
        it("delete some items - query empty", function () {
          
            const api = new PaItems(PA_URL);
            const query = {}
            let result=api.deleteItems({})
            expect(result instanceof Object)
            expect(result.warning).toBe(ITEM_NOT_FOUND_TEXT)
        })
        //todo
        it("invalid id", function () {
            const api = new PaItems(PA_URL);
            const query = {}
            let result=api.deleteItems({})
            expect(result instanceof Object)
            expect(result.warning).toBe(ITEM_NOT_FOUND_TEXT)

        })
    })
    //todo
    describe("update items", function () {
    })
})
