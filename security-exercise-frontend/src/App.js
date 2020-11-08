import React, { useState } from 'react';
import './App.css';
import Login from './components/Login'
import Page from './components/Page'

function App() {
  const initialValue = {
    username: "",
    password: ""
  }
  const [user, setUser] = useState(initialValue);

  function updateUser(user) {
    setUser(user);
  }

  return (
    <div className="App">
      <Page update={updateUser} user={user} />
    </div>
  );
}

export default App;
