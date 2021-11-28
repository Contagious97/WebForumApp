import React, {Component, useEffect, useState} from "react";
import axios from "axios";
import {LOGS} from "../Constants/Constants";
import {Toast, ToastContainer} from "react-bootstrap";
import {useLocation, useParams} from "react-router-dom";


export default function(user) {


    let logs = [];
    console.log(user)
   // setUsername(user)
    updateData(user,logs).then(r => console.log(r));

    console.log(logs)
    useEffect(() => {

        console.log(user)

        //logs = logsParam
        console.log(logs)
//        setUsername(user)

        //updateData().then(r => console.log(r));
    }, [])
    return renderLogs(logs);
}
    async function updateData(user, logs){
        let axiosConfig = {headers:{'Content-Type':'application/json'}}
        let url = LOGS+"/" + (user);
        console.log("url" + url)
        console.log("username: " + user)
        let a = await axios.get(LOGS+"/"+user,
            axiosConfig).then(
            result => {
                console.log(result)
                console.log(result.data)
                logs = result.data;
                console.log(logs)
                //this.setState(({logs:result.data}))
            }).catch(function (error){
                console.log(error.response.data)
        })
    }

    export function renderLogs(logs){
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
