import React, {Component} from "react";
import axios from "axios";
import {LOGIN} from "../Constants/Constants";
import {Route, Switch} from "react-router-dom";
import SignUp from "./signup.component";
import {Form} from "react-bootstrap";

export default class Login extends Component {

    constructor(props) {
        super(props);
        this.state = {username:'', password:''}
        this.handleChange = this.handleChange.bind(this)
        this.handleSubmit = this.handleSubmit.bind(this)
    }

    handleChange(event){
        console.log(event.target.name)
        this.setState(({[event.target.name]: event.target.value}))
    }
    handleSubmit(event){
        let axiosBody = {userName: this.state.username,password: this.state.password}
        console.log(axiosBody)
        let axiosConfig = {headers:{'Content-Type':'application/json'}}
        let a = axios.post(LOGIN,axiosBody,
            axiosConfig).then(
            result => {
                console.log("Result: " + result)
                console.log("Result data: " + result.data)
                console.log("Result data of data: " + result.data.username)
                localStorage.setItem('user',JSON.stringify(result.data));
            }
        )
        event.preventDefault();
    }

    render() {
        return (
            <div className="auth-wrapper">
                <div className="auth-inner">
                    <form onSubmit={this.handleSubmit}>
                        <h3>Sign In</h3>

                        <div className="form-group">
                            <label>Username</label>
                            <input type="username" name={"username"} value={this.state.username} onChange={this.handleChange} className="form-control" placeholder="Enter username"/>
                            <form className="text-muted">
                                We'll never share your email with anyone else.
                            </form>
                        </div>

                        <div className="form-group margin">
                            <label>Password</label>
                            <input type="password" name={"password"} value={this.state.password} onChange={this.handleChange} className="form-control" placeholder="Enter password"/>
                        </div>

                        <div className="form-group">
                            <div className="custom-control custom-checkbox">
                                <input type="checkbox" className="custom-control-input" id="customCheck1"/>
                                <label className="custom-control-label" htmlFor="customCheck1">&nbsp; Remember me</label>
                            </div>
                        </div>

                        <div>

                            <button type="submit" className="btn btn-dark btn-block margin">Submit</button>

                        </div>
                    </form>
                </div>
            </div>
        );
    }
}