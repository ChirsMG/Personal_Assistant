export const FILTER="filter"
export const CLEAR_FILTERS="clear"
export const APPLY_FILTER="apply filter"

export function clearAllFilters() {
    let clearAction = {
        type: FILTER,
        method: CLEAR_FILTERS
    }
    return clearAction
}
