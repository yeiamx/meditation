package com.flctxx.meditation.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class UserInfo {
	@Id
	@Column(name = "password")
	private String password;

	@Column(name = "user_id")	
	private String userId;

	@Column(name = "user_name")
	private String userName;
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public UserInfo(String password, String userId, String userName) {
		super();
		this.password = password;
		this.userId = userId;
		this.userName = userName;
	}
	
	public UserInfo(){
		
	}
}
