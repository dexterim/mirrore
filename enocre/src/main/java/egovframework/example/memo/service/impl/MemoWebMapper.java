package egovframework.example.memo.service.impl;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Mapper("memoWebMapper")
public interface MemoWebMapper {

	//select
	List<EgovMap> selectMemoService(String member_id) throws Exception;
	//insert
	void insertMemoService(Map<String, Object> hashMap) throws Exception;
	//update
	void updateMemoService(Map<String, Object> hashMap) throws Exception;
	//update
	void deleteMemoService(String identifier) throws Exception;
	
}
