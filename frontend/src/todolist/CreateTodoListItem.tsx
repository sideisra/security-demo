import * as React from 'react'
import {useState} from 'react'
import * as api from "../api/Api";
import {Model} from "../model";

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
      <div>
        <form onSubmit={onCreateTodoListItem}>
          <div className="input-group form-group">
            <input type="text" className="form-control" id="value" placeholder="Value" onChange={onValueChange}/>
            <input type="checkbox" className="form-control" id="value" onChange={onDoneChange}/>
          </div>
          <button type="submit" className="btn btn-primary btn-block">Create TodoList Item</button>
          {error && <div>{error}</div>}
        </form>
      </div>
  );
};

export default CreateTodoListItem
