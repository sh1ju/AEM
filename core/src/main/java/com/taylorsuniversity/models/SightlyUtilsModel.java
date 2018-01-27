/* Copyright Taylors University */

package com.taylorsuniversity.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taylorsuniversity.constants.Constants;
import com.taylorsuniversity.utils.CoreUtils;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

/**
 * This class is used to retrieve the values to be used in sightly.
 */
@Model(adaptables = SlingHttpServletRequest.class)
public class SightlyUtilsModel {

	private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	/** The Resolver. */
	@Inject
	private ResourceResolver resolver;

	@Inject
	private Resource resource;

	@Inject
	@Optional
	private String linkTarget;

	private String link = "--";

	/*
	 * Helper method for link checker
	 */
	public String getLink() {
		
		if (StringUtils.isNotBlank(linkTarget)) {
			LOGGER.debug("Link Target is : {}", linkTarget);
			link = CoreUtils.getQualifiedLink(resolver, linkTarget);
		} else {
			LOGGER.debug("Link entered is invalid");
		}
		LOGGER.debug("Link being sent is : {}", link);
		return link;
	}
}
