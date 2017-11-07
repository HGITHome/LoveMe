package com.dgut.member.manager.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dgut.common.hibernate3.Updater;
import com.dgut.common.page.Pagination;
import com.dgut.common.security.BadCredentialsException;
import com.dgut.common.security.UsernameNotFoundException;
import com.dgut.common.security.encoder.PwdEncoder;
import com.dgut.core.entity.Config.ConfigLogin;
import com.dgut.core.manager.ConfigMng;
import com.dgut.member.dao.MemberDao;
import com.dgut.member.entity.Member;
import com.dgut.member.entity.MemberInfo;
import com.dgut.member.manager.MemberMng;
import com.dgut.member.manager.TypeMng;

@Service
@Transactional
public class MemberMngImpl implements MemberMng {
	@Transactional(readOnly = true)
	public Pagination getPage(String queryUsername,String queryEmail,String queryMobile,int pageNo, int pageSize) {
		Pagination page = dao.getPage(queryUsername,queryEmail,queryMobile,  pageNo, pageSize);
		return page;
	}
	
	@Transactional(readOnly = true)
	public List getList(String username, Boolean disabled) {
		List list = dao.getList(username,disabled);
		return list;
	}



	@Transactional(readOnly = true)
	public Member findById(Integer id) {
		Member entity = dao.findById(id);
		return entity;
	}

	@Transactional(readOnly = true)
	public Member findByUsername(String username) {
		Member entity = dao.findByUsername(username);
		return entity;
	}


	public void updateLoginInfo(Integer userId, String ip) {
		Date now = new Timestamp(System.currentTimeMillis());
		Member user = findById(userId);
		if (user != null) {
			user.setLoginCount(user.getLoginCount() + 1);
			user.setLastLoginIp(ip);
			user.setLastLoginTime(now);
		}
	}


	public boolean isPasswordValid(Integer id, String password) {
		 Member user = findById(id);
		return pwdEncoder.isPasswordValid(user.getPassword(), password);
	}

	public void updatePwdRealName(Integer id, String password, String realName) {
		Member user = findById(id);
		if (!StringUtils.isBlank(password)) {
			user.setPassword(pwdEncoder.encodePassword(password));
		}
	}

	public Member saveMember(String username,  String password,
			String ip, Boolean gender,String realname) {
		Date now = new Timestamp(System.currentTimeMillis());
		Member user = new Member();
		user.setUsername(username);
		user.setPassword(pwdEncoder.encodePassword(password));
		user.setRegisterIp(ip);
		user.setRegisterTime(now);
		user.setLastLoginIp(ip);
		user.setLastLoginTime(now);
		user.init();
		dao.save(user);
		return user;
	}


	public Member updateMember(Member bean, String password) {
		
		if (!StringUtils.isBlank(password)) {
			bean.setPassword(pwdEncoder.encodePassword(password));
		}
		Updater<Member> updater = new Updater<Member>(bean);
		Member user = dao.updateByUpdater(updater);
		return user;
	}
	
	public Member updateMember(Member bean){
		Updater<Member> updater = new Updater<Member>(bean);
		Member user = dao.updateByUpdater(updater);
		return user;
	}
    public Member registMember(Member user,String ip){
    	Date now = new Timestamp(System.currentTimeMillis());
    	user.setRegisterIp(ip);
		user.setRegisterTime(now);
		user.setLastLoginIp(ip);
		user.setLastLoginTime(now);
		user.init();
    	Updater<Member> updater = new Updater<Member>(user);
		user = dao.updateByUpdater(updater);
	   return user;
    }
	public Member getByUsername(String username) {
		return dao.findByUsername(username);
	}
	
	
	@Transactional(readOnly = true)
	public MemberInfo convertMemberInfoEntity(MemberInfo bean) {
		
		convertMember(bean);
		return bean;
	}

	
	private void convertMember(MemberInfo bean) {
		if(bean.getConstellation() != null){
		  bean.setConstellation(typeMng.findById(bean.getConstellation()).getName());
		}
		bean.setLabels(additionMsg(bean.getLabels(),bean.getLabel_others()));
		bean.setSports(additionMsg(bean.getSports(),bean.getSport_others()));
		bean.setMusic(additionMsg(bean.getMusic(),bean.getMusic_others()));
		bean.setFoods(additionMsg(bean.getFoods(),bean.getFood_others()));
		bean.setFilms(additionMsg(bean.getFilms(),bean.getFilm_others()));
		bean.setBooks(additionMsg(bean.getBooks(),bean.getBook_others()));
		bean.setTravels(additionMsg(bean.getTravels(),bean.getTravel_others()));
	}

