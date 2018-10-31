package egovframework.example.enocreWeb.service;

public class SettingVO {
	private int now_condition;
	private int out_light;
	private int inside_light;
	private int fan;
	private int door;
	private String id;
	
	public int getNow_condition() {
		return now_condition;
	}
	public void setNow_condition(int now_condition) {
		this.now_condition = now_condition;
	}
	public int getOut_light() {
		return out_light;
	}
	public void setOut_light(int out_light) {
		this.out_light = out_light;
	}
	public int getInside_light() {
		return inside_light;
	}
	public void setInside_light(int inside_light) {
		this.inside_light = inside_light;
	}
	public int getFan() {
		return fan;
	}
	public void setFan(int fan) {
		this.fan = fan;
	}
	public int getDoor() {
		return door;
	}
	public void setDoor(int door) {
		this.door = door;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	
}
