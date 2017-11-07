package com.dgut.member.dao;

import java.util.List;

import com.dgut.common.hibernate3.Updater;
import com.dgut.member.entity.Type;

public interface TypeDao {
	
	public List<Type> getList();
	
	public Type findById(String id);
	
	public Type save(Type bean);
	
	public Type deleteById(String id);
	
	public Type updateByUpdater(Updater<Type> updater);
	
	public Type[] deleteByIds(String[] ids);
}
