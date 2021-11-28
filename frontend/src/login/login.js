import logo from "../logo.svg";

import {loginUtil} from "./LoginUtil"
import React, { useState } from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import axios from "axios";
import {LOGIN} from "../Constants/Constants";

//import "./Login.css";

function Login() {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    let validInput = false;

    function validateForm() {
        validInput = username.length > 0 && password.length > 0;
    }

    function handleSubmit(event) {
        console.log(this.state);
        axios.post(LOGIN,{username: username,password:password}).
        then(response =>{
            console.log(response)
        })
    }

    return (
        <div className="Login">
            <Form onSubmit={handleSubmit}>
                <Form.Group size="lg" controlId="email">
                    <Form.Label>Username</Form.Label>
                    <Form.Control
                        autoFocus
                        type="username"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                    />
                </Form.Group>
                <Form.Group size="lg" controlId="password">
                    <Form.Label>Password</Form.Label>
                    <Form.Control
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                </Form.Group>
                <Button variant={"primary"} block size="lg" type="submit" >
                    Login
                </Button>
            </Form>
        </div>
    );
}
export default Login;