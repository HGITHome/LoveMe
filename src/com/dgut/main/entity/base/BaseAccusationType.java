package com.dgut.main.entity.base;

import java.io.Serializable;

import com.dgut.main.entity.AccusationCategory;

public class BaseAccusationType implements Serializable {

	/**
	 * 举报子类
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private String typeName;
	
	private Integer priority;
	
	private AccusationCategory accusationCategory;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public AccusationCategory getAccusationCategory() {
		return accusationCategory;
	}

	public void setAccusationCategory(AccusationCategory accusationCategory) {
		this.accusationCategory = accusationCategory;
	}
	
	
}
