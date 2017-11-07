package com.dgut.member.entity.base;

import java.io.Serializable;

import com.dgut.member.entity.MemberInfo;

public class BaseLifePicture implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int pictureId ;
	private MemberInfo memberInfo;
	private String photoUrl;
	private String miniUrl;
	public int getPictureId() {
		return pictureId;
	}
	public void setPictureId(int pictureId) {
		this.pictureId = pictureId;
	}
	public MemberInfo getMemberInfo() {
		return memberInfo;
	}
	public void setMemberInfo(MemberInfo memberInfo) {
		this.memberInfo = memberInfo;
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + pictureId;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseLifePicture other = (BaseLifePicture) obj;
		if (pictureId != other.pictureId)
			return false;
		return true;
	}
	
	
	
}
