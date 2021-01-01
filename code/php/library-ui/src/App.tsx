import React from 'react';
import './App.css';
import Author from './Author';
import MenuAction from './MenuAction';
import MenuActionListener from './MenuActionListener';
import NavBar from './NavBar';

function App() {

  let menuSelectionChanged: MenuActionListener = function (action: MenuAction): void {
    console.log('*************', action);
  };

  return (
    <>
      <NavBar menuActionListener={menuSelectionChanged}/>
      <Author />
    </>
  );
}

export default App;
