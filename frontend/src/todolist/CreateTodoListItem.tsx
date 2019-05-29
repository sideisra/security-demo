import * as React from 'react'
import {useState} from 'react'
import * as api from "../api/Api";
import {Model} from "../model";
import Button from "react-bootstrap/Button";
import Alert from "react-bootstrap/Alert";
import Form from "react-bootstrap/Form";
import Col from "react-bootstrap/Col";

export interface CreateTodoListItemProps {
  todoListId: Model.TodoListId;
  todoListItemCreated: () => void;
}

const CreateTodoListItem = (props: CreateTodoListItemProps) => {
  const [value, setValue] = useState("");
  const [done, setDone] = useState(false);
  const [error, setError] = useState<string | undefined>(undefined);

  const onValueChange = (event: any) => {
    setValue(event.target.value);
  };
  const onDoneChange = (event: any) => {
    setDone(event.target.checked);
  };

  const onCreateTodoListItem = (event: any) => {
    event.preventDefault();
    api.createTodoListItem(props.todoListId, value, done)
        .then(response => {
          setError(undefined);
          props.todoListItemCreated();
        })
        .catch(error => setError("Anlegen fehlgeschlagen!"));
  };

  return (
      <Form onSubmit={onCreateTodoListItem}>
        <Form.Row>
          <Col>
            <Form.Control onChange={onValueChange} type="text" placeholder="Value"/>
          </Col>
          <Col>
            <Form.Control onChange={onDoneChange} type="checkbox"/>
          </Col>
          <Col>
            <Button variant="primary" type="submit">
              Create TodoList Item
            </Button>
          </Col>
        </Form.Row>
        {error && <Alert variant="danger">{error}</Alert>}
      </Form>
  );
};

export default CreateTodoListItem
