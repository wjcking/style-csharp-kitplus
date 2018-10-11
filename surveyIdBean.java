package com.eastedge.mobilesurvey.bean;

import java.io.Serializable;

public class surveyIdBean implements Serializable {
	private String surveyid;
	private String version;
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSurveyid() {
		return surveyid;
	}

	public void setSurveyid(String surveyid) {
		this.surveyid = surveyid;
	}

	public String getSurveyname() {
		return surveyname;
	}

	public void setSurveyname(String surveyname) {
		this.surveyname = surveyname;
	}

	private String surveyname;
}
