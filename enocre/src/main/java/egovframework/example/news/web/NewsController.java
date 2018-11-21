package egovframework.example.news.web;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class NewsController {
	public static String[] news;//
	public static StringBuilder sb;//
	 
    static String getString(String input, String data) // API에서 필요한 문자 자르기.
    {
        String[] dataSplit = data.split("{" + input + "}");
        String[] dataSplit2 = dataSplit[1].split("\"" + input + "\"");
        return dataSplit2[0];
    }
    
    public String[] setString() // API에서 필요한 문자 자르기.
    {
        return news;
    }
    
	public StringBuilder makeNews(){
		 
        String clientId = "sM3ppoIQEVPdEN8YUi2V";
        String clientSecret = "vwQynryeHG";
        int display = 5;
        
        try {
            String text = URLEncoder.encode("오늘의 뉴스", "utf-8"); //앞에 검색어
            String apiURL = "https://openapi.naver.com/v1/search/news?query=" + text + "&display=" + display + "&sort=date";
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            sb = new StringBuilder();
            String line;
 
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();
            con.disconnect();
//            System.out.println(sb); 
//            String data = sb.toString();
//            String[] array;
//            array = data.split("\"");
//            String[] title = new String[display];
//            String[] originallink = new String[display];
//            String[] link = new String[display];
//            String[] description = new String[display];
//            String[] pubData = new String[display];
//            int k = 0;
//            for (int i = 0; i < array.length; i++) {
//                if (array[i].equals("title"))
//                    title[k] = array[i + 2];
//                if (array[i].equals("originallink"))
//                	originallink[k] = array[i + 2];
//                if (array[i].equals("link"))
//                	link[k] = array[i + 2];
//                if (array[i].equals("description"))
//                    description[k] = array[i + 2];
//                if (array[i].equals("pubData"))
//                	pubData[k] = array[i + 2];
//               
//            }
//            System.out.println(sb);
            System.out.println("----------------------------");
//            System.out.println("첫번째 타이틀 : " + title[0]);
//            System.out.println("두번째 타이틀 : " + title[1]);
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return sb;
		}

	}
