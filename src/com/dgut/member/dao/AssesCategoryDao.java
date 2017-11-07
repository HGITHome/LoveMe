package com.dgut.member.dao;

import java.util.List;

import com.dgut.common.hibernate3.Updater;
import com.dgut.member.entity.Category;


public interface AssesCategoryDao {
	
	public List<Category> getList();
	
	public Category findById(Integer id);
	
	public Category save(Category bean);
	 
	public Category deleteById(Integer id);
	
	public Category  updateByUpdater(Updater<Category> updater);
	
	public Category[] deleteByIds(Integer[] ids);
}
