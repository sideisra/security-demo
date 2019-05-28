import Keycloak, {KeycloakTokenParsed} from 'keycloak-js'
import {REFRESH_INTERVAL, TOKEN_STORAGE_KEY} from './keycloakConstants'
import {useEffect, useState} from 'react'

const createKeycloak = () => {
  const request = new XMLHttpRequest();
  request.open('GET', '/keycloak-config.json', false);
  request.send(null);
  let keycloakConfig;
  try {
    if (request.status === 200) {
      keycloakConfig = JSON.parse(request.responseText)
    } else {
      console.error('unable to load keycloak config, status: ' + request.status) //tslint:disable-line
    }
  } catch (e) {
    console.error('unable to parse keycloak config ', e) //tslint:disable-line
  }

  return keycloakConfig ? Keycloak(keycloakConfig) : undefined
};

export const useKeycloak = () => {
  const [idTokenParsed, setIdTokenParsed] = useState<KeycloakTokenParsed | undefined>(undefined);
  useEffect(() => {
    const keycloak = createKeycloak();
    if (keycloak) {
      keycloak
          .init({onLoad: 'check-sso', checkLoginIframe: true})
          .success(authenticated => {
            if (keycloak.authenticated && keycloak.token) {
              sessionStorage.setItem(TOKEN_STORAGE_KEY, keycloak.token);
              setIdTokenParsed(keycloak.idTokenParsed);
              console.log(keycloak.idTokenParsed);
              //TODO set user info
              // store.dispatch(setUserInfoAction({
              //   authenticated: true,
              //   username: idTokenParsed.preferred_username,
              //   email: idTokenParsed.email,
              //   fullName: idTokenParsed.name,
              // }));

              setInterval(() => {
                keycloak
                    .updateToken(10)
                    .success(refreshed => {
                      if (refreshed && keycloak.token) {
                        setIdTokenParsed(keycloak.idTokenParsed);
                        sessionStorage.setItem(TOKEN_STORAGE_KEY, keycloak.token)
                      }
                    })
                    .error(() => {
                      //TODO do logout
                    })
              }, REFRESH_INTERVAL)
            } else {
              keycloak.login()
            }
          })
          .error(result => {
            console.error('keycloak init error'); //tslint:disable-line
            console.error(result) //tslint:disable-line
            //TODO set error state
          })
    } else {
      //TODO set error state
    }
  }, []); // only once, see: https://reactjs.org/docs/hooks-reference.html#conditionally-firing-an-effect
  return idTokenParsed
};
