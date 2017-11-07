package com.dgut.member.manager.impl;

import java.text.ParseException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dgut.common.hibernate3.Updater;
import com.dgut.common.page.Pagination;
import com.dgut.common.util.DateUtils;
import com.dgut.member.dao.ApplyLoverDao;
import com.dgut.member.entity.ApplyLover;
import com.dgut.member.entity.Member;
import com.dgut.member.manager.ApplyLoverMng;
import com.dgut.member.manager.MemberMng;
@Service
@Transactional
public class ApplyLoverMngImpl implements ApplyLoverMng {

	@Transactional(readOnly = true)
	public Pagination getList(String queryUsername, String status,Integer pageNo,Integer pageSize) {
		Pagination  pagination =  dao.getList(queryUsername, status, pageNo, pageSize);
     	
		return pagination;
	}

	@Transactional(readOnly= true)
	public ApplyLover findById(Integer id) {
		ApplyLover bean = dao.findById(id);
		return bean;
	}

	
	public ApplyLover findByPublisherReceiver(Member publisher,
			Member receiver, Integer handle_flag) {
		return dao.getPreviousApply(publisher, receiver, handle_flag);
	}
	
	public ApplyLover save(Member publisher,Member receiver,String apply_reason) throws ParseException{
		ApplyLover bean = dao.getPreviousApply(publisher, receiver, 0);
		if(bean != null){
			bean.setApply_reason(apply_reason);
			bean.setApplyTime(DateUtils.format1.parse(new Date() + ""));
			bean.setHandle_flag(0);
			update(bean);
		}else{
			bean = new ApplyLover();
			bean.setPublisher(publisher);
			bean.setReceiver(receiver);
			bean.setApplyTime(DateUtils.format1.parse(new Date() + ""));
			bean.setApply_reason(apply_reason);
			bean.setHandle_flag(0);
			save(bean);
		}
		return bean;
	}
	
	public ApplyLover save(ApplyLover bean) {
		bean = dao.save(bean);
		return bean;
	}


	public ApplyLover update(ApplyLover bean) {
		Updater<ApplyLover> updater = new Updater<ApplyLover>(bean);
		bean = dao.updateByUpdater(updater);
		return bean;
	}
	
	@Autowired
	private ApplyLoverDao dao;
	
	@Autowired
	private MemberMng memberMng;

	
}
