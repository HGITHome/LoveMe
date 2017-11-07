package com.dgut.main.manager.impl;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dgut.common.hibernate3.Updater;
import com.dgut.common.page.Pagination;
import com.dgut.main.dao.AccusationUserDao;
import com.dgut.main.entity.AccusationUser;
import com.dgut.main.entity.Admin;
import com.dgut.main.manager.AccusationUserMng;
import com.dgut.main.web.CmsUtils;

@Service
@Transactional
public class AccusationUserMngImpl implements AccusationUserMng {

	@Transactional(readOnly=true)
	public Pagination getPage(String reporterName, String informantName,
			String queryCategoryId, String handle_flag, Integer pageNo,
			Integer pageSize) {
		Pagination pagination = dao.getPage(reporterName, informantName, queryCategoryId, handle_flag, pageNo, pageSize);
		return pagination;
	}

	@Transactional(readOnly=true)
	public AccusationUser findById(Integer id) {
		
		return dao.findById(id);
	}

	public AccusationUser update(String replyContent,HttpServletRequest request,String id){
		AccusationUser bean = new AccusationUser();
		Admin admin = CmsUtils.getAdmin(request);
		bean.setId(Integer.parseInt(id));
		bean.setHandle_flag(1);
		bean.setReply_content(replyContent);
		bean.setReply_time(new Date());
		bean.setAdmin(admin);
		update(bean);
		return bean;
	}
	
	public AccusationUser save(AccusationUser bean) {
		
		return dao.save(bean);
	}

	
	public AccusationUser update(AccusationUser bean) {
		Updater<AccusationUser> updater = new Updater<AccusationUser>(bean);
		return dao.updateByUpdater(updater);
	}

	
	public AccusationUser deleteById(Integer id) {
		
		return dao.deleteById(id);
	}

	@Autowired
	private AccusationUserDao dao;
}
