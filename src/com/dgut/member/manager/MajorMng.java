package com.dgut.member.manager;

import java.util.List;

import com.dgut.member.entity.Major;

public interface MajorMng {
	
	public List<Major> getList();
	
	public Major findById(Integer id);
	
	public Major save(Major bean);
	
	public Major updateByUpdater(Major bean);

	public Major deleteById(Integer id);
	
	public Major[] deleteByIds(Integer[] ids);
}
