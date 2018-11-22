package egovframework.example.memo.web;


import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketExtension;
import com.neovisionaries.ws.client.WebSocketFactory;

import egovframework.example.cmmn.JsonUtil;
import egovframework.example.memo.service.MemoWebService;
import egovframework.example.nfcMirrorLogin.web.NfcMirrorLogin;
import egovframework.example.webSocket.web.WebSocket;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Controller
public class MemoWebController {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Resource(name="memoWebService")
	private MemoWebService memoWebService;
	
	@RequestMapping(value = "selectMemo.do")
	public void selectMemo(@RequestBody String reqParam,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String param = "";
		Map<String, Object> paramMap = JsonUtil.JsonToMap(reqParam);
		
		param = (String)paramMap.get("id_session_value");
		
		List<EgovMap> memoList = memoWebService.selectMemoService(param);
		
		for (int i = 0; i<memoList.size();i++) {
			System.out.println("memo:"+memoList.get(i));
		}
		HashMap<String,Object> resultMap = new HashMap<String,Object>();
		
		resultMap.put("result", "success");
		resultMap.put("memoList", memoList);
		
		response.setCharacterEncoding("utf-8");
		
		PrintWriter out = response.getWriter();
		
		String resultMapToJson = JsonUtil.HashMapToJson(resultMap);
		
		out.write(resultMapToJson);
	}
	@RequestMapping(value = "memoInsert.do")
	public void insertMemo(@RequestBody String reqParam,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
			String member_id, mirror_id, result;
			Map<String,Object> hashMap;
			hashMap = JsonUtil.JsonToMap(reqParam);
			
			System.out.println(hashMap+"\n:메모 정보 입력");
			member_id = hashMap.get("member_id").toString();
			
			mirror_id = hashMap.get("mirror_id").toString();
			
			NfcMirrorLogin nfcLogin = new NfcMirrorLogin();
			result = nfcLogin.nfcCheck(mirror_id);

			memoWebService.insertMemoService(hashMap);
			
			if(result.equals("validated_user")){
				webSocketMemoUpdate(member_id);
			}
			response.setCharacterEncoding("utf-8");
			
			PrintWriter print = response.getWriter();
			
			//print.write(resultStr);
			print.print(result);
			print.flush();
		
	}
	@RequestMapping(value = "memoUpdate.do")
	public void memoUpdate(@RequestBody String reqParam,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String member_id, mirror_id, result;
		Map<String,Object> hashMap;
		hashMap = JsonUtil.JsonToMap(reqParam);
		
		System.out.println("\n:메모 정보 업데이트");
		
		member_id = hashMap.get("member_id").toString();
		mirror_id = hashMap.get("mirror_id").toString();
		
		NfcMirrorLogin nfcLogin = new NfcMirrorLogin();
		result = nfcLogin.nfcCheck(mirror_id);
		
		memoWebService.updateMemoService(hashMap);
		
		if(result.equals("validated_user")){
			webSocketMemoUpdate(member_id);
		}
		
		PrintWriter print = response.getWriter();
		
		//print.write(resultStr);
		print.print(result);
		print.flush();
		
	}
	@RequestMapping(value = "memoDelete.do")
	public void memoDelete(@RequestBody String reqParam,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String member_id, mirror_id, result;
		Map<String,Object> hashMap;
		hashMap = JsonUtil.JsonToMap(reqParam);
		
		System.out.println(hashMap.get("identifier")+"\n:메모 정보 삭제");
		
		member_id = hashMap.get("member_id").toString();
		System.out.println("delete_memo_member_id: "+member_id);
		
		mirror_id = hashMap.get("mirror_id").toString();
		
		NfcMirrorLogin nfcLogin = new NfcMirrorLogin();
		result = nfcLogin.nfcCheck(mirror_id);
		
		memoWebService.deleteMemoService(hashMap.get("identifier").toString());
		
		if(result.equals("validated_user")){
			webSocketMemoUpdate(member_id);
		}
		
		response.setCharacterEncoding("utf-8");
		
		PrintWriter print = response.getWriter();
		print.print(result);
		print.flush();
	}
	
	public void webSocketMemoUpdate(String member_id){
		try{			
			com.neovisionaries.ws.client.WebSocket ws = connect();
			ws.sendText("memo_update: "+member_id);
			
		} catch (ArrayIndexOutOfBoundsException ae) {
			log.info("array 오류가 발생했습니다."+ae);
		} catch (NullPointerException ne) {
			log.info("null 오류가 발생했습니다."+ne);
		} catch (Exception e) {
			log.info("그 외 오류가 발생했습니다."+e);
		} finally {
			
		}
	}
	private static com.neovisionaries.ws.client.WebSocket connect() throws Exception
    {
        return new WebSocketFactory()
            .setConnectionTimeout(5000)
            .createSocket("ws://172.18.81.57:8081/enocre/websocket/echo.do")
            .addListener(new WebSocketAdapter() {
            })
            .addExtension(WebSocketExtension.PERMESSAGE_DEFLATE)
            .connect();
    }
}