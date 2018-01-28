package com.taylorsuniversity.models.bean;

public class FooterModelLinksBean {
	
	private String pageLinkItem;
	private String pageNameItem;

	
	public FooterModelLinksBean(String pageLink, String pageName) {
		super();
		this.pageLinkItem = pageLink;
		this.pageNameItem = pageName;
	}
	
	public String getPageLinkItem() {
		return pageLinkItem;
	}
	public String getPageNameItem() {
		return pageNameItem;
	}
	
}
