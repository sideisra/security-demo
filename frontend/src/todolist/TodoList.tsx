import * as React from 'react'
import {Model} from "../model";

export type TodoListProps = {
    readonly todoList?: Model.TodoList;
}

const TodoList = (props: TodoListProps) => {
    const {todoList} = props;
    return (
        <div>
            {todoList
                ? <ul>
                    {todoList.items.map(i => <li>{i.value}</li>)}
                </ul>
                : <div>keine Daten</div>
            }
        </div>
    );
};

export default TodoList
