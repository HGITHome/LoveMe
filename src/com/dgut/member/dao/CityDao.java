package com.dgut.member.dao;

import java.util.List;

import com.dgut.common.hibernate3.Updater;
import com.dgut.member.entity.City;

public interface CityDao {
	public List<City> getList();

	public City findById(Integer id);

	public City save(City bean);

	public City updateByUpdater(Updater<City> updater);

	public City deleteById(Integer id);
}