package egovframework.example.calendar.web;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.example.cmmn.JsonUtil;

@Controller
public class CalendarWebController {

	@RequestMapping("calendarText.do")
	public void initNewsWeb(@RequestBody String reqParam,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String resultStr;
		Map<String,Object> hashMap;
		hashMap = JsonUtil.JsonToMap(reqParam);
		
		System.out.println(hashMap+"\n:캘린더 정보 확인");
		
		resultStr = "success";
		response.setCharacterEncoding("utf-8");
		
		PrintWriter print = response.getWriter();
		
		//print.write(resultStr);
		print.print(resultStr);
		print.flush();
		
	}
	
}

