package com.dgut.member.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.dgut.common.hibernate3.Finder;
import com.dgut.common.hibernate3.HibernateBaseDao;
import com.dgut.common.page.Pagination;
import com.dgut.member.dao.MemberInfoDao;
import com.dgut.member.entity.Member;
import com.dgut.member.entity.MemberInfo;

@Repository
public class MemberInfoDaoImpl extends HibernateBaseDao<MemberInfo, Integer> implements MemberInfoDao {

	
	public Pagination getList(String queryUsername,Integer pageNo,Integer pageSize) {
		Finder f = Finder.create("select bean from MemberInfo bean  where 1=1");
		if (!StringUtils.isBlank(queryUsername)) {
			f.append(" and bean.username like :username or bean.realname like :realname");
			f.setParam("username", "%" + queryUsername + "%");
			f.setParam("realname", "%" + queryUsername + "%");
		}
		f.append(" order by bean.id asc");
		return find(f, pageNo, pageSize);
	}

	@SuppressWarnings("unchecked")
	public List<MemberInfo> getMemberList(MemberInfo memberInfo, Integer start,Integer size){
		Finder f = Finder.create("select bean from MemberInfo bean where 1 = 1");
		f.append(" and bean.id != : id");
		f.setParam("id", memberInfo.getId());
		if(memberInfo.getGender() != null){
			f.append(" and bean.gender =: gender");
			f.setParam("gender", memberInfo.getGender());
		}
		f.append(" order by bean.id asc");
		return (List<MemberInfo>) find(f,start,size).getList();
	}
	
	public MemberInfo findById(Integer id) {
		MemberInfo bean = super.get(id);
		return bean;
	}


	public MemberInfo save(MemberInfo bean) {
		getSession().save(bean);
		return bean;
	}


	public MemberInfo deleteById(Integer id) {
		MemberInfo bean = super.get(id);
		if(bean != null){
			getSession().delete(bean);
		}
		return bean;
	}

	
	public MemberInfo[] deleteById(Integer[] ids) {
		MemberInfo[] bean = new MemberInfo[ids.length];
		for(int i = 0 ; i < ids.length ; i ++){
			bean[i] = deleteById(ids[i]);
		}
		return bean;
	}

	protected Class<MemberInfo> getEntityClass() {
		
		return MemberInfo.class;
	}

}
