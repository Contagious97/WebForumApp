import React, {Component} from "react";
import {Container, Dropdown, FormControl, InputGroup} from "react-bootstrap";
import {USERS} from "../Constants/Constants";
import axios from "axios";
import DropdownMenu from "react-bootstrap/DropdownMenu";


export default class DMs extends Component {
    users = [];
     getUsers(){
         console.log("Is inside Direct Messages");
         const users = localStorage.getItem('users');

         let foundUsers = JSON.parse(users)
         console.log("All users: " + foundUsers.toString());
         let axiosConfig = {headers:{'Content-Type':'application/json'}}
         console.log("url" + USERS)
        let a = axios.get(USERS,
            axiosConfig).then(
            result => {
                console.log("Direct message:" + result)
                console.log(result.data)
                this.users.push(result);
                console.log("Inside Direct Messages" + JSON.stringify(this.users))
                // logs = result.data;
                // console.log(logs)
                //this.setState(({logs:result.data}))
            }).catch(function (error){
            console.log(error.response.data)
        })
    }


    render() {
        return (
            <Container>
                <div className={"topLeft"}>
                    {this.users.map((e) =>{
                        return(
                    <DropdownMenu data={e.users} className={"space"} id="dropdown-basic-button " title="Friends" variant="light">
                        <Dropdown.Item>Action</Dropdown.Item>
                    </DropdownMenu>
                        );
                    })}
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