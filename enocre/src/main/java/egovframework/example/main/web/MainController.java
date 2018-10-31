package egovframework.example.main.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import egovframework.example.memberWeb.service.MemberVO;

@Controller
public class MainController extends HandlerInterceptorAdapter {
	
	@RequestMapping("main.do")
	public String initMain(ModelMap model) throws Exception{
		
		return "main/main.tiles";
	}
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		HttpSession session  = request.getSession(false);

		if(session == null) {
			response.sendRedirect(request.getContextPath()+"/enocreWeb.do");
			return false;
		}

		MemberVO member = (MemberVO)session.getAttribute("member");

		if (member == null) {
			response.sendRedirect(request.getContextPath()+"/enocreWeb.do");
			return false;			
		}
		response.sendRedirect(request.getContextPath()+"/enocreWeb.do");
		return true;
	}
	
}



