import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import TodoItem from './TodoItem';

class TodolistEdit extends Component {

  emptyItem = {
    name: '',
  };

  constructor(props) {
    super(props);
    this.state = {
      item: this.emptyItem
    };
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  async componentDidMount() {
    // Edit todo list
    if (this.props.match.params.id !== 'new') {
      const group = await (await fetch(`/api/todolist/${this.props.match.params.id}`)).json();
      this.setState({ item: group });
    }
  }

  handleChange(event) {
    const target = event.target;
    const value = target.value;
    const name = target.name;
    let item = { ...this.state.item };
    item[name] = value;
    this.setState({ item });
  }

  async handleSubmit(event) {
    event.preventDefault();
    const { item } = this.state;

    if (item.id) {
      await fetch(`/api/todolist/${item.id}`, {
        method: 'PUT',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(item),
      });
      this.props.history.push('/todolists');
    } else {
      await fetch('/api/todolist', {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(item),
      });
      this.props.history.push('/todolists');
    }
  }

  render() {
    const { item } = this.state;
    const title = <h2>{item.id ? 'Edit Todo List' : 'Add New Todo List'}</h2>;

    return <div>
      <AppNavbar />
      <Container>
        {title}
        <Form onSubmit={this.handleSubmit}>
          <FormGroup>
            <Label for="name">Name</Label>
            <Input type="text" name="name" id="name" value={item.name || ''}
              onChange={this.handleChange} autoComplete="name" />
          </FormGroup>
          <FormGroup>
            <Button color="primary" type="submit">Save</Button>{' '}
            <Button color="secondary" tag={Link} to="/todolists">Cancel</Button>
          </FormGroup>
        </Form>
      </Container>
      <Router>
        <Switch>
          <Route path='/todolist/:id/todoitems' component={TodoItem}/>
        </Switch>
      </Router>
    </div>
  }
}

export default withRouter(TodolistEdit);