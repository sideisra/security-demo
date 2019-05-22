export namespace Model {
    export type TodoList = {
        readonly id: TodoListId;
        readonly name: string;
        readonly owner: ListOwner;
        readonly items: TodoListItem[];
    }

    export type TodoListId = string
    export type ListOwner = {
        readonly eMail: string;
        readonly firstName: string;
        readonly lastName: string;
    }
    export type TodoListItemId = string
    export type TodoListItem = {
        readonly id: TodoListItemId;
        readonly value: string;
        readonly done: boolean;
    }
}

