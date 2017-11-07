package com.dgut.main.dao;

import com.dgut.common.hibernate3.Updater;
import com.dgut.common.page.Pagination;
import com.dgut.main.entity.AccusationType;
import com.dgut.main.entity.AccusationUser;


public interface AccusationUserDao {
	
	public Pagination getPage(String reporterName,String informantName,String queryCategoryId,String handle_flag,Integer pageNo,Integer pageSize);
	
	public AccusationUser findById(Integer id);
	
	public AccusationUser save(AccusationUser bean);
	
	public AccusationUser updateByUpdater(Updater<AccusationUser> updaer);
	
	public AccusationUser deleteById(Integer id);
}
