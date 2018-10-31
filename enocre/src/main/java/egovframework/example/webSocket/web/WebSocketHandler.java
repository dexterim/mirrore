package egovframework.example.webSocket.web;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class WebSocketHandler extends TextWebSocketHandler {

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    	System.out.println("handler진입");
        for (int i = 0; i < 10; i++) {
            session.sendMessage(new TextMessage(message.getPayload()));
            Thread.sleep(1000);
        }
    }
}