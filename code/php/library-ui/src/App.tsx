import React, { useState } from 'react';
import './App.css';
import Author from './Author';
import Book from './Book';
import Genre from './Genre';
import MenuAction from './MenuAction';
import MenuActionListener from './MenuActionListener';
import NavBar from './NavBar';

function App() {

  const [selectedMenuItem, setSelectedMenuItem] = useState<MenuAction>();

  const menuSelectionChanged: MenuActionListener = function (action: MenuAction): void {
    setSelectedMenuItem(action);
  };

  let componentToDisplay = <h1><span className="badge badge-pill badge-primary align-items-centre">Please select an item to display</span></h1>;

  if (selectedMenuItem === MenuAction.ADD_NEW_GENRE) {
    componentToDisplay = <Genre/>;
  } else if (selectedMenuItem === MenuAction.ADD_NEW_AUTHOR) {
    componentToDisplay = <Author/>;
  } else if (selectedMenuItem === MenuAction.ADD_NEW_BOOK) {
    componentToDisplay = <Book/>;
  }

  return (
    <>
      <NavBar menuActionListener={menuSelectionChanged}/>
      {componentToDisplay}
    </>
  );
}

export default App;
