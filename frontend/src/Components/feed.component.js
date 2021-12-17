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
import faker from 'faker';

import {Line} from 'react-chartjs-2';
import {Bar} from 'react-chartjs-2';

import axios from "axios";
import {LOGIN, LOGS} from "../Constants/Constants";
import Logs from "./logs.component";
import {renderLogs} from "./logs.component";
import {useLocation, useParams} from "react-router-dom";
import {Chart, registerables} from 'chart.js';
import jQuery from "jquery"
import $ from "jquery"
import data from "bootstrap/js/src/dom/data";
Chart.register(...registerables);


export default function Feed() {


    //this.state = {username:'',name:'',content:''};
    //const [username, setUsername] = useState();
    //const [name, setName] = useState()
    const [isLoading, setLoading] = useState(true);
    const [content, setContent] = useState();
    const [logs, setLogs] = useState();
    const [username, setUsername] = useState();
    const [name, setName] = useState();
    const location = useLocation();


    const {userParam} = useParams();
    console.log(userParam)
    useEffect(() => {

        const user = localStorage.getItem('user');
        const foundUser = JSON.parse(user);


        if (userParam) {
            setUsername(userParam)
            console.log(username)

            if (user) {
                console.log("User: " + foundUser.username)
                setUsername(foundUser.username)
                //username = foundUser.username
                //setUsername(foundUser.username)
                //name = foundUser.name
                setName(foundUser.name)
                console.log(username)
            }

        } else {
            const foundUser = JSON.parse(user);
            console.log("User: " + foundUser.username)
            setUsername(foundUser.username)
            setName(foundUser.name)
        }

        if (userParam)
            getLogs(userParam).then(r => console.log(r))
        else getLogs(foundUser.username).then(r => console.log(r))
        //getLogs().then(r => console.log(r))

        console.log(logs)
    }, [])

    async function getLogs(username) {
        let axiosConfig = {headers: {'Content-Type': 'application/json'}}
        let url = LOGS + "/" + (username);
        //let logs;
        console.log("url" + url)
        console.log("username: " + username)
        await axios.get(LOGS + "/" + username,
            axiosConfig).then(
            result => {
                console.log(result)
                console.log(result.data)
                setLogs(result.data);
                console.log("Logs" + logs)
                setLoading(false);
                //return logs;
                //this.setState(({logs:result.data}))
            }).catch(function (error) {
            console.log(error.data)
        })
    }



    function renderTextBox() {

        const user = localStorage.getItem('user');


        let founduser = JSON.parse(user)
        console.log(founduser.username + username)
        if (!userParam) {
            return (
                <div>
                    <Form.Group className={"message-view"} controlId="exampleForm.ControlTextarea1">
                        <Form.Control value={content} onChange={e => setContent(e.target.value)} as="textarea"
                                      placeholder={"What's happening?"} rows={3}/>
                        <button type="submit" className="btn btn-dark btn-block">Submit</button>

                    </Form.Group>

                    {/*{chooseGraph()}*/}

                    {/*<Line options={options} data={data} type={'line'}/>;*/}
                </div>
            )
        } else {
            return (
                <div>hi</div>
            )
        }
    }
    //
    // function  chooseGraph(type){
    //     if (type=== 'line')
    //         return <Line type={'line'} data={data}/>
    //     if (type ==='bar')
    //         return <Bar type={'bar'} data={data}/>
    // }

    async function handleSubmit(event) {
        const user = JSON.parse(localStorage.getItem('user'));
        let axiosBody = {content: content, userName: user.username}
        console.log(axiosBody)
        let axiosConfig = {headers: {'Content-Type': 'application/json'}}
        let a = await axios.post(LOGS, axiosBody,
            axiosConfig).then(
            result => {
                console.log("Result: " + result)
                console.log("Result data: " + result.data)
                console.log("Result data of data: " + result.data.content)
                //localStorage.setItem('user',JSON.stringify(result.data));
                window.location.reload();
            }
        ).catch(function (error) {
            if (error.response) {
                console.log(error.response.data)
            }
        })
        event.preventDefault();
    }

    function Loading() {
        return (
            <h1>LOADING...</h1>
        )
    }

    function render() {
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
                        {renderTextBox(username, name)}
                    </Form>
                </div>


                {isLoading ? Loading() : renderLogs(logs)}

                {console.log(logs)}
                {console.log(username)}


            </Container>
        );
    }

    return render();
}
//
//
// var config = {
//     type: 'line',
//     data: {
//         labels: ["June", "July", "August", "September", "October", "November", "December"],
//         datasets: [{
//             label: "company1",
//             data: [1, 1, 2, 3, 4, 5, 6],
//             fill: false,
//             borderColor: "purple",
//             backgroundColor: "purple"
//         }, {
//             label: "company2",
//             data: [1, 2, 4, 8, 3, 2, 4],
//             fill: false,
//             borderColor: "green",
//             backgroundColor: "green"
//         }]
//     },
//     options: {
//         responsive: true,
//     }
// };
//
// var myChart;
//
// $("#line").click(function() {
//     change('line');
// });
//
// $("#bar").click(function() {
//     change('bar');
// });
//
// function change(newType) {
//     var ctx = document.getElementById("canvas").getContext("2d");
//
//     // Remove the old chart and all its event handles
//     if (myChart) {
//         myChart.destroy();
//     }
//
//     // Chart.js modifies the object you pass in. Pass a copy of the object so we can use the original object later
//     var temp = jQuery.extend(true, {}, config);
//     temp.type = newType;
//     myChart = new Chart(ctx, temp);
// };