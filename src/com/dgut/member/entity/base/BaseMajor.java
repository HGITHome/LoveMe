package com.dgut.member.entity.base;

import java.io.Serializable;
import java.util.Set;

import com.dgut.member.entity.MemberInfo;

public abstract class BaseMajor implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


		// constructors
		public BaseMajor () {
			initialize();
		}

		/**
		 * Constructor for primary key
		 */
		public BaseMajor (java.lang.Integer id) {
			this.setId(id);
			initialize();
		}

		/**
		 * Constructor for required fields
		 */

		protected void initialize () {}



		private int hashCode = Integer.MIN_VALUE;
		
		private java.lang.Integer id;
		private java.lang.String majorName;
		private java.lang.Integer priority;
		
		private Set<MemberInfo> memberInfoSet;
		
		
		public BaseMajor( Integer id, String majorName,
				Integer priority) {
			super();
			this.id = id;
			this.majorName = majorName;
			this.priority = priority;
		}

		public int getHashCode() {
			return hashCode;
		}

		public void setHashCode(int hashCode) {
			this.hashCode = hashCode;
		}

		public java.lang.Integer getId() {
			return id;
		}

		public void setId(java.lang.Integer id) {
			this.id = id;
		}

		public java.lang.String getMajorName() {
			return majorName;
		}

		public void setMajorName(java.lang.String majorName) {
			this.majorName = majorName;
		}

		public java.lang.Integer getPriority() {
			return priority;
		}

		public void setPriority(java.lang.Integer priority) {
			this.priority = priority;
		}

		public Set<MemberInfo> getMemberInfoSet() {
			return memberInfoSet;
		}

		public void setMemberInfoSet(Set<MemberInfo> memberInfoSet) {
			this.memberInfoSet = memberInfoSet;
		}
		
		
		
}
