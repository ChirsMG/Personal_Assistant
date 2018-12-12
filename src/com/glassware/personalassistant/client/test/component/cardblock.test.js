import React from "react";
import { Provider } from 'react-redux'
import { shallow, mount, render } from "enzyme";
import { Card } from "../../src/components/presentation";
import { CardBlock } from "../../src/components/CardColumn";
import configureStore from 'redux-mock-store'
import Adapter from 'enzyme-adapter-react-16';
import { createStore } from "../../node_modules/redux";
import { UI, ENABLE_DRAG } from "../../src/actions/uiActions"
import { ENABLED } from "../../src/utils/constants"
import { getCardListContainer } from "../../src/components/CardListContainer";
import { diff } from "../diff"

describe("LockScreen", () => {
  let props;
  let mountedCardBlock;
  let shallowCardBlock;
  let initialState = {
    items: {
      "0001": { id: "0001", title: "test 001", content: "some content" },
      "0002": { id: "0002", title: "test 002", content: "some more content" },
      "0003": { id: "0003", title: "test 003", content: "some more content" }
    },
    displayedItems: ["0001", "0002", "0003"],
    appliedfilters: [],
    uiState: { drag: {} }
  }
  const mockStore = configureStore([])
  const Store = mockStore(initialState)

  beforeEach(() => {
    // const store = createStore()
    shallowCardBlock = shallow(
      <CardBlock numCols={3} items={[{ id: "0001" }, { id: "0002" }, { id: "0003" }]} />)
    mountedCardBlock = mount(
      <Provider store={Store}>
        <CardBlock dragId={Store.getState().uiState.drag.id} numCols={3} items={[{ id: "0001" }, { id: "0002" }, { id: "0003" }]} />
      </Provider>)
  });

  it("Should render", function () {
    console.log("cardblock", shallowCardBlock)
    console.log("cardblock find", shallowCardBlock.find('CardColumn'))
    console.log("cardblock find", shallowCardBlock.find('DraggingContainer'))

    expect(shallowCardBlock.find("CardColumn")).toHaveLength(3)
    //should not render either because shallow or because Draggign container return null
    expect(shallowCardBlock.find("DraggingContainer")).toHaveLength(0)
  })

  it("Should render fully", function () {
    expect(mountedCardBlock.find("CardColumn")).toHaveLength(3)
    
    //Need to figure out how to determie if the container properly returned null
    // expect(mountedCardBlock.find('Connect(DragWrapper)').get(0)).toBeFalsy()

  })

  it("should render dragging containter", function () {
    let id = "0002"
    let testState = initialState
    testState.uiState.drag = {
      id: id,
      status: ENABLED,
      xPos: 1,
      yPos: 2
    }
    let newStore = mockStore(testState)
    let oldInstance = mountedCardBlock.find('Connect(DragWrapper)').instance()
    let oldGet = mountedCardBlock.find('Connect(DragWrapper)').get(0)
    let oldChildren = mountedCardBlock.find('Connect(DragWrapper)').children()
    // mountedCardBlock.update()
    mountedCardBlock = mount(
      <Provider store={newStore}>
        <CardBlock dragId={newStore.getState().uiState.drag.id} numCols={3} items={[{ id: "0001" }, { id: "0002" }, { id: "0003" }]} />
      </Provider>)
      console.log("updated")
    expect(mountedCardBlock.find("CardColumn")).toHaveLength(3)
    console.log(mountedCardBlock.find("CardColumn").children())
  })

});