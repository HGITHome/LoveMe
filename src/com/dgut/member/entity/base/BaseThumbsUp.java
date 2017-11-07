package com.dgut.member.entity.base;

import java.io.Serializable;
import java.util.Date;

import com.dgut.member.entity.Member;

public class BaseThumbsUp implements Serializable{

	/**
	 * 点赞类
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private Member publisher;
	
	private Member receiver;
	
	private Date thumbs_up_time;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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
	
	

	public Date getThumbs_up_time() {
		return thumbs_up_time;
	}

	public void setThumbs_up_time(Date thumbs_up_time) {
		this.thumbs_up_time = thumbs_up_time;
	}

	public BaseThumbsUp() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
