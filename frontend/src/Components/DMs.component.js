import {Container,Dropdown, DropdownButton, FormControl, InputGroup} from "react-bootstrap";
import {MESSAGES, USERS} from "../Constants/Constants";
import axios from "axios";
import {Component} from "react";
import React from "react";

export default class DMs extends Component {

    constructor(props) {
        super(props);

        this.state = {users:[],selectedUser:'',loading:true, content: ''}
        this.componentDidMount = this.componentDidMount.bind(this)
        this.updateData = this.updateData.bind(this)
    }
    componentDidMount() {

        this.updateData().then((r)=> console.log(r));
    }

    async updateData(){
        let axiosConfig = {headers:{'Content-Type':'application/json'}}
        await axios.get(USERS,
            axiosConfig).then(
            result => {
                console.log(result)
                console.log(result.data)
                this.setState(({users:result.data}))
                this.setState(({loading: false}))
            })
    }



    buttons(){
        if (this.state.loading){
            return <div> No friends</div>
        } else {
            return (
                <DropdownButton className={"space"} id="dropdown-basic-button " title="Friends" variant="light">

                    {this.state.users.map((user) =>{
                    return(
                            <Dropdown.Item as={"button"} onClick={() => this.setState(({selectedUser: user}))} href="">{user.username}</Dropdown.Item>
                    )
                })}
                </DropdownButton>
            )
        }
    }

    async sendMessage(){
        let axiosConfig = {headers:{'Content-Type':'application/json'}}

        let user = JSON.parse(localStorage.getItem('user'))

        let body = {receiverName: this.state.selectedUser, senderName: user.username,content: this.state.content}
        await axios.post(MESSAGES, body,
            axiosConfig).then(
            result => {
                console.log(result)
                console.log(result.data)
                alert("Message sent")
            }).catch(function (error) {
                console.log(error.response.data)
        })
    }

    render() {
        return (
            <Container className="auth-wrapper">
                <div className={"topLeft"}>
                        {this.buttons()}

                    <InputGroup className={"smaller-box"}>
                        <FormControl value={this.state.content} onChange={event => this.setState(({content:event.target.value}))} placeholder="Start a new message" as="textarea" aria-label="With textarea"/>

                    </InputGroup>

                </div>
                <button type="submit" onSubmit={this.sendMessage} className="btn btn-light btn-block space">Submit</button>

            </Container>

        );
    }
}