package com.dgut.main.entity.base;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.dgut.main.entity.AccusationPicture;
import com.dgut.main.entity.AccusationType;
import com.dgut.main.entity.Admin;
import com.dgut.member.entity.Member;

public class BaseAccusationUser implements Serializable {

	/**
	 * 举报者类
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private Member reporter;//举报者
	
	private Member informant;//被举报者
	
	private AccusationType acType;//举报之类
	
	private String report_content;//举报内容
	
	private Date report_time;//举报时间
	
	private Integer handle_flag;//处理状态
	
	private Date reply_time;//处理时间
	
	private String reply_content;//处理内容
	
	private Admin admin;//后台处理人员

	private List<AccusationPicture> acPictures;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Member getReporter() {
		return reporter;
	}

	public void setReporter(Member reporter) {
		this.reporter = reporter;
	}

	public Member getInformant() {
		return informant;
	}

	public void setInformant(Member informant) {
		this.informant = informant;
	}

	public AccusationType getAcType() {
		return acType;
	}

	public void setAcType(AccusationType acType) {
		this.acType = acType;
	}

	public String getReport_content() {
		return report_content;
	}

	public void setReport_content(String report_content) {
		this.report_content = report_content;
	}

	public Date getReport_time() {
		return report_time;
	}

	public void setReport_time(Date report_time) {
		this.report_time = report_time;
	}

	public Integer getHandle_flag() {
		return handle_flag;
	}

	public void setHandle_flag(Integer handle_flag) {
		this.handle_flag = handle_flag;
	}

	public Date getReply_time() {
		return reply_time;
	}

	public void setReply_time(Date reply_time) {
		this.reply_time = reply_time;
	}

	public String getReply_content() {
		return reply_content;
	}

	public void setReply_content(String reply_content) {
		this.reply_content = reply_content;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public List<AccusationPicture> getAcPictures() {
		return acPictures;
	}

	public void setAcPictures(List<AccusationPicture> acPictures) {
		this.acPictures = acPictures;
	}
	
	

}
