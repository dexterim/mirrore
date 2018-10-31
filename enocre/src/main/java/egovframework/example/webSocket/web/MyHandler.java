package egovframework.example.webSocket.web;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class MyHandler extends TextWebSocketHandler implements InitializingBean {
	private static Logger logger = LoggerFactory.getLogger(MyHandler.class);
	private List<WebSocketSession> sessionList = new ArrayList<WebSocketSession>();
	  
	public MyHandler(){
		super();
		logger.info("create SocketHandler instance!");
		logger.info("sessionList:"+sessionList);
		logger.info("--------------------------");
	}
	public List<WebSocketSession> getSocketSessionList(){
		return sessionList;
	}
	// 클라이언트와 연결 이후에 실행되는 메서드
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		super.afterConnectionEstablished(session);
		  
		sessionList.add(session);
		logger.info("{} 연결됨", session.getId());
		logger.info("sessionList:"+sessionList);
		logger.info("--------------------------");
		
	}
	 
	// 클라이언트가 서버로 메시지를 전송했을 때 실행되는 메서드
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		logger.info("{}로 부터 {} 받음", session.getId(), message.getPayload());
		logger.info("sessionList:"+sessionList);
		logger.info("--------------------------");
		for (WebSocketSession sess : sessionList) {
			sess.sendMessage(
				new TextMessage(message.getPayload())
			);
		}
	}
	// 클라이언트와 연결을 끊었을 때 실행되는 메소드
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		  super.afterConnectionClosed(session, status);
		  
		  sessionList.remove(session);
		  logger.info("{} 연결 끊김", session.getId());
	}
	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
	      logger.error("web socket error!", exception);
	}
	@Override
	public boolean supportsPartialMessages() {
	      logger.info("call method!");
	      logger.info("sessionList:"+sessionList);
	      logger.info("--------------------------");
	      return super.supportsPartialMessages();
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		
	}
}