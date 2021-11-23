import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
// import {BrowserRouter, Route, Router} from "react-router-dom";
import { BrowserRouter as Router, Switch, Route, Link } from "react-router-dom";
//import login from "login"
import Routes from "./Routes";
import Login from "./Components/login.component";
import signup from "./SignUp/signup";
import SignUp from "./Components/signup.component";

ReactDOM.render(
  <React.StrictMode>
      <App/>
      {/*<Router>*/}
      {/*    <Routes>*/}
      {/*        <Route path="/login" element= {<Login/>}/>*/}
      {/*        <Route path="/" element={<App/>}/>*/}
              {/*<Route path="/sign-up" element={<signup/>}/>*/}
          {/*</Routes>*/}

      {/*</Router>*/}
  </React.StrictMode>,
  document.getElementById('root')
);
// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
