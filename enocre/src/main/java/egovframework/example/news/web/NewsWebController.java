package egovframework.example.news.web;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class NewsWebController {

	@RequestMapping("newsWeb.do")
	public String initNewsWeb(ModelMap model) throws Exception{
		NewsController news = new NewsController();
		
		try {
			StringBuilder sb = news.makeNews();
			String sbStr = sb.toString();
			
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(sbStr);
			JSONObject jsonNews = (JSONObject) obj;
			//String jsonItemsStr = (String) jsonNews.get("items");
			//System.out.println(jsonNews.get("items"));
			
			model.addAttribute("userNews", jsonNews.get("items"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "ui.tiles";
	}
	
}

