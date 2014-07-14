package com.example.hackathon1;

public class Schedule {
	
	public static final String PREF_KEY = "preferenceSchedule";
	
	public static final String KEY_ID_INT = "id";
	public static final String KEY_TIME_TEXT = "time";
	public static final String KEY_COMMENT_TEXT = "comment";
	public static final String KEY_START_LOCATION_TEXT = "start_location";
	public static final String KEY_END_LOCATION_TEXT = "end_location";
	
	public static final String KEY_NAME_TEXT = "name";
	public static final String KEY_AGE_TEXT = "age";
	public static final String KEY_TELEPHONE_TEXT = "telephone";
	
	public int id;
	public String time;
	public String comment;
	public String startLocation;
	public String endLocation;
	public Driver driver;
	
	public Schedule(int id,String time,String comment,String startLocation,String endLocation,Driver driver){
		this.id = id;
		this.time = time;
		this.comment = comment;
		this.startLocation = startLocation;
		this.endLocation = endLocation;
		this.driver = driver;
	}	
}
