package com.example.hackathon1;


public class Driver {
	
	public static final String PREF_KEY = "preferenceDriver";  
	public static final String KEY_NAME_TEXT = "name";
	public static final String KEY_AGE_TEXT = "age";
	public static final String KEY_TELEPHONE_TEXT = "telephone";
	
	
	public String name;
	public int age;
	public String telephone;
	public Driver(String name,int age,String telephone){
		this.name = name;
		this.age = age;
		this.telephone = telephone;
	}
	
}
