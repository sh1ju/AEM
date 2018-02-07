package com.taylorsuniversity.core.beans;

public class TouchMultiCompositeFieldBean {
	private String title;
	private String text;
	private String mobileCSS;

	
	public String getText() {
		return text;
	}	

	public void setText(String text) {
		this.text = text;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getMobileCSS() {
		return mobileCSS;
	}

	public void setMobileCSS(String mobileCSS) {
		this.mobileCSS = mobileCSS;
	}
}
