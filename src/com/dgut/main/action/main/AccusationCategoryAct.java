package com.dgut.main.action.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.dgut.common.page.SimplePage.cpn;

import org.apache.commons.lang.StringUtils;
import org.quartz.impl.calendar.WeeklyCalendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dgut.common.page.Pagination;
import com.dgut.common.web.CookieUtils;
import com.dgut.common.web.ResponseUtils;
import com.dgut.core.web.WebErrors;
import com.dgut.main.entity.AccusationCategory;
import com.dgut.main.manager.AccusationCategoryMng;
import com.dgut.main.manager.AccusationTypeMng;
import com.dgut.main.manager.AdminLogMng;

@Controller
@RequestMapping("accusation/category")
public class AccusationCategoryAct {
	
	@RequestMapping("v_list.do")
	public String getPage(HttpServletRequest request,HttpServletResponse response,ModelMap model,String queryCategoryName,Integer pageNo){
		
		Pagination pagination = categoryMng.getPage(queryCategoryName, cpn(pageNo), CookieUtils.getPageSize(request));
		model.addAttribute("queryCategoryName", queryCategoryName);
		model.addAttribute("pagination", pagination);
		model.addAttribute("pageNo", pageNo);
		return "accusation/category/list";
	}
	

	@RequestMapping("v_edit.do")
	public String categoryEdit(HttpServletRequest request,HttpServletResponse response,ModelMap model,Integer id){
		WebErrors errors = validateEdit(id,request);
		if(errors.hasErrors()){
			return errors.showErrorPage(model);
		}
		AccusationCategory category = categoryMng.findById(id);
		model.addAttribute("category", category);
		return "accusation/category/edit";
	}
	
	@RequestMapping("o_update.do")
	public String categoryUpdate(HttpServletRequest request,HttpServletResponse response,ModelMap model,AccusationCategory accusationCategory){
		categoryMng.update(accusationCategory);
		adminLogMng.operating(request, "cms.accusation.accusationCategory.update", "update.accusationCategory.id="+accusationCategory.getId());
		return "redirect:v_list.do";
	}
	
	@RequestMapping("v_add.do")
	public String catgoryAdd(HttpServletRequest request,HttpServletResponse response,ModelMap model){
		return "accusation/category/add";
	}
	
	@RequestMapping("/v_checkCategoryName.do")
	public void checkCategoryName(String queryCategoryName,HttpServletRequest request,HttpServletResponse response){
		AccusationCategory category = categoryMng.findByCategoryName(queryCategoryName);
		boolean pass = category == null;
		ResponseUtils.renderJson(response, pass?"true":"false");
	}
	
	@RequestMapping("o_save.do")
	public String categorySave(HttpServletRequest request,HttpServletResponse response,ModelMap model,String queryCategoryName,Integer priority){
		WebErrors errors = validateSave(queryCategoryName,priority,request);
		if(errors.hasErrors()){
			return errors.showErrorPage(model);
		}
		AccusationCategory category = new AccusationCategory();
		category.setCategoryName(queryCategoryName);
		category.setPriority(priority);
		categoryMng.save(category);
		adminLogMng.operating(request, "cms.accusation.save.accusationCategory","save.accusationCategory.name=" + queryCategoryName);
		return "redirect:v_list.do";
	}
	@RequestMapping("o_delete.do")
	public String categoryDelete(HttpServletRequest request,HttpServletResponse response,ModelMap model,Integer id){
		WebErrors errors = validateDelete(id,request);
		if(errors.hasErrors()){
			return errors.showErrorPage(model);
		}
		AccusationCategory category = categoryMng.deleteById(id);
		if(category == null){
			errors.addErrorString("删除失败");
			return errors.showErrorPage(model);
		}
		adminLogMng.operating(request, "cms.accusation.accusationCategory.delete", "delete.accusationCategory.name="+category.getCategoryName());
		return "redirect:v_list.do";
	}
	
	private WebErrors validateDelete(Integer id, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if(errors.ifNull(id, "id")){
			return errors;
		}
		return errors;
	}


	private WebErrors validateSave(String queryCategoryName, Integer priority,
			HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if(StringUtils.isBlank(queryCategoryName)){
			errors.addErrorCode("error.required", "大类名称");
			return errors;
		}
		return errors;
	}


	private WebErrors validateEdit(Integer id, HttpServletRequest request) {
			WebErrors errors = WebErrors.create(request);
			if(errors.ifNull(id, "id")){
				errors.addErrorCode("error.required","id");
		            return errors;
			}
		return errors;
	}
	@Autowired
	private AccusationCategoryMng categoryMng;
	
	@Autowired
	private AccusationTypeMng typeMng;
	
	@Autowired
	private AdminLogMng adminLogMng;
}
