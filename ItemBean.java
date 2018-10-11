package com.eastedge.mobilesurvey.bean;

import java.io.Serializable;

public class ItemBean implements Serializable {
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public QuestionBean getQuestion() {
		return question;
	}

	public void setQuestion(QuestionBean question) {
		this.question = question;
	}

	private QuestionBean question;
}
