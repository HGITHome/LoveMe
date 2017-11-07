package com.dgut.member.entity.base;

import java.io.Serializable;
import java.util.Date;

import com.dgut.main.entity.Enum.ApplyFlag;
import com.dgut.member.entity.Member;

public class BaseApplyLover implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private java.lang.Integer id;
	private Member publisher;
	private Member receiver;
	

    private Integer handle_flag;

    private String apply_reason;  //申请理由

    private Date applyTime;//申请时间
    private Date replyTime;//处理时间





	public java.lang.Integer getId() {
		return id;
	}




	public void setId(java.lang.Integer id) {
		this.id = id;
	}









	public Member getPublisher() {
		return publisher;
	}




	public void setPublisher(Member publisher) {
		this.publisher = publisher;
	}




	public Member getReceiver() {
		return receiver;
	}




	public void setReceiver(Member receiver) {
		this.receiver = receiver;
	}







	public Integer getHandle_flag() {
		return handle_flag;
	}




	public void setHandle_flag(Integer handle_flag) {
		this.handle_flag = handle_flag;
	}




	public String getApply_reason() {
		return apply_reason;
	}




	public void setApply_reason(String apply_reason) {
		this.apply_reason = apply_reason;
	}




	public Date getApplyTime() {
		return applyTime;
	}




	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}




	public Date getReplyTime() {
		return replyTime;
	}




	public void setReplyTime(Date replyTime) {
		this.replyTime = replyTime;
	}
    
}
