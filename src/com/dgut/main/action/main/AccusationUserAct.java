package com.dgut.main.action.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.dgut.common.page.SimplePage.cpn;

import com.dgut.common.page.Pagination;
import com.dgut.common.web.CookieUtils;
import com.dgut.core.web.WebErrors;
import com.dgut.main.entity.AccusationCategory;
import com.dgut.main.entity.AccusationUser;
import com.dgut.main.manager.AccusationCategoryMng;
import com.dgut.main.manager.AccusationUserMng;
import com.dgut.main.manager.AdminLogMng;

@Controller
@RequestMapping("accusation")
public class AccusationUserAct {
	
	@RequestMapping("v_list.do")
	public String accusationUserPage(HttpServletRequest request,HttpServletResponse response,
			ModelMap model,String queryReporterName,String queryInformantName,String queryCategoryId,String queryStatus,Integer pageNo){
	   Pagination pagination = acUserMng.getPage(queryReporterName, queryInformantName, queryCategoryId, queryStatus, cpn(pageNo), CookieUtils.getPageSize(request));
	   List<AccusationCategory> accusationCategoryList = categoryMng.getList();
	   model.addAttribute("queryReporterName", queryReporterName);
	   model.addAttribute("queryInformantName", queryInformantName);
	   model.addAttribute("queryCategoryId", queryCategoryId);
	   model.addAttribute("queryStatus", queryStatus);
	   model.addAttribute("categoryList", accusationCategoryList);
	   model.addAttribute("pageNo", pageNo);
	   model.addAttribute("pagination", pagination);
	   return "accusation/list";	
	}
	
	@RequestMapping("o_info.do")
	public String accusationInfo(HttpServletRequest request,HttpServletResponse response,ModelMap model,Integer id){
		WebErrors errors = validateInfo(id,request);
		if(errors.hasErrors()){
			return errors.showErrorPage(model);
		}
		AccusationUser accusation = acUserMng.findById(id);
		model.addAttribute("accusation", accusation);
		return "accusation/info";
	}
	
	@RequestMapping("o_update.do")
	public String accusationUpdate(HttpServletRequest request,HttpServletResponse response,ModelMap model,String id){
		String replyContent = request.getParameter("content");
		WebErrors errors = validateUpdate(replyContent,id,request);
		if(errors.hasErrors()){
			return errors.showErrorPage(model);
		}
		acUserMng.update(replyContent,request,id);
		adminLogMng.operating(request, "cms.accusation.accusationUser.handle", "handle.accusationUser.id="+id);
		return "redirect:v_list.do";
	}
	
	@RequestMapping("o_delete.do")
	public String accusationDelete(Integer id,HttpServletRequest request,HttpServletResponse response,ModelMap model){
		WebErrors errors = validateDelete(id,request);
		if(errors.hasErrors()){
			return errors.showErrorPage(model);
		}
		AccusationUser acUser = acUserMng.deleteById(id);
		if(acUser == null){
			errors.addErrorString("删除失败!");
			return errors.showErrorPage(model);
		}
		adminLogMng.operating(request, "cms.accusation.accusationUser.delete", "delete.accusationUser.id" + acUser.getId());
		return "redirect:v_list.do"; 
	}
	
	private WebErrors validateDelete(Integer id, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if(id == null ){
			errors.addErrorCode("error.required", "删除举报记录的id");
			return errors;
		}
		return errors;
	}

	private WebErrors validateUpdate(String replyContent,String id,HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if(replyContent == null || replyContent.trim().equals("")){
			errors.addErrorString("处理结果内容不能为空!");
			return errors;
		}
		if(id == null || id.trim().equals("")){
			errors.addErrorCode("error.required", "举报id");
			return errors;
		}
		return errors;
	}

	private WebErrors validateInfo(Integer id, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if(id == null){
			errors.addErrorCode("error.required", "投诉id");
			return errors;
		}
		return errors;
	}




	@Autowired
	private AccusationUserMng acUserMng;
	
	@Autowired
	private AccusationCategoryMng categoryMng;
	
	@Autowired
	private AdminLogMng adminLogMng;
}
