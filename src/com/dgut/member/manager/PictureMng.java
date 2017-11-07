package com.dgut.member.manager;

import java.util.List;

import com.dgut.member.entity.LifePicture;

public interface PictureMng {
	
	public List<LifePicture> getList();
	
	public LifePicture findById(Integer id);
	
	public LifePicture save(LifePicture bean);
	
	public LifePicture update(LifePicture bean);
	
	public LifePicture deleteById(Integer id);
	
	public LifePicture[] deleteByIds(Integer[] ids);
}
