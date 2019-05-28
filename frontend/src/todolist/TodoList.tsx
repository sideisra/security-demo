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
                    {todoList.items.map(i => <li>{i.value}</li>)}
                </ul>
                : <div>keine Todo Liste ausgewählt</div>
            }
        </div>
    );
};

export default TodoList
