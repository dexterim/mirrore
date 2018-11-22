package egovframework.example.nfcMirrorLogin.service;

import java.util.Map;

public interface NfcMirrorLoginService {
		//update
		public void updateMemberMirror(Map<String, Object> hashMap) throws Exception;
		//select
		public String selectMirrorLoginCheck() throws Exception;
}
