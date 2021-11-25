import React, {Component} from "react";
import {
    Card, Col,
    Container,
    Form,
    ListGroup,
    Row,
    Toast,
    ToastContainer
} from "react-bootstrap";
import axios from "axios";
import {LOGIN, LOGS} from "../Constants/Constants";
import Logs from "./logs.component";
import {useLocation, useParams} from "react-router-dom";

export default class Feed extends Component {


    constructor(props) {
        super(props);
        this.state = {username:'',name:'',content:''}
        this.componentDidMount = this.componentDidMount.bind(this)
        this.handleSubmit = this.handleSubmit.bind(this)
    }

    componentDidMount() {
        const user = localStorage.getItem('user');
        if (user){
            const foundUser = JSON.parse(user);
            console.log("User: "+ foundUser.username)
            this.setState(({username:foundUser.username,name:foundUser.name}),()=>
            {console.log(this.state.name)
            console.log(this.state.username)} )
        }


    }

    handleSubmit(event){
        let axiosBody = {content: this.state.content,userName: this.state.username}
        console.log(axiosBody)
        let axiosConfig = {headers:{'Content-Type':'application/json'}}
        let a = axios.post(LOGS,axiosBody,
            axiosConfig).then(
            result => {
                console.log("Result: " + result)
                console.log("Result data: " + result.data)
                console.log("Result data of data: " + result.data.content)
                //localStorage.setItem('user',JSON.stringify(result.data));
                window.location.reload();
            }
        ).catch(function (error){
            if (error.response){
                console.log(error.response.data)
            }
        })
        event.preventDefault();
    }

    render() {
        return (
            <Container className={"feedContainer"}>
                <div>
                    <Card className={"card-image"}>
                        <Card.Header>User Information</Card.Header>
                        <ListGroup variant="flush">
                            <ListGroup.Item>Name: {this.state.name}</ListGroup.Item>
                            <ListGroup.Item>Username: {this.state.username}</ListGroup.Item>
                        </ListGroup>
                    </Card>
                </div>
                <div>
                    <Form onSubmit={this.handleSubmit}>
                        <Form.Group className={"message-view"} controlId="exampleForm.ControlTextarea1">
                            <Form.Control value={this.state.content} onChange={e =>this.setState({content:e.target.value})} as="textarea" placeholder={"What's happening?"} rows={3}/>
                            <button type="submit" className="btn btn-dark btn-block flex-end">Submit</button>
                        </Form.Group>
                    </Form>

                </div>

                <Logs/>

            </Container>
        );
    }
}