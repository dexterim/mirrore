package egovframework.example.memo.service;

import java.sql.Timestamp;

public class MemberVO {
	private static String id;
	private static String pw;
	private static String name;
	private static String phone;
	private static String weather_location;
	private static String subway_location;
	private static Timestamp reg_date;
	
	public static String getId() {
		return id;
	}
	public static void setId(String id) {
		MemberVO.id = id;
	}
	public static String getPw() {
		return pw;
	}
	public static void setPw(String pw) {
		MemberVO.pw = pw;
	}
	public static String getName() {
		return name;
	}
	public static void setName(String name) {
		MemberVO.name = name;
	}
	public static String getPhone() {
		return phone;
	}
	public static void setPhone(String phone) {
		MemberVO.phone = phone;
	}
	public static String getWeather_location() {
		return weather_location;
	}
	public static void setWeather_location(String weather_location) {
		MemberVO.weather_location = weather_location;
	}
	public static String getSubway_location() {
		return subway_location;
	}
	public static void setSubway_location(String subway_location) {
		MemberVO.subway_location = subway_location;
	}
	public static Timestamp getReg_date() {
		return reg_date;
	}
	public static void setReg_date(Timestamp reg_date) {
		MemberVO.reg_date = reg_date;
	}
	
	
}
