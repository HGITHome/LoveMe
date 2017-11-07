package com.dgut.member.manager;

import java.util.List;

import com.dgut.member.entity.City;
import com.dgut.member.entity.Province;

public interface ProvinceMng {
	public List<Province> getlist();
	
	public Province findById(Integer id);
	
	public Province save(Province bean);
	
	public Province update(Province bean);
	
	public Province  deleteById(Integer id);
	
	public Province[] deleteByIds(Integer[] ids);

	public String dataToString(List<City> cityList);
	
}
