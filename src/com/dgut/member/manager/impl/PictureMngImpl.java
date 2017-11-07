package com.dgut.member.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dgut.common.hibernate3.Updater;
import com.dgut.member.dao.PictureMngDao;
import com.dgut.member.entity.LifePicture;
import com.dgut.member.manager.PictureMng;

@Service
@Transactional
public class PictureMngImpl implements PictureMng {


	@Transactional(readOnly = true)
	public List<LifePicture> getList() {
		return dao.getList();
	}

	@Transactional(readOnly = true)
	public LifePicture findById(Integer id) {
		
		return dao.findById(id);
	}


	public LifePicture save(LifePicture bean) {
		
		return dao.save(bean);
	}


	public LifePicture update(LifePicture bean) {
		Updater<LifePicture> updater = new Updater<LifePicture>(bean);
		bean = dao.updateByUpdater(updater);
		return bean;
	}


	public LifePicture deleteById(Integer id) {
	
		return dao.deleteById(id);
	}

	
	public LifePicture[] deleteByIds(Integer[] ids) {
		
		return dao.deleteByIds(ids);
	}
	
	@Autowired
	private PictureMngDao dao;
}
