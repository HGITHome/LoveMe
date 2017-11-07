package com.dgut.main.action.assist;


import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dgut.common.util.DateUtils;
import com.dgut.common.util.RandomNumberUtils;
import com.dgut.common.web.ResponseUtils;
import com.dgut.common.web.session.SessionProvider;
import com.dgut.common.web.springmvc.RealPathResolver;
import com.dgut.core.web.WebErrors;
import com.dgut.main.Constants;
import com.dgut.main.entity.DbFile;
import com.dgut.main.entity.assist.JXField;
import com.dgut.main.manager.assist.DbFileMng;
import com.dgut.main.manager.assist.JXDataBackMng;


@Controller
public class DataAct {
	
	private static String backup_table;
	private static final Logger log = LoggerFactory
	.getLogger(DataAct.class);
	
	@RequestMapping("/data/v_list.do")
	public String list(ModelMap model, HttpServletRequest request,
			HttpServletResponse response) {

		List<String> tables = null;  
     
        try {  
            tables = dataBackMng.listTabels(dataBackMng.getDefaultCatalog());  
        }  
        catch (SQLException e) {  
            model.addAttribute("msg", e.toString());  
            return "common/error_message";  
        }  
        model.addAttribute("tables", tables);  
        return "data/list";  
	}
	
	@RequestMapping("/data/v_listfields.do")
	public String listfiled(String tablename, ModelMap model,
			HttpServletRequest request, HttpServletResponse response) {
		List<JXField> list = dataBackMng.listFields(tablename);
		model.addAttribute("list", list);
		return "data/fields";
	}
	

	
	
	
	@RequestMapping("/data/o_backup.do")
	public String backup(String tableNames,ModelMap model,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, InterruptedException {
		WebErrors errors = validateTableNames(tableNames,request);
		if(errors.hasErrors()){
			return errors.showErrorPage(model);
		}
		String backPath = realPathResolver.get(Constants.BACKUP_PATH);
		File backDirectory = new File(backPath);
		if (!backDirectory.exists()) {
			backDirectory.mkdir();
		}
		DateUtils dateUtils = DateUtils.getDateInstance(); 
		RandomNumberUtils numberUtils = RandomNumberUtils.getInstance();
		String[] tables = tableNames.split(",");
		for(String table:tables){
		   String name = dateUtils.getNowString()+numberUtils.randomNumber() + "."+ Constants.SUFFIX;
		   File file=new File(backPath,name);
		   if(!file.exists()){
		  try {
			file.createNewFile();
		  } catch (IOException e) {
		   e.printStackTrace();
		   }
		  }
		  String backFilePath = backPath + Constants.SLASH+ name;
		  Thread thread =new DateBackupTableThread(file,backFilePath,table);
		  thread.start();
		   
		}
		return "data/backupProgress";
	}
	
	private void saveDbFileInfo(String backupPath, String table) {
		dbFileMng.save(backupPath,table);
	}

	private WebErrors validateTableNames(String tableNames,
			HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if(StringUtils.isBlank(tableNames)){
			errors.addErrorCode("error.required", "表名");
			return errors;
		}
		return errors;
	}

	@RequestMapping("/data/v_listfiles.do")
	public String listBackUpFiles(ModelMap model, HttpServletRequest request,
			HttpServletResponse response) {
		//model.addAttribute("list",resourceMng.listFile(Constants.BACKUP_PATH, false));
		return "data/files";
	}
	
	
	

	
	@RequestMapping("/data/o_backup_progress.do")
	public void getBackupProgress(HttpServletRequest request, HttpServletResponse response) throws JSONException{
		JSONObject json=new JSONObject();
		json.put("tablename", backup_table);
		json.put("progress", backup_table);
		json.put("per", progress);
		ResponseUtils.renderJson(response, json.toString());
	}
	
	
	

	
	
	private class DateBackupTableThread extends Thread{
		private File file;
		private String backFilePath;
		private String tableName;
		//private String[] tablenames;
		public DateBackupTableThread(File file, String backFilePath,String tableName) {
			super();
			this.file = file;
			this.backFilePath = backFilePath;
			this.tableName = tableName;
			//this.tablenames=tablenames;
		
		}
		public void run() {
//			FileOutputStream out;
//			OutputStreamWriter writer=null;
//			try {
//				out = new FileOutputStream(file);
//				writer = new OutputStreamWriter(out, "utf8");
//				writer.write(Constants.ONESQL_PREFIX + DISABLEFOREIGN);
//				for (int i=0;i<tablenames.length;i++) {
//					backup_table=tablenames[i];
//					backupTable(writer,tablenames[i]);
//				}
//				writer.write(Constants.ONESQL_PREFIX + ABLEFOREIGN);
//				backup_table="";
//				writer.close();
//				out.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
			progress = 12;
			backup_table = "备份数据库中";
			/*String sql = "BACKUP DATABASE [old] TO  DISK = N'"+backFilePath+"' WITH NOFORMAT, NOINIT,  NAME = N'old-FULL BACKUP', SKIP, NOREWIND, NOUNLOAD,  STATS = 10 ";
			dataBackMng.executeSQL(sql);*/
			
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("D:\\Mysql\\bin\\mysqldump").append(" --opt").append(" -h").append("127.0.0.1");
			stringBuilder.append(" --user=").append("root") .append(" --password=").append("123456");
			stringBuilder.append(" --result-file=").append(backFilePath). append(" "). append(" --default-character-set=utf8 ").append("loveme").append(" " +tableName);
			try {
				Process process = Runtime.getRuntime().exec(stringBuilder.toString());
				if (process.waitFor() == 0) {// 0 表示线程正常终止。
					progress = 100;
					backup_table="";
					saveDbFileInfo(backFilePath,tableName);
					return ;
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
			
		}
		
		
	}
	private static Integer progress;
	
	@Autowired
	private RealPathResolver realPathResolver;
	
	@Autowired
	private JXDataBackMng dataBackMng;
	
	/*@Autowired
	private JXResourceMng resourceMng;*/
	
	@Autowired
    private DbFileMng dbFileMng;
	
	@Autowired
	private SessionProvider session;
}
