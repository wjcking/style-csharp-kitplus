package com.eastedge.mobilesurvey.bean;

import java.io.Serializable;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;


public class SurveyBean implements Serializable {
	private String endtime;
	private String starttime;
	private String surveyid;
	private String name;
	private String order;
	private String isalive;
	private String description;
	
	private String type;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	private String version;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	private List<ItemBean> item;
	public String getEndtime() {
		return endtime;
	}
	public String getIsalive() {
		return isalive;
	}
	public List<ItemBean> getItem() {
		return item;
	}
	public String getName() {
		return name;
	}
	public String getOrder() {
		return order;
	}
	public String getStarttime() {
		return starttime;
	}
	public String getSurveyid() {
		return surveyid;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public void setIsalive(String isalive) {
		this.isalive = isalive;
	}
	public void setItem(List<ItemBean> item) {
		this.item = item;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOrder(String order) {
		this.order = order;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public void setSurveyid(String surveyid) {
		this.surveyid = surveyid;
	}


}
