package egovframework.example.webSocket.web;

import org.springframework.stereotype.Controller;

import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketExtension;
import com.neovisionaries.ws.client.WebSocketFactory;

@Controller
public class WebSocket {

	public static com.neovisionaries.ws.client.WebSocket connect() throws Exception
    {
        return new WebSocketFactory()
            .setConnectionTimeout(5000)
            .createSocket("ws://172.18.81.57:8081/enocre/websocket/echo.do")
            .addListener(new WebSocketAdapter() {
                // A text message arrived from the server.
                public void onTextMessage(WebSocket websocket, String message) {
                    System.out.println(message);
                }
            })
            .addExtension(WebSocketExtension.PERMESSAGE_DEFLATE)
            .connect();
    }
}