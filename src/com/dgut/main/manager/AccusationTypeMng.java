package com.dgut.main.manager;

import com.dgut.common.page.Pagination;
import com.dgut.main.entity.AccusationCategory;
import com.dgut.main.entity.AccusationType;

public interface AccusationTypeMng {

   public Pagination getPage(String queryTypeName,Integer categoryId,Integer pageNo,Integer pageSize);
	
	public AccusationType findById(Integer id);
	
	public AccusationType save(AccusationType bean);
	
	public AccusationType update(AccusationType bean);
	
	public AccusationType deleteById(Integer id);

	public AccusationType findByTypeName(String typeName);
}
