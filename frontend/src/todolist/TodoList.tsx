import * as React from 'react'
import {Model} from "../model";

interface TodoListProps {
  todoList?: Model.TodoList
}

const TodoList = (props: TodoListProps) => {
  const {todoList} = props;
  return (
      <div>
        {todoList
            ? <ul>
              {todoList.items && todoList.items.length > 0
                  ? todoList.items.map(i => <li>{i.value}</li>)
                  : <div>keine Todos :-)</div>
              }
            </ul>
            : <div>keine Todo Liste ausgewählt</div>
        }
      </div>
  );
};

export default TodoList
