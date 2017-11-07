package com.dgut.member.manager.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dgut.common.hibernate3.Updater;
import com.dgut.common.page.Pagination;
import com.dgut.member.dao.MemberInfoDao;
import com.dgut.member.entity.Member;
import com.dgut.member.entity.MemberInfo;
import com.dgut.member.manager.MemberInfoMng;
import com.dgut.member.manager.MemberMng;

@Service
@Transactional
public class MemberInfoMngImpl implements MemberInfoMng {

	@Transactional(readOnly = true)
	public Pagination getList(String queryUsername,Integer pageNo,Integer pageSize) {
		Pagination pagination = dao.getList(queryUsername,pageNo,pageSize);
		return pagination;
	}

	@Transactional(readOnly = true)
	public List<MemberInfo> getMemberList(MemberInfo memberInfo, Integer start,Integer size){
		List<MemberInfo> memberList = dao.getMemberList(memberInfo, start, size);
		for(int i = 0 ; memberList.size() > 0 && i < memberList.size() ; i ++){
			memberMng.convertMemberInfoEntity(memberList.get(i));
		}
		return memberList;
	}
	
	@Transactional(readOnly=true)
	public MemberInfo findById(Integer id) {
		MemberInfo bean = dao.findById(id);
		return bean;
	}

	
	public MemberInfo save(MemberInfo bean) {
		bean = dao.save(bean);
		return bean;
	}

	
	public MemberInfo update(MemberInfo bean) {
		Updater<MemberInfo> updater = new Updater<MemberInfo>(bean);
		bean = dao.updateByUpdater(updater);
		return bean;
	}

	
	public MemberInfo deleteById(Integer id) {
		MemberInfo bean = dao.deleteById(id);
		return bean;
	}

	
	public MemberInfo[] deleteById(Integer[] ids) {
		MemberInfo[] bean = dao.deleteById(ids);
		return bean;
	}
	
	@Autowired
	private MemberInfoDao dao;
	
	@Autowired
	private MemberMng memberMng;
}
