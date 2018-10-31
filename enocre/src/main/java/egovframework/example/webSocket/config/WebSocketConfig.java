/*package egovframework.example.webSocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Override  
    public void configureMessageBroker(MessageBrokerRegistry config) {
    	// broker의 대상 목적지에 대한 prefix를 설정한다.
    	// 쉽게 말해 server-side에서 client-side로 전송할 때 사용하는 경로의 prefix를 말한다.
    	// 출처 : https://github.com/devcken/webapp-boot
    	// 
    	// 그런데 컨트롤러에서 @SendTo("/greetings")로 하면 클라이언트로 응답이 전달되지 않음...
    	// 정확히 무슨 의미인지 모르겠음.
    	config.enableSimpleBroker("/topic");
    	
    	// Server의 controller에서 사용할 토픽의 prefix
    	// 이 설정으로 인해서 컨트롤러에서 @MessageMapping 할 때 "/app/hello" -> "/hello" 라고 선언해도 됨.
        config.setApplicationDestinationPrefixes("/app");
    }

    // Client가 접속 할 Server의 URL
    // 웹브라우저에서 connect 버튼 클릭시 다음과 같이 접속요청을 한다.
    // Request URL:http://127.0.0.1:8080/gs-guide-websocket/info?t=1488257937687
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/gs-guide-websocket").withSockJS();
    }

}*/