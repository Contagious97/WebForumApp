import React, {useEffect} from "react";
import { CONNECTING } from "ws";
import './style.css'
//import WebSocket from 'isomorphic-ws';


const ws = new WebSocket('ws://localhost:6969');

// ws.onopen = e => {
//     ws.send(JSON.stringify({
//         event: "verify",
//         data: foundUser.username
//     }))
// }
class Board extends React.Component {

    
  constructor(props) {
        super(props);
    }

    componentDidMount() {

        
        this.drawOnCanvas();

    }

    drawOnCanvas() {
      //let canvas = document.getElementById("board");
      const canvasL = document.getElementsByClassName("board")
      const canvas = canvasL[0];

      console.log(canvasL)
      console.log(canvas)
        const ctx = canvas.getContext('2d');

        const sketch = document.querySelector('#sketch');
        const sketch_style = getComputedStyle(sketch);
        canvas.width = parseInt(sketch_style.getPropertyValue('width'));
        canvas.height = parseInt(sketch_style.getPropertyValue('height'));

        const mouse = {x: 0, y: 0};
        const last_mouse = {x: 0, y: 0};

        /* Mouse Capturing Work */
        canvas.addEventListener('mousemove', function (e) {
            last_mouse.x = mouse.x;
            last_mouse.y = mouse.y;

            mouse.x = e.x - canvas.getBoundingClientRect().x;
            mouse.y = e.y - canvas.getBoundingClientRect().y;
            let fromX = last_mouse.x;
            let fromY = last_mouse.y;
            let toX = mouse.x;
            let toY = mouse.y;
            if(e.buttons !== 1)
            return
            sendEvent("draw", {fromX, fromY, toX, toY});
        }, false);


        /* Drawing on Paint App */
        ctx.lineWidth = 5;
        ctx.lineJoin = 'round';
        ctx.lineCap = 'round';
        ctx.strokeStyle = 'blue';

        canvas.addEventListener('mousedown', function (e) {
            canvas.addEventListener('mousemove', onPaint, false);
            let fromX = mouse.x;
            let fromY = mouse.y;
    
            console.log("From: " + fromX + "")
            sendEvent("draw", {fromX, fromY, fromX, fromY});
        }, false);

        canvas.addEventListener('mouseup', function () {
            canvas.removeEventListener('mousemove', onPaint, false);
            let fromX = last_mouse.x;
            let fromY = last_mouse.y;
            let toX = mouse.x;
            let toY = mouse.y;
            sendEvent("draw", {fromX, fromY, toX, toY});
        }, false);

        var root = this;
        var eventHandlers = {
            "draw": this.draw,
        }
        const foundUser = JSON.parse(localStorage.getItem('user'));
        console.log("Found user: " + foundUser.username)




        ws.addEventListener('open', (event) => {
        ws.send(JSON.stringify({
            event: "verify",
                    data: foundUser.username
                }));
        });

        ws.onmessage = m => {
        try {
        let data = JSON.parse(m.data)
        let event = data.event
        delete data.event

        
        console.log(eventHandlers[event]);
        console.log("Data: " + JSON.stringify(data))
        if(eventHandlers[event] != undefined){
            eventHandlers[event](data.fromX,data.fromY,data.toX,data.toY)
        } 
        else console.error("Unhandled event: "+event)

        } catch (error) {
        console.error("Could not process ws message:", error)
        }
        }


        const onPaint = ()=> {
          this.draw(last_mouse.x,last_mouse.y,mouse.x,mouse.y)
          //ws.send("")

            /*if (root.timeout !== undefined) clearTimeout(root.timeout)
            root.timeout = setTimeout(function(){
                var base64ImageData = canvas.toDataUrl("image/png")
            },1000)*/
        };
        const sendEvent = (event, data)=> {
            data["event"] = event
            if(!ws.CONNECTING)
                ws.send(JSON.stringify(data))
        }
    }

    draw(fromX, fromY, toX, toY) {
        // if(ctx === null || canvas == null){
        //     return;
        // }
        const canvasL = document.getElementsByClassName("board")
      const canvas = canvasL[0];
      const ctx = canvas.getContext('2d');
      console.log("In draw: ctx: " + ctx + " canvas + " + canvas);
      console.log("Fromx: " + fromX + " fromY: " + fromY)
      ctx.beginPath();
      ctx.moveTo(fromX, fromY);
      ctx.lineTo(toX, toY);
      ctx.closePath();
      ctx.stroke();
      

      let image = canvas.toDataURL("image/png");
      //this.sendEvent("draw", {fromX, fromY, toX, toY});
      //console.log(image)
    }

    


    render() {
        return (
            <div className={"sketch"} id={"sketch"}>
                <canvas className={"board"} id={"board"}></canvas>
                <button onClick={this.saveImage}> Save Image</button>
            </div>
        )
    }
    saveImage(){
        const canvasL = document.getElementsByClassName("board")
        const canvas = canvasL[0];
        let image = canvas.toDataURL("image/png");
        const data = {event:"save", image: image}
        ws.send(JSON.stringify(data))
    }
}

export default Board
