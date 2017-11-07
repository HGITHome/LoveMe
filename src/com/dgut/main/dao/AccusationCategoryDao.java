package com.dgut.main.dao;

import java.util.List;

import com.dgut.common.hibernate3.Updater;
import com.dgut.common.page.Pagination;
import com.dgut.main.entity.AccusationCategory;

public interface AccusationCategoryDao {
	public Pagination getPage(String queryCategoryName,Integer pageNo,Integer pageSize);
	
	public AccusationCategory findById(Integer id);
	
	public AccusationCategory findByCategoryName(String categoryName);
	
	public AccusationCategory save(AccusationCategory bean);
	
	public AccusationCategory updateByUpdater(Updater<AccusationCategory> updater);
	
	public AccusationCategory deleteById(Integer id);

	public List<AccusationCategory> getList();

	
}
