import { WebSocketServer } from 'ws';
const wss = new WebSocketServer({ port: 34343 });
let eventHandlers = new Map();
eventHandlers.set("mouse", (client, data) => {
    for (const otherClient of wss.clients) {
        if (otherClient != client)
            sendEvent("mouse", data, otherClient);
    }
});
eventHandlers.set("draw", (client, data) => {
    for (const otherClient of wss.clients) {
        if (otherClient != client)
            sendEvent("draw", data, otherClient);
    }
});
wss.on('connection', function connection(client) {
    client.on('message', function message(data) {
        try {
            let eventData = JSON.parse(data.toString());
            let event = eventData.event;
            delete eventData.event;
            if (event == "verify") {
                sendEvent("connected", {}, client);
            }
            else {
                let handler = eventHandlers.get(event);
                if (handler)
                    handler(client, eventData);
                else
                    console.error("Unhandled event: " + event);
            }
        }
        catch (error) {
            console.error("Error reading ws message, " + error);
        }
    });
});
function sendEvent(event, data, client) {
    data["event"] = event;
    client.send(JSON.stringify(data));
}
//# sourceMappingURL=server.js.map