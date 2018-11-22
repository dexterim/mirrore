package egovframework.example.alarmWeb;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
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
import egovframework.example.nfcMirrorLogin.service.NfcMirrorLoginService;
import egovframework.example.nfcMirrorLogin.web.NfcMirrorLogin;
import egovframework.example.webSocket.web.WebSocket;

@Controller
public class AlarmWebController {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Resource(name="nfcMirrorLoginService")
	private NfcMirrorLoginService nfcMirrorLoginService;
	
	@RequestMapping("initAlarm.do")
	public void initAlarmWeb(@RequestBody String reqParam,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
			String member_id, mirror_id, result = "";
			Map<String,Object> hashMap;
			hashMap = JsonUtil.JsonToMap(reqParam);
			
			member_id = hashMap.get("member_id").toString();
			mirror_id = hashMap.get("mirror_id").toString();
			System.out.println("mirror_id:"+mirror_id);
			
			
			if(!mirror_id.equals("")){
				String member_check =nfcMirrorLoginService.selectMirrorLoginCheck(member_id);
				System.out.println("member_check:"+member_check);
				if(member_id.equals(member_check)){
					result= "validated_user";
				}
			}else{
				result="invalidate_session";
			}
			
			if(result.equals("validated_user")){
				
				try{		
					com.neovisionaries.ws.client.WebSocket ws = connect();
					ws.sendText("init_motion");
				} catch (ArrayIndexOutOfBoundsException ae) {
					log.info("array 오류가 발생했습니다."+ae);
				} catch (NullPointerException ne) {
					log.info("null 오류가 발생했습니다."+ne);
				} catch (Exception e) {
					log.info("그 외 오류가 발생했습니다."+e);
				} finally {
					log.info("alarm_init.do");
				}
			}
						
			response.setCharacterEncoding("utf-8");
			
			PrintWriter print = response.getWriter();
			print.print(result);
			print.flush();
	}

	@RequestMapping("alarmWeb.do")
	public void initMotionWeb(@RequestBody String reqParam,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
			String alarmValue, member_id, mirror_id, result = "";
			Map<String,Object> hashMap;
			hashMap = JsonUtil.JsonToMap(reqParam);
			
			alarmValue = hashMap.get("key_motion").toString();
			System.out.println("모션 value 텍스트 : " + alarmValue);
			
			member_id = hashMap.get("member_id").toString();
			mirror_id = hashMap.get("mirror_id").toString();
			System.out.println("mirror_id:"+mirror_id);
			
			
			if(!mirror_id.equals("")){
				String member_check =nfcMirrorLoginService.selectMirrorLoginCheck(member_id);
				System.out.println("member_check:"+member_check);
				if(member_id.equals(member_check)){
					result= "validated_user";
				}
			}else{
				result="invalidate_session";
			}
			
			if(result.equals("validated_user")){
				
				try{	
					com.neovisionaries.ws.client.WebSocket ws = connect();
					ws.sendText(alarmValue+" key_motion");
				} catch (ArrayIndexOutOfBoundsException ae) {
					log.info("array 오류가 발생했습니다."+ae);
				} catch (NullPointerException ne) {
					log.info("null 오류가 발생했습니다."+ne);
				} catch (Exception e) {
					log.info("그 외 오류가 발생했습니다."+e);
				} finally {
					log.info("alarm_motion.do");
				}
			}
						
			response.setCharacterEncoding("utf-8");
			
			PrintWriter print = response.getWriter();
			print.print(result);
			print.flush();
	}
	
	public static com.neovisionaries.ws.client.WebSocket connect() throws Exception
    {
        return new WebSocketFactory()
            .setConnectionTimeout(5000)
            .createSocket("ws://172.18.81.57:8081/enocre/websocket/echo.do")
            .addListener(new WebSocketAdapter() {
            })
            .addExtension(WebSocketExtension.PERMESSAGE_DEFLATE)
            .connect();
    }
	
}

