package com.dgut.main.manager.assist;

import com.dgut.common.page.Pagination;
import com.dgut.main.entity.DbFile;

public interface DbFileMng {

   public Pagination getPage(Integer pageNo,Integer pageSize);
	
	public DbFile findById(Integer id);
	
	public DbFile findByFileName(String fileName);
	
	public DbFile save(DbFile bean);
	
	public DbFile update(DbFile bean);
	
	public DbFile deleteById(Integer id);
	
	public DbFile deleteByFileName(String fileName);

	public void save(String backupPath, String tableName);
}
