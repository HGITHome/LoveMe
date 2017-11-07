package com.dgut.member.entity.base;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.dgut.member.entity.Activity;
import com.dgut.member.entity.LoverActivityRecord;
import com.dgut.member.entity.Lovership;

public class BaseLoverActivity implements Serializable{

	/**
	 * 一对恋人的七天活动安排
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private Integer day_number;
	
	private String loverUUID;
	
	private Activity activity;
	
	private Date start_time;
	
	private Integer completion_status;
	
	private Date completion_time;

	private List<LoverActivityRecord> records;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDay_number() {
		return day_number;
	}

	public void setDay_number(Integer day_number) {
		this.day_number = day_number;
	}



	public String getLoverUUID() {
		return loverUUID;
	}

	public void setLoverUUID(String loverUUID) {
		this.loverUUID = loverUUID;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public Date getStart_time() {
		return start_time;
	}

	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}

	public Integer getCompletion_status() {
		return completion_status;
	}

	public void setCompletion_status(Integer completion_status) {
		this.completion_status = completion_status;
	}

	public Date getCompletion_time() {
		return completion_time;
	}

	public void setCompletion_time(Date completion_time) {
		this.completion_time = completion_time;
	}

	public List<LoverActivityRecord> getRecords() {
		return records;
	}

	public void setRecords(List<LoverActivityRecord> records) {
		this.records = records;
	}

	
	
	
}
