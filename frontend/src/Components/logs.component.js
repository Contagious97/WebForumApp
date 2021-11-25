import React, {Component} from "react";
import axios from "axios";
import {LOGS} from "../Constants/Constants";
import {Toast, ToastContainer} from "react-bootstrap";
import {useParams} from "react-router-dom";


export default class Logs extends Component{


    constructor(props) {
        console.log("props" + props.name)
        super(props);
        const user = localStorage.getItem('user');
        let username;
        if (user){
            const foundUser = JSON.parse(user);
            console.log("User: "+ foundUser.username)
            username = foundUser.username;
        }

        this.state ={logs: [],username:username}
        this.componentDidMount = this.componentDidMount.bind(this)
    }

    componentDidMount() {

        this.updateData();
    }

    updateData(){
        let axiosConfig = {headers:{'Content-Type':'application/json'}}
        let url = LOGS+"/" + (this.state.username);
        console.log("url" + url)
        console.log("username: " + this.state.username)
        let a = axios.get(LOGS+"/"+this.state.username,
            axiosConfig).then(
            result => {
                console.log(result)
                console.log(result.data)
                this.setState(({logs:result.data}))
            })
    }

    render() {
        return(
            <ToastContainer>
                {this.state.logs.map((e) =>{
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


}