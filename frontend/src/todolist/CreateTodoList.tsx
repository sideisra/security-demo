import * as React from 'react'
import {useState} from 'react'
import * as api from "../api/Api";

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
      <div>
        <form onSubmit={onCreateTodoList}>
          <input type="text" onChange={onNameChange} value={name}/>
          <button type="submit">Create TodoList</button>
          {error && <div>{error}</div>}
        </form>
      </div>
  );
};

export default CreateTodoList
