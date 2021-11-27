import React, {Component, useEffect, useState} from "react";
import axios from "axios";
import {LOGS} from "../Constants/Constants";
import {Toast, ToastContainer} from "react-bootstrap";
import {useLocation, useParams} from "react-router-dom";


export default function Logs(logs){

    const [username, setUsername] = useState();
    const {userParam} = useParams();
    const location = useLocation();

    console.log(logs)

    useEffect(()=>{

        console.log(userParam)

        const user = localStorage.getItem('user');
        let username;
        if (userParam){
            username = userParam;
        }
        else if (user){
            const foundUser = JSON.parse(user);
            console.log("User: "+ foundUser.username)
            username = foundUser.username;
        }
        if (username)
            setUsername(username)

        updateData();
    },[location.pathname])

    function updateData(){
        let axiosConfig = {headers:{'Content-Type':'application/json'}}
        let url = LOGS+"/" + (username);
        console.log("url" + url)
        console.log("username: " + username)
        let a = axios.get(LOGS+"/"+username,
            axiosConfig).then(
            result => {
                console.log(result)
                console.log(result.data)
                //logs = result.data;
                //this.setState(({logs:result.data}))
            })
    }

        return(
            <ToastContainer>
                {logs.map((e) =>{
                    return(
                        <Toast>
                            <Toast.Header key={e} closeButton={false}>
                                <img src="holder.js/20x20?text=%20" className="rounded me-2" alt=""/>
                                <strong className="me-auto"> <a href="/feed/username"> {e.username}</a> </strong>
                                <small className="text-muted">{e.date}</small>
                            </Toast.Header>
                            <Toast.Body>{e.content}</Toast.Body>
                        </Toast>
                );
                })}

            </ToastContainer>

    );



}