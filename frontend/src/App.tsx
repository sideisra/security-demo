import React from 'react';
import './App.css';
import TodoLists from "./todolist/TodoLists";
import {AuthContext} from './AuthContext'
import {useKeycloak, UseKeycloakResult} from "./keycloakConfig";

const App: React.FC = () => {
  const useKeycloakResult: UseKeycloakResult = useKeycloak();
  const authenticated: boolean = !!useKeycloakResult.idTokenParsed;

  return (
      <div className="App">
        <AuthContext.Provider
            value={{
              authenticated,
              email: useKeycloakResult.idTokenParsed && (useKeycloakResult.idTokenParsed as any).email,
            }}
        >
          <button type="button"
                  onClick={() => useKeycloakResult.kcInstance && useKeycloakResult.kcInstance.logout()}>Logout
          </button>
          <TodoLists/>
        </AuthContext.Provider>
      </div>
  );
};

export default App;
