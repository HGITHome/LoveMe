package com.dgut.app.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dgut.app.utils.JSONUtils;
import com.dgut.common.util.DateUtils;
import com.dgut.main.web.CmsUtils;
import com.dgut.member.entity.Category;
import com.dgut.member.entity.City;
import com.dgut.member.entity.LifePicture;
import com.dgut.member.entity.Major;
import com.dgut.member.entity.Member;
import com.dgut.member.entity.MemberInfo;
import com.dgut.member.entity.Province;
import com.dgut.member.manager.AssesCategoryMng;
import com.dgut.member.manager.CityMng;
import com.dgut.member.manager.MajorMng;
import com.dgut.member.manager.MemberMng;
import com.dgut.member.manager.ProvinceMng;

@Service
public class MemberService {
	
	public String getSelectInfo(HttpServletRequest request,HttpServletResponse response, Map<String, String> parameters) throws IOException{
		HashMap<String, Object> jsonMap = new HashMap<String, Object>();
		Member member = CmsUtils.getMember(request);
		List<Major> majorList = majorMng.getList();
		List<Province> provinceList = provinceMng.getlist();
		List<Category> categoryList = categoryMng.getList();
		JsonConfig config = new JsonConfig();
		config.setExcludes(new String[]{"memberInfoSet","province","category","handler","hibernateLazyInitializer","member","memberInfo"}); 
        config.registerJsonValueProcessor(java.util.Date.class, new JsonValueProcessor() {
			
			public Object processArrayValue(Object arg0, JsonConfig arg1) {
				return null;
			}
			public Object processObjectValue(String key, Object value,
					JsonConfig config) {
				if(value==null){
					return "";
				}
				if(value instanceof java.util.Date){
					String str=DateUtils.format2.format((java.util.Date)value);
					return str;
				}
				return null;
			}
		});
		jsonMap.put("error", "-1");
		jsonMap.put("msg", "获取个人信息设置选项成功");
		jsonMap.put("majorList", majorList);
		jsonMap.put("provinceList", provinceList);
		jsonMap.put("categoryList", categoryList);
		return JSONObject.fromObject(jsonMap,config).toString();
	}
	
	public String getMemberInfo(HttpServletRequest request,
			HttpServletResponse response, Map<String, String> parameters) throws IOException {
		HashMap<String, Object> jsonMap = new HashMap<String, Object>();
		Member member = CmsUtils.getMember(request);
		if(member == null){
			jsonMap.put("error", "-3");
			jsonMap.put("msg", "用户不存在");
			return JSONUtils.printObject(jsonMap);
		}
		MemberInfo memberInfo = member.getMemberInfo();
		if(memberInfo == null){
			jsonMap.put("error", "-3");
			jsonMap.put("msg", "个人信息未设置");
			return JSONUtils.printObject(jsonMap);
		}
		memberMng.convertMemberInfoEntity(member.getMemberInfo());
		jsonMap.put("error", "-1");
		jsonMap.put("msg", "获取用户信息成功");
		JsonConfig config = new JsonConfig();
		config.setExcludes(new String[]{"member","memberInfoSet","citys","handler","hibernateLazyInitializer","memberInfo"}); 
		jsonMap.put("memInfo", memberInfo);
		config.registerJsonValueProcessor(java.util.Date.class, new JsonValueProcessor() {
			
			public Object processArrayValue(Object arg0, JsonConfig arg1) {
				return null;
			}
			public Object processObjectValue(String key, Object value,
					JsonConfig config) {
				if(value==null){
					return "";
				}
				if(value instanceof java.util.Date){
					String str=DateUtils.format2.format((java.util.Date)value);
					return str;
				}
				return null;
			}
		});
		return JSONObject.fromObject(jsonMap,config).toString();
	}

	
	/**
	 * 获取城市列表
	 */
	public String citys(HttpServletRequest request,
			HttpServletResponse response, Map<String, String> parameters)
			throws IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		List<City> list = cityMng.getList();
		jsonMap.put("error", -1);
		jsonMap.put("msg", "请求成功");
		jsonMap.put("citys",list);
		System.out.println("member:"+CmsUtils.getMember(request));
		return JSONUtils.printObject(jsonMap);
	}
	

	
	@Autowired
	private MemberMng memberMng;
	
	@Autowired
	private CityMng cityMng;
	
	@Autowired
	private MajorMng majorMng;
	
	@Autowired
	private AssesCategoryMng categoryMng;
	
	@Autowired
	private ProvinceMng provinceMng;
}
