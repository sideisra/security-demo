import * as React from 'react'
import {useState} from 'react'
import * as api from "../api/Api";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import Alert from "react-bootstrap/Alert";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";

export interface CreateTodoListProps {
  todoListCreated: () => void;
}

const CreateTodoList = (props: CreateTodoListProps) => {
  const [name, setName] = useState("");
  const [error, setError] = useState<string | undefined>(undefined);

  const onNameChange = (event: any) => {
    setName(event.target.value);
  };

  const onCreateTodoList = (event: any) => {
    event.preventDefault();
    api.createTodoList(name)
        .then(response => {
          setError(undefined);
          props.todoListCreated();
        })
        .catch(error => setError("Anlegen fehlgeschlagen!"));
  };

  return (
      <Form onSubmit={onCreateTodoList}>
        <Form.Row>
          <Col>
            <Form.Control onChange={onNameChange} type="text" placeholder="Neue Liste..."/>
          </Col>
          <Col>
            <Button variant="primary" type="submit">
              Create
            </Button>
          </Col>
        </Form.Row>
        {error && <Alert variant="danger">{error}</Alert>}
      </Form>
  );
};

export default CreateTodoList
