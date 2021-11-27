import React, {Component, useEffect, useState} from "react";
import axios from "axios";
import {LOGS} from "../Constants/Constants";
import {Toast, ToastContainer} from "react-bootstrap";
import {useParams} from "react-router-dom";


export default class Logout extends React.Component{

    constructor(props) {
        super(props);

        localStorage.clear();
        this.props.history.push('/login')

    }

    render() {
        return(
            <div></div>
        )
    }

}