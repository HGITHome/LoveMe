package com.dgut.member.entity;

import java.util.Date;







import com.dgut.member.entity.base.BaseLovership;

public class Lovership extends BaseLovership {


	public Lovership(){}

	public Lovership(Member owner, Member lover,
			Integer lovership_status, Date registerTime,String pairUUID) {
		super(owner, lover, lovership_status, registerTime, pairUUID);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	

	
}
