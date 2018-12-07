package egovframework.example.memberWeb.web;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.socket.WebSocketSession;

import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketExtension;
import com.neovisionaries.ws.client.WebSocketFactory;

import egovframework.example.cmmn.JsonUtil;
import egovframework.example.memberWeb.service.MemberWebService;
import egovframework.example.memberWeb.service.SessionVO;
import egovframework.example.nfcMirrorLogin.service.NfcMirrorLoginService;
import egovframework.example.nfcMirrorLogin.web.NfcMirrorLogin;
import egovframework.example.webSocket.web.MyHandler;
import egovframework.example.webSocket.web.WebSocket;
import egovframework.rte.psl.dataaccess.util.EgovMap;

import org.hsqldb.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class MemberWebController {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Resource(name="memberWebService")
	private MemberWebService memberWebService;
	
	@Resource(name="nfcMirrorLoginService")
	private NfcMirrorLoginService nfcMirrorLoginService;

	
	@RequestMapping("register.do")
	public void userRegister(@RequestBody String reqParam,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String resultStr;
		Map<String,Object> hashMap;
		hashMap = JsonUtil.JsonToMap(reqParam);
		
		System.out.println(hashMap+"\n:map으로 변환_회원정보!");
		
		String insertSuccessId = memberWebService.insertRegisterServiceMap(hashMap);
		System.out.println("insert 후 pk : " + insertSuccessId);
		
		memberWebService.insertRegisterSettingServiceMap(hashMap);
		
		if(insertSuccessId == null) {
			resultStr = "error";
		}else {
			if(insertSuccessId.equals(hashMap.get("id"))){
				resultStr = "success";
			} else {
				resultStr = "please reenter your information";
			}
		}
		response.setCharacterEncoding("utf-8");
		
		PrintWriter print = response.getWriter();
		
		//print.write(resultStr);
		print.print(resultStr);
		print.flush();
		//return "redirect:/enocreWeb.do";
	}
	@RequestMapping("id_overlap_check.do")
	public void idOverlapCheck(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String id, checkId, resultStr = null;
		id = request.getParameter("id");
		System.out.println("overlap_id_request : "+id);
		checkId = memberWebService.selectIdOverlapCheckService(id);
		if(id.equals(checkId)) {
			resultStr = "exists";
		}
		response.setCharacterEncoding("utf-8");
		
		PrintWriter print = response.getWriter();
		print.write(resultStr);
		print.flush();
	}
	
	@RequestMapping("mirror_id_check.do")
	public void mirrorIdCheck(@RequestBody String reqParam,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String mirror_id, resultStr = null;
		Map<String,Object> hashMap;
		hashMap = JsonUtil.JsonToMap(reqParam);
		
		mirror_id = (String)hashMap.get("mirror_id");
		System.out.println("미러 아이디 존재 여부 확인: "+mirror_id);
		
		int limit_member = memberWebService.selectMirrorIdService(mirror_id);
		System.out.println("limit_member : " + limit_member);
		HashMap<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("limit_member", limit_member);
		
		if(limit_member == 0) {
			resultStr = "didn't assigned MIRROR_ID";
		}else {	
			resultStr = "success";
		}
		resultMap.put("resultStr", resultStr);
		
		response.setCharacterEncoding("utf-8");
		
		PrintWriter print = response.getWriter();
			
		String resultMapToJson = JsonUtil.HashMapToJson(resultMap);
		
		print.write(resultMapToJson);
		print.print(resultMapToJson);
		print.flush();
				
		//return "redirect:/enocreWeb.do";
	}
	
	@RequestMapping("login.do")
	public void userLoginCheck(@RequestBody String reqParam,
			HttpServletRequest request,
			HttpServletResponse response,
			ModelMap model) throws Exception{
		String id, pw;
		Map<String,Object> hashMap;
		hashMap = JsonUtil.JsonToMap(reqParam);
		
		System.out.println(hashMap.get("id")+":id_object타입의 형변환 필요!");
		System.out.println(hashMap.get("pw")+":pw_object타입의 형변환 필요!");
		
		id = (String)hashMap.get("id");
		pw = (String)hashMap.get("pw");
		
		String checkPw = memberWebService.selectMemberLoginServiceList(id);
		System.out.println("해당 id: "+ id +" 의 비밀번호 확인: "+ checkPw);
		HashMap<String,Object> resultMap = new HashMap<String,Object>();
		if(checkPw == null) {
			resultMap.put("result", "id_missing");
			resultMap.put("memberList", null);
		}else {
			if(!checkPw.equals(pw)) {
				resultMap.put("result", "pw_missing");
				resultMap.put("memberList", null);
			} else {
				List<EgovMap> memberList = memberWebService.selectMemberWebServiceList(id);
				for (int i = 0; i<memberList.size();i++) {
					System.out.println("member:"+memberList.get(i));
				}
				resultMap.put("result", "success");
				resultMap.put("memberList", memberList);
				String userId = memberList.get(0).get("id").toString();
				String userName = memberList.get(0).get("name").toString();
				System.out.println("member_Id : "+userId);
				
			}
			response.setCharacterEncoding("utf-8");
			
			PrintWriter print = response.getWriter();
				
			String resultMapToJson = JsonUtil.HashMapToJson(resultMap);
			
			print.write(resultMapToJson);
			print.print(resultMapToJson);
			print.flush();
			
			System.out.println("resultMapToJson: "+resultMapToJson);
		}
	}
	@RequestMapping(value = "get_member_info.do")
	public void selectMemberInfo(@RequestBody String reqParam,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String param = "";
		Map<String, Object> paramMap = JsonUtil.JsonToMap(reqParam);
		
		param = (String)paramMap.get("id_session_value");
		
		List<EgovMap> memberInfoList = memberWebService.selectMemberWebServiceList(param);
		
		for (int i = 0; i<memberInfoList.size();i++) {
			System.out.println("memberInfoList:"+memberInfoList.get(i));
		}
		HashMap<String,Object> resultMap = new HashMap<String,Object>();
		
		resultMap.put("result", "success");
		resultMap.put("memberInfoList", memberInfoList);
		
		response.setCharacterEncoding("utf-8");
		
		PrintWriter out = response.getWriter();
		
		String resultMapToJson = JsonUtil.HashMapToJson(resultMap);
		
		out.write(resultMapToJson);
	}
	@RequestMapping("logout.do")
	public void userLogout(@RequestBody String reqParam,
			HttpServletRequest request,
			HttpServletResponse response,
			ModelMap model) throws Exception{
		String key_skill ="";
		String resultStr = "";
		String mirror_id = "";
		Map<String,Object> hashMap;
		hashMap = JsonUtil.JsonToMap(reqParam);
		
		mirror_id = (String)hashMap.get("mirror_id");
		key_skill = (String)hashMap.get("key_skill");
		System.out.println("미러 아이디: "+mirror_id);
		System.out.println("로그아웃: "+key_skill);
		
		resultStr = "success";
			
		response.setCharacterEncoding("utf-8");
		
		PrintWriter print = response.getWriter();
		
		print.print(resultStr);
		print.flush();
		
		log.info("logout.do");
	}
	
	@RequestMapping(value = "setting.do")
	public void selectMemberSetting(@RequestBody String reqParam,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String param = "";
		Map<String, Object> paramMap = JsonUtil.JsonToMap(reqParam);
		
		param = (String)paramMap.get("id_session_value");
		
		List<EgovMap> memberSettingList = memberWebService.selectSettingServiceList(param);
		
		for (int i = 0; i<memberSettingList.size();i++) {
			System.out.println("memberSetting:"+memberSettingList.get(i));
		}
		HashMap<String,Object> resultMap = new HashMap<String,Object>();
		
		resultMap.put("result", "success");
		resultMap.put("memberSettingList", memberSettingList);
		
		response.setCharacterEncoding("utf-8");
		
		PrintWriter out = response.getWriter();
		
		String resultMapToJson = JsonUtil.HashMapToJson(resultMap);
		
		out.write(resultMapToJson);
	}
	@RequestMapping("update_member.do")
	public void updateMember(
			@RequestBody String reqParam,
			HttpServletRequest request,
			HttpServletResponse response,
			ModelMap model) throws Exception{
		
			String member_key, member_value_en, member_value, member_id, mirror_id, result = null;
			Map<String,Object> hashMap;
			hashMap = JsonUtil.JsonToMap(reqParam);
			
			member_key = (String)hashMap.get("member_key");
			member_value_en = (String)hashMap.get("member_value_en");
			member_value = (String)hashMap.get("member_value");
			member_id = (String)hashMap.get("member_id");
			
			System.out.println("update_column : "+member_key);
			System.out.println("update_value_en : "+member_value_en);
			System.out.println("update_value : "+member_value);
			System.out.println("update_id : "+member_id);
					
			memberWebService.updateMember(hashMap);
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
					if(member_key.equals("weather_loc")) {
						System.out.println("weather 변경");
						ws.sendText(member_id+" update_member_weather_loc");
						
					}else if(member_key.equals("subway_loc")) {
						System.out.println("subway_loc 변경");
						ws.sendText(member_id+" update_member_subway_loc");
					}
					
				} catch (ArrayIndexOutOfBoundsException ae) {
					log.info("array 오류가 발생했습니다."+ae);
				} catch (NullPointerException ne) {
					log.info("null 오류가 발생했습니다."+ne);
				} catch (Exception e) {
					log.info("그 외 오류가 발생했습니다."+e);
				}
			}
			
			response.setCharacterEncoding("utf-8");
			
			PrintWriter print = response.getWriter();
			print.print(result);
			print.flush();
	
			log.debug("MemberWebController입니다.");
		
	}
	
	public static com.neovisionaries.ws.client.WebSocket connect() throws Exception
    {
        return new WebSocketFactory()
            .setConnectionTimeout(5000)
            .createSocket("ws://172.18.73.125:8081/enocre/websocket/echo.do")
            .addListener(new WebSocketAdapter() {
            })
            .addExtension(WebSocketExtension.PERMESSAGE_DEFLATE)
            .connect();
    }
	/*@RequestMapping("sessionMatch.do")
	public void sessionMatch(List<EgovMap> memberList) throws Exception {
		
		SessionVO.setS_weather_location(memberList.get(0).get("weather_loc").toString());
		SessionVO.setS_subway_location(memberList.get(0).get("subway_loc").toString());
		SessionVO.setS_id(memberList.get(0).get("id").toString());
		SessionVO.setS_name(memberList.get(0).get("name").toString());
	}*/
	
}