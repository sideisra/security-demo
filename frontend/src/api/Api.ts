import axios from "axios";
import {Model} from "../model";

export const getTodoLists = () => {
  return axios.get("/todolists")
      .then(response => ({response, error: undefined}))
      .catch(error => ({response: undefined, error}));
};

export const createTodoList = (todoListName: string) => {
  return axios.post("/todolists", {name: todoListName, items: []})
      .then(response => ({response, error: undefined}))
      .catch(error => ({response: undefined, error}));
};

export const createTodoListItem = (todoListId: Model.TodoListId, value: string, done: boolean) => {
  return axios.post(`/todolists/${todoListId}/addItem`, {value: value, done: done})
      .then(response => ({response, error: undefined}))
      .catch(error => ({response: undefined, error}));
};

export const getTodoList = (todoListId: Model.TodoListId) => {
  return axios.get(`/todolists/${todoListId}`)
      .then(response => ({response, error: undefined}))
      .catch(error => ({response: undefined, error}));
};
