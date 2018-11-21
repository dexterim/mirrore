package egovframework.example.nfcMirrorLogin;

public class MirrorVO {
	private static String mirror_id;
	
	public static String getMirrorId() {
		return mirror_id;
	}
	public static void setMirrorId(String mirror_id) {
		MirrorVO.mirror_id = mirror_id;
	}
}
