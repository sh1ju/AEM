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
	
	
	/**
	 * @param socialIcon
	 */
	public void setSocialIcon(String socialIcon) {
		this.socialIcon = socialIcon;
	}


	/**
	 * @param socialLink
	 */
	public void setSocialLink(String socialLink) {
		this.socialLink = socialLink;
	}


	/**
	 * @param socialText
	 */
	public void setSocialText(String socialText) {
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
