package com.dgut.member.dao;

import java.util.List;

import com.dgut.common.hibernate3.Updater;
import com.dgut.member.entity.LoverActivity;

public interface LoverActivityDao {
	
	public List<LoverActivity> getList();
	
	public LoverActivity findById(Integer id);
	
	public List<LoverActivity> findByPairUUID(String pairUUID);
	
	public LoverActivity findByDayAndPairUUID(Integer dayNumber, String pairUUID);
	
	public LoverActivity save(LoverActivity bean);
	
	public LoverActivity updateByUpdater(Updater<LoverActivity> updater);
	
	public LoverActivity deleteById(Integer id);
	
	public LoverActivity[] deleteByIds(Integer[] ids);

	
}
