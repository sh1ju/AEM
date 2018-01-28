package com.taylorsuniversity.models.bean;

public class TileListingModelBean {
	
	private String imageItem;
	private String altTextItem;
	private String titleItem;
	private String titleLinkItem;
	private String titleLinkTypeItem;
	private String descriptionItem;
	
	public TileListingModelBean(String imageItem, String altTextItem, String titleItem, String titleLinkItem,
			String titleLinkTypeItem, String descriptionItem) {
		super();
		this.imageItem = imageItem;
		this.altTextItem = altTextItem;
		this.titleItem = titleItem;
		this.titleLinkItem = titleLinkItem;
		this.titleLinkTypeItem = titleLinkTypeItem;
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
	
	public String getTitleLinkTypeItem() {
		return titleLinkTypeItem;
	}
	public String getDescriptionItem() {
		return descriptionItem;
	}
}
