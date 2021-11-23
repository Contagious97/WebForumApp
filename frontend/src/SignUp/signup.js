import 'bootstrap/dist/css/bootstrap.min.css';
import '../../node_modules/bootstrap/dist/css/bootstrap.min.css';
import {Container, Nav, Navbar} from "react-bootstrap";
import React from "react";
import SignUp from "../Components/signup.component";
// import '../index.css';
import './signup.css';


function signup(){
    return (
        <div className="signup">
            <Navbar className="navbar navbar-expand-lg navbar-light fixed-top" bg="dark" variant="dark">
                <Container>
                    <Navbar.Brand>Tumbler</Navbar.Brand>
                    <Nav className="me-auto">
                        <Nav.Link href="/">Home</Nav.Link>
                        <Nav.Link href="/sign-up">Sign Up</Nav.Link>
                    </Nav>
                </Container>
            </Navbar>
            <div className="auth-wrapper">
                <div className="auth-inner">
                    <SignUp/>
                </div>
            </div>
        </div>
    );
}

export default signup;