package com.dgut.main.action.main;

import static com.dgut.common.page.SimplePage.cpn;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dgut.common.page.Pagination;
import com.dgut.common.web.CookieUtils;
import com.dgut.common.web.ResponseUtils;
import com.dgut.core.web.WebErrors;
import com.dgut.main.entity.AccusationCategory;
import com.dgut.main.entity.AccusationType;
import com.dgut.main.manager.AccusationCategoryMng;
import com.dgut.main.manager.AccusationTypeMng;
import com.dgut.main.manager.AdminLogMng;
import com.dgut.main.manager.AdminMng;

@Controller
@RequestMapping("accusation/category/type")
public class AccusationTypeAct {
	@RequestMapping("v_list.do")
	public String getPage(HttpServletRequest request,HttpServletResponse response,ModelMap model,Integer categoryId,String queryTypeName,Integer pageNo){
		Pagination pagination = typeMng.getPage(queryTypeName, categoryId,cpn(pageNo), CookieUtils.getPageSize(request));
		AccusationCategory accusationCategory = categoryMng.findById(categoryId);
		model.addAttribute("category", accusationCategory);
		model.addAttribute("pagination", pagination);
		model.addAttribute("pageNo", pageNo);
		return "accusation/category/type/list";
	}

	@RequestMapping("v_edit.do")
	public String typeEdit(HttpServletRequest request,HttpServletResponse response,ModelMap model,Integer typeId,Integer categoryId){
		WebErrors errors = validateEdit(typeId,request);
		if(errors.hasErrors()){
			return errors.showErrorPage(model);
		}
		AccusationType  type = typeMng.findById(typeId);
		model.addAttribute("type", type);
		model.addAttribute("categoryId", categoryId);
		return "accusation/category/type/edit";
	}
	
	@RequestMapping("o_update.do")
	public String typeUpdate(HttpServletRequest request,HttpServletResponse response,ModelMap model,AccusationType type,Integer categoryId){
		typeMng.update(type);
		adminLogMng.operating(request, "cms.accusation.accusationType.update", "update.accusationType.id="+type.getId());
		return "redirect:v_list.do?&categoryId="+categoryId;
	}
	
	@RequestMapping("v_add.do")
	public String typeAdd(HttpServletRequest request,HttpServletResponse response,ModelMap model,String categoryId){
		WebErrors errors = validateCategoryId(request,categoryId);
		if(errors.hasErrors()){
			return errors.showErrorPage(model);
		}
		model.addAttribute("categoryId", categoryId);
		return "accusation/category/type/add";
	}
	@RequestMapping("/v_checkTypeName.do")
	public void checkTypeName(String typeName,HttpServletRequest request,HttpServletResponse response){
		AccusationType type = typeMng.findByTypeName(typeName);
		boolean pass = type == null;
		ResponseUtils.renderJson(response, pass ? "true" : "false");
	}
	
	@RequestMapping("o_save.do")
	public String typeSave(HttpServletRequest request,HttpServletResponse response,ModelMap model,String categoryId,AccusationType type){
		WebErrors errors = validateCategoryId(request,categoryId);
		if(errors.hasErrors()){
			return errors.showErrorPage(model);
		}
		AccusationCategory category = categoryMng.findById(Integer.parseInt(categoryId));
		type.setAccusationCategory(category);
		typeMng.save(type);
		adminLogMng.operating(request, "cms.accusation.accusationType.save", "save.accusationType.name="+type.getTypeName());
		return "redirect:v_list.do?categoryId="+categoryId;
	}
	
	@RequestMapping("o_delete.do")
	public String typeDelete(HttpServletRequest request,HttpServletResponse response,ModelMap model,Integer typeId,Integer categoryId){
		WebErrors errors = validateDelete(typeId,categoryId,request);
		if(errors.hasErrors()){
			return errors.showErrorPage(model);
		}
		AccusationType type = typeMng.deleteById(typeId);
		if(type == null){
			errors.addErrorString("删除失败");
			return errors.showErrorPage(model);
		}
		adminLogMng.operating(request, "cms.accusation.accusationType.delete", "delete.accusationType.name="+type.getTypeName());
		return "redirect:v_list.do?categoryId="+categoryId;
	}
	private WebErrors validateDelete(Integer typeId, Integer categoryId,
			HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if(categoryId == null){
			errors.addErrorCode("error.required","大类AccusationCategory的id");
			return errors;
		}
		if(typeId == null){
			errors.addErrorCode("error.required", "子类AccusationType的id");
			return errors;
		}
		return errors;
	}

	private WebErrors validateCategoryId(HttpServletRequest request, String categoryId) {
		WebErrors errors = WebErrors.create(request);
		if(categoryId == null){
			errors.addErrorCode("error.required", "大类id");
		}
		return errors;
	}

	private WebErrors validateEdit(Integer typeId, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if(typeId==null){
			errors.addErrorCode("error.required", "子类id");
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
