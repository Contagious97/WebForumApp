import React, {Component} from "react";
import {
    Card, Col,
    Container,
    Form,
    ListGroup,
    Row,
    Toast,
    ToastContainer
} from "react-bootstrap";

export default class Feed extends Component {
    render() {
        return (

            <Container className={"feedContainer"}>

                <div>
                    <Card className={"card-image"}>
                        <Card.Header>User Information</Card.Header>
                        <ListGroup variant="flush">
                            <ListGroup.Item>First: Robin</ListGroup.Item>
                            <ListGroup.Item>Last: Jamsahar</ListGroup.Item>
                            <ListGroup.Item>Username: Grobblin</ListGroup.Item>
                        </ListGroup>
                    </Card>
                </div>

                    <Form.Group className={"message-view"} controlId="exampleForm.ControlTextarea1">
                        <Form.Control as="textarea" placeholder={"What's happening?"} rows={3}/>
                        <button type="submit" className="btn btn-dark btn-block flex-end">Submit</button>
                    </Form.Group>
                <ToastContainer>
                    <Toast>
                        <Toast.Header>
                            <img src="holder.js/20x20?text=%20" className="rounded me-2" alt=""/>
                            <strong className="me-auto">Grobblin</strong>
                            <small className="text-muted">just now</small>
                        </Toast.Header>
                        <Toast.Body>See? Just like this.</Toast.Body>
                    </Toast>
                    <Toast>
                        <Toast.Header>
                            <img src="holder.js/20x20?text=%20" className="rounded me-2" alt=""/>
                            <strong className="me-auto">Grobblin</strong>
                            <small className="text-muted">2 seconds ago</small>
                        </Toast.Header>
                        <Toast.Body>Heads up, toasts will stack automatically</Toast.Body>
                    </Toast>
                    <Toast>
                        <Toast.Header>
                            <img src="holder.js/20x20?text=%20" className="rounded me-2" alt=""/>
                            <strong className="me-auto">Grobblin</strong>
                            <small className="text-muted">2 seconds ago</small>
                        </Toast.Header>
                        <Toast.Body>Heads up, toasts will stack automatically</Toast.Body>
                    </Toast>
                </ToastContainer>
            </Container>
        );
    }
}