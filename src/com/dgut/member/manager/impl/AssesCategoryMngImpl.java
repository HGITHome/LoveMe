package com.dgut.member.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dgut.common.hibernate3.Updater;
import com.dgut.member.dao.AssesCategoryDao;
import com.dgut.member.entity.Category;
import com.dgut.member.manager.AssesCategoryMng;
@Service
@Transactional
public class AssesCategoryMngImpl implements AssesCategoryMng {

	@Transactional(readOnly = true)
	public List<Category> getList() {
		List<Category> list = dao.getList();
		return list;
	}

	@Transactional(readOnly = true)
	public Category findById(Integer id) {
		Category bean = dao.findById(id);
		return bean;
	}

	
	public Category save(Category bean) {
		dao.save(bean);
		return bean;
	}


	public Category deleteById(Integer id) {
		Category bean = dao.deleteById(id);
		return bean;
	}

	
	public Category update(Category bean) {
		Updater<Category> updater = new Updater<Category>(bean);
		bean = dao.updateByUpdater(updater);
		return bean;
	}

	
	public Category[] deleteByIds(Integer[] ids) {
		Category[] categorys = dao.deleteByIds(ids);
		return categorys;
	}
	
	@Autowired
    private AssesCategoryDao dao;
}
