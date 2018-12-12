

export default class PaItems {

        constructor(URL) {
                this.items = [];
                this.url = URL + "/item"
                console.log("initializing api to ", this.url)
                if (!this.url) {
                        throw "url cannot be null or undefined"
                }
        }

        getItems() {
                const params = { "query": "ALL" }
                var result = this.makeRequest("GET", this.retrieveItems, null, params, null)
                // console.log("get this items: ", this.items.length)
                return this.items;
        }

        getItem(id) {
                let request = this.makeRequest("GET", this.retrieveItems, null, null, "/" + id)
                // console.log(request)
                if (request.hasOwnProperty("error")) {
                        this.logError(request)
                        return request
                }
                return request
        }
        //todo pull out to utils class or something
        logError(responseObj) {
                console.error(responseObj.error)
        }
        makeRequest(method, callback, payload, params, addtnlPath) {
                addtnlPath = addtnlPath ? addtnlPath : '' // null check
                // console.log(params)
                console.debug("mocking %s method to %s with paramters: ", method, this.url, params)
                if (!callback) {
                        console.trace()
                        throw "request must be handled"
                }
                const param = []
                let queryString = ""
                if (params) {
                        queryString = "?"
                        Object.keys(params).forEach(element => {
                                param.push(element + "=" + params[element])


                        });
                        queryString += param.join('&')
                        console.log("query string: ", queryString)
                }

                let request = new XMLHttpRequest()
                // request.onreadystatechange = callback(request,this);
                let endpoint = this.url + addtnlPath + queryString
                request.open(method, endpoint, false);
                request.setRequestHeader("Content-Type", "application/json");
                // xmlhttp.setRequestHeader('Authorization', 'Basic ' + window.btoa('apiusername:apiuserpassword')); //in prod, you should encrypt user name and password and provide encrypted keys here instead 
                if (payload) {
                        request.send(JSON.stringify(payload));
                } else {
                        request.send()
                }
                if (request.status === 200) {
                        return callback(request, this)
                } else {
                        console.error("status not 200 status:", request.status, endpoint)
                        console.log(queryString)
                }
                // return request
        }

        retrieveItems(xmlhttp, self) {
                let result = JSON.parse(xmlhttp.responseText)
                self.items = self.items.concat(result)
                return result
        }

        confirmItem(xmlhttp) {
                // console.log("confirmItem:", xmlhttp.responseText)
                return JSON.parse(xmlhttp.responseText)
        }
        handleResponse(xmlhttp) {

        }

        /*
        UNSENT	
        OPENED
        HEADERS_RECEIVED
        LOADING
        DONE
        */
        // getItems(ids = []) {
        //         let request = this.makeRequest("GET", this.retrieveItems, ids)
        // }

        createItem(item) {
                let request = this.makeRequest("POST", this.confirmItem, item, null)
                return request
        }

        updateItem(itemUpdate) {
                let request = this.makeRequest("PATCH", this.sendItemConfirmation, itemUpdate)
                return request
        }

        // getItem(id) {
        //         let request = this.makeRequest("GET", this.retrieveItems, id)
        // }

        deleteItem(id) {
                let request = this.makeRequest("DELETE", this.confirmItem, null, null, id)
                return request
        }

        deleteItems(ids = []) {
                //payload = []
                let request = this.makeRequest("DELETE", this.confirmItem, null, { id: ids })
                return request

        }
}