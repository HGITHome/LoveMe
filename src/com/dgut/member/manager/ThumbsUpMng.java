package com.dgut.member.manager;


import java.text.ParseException;

import com.dgut.common.page.Pagination;
import com.dgut.member.entity.Member;
import com.dgut.member.entity.ThumbsUp;

public interface ThumbsUpMng {

public Pagination getList(String queryPublisher,String queryReceiver,Integer pageNo,Integer pageSize);
	
	public ThumbsUp findById(Integer id);
	
	public ThumbsUp save(ThumbsUp bean);
	
	public ThumbsUp update(ThumbsUp bean);
	
	public ThumbsUp deleteById(Integer id);
	
	public ThumbsUp deleteByBean(ThumbsUp bean);
	
	public ThumbsUp[] deleteByIds(Integer[] ids);

	public ThumbsUp save(Member member, Member receiver) throws ParseException;

	public ThumbsUp deleteByMemberReceiver(Member member, Member receiver) throws Exception; 
}
