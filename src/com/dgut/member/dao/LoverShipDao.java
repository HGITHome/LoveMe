package com.dgut.member.dao;

import com.dgut.common.hibernate3.Updater;
import com.dgut.common.page.Pagination;
import com.dgut.member.entity.Lovership;

public interface LoverShipDao {
    public Pagination getList(String queryUsername,String queryStatus,Integer pageNo,Integer pageSize);
	
    public Lovership findByOwnId(Integer ownId);
    
	public Lovership findById(Integer id);
	
	public Lovership save(Lovership bean);
	
	public Lovership updateByUpdater(Updater<Lovership> updater);
}
