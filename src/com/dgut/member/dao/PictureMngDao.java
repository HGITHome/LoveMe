package com.dgut.member.dao;

import java.util.List;

import com.dgut.common.hibernate3.Updater;
import com.dgut.member.entity.LifePicture;

public interface PictureMngDao {
    public List<LifePicture> getList();
	
	public LifePicture findById(Integer id);
	
	public LifePicture save(LifePicture bean);
	
	public LifePicture deleteById(Integer id);
	
	public LifePicture updateByUpdater(Updater<LifePicture> updater);
	
	public LifePicture[] deleteByIds(Integer[] ids);
}
