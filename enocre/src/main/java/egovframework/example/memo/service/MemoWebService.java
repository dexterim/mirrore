package egovframework.example.memo.service;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public interface MemoWebService {

	//select
	List<EgovMap> selectMemoService(String member_id) throws Exception;
	//insert
	void insertMemoService(Map<String, Object> hashMap) throws Exception;
	//update
	void updateMemoService(Map<String, Object> hashMap) throws Exception;
	//update
	void deleteMemoService(String identifier) throws Exception;

}
