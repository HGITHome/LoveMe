package com.dgut.member.manager;

import java.util.List;

import com.dgut.common.page.Pagination;
import com.dgut.common.security.BadCredentialsException;
import com.dgut.common.security.UsernameNotFoundException;
import com.dgut.member.entity.Member;
import com.dgut.member.entity.MemberInfo;

public interface MemberMng {

	public Member login(String queryUsername, String password, String ip) throws UsernameNotFoundException, BadCredentialsException;
	public Pagination getPage(String username,String queryEmail,String queryMobile, int pageNo, int pageSize);
	
	public List<Member> getList(String username,  Boolean disabled);
	
	public Member findById(Integer id);

	public Member findByUsername(String username);


	public void updateLoginInfo(Integer userId, String ip);


	public void updatePwdRealName(Integer id, String password, String realName);

	public boolean isPasswordValid(Integer id, String password);

	public Member saveMember(String username,  String password,
			String ip,Boolean gender,String realname);

	public Member updateMember(Member bean, String password);
	
	public Member updateMember(Member bean);
	
	public Member registMember(Member bean,String ip);

    public MemberInfo convertMemberInfoEntity(MemberInfo bean);
	
	public Member deleteById(Integer id);

	public Member[] deleteByIds(Integer[] ids);
	
	public boolean usernameNotExist(String username);
	
	public Integer errorRemaining(String username);
}
