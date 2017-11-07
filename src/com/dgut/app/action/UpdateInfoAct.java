package com.dgut.app.action;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.dgut.app.utils.JSONUtils;
import com.dgut.common.image.ImageScale;
import com.dgut.common.upload.FileRepository;
import com.dgut.common.upload.UploadUtils;
import com.dgut.common.util.DateUtils;
import com.dgut.common.web.ResponseUtils;
import com.dgut.main.web.CmsUtils;
import com.dgut.member.entity.LifePicture;
import com.dgut.member.entity.Member;
import com.dgut.member.entity.MemberInfo;
import com.dgut.member.manager.AssesCategoryMng;
import com.dgut.member.manager.CityMng;
import com.dgut.member.manager.MajorMng;
import com.dgut.member.manager.MemberInfoMng;
import com.dgut.member.manager.MemberLogMng;
import com.dgut.member.manager.MemberMng;
import com.dgut.member.manager.ProvinceMng;

@Controller
public class UpdateInfoAct {

	private static final Logger log = LoggerFactory.getLogger(UpdateInfoAct.class);
	@SuppressWarnings("null")
	@RequestMapping("/update.do")
	 public void updateOrSaveInfo(HttpServletRequest request,HttpServletResponse response,@RequestParam(value="photo" , required=false) MultipartFile[] photo) throws Exception{
		 HashMap<String, Object> jsonMap = new HashMap<String, Object>();
		 Member member = CmsUtils.getMember(request);
		 if(member == null){
			 jsonMap.put("error", "-3");
			 jsonMap.put("msg", "太久未登录，请重新登录");
			 ResponseUtils.renderJson(response, JSONUtils.printObject(jsonMap));
			 return;
		 }
		 MemberInfo memberInfo = member.getMemberInfo();
		 if(memberInfo == null){
			 memberInfo = new MemberInfo();
		 }
		 String photoUrl=null,miniUrl=null;
		 List<LifePicture> pictureList = null;
		 LifePicture picture = null;
		 pictureList = memberInfo.getPictures();
		 if(pictureList == null){
			 pictureList = new ArrayList<LifePicture>();
		 }
		 if(photo != null){
				int i = 0;
		   for(MultipartFile file : photo){
				String origName = file.getOriginalFilename();
				String extName = FilenameUtils.getExtension(origName).toLowerCase(Locale.ENGLISH);
				if(!"gif,jpg,jpeg,png,bmp".contains(extName)){
					jsonMap.put("error", "-3");
					jsonMap.put("msg", "请上传后缀名为:gif,jpg,jpeg,png,bmp的照片");
					ResponseUtils.renderJson(response, JSONUtils.printObject(jsonMap));
					return;
				}
				try {
					photoUrl=fileRepository.storeByExt("/upload/file", extName, file);
					ServletContext  context=request.getSession().getServletContext();
					File fi = new File(context.getRealPath(photoUrl)); //大图文件  


					String miniPath=context.getRealPath("/upload/file");
					String miniName=UploadUtils.generateFilename("mini", extName);
					File fo = new File(miniPath,miniName); //将要转换出的小图文件
					miniUrl="/upload/file/"+miniName;


					imageScale.resizeFix(fi, fo,180,180);

				} catch (IOException e) {
					jsonMap.put("error", "-3");
					jsonMap.put("msg", e.getMessage());
					ResponseUtils.renderJson(response, JSONUtils.printObject(jsonMap));
					return;
				} catch (Exception e) {
					jsonMap.put("error", "-3");
					jsonMap.put("msg", e.getMessage());
					ResponseUtils.renderJson(response, JSONUtils.printObject(jsonMap));
					return;
			    }
				if(pictureList != null){
					if(photo.length <= pictureList.size() || i <= (pictureList.size() - 1)){
				    	 pictureList.get(i).setPhotoUrl(photoUrl);
					     pictureList.get(i).setMiniUrl(miniUrl);
					     i++;
					}else{
						 picture = new LifePicture();
						 picture.setMemberInfo(memberInfo);
						 picture.setPhotoUrl(photoUrl);
						 picture.setMiniUrl(miniUrl);
						 pictureList.add(picture);
				        }
				}else{
					 picture = new LifePicture();
					 picture.setMemberInfo(memberInfo);
					 picture.setPhotoUrl(photoUrl);
					 picture.setMiniUrl(miniUrl);
					 pictureList.add(picture);
				}
				
				}
			 }
			memberInfo.setPhotoPath(pictureList.get(0).getPhotoUrl());
			memberInfo.setMiniPhotoPath(pictureList.get(0).getMiniUrl());
			memberInfo.setPictures(pictureList);
			if(!StringUtils.isBlank(request.getParameter("nickname"))){
			   memberInfo.setNickname(request.getParameter("nickname"));
			}
			if(!StringUtils.isBlank(request.getParameter("username"))){
			      memberInfo.setRealname(request.getParameter("username"));
			}
			if(!StringUtils.isBlank(request.getParameter("sex"))){
			   memberInfo.setGender(Integer.parseInt(request.getParameter("sex")));
			}
			if(!StringUtils.isBlank(request.getParameter("age"))){
			  memberInfo.setAge(Integer.parseInt(request.getParameter("age")));
			}
			if(!StringUtils.isBlank(request.getParameter("birthday"))){
			  try {
				  memberInfo.setBirthday(DateUtils.format2.parse(request.getParameter("birthday")));
			  } catch (ParseException e) {
				jsonMap.put("error", "-3");
				jsonMap.put("msg", "日期解析出错");
				ResponseUtils.renderJson(response, JSONUtils.printObject(jsonMap));
				return;
			 }
			}
			String cityId = request.getParameter("city");
			if(!StringUtils.isBlank(cityId)){
				memberInfo.setCity(cityMng.findById(Integer.parseInt(cityId)));
			}
			String majorId = request.getParameter("major");
			if(!StringUtils.isBlank(majorId)){
				memberInfo.setMajor(majorMng.findById(Integer.parseInt(majorId)));
			}
			if(!StringUtils.isBlank(request.getParameter("constellation"))){
			     memberInfo.setConstellation(request.getParameter("constellation"));
			}
			if(!StringUtils.isBlank(request.getParameter("autograph"))){
		     	memberInfo.setAutograph(request.getParameter("autograph"));
			}
			if(!StringUtils.isBlank(request.getParameter("wechat"))){
			   memberInfo.setWechat(request.getParameter("wechat"));
			}
			memberInfo = multiSelectMsg(request,memberInfo);
			memberInfo.setMember(member);
			if(memberInfo.getId() == null){
				memberInfoMng.save(memberInfo);
			}else{
			    memberInfoMng.update(memberInfo);
			}
			log.info("update memberInfo id={}", memberInfo.getId());
			memberLogMng.operating(request, "cms.memberInfo.update", "会员信息更改");
		    jsonMap.put("error", "-1");
		    jsonMap.put("msg", "更新个人信息成功");
		    ResponseUtils.renderJson(response, JSONUtils.printObject(jsonMap));
	 }
	private MemberInfo multiSelectMsg(HttpServletRequest request,MemberInfo memberInfo) {
		List<String> list = new ArrayList<String>();
		String[] labels = request.getParameterValues("labels[]"); 
		if(labels != null){
		   Collections.addAll(list, labels);
		   if(list.contains("A1.29")){
			   String label_others = request.getParameter("label_others");
			   if(label_others != null && !label_others.trim().equals("")){
				  memberInfo.setLabel_others(label_others);
			   }else{
				  memberInfo.setLabel_others("其他个性标签");
			  }
		 	  list.remove("A1.29");
	    	}else{
			 memberInfo.setLabel_others("");
		  }
		  memberInfo.setLabels(handlMsg(list));
		}
		list = new ArrayList<String>();
		String[] sports = request.getParameterValues("sports[]");
		if(sports != null){
			Collections.addAll(list, sports);
			if(list.contains("A3.27")){
				String sport_others = request.getParameter("sport_others");
				if(sport_others != null && !sport_others.trim().equals("")){
					memberInfo.setSport_others(sport_others);
				}else{
					memberInfo.setSport_others("其他运动类型");
				}
				list.remove("A3.27");
			}else{
				memberInfo.setSport_others("");
			}
			memberInfo.setSports(handlMsg(list));
		}
		
		list = new ArrayList<String>();
		String[] music = request.getParameterValues("music[]");
		if(music != null){
			Collections.addAll(list, music);
			if(list.contains("A4.11")){
				String music_others = request.getParameter("music_others");
				if(music_others != null  && !music_others.trim().equals("")){
					memberInfo.setMusic_others(music_others);
				}else{
					memberInfo.setMusic_others("其他音乐类型");
				}
				list.remove("A4.11");
			}else{
				memberInfo.setMusic_others("");
			}
			memberInfo.setMusic(handlMsg(list));
		}
		
		
		list = new  ArrayList<String>();
		String[] foods = request.getParameterValues("foods[]");
		if(foods != null){
			Collections.addAll(list, foods);
			if(list.contains("A5.11")){
				String food_others = request.getParameter("food_others");
				if(food_others != null  && !food_others.trim().equals("")){
					memberInfo.setFood_others(food_others);
				}else{
					memberInfo.setFood_others("其他食物类型");
				}
				list.remove("A5.11");
			}else{
				memberInfo.setFood_others("");
			}
			memberInfo.setFoods(handlMsg(list));
		}
		
		list = new ArrayList<String>();
		String[] films = request.getParameterValues("films[]");
		if(films != null){
			Collections.addAll(list, films);
			if(list.contains("A6.6")){
				String film_others = request.getParameter("film_others");
				if(film_others != null && !film_others.trim().equals("")){
					memberInfo.setFilm_others(film_others);
				}else{
					memberInfo.setFilm_others("其他电影类型");
				}
				list.remove("A6.6");
			}else{
				memberInfo.setFilm_others("");
			}
			memberInfo.setFilms(handlMsg(list));
		}
		
		
		list = new ArrayList<String>();
		String[] books = request.getParameterValues("books[]");
		if(books != null){
			Collections.addAll(list, books);
			if(list.contains("A7.6")){
				String book_others = request.getParameter("book_others");
				if(book_others != null  && !book_others.trim().equals("")){
					memberInfo.setBook_others(book_others);
				}else{
					memberInfo.setBook_others("其他书籍和动漫");
				}
				list.remove("A7.6");
			}else{
				memberInfo.setBook_others("");
			}
			memberInfo.setBooks(handlMsg(list));
		}
		
		
		list = new ArrayList<String>();
		String[] travels = request.getParameterValues("travels[]");
		if(travels != null){
			Collections.addAll(list, travels);
			if(list.contains("A8.6")){
				String travel_others = request.getParameter("travel_others");
				if(travel_others != null && !travel_others.trim().equals("")){
					memberInfo.setTravel_others(travel_others);
				}else{
					memberInfo.setTravel_others("其他旅行足迹");
				}
				list.remove("A8.6");
			}else{
				memberInfo.setTravel_others("");
			}
			memberInfo.setTravels(handlMsg(list));
		}
		return memberInfo;
	}
	

	private String handlMsg(List<String> list) {
		StringBuffer sb = new StringBuffer();
		sb.delete(0, list.size());
		for(String s : list){
			sb.append(s + ",");
		}
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}

	@Autowired
	private MemberMng memberMng;
	
	@Autowired
	private FileRepository fileRepository;
	
	@Autowired
	private ImageScale imageScale;
	
	@Autowired
	private MemberInfoMng memberInfoMng;
	
	@Autowired
	private MemberLogMng memberLogMng;
	
	@Autowired
	private CityMng cityMng;
	
	@Autowired
	private MajorMng majorMng;
	
	@Autowired
	private AssesCategoryMng categoryMng;
	
	@Autowired
	private ProvinceMng provinceMng;
}
