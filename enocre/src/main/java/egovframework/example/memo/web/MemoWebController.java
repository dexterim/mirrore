package egovframework.example.memo.web;


import java.io.PrintWriter;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.example.cmmn.JsonUtil;
import egovframework.example.memo.service.MemoWebService;

@Controller
public class MemoWebController {
	
	@Resource(name="memoWebService")
	private MemoWebService memoWebService;
	
	@RequestMapping(value = "memoInsert.do")
	public void insertMemo(@RequestBody String reqParam,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String resultStr;
		Map<String,Object> hashMap;
		hashMap = JsonUtil.JsonToMap(reqParam);
		
		System.out.println(hashMap+"\n:메모 정보 입력");
		
		memoWebService.insertMemoService(hashMap);
		
		resultStr = "success";
		response.setCharacterEncoding("utf-8");
		
		PrintWriter print = response.getWriter();
		
		//print.write(resultStr);
		print.print(resultStr);
		print.flush();
		
	}
	@RequestMapping(value = "memoUpdate.do")
	public void memoUpdate(@RequestBody String reqParam,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		System.out.println("\n:메모 정보 업데이트");
		
	}
	@RequestMapping(value = "memoDelete.do")
	public void memoDelete(@RequestBody String reqParam,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		System.out.println("\n:메모 정보 삭제");
		
	}
	
}