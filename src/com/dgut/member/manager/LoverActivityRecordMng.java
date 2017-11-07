package com.dgut.member.manager;

import java.util.List;

import com.dgut.member.entity.LoverActivityRecord;

public interface LoverActivityRecordMng {
	
    public List<LoverActivityRecord> getList();
	
	public LoverActivityRecord findById(Integer id);
	
	public LoverActivityRecord save(LoverActivityRecord bean);
	
	public LoverActivityRecord deleteById(Integer id);
	
}
