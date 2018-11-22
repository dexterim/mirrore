package egovframework.example.memberWeb.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.example.memberWeb.service.MemberWebService;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Service("memberWebService")
public class MemberWebServiceImpl implements MemberWebService {

	@Resource(name = "memberWebMapper")
	private MemberWebMapper memberWebMapper;
	
	@Override
	public String insertRegisterServiceMap(Map<String, Object> hashMap) throws Exception {
		// TODO Auto-generated method stub
		String insertCheck = (String)hashMap.get("id");
		System.out.println("type_check: "+memberWebMapper.insertRegisterServiceMap(hashMap));
		return insertCheck;
	}
	
	@Override
	public void insertRegisterSettingServiceMap(Map<String, Object> hashMap) throws Exception {
		// TODO Auto-generated method stub
		memberWebMapper.insertRegisterSettingServiceMap(hashMap);
	}
	
	@Override
	public String selectIdOverlapCheckService(String id) throws Exception {
		// TODO Auto-generated method stub
		return memberWebMapper.selectIdOverlapCheckService(id);
	}
	
	
	@Override
	public int selectMirrorIdService(String mirror_id) throws Exception {
		// TODO Auto-generated method stub
		return memberWebMapper.selectMirrorIdService(mirror_id);
	}
	
	@Override
	public List<EgovMap> selectMemberWebServiceList(String checkPw) throws Exception {
		return memberWebMapper.selectMemberWebServiceList(checkPw);
	}
	@Override
	public String selectMemberLoginServiceList(String id) throws Exception {
		// TODO Auto-generated method stub
		return memberWebMapper.selectMemberLoginServiceList(id);
	}

	@Override
	public List<EgovMap> selectSettingServiceList(String id) throws Exception {
		// TODO Auto-generated method stub
		return memberWebMapper.selectSettingServiceList(id);
	}

	@Override
	public void updateMember(Map<String, Object> hashMap) throws Exception {
		// TODO Auto-generated method stub
		memberWebMapper.updateMember(hashMap);
	}
	
}
