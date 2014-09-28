package com.cumtLife.bean;

import cn.bmob.v3.BmobObject;

public class UserInfo extends BmobObject{
	
	private String name;
	private String password;
	private String email;
	private static String currentUser;
	public static String getCurrentUser() {
		return currentUser;
	}
	public static void setCurrentUser(String currentUser) {
		UserInfo.currentUser = currentUser;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
