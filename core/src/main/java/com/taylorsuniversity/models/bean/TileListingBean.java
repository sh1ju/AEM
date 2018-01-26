package com.taylorsuniversity.models.bean;

public class TileListingBean {
	
	private final String imageItem;
	private final String altTextItem;
	private final String titleItem;
	private final String titleLinkItem;
	private final String descriptionItem;
	
	public TileListingBean(String imageItem, String altTextItem, String titleItem, String titleLinkItem,
			String descriptionItem) {
		super();
		this.imageItem = imageItem;
		this.altTextItem = altTextItem;
		this.titleItem = titleItem;
		this.titleLinkItem = titleLinkItem;
		this.descriptionItem = descriptionItem;
	}
	
	public String getImageItem() {
		return imageItem;
	}
	public String getAltTextItem() {
		return altTextItem;
	}
	public String getTitleItem() {
		return titleItem;
	}
	public String getTitleLinkItem() {
		return titleLinkItem;
	}
	public String getDescriptionItem() {
		return descriptionItem;
	}
}
