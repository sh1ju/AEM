package com.taylorsuniversity.models.bean;

/**
 * @author prajput
 *
 */
public class FooterModelLinksBean {
	
	private String pageLinkItem;
	private String pageNameItem;

	public FooterModelLinksBean(String pageLink, String pageName) {
		super();
		this.pageLinkItem = pageLink;
		this.pageNameItem = pageName;
	}
	
	/**
	 * @return String
	 */
	public String getPageLinkItem() {
		return pageLinkItem;
	}
	/**
	 * @return String
	 */
	public String getPageNameItem() {
		return pageNameItem;
	}
}
