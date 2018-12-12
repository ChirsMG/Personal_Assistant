import { CLEAR_FILTERS , APPLY_FILTER} from "../actions/filterActions"
import * as FILTERS from "../utils/filters"

export function filterItems(state, filters) {
    let ids=filter(filters,state)
    return ids
}


export function filterReducer(state, action) {
    switch (action.method) {
        case CLEAR_FILTERS:
            return Object.assign({}, {})
        case APPLY_FILTER:
            return filterItems(state,action)
        default:
         return state
    }

}

function filter(filterMapList,setToFilter){
    let filterList=Object.keys(setToFilter)
    for(let i =0; i< filterMapList.lenth;i++){
        let filter=filterMapList[i]
        filterList=FILTERS[filter.name](filter.arg1,filter.arg2,setToFilter)
    }
    return filterList
    
}

//filter options
/*
- field contains text
- field does not contain text
- field equals
- does not equal

*/

//storage convention
/* 
    filter:[{
        name:[filter name for lookup],
        arg1:'',
        arg2:''

    }...]

*/