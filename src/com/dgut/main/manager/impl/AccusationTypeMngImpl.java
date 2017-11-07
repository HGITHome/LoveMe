package com.dgut.main.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.dgut.common.hibernate3.Updater;
import com.dgut.common.page.Pagination;
import com.dgut.main.dao.AccusationTypeDao;
import com.dgut.main.entity.AccusationCategory;
import com.dgut.main.entity.AccusationType;
import com.dgut.main.manager.AccusationTypeMng;

@Service
@Transactional
public class AccusationTypeMngImpl implements AccusationTypeMng {

	@Transactional(readOnly=true)
	public Pagination getPage(String queryTypeName,Integer categoryId, Integer pageNo,Integer pageSize) {
		Pagination pagination  = dao.getPage(queryTypeName, categoryId, pageNo, pageSize);
		return pagination;
	}


	@Transactional(readOnly=true)
	public AccusationType findById(Integer id) {
		
		return dao.findById(id);
	}

	@Transactional(readOnly=true)
	public AccusationType findByTypeName(String typeName){
		return dao.findByTypeName(typeName);
	}
	
	public AccusationType save(AccusationType bean) {
		
		return dao.save(bean);
	}

	
	public AccusationType update(AccusationType bean) {
		Updater<AccusationType> updater = new Updater<AccusationType>(bean);
		return dao.updateByUpdater(updater);
	}

	
	public AccusationType deleteById(Integer id) {
		
		return dao.deleteById(id);
	}
	
	@Autowired
	private AccusationTypeDao dao;

}
