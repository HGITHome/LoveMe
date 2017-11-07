package com.dgut.member.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dgut.common.hibernate3.Updater;
import com.dgut.member.dao.TypeDao;
import com.dgut.member.entity.Type;
import com.dgut.member.manager.TypeMng;

@Service
@Transactional
public class TypeMngImpl implements TypeMng {

	@Transactional(readOnly=true)
	public List<Type> getList() {
		List<Type> list = dao.getList();
		return list;
	}

	@Transactional(readOnly=true)
	public Type findById(String id) {
		Type bean = dao.findById(id);
		return bean;
	}

	
	public Type save(Type bean) {
		bean = dao.save(bean);
		return bean;
	}

	
	public Type deleteById(String id) {
		Type bean = dao.deleteById(id);
		return bean;
	}

	
	public Type update(Type bean) {
		Updater<Type> updater = new Updater<Type>(bean);
		bean = dao.updateByUpdater(updater);
		return bean;
	}

	
	public Type[] deleteByIds(String[] ids) {
		Type[] types = dao.deleteByIds(ids);
		return types;
	}

	@Autowired
	private TypeDao dao;
}
