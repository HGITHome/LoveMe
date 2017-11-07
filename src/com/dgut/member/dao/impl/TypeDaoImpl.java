package com.dgut.member.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dgut.common.hibernate3.HibernateBaseDao;
import com.dgut.member.dao.TypeDao;
import com.dgut.member.entity.Type;

@Repository
public class TypeDaoImpl extends HibernateBaseDao<Type, String> implements TypeDao {

	public List<Type> getList() {
		String hql="from Type bean";
		return find(hql);
	}

	public Type findById(String id) {
		Type bean = super.get(id);
		return bean;
	}


	public Type save(Type bean) {
		getSession().save(bean);
		return bean;
	}


	public Type deleteById(String id) {
		Type bean = super.get(id);
		if(bean != null){
			 getSession().delete(bean);
		}
		return bean;
	}


	public Type[] deleteByIds(String[] ids) {
		Type[] types = new Type[ids.length];
		for(int i = 0 ; i < ids.length ; i ++){
			types[i] = deleteById(ids[i]);
		}
		return types;
	}

	protected Class<Type> getEntityClass() {
		return Type.class;
	}

}
