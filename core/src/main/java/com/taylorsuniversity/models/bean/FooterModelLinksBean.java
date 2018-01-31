package com.taylorsuniversity.models.bean;

import java.util.List;

public class FooterModelLinksBean {
	
	private String footerLinksTitle;
	private List<FooterModelLinkPathBean> footerLinksPages;
	
	public String getFooterLinksTitle() {
		return footerLinksTitle;
	}
	public void setFooterLinksTitle(String footerLinksTitle) {
		this.footerLinksTitle = footerLinksTitle;
	}
	public List<FooterModelLinkPathBean> getFooterLinksPages() {
		return footerLinksPages;
	}
	public void setFooterLinksPages(List<FooterModelLinkPathBean> footerLinksPages) {
		this.footerLinksPages = footerLinksPages;
	}
}

