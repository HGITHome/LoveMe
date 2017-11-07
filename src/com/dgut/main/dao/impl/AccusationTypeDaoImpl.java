package com.dgut.main.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.dgut.common.hibernate3.Finder;
import com.dgut.common.hibernate3.HibernateBaseDao;
import com.dgut.common.page.Pagination;
import com.dgut.main.dao.AccusationTypeDao;
import com.dgut.main.entity.AccusationCategory;
import com.dgut.main.entity.AccusationType;

@Repository
public class AccusationTypeDaoImpl extends HibernateBaseDao<AccusationType, Integer> implements AccusationTypeDao {

	
	public Pagination getPage(String queryTypeName,Integer categoryId, Integer pageNo,Integer pageSize) {
		Finder f = Finder.create("select bean from AccusationType bean where 1=1");
		if(!StringUtils.isBlank(queryTypeName)){
			f.append(" and bean.typeName =:typeName");
			f.setParam("typeName", queryTypeName);
		}
		if(categoryId != null){
			f.append(" and bean.accusationCategory.id =:categoryId");
			f.setParam("categoryId", categoryId);
		}
		f.append(" order by bean.id asc");
		return find(f, pageNo, pageSize);
	}

	
	public AccusationType findById(Integer id) {
		 AccusationType bean = super.get(id);
		return bean;
	}

	public AccusationType findByTypeName(String typeName){
		Finder f = Finder.create("select bean from AccusationType bean where 1=1 and bean.typeName like:typeName");
		f.setParam("typeName", "%"+typeName+"%");
		List<AccusationType> list = find(f);
		return list.size() > 0 ? list.get(0) : null;
	}
	
	public AccusationType save(AccusationType bean) {
		getSession().save(bean);
		return bean;
	}

	
	public AccusationType deleteById(Integer id) {
		AccusationType bean = super.get(id);
		if(bean != null){
			getSession().delete(bean);
		}
		return bean;
	}

	
	protected Class<AccusationType> getEntityClass() {
		
		return AccusationType.class;
	}

}
