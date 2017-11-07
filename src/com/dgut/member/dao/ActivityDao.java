package com.dgut.member.dao;

import com.dgut.common.hibernate3.Updater;
import com.dgut.common.page.Pagination;
import com.dgut.main.entity.Admin;
import com.dgut.member.entity.Activity;

public interface ActivityDao {
	
	public Pagination getPage(String adminName ,String queryActivityName,String queryActivityAddress,Integer pageNo,Integer pageSize);
	
	public Activity findById(Integer id);
	
	public Activity findByActivityName(String queryActivityName);
	
	public Activity save(Activity bean);
	
	public Activity updateByUpdater(Updater<Activity> updater);
	
	public Activity deleteById(Integer id);
	
	public Activity[] deleteByIds(Integer[] ids);

	
}
