package com.dgut.member.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.dgut.common.hibernate3.Finder;
import com.dgut.common.hibernate3.HibernateBaseDao;
import com.dgut.common.page.Pagination;
import com.dgut.main.entity.Admin;
import com.dgut.member.dao.ActivityDao;
import com.dgut.member.entity.Activity;

import freemarker.template.utility.StringUtil;

@Repository
public class ActivityDaoImpl extends HibernateBaseDao<Activity, Integer> implements
		ActivityDao {

	
	public Pagination getPage(String adminName, String queryActivityName,String queryActivityAddress, Integer pageNo, Integer pageSize) {
		Finder f = Finder.create("select bean from Activity bean where 1=1 ");
		if(!StringUtils.isBlank(adminName)){
			f.append(" and bean.admin.username like:adminName");
			f.setParam("adminName", adminName);
		}
		if(!StringUtils.isBlank(queryActivityName)){
			f.append(" and bean.activityName like:activityName");
			f.setParam("activityName","%" + queryActivityName + "%");
		}
		if(!StringUtils.isBlank(queryActivityAddress)){
			f.append(" and bean.activityAddress like:activityAddress");
			f.setParam("activityAddress","%"+ queryActivityAddress + "%");
		}
		f.append(" order by bean.id asc");
		return find(f,pageNo,pageSize);
	}

	public Activity findByActivityName(String queryActivityName){
		Finder f = Finder.create("select bean from Activity bean where 1=1 and bean.activityName like:activityName");
		f.setParam("activityName", "%"+queryActivityName+"%");
		List<Activity> list = find(f);
		return list.size()>0 ? list.get(0) : null;
	}
	
	public Activity findById(Integer id) {
		Activity bean = super.get(id);
		return bean;
	}

	
	public Activity save(Activity bean) {
		getSession().save(bean);
		return bean;
	}

	
	public Activity deleteById(Integer id) {
		Activity bean = super.get(id);
		if(bean != null){
			getSession().delete(bean);
		}
		return bean;
	}

	
	public Activity[] deleteByIds(Integer[] ids) {
		Activity[] activities = new Activity[ids.length];
		for(int i = 0 ; i < ids.length ; i ++){
			activities[i] = deleteById(ids[i]);
		}
		return activities;
	}

	
	protected Class<Activity> getEntityClass() {
		return Activity.class;
	}

}
