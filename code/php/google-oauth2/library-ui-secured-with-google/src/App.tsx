import React, { useState } from 'react';
import { GoogleLoginResponse, GoogleLoginResponseOffline } from 'react-google-login';
import './App.css';
import Author from './Author';
import Book from './Book';
import Genre from './Genre';
import { GoogleSignInComponent } from './GoogleSignInComponent';
import MenuAction from './MenuAction';
import MenuActionListener from './MenuActionListener';
import NavBar from './NavBar';

function App() {

  const [selectedMenuItem, setSelectedMenuItem] = useState<MenuAction>();
  const [googleAccessToken, setGoogleAccessToken] = useState<string>('');

  const menuSelectionChanged: MenuActionListener = function (action: MenuAction): void {
    setSelectedMenuItem(action);
    if (action === MenuAction.LOGOUT) {
      setGoogleAccessToken('');
    }
  };

  let componentToDisplay;

  if (!googleAccessToken) {
    componentToDisplay = <GoogleSignInComponent loginSuccess={(response: GoogleLoginResponse | GoogleLoginResponseOffline) => {
      if ('tokenId' in response) {
        setGoogleAccessToken(response.tokenId);
      }
    }} />
  } else if (selectedMenuItem === MenuAction.ADD_NEW_GENRE) {
    componentToDisplay = <Genre googleAccessToken={googleAccessToken} />;
  } else if (selectedMenuItem === MenuAction.ADD_NEW_AUTHOR) {
    componentToDisplay = <Author googleAccessToken={googleAccessToken} />;
  } else if (selectedMenuItem === MenuAction.ADD_NEW_BOOK) {
    componentToDisplay = <Book googleAccessToken={googleAccessToken} />;
  } else {
    componentToDisplay = <h1><span className="badge badge-pill badge-danger align-items-centre">Please select an item to display</span></h1>;
  }

  return (
    <>
      {googleAccessToken &&
        <NavBar menuActionListener={menuSelectionChanged} />
      }      
      {componentToDisplay}
    </>
  );
}

export default App;
