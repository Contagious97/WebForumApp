import logo from './logo.svg';
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import '../node_modules/bootstrap/dist/css/bootstrap.min.css';
import {Container, Nav, Navbar} from "react-bootstrap";
import React, {useEffect, useState} from "react";

import Login from "./Components/login.component";
import SignUp from "./Components/signup.component";
import {Route, BrowserRouter as Router, Switch, useParams, useHistory, useLocation} from "react-router-dom";
import Feed from "./Components/feed.component";
import Logout from "./Components/logout.component";
import DMs from "./Components/DMs.component";
import Whiteboard from './Components/Whiteboard';
import container from "./Components/Container/container";
import Charts from "./Components/Chart/Chart";




    export default function(){
        const [user,setUser] = useState();
        const location = useLocation();
        useEffect(()=>{
            const lookup = localStorage.getItem('user');
            if (lookup != null){
                const foundUser = JSON.parse(lookup)
                console.log(foundUser.username)
                console.log(foundUser.name)
                setUser(foundUser);
                console.log()
            }
        },[location.pathname])

        return (
            <div className="App">
                {routes()}
                <Navbar className="navbar navbar-expand-lg navbar-light fixed-top" bg="dark" variant="dark">
                    <Container>
                        <Navbar.Brand>WebbForumApp {user?user.name:''}</Navbar.Brand>
                        {headers()}
                    </Container>
                </Navbar>
            </div>
        );
    }






    function headers(){
        let loggeduser = localStorage.getItem('user');
        if (loggeduser == null){
            return (
                <Nav className="me-auto">
                    <Nav.Link href="/sign-in">Login</Nav.Link>
                    <Nav.Link href="/sign-up">Sign Up</Nav.Link>
                    <Nav.Link href="/canvas">Canvas</Nav.Link>
                </Nav>
            )
        } else{
            return (
                <Nav className="me-auto">
                    <Nav.Link href="/feed">Feed</Nav.Link>
                    <Nav.Link href="/logout">Logout</Nav.Link>
                    <Nav.Link href="/message">Message</Nav.Link>
                    <Nav.Link href="/charts">Charts</Nav.Link>
                </Nav>

            )
        }
    }

    function routes(){

        let loggedInUser = localStorage.getItem('user');
        console.log(loggedInUser)

        if (loggedInUser == null){
            return(
                <Switch>
                    <Route exact path='/' component={Login} />
                    <Route path="/sign-in" component={Login} />
                    <Route path="/sign-up" component={SignUp} />
                    <Route path = "/canvas" component = {container}/>
                </Switch>
            )
        } else {
            return(
                <Switch>
                    <Route path = "/charts" component = {Charts}/>
                    <Route path="/message" component={DMs}/>
                    <Route path="/feed/:userParam" component={Feed}/>
                    <Route path="/logout" component={Logout}/>
                    <Route exact-path="/feed" component={Feed}/>
                </Switch>
            )
        }
    }



//export default App;
