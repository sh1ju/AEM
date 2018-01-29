package com.taylorsuniversity.models.bean;

import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.api.resource.ResourceResolver;
import com.taylorsuniversity.utils.CoreUtils;

@Model(adaptables = Resource.class)
public class TileListingModelBean {
	
	@Inject
	ResourceResolver resolver;
	
	@Inject
	@Optional
	private String image;
	@Inject
	@Optional
	private String alt;
	@Inject
	@Optional
	private String title;
	@Inject
	@Optional
	private String titleLink;
	@Inject
	@Optional
	private String description;
	
	public String getImage() {
		return image;
	}
	public String getAlt() {
		return alt;
	}
	public String getTitle() {
		return title;
	}
	public String getTitleLink() {
		if (null != titleLink && !titleLink.isEmpty()) {
			return CoreUtils.getQualifiedLink(resolver, titleLink);
		}else {
			return StringUtils.EMPTY;
		}
	}
	public String getTitleLinkType() {
		return String.valueOf(CoreUtils.isInternalLink(titleLink));
	}
	public String getDescription() {
		return description;
	}
}
