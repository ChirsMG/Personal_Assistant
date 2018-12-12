
export function diff(Object1, Object2){
    let returnObj = {}

    for(let val in Object1){
        if(!(Object2.hasOwnProperty(val)&& Object2[val]==Object1[val])){
            returnObj[val]={"Object1":Object1[val],"Object2":Object2[val]}
        }
    }

    return returnObj
}   

export function diffwIgnore(Object1, Object2,ignorArray){
    let returnObj = {}
    for(let val in Object1){
        if(!(ignorArray.contains(val) && Object2.hasOwnProperty(val)&& Object2[val]==Object1[val])){
            returnObj[val]={"Object1":Object1[val],"Object2":Object2[val]}
        }
    }
    return returnObj

}