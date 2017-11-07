package com.dgut.member.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dgut.common.hibernate3.HibernateBaseDao;
import com.dgut.member.dao.AssesCategoryDao;
import com.dgut.member.entity.Category;

@Repository
public class AssesCategoryDaoImpl extends HibernateBaseDao<Category, Integer> implements AssesCategoryDao {


	public List<Category> getList() {
		String hql = "from Category bean order by bean.priority asc";
		return find(hql);
	}

	
	public Category findById(Integer id) {
		Category bean = super.get(id);
		return bean;
	}

	
	public Category save(Category bean) {
		getSession().save(bean);
		return bean;
	}

	
	public Category deleteById(Integer id) {
		Category bean = super.get(id);
		if(bean != null){
			getSession().delete(bean);
		}
		return bean;
	}

	
	public Category[] deleteByIds(Integer[] ids) {
		Category[] categorys =  new Category[ids.length];
		for(int i = 0 ; i < ids.length ; i ++){
			categorys[i] = deleteById(ids[i]);
		}
		return categorys;
	}

	
	protected Class<Category> getEntityClass() {
		return Category.class;
	}



}
