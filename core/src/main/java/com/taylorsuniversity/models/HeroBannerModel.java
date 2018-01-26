package com.taylorsuniversity.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.dam.api.Asset;
import com.day.cq.dam.commons.util.DamUtil;

@Model(adaptables = Resource.class)
public class HeroBannerModel {

	@Inject
	ResourceResolver resourceResolver;

	@Inject
	@Optional
	private String fileReference;

	Logger LOGGER = LoggerFactory.getLogger(HeroBannerModel.class);
	private String damType = "--";

	@PostConstruct
	protected void init() {

		if (StringUtils.isNotBlank(fileReference)) {
			Resource resource = resourceResolver.getResource(fileReference);
			
			if(StringUtils.isNotBlank(resource.toString())){
				Asset asset = resource.adaptTo(Asset.class);
				
				if(StringUtils.isNotBlank(asset.toString())) {
					
					if (DamUtil.isImage(asset)) {
						damType = "image";
						LOGGER.debug("Found image at: {}", asset.getPath());
					}
					if (DamUtil.isVideo(asset)) {
						damType = "video";
						LOGGER.debug("Found video at: {}", asset.getPath());
					}
				}
			}
		} else {
			LOGGER.debug("Found desktop asset variable as null, empty or invalid");
		}
	}

	public String getDamType() {
		LOGGER.debug("Dam type is : {}", damType);
		return damType;
	}

}
