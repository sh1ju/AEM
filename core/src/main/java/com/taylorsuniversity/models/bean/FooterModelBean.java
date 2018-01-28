package com.taylorsuniversity.models.bean;

public class FooterModelBean {
	
	private String socialIcon;
	private String socialLink;
	private String socialText;
	
	public FooterModelBean(String socialIcon, String socialLink, String socialText) {
		super();
		this.socialIcon = socialIcon;
		this.socialLink = socialLink;
		this.socialText = socialText;
	}
	
	public String getSocialIcon() {
		return socialIcon;
	}
	public String getSocialLink() {
		return socialLink;
	}
	public String getSocialText() {
		return socialText;
	}
}
