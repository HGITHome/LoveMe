package com.dgut.member.dao;

import java.util.List;

import com.dgut.common.hibernate3.Updater;
import com.dgut.member.entity.Province;

public interface ProvinceDao {
    public List<Province> getlist();
	
	public Province findById(Integer id);
	
	public Province save(Province bean);
	
	public Province updateByUpdater(Updater<Province> updater);
	
	public Province  deleteById(Integer id);
	
	public Province[] deleteByIds(Integer[] ids);
}
