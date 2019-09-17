import * as React from 'react'
import {Model} from "../model";
import CreateTodoListItem from "./CreateTodoListItem";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faCheckCircle, faCircle} from "@fortawesome/free-regular-svg-icons";

interface TodoListProps {
  todoList?: Model.TodoList;
  reloadTodoList: (id: Model.TodoListId) => void;
}

const TodoList = (props: TodoListProps) => {
  const {todoList} = props;

  const onTodoListItemCreated = () => {
    props.reloadTodoList(props.todoList!.id);
  };

  const onDoneClick = (item: Model.TodoListItem) => {
    console.log("toggle done of item " + JSON.stringify(item));
  };

  return (
      <div>
        {todoList
            ? <div>
              {todoList.items && todoList.items.length > 0
                  ? (
                      <ul className="fa-ul">
                        {todoList.items.map(i => {
                          return (
                              <li key={i.id}>
                                <span onClick={() => onDoneClick(i)} className="fa-li"><FontAwesomeIcon
                                    icon={i.done ? faCheckCircle : faCircle}/></span>
                                {i.value}
                              </li>
                          );
                        })}
                      </ul>
                  )
                  : <div>keine Todos :-)</div>
              }
              <CreateTodoListItem todoListId={props.todoList!.id} todoListItemCreated={onTodoListItemCreated}/>
            </div>
            : <div>keine Todo Liste ausgewählt</div>
        }
      </div>
  );
};

export default TodoList
