import React from 'react';
import './App.css';
import TodoLists from "./todolist/TodoLists";
import {AuthContext} from './AuthContext'
import {useKeycloak} from "./keycloakConfig";

const App: React.FC = () => {
  const idTokenParsed = useKeycloak();
  const authenticated: boolean = !!idTokenParsed;

  return (
      <div className="App">
        <header className="App-header">
          <AuthContext.Provider
              value={{
                authenticated,
                email: idTokenParsed && (idTokenParsed as any).email,
              }}
          >
            <TodoLists/>
          </AuthContext.Provider>
        </header>
      </div>
  );
};

export default App;
