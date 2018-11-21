package egovframework.example.nfcMirrorLogin;

import java.io.PrintWriter;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketExtension;
import com.neovisionaries.ws.client.WebSocketFactory;

import egovframework.example.cmmn.JsonUtil;
import egovframework.example.memberWeb.service.MemberWebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class NfcMirrorLogin{
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Resource(name="memberWebService")
	private MemberWebService memberWebService;
	
	@RequestMapping("nfc_mirror_login.do")
	public void userNfcMirrorLogin(@RequestBody String reqParam,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String resultStr;
		Map<String,Object> hashMap;
		hashMap = JsonUtil.JsonToMap(reqParam);
		
		System.out.println(hashMap);
		try{		
			com.neovisionaries.ws.client.WebSocket ws = connect();
			ws.sendText(hashMap.get("id")+" mirror_login");
		} catch (ArrayIndexOutOfBoundsException ae) {
			log.info("array 오류가 발생했습니다."+ae);
		} catch (NullPointerException ne) {
			log.info("null 오류가 발생했습니다."+ne);
		} catch (Exception e) {
			log.info("그 외 오류가 발생했습니다."+e);
		} finally {
			log.info("mirror_login.do");
		}
		
		
		resultStr= "success";
		response.setCharacterEncoding("utf-8");
		
		PrintWriter print = response.getWriter();
		
		//print.write(resultStr);
		print.print(resultStr);
		print.flush();
		//return "redirect:/enocreWeb.do";
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