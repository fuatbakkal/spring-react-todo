import React, { Component } from 'react';
import './App.css';
import Home from './Home';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import TodoList from './TodoList';
import TodolistEdit from './TodolistEdit';
import TodoItem from './TodoItem';

class App extends Component {
  render() {
    return (
      <Router>
        <Switch>
          <Route path='/' exact={true} component={Home}/>
          <Route path='/todolists' exact={true} component={TodoList}/>
          <Route path='/todolist/:id' component={TodolistEdit}/>
          <Route path='/todolist/:id/todoitems' component={TodoItem}/>
        </Switch>
      </Router>
    )
  }
}

export default App;