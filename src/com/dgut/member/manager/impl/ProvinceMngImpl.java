package com.dgut.member.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dgut.common.hibernate3.Updater;
import com.dgut.member.dao.ProvinceDao;
import com.dgut.member.entity.City;
import com.dgut.member.entity.Province;
import com.dgut.member.manager.ProvinceMng;

@Service
@Transactional
public class ProvinceMngImpl implements ProvinceMng {

	@Transactional(readOnly = true)
	public List<Province> getlist() {
		List<Province> list = dao.getlist();
		return list;
	}

	@Transactional(readOnly = true)
	public Province findById(Integer id) {
		return dao.findById(id);
	}

	
	public Province save(Province bean) {
		bean = dao.save(bean);
		return bean;
	}

	
	public Province update(Province bean) {
		Updater<Province> updater = new Updater<Province>(bean);
		bean = dao.updateByUpdater(updater);
		return bean;
	}

	
	public Province deleteById(Integer id) {
		Province bean = dao.deleteById(id);
		return bean;
	}

	
	public Province[] deleteByIds(Integer[] ids) {
		Province[] provinces = dao.deleteByIds(ids);
		return provinces;
	}
	
	
	public String dataToString(List<City> cityList) {
		StringBuffer sb = new StringBuffer();
		sb.delete(0, sb.length());
		for(City city : cityList){
			sb.append(city.getName() + ",");
		}
		sb.replace(sb.length()-1, sb.length(), "=");
		for(City city : cityList){
			sb.append(city.getId() + ",");
		}
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}

	@Autowired
	private ProvinceDao dao;

	
}
