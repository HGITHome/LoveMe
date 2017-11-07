package com.dgut.member.dao;

import java.util.List;

import com.dgut.common.hibernate3.Updater;
import com.dgut.common.page.Pagination;
import com.dgut.member.entity.Member;
import com.dgut.member.entity.MemberInfo;

public interface MemberInfoDao {
	
	public Pagination getList(String queryUsername,Integer pageNo,Integer pageSize);

	public List<MemberInfo> getMemberList(MemberInfo memberInfo, Integer start,Integer size);
	
	public MemberInfo findById(Integer id);
	
	public MemberInfo save(MemberInfo bean);
	
	public MemberInfo updateByUpdater(Updater<MemberInfo> updater);
	
	public MemberInfo deleteById(Integer id);
	
	public MemberInfo[] deleteById(Integer[] ids);
}
