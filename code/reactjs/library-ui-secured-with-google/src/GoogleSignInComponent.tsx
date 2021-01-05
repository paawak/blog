import React, { FunctionComponent, useState } from 'react'
import { GoogleLogin, GoogleLoginResponse, GoogleLoginResponseOffline } from 'react-google-login';
import Alert from './Alert';
import AlertType from './AlertType';

interface GoogleSignInComponentProps {
  loginSuccess: (response: GoogleLoginResponse | GoogleLoginResponseOffline) => void;
}

export const GoogleSignInComponent: FunctionComponent<GoogleSignInComponentProps> = ({ loginSuccess }) => {

  const [loginFailed, setLoginFailed] = useState<boolean>();

  return (
    <div className="text-center mb-4">
      <h1 className="h3 mb-3 font-weight-normal">Welcome to Library Portal</h1>
      {loginFailed &&
        <Alert message="Could not sign you in! Try again." type={AlertType.ERROR} />
      }
      <p>Sign In</p>
      <GoogleLogin
        clientId={`${process.env.REACT_APP_GOOGLE_AUTH_CLIENT_ID}`}
        buttonText='Google'
        onSuccess={loginSuccess}
        onFailure={(response: any) => {
          setLoginFailed(true);
        }}
        cookiePolicy={'single_host_origin'}
        responseType='code,token'
      />
    </div>
  )
};
