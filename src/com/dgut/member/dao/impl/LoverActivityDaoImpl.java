package com.dgut.member.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dgut.common.hibernate3.Finder;
import com.dgut.common.hibernate3.HibernateBaseDao;
import com.dgut.common.hibernate3.Updater;
import com.dgut.member.dao.LoverActivityDao;
import com.dgut.member.entity.LoverActivity;

@Repository
public class LoverActivityDaoImpl extends HibernateBaseDao<LoverActivity,Integer> implements
		LoverActivityDao {

	
	public List<LoverActivity> getList() {
		String hql = "select bean from LoverActivity bean where 1=1";
		return find(hql);
	}

	
	public LoverActivity findById(Integer id) {
		LoverActivity bean = super.get(id);
		return bean;
	}

	public LoverActivity findByDayAndPairUUID(Integer dayNumber, String pairUUID){
		Finder f = Finder.create("select bean from LoverActivity bean where 1=1 and bean.day_number =:day_number and bean.loverUUID =:loverUUID");
		f.setParam("day_number", dayNumber).setParam("loverUUID", pairUUID);
		List<LoverActivity> list = find(f);
		return list.size() > 0 ? list.get(0) : null;
	}
	
	public List<LoverActivity> findByPairUUID(String pairUUID) {
		Finder f = Finder.create("select bean from LoverActivity bean where 1=1 and bean.loverUUID =:loverUUID order by bean.day_number asc");
		f.setParam("loverUUID", pairUUID);
		return find(f);
	}

	
	public LoverActivity save(LoverActivity bean) {
		getSession().save(bean);
		return bean;
	}


	
	public LoverActivity deleteById(Integer id) {
		LoverActivity bean = super.get(id);
		if(bean != null){
			getSession().delete(bean);
		}
		return bean;
	}

	
	public LoverActivity[] deleteByIds(Integer[] ids) {
		LoverActivity[] loverActivities = new LoverActivity[ids.length];
		for(int i = 0 ; i < ids.length ; i ++){
			loverActivities[i] = deleteById(ids[i]);
		}
		return loverActivities;
	}

	
	protected Class<LoverActivity> getEntityClass() {
		
		return LoverActivity.class;
	}

}
