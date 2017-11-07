package com.dgut.member.action;

import static com.dgut.common.page.SimplePage.cpn;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.dgut.common.file.FileNameUtils;
import com.dgut.common.image.ImageScale;
import com.dgut.common.page.Pagination;
import com.dgut.common.upload.FileRepository;
import com.dgut.common.upload.UploadUtils;
import com.dgut.common.util.DateUtils;
import com.dgut.common.web.CookieUtils;
import com.dgut.core.web.WebErrors;
import com.dgut.member.entity.Category;
import com.dgut.member.entity.City;
import com.dgut.member.entity.LifePicture;
import com.dgut.member.entity.Major;
import com.dgut.member.entity.Member;
import com.dgut.member.entity.MemberInfo;
import com.dgut.member.entity.Province;
import com.dgut.member.entity.Type;
import com.dgut.member.manager.AssesCategoryMng;
import com.dgut.member.manager.CityMng;
import com.dgut.member.manager.MajorMng;
import com.dgut.member.manager.MemberInfoMng;
import com.dgut.member.manager.MemberLogMng;
import com.dgut.member.manager.MemberMng;
import com.dgut.member.manager.PictureMng;
import com.dgut.member.manager.ProvinceMng;

@Controller
@RequestMapping("member")
public class MemberAct {
	private static final Logger log = LoggerFactory.getLogger(MemberAct.class);
	
	@RequestMapping("v_list.do")
	public String memberlist(HttpServletRequest request,HttpServletResponse response,ModelMap model,String queryUsername,String queryEmail,String queryMobile,Integer pageNo){
		Pagination pagination = memberMng.getPage(queryUsername,queryEmail,queryMobile,cpn(pageNo), CookieUtils.getPageSize(request));
		model.addAttribute("queryUsername", queryUsername);
		model.addAttribute("queryEmail", queryEmail);
		model.addAttribute("queryMobile", queryMobile);
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("pagination", pagination);
		return "member/info/list";
	}
	
	@RequestMapping("o_info.do")
	public String memberShow(HttpServletRequest request,HttpServletResponse response,ModelMap model,Integer id){
		WebErrors errors = validateId(request,id);
		if(errors.hasErrors()){
			return errors.showErrorPage(model);
		}
		Member member = memberMng.findById(id);
		if(member==null){
			errors.addErrorString("会员不存在");
			return errors.showErrorPage(model);
		}
		if(member.getMemberInfo() == null){
			errors.addErrorString("个人信息未设置，请前往设置");
			return errors.showErrorPage(model);
		}
		memberMng.convertMemberInfoEntity(member.getMemberInfo());
		model.addAttribute("pictureList",member.getMemberInfo().getPictures());
		model.addAttribute("member",member);
		return "member/info/show";
	}
	
