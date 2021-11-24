import React, { Component } from "react";
import {Form} from "react-bootstrap";
import {Route, Switch} from "react-router-dom";
import Login from "./login.component";

export default class SignUp extends Component {
    render() {
        return (
            <div className="auth-wrapper">
                <div className="auth-inner">
                    <Form>
                        <h3>Sign Up</h3>
                        <Form.Group className="mb-3" id="formGridFirst">
                            <label>First name</label>
                            <input type="text" className="form-control" placeholder="First name" />
                        </Form.Group>

                        <Form.Group className="mb-3" id="formGridLast">
                            <label>Last name</label>
                            <input type="text" className="form-control" placeholder="Last name" />
                        </Form.Group>

                        <Form.Group className="mb-3" id="formGridEmail">
                            <label>Email address</label>
                            <input type="email" className="form-control" placeholder="Enter email" />
                        </Form.Group>

                        <Form.Group className="mb-3" id="formGridPassword">
                            <label>Password</label>
                            <input type="password" className="form-control" placeholder="Enter password" />
                        </Form.Group>

                        <button type="submit" className="btn btn-dark btn-block">Sign Up</button>
                        <p className="forgot-password text-right">
                            Already registered <a href="/">sign in?</a>
                        </p>
                    </Form>
                </div>
            </div>

        );
    }
}