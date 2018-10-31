package egovframework.example.enocreWeb.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.example.enocreWeb.service.EnocreWebService;
import egovframework.example.enocreWeb.service.MemberVO;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Service("enocreWebService")
public class EnocreWebServiceImpl implements EnocreWebService {

	@Resource(name = "enocreWebMapper")
	private EnocreWebMapper enocreWebMapper;
	
	@Override
	public List<EgovMap> selectEnocreWebServiceList(String checkPw) throws Exception {
		return enocreWebMapper.selectEnocreWebServiceList(checkPw);
	}
	@Override
	public MemberVO selectEnocreWebServiceVO() throws Exception {
		// TODO Auto-generated method stub
		return enocreWebMapper.selectEnocreWebServiceVO();
	}
	@Override
	public String selectSettingService() throws Exception {
		return enocreWebMapper.selectSettingService();
	}
	@Override
	public void updateOnSetting(Map<String, Object> hashmap) throws Exception {
		enocreWebMapper.updateOnSetting(hashmap);
	}
	@Override
	public void updateOffSetting(HashMap<String, Object> hashMap) throws Exception {
		enocreWebMapper.updateOffSetting(hashMap);
		
	}
	@Override
	public String selectEnocreLoginServiceList(String id) throws Exception {
		// TODO Auto-generated method stub
		return enocreWebMapper.selectEnocreLoginServiceList(id);
	}
	@Override
	public String insertRegisterServiceMap(Map<String, Object> hashMap) throws Exception {
		// TODO Auto-generated method stub
		String insertCheck = (String)hashMap.get("id");
		System.out.println("type_check: "+enocreWebMapper.insertRegisterServiceMap(hashMap));
		return insertCheck;
		
	}
	@Override
	public void insertRegisterSettingServiceMap(Map<String, Object> hashMap) throws Exception {
		// TODO Auto-generated method stub
		enocreWebMapper.insertRegisterSettingServiceMap(hashMap);
		
	}
	@Override
	public int selectMirrorIdService(String mirror_id) throws Exception {
		// TODO Auto-generated method stub
		return enocreWebMapper.selectMirrorIdService(mirror_id);
	}
	@Override
	public String selectIdOverlapCheckService(String id) throws Exception {
		// TODO Auto-generated method stub
		return enocreWebMapper.selectIdOverlapCheckService(id);
	}
	
	

}
