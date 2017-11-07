package com.dgut.main.dao;


import com.dgut.common.hibernate3.Updater;
import com.dgut.common.page.Pagination;
import com.dgut.main.entity.AccusationCategory;
import com.dgut.main.entity.AccusationType;

public interface AccusationTypeDao {
	
	public Pagination getPage(String queryTypeName,Integer categoryId,Integer pageNo,Integer pageSize);
	
	public AccusationType findById(Integer id);
	
	public AccusationType save(AccusationType bean);
	
	public AccusationType updateByUpdater(Updater<AccusationType> updater);
	
	public AccusationType deleteById(Integer id);

	public AccusationType findByTypeName(String typeName);
}
