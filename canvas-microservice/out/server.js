var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
import { WebSocketServer } from 'ws';
import { connectToDatabase, saveImage } from '../out/database.js';
connectToDatabase();
const wss = new WebSocketServer({ port: 6969 });
const clients = new Map();
function sendEvent(event, data, client) {
    data["event"] = event;
    client.send(JSON.stringify(data));
}
let eventHandlers = new Map();
eventHandlers.set("mouse", (client, data) => {
    for (const otherClient of clients.keys()) {
        sendEvent("mouse", data, otherClient);
    }
});
eventHandlers.set("draw", (client, data) => {
    const convo = clients.get(client);
    for (const otherClient of clients.keys()) {
        sendEvent("draw", data, otherClient);
    }
});
eventHandlers.set("save", (client, data) => __awaiter(void 0, void 0, void 0, function* () {
    yield saveImage(data.image, clients.get(client));
}));
wss.on('connection', function connection(ws) {
    ws.on('message', function message(data) {
        let eventData = JSON.parse(data.toString());
        let event = eventData.event;
        if (event == 'verify') {
            clients.set(ws, eventData.data);
        }
        else {
            let handler = eventHandlers.get(event);
            if (handler)
                handler(ws, eventData);
            else
                console.error("Unhandled event: " + event);
        }
    });
    ws.send('something');
});
console.log("Started socket server");
//# sourceMappingURL=server.js.map