package com.dgut.main.dao.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.dgut.common.hibernate3.Finder;
import com.dgut.common.hibernate3.HibernateBaseDao;
import com.dgut.common.page.Pagination;
import com.dgut.main.dao.AccusationUserDao;

import com.dgut.main.entity.AccusationUser;


@Repository
public class AccusationUserDaoImpl extends HibernateBaseDao<AccusationUser, Integer> implements
		AccusationUserDao {

	
	public Pagination getPage(String reporterName, String informantName,String  queryCategoryId, String handle_flag,Integer pageNo,Integer pageSize) {
		Finder f = Finder.create("select bean from AccusationUser bean where 1=1");
		if(!StringUtils.isBlank(reporterName)){
			f.append("and bean.reporter.memberInfo.nickname like=:reporterName");
			f.setParam("reporterName", "%"+reporterName+"%");
		}
		if(!StringUtils.isBlank(informantName)){
			f.append(" and bean.informant.memberInfo.nickname like=:informantName");
			f.setParam("informantName", "%"+informantName+"%");
		}
		if(!StringUtils.isBlank(queryCategoryId)){
			f.append(" and bean.acType.accusationCategory.id=:categoryId");
			f.setParam("categoryId", Integer.parseInt(queryCategoryId));
		}
		if(!StringUtils.isBlank(handle_flag)){
			f.append(" and bean.handle_flag =:handle_flag");
			f.setParam("handle_flag", Integer.parseInt(handle_flag));
		}
		f.append(" order by bean.id asc");
		return find(f,pageNo,pageSize);
	}

	
	public AccusationUser findById(Integer id) {
		AccusationUser bean = super.get(id);
		return bean;
	}

	
	public AccusationUser save(AccusationUser bean) {
		getSession().save(bean);
		return bean;
	}

	
	public AccusationUser deleteById(Integer id) {
		AccusationUser bean = super.get(id);
		if(bean != null){
			getSession().delete(bean);
		}
		return bean;
	}

	
	protected Class<AccusationUser> getEntityClass() {
		
		return AccusationUser.class;
	}

}
