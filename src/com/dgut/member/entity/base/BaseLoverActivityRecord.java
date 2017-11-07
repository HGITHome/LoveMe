package com.dgut.member.entity.base;

import java.io.Serializable;
import java.util.Date;

import com.dgut.member.entity.LoverActivity;
import com.dgut.member.entity.Member;

public class BaseLoverActivityRecord implements Serializable {

	/**
	 * 恋人活动记录类
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private LoverActivity loverActivity;
	
	private Member member;
	
	private Date upload_time;
	
	private String photoPath;
	
	private String miniPath;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	

	public LoverActivity getLoverActivity() {
		return loverActivity;
	}

	public void setLoverActivity(LoverActivity loverActivity) {
		this.loverActivity = loverActivity;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Date getUpload_time() {
		return upload_time;
	}

	public void setUpload_time(Date upload_time) {
		this.upload_time = upload_time;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public String getMiniPath() {
		return miniPath;
	}

	public void setMiniPath(String miniPath) {
		this.miniPath = miniPath;
	}
	
	
	
	
}
