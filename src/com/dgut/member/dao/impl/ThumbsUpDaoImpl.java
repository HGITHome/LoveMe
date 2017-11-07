package com.dgut.member.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.dgut.common.hibernate3.Finder;
import com.dgut.common.hibernate3.HibernateBaseDao;
import com.dgut.common.hibernate3.Updater;
import com.dgut.common.page.Pagination;
import com.dgut.member.dao.ThumbsUpDao;
import com.dgut.member.entity.Member;
import com.dgut.member.entity.ThumbsUp;

@Repository
public class ThumbsUpDaoImpl extends HibernateBaseDao<ThumbsUp, Integer> implements
		ThumbsUpDao {

	
	public Pagination getList(String queryPublisher, String queryReceiver,Integer pageNo, Integer pageSize) {
		Finder f = Finder.create("select bean from ThumbsUp bean where 1=1");
		if(!StringUtils.isBlank(queryPublisher)){
			f.append("and bean.publisher.username like:username");
			f.setParam("username", "%"+queryPublisher+"%");
		}
		if(!StringUtils.isBlank(queryReceiver)){
			f.append(" and bean.receiver.username like:username");
			f.setParam("username", "%"+queryReceiver+"%");
		}
		f.append(" order by bean.id asc");
		return find(f,pageNo,pageSize);
	}

	

	
	public ThumbsUp deleteByMemberReceiver(Member member, Member receiver) {
		Finder f = Finder.create("select bean from ThumbsUp bean where 1=1");
		f.append(" and bean.publisher =:publisher and bean.receiver =:receiver");
		f.setParam("publisher", member).setParam("receiver", receiver);
		List<ThumbsUp> list = find(f);
		return list.size() > 0 ? list.get(0) : null;
	}



	
	public ThumbsUp findById(Integer id) {
		ThumbsUp bean = super.get(id);
		return bean;
	}

	
	public ThumbsUp save(ThumbsUp bean) {
		getSession().save(bean);
		return bean;
	}

	public ThumbsUp deleteByBean(ThumbsUp bean){
		getSession().delete(bean);
		return bean;
	}

	public ThumbsUp deleteById(Integer id) {
		ThumbsUp bean = super.get(id);
		if(bean != null){
			getSession().delete(bean);
		}
		return bean;
	}

	
	public ThumbsUp[] deleteByIds(Integer[] ids) {
		ThumbsUp[] thumbsUps = new ThumbsUp[ids.length];
		for(int  i = 0 ; i < ids.length ; i ++){
			thumbsUps[i] = deleteById(ids[i]);
		}
		return thumbsUps;
	}

	
	protected Class<ThumbsUp> getEntityClass() {
		return ThumbsUp.class;
	}


	

}
