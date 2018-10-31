package egovframework.example.memberWeb.service.impl;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Mapper("memberWebMapper")
public interface MemberWebMapper {

	//insert
	int insertRegisterServiceMap(Map<String, Object> hashMap) throws Exception;
	void insertRegisterSettingServiceMap(Map<String, Object> hashMap) throws Exception;
	
	//id_check
	String selectIdOverlapCheckService(String id) throws Exception;
	int selectMirrorIdService(String mirror_id) throws Exception;
	
	//select
	List<EgovMap> selectMemberWebServiceList(String checkPw) throws Exception;
	String selectMemberLoginServiceList(String id) throws Exception;
	
	List<EgovMap> selectSettingServiceList(String id) throws Exception;
	
	//update
	void updateMember(Map<String, Object> hashMap) throws Exception;

}
