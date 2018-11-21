package egovframework.example.enocreWeb.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public interface EnocreWebService {

	List<EgovMap> selectEnocreWebServiceList(String checkPw) throws Exception;
	MemberVO selectEnocreWebServiceVO() throws Exception;
	String selectSettingService() throws Exception;
	void updateOnSetting(Map<String,Object> hashMap) throws Exception;
	void updateOffSetting(Map<String, Object> hashMap) throws Exception;
	
	String selectEnocreLoginServiceList(String id) throws Exception;
	String insertRegisterServiceMap(Map<String, Object> hashMap) throws Exception;
	void insertRegisterSettingServiceMap(Map<String, Object> hashMap) throws Exception;
	int selectMirrorIdService(String mirror_id) throws Exception;
	String selectIdOverlapCheckService(String id) throws Exception;
	
}
