import React from 'react';
import './App.css';
import TodoLists from "./todolist/TodoLists";
import {AuthContext} from './AuthContext'
import {useKeycloak, UseKeycloakResult} from "./keycloakConfig";
import {Model} from "./model";

const App: React.FC = () => {
  const useKeycloakResult: UseKeycloakResult = useKeycloak();
  const authenticated: boolean = !!useKeycloakResult.idTokenParsed;

  const user: Model.ListOwner | undefined = useKeycloakResult.idTokenParsed ? {
    eMail: (useKeycloakResult.idTokenParsed as any).email,
    firstName: (useKeycloakResult.idTokenParsed as any).given_name,
    lastName: (useKeycloakResult.idTokenParsed as any).family_name,
  } : undefined;

  return (
      <div className="App">
        <AuthContext.Provider
            value={{
              authenticated,
              user: user,
            }}
        >
          {authenticated
              ? <>
                {user && user.firstName + " " + user.lastName}
                <button type="button"
                        onClick={() => useKeycloakResult.kcInstance && useKeycloakResult.kcInstance.logout()}>Logout
                </button>
                <TodoLists/>
              </>
              : <div>Nicht eingeloggt!</div>
          }
        </AuthContext.Provider>
      </div>
  );
};

export default App;
