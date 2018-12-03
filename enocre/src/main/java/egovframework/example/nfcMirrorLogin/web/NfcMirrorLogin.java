package egovframework.example.nfcMirrorLogin.web;

import java.io.PrintWriter;
import java.util.HashMap;
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
import egovframework.example.enocreWeb.service.EnocreWebService;
import egovframework.example.memberWeb.service.MemberWebService;
import egovframework.example.nfcMirrorLogin.service.NfcMirrorLoginService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class NfcMirrorLogin{
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Resource(name="enocreWebService")
	private EnocreWebService enocreWebService;
	
	@Resource(name="nfcMirrorLoginService")
	private NfcMirrorLoginService nfcMirrorLoginService;
	
	@RequestMapping("nfc_mirror_login.do")
	public void userNfcMirrorLogin(@RequestBody String reqParam,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
			String resultStr="";
			Map<String,Object> hashMap;
			hashMap = JsonUtil.JsonToMap(reqParam);
			
			HashMap<String,Object> hashMap_mirror = new HashMap<String,Object>();
			hashMap_mirror.put("setting_key", "now_condition");
			hashMap_mirror.put("setting_id", hashMap.get("id"));
			hashMap_mirror.put("member_id", hashMap.get("id"));
			
			enocreWebService.updateOnSetting(hashMap_mirror);
			nfcMirrorLoginService.updateMemberMirror(hashMap_mirror);
			String mirror_login_user = nfcMirrorLoginService.selectMirrorLoginCheck();
			if(mirror_login_user.equals(hashMap.get("id"))){
				try{		
					com.neovisionaries.ws.client.WebSocket ws = connect();
					ws.sendText(mirror_login_user+" mirror_login");
				} catch (ArrayIndexOutOfBoundsException ae) {
					log.info("array 오류가 발생했습니다."+ae);
				} catch (NullPointerException ne) {
					log.info("null 오류가 발생했습니다."+ne);
				} catch (Exception e) {
					log.info("그 외 오류가 발생했습니다."+e);
				} finally {
					log.info("mirror_login.do");
				}
			}
			resultStr= "success";
			response.setCharacterEncoding("utf-8");
			
			PrintWriter print = response.getWriter();
			
			//print.write(resultStr);
			print.print(resultStr);
			print.flush();
			//return "redirect:/enocreWeb.do";
			
		
	}
	@RequestMapping("nfc_check.do")
	public String nfcCheck(String setting_id){
		String mirror_login_user = "", resultStr ="";
		try {
			mirror_login_user = nfcMirrorLoginService.selectMirrorLoginCheck(setting_id);
			System.out.println("mirrod_id_check_user :"+mirror_login_user);
			if(setting_id.equals(mirror_login_user) && setting_id != null && !setting_id.equals("")){
				resultStr = "validated_user";
			}else{
				resultStr = "invalidate_session";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultStr;
		 
	}
	
	public static com.neovisionaries.ws.client.WebSocket connect() throws Exception
    {
        return new WebSocketFactory()
            .setConnectionTimeout(5000)
            .createSocket("ws://172.18.93.154:8081/enocre/websocket/echo.do")
            .addListener(new WebSocketAdapter() {
            })
            .addExtension(WebSocketExtension.PERMESSAGE_DEFLATE)
            .connect();
    }
	
}