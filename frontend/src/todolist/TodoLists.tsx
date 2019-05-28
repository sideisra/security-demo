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

  const loadTodoLists = function () {
    api.getTodoLists()
        .then(result => {
          if (result && result.response) {
            const fetchedTodoLists: Model.TodoList[] = result.response.data;
            setTodoLists(fetchedTodoLists);
          }
        })
        .catch(error => console.log(error));
  };
  useEffect(() => {
    loadTodoLists();
    return () => {
    }
  }, []);

  const onTodoListCreated = () => {
    loadTodoLists();
  };

  const onReloadTodoList = (id: Model.TodoListId) => {
    api.getTodoList(id)
        .then(result => {
          return setTodoLists(todoLists.map(list => {
            if (result && result.response && list.id === id) {
              return result.response.data;
            } else {
              return list;
            }
          }));
        })
        .catch(error => console.log(error))
  };

  return (
      <div>
        <CreateTodoList todoListCreated={onTodoListCreated}/>
        <div>
          {todoLists.map(todoList =>
              <h2 className={"todolist-entry" + (selectedTodoListId === todoList.id ? " todolist-selected" : "")}
                  onClick={() => setSelectedTodoListId(todoList.id)}
                  key={todoList.id}>{todoList.name}</h2>
          )}
        </div>
        <TodoList todoList={todoLists.find(list => list.id === selectedTodoListId)} reloadTodoList={onReloadTodoList}/>
      </div>
  );
};

export default TodoLists
