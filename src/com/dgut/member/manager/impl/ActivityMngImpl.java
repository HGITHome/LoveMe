package com.dgut.member.manager.impl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dgut.common.hibernate3.Updater;
import com.dgut.common.page.Pagination;
import com.dgut.common.util.DateUtils;
import com.dgut.main.entity.Admin;
import com.dgut.member.dao.ActivityDao;
import com.dgut.member.entity.Activity;
import com.dgut.member.manager.ActivityMng;

@Service
@Transactional
public class ActivityMngImpl implements ActivityMng {

	@Transactional(readOnly = true)
	public Pagination getPage(String adminName, String queryActivityName,
			String queryActivityAddress, Integer pageNo, Integer pageSize) {
		Pagination pagination = dao.getPage(adminName, queryActivityName, queryActivityAddress, pageNo, pageSize);
		return pagination;
	}

	@Transactional(readOnly = true)
	public Activity findById(Integer id) {
		
		return dao.findById(id);
	}

	@Transactional(readOnly=true)
	public Activity findByActivityName(String queryActivityName){
		return dao.findByActivityName(queryActivityName);
	}
	public Activity save(Admin admin, String queryActivityName,
			String queryAddress, String days, String activityContent){
		Activity bean = new Activity();
		bean.setAdmin(admin);
		bean.setActivityName(queryActivityName);
		bean.setActivityAddress(queryAddress);
		bean.setCompletion_time(Integer.parseInt(days));
		bean.setActivityContent(activityContent);
		bean.setRegister_time(new Date());
		bean.setLastUpdateTime(new Date());
		save(bean);
		return bean;
	}
	
	public Activity save(Activity bean) {
		
		return dao.save(bean);
	}

	
	public Activity update(Activity bean) {
		bean.setLastUpdateTime(new Date());
		Updater<Activity> updater = new Updater<Activity>(bean);
		return dao.updateByUpdater(updater);
	}

	
	public Activity deleteById(Integer id) {
		
		return dao.deleteById(id);
	}

	
	public Activity[] deleteByIds(Integer[] ids) {
		
		return dao.deleteByIds(ids);
	}
	
	@Autowired
	private ActivityDao dao;

}
