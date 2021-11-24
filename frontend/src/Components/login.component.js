import React, {Component} from "react";

export default class Login extends Component {
    render() {
        return (
            <form>
                <h3>Sign In</h3>

                <div className="form-group">
                    <label>Email address</label>
                    <input type="email" className="form-control" placeholder="Enter email"/>
                </div>

                <div className="form-group">
                    <label>Password</label>
                    <input type="password" className="form-control" placeholder="Enter password"/>
                </div>
                <p>
                <div className="form-group">
                    <div className="custom-control custom-checkbox">
                        <input type="checkbox" className="custom-control-input" id="customCheck1"/>
                            <label className="custom-control-label" htmlFor="customCheck1">&nbsp; Remember me</label>
                    </div>
                </div>
                </p>
                <div>
                    <p>
                        <button type="submit" className="btn btn-dark btn-block">Submit</button>
                    </p>
                </div>
            </form>
        );
    }
}