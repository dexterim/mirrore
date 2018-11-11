package egovframework.example.enocreWeb.web;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.socket.WebSocketSession;

import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketExtension;

import egovframework.example.enocreWeb.service.EnocreWebService;
import egovframework.example.webSocket.web.MyHandler;
import egovframework.example.webSocket.web.WebSocket;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Controller
public class EnocreWebController {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Resource(name="enocreWebService")
	private EnocreWebService enocreWebService;

	@RequestMapping("android.do") 
	public void androidTest(HttpServletRequest request) { 
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		
		System.out.println("id:"+id);
		System.out.println("password:"+pwd);
		
		System.out.println("ANDROID로 접근했습니다."); 
		//return "redirect:/enocreWeb.do";
	}
	@RequestMapping("enocreWeb.do")
	public String initEnocreWeb(
			ModelMap model) throws Exception{
		List<EgovMap> memberList = enocreWebService.selectEnocreWebServiceList("miri@gmail.com");
    	String setting = enocreWebService.selectSettingService();
    	String userName = "";
		System.out.println(memberList);
		System.out.println(setting);
		
		model.addAttribute("memberList", memberList);
		model.addAttribute("setting", setting);
		for(int i = 0; i<memberList.size(); i++) {
			if(memberList.get(0).getValue(0).equals(setting)) {
				userName = (String) memberList.get(0).getValue(2);
			};
		}
		System.out.println("memberList_name : "+memberList.get(0).getValue(2));
		System.out.println("userName : "+userName);
		model.addAttribute("userName", userName);
		
//		response.setHeader("Refresh", "0; URL=" +request.getContextPath() + "/enocreWeb.do");
		
		return "ui.tiles";
	}
	@RequestMapping("refresh.do")
	public String refresh() throws Exception{
		
		return "redirect:/enocreWeb.do";
	}
	@RequestMapping("update_setting.do")
	public void updateSetting(
			HttpServletRequest request,
			HttpServletResponse response,
			ModelMap model) throws Exception{
		
		try{			
			com.neovisionaries.ws.client.WebSocket ws = connect();
			ws.sendText("java_client");
			
		} catch (ArrayIndexOutOfBoundsException ae) {
			log.info("array 오류가 발생했습니다."+ae);
		} catch (NullPointerException ne) {
			log.info("null 오류가 발생했습니다."+ne);
		} catch (Exception e) {
			log.info("그 외 오류가 발생했습니다."+e);
		} finally {
			String setting_key, setting_value, setting_id, resultStr = null;
			setting_key = request.getParameter("setting_key");
			setting_value = request.getParameter("setting_value");
			setting_id = request.getParameter("setting_id");
			
			System.out.println("update_column : "+setting_key);
			System.out.println("update_value : "+setting_value);
			System.out.println("update_id : "+setting_id);
			
			HashMap<String,Object> hashMap = new HashMap<String,Object>();
			hashMap.put("setting_key", setting_key);
			hashMap.put("setting_id", setting_id);
			
			if(setting_value.equals("1")) {
				enocreWebService.updateOnSetting(hashMap);
				switch(setting_key) {
				case "watch":
					model.addAttribute("tilesWatch", 1);
					break;
				case "memo":
					System.out.println("memo on");
					model.addAttribute("tilesMemo", 1);
					break;
				case "calendar":
					System.out.println("calendar on");
					model.addAttribute("tilesCalendar", 1);
					break;	
				case "subway":
					System.out.println("subway on");
					model.addAttribute("tilesSubway", 1);
					break;
				case "news":
					System.out.println("news on");
					model.addAttribute("tilesNews", 1);
					break;
				}
				resultStr = "success";
			} else {
				enocreWebService.updateOffSetting(hashMap);
				switch(setting_key) {
				case "watch":
					model.addAttribute("tilesWatch", 0);
					break;
				case "memo":
					System.out.println("memo off");
					model.addAttribute("tilesMemo", 0);
					break;
				case "calendar":
					System.out.println("calendar off");
					model.addAttribute("tilesCalendar", 0);
					break;	
				case "subway":
					System.out.println("subway off");
					model.addAttribute("tilesSubway", 0);
					break;
				case "news":
					System.out.println("news off");
					model.addAttribute("tilesNews", 0);
					break;
				}
				resultStr = "success";
			}
			
			response.setCharacterEncoding("utf-8");
			
			PrintWriter print = response.getWriter();
			print.print(resultStr);
			print.flush();
			
			log.debug("EnocreWebController_update_setting_success");
		}
		
//		String url = "enocreWeb.do";
		//response.sendRedirect(url);
		/*RefreshController refreshController = new RefreshController();
		refreshController.refresh("success_calling_RefreshController", model);*/
		
//		initEnocreWeb(request,response,model);
	}
	private static com.neovisionaries.ws.client.WebSocket connect() throws Exception
    {
        return new WebSocketFactory()
            .setConnectionTimeout(5000)
            .createSocket("ws://172.18.71.3:8081/enocre/websocket/echo.do")
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