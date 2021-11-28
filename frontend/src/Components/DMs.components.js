import React, {Component} from "react";
import {Col, Container, Dropdown, DropdownButton, FormControl, InputGroup, Row} from "react-bootstrap";
import {LOGS} from "../Constants/Constants";
import axios from "axios";


export default class DMs extends Component {
    constructor(props) {
        super(props);
        console.log("props param: "+props.userParam)
        this.state = {username:'',name:'',content:''}
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
        return (
            <Container>
                <div className={"topLeft"}>
                    <DropdownButton className={"space"} id="dropdown-basic-button " title="Friends" variant="light">
                        <Dropdown.Item href="#/action-1">Action</Dropdown.Item>
                    </DropdownButton>

                    <InputGroup className={"smaller-box"}>
                        <FormControl placeholder="Start a new message" as="textarea" aria-label="With textarea"/>
                        placeholder="Username"
                    </InputGroup>

                </div>
                    <button type="submit" className="btn btn-light btn-block space">Submit</button>

            </Container>

        );
    }
}