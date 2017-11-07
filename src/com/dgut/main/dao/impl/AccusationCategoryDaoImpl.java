package com.dgut.main.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.dgut.common.hibernate3.Finder;
import com.dgut.common.hibernate3.HibernateBaseDao;
import com.dgut.common.hibernate3.Updater;
import com.dgut.common.page.Pagination;
import com.dgut.main.dao.AccusationCategoryDao;
import com.dgut.main.entity.AccusationCategory;

@Repository
public class AccusationCategoryDaoImpl extends HibernateBaseDao<AccusationCategory, Integer>
		implements AccusationCategoryDao {

	
	public Pagination getPage(String queryCategoryName, Integer pageNo,Integer pageSize) {
		Finder f = Finder.create("select bean from AccusationCategory bean where 1=1");
		if(!StringUtils.isBlank(queryCategoryName)){
			f.append(" and bean.categoryName =:categoryName");
			f.setParam("categoryName", queryCategoryName);
		}
		f.append(" order by bean.id asc");
		return find(f,pageNo,pageSize);
	}

	public List<AccusationCategory> getList(){
		String hql="select bean from AccusationCategory bean where 1=1";
		return find(hql);
	}
	
	public AccusationCategory findById(Integer id) {
		AccusationCategory bean = super.get(id);
		return bean;
	}

	public AccusationCategory findByCategoryName(String categoryName){
		Finder f = Finder.create("select bean from AccusationCategory bean where 1=1 and bean.categoryName like:categoryName");
		f.setParam("categoryName", categoryName);
		List<AccusationCategory> list = find(f);
		return list.size()>0 ? list.get(0) : null;
	}
	
	
	public AccusationCategory save(AccusationCategory bean) {
		getSession().save(bean);
		return bean;
	}


	
	public AccusationCategory deleteById(Integer id) {
		AccusationCategory bean = super.get(id);
		if(bean != null){
			getSession().delete(bean);
		}
		return bean;
	}

	
	protected Class<AccusationCategory> getEntityClass() {
		
		return AccusationCategory.class;
	}

}
