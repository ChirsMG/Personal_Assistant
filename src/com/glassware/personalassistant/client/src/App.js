import React, { Component } from 'react';
import './App.css';
import './components/Card.css';
import './components/CardBlock.css'
import  CardListContainer  from './components/CardListContainer';
import {store} from './reducers/reducers'
import {disableDrag} from "./actions/uiActions"
import DraggingContainer from './components/DraggingContainer';

class App extends Component {

  render() {
    return (
      <div className="App" >
        <header className="App-header">
          {/* <img src={logo} className="App-logo" alt="logo" /> */}
          <h1 className="App-title">Welcome to React</h1>
        </header>
        <p className="App-intro"/>
          <CardListContainer numCols={3}/>
      </div>
    );
  }
}

export default App;
