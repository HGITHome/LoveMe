package com.dgut.app.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.dgut.app.utils.Encrypt;
import com.dgut.app.utils.JSONUtils;
import com.dgut.common.util.DateUtils;
import com.dgut.main.Constants;
import com.dgut.main.web.CmsUtils;
import com.dgut.member.entity.ApplyLover;
import com.dgut.member.entity.Member;
import com.dgut.member.manager.ApplyLoverMng;
import com.dgut.member.manager.LoverShipMng;
import com.dgut.member.manager.MemberLogMng;
import com.dgut.member.manager.MemberMng;


public class ApplyService {
	private final static Logger log = LoggerFactory.getLogger(ApplyService.class);
	

	public String apply(HttpServletRequest request,HttpServletResponse response,Map<String, String> parameters) throws IOException{
		HashMap<String,Object> jsonMap = new HashMap<String, Object>();
		JsonConfig config = new JsonConfig();
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
		Member publisher = CmsUtils.getMember(request);
		String cid = request.getParameter("cid");
		if(StringUtils.isBlank(cid)){
			jsonMap.put("error", "-3");
			jsonMap.put("msg", "异性的id为空");
			return JSONUtils.printObject(jsonMap);
		}
		ApplyLover apLover = null;
		cid = Encrypt.decrypt3DES(cid, Constants.ENCRYPTION_KEY);
		Member receiver = memberMng.findById(Integer.parseInt(cid));
		try {
			apLover = applyMng.save(publisher,receiver,request.getParameter("apply_reason"));
		} catch (ParseException e) {
			log.error("日期解析出错");
			jsonMap.put("error", "-3");
			jsonMap.put("msg", "申请日期解析出错");
			return JSONUtils.printObject(jsonMap);
		}
		
		jsonMap.put("error", "-1");
		jsonMap.put("msg", "发送申请成功");
		jsonMap.put("applyTime", apLover.getApplyTime());
		memberLogMng.operating(request, "cms.member.Apply","applyLoverid="+receiver.getId());
		return JSONObject.fromObject(jsonMap, config).toString();
	}
	
	public String applyHandle(HttpServletRequest request,HttpServletResponse response,Map<String,String> parameters) throws IOException{
		HashMap<String,Object> jsonMap = new HashMap<String, Object>();
		Member member = CmsUtils.getMember(request);
		if(parameters.get("pid") == null && parameters.get("handle_flag") == null){
			jsonMap.put("error", "-3");
			jsonMap.put("msg", "参数不完整");
			return JSONUtils.printObject(jsonMap);
		}
		
		Member publisher = memberMng.findById(Integer.parseInt(parameters.get("pid")));
		ApplyLover applyLover = applyMng.findByPublisherReceiver(publisher, member, 0);
		applyLover.setHandle_flag(Integer.parseInt(parameters.get("handle_flag")));
		try {
			applyLover.setReplyTime(DateUtils.format1.parse(new Date() + ""));
		} catch (ParseException e) {
			jsonMap.put("error","-3");
			jsonMap.put("msg", "日期解析出错");
			return JSONUtils.printObject(jsonMap);
		}
		applyLover = applyMng.update(applyLover);
		if(applyLover.getHandle_flag() == 1){
			try {
				 JSONObject result = easemobService.addFriend(member.getEasemob_username(), publisher.getEasemob_username());
		          if (result.getInt("responseStatus") != 200) {
		                jsonMap.put("state", -1);
		                jsonMap.put("msg", "服务器内部错误");
		            }else{
				     shipMng.registShip(applyLover.getPublisher(), applyLover.getReceiver(), applyLover.getHandle_flag());
		            }
			} catch (ParseException e) {
				jsonMap.put("error", "-3");
				jsonMap.put("msg", "日期解析失败");
				return JSONUtils.printObject(jsonMap);
			}
		}
		jsonMap.put("error", "-1");
		jsonMap.put("msg","处理成功");
		 memberLogMng.operating(request, "cms.member.handleApply",applyLover.getHandle_flag() == 1? "同意":"拒绝"+"friendship_id="+member.getId());
		return "";
	}
	@Autowired
	private MemberMng memberMng;
	
	@Autowired
	private ApplyLoverMng applyMng;
	
	@Autowired
	private LoverShipMng shipMng;
	
	@Autowired
	private EasemobService easemobService;
	
	@Autowired
	private MemberLogMng memberLogMng;
}
