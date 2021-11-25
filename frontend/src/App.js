import logo from './logo.svg';
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import '../node_modules/bootstrap/dist/css/bootstrap.min.css';
import {Container, Nav, Navbar} from "react-bootstrap";
import React, {useEffect, useState} from "react";

import Login from "./Components/login.component";
import SignUp from "./Components/signup.component";
import {Route, BrowserRouter as Router, Switch} from "react-router-dom";
import Feed from "./Components/feed.component";



function App(){

    const [user,setUser] = useState();

    useEffect(()=>{
        const lookup = localStorage.getItem('user');
        if (lookup){
            const foundUser = JSON.parse(lookup)
            console.log(foundUser.username)
            console.log(foundUser.name)
            setUser(foundUser);
            console.log()
        }
    },[])

    return (<Router>
        <div className="App">
        <Navbar className="navbar navbar-expand-lg navbar-light fixed-top" bg="dark" variant="dark">
            <Container>
                <Navbar.Brand>WebbForumApp {user?user.name:0}</Navbar.Brand>
                <Nav className="me-auto">
                    <Nav.Link href="/">Home</Nav.Link>
                    <Nav.Link href="/sign-up">Sign Up</Nav.Link>
                    <Nav.Link href="/feed">Feed</Nav.Link>
                </Nav>
            </Container>
        </Navbar>
            <Switch>
                <Route exact path='/' component={Login} />
                <Route path="/sign-in" component={Login} />
                <Route path="/sign-up" component={SignUp} />
                <Route path="/feed" component={Feed}/>
            </Switch>

        </div>
        </Router>
    );
}

export default App;
