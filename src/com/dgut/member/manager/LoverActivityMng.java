package com.dgut.member.manager;

import java.util.List;




import com.dgut.member.entity.LoverActivity;

public interface LoverActivityMng {
   
	public List<LoverActivity> getList();
	
	public LoverActivity findById(Integer id);
	
	public List<LoverActivity> findByPairUUID(String pairUUID);
	
	public LoverActivity findByDayAndPairUUID(Integer dayNumber,String pairUUID);
	
	public LoverActivity save(LoverActivity bean);
	
	public LoverActivity update(LoverActivity updater);
	
	public LoverActivity deleteById(Integer id);
	
	public LoverActivity[] deleteByIds(Integer[] ids);

	public LoverActivity saveLoverActivity(String pairUUID, String tableValues,
			String dayNumbers);
}
