package com.dgut.member.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dgut.common.hibernate3.HibernateBaseDao;
import com.dgut.member.dao.ProvinceDao;
import com.dgut.member.entity.Province;

@Repository
public class ProvinceDaoImpl extends HibernateBaseDao<Province, Integer> implements ProvinceDao {

	
	public List<Province> getlist() {
		String hql = "from Province bean order by bean.id asc";
		return find(hql);
	}

	
	public Province findById(Integer id) {
		Province bean = super.get(id);
		return bean;
	}

	
	public Province save(Province bean) {
	     getSession().save(bean);
		 return bean;
	}

	
	public Province deleteById(Integer id) {
		Province bean = super.get(id);
		if(bean != null){
			getSession().delete(bean);
		}
		return bean;
	}

	
	public Province[] deleteByIds(Integer[] ids) {
		Province[] provinces = new Province[ids.length];
		for(int  i = 0 ; i < ids.length ; i ++){
			provinces[i] = deleteById(ids[i]);
		}
		return provinces;
	}

	
	protected Class<Province> getEntityClass() {
		
		return Province.class;
	}

}
