package egovframework.example.memo.service.impl;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Mapper("memoWebMapper")
public interface MemoWebMapper {

	//insert
	void insertMemoService(Map<String, Object> hashMap) throws Exception;
	//update
	void updateMemoService(Map<String, Object> hashMap) throws Exception;
	//update
	void deleteMemoService(Map<String, Object> hashMap) throws Exception;
}
