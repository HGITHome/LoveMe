package com.dgut.member.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dgut.member.dao.LoverActivityRecordDao;
import com.dgut.member.entity.LoverActivityRecord;
import com.dgut.member.manager.LoverActivityRecordMng;

@Service
@Transactional
public class LoverActivityRecordMngImpl implements LoverActivityRecordMng {

	@Transactional(readOnly=true)
	public List<LoverActivityRecord> getList() {
		
		return dao.getList();
	}

	@Transactional(readOnly=true)
	public LoverActivityRecord findById(Integer id) {
		
		return dao.findById(id);
	}

	
	public LoverActivityRecord save(LoverActivityRecord bean) {
		
		return dao.save(bean);
	}

	
	public LoverActivityRecord deleteById(Integer id) {
		
		return dao.deleteById(id);
	}
	
	@Autowired
	private LoverActivityRecordDao dao;
}
