package com.dgut.main.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dgut.common.hibernate3.Updater;
import com.dgut.common.page.Pagination;
import com.dgut.main.dao.AccusationCategoryDao;
import com.dgut.main.entity.AccusationCategory;
import com.dgut.main.manager.AccusationCategoryMng;

@Service
@Transactional
public class AccusationCategoryMngImpl implements AccusationCategoryMng {

	@Transactional(readOnly=true)
	public Pagination getPage(String queryCategoryName, Integer pageNo,
			Integer pageSize) {
		Pagination pagination = dao.getPage(queryCategoryName, pageNo, pageSize);
		return pagination;
	}

	@Transactional(readOnly=true)
	public List<AccusationCategory> getList(){
		return dao.getList();
	}
	
	@Transactional(readOnly=true)
	public AccusationCategory findById(Integer id) {
		
		return dao.findById(id);
	}

	@Transactional(readOnly=true)
	public AccusationCategory findByCategoryName(String categoryName){
		return dao.findByCategoryName(categoryName);
	}
	
	public AccusationCategory save(AccusationCategory bean) {
		
		return dao.save(bean);
	}

	
	public AccusationCategory update(AccusationCategory bean) {
		Updater<AccusationCategory> updater = new Updater<AccusationCategory>(bean);
		return dao.updateByUpdater(updater);
	}

	
	public AccusationCategory deleteById(Integer id) {
		
		return dao.deleteById(id);
	}
	
	@Autowired
	private AccusationCategoryDao dao;

}
