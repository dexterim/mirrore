package egovframework.example.nfcMirrorLogin.service.impl;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Mapper("nfcMirrorLoginMapper")
public interface NfcMirrorLoginMapper {
	
	//update
	public void updateMemberMirror(Map<String, Object> hashMap) throws Exception;
	//select
	public String selectMirrorLoginCheck() throws Exception;
	
}
