package com.dgut.main.manager.assist.impl;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dgut.common.hibernate3.Updater;
import com.dgut.common.page.Pagination;
import com.dgut.common.web.springmvc.RealPathResolver;
import com.dgut.main.dao.assist.DbFileDao;
import com.dgut.main.entity.DbFile;
import com.dgut.main.manager.assist.DbFileMng;

@Service
@Transactional
public class DbFileMngImpl implements DbFileMng {

	@Transactional(readOnly=true)
	public Pagination getPage(Integer pageNo, Integer pageSize) {
		
		return dao.getPage(pageNo, pageSize);
	}

	@Transactional(readOnly=true)
	public DbFile findById(Integer id) {
		
		return dao.findById(id);
	}


	@Transactional(readOnly=true)
	public DbFile findByFileName(String fileName) {
		
		return dao.findByFileName(fileName);
	}

	public void save(String backupPath, String tableName){
		DbFile bean = new DbFile();
		bean.setTableName(tableName);
		bean.setFilePath(backupPath.substring(0,backupPath.lastIndexOf("\\")+1));
		String fileName = backupPath.substring(backupPath.lastIndexOf("\\")+1, backupPath.length());
		bean.setFileName(fileName);
		File file = new File(backupPath);
		if(!file.exists() && !file.isFile()){
		  return;
		}
		bean.setLastModified(new Date(file.lastModified()));
		DecimalFormat df = new DecimalFormat("#.00"); 
		String size = df.format((double) file.length() / 1024);
		bean.setFileSize(Double.parseDouble(size));
		bean.setBackupTime(new Date());
		save(bean);
	}
	
	public DbFile save(DbFile bean) {
		
		return dao.save(bean);
	}

	
	public DbFile update(DbFile bean) {
		Updater<DbFile> updater = new Updater<DbFile>(bean);
		return dao.updateByUpdater(updater);
	}

	
	public DbFile deleteById(Integer id) {
		
		return dao.deleteById(id);
	}

	
	public DbFile deleteByFileName(String fileName) {
		
		return dao.deleteByFileName(fileName);
	}

	@Autowired
	private DbFileDao dao;

}
