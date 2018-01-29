package com.taylorsuniversity.models.bean;

import javax.inject.Inject;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.api.resource.ResourceResolver;
import com.taylorsuniversity.utils.CoreUtils;

@Model(adaptables = Resource.class)
public class TileListingModelBean {
	
	@Inject
	ResourceResolver resolver;
	
	@Inject
	private String image;
	@Inject
	private String altText;
	@Inject
	private String title;
	@Inject
	private String titleLink;
	@Inject
	private String description;
	
	public String getImage() {
		return image;
	}
	public String getAlt() {
		return altText;
	}
	public String getTitle() {
		return title;
	}
	public String getTitleLink() {
		return CoreUtils.getQualifiedLink(resolver, titleLink);
	}
	public String getTitleLinkType() {
		return String.valueOf(CoreUtils.isInternalLink(titleLink));
	}
	public String getDescription() {
		return description;
	}
	
}
