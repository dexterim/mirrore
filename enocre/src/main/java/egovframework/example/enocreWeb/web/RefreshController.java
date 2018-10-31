package egovframework.example.enocreWeb.web;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.filter.OncePerRequestFilter;

import egovframework.example.enocreWeb.service.EnocreWebService;
import egovframework.rte.psl.dataaccess.util.EgovMap;

/*@Controller
public class RefreshController {
	
	@Resource(name="enocreWebService")
	private EnocreWebService enocreWebService;

	@RequestMapping("refresh_1.do")
	public String refresh(String param, ModelMap model) throws Exception{
		
		List<EgovMap> member = enocreWebService.selectEnocreWebServiceList("miri@gmail.com");
    	String setting = enocreWebService.selectSettingService();
    	String userName = "";
		System.out.println(member);
		System.out.println(setting);
		
		model.addAttribute("memberList", member);
		model.addAttribute("setting", setting);
		for(int i = 0; i<member.size(); i++) {
			if(member.get(0).getValue(0).equals(setting)) {
				userName = (String) member.get(0).getValue(2);
			};
		}
		System.out.println("memberList_name : "+member.get(0).getValue(2));
		System.out.println("userName : "+userName);
		System.out.println("parameter: "+param);
		model.addAttribute("userName", userName);
		return "ui.tiles";
	}
	
}*/
@Component
public class RefreshController extends OncePerRequestFilter {
@Override
protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
	super.doFilter(request, response, filterChain);
	HttpSession session = request.getSession(false);
	if (session != null) {
	    /*IFlash flash = new Flash(session);
	    flash.recycle();*/
	}
    }
}