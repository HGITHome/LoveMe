package com.dgut.main.action.assist;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.dgut.common.page.SimplePage.cpn;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dgut.common.page.Pagination;
import com.dgut.common.util.DateUtils;
import com.dgut.common.util.ZipUtils;
import com.dgut.common.web.CookieUtils;
import com.dgut.core.web.WebErrors;
import com.dgut.main.entity.DbFile;
import com.dgut.main.manager.AdminLogMng;
import com.dgut.main.manager.assist.DbFileMng;

@Controller
@RequestMapping("data/file")
public class DbFileAct {
	@RequestMapping("v_list.do")
	public String getPage(HttpServletRequest request,HttpServletResponse response,ModelMap model,Integer pageNo){
		Pagination pagination = dbFileMng.getPage(cpn(pageNo), CookieUtils.getPageSize(request));
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("pagination", pagination);
		return "data/dbfilelist";
	}
	
	@RequestMapping("o_export.do")
	public void exportFile(HttpServletRequest request,HttpServletResponse response,ModelMap model,String fileNames) throws Exception{
		validateFileName(fileNames,request,model);
		String[] fileNameArray = fileNames.split(",");
		DateUtils dateUtils = DateUtils.getDateInstance();
		response.setContentType("APPLICATION/OCTET-STREAM");  
		response.setHeader("content-disposition", "attachment;filename=" + dateUtils.getNowString()+".zip");
		
		ZipOutputStream out = new ZipOutputStream(response.getOutputStream());
        try {
        	for(String fileName : fileNameArray){
        	   DbFile file = dbFileMng.findByFileName(fileName);
               ZipUtils.doCompress(file.getFilePath()+file.getFileName(), out);
               response.flushBuffer();
        	}
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            out.close();
        }
		
		adminLogMng.operating(request, "cms.backup.database.tables", "backup.database.tables.numbers="+fileNameArray.length);
	/*	FileInputStream in = new FileInputStream(file.getFilePath());
		OutputStream out = response.getOutputStream();
		byte buffer[] = new byte[1024];
		int len = 0;
		while((len=in.read(buffer))>0){
		     out.write(buffer, 0, len);
		  }
	    in.close();
	    out.close();*/
	}
	@RequestMapping("o_delete.do")
	public String fileDelete(HttpServletRequest request,HttpServletResponse response,ModelMap model,String fileNames){
		WebErrors errors = validateDelete(fileNames,request);
		if(errors.hasErrors()){
			return errors.showErrorPage(model);
		}
		String[] fileNameArray = fileNames.split(",");
		for(String fileName : fileNameArray){
			DbFile file = dbFileMng.findByFileName(fileName);
			if(!deleteFile(file.getFilePath()+fileName)){
				errors.addErrorString("删除文件失败");
				return errors.showErrorPage(model);
			}
			dbFileMng.deleteByFileName(file.getFileName());
		}
		adminLogMng.operating(request, "cms.backup.files.delete", "delete.backup.files.numbers="+fileNameArray.length);
		return "redirect:v_list.do";
	}
	
	private boolean deleteFile(String filePath) {
		File file = new File(filePath);
		if(file.exists() && file.isFile()){
			file.delete();
			return true;
		}
		return false;
	}

	private WebErrors validateDelete(String fileNames,
			HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if(StringUtils.isBlank(fileNames)){
			errors.addErrorCode("error.required", "文件名");
			return errors;
		}
		return errors;
	}

	public String validateFileName(String fileNames,
			HttpServletRequest request,ModelMap model) {
		WebErrors errors = WebErrors.create(request);
		if(StringUtils.isBlank(fileNames)){
			errors.addErrorCode("error.required", "文件名");
			return errors.showErrorPage(model);
		}
		return "";
	}

	@Autowired
	private DbFileMng dbFileMng;
	
	@Autowired
	private AdminLogMng adminLogMng;
}
