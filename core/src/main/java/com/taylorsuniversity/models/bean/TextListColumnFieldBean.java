package com.taylorsuniversity.models.bean;

public class TextListColumnFieldBean {
	private String title;
	private String text;
	private String mobileCSS;
	
	public TextListColumnFieldBean(final String title, final String text, final String mobileCSS) {
		super();
	    this.title = title;
	    this.text = text;
	    this.mobileCSS = mobileCSS;
	}
	
	public String getText() {
		return text;
	}

	public String getTitle() {
		return title;
	}
	
	public String getMobileCSS() {
		return mobileCSS;
	}
}
