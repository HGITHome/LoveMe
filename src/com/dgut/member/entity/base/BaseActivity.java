package com.dgut.member.entity.base;

import java.io.Serializable;
import java.util.Date;

import com.dgut.main.entity.Admin;

public class BaseActivity implements Serializable {

	/**
	 * 活动类
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private Admin admin;
	
	private String activityName;
	
	private String activityAddress;
	
	private Integer completion_time;
	
	private Date register_time;
	
	private Date lastUpdateTime;
	
	private String activityContent;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getActivityAddress() {
		return activityAddress;
	}

	public void setActivityAddress(String activityAddress) {
		this.activityAddress = activityAddress;
	}

	public Integer getCompletion_time() {
		return completion_time;
	}

	public void setCompletion_time(Integer completion_time) {
		this.completion_time = completion_time;
	}

	public Date getRegister_time() {
		return register_time;
	}

	public void setRegister_time(Date register_time) {
		this.register_time = register_time;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getActivityContent() {
		return activityContent;
	}

	public void setActivityContent(String activityContent) {
		this.activityContent = activityContent;
	}

	
	
}
