package com.dgut.member.manager.impl;

import java.text.ParseException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dgut.common.hibernate3.Updater;
import com.dgut.common.page.Pagination;
import com.dgut.common.util.DateUtils;
import com.dgut.member.dao.ThumbsUpDao;
import com.dgut.member.entity.Member;
import com.dgut.member.entity.ThumbsUp;
import com.dgut.member.manager.ThumbsUpMng;

@Service
@Transactional
public class ThumbsUpMngImpl implements ThumbsUpMng {

	@Transactional(readOnly = true)
	public Pagination getList(String queryPublisher, String queryReceiver,
			Integer pageNo, Integer pageSize) {
		Pagination pagination = dao.getList(queryPublisher, queryReceiver, pageNo, pageSize);
		return pagination;
	}

	
	public ThumbsUp findById(Integer id) {
		ThumbsUp bean = dao.findById(id);
		return bean;
	}

	
	public ThumbsUp save(Member member, Member receiver) throws ParseException {
		ThumbsUp bean = new ThumbsUp();
		bean.setPublisher(member);
		bean.setReceiver(receiver);
		bean.setThumbs_up_time(DateUtils.format1.parse(new Date() + ""));
		bean = save(bean);
		return bean;
	}
	public ThumbsUp save(ThumbsUp bean) {
		bean = dao.save(bean);
		return bean;
	}

	
	public ThumbsUp update(ThumbsUp bean) {
		Updater<ThumbsUp> updater = new Updater<ThumbsUp>(bean);
		bean =  dao.updateByUpdater(updater);
		return bean;
	}

	public ThumbsUp deleteByMemberReceiver(Member member, Member receiver) throws Exception {
		ThumbsUp bean = dao.deleteByMemberReceiver(member,receiver);
		if(bean == null){
			throw new RuntimeException();
		}
		bean = deleteByBean(bean);
		return bean;
	}

	
	public ThumbsUp deleteByBean(ThumbsUp bean){
		bean = dao.deleteByBean(bean);
		return bean;
	}
	
	public ThumbsUp deleteById(Integer id) {
		ThumbsUp bean = dao.deleteById(id);
		return bean;
	}

	
	public ThumbsUp[] deleteByIds(Integer[] ids) {
		ThumbsUp[] thumbsUps = dao.deleteByIds(ids);
		return thumbsUps;
	}
	
	@Autowired
	private ThumbsUpDao dao;


	
}
