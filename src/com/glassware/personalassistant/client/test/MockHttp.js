export default {


    open: jest.fn(function (method, url) {
        console.debug("mocking http connection to %s with method %s", url, method)
        let urlObj = new URL(url);
        let path = []
        if (urlObj.pathname) {
            // console.debug("parsing path name: ",urlObj.pathname)
            path = urlObj.pathname.split('/');
        }
        const parameters = urlObj.searchParams;
        switch (method.toUpperCase()) {
            case "GET":
                switch (path[1]) { //path[0] is alway ''
                    case "item":
                        if (path.length > 2) {//assume path[2]= item id
                            console.log(path[2])
                            this.responseText = JSON.stringify(getItembyId(path[2]))
                            break;
                        }
                        this.responseText = JSON.stringify(queryResult(parameters))
                        break;
                    default:
                        console.error("unhandled path: ", path[1], url)
                        break;
                }
                break;
            case "POST":
                //is there anything to do on open??
                break;
            case "DELETE":
                this.responseText = JSON.stringify(findAndDelete(path[2] ? path[2] : parameters.get("id")))
                break;
            default:
                console.error("unhandled method", method)
                break;
        }
    }),
    send: jest.fn(function (payload) {
        if (payload) {
            let payloadObj = JSON.parse(payload)
            validateItem(payloadObj)
            payloadObj.id = "TEST-CREATED"
            ITEMS.push(payloadObj)
            console.log("neg1: ", ITEMS[0])
            this.responseText =JSON.stringify({id:ITEMS[ITEMS.length - 1].id})
        } else {
            console.log("no payload")
        }
    }),
    readyState: 4,
    status: 200,
    responseText: JSON.stringify(
        ITEMS
    ),
    setRequestHeader: jest.fn()

};

function validateItem(obj) {
    // console.log("payload obj: ",obj)
    if (!obj.hasOwnProperty("name") || !obj.hasOwnProperty("description")) {
        throw "invalid Item"
    }
}

const oldXMLHttpRequest = window.XMLHttpRequest;
window.XMLHttpRequest = jest.fn(() => this);

const item1 = { id: "0001", name: "test item 001", description: "a testing item" };
const item2 = { id: "0002", name: "test item 002", description: "another testing item" };
const item3 = { id: "0003", name: "test item 003", description: "a third testing item" };
const item4 = { id: "0004", name: "test item 004", description: "a foruth testing item" };

let ITEMS = [item1, item2, item3, item4]
function getItembyId(id) {
    for (var idx in ITEMS) {//not ideal pattern for array iteration
        if (ITEMS[idx].id == id) {
            return ITEMS[idx]

        }
    }
    return { error: "no item found" }
}
function findAndDelete(id) {
    if (id instanceof Array) {
        errorCount=0;
        for (let i = 0; i < id.length; i++) {
            let item = getItembyId()
            if (item.hasOwnProperty("error")){
                errorCount++;
            }
        }
        if(errorCount == id.length){
                return { status: "success", warning: "no items found" }

        }
        if(errorCount){
            return { status: "success", warning: errorCount+" items not found" }
        }else{
                return { status: "success"}
        }
    }

    let itemSearch = getItembyId()
    if (itemSearch.hasOwnProperty("error")) {
        return { status: "success", warning: itemSearch.error }
    }
    return { status: "success" }
}
function queryResult(parameters) {
    if (!parameters) {
        return { error: "no parameters specified" }
    }
    if (parameters.get("query") == "ALL") {
        return ITEMS
    }
    if (parameters.has("id")) {
        let foundItem = { error: "no item found" }
        console.log(parameters.get("id"))
        foundItem = getItembyId(parameters.get("id"))
        return foundItem
    }
    return { error: "item(s) not found" }
}