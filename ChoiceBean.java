package com.eastedge.mobilesurvey.bean;

import java.io.Serializable;

public class ChoiceBean implements Serializable{
	private String id;

	private String alias;

	private String label;

	public String getAlias() {
		return alias;
	}

	public String getId() {
		return id;
	}

	public String getLabel() {
		return label;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public void setId(String id) {
		this.id = id;
	}
	public void setLabel(String label) {
		this.label = label;
	}

}
