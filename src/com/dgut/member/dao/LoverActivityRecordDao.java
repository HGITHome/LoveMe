package com.dgut.member.dao;

import java.util.List;

import com.dgut.member.entity.LoverActivityRecord;

public interface LoverActivityRecordDao {
	
	public List<LoverActivityRecord> getList();
	
	public LoverActivityRecord findById(Integer id);
	
	public LoverActivityRecord save(LoverActivityRecord bean);
	
	public LoverActivityRecord deleteById(Integer id);
	
}
