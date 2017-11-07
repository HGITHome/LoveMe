package com.dgut.main.manager;


import javax.servlet.http.HttpServletRequest;

import com.dgut.common.page.Pagination;
import com.dgut.main.entity.AccusationUser;

public interface AccusationUserMng {
	
public Pagination getPage(String reporterName,String informantName,String queryCategoryId,String handle_flag,Integer pageNo,Integer pageSize);
	
	public AccusationUser findById(Integer id);
	
	public AccusationUser save(AccusationUser bean);
	
	public AccusationUser update(AccusationUser bean);
	
	public AccusationUser deleteById(Integer id);

	public AccusationUser update(String replyContent,HttpServletRequest request,String id);
}
