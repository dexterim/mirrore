package egovframework.example.speak;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketExtension;
import com.neovisionaries.ws.client.WebSocketFactory;

import egovframework.example.cmmn.JsonUtil;
import egovframework.example.webSocket.web.WebSocket;

@Controller
public class SpeakWebController {
	
	private final Logger log = LoggerFactory.getLogger(getClass());

	@RequestMapping("speakWeb.do")
	public void initSpeakWeb(@RequestBody String reqParam,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			String speakText,resultStr = null;
			Map<String,Object> hashMap;
			hashMap = JsonUtil.JsonToMap(reqParam);
			
			speakText = hashMap.get("text").toString();
			System.out.println("음성 인식 텍스트 : " + speakText);
			
			try{	
				com.neovisionaries.ws.client.WebSocket ws = connect();
				ws.sendText("speakText:"+speakText);
				
				resultStr = "success";
			} catch (ArrayIndexOutOfBoundsException ae) {
				log.info("array 오류가 발생했습니다."+ae);
			} catch (NullPointerException ne) {
				log.info("null 오류가 발생했습니다."+ne);
			} catch (Exception e) {
				log.info("그 외 오류가 발생했습니다."+e);
			}
			
			response.setCharacterEncoding("utf-8");
			
			PrintWriter print = response.getWriter();
			print.print(resultStr);
			print.flush();
			
		} catch (ArrayIndexOutOfBoundsException ae) {
			log.info("array 오류가 발생했습니다."+ae);
		} catch (NullPointerException ne) {
			log.info("null 오류가 발생했습니다."+ne);
		} catch (Exception e) {
			log.info("그 외 오류가 발생했습니다."+e);
		} finally {
			log.debug("MemberWebController입니다.");
		}
		
	}
	
	public static com.neovisionaries.ws.client.WebSocket connect() throws Exception
    {
        return new WebSocketFactory()
            .setConnectionTimeout(5000)
            .createSocket("ws://172.18.65.185:8081/enocre/websocket/echo.do")
            .addListener(new WebSocketAdapter() {
            })
            .addExtension(WebSocketExtension.PERMESSAGE_DEFLATE)
            .connect();
    }
	
}

