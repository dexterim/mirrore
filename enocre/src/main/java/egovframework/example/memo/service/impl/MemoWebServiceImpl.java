package egovframework.example.memo.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.example.memo.service.MemoWebService;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Service("memoWebService")
public class MemoWebServiceImpl implements MemoWebService {

	@Resource(name = "memoWebMapper")
	private MemoWebMapper memoWebMapper;
	
	@Override
	public List<EgovMap> selectMemoService(String member_id) throws Exception {
		// TODO Auto-generated method stub
		return memoWebMapper.selectMemoService(member_id);
	}
	@Override
	public void insertMemoService(Map<String, Object> hashMap) throws Exception {
		// TODO Auto-generated method stub
		memoWebMapper.insertMemoService(hashMap);
	}
	@Override
	public void updateMemoService(Map<String, Object> hashMap) throws Exception {
		// TODO Auto-generated method stub
		memoWebMapper.updateMemoService(hashMap);
	}
	@Override
	public void deleteMemoService(String identifier) throws Exception {
		// TODO Auto-generated method stub
		memoWebMapper.deleteMemoService(identifier);
	}
	
	
}
