package com.dgut.member.manager;



import com.dgut.common.page.Pagination;
import com.dgut.main.entity.Admin;
import com.dgut.member.entity.Activity;

public interface ActivityMng {
	
    public Pagination getPage(String admin ,String queryActivityName,String queryActivityAddress,Integer pageNo,Integer pageSize);
	
	public Activity findById(Integer id);
	
	public Activity save(Activity bean);
	
	public Activity update(Activity bean);
	
	public Activity deleteById(Integer id);
	
	public Activity[] deleteByIds(Integer[] ids);

	public Activity save(Admin admin, String queryActivityName,
			String queryAddress, String days, String activityContent);

	public Activity findByActivityName(String queryActivityName);
}
