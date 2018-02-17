/* Copyright Taylors University */

package com.taylorsuniversity.models;

import java.util.Iterator;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.taylorsuniversity.utils.CoreUtils;

/**
 * This class is used to retrieve the values to be used in sightly.
 */
@Model(adaptables = SlingHttpServletRequest.class)
public final class SightlyUtilsModel {

	/** The Constant LOG. */
	public static final Logger LOGGER = LoggerFactory.getLogger(SightlyUtilsModel.class);

	/** The Resolver. */
	@Inject
	private ResourceResolver resolver;

	@Self
	private SlingHttpServletRequest request;

	@Inject
	@Optional
	@Via("resource")
	private PageManager pageManager;

	@Inject
	@Optional
	private Page currentPage;

	@Inject
	@Optional
	private String linkTarget;

	private String link = StringUtils.EMPTY;
	private String linkTitle = StringUtils.EMPTY;

	/**
	 * Helper method for link checker
	 *
	 * @return String String
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

	/**
	 * Helper method for checking the type of link i.e. internal or external
	 *
	 * @return String String
	 */
	public String getTitleLinkType() {
		if (StringUtils.isBlank(linkTarget)) {
			LOGGER.debug("Link Type is : {}", linkTarget);
			return StringUtils.EMPTY;
		}
		return String.valueOf(CoreUtils.isInternalLink(linkTarget));
	}

	/**
	 * Helper method for hide in navigation for all the child pages of a given
	 * page
	 *
	 * @return String String
	 */
	public String getHideInNavForAllPages() {
		String hideInNavForAllPages = "true";
		if (StringUtils.isNotBlank(linkTarget)) {
			LOGGER.debug("getHideInNavForAllPages Link Target is : {}", linkTarget);
			LOGGER.debug("PageManager is  : {}", pageManager);
			if (pageManager == null) {
				LOGGER.error("PageManager is null in getNavigationSectionModelBean ...");
				return "";
			}
			Page currentPage1 = pageManager.getPage(linkTarget);
			LOGGER.debug("Current page in getHideInNavForAllPages is : {}", currentPage1.getPath());
			Iterator<Page> itr = currentPage1.listChildren();
			while (itr.hasNext()) {
				Page page = itr.next();
				LOGGER.debug("Current child of the page {} in getHideInNavForAllPages is : {}",
						currentPage1.getPath(),
						page);
				if (!page.isHideInNav()) {
					LOGGER.debug(
							"isHideInNav of the Current child {] of the page {} "
									+ "in getHideInNavForAllPages is : {}",
							page, currentPage1.getPath(), page.isHideInNav());
					hideInNavForAllPages = "false";
				}
			}

		} else {
			LOGGER.debug("getHideInNavForAllPages LinkTarget is invalid");
		}
		LOGGER.debug("hideInNavForAllPages is : {}", hideInNavForAllPages);
		return hideInNavForAllPages;
	}

	/**
	 * Helper method for getting the title of an internal page
	 *
	 * @return String String
	 */
	public String getLinkTitle() {
		if (null != pageManager) {
			if (CoreUtils.isInternalLink(linkTarget) && (null != pageManager.getPage(linkTarget))) {
				LOGGER.debug("Link Path received is: {} ", linkTarget);
				linkTitle = pageManager.getPage(linkTarget).getTitle();
			} else {
				LOGGER.debug("Link title not available for: {} ", linkTarget);
				linkTitle = linkTarget;
			}
		} else {
			LOGGER.debug("Page Manager is null.");
		}
		LOGGER.debug("Link Title for Link Path: {}  is {} : ", linkTarget, linkTitle);
		return linkTitle;
	}
}
