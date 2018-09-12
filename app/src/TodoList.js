import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';

class TodoList extends Component {

  constructor(props) {
    super(props);
    this.state = {todolists: [], isLoading: true};
    this.remove = this.remove.bind(this);
  }

  componentDidMount() {
    this.setState({isLoading: true});

    fetch('api/todolists')
      .then(response => response.json())
      .then(data => this.setState({todolists: data, isLoading: false}));
  }

  async remove(id) {
    await fetch(`/api/todolist/${id}`, {
      method: 'DELETE',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      }
    }).then(() => {
      let updatedTodolists = [...this.state.todolists].filter(i => i.id !== id);
      this.setState({todolists: updatedTodolists});
    });
  }

  render() {
    const {todolists, isLoading} = this.state;

    if (isLoading) {
      return <p>Loading...</p>;
    }

    const todoList = todolists.map(todolist => {
      return <tr key={todolist.id}>
        <td style={{whiteSpace: 'nowrap'}}>{todolist.name}</td>
        <td>{todolist.items.map(item => {
          return <div key={item.id}>{item.name}</div>
        })}</td>
        <td>{todolist.itemCount}</td>
        <td>
          <ButtonGroup>
          <Button size="sm" color="success" tag={Link} to={"/todolist/" + todolist.id + "/todoitems"}>Edit Items</Button>
            <Button size="sm" color="primary" tag={Link} to={"/todolist/" + todolist.id}>Edit List</Button>
            <Button size="sm" color="danger" onClick={() => this.remove(todolist.id)}>Delete List</Button>
          </ButtonGroup>
        </td>
      </tr>
    });

    return (
      <div>
        <AppNavbar/>
        <Container fluid>
          <div className="float-right">
            <Button color="success" tag={Link} to="/todolist/new">Add New Todo List</Button>
          </div>
          <h3>TODO Lists</h3>
          <Table className="mt-4">
            <thead>
            <tr>
              <th width="20%">Name</th>
              <th>Items</th>
              <th width="10%">Item Count</th>
              <th width="10%">Actions</th>
            </tr>
            </thead>
            <tbody>
            {todoList}
            </tbody>
          </Table>
        </Container>

      </div>
    );
  }
}

export default TodoList;