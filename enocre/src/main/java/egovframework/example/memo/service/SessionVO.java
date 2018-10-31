package egovframework.example.memo.service;

import java.sql.Timestamp;

public class SessionVO {
	//member
	private static String s_id;
	private static String s_pw;
	private static String s_name;
	private static String s_phone;
	private static String s_weather_location;
	private static String s_subway_location;
	private static Timestamp s_reg_date;
	
	//setting
	private int s_weather;
	private int s_subway;
	private int s_memo;
	private int s_calendar;
	private int s_news;
	private int s_mirror_light;
	private int s_room_light;
	private int s_fan;
	private int s_door_lock;
	private int s_now_condition;
	public static String getS_id() {
		return s_id;
	}
	
	//member
	public static void setS_id(String s_id) {
		SessionVO.s_id = s_id;
	}
	public static String getS_pw() {
		return s_pw;
	}
	public static void setS_pw(String s_pw) {
		SessionVO.s_pw = s_pw;
	}
	public static String getS_name() {
		return s_name;
	}
	public static void setS_name(String s_name) {
		SessionVO.s_name = s_name;
	}
	public static String getS_phone() {
		return s_phone;
	}
	public static void setS_phone(String s_phone) {
		SessionVO.s_phone = s_phone;
	}
	
	//setting
	public static String getS_weather_location() {
		return s_weather_location;
	}
	public static void setS_weather_location(String s_weather_location) {
		SessionVO.s_weather_location = s_weather_location;
	}
	public static String getS_subway_location() {
		return s_subway_location;
	}
	public static void setS_subway_location(String s_subway_location) {
		SessionVO.s_subway_location = s_subway_location;
	}
	public static Timestamp getS_reg_date() {
		return s_reg_date;
	}
	public static void setS_reg_date(Timestamp s_reg_date) {
		SessionVO.s_reg_date = s_reg_date;
	}
	public int getS_weather() {
		return s_weather;
	}
	public void setS_weather(int s_weather) {
		this.s_weather = s_weather;
	}
	public int getS_subway() {
		return s_subway;
	}
	public void setS_subway(int s_subway) {
		this.s_subway = s_subway;
	}
	public int getS_memo() {
		return s_memo;
	}
	public void setS_memo(int s_memo) {
		this.s_memo = s_memo;
	}
	public int getS_calendar() {
		return s_calendar;
	}
	public void setS_calendar(int s_calendar) {
		this.s_calendar = s_calendar;
	}
	public int getS_news() {
		return s_news;
	}
	public void setS_news(int s_news) {
		this.s_news = s_news;
	}
	public int getS_mirror_light() {
		return s_mirror_light;
	}
	public void setS_mirror_light(int s_mirror_light) {
		this.s_mirror_light = s_mirror_light;
	}
	public int getS_room_light() {
		return s_room_light;
	}
	public void setS_room_light(int s_room_light) {
		this.s_room_light = s_room_light;
	}
	public int getS_fan() {
		return s_fan;
	}
	public void setS_fan(int s_fan) {
		this.s_fan = s_fan;
	}
	public int getS_door_lock() {
		return s_door_lock;
	}
	public void setS_door_lock(int s_door_lock) {
		this.s_door_lock = s_door_lock;
	}
	public int getS_now_condition() {
		return s_now_condition;
	}
	public void setS_now_condition(int s_now_condition) {
		this.s_now_condition = s_now_condition;
	}
}
