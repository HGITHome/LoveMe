package com.dgut.member.dao;

import com.dgut.common.hibernate3.Updater;
import com.dgut.common.page.Pagination;
import com.dgut.member.entity.Member;
import com.dgut.member.entity.ThumbsUp;

public interface ThumbsUpDao {
	
	public Pagination getList(String queryPublisher,String queryReceiver,Integer pageNo,Integer pageSize);
	
	public ThumbsUp findById(Integer id);
	
	public ThumbsUp save(ThumbsUp bean);
	
	public ThumbsUp updateByUpdater(Updater<ThumbsUp> updater);
	
	public ThumbsUp deleteById(Integer id);
	
	public ThumbsUp[] deleteByIds(Integer[] ids);

	public ThumbsUp deleteByMemberReceiver(Member member, Member receiver);

	public ThumbsUp deleteByBean(ThumbsUp bean); 
}
