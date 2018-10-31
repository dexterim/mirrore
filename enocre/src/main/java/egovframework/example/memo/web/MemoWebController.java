package egovframework.example.memo.web;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.example.memo.service.MemoWebService;

@Controller
public class MemoWebController {
	
	@Resource(name="memoWebService")
	private MemoWebService memoWebService;
	
	@RequestMapping(value = "updateMemo.do")
	public void memoUpdate(@RequestBody String reqParam,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
	}
	
}