	private String additionMsg(String source,String addition) {
		String[] strings;
		StringBuffer sb = new StringBuffer();
		
		if(source != null && !source.trim().equals("")){
			strings = source.split(",");
			for(String s : strings){
				sb.append(typeMng.findById(s).getName() + ",");
			}
			if(addition != null && !addition.trim().equals("")){
				sb.append(addition);
			}else{
				sb.deleteCharAt(sb.length()-1);
			}
		}else{
			if(addition != null && !addition.trim().equals("")){
				sb.append(addition);
			}
		}
		return sb.toString();
	}

	public Member login(String username, String password, String ip)
			throws UsernameNotFoundException, BadCredentialsException {
		Member user = getByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("username not found: "
					+ username);
		}
		if (!pwdEncoder.isPasswordValid(user.getPassword(), password)) {
			updateLoginError(user.getId(), ip);
			throw new BadCredentialsException("password invalid");
		}
		updateLoginSuccess(user.getId(), ip);
		return user;
	}
	public void updateLoginSuccess(Integer userId, String ip) {
		Member user = findById(userId);
		Date now = new Timestamp(System.currentTimeMillis());

		user.setLoginCount(user.getLoginCount() + 1);
		user.setLastLoginIp(ip);
		user.setLastLoginTime(now);

		user.setErrorCount(0);
		user.setErrorTime(null);
		user.setErrorIp(null);
	}
	
	public void updateLoginError(Integer userId, String ip) {
		Member user = findById(userId);
		Date now = new Timestamp(System.currentTimeMillis());
		ConfigLogin configLogin = configMng.getConfigLogin();
		int errorInterval = configLogin.getErrorInterval();
		Date errorTime = user.getErrorTime();

		user.setErrorIp(ip);
		if (errorTime == null
				|| errorTime.getTime() + errorInterval * 60 * 1000 < now
						.getTime()) {
			user.setErrorTime(now);
			user.setErrorCount(1);
		} else {
			user.setErrorCount(user.getErrorCount() + 1);
		}
	}

	
	public Member deleteById(Integer id) {
		Member bean = dao.deleteById(id);
		return bean;
	}

	public Member[] deleteByIds(Integer[] ids) {
		Member[] beans = new Member[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	public boolean usernameNotExist(String mobile) {
		return dao.countByUsername(mobile) <= 0;
	}
	
	public Integer errorRemaining(String username) {
		if (StringUtils.isBlank(username)) {
			return null;
		}
		Member user = getByUsername(username);
		if (user == null) {
			return null;
		}
		long now = System.currentTimeMillis();
		ConfigLogin configLogin = configMng.getConfigLogin();
		int maxErrorTimes = configLogin.getErrorTimes();
		int maxErrorInterval = configLogin.getErrorInterval() * 60 * 1000;
		Integer errorCount = user.getErrorCount();
		Date errorTime = user.getErrorTime();
		if (errorCount <= 0 || errorTime == null
				|| errorTime.getTime() + maxErrorInterval < now) {
			return maxErrorTimes;
		}
		return maxErrorTimes - errorCount;
	}

	private MemberDao dao;

	@Autowired
	public void setDao(MemberDao dao) {
		this.dao = dao;
	}
	private ConfigMng configMng;

	private PwdEncoder pwdEncoder;
	
	@Autowired
	public void setPwdEncoder(PwdEncoder pwdEncoder) {
		this.pwdEncoder = pwdEncoder;
	}
	@Autowired
	public void setConfigMng(ConfigMng configMng) {
		this.configMng = configMng;
	}
	@Autowired
	private TypeMng typeMng;

	
	
}
