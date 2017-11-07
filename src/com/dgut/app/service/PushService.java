package com.dgut.app.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import static com.dgut.common.page.SimplePage.cpn;











import org.springframework.beans.factory.annotation.Autowired;







import com.dgut.app.AppConstants;
import com.dgut.app.utils.JSONUtils;
import com.dgut.common.web.ResponseUtils;
import com.dgut.main.web.CmsUtils;
import com.dgut.member.entity.Member;
import com.dgut.member.entity.MemberInfo;
import com.dgut.member.manager.MemberInfoMng;



public class PushService {
	public void pushMember(HttpServletRequest request,HttpServletResponse response,Map<String,String> parameters) throws IOException{
		HashMap<String,Object> jsonMap = new HashMap<String,Object>();
		Member member = CmsUtils.getMember(request);
		if(parameters.get("pageNo") == null){
			jsonMap.put("error", "-3");
			jsonMap.put("msg", "参数不完整");
			ResponseUtils.renderJson(response, JSONUtils.printObject(jsonMap));
			return;
		}
		Integer pageNo = Integer.parseInt( parameters.get("pageNo"));
		MemberInfo memberInfo = member.getMemberInfo();
		List<MemberInfo> memberList = memberInfoMng.getMemberList(memberInfo, cpn(pageNo), AppConstants.APP_SIZE);
		jsonMap.put("error", "-1");
		jsonMap.put("msg", "获取成功");
		jsonMap.put("memberList", memberList);
		ResponseUtils.renderJson(response, JSONUtils.printObject(jsonMap));
	}
	
	@Autowired
	private MemberInfoMng memberInfoMng;
}
