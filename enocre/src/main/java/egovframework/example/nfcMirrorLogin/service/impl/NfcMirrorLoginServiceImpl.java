package egovframework.example.nfcMirrorLogin.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.example.nfcMirrorLogin.service.NfcMirrorLoginService;

@Service("nfcMirrorLoginService")
public class NfcMirrorLoginServiceImpl implements NfcMirrorLoginService {

	@Resource(name = "nfcMirrorLoginMapper")
	private NfcMirrorLoginMapper nfcMirrorLoginMapper;
	
	
	@Override
	public void updateMemberMirror(Map<String, Object> hashMap) throws Exception {
		// TODO Auto-generated method stub
		nfcMirrorLoginMapper.updateMemberMirror(hashMap);
	}


	@Override
	public String selectMirrorLoginCheck() throws Exception {
		// TODO Auto-generated method stub
		return nfcMirrorLoginMapper.selectMirrorLoginCheck();
	}
	
	
}
