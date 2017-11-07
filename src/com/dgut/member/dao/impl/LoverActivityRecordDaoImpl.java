package com.dgut.member.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dgut.common.hibernate3.HibernateBaseDao;
import com.dgut.member.dao.LoverActivityRecordDao;
import com.dgut.member.entity.LoverActivityRecord;

@Repository
public class LoverActivityRecordDaoImpl extends HibernateBaseDao<LoverActivityRecord, Integer>
		implements LoverActivityRecordDao {

	
	public List<LoverActivityRecord> getList() {
		String hql = "select bean from LoverActivityRecord bean where 1=1";
		return find(hql);
	}

	
	public LoverActivityRecord findById(Integer id) {
		LoverActivityRecord bean = super.get(id);
		return bean;
	}

	
	public LoverActivityRecord save(LoverActivityRecord bean) {
		getSession().save(bean);
		return bean;
	}

	
	public LoverActivityRecord deleteById(Integer id) {
		LoverActivityRecord bean = super.get(id);
		if(bean != null){
			getSession().delete(bean);
		}
		return bean;
	}

	
	protected Class<LoverActivityRecord> getEntityClass() {
		
		return LoverActivityRecord.class;
	}

}
