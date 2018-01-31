package com.taylorsuniversity.models.bean;

/**
 * This bean class is used to set and retrieve the social links properties 
 * configured in the footer component of the home-page template.
 *
 */
public class FooterModelSocialBean {
	
	private String socialIcon;
	private String socialLink;
	private String socialText;
	
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
