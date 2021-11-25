import React, {Component} from "react";
import {Col, Container, Dropdown, DropdownButton, FormControl, InputGroup, Row} from "react-bootstrap";


export default class DMs extends Component {
    render() {
        return (
            <Container>
                <div className={"topLeft"}>
                    <DropdownButton className={"space"} id="dropdown-basic-button " title="Friends" variant="light">
                        <Dropdown.Item href="#/action-1">Action</Dropdown.Item>
                        <Dropdown.Item href="#/action-2">Another action</Dropdown.Item>
                        <Dropdown.Item href="#/action-3">Something else</Dropdown.Item>
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