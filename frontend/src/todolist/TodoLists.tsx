import * as React from 'react'
import {useEffect, useState} from 'react'
import {Model} from "../model";
import * as api from "../api/Api";
import './TodoLists.css';
import TodoList from "./TodoList";
import CreateTodoList from "./CreateTodoList";

const TodoLists = () => {
  const [todoLists, setTodoLists] = useState<Model.TodoList[]>([]);
  const [selectedTodoListId, setSelectedTodoListId] = useState<Model.TodoListId | undefined>(undefined);

  useEffect(() => {
    api.getTodoLists()
        .then(result => {
          console.log(result);
          if (result && result.response) {
            const fetchedTodoLists: Model.TodoList[] = result.response.data;
            setTodoLists(fetchedTodoLists);
          }
        })
        .catch(error => console.log(error));
    return () => {
    }
  }, []);

  return (
      <div>
        <CreateTodoList/>
        <div>
          {todoLists.map(todoList =>
              <h2 className={"todolist-entry" + (selectedTodoListId === todoList.id ? " todolist-selected" : "")}
                  onClick={() => setSelectedTodoListId(todoList.id)}
                  key={todoList.id}>{todoList.name}</h2>
          )}
        </div>
        <TodoList todoList={todoLists.find(list => list.id === selectedTodoListId)}/>
      </div>
  );
};

export default TodoLists
