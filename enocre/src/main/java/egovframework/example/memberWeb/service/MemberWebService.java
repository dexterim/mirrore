package egovframework.example.memberWeb.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public interface MemberWebService {

	//sign_up_user_all_data
	String insertRegisterServiceMap(Map<String, Object> hashMap) throws Exception;
	//insert_user_setting_all_data
	void insertRegisterSettingServiceMap(Map<String, Object> hashMap) throws Exception;
	//exist id?(아이디 중복 확인)
	String selectIdOverlapCheckService(String id) throws Exception;
	//exist mirror_id(validate_check)
	int selectMirrorIdService(String mirror_id) throws Exception;
	//user_Login_validated_user_information
	List<EgovMap> selectMemberWebServiceList(String checkPw) throws Exception;
	String selectMemberLoginServiceList(String id) throws Exception;
	//setting
	List<EgovMap> selectSettingServiceList(String id) throws Exception;
	void updateMember(HashMap<String, Object> hashMap) throws Exception;
	
}