	@RequestMapping("o_edit.do")
	public String memberEdit(HttpServletRequest request,HttpServletResponse response,ModelMap model,Integer id){
		List<City> cityList=null;
		Member member = memberMng.findById(id);
		List<Province> provinceList = provinceMng.getlist();
		if(member.getMemberInfo() != null && member.getMemberInfo().getCity() != null){
		     cityList = new ArrayList<City>(member.getMemberInfo().getCity().getProvince().getCitys());
		}else{
			cityList = cityMng.getList();
		}
		List<Major> majorList = majorMng.getList();
		List<Category> categoryList = categoryMng.getList();
		categoryListToModel(categoryList,model);
		model.addAttribute("member", member);
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("cityList", cityList);
		model.addAttribute("majorList", majorList);
		return "member/info/edit";
	}
	
	
	@RequestMapping("o_update.do")
	public String memberSave(Integer id,HttpServletRequest request,HttpServletResponse response,@RequestParam(value = "photo", required = false) MultipartFile[] photo,ModelMap model){
		WebErrors errors = validateId(request,id);
		String photoUrl=null,miniUrl=null;
		List<LifePicture> pictureList = null;
		LifePicture picture = null;
		if(errors.hasErrors()){
			return errors.showErrorPage(model);
		}
		Member member = memberMng.findById(id);
		MemberInfo memberInfo = member.getMemberInfo();
		pictureList = memberInfo.getPictures();
		Integer pictureSize = pictureList.size();
		if(pictureSize == 0){
			pictureList = new ArrayList<LifePicture>();
		}
		if(photo != null){
			int i = 0;
		 for(MultipartFile file : photo){
			String origName = file.getOriginalFilename();
			String extName = FilenameUtils.getExtension(origName).toLowerCase(Locale.ENGLISH);
			if(!"gif,jpg,jpeg,png,bmp".contains(extName)){
				errors.addErrorString("请上传后缀名为:gif,jpg,jpeg,png,bmp的照片");
				return errors.showErrorPage(model);
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
				errors.addErrorString("上传相片失败");
				return errors.showErrorPage(model);
			} catch (Exception e) {
				errors.addErrorString("上传相片失败");
				return errors.showErrorPage(model);
		    }
	
		     if(photo.length <= pictureSize || i <= (pictureSize - 1)){
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
			}
		 }
		memberInfo.setPhotoPath(pictureList.get(0).getPhotoUrl());
		memberInfo.setMiniPhotoPath(pictureList.get(0).getMiniUrl());
		memberInfo.setPictures(pictureList);
		memberInfo.setNickname(request.getParameter("nickname"));
		memberInfo.setRealname(request.getParameter("username"));
		memberInfo.setGender(Integer.parseInt(request.getParameter("sex")));
		memberInfo.setAge(Integer.parseInt(request.getParameter("age")));
		try {
			memberInfo.setBirthday(DateUtils.format2.parse(request.getParameter("birthday")));
		} catch (ParseException e) {
			errors.addErrorString("出生日期格式有误");
			return errors.showErrorPage(model);

		}
		String cityId = request.getParameter("city");
		if(cityId != null){
			memberInfo.setCity(cityMng.findById(Integer.parseInt(cityId)));
		}
		String majorId = request.getParameter("major");
		if(majorId != null && !majorId.trim().equals("")){
			memberInfo.setMajor(majorMng.findById(Integer.parseInt(majorId)));
		}
		memberInfo.setConstellation(request.getParameter("constellation"));
		memberInfo.setAutograph(request.getParameter("autograph"));
		memberInfo.setWechat(request.getParameter("wechat"));
		member.setEmail(request.getParameter("email"));
		member.setMobile(request.getParameter("mobile"));
		memberInfo = multiSelectMsg(request,memberInfo);
		memberInfo.setMember(member);
	    memberInfoMng.update(memberInfo);
		log.info("update memberInfo id={}", memberInfo.getId());
		//memberLogMng.operating(request, "cms.memberInfo.update", "会员信息更改");
		return "redirect:/admin/member/v_list.do";
	}
	
	@RequestMapping(value="ajaxAddress.do" , method=RequestMethod.POST)
	public void ajaxAddress(Integer id,HttpServletRequest request,HttpServletResponse response,ModelMap model) throws IOException, IOException{
		Province province = provinceMng.findById(id);
		List<City> cityList = new ArrayList<City>(province.getCitys());
		String data = provinceMng.dataToString(cityList);
		response.getOutputStream().write(data.getBytes("UTF-8"));
	}
	

	private void categoryListToModel(List<Category> categoryList, ModelMap model) {
		for(int i = 0 ; i < categoryList.size(); i ++ ){
			model.addAttribute(categoryList.get(i).getListName(), new ArrayList<Type>(categoryList.get(i).getTypes()));
		}
	}

	private WebErrors validateId(HttpServletRequest request, Integer id) {
		WebErrors errors = WebErrors.create(request);
		if(errors.ifNull(id, "id")){
			return errors;
		}
		return errors;
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
	private CityMng cityMng;
	
	@Autowired
	private MajorMng majorMng;
	
	@Autowired
	private AssesCategoryMng categoryMng;
	
	@Autowired
	private FileRepository fileRepository;
	
	@Autowired
	private ImageScale imageScale;
	
	@Autowired
	private MemberInfoMng memberInfoMng;
	
	@Autowired
	private MemberLogMng memberLogMng;

	@Autowired
	private ProvinceMng provinceMng;
	
	@Autowired
	private PictureMng pictureMng;
}
