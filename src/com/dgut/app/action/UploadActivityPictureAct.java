package com.dgut.app.action;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

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
import com.dgut.common.web.ResponseUtils;
import com.dgut.main.web.CmsUtils;
import com.dgut.member.entity.LoverActivity;
import com.dgut.member.entity.LoverActivityRecord;
import com.dgut.member.entity.Member;
import com.dgut.member.manager.LoverActivityMng;
import com.dgut.member.manager.LoverActivityRecordMng;
import com.dgut.member.manager.MemberLogMng;

@Controller
public class UploadActivityPictureAct {
	
	private static final Logger log = LoggerFactory.getLogger(UploadActivityPictureAct.class);
	
	@RequestMapping("/uploadRecordPicture.do")
	public void uploadRecordPicture(HttpServletRequest request,HttpServletResponse response,@RequestParam(value="photo" , required=false) MultipartFile photo) throws IOException{
		HashMap<String,Object> jsonMap = new HashMap<String, Object>();
		Member member = CmsUtils.getMember(request);
		if(member == null){
			jsonMap.put("error", "-3");
			jsonMap.put("error", "太久未登录了，请先登录");
			ResponseUtils.renderJson(response, JSONUtils.printObject(jsonMap));
			return;
		}
		String loverActivityId = request.getParameter("aid");
		if(StringUtils.isBlank(loverActivityId)){
			jsonMap.put("error", "-3");
			jsonMap.put("error", "参数不完成");
			ResponseUtils.renderJson(response, JSONUtils.printObject(jsonMap));
			return;
		}
		LoverActivity loverActivity = loverActivityMng.findById(Integer.parseInt(loverActivityId));
		String photoUrl = null,miniUrl = null;
		 if(photo != null){
				String origName = photo.getOriginalFilename();
				String extName = FilenameUtils.getExtension(origName).toLowerCase(Locale.ENGLISH);
				if(!"gif,jpg,jpeg,png,bmp".contains(extName)){
					jsonMap.put("error", "-3");
					jsonMap.put("msg", "请上传后缀名为:gif,jpg,jpeg,png,bmp的照片");
					ResponseUtils.renderJson(response, JSONUtils.printObject(jsonMap));
					return;
				}
				try {
					photoUrl=fileRepository.storeByExt("/upload/activity/file", extName, photo);
					ServletContext  context=request.getSession().getServletContext();
					File fi = new File(context.getRealPath(photoUrl)); //大图文件  

					String miniPath=context.getRealPath("/upload/activity/file");
					String miniName=UploadUtils.generateFilename("mini", extName);
					File fo = new File(miniPath,miniName); //将要转换出的小图文件
					miniUrl="/upload/activity/file/"+miniName;
					imageScale.resizeFix(fi, fo,180,180);
				} catch (IOException e) {
					log.error("上传照片记录时：" + e.getMessage());
					jsonMap.put("error", "-3");
					jsonMap.put("msg", e.getMessage());
					ResponseUtils.renderJson(response, JSONUtils.printObject(jsonMap));
					return;
				} catch (Exception e) {
					log.error("上传照片记录时：" + e.getMessage());
					jsonMap.put("error", "-3");
					jsonMap.put("msg", e.getMessage());
					ResponseUtils.renderJson(response, JSONUtils.printObject(jsonMap));
					return;
			    }
		   }
		 LoverActivityRecord record = new LoverActivityRecord();
		 record.setLoverActivity(loverActivity);
		 record.setMember(member);
		 record.setUpload_time(new Date());
		 record.setPhotoPath(photoUrl);
		 record.setMiniPath(miniUrl);
		 recordMng.save(record);
		 jsonMap.put("error", "-1");
		 jsonMap.put("error", "上传成功");
		 log.info("upload recordPicture memberId={}",member.getId());
		 memberLogMng.operating(request, "cms.member.uploadRecordPicture", "username=" + member.getUsername());
		 ResponseUtils.renderJson(response, JSONUtils.printObject(jsonMap));
	}
	
	
	@Autowired
	private FileRepository fileRepository;
	
	@Autowired
	private ImageScale imageScale;
	
	@Autowired
	private LoverActivityMng loverActivityMng;
	
	@Autowired
	private LoverActivityRecordMng recordMng;
	
	@Autowired
	private MemberLogMng memberLogMng;
}
