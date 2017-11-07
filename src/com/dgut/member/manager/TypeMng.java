package com.dgut.member.manager;

import java.util.List;



import com.dgut.member.entity.Type;

public interface TypeMng {
	
    public List<Type> getList();
	
	public Type findById(String id);
	
	public Type save(Type bean);
	
	public Type deleteById(String id);
	
	public Type update(Type bean);
	
	public Type[] deleteByIds(String[] ids);
}
