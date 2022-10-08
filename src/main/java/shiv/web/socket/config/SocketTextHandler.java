package shiv.web.socket.config;

import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Date;

@Component
public class SocketTextHandler extends TextWebSocketHandler {
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        System.out.println("message from client-"+payload);
        JSONObject jsonObject = new JSONObject(payload);
        System.out.println("json-"+jsonObject);
        jsonObject.put("message","Hello data");
        jsonObject.put("time",new Date());
        session.sendMessage(new TextMessage(jsonObject.toString()));
    }
}
