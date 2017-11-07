package com.dgut.member.dao;

import java.util.List;

import com.dgut.common.hibernate3.Updater;
import com.dgut.member.entity.Major;

public interface MajorDao {

    public List<Major> getList();
	
	public Major findById(Integer id);
	
	public Major save(Major bean);
	
	public Major updateByUpdater(Updater<Major> updater);

	public Major deleteById(Integer id);
}
