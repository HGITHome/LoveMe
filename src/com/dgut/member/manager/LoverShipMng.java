package com.dgut.member.manager;

import java.text.ParseException;

import com.dgut.common.page.Pagination;
import com.dgut.member.entity.Lovership;
import com.dgut.member.entity.Member;

public interface LoverShipMng {
	
	public Pagination getList(String queryUsername,String queryStatus,Integer pageNo,Integer pageSize);
	
	public void registShip(Member publisher,Member receiver,Integer ship_status) throws ParseException;
	
	public Lovership findByOwnId(Integer ownId);
	
	public Lovership findById(Integer id);
	
	public Lovership save(Lovership bean);
	
	public Lovership update(Lovership bean);
}
