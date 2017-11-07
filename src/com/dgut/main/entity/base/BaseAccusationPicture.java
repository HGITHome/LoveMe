package com.dgut.main.entity.base;

import java.io.Serializable;

import com.dgut.main.entity.AccusationUser;


public class BaseAccusationPicture implements Serializable {

	/**
	 * 举办上传照片类
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private String photoUrl;
	
	private String miniUrl;
	
	private AccusationUser acUser;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getMiniUrl() {
		return miniUrl;
	}

	public void setMiniUrl(String miniUrl) {
		this.miniUrl = miniUrl;
	}

	public AccusationUser getAcUser() {
		return acUser;
	}

	public void setAcUser(AccusationUser acUser) {
		this.acUser = acUser;
	}

	
	
}
