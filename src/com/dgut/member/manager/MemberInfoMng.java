package com.dgut.member.manager;

import java.util.List;

import com.dgut.common.page.Pagination;
import com.dgut.member.entity.Member;
import com.dgut.member.entity.MemberInfo;

public interface MemberInfoMng {
	
	public Pagination getList(String queryUsername,Integer pageNo, Integer pageSize);
	
	public List<MemberInfo> getMemberList(MemberInfo memberInfo, Integer start,Integer size);
	
	public MemberInfo findById(Integer id);
	
	public MemberInfo save(MemberInfo bean);
	
	public MemberInfo update(MemberInfo bean);
	
	public MemberInfo deleteById(Integer id);
	
	public MemberInfo[] deleteById(Integer[] ids);
}
