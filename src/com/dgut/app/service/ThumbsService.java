package com.dgut.app.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.dgut.app.utils.Encrypt;
import com.dgut.app.utils.JSONUtils;
import com.dgut.main.Constants;
import com.dgut.main.web.CmsUtils;
import com.dgut.member.entity.Member;
import com.dgut.member.entity.ThumbsUp;
import com.dgut.member.manager.MemberLogMng;
import com.dgut.member.manager.MemberMng;
import com.dgut.member.manager.ThumbsUpMng;

public class ThumbsService {

	public String thumbsUp(HttpServletRequest request,HttpServletResponse response, Map<String, String> parameters) throws IOException {
		HashMap<String, Object> jsonMap = new HashMap<String, Object>();
		Member member = CmsUtils.getMember(request);
		String receiverId = parameters.get("rId");
		if(receiverId == null){
			jsonMap.put("error", "-3");
			jsonMap.put("msg", "参数不完整");
			return JSONUtils.printObject(jsonMap);
		}
		receiverId = Encrypt.decrypt3DES(receiverId,Constants.ENCRYPTION_KEY);
		Member receiver = memberMng.findById(Integer.parseInt(receiverId));
		try {
			thumbsUpMng.save(member,receiver);
		} catch (ParseException e) {
			jsonMap.put("error", "-3");
			jsonMap.put("msg", "日期解析出错");
			return JSONUtils.printObject(jsonMap);
		}
		jsonMap.put("error", "-1");
		jsonMap.put("msg", "点赞成功");
		receiver.getMemberInfo().setThumbs_up(receiver.getMemberInfo().getThumbs_up()+1);
		memberMng.updateMember(receiver);
		memberLogMng.operating(request, "点赞", "username=" + receiver.getUsername());
		return JSONUtils.printObject(jsonMap);
	}

	public String cancelThumbsUp(HttpServletRequest request,HttpServletResponse response, Map<String, String> parameters) throws IOException{
		HashMap<String, Object> jsonMap = new HashMap<String, Object>();
		Member member = CmsUtils.getMember(request);
		String receiverId = parameters.get("rid");
		if(receiverId == null){
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "参数不完整");
			return JSONUtils.printObject(jsonMap);
		}
		Member receiver = memberMng.findById(Integer.parseInt(receiverId));
		try {
			thumbsUpMng.deleteByMemberReceiver(member,receiver);
		} catch (Exception e) {
			jsonMap.put("error", "-3");
			jsonMap.put("msg", "取消点赞失败");
			return JSONUtils.printObject(jsonMap);
		}
		jsonMap.put("error", "-3");
		jsonMap.put("msg", "取消点赞成功");
		receiver.getMemberInfo().setThumbs_up(receiver.getMemberInfo().getThumbs_up()-1);
		memberMng.updateMember(receiver);
		memberLogMng.operating(request, "取消点赞", "username=" + receiver.getUsername());
		return JSONUtils.printObject(jsonMap);
	}
	@Autowired
	private ThumbsUpMng thumbsUpMng;
	
	@Autowired
	private MemberMng memberMng;
	
	@Autowired
	private MemberLogMng memberLogMng;
}
