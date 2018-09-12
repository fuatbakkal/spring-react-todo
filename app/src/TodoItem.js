import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';

class TodoItem extends Component {

  constructor(props) {
    super(props);
    this.state = {todoitems: [], isLoading: true};
    this.remove = this.remove.bind(this);
  }

  componentDidMount() {
    this.setState({isLoading: true});

    fetch(`api/todolist/${this.props.match.params.id}/todoitems`)
      .then(response => response.json())
      .then(data => this.setState({todoitems: data, isLoading: false}));
  }

  async remove(id) {
    await fetch(`/api/todolist/${this.props.match.params.id}/todoitem/${id}`, {
      method: 'DELETE',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      }
    }).then(() => {
      let updatedTodoitems = [...this.state.todoitems].filter(i => i.id !== id);
      this.setState({todoitems: updatedTodoitems});
    });
  }

  render() {
    const {todoitems, isLoading} = this.state;

    if (isLoading) {
      return <p>Loading...</p>;
    }

    const todoItem = todoitems.map(todoitem => {
      return <tr key={todoitem.id}>
        <td style={{whiteSpace: 'nowrap'}}>{todoitem.name}</td>
        <td>{todoitem.items.map(item => {
          return <div key={item.id}>{item.name}</div>
        })}</td>
        <td>{todoitem.itemCount}</td>
        <td>
          <ButtonGroup>
          <Button size="sm" color="success" tag={Link} to={"/todolist/" + todoitem.id + "/todoitems"}>Edit Items</Button>
            <Button size="sm" color="primary" tag={Link} to={"/todolist/" + todoitem.id}>Edit Item</Button>
            <Button size="sm" color="danger" onClick={() => this.remove(todoitem.id)}>Delete Item</Button>
          </ButtonGroup>
        </td>
      </tr>
    });

    return (
      <div>
        <AppNavbar/>
        <Container fluid>
          <div className="float-right">
            <Button color="success" tag={Link} to="/todoitem/new">Add New Todo Item</Button>
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
            {todoItem}
            </tbody>
          </Table>
        </Container>
      </div>
    );
  }
}

export default TodoItem;