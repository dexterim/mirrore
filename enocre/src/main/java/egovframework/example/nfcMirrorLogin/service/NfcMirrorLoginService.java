package egovframework.example.nfcMirrorLogin.service;

import java.util.Map;

public interface NfcMirrorLoginService {
		//update
		void updateMemberMirror(Map<String, Object> hashMap) throws Exception;
		//select
		String selectMirrorLoginCheck() throws Exception;
}
