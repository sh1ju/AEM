package com.taylorsuniversity.models.bean;

/**
 * This bean class is used to set and retrieve the link name and link path 
 * for the url entered by the user in Footer component of the home-page template.
 *
 */
public class FooterModelBean {
	
	private String pageLinkItem;
	private String pageNameItem;

	public FooterModelBean(String pageLinkItem, String pageNameItem) {
		super();
		this.pageLinkItem = pageLinkItem;
		this.pageNameItem = pageNameItem;
	}
	
	public String getPageLinkItem() {
		return pageLinkItem;
	}
	public String getPageNameItem() {
		return pageNameItem;
	}
}
