package com.taylorsuniversity.models;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.ResourceResolver;

import com.taylorsuniversity.utils.CoreUtils;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Model(adaptables = SlingHttpServletRequest.class)
public class LinkHelper {

	
	@Inject
	ResourceResolver resourceResolver;

	@Inject
	@Optional
	private String linkTarget;

	Logger LOGGER = LoggerFactory.getLogger(LinkHelper.class);
	private String link = "--";
	
	@PostConstruct
	protected void init() {

		if (StringUtils.isNotBlank(linkTarget)) {
			link = CoreUtils.getQualifiedLink(resourceResolver, linkTarget);
		} else {
			LOGGER.debug("Link is invalid");
		}
	}

	public String getLink() {
		return link;
	}
}
