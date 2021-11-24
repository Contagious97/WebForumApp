import React, { Component } from "react";
import axios from "axios";
import {CONFIG,SIGNUP} from "../Constants/Constants";
import {Route, Switch} from "react-router-dom";
import Login from "./login.component";


export default class SignUp extends Component {

    constructor(props) {
        super(props);
        this.state = {name:'', username:'', password:''}
        this.handleChange = this.handleChange.bind(this)
        this.handleSubmit = this.handleSubmit.bind(this)
    }

    handleChange(event){
        console.log(event.target.name)
        this.setState(({[event.target.name]: event.target.value}))
    }
    handleSubmit(event){
        let axiosBody = {name: this.state.name,userName: this.state.username,password: this.state.password}
        console.log(axiosBody)
        let axiosConfig = {headers:{'Content-Type':'application/json'}}
        let a = axios.post(SIGNUP,axiosBody,
            axiosConfig).then(
            result => console.log(result)
    )
        event.preventDefault();
    }

    render() {
        return (
            <div className="auth-wrapper">
                <div className="auth-inner">
                    <form onSubmit={this.handleSubmit}>
                        <h3>Sign Up</h3>

                        <div className="form-group">
                            <label>Name</label>
                            <input type="text" name={"name"} value={this.state.name} onChange={this.handleChange} className="form-control" placeholder="Full name" />
                        </div>

                        <div className="form-group">
                            <label>Username</label>
                            <input type="username" name={"username"} value={this.state.username} onChange={this.handleChange} className="form-control" placeholder="Enter username" />
                        </div>

                        <div className="form-group">
                            <label>Password</label>
                            <input type="password" name={"password"} value={this.state.password} onChange={this.handleChange} className="form-control" placeholder="Enter password" />
                        </div>

                        <button type="submit" className="btn btn-primary btn-block">Sign Up</button>
                        <p className="forgot-password text-right">
                            Already registered <a href="/">sign in?</a>
                        </p>
                    </form>
                </div>
            </div>

        );
    }
}