package com.dgut.main.dao.assist.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dgut.common.hibernate3.Finder;
import com.dgut.common.hibernate3.HibernateBaseDao;
import com.dgut.common.page.Pagination;
import com.dgut.main.dao.assist.DbFileDao;
import com.dgut.main.entity.DbFile;

@Repository
public class DbFileDaoImpl extends HibernateBaseDao<DbFile, Integer> implements DbFileDao {


	public Pagination getPage(Integer pageNo,Integer pageSize) {
		Finder f = Finder.create("select bean from DbFile bean where 1=1 order by bean.id");
		return find(f,pageNo,pageSize);
	}

	
	public DbFile findById(Integer id) {
		DbFile bean = super.get(id);
		return bean;
	}

	
	public DbFile findByFileName(String fileName) {
		Finder f = Finder.create("select bean from DbFile bean where 1=1 and bean.fileName like:fileName");
		f.setParam("fileName", "%"+fileName+"%");
		List<DbFile> list = find(f);
		return list.size()>0?list.get(0):null;
	}

	
	public DbFile save(DbFile bean) {
		getSession().save(bean);
		return bean;
	}

	
	public DbFile deleteById(Integer id) {
		DbFile bean = super.get(id);
		if(bean != null){
			getSession().delete(bean);
		}
		return bean;
	}

	
	public DbFile deleteByFileName(String fileName) {
		DbFile bean = findByFileName(fileName);
		if(bean != null){
			getSession().delete(bean);
		}
		return bean;
	}

	@Override
	protected Class<DbFile> getEntityClass() {
	
		return DbFile.class;
	}

}
