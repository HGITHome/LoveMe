package com.dgut.member.entity.base;

import java.io.Serializable;
import java.util.Date;



import com.dgut.member.entity.MemberInfo;


public abstract class BaseMember implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;





	public BaseMember(){
		initialize();
	}
	private void initialize() {}
	
	public BaseMember (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}
	
	private int hashCode = Integer.MIN_VALUE;
	
	private java.lang.Integer id;
	private MemberInfo memberInfo;
	private java.lang.String username;
	private java.lang.String easemob_username;
	private java.lang.String password;
	private java.lang.String email;
	private java.lang.String mobile;
	private java.lang.Boolean easemob_flag;
	private java.lang.String loginAddress;
	private java.util.Date registerTime;
	private java.lang.String registerIp;
	private java.util.Date lastLoginTime;
	private java.lang.String lastLoginIp;
	private java.lang.Integer loginCount;
	private java.lang.Boolean disabled;

	private java.util.Date errorTime;
	private java.lang.Integer errorCount;
	private java.lang.String errorIp;
	
	
	
	
	
	public BaseMember(int hashCode, Integer id, String username, String easemob_username,
			String password,Boolean easemob_flag, String email, String mobile, String loginAddress,
			Date registerTime, String registerIp, Date lastLoginTime,
			String lastLoginIp, Integer loginCount, Boolean disabled,
			Date errorTime, Integer errorCount, String errorIp) {
		super();
		this.hashCode = hashCode;
		this.id = id;
		this.username = username;
		this.easemob_username = easemob_username;
		this.password = password;
		this.easemob_flag = easemob_flag;
		this.email = email;
		this.mobile = mobile;
		this.loginAddress = loginAddress;
		this.registerTime = registerTime;
		this.registerIp = registerIp;
		this.lastLoginTime = lastLoginTime;
		this.lastLoginIp = lastLoginIp;
		this.loginCount = loginCount;
		this.disabled = disabled;
		this.errorTime = errorTime;
		this.errorCount = errorCount;
		this.errorIp = errorIp;
	}
	public java.lang.Integer getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (java.lang.Integer id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}
	
	
	
	
	public MemberInfo getMemberInfo() {
		return memberInfo;
	}
	public void setMemberInfo(MemberInfo memberInfo) {
		this.memberInfo = memberInfo;
	}

	public int getHashCode() {
		return hashCode;
	}
	public void setHashCode(int hashCode) {
		this.hashCode = hashCode;
	}
	public java.lang.String getUsername() {
		return username;
	}
	
	public void setUsername(java.lang.String username) {
		this.username = username;
	}
	
	public java.lang.String getEasemob_username() {
		return easemob_username;
	}
	public void setEasemob_username(java.lang.String easemob_username) {
		this.easemob_username = easemob_username;
	}
	public java.lang.String getPassword() {
		return password;
	}
	public void setPassword(java.lang.String password) {
		this.password = password;
	}
	
	public java.lang.Boolean getEasemob_flag() {
		return easemob_flag;
	}
	public void setEasemob_flag(java.lang.Boolean easemob_flag) {
		this.easemob_flag = easemob_flag;
	}
	public java.lang.String getEmail() {
		return email;
	}
	public void setEmail(java.lang.String email) {
		this.email = email;
	}
	public java.lang.String getMobile() {
		return mobile;
	}
	public void setMobile(java.lang.String mobile) {
		this.mobile = mobile;
	}
	public java.util.Date getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(java.util.Date registerTime) {
		this.registerTime = registerTime;
	}
	public java.lang.String getRegisterIp() {
		return registerIp;
	}
	public void setRegisterIp(java.lang.String registerIp) {
		this.registerIp = registerIp;
	}
	public java.util.Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(java.util.Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public java.lang.String getLastLoginIp() {
		return lastLoginIp;
	}
	public void setLastLoginIp(java.lang.String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}
	public java.lang.Integer getLoginCount() {
		return loginCount;
	}
	public void setLoginCount(java.lang.Integer loginCount) {
		this.loginCount = loginCount;
	}
	public java.lang.Boolean getDisabled() {
		return disabled;
	}
	public void setDisabled(java.lang.Boolean disabled) {
		this.disabled = disabled;
	}
	public java.util.Date getErrorTime() {
		return errorTime;
	}
	public void setErrorTime(java.util.Date errorTime) {
		this.errorTime = errorTime;
	}
	public java.lang.Integer getErrorCount() {
		return errorCount;
	}
	public void setErrorCount(java.lang.Integer errorCount) {
		this.errorCount = errorCount;
	}
	public java.lang.String getErrorIp() {
		return errorIp;
	}
	public void setErrorIp(java.lang.String errorIp) {
		this.errorIp = errorIp;
	}
	public java.lang.String getLoginAddress() {
		return loginAddress;
	}
	public void setLoginAddress(java.lang.String loginAddress) {
		this.loginAddress = loginAddress;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
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
		BaseMember other = (BaseMember) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "BaseMember [hashCode=" + hashCode + ", id=" + id
				+ ", memberInfo=" + memberInfo + ", username=" + username
				+ ", password=" + password + ", email=" + email + ", mobile="
				+ mobile + ", loginAddress=" + loginAddress + ", registerTime="
				+ registerTime + ", registerIp=" + registerIp
				+ ", lastLoginTime=" + lastLoginTime + ", lastLoginIp="
				+ lastLoginIp + ", loginCount=" + loginCount + ", disabled="
				+ disabled + ", errorTime=" + errorTime + ", errorCount="
				+ errorCount + ", errorIp=" + errorIp + "]";
	}
	


}
