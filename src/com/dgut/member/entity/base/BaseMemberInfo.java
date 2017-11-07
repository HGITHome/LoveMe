package com.dgut.member.entity.base;

import java.io.Serializable;
import java.util.List;

import com.dgut.member.entity.City;
import com.dgut.member.entity.LifePicture;
import com.dgut.member.entity.Major;
import com.dgut.member.entity.Member;

public class BaseMemberInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public BaseMemberInfo(){
		initialize();
	}
	private void initialize() {}
	
	public BaseMemberInfo (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}
	private int hashCode = Integer.MIN_VALUE;
	
	private java.lang.Integer id;
	private Member member;
	private java.lang.String nickname;
	private java.lang.String realname;
	private java.lang.Integer gender;
	private java.lang.Integer age;
	private java.util.Date birthday;
	private java.lang.String constellation;
	private Major major;
	private java.lang.String autograph;//签名
	private java.lang.String wechat;
	private java.lang.Integer thumbs_up;
	private City city;
	private java.lang.String labels;
	private java.lang.String label_others;
	private java.lang.String sports;
	private java.lang.String sport_others;
	private java.lang.String music;
	private java.lang.String music_others;
	private java.lang.String foods;
	private java.lang.String food_others;
	private java.lang.String films;
	private java.lang.String film_others;
	private java.lang.String books;
	private java.lang.String book_others;
	private java.lang.String travels;
	private java.lang.String travel_others;
	private java.lang.String photoPath;
	private java.lang.String miniPhotoPath;
	private java.lang.Integer pair_id;
	private List<LifePicture> pictures;
	
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
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
	public java.lang.String getNickname() {
		return nickname;
	}
	public void setNickname(java.lang.String nickname) {
		this.nickname = nickname;
	}
	public java.lang.String getRealname() {
		return realname;
	}
	public void setRealname(java.lang.String realname) {
		this.realname = realname;
	}
	public java.lang.Integer getGender() {
		return gender;
	}
	public void setGender(java.lang.Integer gender) {
		this.gender = gender;
	}
	public java.lang.Integer getAge() {
		return age;
	}
	public void setAge(java.lang.Integer age) {
		this.age = age;
	}
	public java.util.Date getBirthday() {
		return birthday;
	}
	public void setBirthday(java.util.Date birthday) {
		this.birthday = birthday;
	}

	public java.lang.String getConstellation() {
		return constellation;
	}
	public void setConstellation(java.lang.String constellation) {
		this.constellation = constellation;
	}

	public Major getMajor() {
		return major;
	}
	public void setMajor(Major major) {
		this.major = major;
	}
	public java.lang.String getAutograph() {
		return autograph;
	}
	public void setAutograph(java.lang.String autograph) {
		this.autograph = autograph;
	}
	public java.lang.String getWechat() {
		return wechat;
	}
	public void setWechat(java.lang.String wechat) {
		this.wechat = wechat;
	}
	

	public java.lang.Integer getThumbs_up() {
		return thumbs_up;
	}
	public void setThumbs_up(java.lang.Integer thumbs_up) {
		this.thumbs_up = thumbs_up;
	}
	public City getCity() {
		return city;
	}
	public void setCity(City city) {
		this.city = city;
	}
	public java.lang.String getLabels() {
		return labels;
	}
	public void setLabels(java.lang.String labels) {
		this.labels = labels;
	}
	public java.lang.String getLabel_others() {
		return label_others;
	}
	public void setLabel_others(java.lang.String label_others) {
		this.label_others = label_others;
	}

	public java.lang.String getSports() {
		return sports;
	}
	public void setSports(java.lang.String sports) {
		this.sports = sports;
	}
	public java.lang.String getSport_others() {
		return sport_others;
	}
	public void setSport_others(java.lang.String sport_others) {
		this.sport_others = sport_others;
	}
	public java.lang.String getMusic() {
		return music;
	}
	public void setMusic(java.lang.String music) {
		this.music = music;
	}
	public java.lang.String getMusic_others() {
		return music_others;
	}
	public void setMusic_others(java.lang.String music_others) {
		this.music_others = music_others;
	}
	public java.lang.String getFoods() {
		return foods;
	}
	public void setFoods(java.lang.String foods) {
		this.foods = foods;
	}
	public java.lang.String getFood_others() {
		return food_others;
	}
	public void setFood_others(java.lang.String food_others) {
		this.food_others = food_others;
	}
	public java.lang.String getFilms() {
		return films;
	}
	public void setFilms(java.lang.String films) {
		this.films = films;
	}
	public java.lang.String getFilm_others() {
		return film_others;
	}
	public void setFilm_others(java.lang.String film_others) {
		this.film_others = film_others;
	}
	public java.lang.String getBooks() {
		return books;
	}
	public void setBooks(java.lang.String books) {
		this.books = books;
	}
	public java.lang.String getBook_others() {
		return book_others;
	}
	public void setBook_others(java.lang.String book_others) {
		this.book_others = book_others;
	}
	public java.lang.String getTravels() {
		return travels;
	}
	public void setTravels(java.lang.String travels) {
		this.travels = travels;
	}
	public java.lang.String getTravel_others() {
		return travel_others;
	}
	public void setTravel_others(java.lang.String travel_others) {
		this.travel_others = travel_others;
	}
	public java.lang.String getPhotoPath() {
		return photoPath;
	}
	public void setPhotoPath(java.lang.String photoPath) {
		this.photoPath = photoPath;
	}
	public java.lang.String getMiniPhotoPath() {
		return miniPhotoPath;
	}
	public void setMiniPhotoPath(java.lang.String miniPhotoPath) {
		this.miniPhotoPath = miniPhotoPath;
	}

	public java.lang.Integer getPair_id() {
		return pair_id;
	}
	public void setPair_id(java.lang.Integer pair_id) {
		this.pair_id = pair_id;
	}
	
	

	public List<LifePicture> getPictures() {
		return pictures;
	}
	public void setPictures(List<LifePicture> pictures) {
		this.pictures = pictures;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((realname == null) ? 0 : realname.hashCode());
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
		BaseMemberInfo other = (BaseMemberInfo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (realname == null) {
			if (other.realname != null)
				return false;
		} else if (!realname.equals(other.realname))
			return false;
		return true;
	}
	
	
	
	
	
}
