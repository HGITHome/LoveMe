package com.dgut.main.entity.base;

import java.io.Serializable;
import java.util.Date;

public class BaseDbFile implements Serializable {

	/**
	 * 文件类
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private String tableName;
	
	private String filePath;
	
	private String fileName;
	
	private double fileSize;
	
	private Date lastModified;
	
	private Date backupTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public double getFileSize() {
		return fileSize;
	}

	public void setFileSize(double fileSize) {
		this.fileSize = fileSize;
	}

	
	
	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public Date getBackupTime() {
		return backupTime;
	}

	public void setBackupTime(Date backupTime) {
		this.backupTime = backupTime;
	}

	
	
}
