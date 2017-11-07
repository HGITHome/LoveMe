package com.dgut.member.manager;

import java.text.ParseException;

import com.dgut.common.page.Pagination;
import com.dgut.member.entity.ApplyLover;
import com.dgut.member.entity.Member;

public interface ApplyLoverMng {
	
	public Pagination getList(String queryUsername,String status,Integer pageNo,Integer pageSize);
	
	public ApplyLover findByPublisherReceiver(Member publisher,Member receiver,Integer handle_flag);
	
	public ApplyLover findById(Integer id);
	
	public ApplyLover save(Member publisher,Member receiver,String apply_reason) throws ParseException;
	
	public ApplyLover save(ApplyLover bean);
	
	public ApplyLover update(ApplyLover bean);
}
