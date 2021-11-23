import logo from './logo.svg';
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import '../node_modules/bootstrap/dist/css/bootstrap.min.css';
import {Container, Nav, Navbar} from "react-bootstrap";
import React from "react";

import Login from "./Components/login.component";
import SignUp from "./Components/signup.component";
import {Route, BrowserRouter as Router, Switch} from "react-router-dom";

function App(){
    return (<Router>
        <div className="App">
        <Navbar className="navbar navbar-expand-lg navbar-light fixed-top" bg="dark" variant="dark">
            <Container>
                <Navbar.Brand>Tumbler</Navbar.Brand>
                <Nav className="me-auto">
                    <Nav.Link href="/">Home</Nav.Link>
                    <Nav.Link href="/sign-up">Sign Up</Nav.Link>
                </Nav>
            </Container>
        </Navbar>
            <div className="auth-wrapper">
                <div className="auth-inner">
                    <Switch>
                        <Route exact path='/' component={Login} />
                        <Route path="/sign-in" component={Login} />
                        <Route path="/sign-up" component={SignUp} />
                    </Switch>
                </div>
            </div>
        </div>
        </Router>
    );
}

export default App;
