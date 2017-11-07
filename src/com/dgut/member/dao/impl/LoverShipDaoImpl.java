package com.dgut.member.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.dgut.common.hibernate3.Finder;
import com.dgut.common.hibernate3.HibernateBaseDao;
import com.dgut.common.page.Pagination;
import com.dgut.member.dao.LoverShipDao;
import com.dgut.member.entity.Lovership;

@Repository
public class LoverShipDaoImpl extends HibernateBaseDao<Lovership, Integer> implements LoverShipDao {

	
	public Pagination getList(String queryUsername, String queryStatus,
			Integer pageNo, Integer pageSize) {
		Finder f = Finder.create("select bean from Lovership bean  where 1=1");
		if (!StringUtils.isBlank(queryUsername)) {
			f.append(" and bean.owner.username like :username ");
			f.setParam("username", "%" + queryUsername + "%");
		}
		if(!StringUtils.isBlank(queryStatus)){
			f.append(" and bean.lovership_status =:lovership_status");
			f.setParam("lovership_status", Integer.parseInt(queryStatus));
		}
		f.append(" order by bean.id desc");
		return find(f, pageNo, pageSize);
	}

	public Lovership findByOwnId(Integer ownId){
		Finder f = Finder.create("select bean from Lovership bean where 1=1 and bean.owner.id =:id");
		f.setParam("id", ownId);
		List<Lovership> list = find(f);
		return list.size()>0 ? list.get(0) : null;
	}

	public Lovership findById(Integer id) {
		Lovership bean = super.get(id);
		return bean;
	}

	
	public Lovership save(Lovership bean) {
		getSession().save(bean);
		return bean;
	}

	protected Class<Lovership> getEntityClass() {
		
		return Lovership.class;
	}

}
