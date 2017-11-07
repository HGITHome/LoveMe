package com.dgut.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.dgut.common.page.SimplePage.cpn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dgut.common.page.Pagination;
import com.dgut.common.web.CookieUtils;
import com.dgut.core.web.WebErrors;
import com.dgut.main.manager.AdminLogMng;
import com.dgut.member.manager.ThumbsUpMng;

@Controller
@RequestMapping("member/thumbsUp")
public class ThumbsUpAct {
	
	@RequestMapping("v_list.do")
	public String thumbsUpList(HttpServletRequest request,HttpServletResponse response,String queryPublisher,String queryReceiver,ModelMap model,Integer pageNo){
		Pagination pagination = thumbsUpMng.getList(queryPublisher, queryReceiver, cpn(pageNo), CookieUtils.getPageSize(request));
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("queryPublisher", queryPublisher);
		model.addAttribute("queryReceiver", queryReceiver);
		model.addAttribute("pagination", pagination);
		return "member/thumbsUp/list";
	}
	
	@RequestMapping("o_delete_thumbsUp.do")
	public String thumbsUpDelete(HttpServletRequest request,HttpServletResponse response,Integer id,ModelMap model){
		WebErrors errors = validateId(request,id);
		if(errors.hasErrors()){
			return errors.showErrorPage(model);
		}
		if(thumbsUpMng.deleteById(id) == null){
		   errors.addErrorString("删除对象不存在，删除失败！");
		   return errors.showErrorPage(model);
		}
		adminLogMng.operating(request, "删除点赞记录", "id="+id);
		return "redirect:v_list.do";
	}
	
	private WebErrors validateId(HttpServletRequest request, Integer id) {
		WebErrors errors = WebErrors.create(request);
		if(errors.ifNull(id, "id")){
			errors.addErrorString("删除的id为空");
			return errors;
		}
		return errors;
	}

	@Autowired
	private ThumbsUpMng thumbsUpMng;
	
	@Autowired
	private AdminLogMng adminLogMng;
}
