package com.dgut.member.dao;

import java.util.List;

import com.dgut.common.hibernate3.Updater;
import com.dgut.common.page.Pagination;
import com.dgut.main.entity.Enum.ApplyFlag;
import com.dgut.member.entity.ApplyLover;
import com.dgut.member.entity.Member;

public interface ApplyLoverDao {
   public Pagination getList(String queryUsername,String status,Integer pageNo,Integer pageSize);
	
	public ApplyLover findById(Integer id);
	
	public ApplyLover getPreviousApply(Member publisher,Member receiver,Integer status);
	
	public List<ApplyLover> findByPublisherId(Integer id,Integer status);
	
	public List<ApplyLover> findByReceiverId(Integer id,Integer status);
	
	public ApplyLover save(ApplyLover bean);
	
	public ApplyLover updateByUpdater(Updater<ApplyLover> updater);
}
