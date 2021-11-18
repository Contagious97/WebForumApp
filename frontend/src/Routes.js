import React from "react";
import {BrowserRouter as Router, Route} from "react-router-dom";
//import Home from "./containers/Home";
import Login from "./login/login";
import App from "./App";

export default function Routes() {
    return (
        <Router>
            <Route exact path="/login">
                <Login/>
            </Route>
            <Route exact path="/">
                <App />
            </Route>
        </Router>
    );
}