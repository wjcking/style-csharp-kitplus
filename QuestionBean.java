package com.eastedge.mobilesurvey.bean;

import java.io.Serializable;
import java.util.List;

public class QuestionBean implements Serializable {
	private int id;

	@Override
	public String toString() {
		return "QuestionBean [id=" + id + ", type=" + type + ", order=" + order
				+ ", alias=" + alias + ", text=" + text + ", max_select="
				+ max_select + ", min_select=" + min_select + ", dialog="
				+ dialog + ", postconditions=" + postconditions + ", choices="
				+ choices + "]";
	}

	private String type;
	private String order;
	private String alias;
	private String text;
	private String max_select;
	private String min_select;
	private String dialog;
	private String barcodeinfo;
	private List<ChoiceBean> choices;

	public String getBarcodeinfo() {
		return barcodeinfo;
	}

	public void setBarcodeinfo(String barcodeinfo) {
		this.barcodeinfo = barcodeinfo;
	}

	private List<PostconditionBean> postconditions;

	// private List<PreconditionBean> preconditions;

	

	public String getAlias() {
		return alias;
	}

	public List<ChoiceBean> getChoices() {
		return choices;
	}

	public String getDialog() {
		return dialog;
	}

	public int getId() {
		return id;
	}

	public String getMax_select() {
		return max_select;
	}

	public String getMin_select() {
		return min_select;
	}

	public String getOrder() {
		return order;
	}

	public List<PostconditionBean> getPostconditions() {
		return postconditions;
	}

	// public List<PreconditionBean> getPreconditions() {
	// return preconditions;
	// }
	public String getText() {
		return text;
	}

	public String getType() {
		return type;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public void setChoices(List<ChoiceBean> choices) {
		this.choices = choices;
	}

	public void setDialog(String dialog) {
		this.dialog = dialog;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setMax_select(String max_select) {
		this.max_select = max_select;
	}

	public void setMin_select(String min_select) {
		this.min_select = min_select;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public void setPostconditions(List<PostconditionBean> postconditions) {
		this.postconditions = postconditions;
	}

	// public void setPreconditions(List<PreconditionBean> preconditions) {
	// this.preconditions = preconditions;
	// }

	public void setText(String text) {
		this.text = text;
	}

	public void setType(String type) {
		this.type = type;
	}
}
