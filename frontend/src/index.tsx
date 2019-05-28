import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import * as serviceWorker from './serviceWorker';
import {HTTP_UNAUTHORIZED, TOKEN_STORAGE_KEY} from "./keycloakConstants";
import axios from "axios";

axios.interceptors.request.use((config) => {
  const token = sessionStorage.getItem(TOKEN_STORAGE_KEY);
  if (token) {
    return {...config, headers: {...config.headers, Authorization: "Bearer " + token}};
  } else {
    console.warn("no token"); //tslint:disable-line
    return config;
  }
});
const onReqFailure = (error: any) => {
  if (error
      && error.response
      && (error.response.status === HTTP_UNAUTHORIZED)) {
    console.warn("auto logout because backend returned " + error.response.status); //tslint:disable-line
    //TODO logout
  }
  return Promise.reject(error);
};
axios.interceptors.response.use((response) => (response), onReqFailure);

ReactDOM.render(<App/>, document.getElementById('root'));

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
