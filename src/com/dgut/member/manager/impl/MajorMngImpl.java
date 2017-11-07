package com.dgut.member.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dgut.common.hibernate3.Updater;
import com.dgut.member.dao.MajorDao;
import com.dgut.member.entity.Major;
import com.dgut.member.manager.MajorMng;
@Service
@Transactional
public class MajorMngImpl implements MajorMng {

	@Transactional(readOnly=true)
	public List<Major> getList() {
		List<Major> list = dao.getList();
		return list;
	}

	@Transactional(readOnly=true)
	public Major findById(Integer id) {
		Major major = dao.findById(id);
		return major;
	}

	
	public Major save(Major bean) {
		dao.save(bean);
		return bean;
	}


	public Major updateByUpdater(Major bean) {
		Updater<Major> updater = new Updater<Major>(bean);
		bean = dao.updateByUpdater(updater);
		return bean;
	}

	
	public Major deleteById(Integer id) {
	       dao.deleteById(id);
		return null;
	}

	
	public Major[] deleteByIds(Integer[] ids) {
		Major[] majors = new Major[ids.length];
		for(int i = 0; i < ids.length ; i ++){
			majors[i] = deleteById(ids[i]);
		}
		return null;
	}
	
	@Autowired
	private MajorDao dao;

}
