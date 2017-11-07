package com.dgut.member.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dgut.common.hibernate3.HibernateBaseDao;
import com.dgut.member.dao.PictureMngDao;
import com.dgut.member.entity.LifePicture;

@Repository
public class PictureMngDaoImpl extends HibernateBaseDao<LifePicture, Integer> implements
		PictureMngDao {
	public List<LifePicture> getList() {
		String hql = "from LifePicture bean";
		return find(hql);
	}

	
	public LifePicture findById(Integer id) {
		LifePicture bean  = super.get(id);
		return bean;
	}

	
	public LifePicture save(LifePicture bean) {
		getSession().save(bean);
		return bean;
	}

	
	public LifePicture deleteById(Integer id) {
		LifePicture bean = super.get(id);
		if(bean != null){
			 getSession().delete(bean);
		}
		return bean;
	}

	
	public LifePicture[] deleteByIds(Integer[] ids) {
		LifePicture[] pictures = new LifePicture[ids.length];
		for(int i = 0 ; i < ids.length ; i ++){
			pictures[i] = deleteById(ids[i]);
		}
		return pictures;
	}

	
	protected Class<LifePicture> getEntityClass() {
		return LifePicture.class;
	}

}
