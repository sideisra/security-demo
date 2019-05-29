import * as React from 'react'
import {Model} from "./model";

export interface IAuthContextProps {
  authenticated: boolean,
  user: Model.ListOwner,

}

export const AuthContext = React.createContext<Partial<IAuthContextProps>>({});
