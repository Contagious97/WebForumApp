import React, {Component} from "react";
import {Form} from "react-bootstrap";
import Button from "react-bootstrap/Button";
import {Route, Switch} from "react-router-dom";
import SignUp from "./signup.component";


export default class Login extends Component {
    render() {
        return (
            <div className="auth-wrapper">
                <div className="auth-inner">
                    <Form>
                        <h3>Sign In</h3>
                        <Form.Group className="mb-3" controlId="formBasicEmail">
                            <Form.Label>Email address</Form.Label>
                            <Form.Control type="email" placeholder="Enter email"/>
                            <Form.Text className="text-muted">
                                We'll never share your email with anyone else.
                            </Form.Text>
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="formBasicPassword">
                            <Form.Label>Password</Form.Label>
                            <Form.Control type="password" placeholder="Password"/>
                        </Form.Group>
                        <Form.Group className="mb-3" controlId="formBasicCheckbox">
                            <Form.Check type="checkbox" label="Remember me"/>
                        </Form.Group>
                        <Button variant="dark" type="submit">
                            Submit
                        </Button>
                    </Form>
                </div>
            </div>

        );
    }
}