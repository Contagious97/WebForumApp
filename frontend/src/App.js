import logo from './logo.svg';
import 'bootstrap/dist/css/bootstrap.min.css';
import '../node_modules/bootstrap/dist/css/bootstrap.min.css';
import {Container, Nav, Navbar} from "react-bootstrap";
import React from "react";

import Login from "./Components/login.component";
import SignUp from "./Components/signup.component";
import Feed from "./Components/feed.component";

import '../node_modules/bootstrap/dist/css/bootstrap.min.css';
import './App.css';

import {Route, BrowserRouter as Router, Switch} from "react-router-dom";

function App() {
    return (
        <div className="App">
            <Navbar className="navbar navbar-expand-lg navbar-light fixed-top" bg="dark" variant="dark">
                <Container>
                    <Navbar.Brand>Tumbler</Navbar.Brand>
                    <Nav className="me-auto">
                        <Nav.Link href="/">Home</Nav.Link>
                        <Nav.Link href="/sign-up">Sign Up</Nav.Link>
                        <Nav.Link href="/feed">Feed</Nav.Link>
                    </Nav>
                </Container>
            </Navbar>
            <Router>
                <Switch>
                    <Route exact path='/' component={Login}/>
                    <Route path="/sign-in" component={Login}/>
                    <Route path="/sign-up" component={SignUp}/>
                    <Route path="/feed" component={Feed}/>
                </Switch>
            </Router>
        </div>
    );
}

export default App;
