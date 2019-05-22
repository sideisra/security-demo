import axios from "axios";

export const getTodoLists = () => {
    return axios.get("/todolists")
        .then(response => ({response, error: undefined}))
        .catch(error => ({response: undefined, error}));
};
