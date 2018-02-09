/* Copyright Taylors University */

package com.taylorsuniversity.models.bean;

/**
 * This bean class is used to set and retrieve the footer links path properties
 * configured in the footer component of the home-page template.
 *
 */

public final class SchoolLinksBean {
   
    private String linkPath;
    private String linkTitle;

	public String getLinkPath() {
		return linkPath;
	}
	public String getLinkTitle() {
		return linkTitle;
	}
	public void setLinkTitle(String linkTitle) {
		this.linkTitle = linkTitle;
	}

}
