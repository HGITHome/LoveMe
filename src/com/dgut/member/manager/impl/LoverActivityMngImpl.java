package com.dgut.member.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dgut.common.hibernate3.Updater;
import com.dgut.member.dao.LoverActivityDao;
import com.dgut.member.entity.LoverActivity;
import com.dgut.member.manager.ActivityMng;
import com.dgut.member.manager.LoverActivityMng;

@Service
@Transactional
public class LoverActivityMngImpl implements LoverActivityMng {

	@Transactional(readOnly=true)
	public List<LoverActivity> getList() {
		
		return dao.getList();
	}

	@Transactional(readOnly=true)
	public LoverActivity findById(Integer id) {
		
		return dao.findById(id);
	}

	@Transactional(readOnly=true)
	public List<LoverActivity> findByPairUUID(String pairUUID) {
		
		return dao.findByPairUUID(pairUUID);
	}
	
	@Transactional(readOnly=true)
	public LoverActivity findByDayAndPairUUID(Integer dayNumber,String pairUUID){
		return dao.findByDayAndPairUUID(dayNumber,pairUUID);
	}

	public LoverActivity saveLoverActivity(String pairUUID, String tableValues,String dayNumbers){
		LoverActivity activity = new LoverActivity();
		String[] values = strToAry(tableValues);
		String[] numbers = strToAry(dayNumbers);
		for(int i = 0 ; i < values.length ; i++ ){
			activity.setDay_number(Integer.parseInt(numbers[i]));
			activity.setLoverUUID(pairUUID);
			activity.setActivity(activityMng.findById(Integer.parseInt(values[i])));
			activity.setCompletion_status(3);
			save(activity);
		}
		return activity;
	}
	
	public LoverActivity save(LoverActivity bean) {
		
		return dao.save(bean);
	}

	
	public LoverActivity update(LoverActivity bean) {
		Updater<LoverActivity> updater = new Updater<LoverActivity>(bean);
		return dao.updateByUpdater(updater);
	}

	
	public LoverActivity deleteById(Integer id) {
		
		return dao.deleteById(id);
	}

	
	public LoverActivity[] deleteByIds(Integer[] ids) {
		
		return dao.deleteByIds(ids);
	}
	
	private String[] strToAry(String text){
		return text.split(",");
	}
	
	@Autowired
	private LoverActivityDao dao;
	@Autowired
	private ActivityMng activityMng;
}
