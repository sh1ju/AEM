package com.taylorsuniversity.models.bean;

/**
 * @author prajput
 *
 */
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
	
	
	/**
	 * @return String
	 */
	public String getSocialIcon() {
		return socialIcon;
	}
	/**
	 * @return String
	 */
	public String getSocialLink() {
		return socialLink;
	}
	/**
	 * @return String
	 */
	public String getSocialText() {
		return socialText;
	}
}
