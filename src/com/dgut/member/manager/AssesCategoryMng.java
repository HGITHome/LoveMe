package com.dgut.member.manager;

import java.util.List;

import com.dgut.member.entity.Category;

public interface AssesCategoryMng {
	
	public List<Category> getList();
	
	public Category findById(Integer id);
	
	public Category save(Category bean);
	 
	public Category deleteById(Integer id);
	
	public Category update(Category updater);
	
	public Category[] deleteByIds(Integer[] ids);
}
