package com.dgut.main.dao.assist;

import com.dgut.common.hibernate3.Updater;
import com.dgut.common.page.Pagination;
import com.dgut.main.entity.DbFile;

public interface DbFileDao {
	
	public Pagination getPage(Integer pageNo,Integer pageSize);
	
	public DbFile findById(Integer id);
	
	public DbFile findByFileName(String fileName);
	
	public DbFile save(DbFile bean);
	
	public DbFile updateByUpdater(Updater<DbFile> updater);
	
	public DbFile deleteById(Integer id);
	
	public DbFile deleteByFileName(String fileName);
}
