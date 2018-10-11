package com.eastedge.mobilesurvey.bean;

import java.io.Serializable;

public class PreconditionBean implements Serializable {
	private String then;
	private String ruguo;
	private String fouze;

	public String getFouze() {
		return fouze;
	}

	public String getRuguo() {
		return ruguo;
	}

	public String getThen() {
		return then;
	}

	public void setFouze(String fouze) {
		this.fouze = fouze;
	}

	public void setRuguo(String ruguo) {
		this.ruguo = ruguo;
	}

	public void setThen(String then) {
		this.then = then;
	}
}
