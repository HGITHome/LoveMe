package com.dgut.member.entity.base;

import java.io.Serializable;
import java.util.Set;

import com.dgut.member.entity.Type;

public abstract class BaseCategory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BaseCategory() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	private java.lang.Integer id;
	private java.lang.String name;
	private java.lang.Integer priority;
	private java.lang.String listName;
	private Set<Type> types;

	public java.lang.Integer getId() {
		return id;
	}
	public void setId(java.lang.Integer id) {
		this.id = id;
	}
	public java.lang.String getName() {
		return name;
	}
	public void setName(java.lang.String name) {
		this.name = name;
	}
	public java.lang.Integer getPriority() {
		return priority;
	}
	public void setPriority(java.lang.Integer priority) {
		this.priority = priority;
	}

	
	public java.lang.String getListName() {
		return listName;
	}
	public void setListName(java.lang.String listName) {
		this.listName = listName;
	}
	public Set<Type> getTypes() {
		return types;
	}
	public void setTypes(Set<Type> types) {
		this.types = types;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		BaseCategory other = (BaseCategory) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
	
	
}
