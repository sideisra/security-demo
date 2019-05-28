import axios from "axios";

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
