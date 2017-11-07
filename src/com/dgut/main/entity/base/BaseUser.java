package com.dgut.main.entity.base;

import java.io.Serializable;

import com.dgut.main.entity.Enum.Gender;

public abstract class BaseUser implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;




	public BaseUser(){
		initialize();
	}
	public BaseUser(java.lang.Integer id){
		this.setId(id);
		initialize();
	}
	private void initialize() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 用来存储注册页面提交的信息（临时存储)
	 */
	
	private java.lang.Integer id;
	private java.lang.String userName;
	private java.lang.String realName;
	private java.lang.String password;
	private java.lang.String password1;
	private java.lang.String gender;
	private java.lang.String phone;
	private java.lang.String email;
	private java.lang.String captcha;
	
	
	
	
	public java.lang.Integer getId() {
		return id;
	}
	public void setId(java.lang.Integer id) {
		this.id = id;
	}
	public java.lang.String getUserName() {
		return userName;
	}
	public void setUserName(java.lang.String userName) {
		this.userName = userName;
	}
	public java.lang.String getRealName() {
		return realName;
	}
	public void setRealName(java.lang.String realName) {
		this.realName = realName;
	}
	public java.lang.String getPassword() {
		return password;
	}
	public void setPassword(java.lang.String password) {
		this.password = password;
	}
	public java.lang.String getPassword1() {
		return password1;
	}
	public void setPassword1(java.lang.String password1) {
		this.password1 = password1;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	
	public java.lang.String getPhone() {
		return phone;
	}
	public void setPhone(java.lang.String phone) {
		this.phone = phone;
	}
	public java.lang.String getEmail() {
		return email;
	}
	public void setEmail(java.lang.String email) {
		this.email = email;
	}
	public java.lang.String getCaptcha() {
		return captcha;
	}
	public void setCaptcha(java.lang.String captcha) {
		this.captcha = captcha;
	}
	

	

}
