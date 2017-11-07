package com.dgut.member.entity.base;

import java.io.Serializable;

import com.dgut.member.entity.Category;

public abstract class BaseType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BaseType() {
		super();
	}
	private java.lang.String key;
	private java.lang.String name;
	private Category category;

	public java.lang.String getKey() {
		return key;
	}
	public void setKey(java.lang.String key) {
		this.key = key;
	}
	public java.lang.String getName() {
		return name;
	}
	public void setName(java.lang.String name) {
		this.name = name;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseType other = (BaseType) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
	
	
}
