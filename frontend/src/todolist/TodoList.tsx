import * as React from 'react'
import {Model} from "../model";
import CreateTodoListItem from "./CreateTodoListItem";

interface TodoListProps {
  todoList?: Model.TodoList;
  reloadTodoList: (id: Model.TodoListId) => void;
}

const TodoList = (props: TodoListProps) => {
  const {todoList} = props;

  const onTodoListItemCreated = () => {
    props.reloadTodoList(props.todoList!.id);
  };

  return (
      <div>
        {todoList
            ? <div>
              <ul>
                {todoList.items && todoList.items.length > 0
                    ? todoList.items.map(i => <li key={i.id}>{i.value}</li>)
                    : <div>keine Todos :-)</div>
                }
              </ul>
              <CreateTodoListItem todoListId={props.todoList!.id} todoListItemCreated={onTodoListItemCreated}/>
            </div>
            : <div>keine Todo Liste ausgewählt</div>
        }
      </div>
  );
};

export default TodoList
