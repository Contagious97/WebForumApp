import logo from './logo.svg';
import './App.css';
import login from "./login/login";
import Login from "./login/login";
import {Navbar} from "react-bootstrap";
import Nav from "react-bootstrap/Nav";
import {Link, Outlet} from "react-router-dom";
import React from "react";

function App() {
  return (
          <div className="App">
              {/*<Navbar collapseOnSelect bg="light" expand="md" className="mb-3">
                  <Navbar.Brand className="font-weight-bold text-muted">
                      Welcome to the WebForum App
                  </Navbar.Brand>
                  <Navbar.Toggle />
              </Navbar>*/}
              <div className="Content">
                  <h1>Hello! </h1>

                  <Link to="/login">Login</Link>

              </div>
              <Outlet/>
          </div>
  );
}

export default App;
