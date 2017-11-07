package com.dgut.app.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
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
import com.dgut.main.entity.AccusationPicture;
import com.dgut.main.entity.AccusationType;
import com.dgut.main.entity.AccusationUser;
import com.dgut.main.manager.AccusationTypeMng;
import com.dgut.main.manager.AccusationUserMng;
import com.dgut.main.web.CmsUtils;
import com.dgut.member.entity.Member;
import com.dgut.member.manager.MemberLogMng;
import com.dgut.member.manager.MemberMng;

@Controller
public class AccusationAct {
	private final static Logger log = LoggerFactory.getLogger(AccusationAct.class);
	@RequestMapping("/accusation.do")
	public void accusation(HttpServletRequest request,HttpServletResponse response,@RequestParam(value="photo" , required=false) MultipartFile[] photos) throws IOException{
		HashMap<String, Object> jsonMap = new HashMap<String, Object>();
		AccusationUser acUser = new AccusationUser();
		Member member = CmsUtils.getMember(request);
		if(member == null){
			jsonMap.put("error", "-3");
			jsonMap.put("msg", "太久未登录了，请先登录");
			ResponseUtils.renderJson(response, JSONUtils.printObject(jsonMap));
			return;
		}
		acUser.setReporter(member);
		String informantId = request.getParameter("aid");
		if(informantId == null){
			jsonMap.put("error", "-3");
			jsonMap.put("msg", "被举报的id不能为空");
			ResponseUtils.renderJson(response, JSONUtils.printObject(jsonMap));
			return;
		}
		Member informant = memberMng.findById(Integer.parseInt(informantId));
		acUser.setInformant(informant);
		String report_content = request.getParameter("reportContent");
		if(report_content == null){
			jsonMap.put("error", "-3");
			jsonMap.put("msg", "举报内容不能为空");
			ResponseUtils.renderJson(response, JSONUtils.printObject(jsonMap));
			return;
		}
		acUser.setReport_content(report_content);
		String typeId = request.getParameter("typeId");
		if(typeId == null){
			jsonMap.put("error", "-3");
			jsonMap.put("msg", "请选择举报类型");
			ResponseUtils.renderJson(response, JSONUtils.printObject(jsonMap));
			return;
		}
		AccusationType type = typeMng.findById(Integer.parseInt(typeId));
		acUser.setAcType(type);
		String photoUrl = null ,miniUrl = null;
		List<AccusationPicture> pictureList = new ArrayList<AccusationPicture>();
		if(photos == null){
			jsonMap.put("error", "-3");
			jsonMap.put("msg", "图片证据不能为空");
			ResponseUtils.renderJson(response, JSONUtils.printObject(jsonMap));
			return;
		}else{
			for(MultipartFile photo : photos){
				AccusationPicture picture = new AccusationPicture();
				String origName = photo.getOriginalFilename();
				String extName = FilenameUtils.getExtension(origName).toLowerCase(Locale.ENGLISH);
				if(!"gif,jpg,jpeg,png,bmp".contains(extName)){
					jsonMap.put("error", "-3");
					jsonMap.put("msg", "请上传后缀名为:gif,jpg,jpeg,png,bmp的照片");
					ResponseUtils.renderJson(response, JSONUtils.printObject(jsonMap));
					return;
				}
				try {
					photoUrl=fileRepository.storeByExt("/upload/accusation/file", extName, photo);
					ServletContext  context=request.getSession().getServletContext();
					File fi = new File(context.getRealPath(photoUrl)); //大图文件  

					String miniPath=context.getRealPath("/upload/accusation/file");
					String miniName=UploadUtils.generateFilename("mini", extName);
					File fo = new File(miniPath,miniName); //将要转换出的小图文件
					miniUrl="/upload/accusation/file/"+miniName;
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
				picture.setAcUser(acUser);
				picture.setPhotoUrl(photoUrl);
				picture.setMiniUrl(miniUrl);
				pictureList.add(picture);
			}
		}
		acUser.setAcPictures(pictureList);
		acUserMng.save(acUser);
		jsonMap.put("error", "-1");
		jsonMap.put("msg", "举报成功");
		memberLogMng.operating(request, "cms.accusation.user", "accusation.user.id="+informantId);
		ResponseUtils.renderJson(response, JSONUtils.printObject(jsonMap));
 	}
	
	@Autowired
	private FileRepository fileRepository;
	
	@Autowired
	private ImageScale imageScale;
	
	@Autowired
	private MemberMng memberMng;
	
	@Autowired
	private MemberLogMng memberLogMng;
	
	@Autowired
	private AccusationUserMng acUserMng;
	
	@Autowired
	private AccusationTypeMng typeMng;
}
