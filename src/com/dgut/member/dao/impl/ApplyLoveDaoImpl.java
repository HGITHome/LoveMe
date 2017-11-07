package com.dgut.member.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.dgut.common.hibernate3.Finder;
import com.dgut.common.hibernate3.HibernateBaseDao;
import com.dgut.common.hibernate3.Updater;
import com.dgut.common.page.Pagination;
import com.dgut.main.entity.Enum.ApplyFlag;
import com.dgut.member.dao.ApplyLoverDao;
import com.dgut.member.entity.ApplyLover;
import com.dgut.member.entity.Member;
@Repository
public class ApplyLoveDaoImpl extends HibernateBaseDao<ApplyLover,Integer> implements
		ApplyLoverDao {

	
	public Pagination getList(String queryUsername, String status,Integer pageNo,Integer pageSize) {
		Finder f = Finder.create("select bean from ApplyLover bean  where 1=1");
		if (!StringUtils.isBlank(queryUsername)) {
			f.append(" and bean.publisher.username like :username");
			f.setParam("username", "%" + queryUsername + "%");
		}
		if(!StringUtils.isBlank(status)){
			f.append("and bean.handle_flag =:handle_flag");
			f.setParam("handle_flag",Integer.parseInt(status));
		}
		f.append(" order by bean.id desc");
		return find(f,pageNo,pageSize);
	}

	
	public ApplyLover findById(Integer id) {
		ApplyLover bean = super.get(id);
		return bean;
	}

	public ApplyLover getPreviousApply(Member publisher,Member receiver,Integer status){
		Finder f = Finder.create("select bean from ApplyLover bean where 1=1");
		if(publisher != null){
			f.append(" and bean.publisher =:publisher");
			f.setParam("pid", publisher);
		}
		if(receiver != null){
			f.append(" and bean.receiver =:receiver");
			f.setParam("receiver", receiver);
		}
		if(status != null){
			f.append(" and bean.handle_flag =:status");
			f.setParam("status", status);
		}
		List<ApplyLover> list = find(f);
		return list.size() > 0 ? list.get(0) : null;
	}

	public List<ApplyLover> findByPublisherId(Integer id,Integer status) {
		Finder f = Finder.create("select bean from ApplyLover bean where 1=1");
		if(id != null){
			f.append(" and bean.publisher.id =: id");
			f.setParam("id", id);
		}
		if(status != null){
			f.append(" and bean.handle_flag =:status");
			f.setParam("status", status);
		}
		f.append(" order by bean.id asc");
		return find(f);
	}


	
	public List<ApplyLover> findByReceiverId(Integer id,Integer status) {
		Finder f = Finder.create("select bean from ApplyLover bean where");
		if(id != null){
			f.append(" and bean.receiver.id =: id");
			f.setParam("id", id);
		}
		if(status != null){
			f.append(" and bean.handle_flag =:status");
			f.setParam("status", status);
		}
		f.append(" order by bean.id asc");
		return find(f);
	}
	
	public ApplyLover save(ApplyLover bean) {
		getSession().save(bean);
		return bean;
	}


	protected Class<ApplyLover> getEntityClass() {
		return ApplyLover.class;
	}




}
