package com.dgut.main.manager;


import java.util.List;

import com.dgut.common.page.Pagination;
import com.dgut.main.entity.AccusationCategory;

public interface AccusationCategoryMng {

    public Pagination getPage(String queryCategoryName,Integer pageNo,Integer pageSize);
	
    public List<AccusationCategory> getList();
    
	public AccusationCategory findById(Integer id);
	
	public AccusationCategory findByCategoryName(String categoryName);
	
	public AccusationCategory save(AccusationCategory bean);
	
	public AccusationCategory update(AccusationCategory bean);
	
	public AccusationCategory deleteById(Integer id);


}
