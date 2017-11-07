package com.dgut.member.manager.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dgut.common.hibernate3.Updater;
import com.dgut.common.page.Pagination;
import com.dgut.common.util.DateUtils;
import com.dgut.member.dao.LoverShipDao;
import com.dgut.member.entity.Lovership;
import com.dgut.member.entity.Member;
import com.dgut.member.manager.LoverShipMng;

@Service
@Transactional
public class LoverShipMngImpl implements LoverShipMng {

	@Transactional(readOnly=true)
	public Pagination getList(String queryUsername, String queryStatus,Integer pageNo, Integer pageSize) {
		Pagination pagination = dao.getList(queryUsername, queryStatus, pageNo, pageSize);
		return pagination;
	}

	@Transactional(readOnly=true)
	public Lovership findById(Integer id) {
		Lovership bean = dao.findById(id);
		return bean;
	}

	@Transactional(readOnly=true)
	public Lovership findByOwnId(Integer ownId){
		return dao.findByOwnId(ownId);
	}
	
	public void registShip(Member publisher,Member receiver,Integer ship_status) throws ParseException{
		Date registTime = DateUtils.format1.parse(new Date() + "");
		String pairUUID = StringUtils.remove(UUID.randomUUID().toString(), '-');
		save(new Lovership(publisher, receiver, ship_status, registTime,pairUUID));
		save(new Lovership(receiver, publisher, ship_status, registTime,pairUUID));
	}

	public Lovership save(Lovership bean) {
	    bean = dao.save(bean);
		return bean;
	}

	
	public Lovership update(Lovership bean) {
		Updater<Lovership> updater = new Updater<Lovership>(bean);
		bean = dao.updateByUpdater(updater);
		return bean;
	}
	
	@Autowired
	private LoverShipDao dao;
}
