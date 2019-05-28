import * as React from 'react'

export interface IAuthContextProps {
  authenticated: boolean
  email: string
}

export const AuthContext = React.createContext<Partial<IAuthContextProps>>({});
