package com.dgut.member.manager;

import java.util.List;

import com.dgut.member.entity.City;

public interface CityMng {
	public List<City> getList();

	public City findById(Integer id);

	public City save(City bean);

	public City update(City bean);

	public City deleteById(Integer id);

	public City[] deleteByIds(Integer[] ids);
}