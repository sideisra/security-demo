import React from 'react';
import './App.css';
import TodoLists from "./todolist/TodoLists";
import {AuthContext} from './AuthContext'
import {useKeycloak, UseKeycloakResult} from "./keycloakConfig";
import {Model} from "./model";
import Button from 'react-bootstrap/Button';
import Alert from "react-bootstrap/Alert";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";

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
              ? <Container>
                <Row>
                  <Col md="auto">
                    My Todo Lists
                  </Col>
                  <Col className="user-info">
                    {user && user.firstName + " " + user.lastName}
                    <Button className="btn-logout" type="button" variant="secondary"
                            onClick={() => useKeycloakResult.kcInstance && useKeycloakResult.kcInstance.logout()}>Logout
                    </Button>
                  </Col>
                </Row>
                <TodoLists/>
              </Container>
              : <Alert variant="primary"> Nicht eingeloggt!</Alert>
          }
        </AuthContext.Provider>
      </div>
  );
};

export default App;
