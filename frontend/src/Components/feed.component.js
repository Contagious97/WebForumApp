import React, {Component, useEffect, useState} from "react";
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


export default function Feed() {

    //this.state = {username:'',name:'',content:''};
    const [username, setUsername] = useState();
    const [name, setName] = useState()
    const [content,setContent] = useState();
    const location = useLocation();
    let logs=[];
    const {userParam} = useParams();
    console.log(userParam)
    useEffect(() =>{

        const user = localStorage.getItem('user');
        if (userParam){
            setUsername(userParam)
            console.log(username)

        }
        else if (user != null){
            const foundUser = JSON.parse(user);
            console.log("User: "+ foundUser.username)
            setUsername(foundUser.username)
            setName(foundUser.name)
        }
        logs = getLogs();
    },[location.pathname])

    async function getLogs(){
        let axiosConfig = {headers:{'Content-Type':'application/json'}}
        let url = LOGS+"/" + (username);
        let logs;
        console.log("url" + url)
        console.log("username: " + username)
        await axios.get(LOGS+"/"+username,
            axiosConfig).then(
            result => {
                console.log(result)
                console.log(result.data)
                logs = result.data;
                console.log("Logs" + logs)
                return logs;
                //this.setState(({logs:result.data}))
            }).catch(function (error) {
                console.log(error.response.data)
        })


    }

    async function handleSubmit(event){
        let axiosBody = {content: content,userName: username}
        console.log(axiosBody)
        let axiosConfig = {headers:{'Content-Type':'application/json'}}
        let a = await axios.post(LOGS,axiosBody,
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


        return (
            <Container className={"feedContainer"}>
                <div>
                    <Card className={"card-image"}>
                        <Card.Header>User Information</Card.Header>
                        <ListGroup variant="flush">
                            <ListGroup.Item>Name: {name}</ListGroup.Item>
                            <ListGroup.Item>Username: {username}</ListGroup.Item>
                        </ListGroup>
                    </Card>
                </div>
                <div>
                    <Form onSubmit={handleSubmit}>
                        <Form.Group className={"message-view"} controlId="exampleForm.ControlTextarea1">
                            <Form.Control value={content} onChange={e => setContent(e.target.value)} as="textarea" placeholder={"What's happening?"} rows={3}/>
                            <button type="submit" className="btn btn-dark btn-block flex-end">Submit</button>
                        </Form.Group>
                    </Form>

                </div>



            </Container>
        );

}