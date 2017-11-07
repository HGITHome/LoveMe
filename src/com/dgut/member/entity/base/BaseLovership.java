package com.dgut.member.entity.base;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.dgut.main.entity.Enum.Lovership_status;
import com.dgut.member.entity.LoverActivity;
import com.dgut.member.entity.Member;


public class BaseLovership implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    private Integer id; //编号

    //fields
    private Member owner; //拥有者
    private Member lover; //恋人
    protected Integer  lovership_status; //恋人关系状态
    private Date registerTime; //成为好友的时间
    private String pairUUID;

 

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}



	public Member getOwner() {
		return owner;
	}

	public void setOwner(Member owner) {
		this.owner = owner;
	}

	public Member getLover() {
		return lover;
	}

	public void setLover(Member lover) {
		this.lover = lover;
	}



	public Integer getLovership_status() {
		return lovership_status;
	}

	public void setLovership_status(Integer lovership_status) {
		this.lovership_status = lovership_status;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	
	
	
	public String getPairUUID() {
		return pairUUID;
	}

	public void setPairUUID(String pairUUID) {
		this.pairUUID = pairUUID;
	}

	public BaseLovership(Member owner, Member lover,
			Integer lovership_status, Date registerTime,String pairUUID) {
		super();
		this.owner = owner;
		this.lover = lover;
		this.lovership_status = lovership_status;
		this.registerTime = registerTime;
		this.pairUUID = pairUUID;
	}

   public  BaseLovership(){}

	

}
