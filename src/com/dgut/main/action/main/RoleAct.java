package com.dgut.main.action.main;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dgut.core.web.WebErrors;
import com.dgut.main.entity.Role;
import com.dgut.main.manager.AdminLogMng;
import com.dgut.main.manager.RoleMng;

@Controller
@RequestMapping("role")
public class RoleAct {
	
	@RequestMapping("v_list.do")
	public String roleList(HttpServletRequest request,HttpServletResponse response,Model model){
		List<Role> list = roleMng.getList();
		model.addAttribute("list", list);
		return "role/list";
	}
	
	@RequestMapping("v_edit.do")
	public String edit(HttpServletRequest request,HttpServletResponse response,Integer id,Model model){
		Role role = roleMng.findById(id);
		model.addAttribute("cmsRole", role);
		return "role/edit";
	}
	
	@RequestMapping("o_update.do")
	public String update(HttpServletRequest request,HttpServletResponse response,ModelMap model,Role role,String[] perms){
		WebErrors errors = validateRoleId(role.getId(),request);
		if(errors.hasErrors()){
			return errors.showErrorPage(model);
		}
		role = roleMng.update(role, splitPerms(perms));
		adminLogMng.operating(request, "cms.role.update", "update.role.id="+role.getId());
		return "redirect:v_list.do";
	}

	@RequestMapping("v_add.do")
	public String add(HttpServletRequest request,HttpServletResponse response,ModelMap model){
		return "role/add";
	}
	
	@RequestMapping("o_save.do")
	public String save(HttpServletRequest request,HttpServletResponse response,ModelMap model,Role role,String[] perms){
		WebErrors errors = validateSave(role,request);
		if(errors.hasErrors()){
			return errors.showErrorPage(model);
		}
		role = roleMng.save(role, splitPerms(perms));
		adminLogMng.operating(request, "cms.role.add", "add.role.name="+role.getName());
		return "redirect:v_list.do";
	}
	
	
	private WebErrors validateSave(Role role, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		return errors;
	}

	private Set<String> splitPerms(String[] perms) {
		Set<String> permSet = new HashSet<String>();
		if(perms != null){
		  for(String s : perms){
			 for(String p : s.split(",")){
				 permSet.add(p);
			 }
		   }
    	}
		return permSet;
	}

	private WebErrors validateRoleId(Integer id, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if(errors.ifNull(id, "id")){
			return errors;
		}
		return errors;
	}

	@Autowired
	private RoleMng roleMng;
	
	@Autowired
	private AdminLogMng adminLogMng;
}
