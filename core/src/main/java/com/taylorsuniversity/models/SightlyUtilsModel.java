/* Copyright Taylors University */

package com.taylorsuniversity.models;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.taylorsuniversity.utils.CoreUtils;
import java.util.Iterator;
import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    @Inject
    @Optional
    private String linkTarget;

    @Inject
    @Optional
    private String linkPath;

    private PageManager pageManager;
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
			pageManager = resolver.adaptTo(PageManager.class);
			LOGGER.debug("PageManager is  : {}", pageManager);
			if (pageManager == null) {
				LOGGER.error("PageManager is null in getNavigationSectionModelBean ...");
				return "";
			}
			Page currentPage = pageManager.getPage(linkTarget);
			LOGGER.debug("Current page in getHideInNavForAllPages is : {}", currentPage.getPath());
			Iterator<Page> itr = currentPage.listChildren();
			while (itr.hasNext()) {
				Page page = itr.next();
				LOGGER.debug("Current child of the page {} in getHideInNavForAllPages is : {}",
						currentPage.getPath(), page);
				if (!page.isHideInNav()) {
					LOGGER.debug(
							"isHideInNav of the Current child {] of the page {} "
									+ "in getHideInNavForAllPages is : {}",
							page, currentPage.getPath(), page.isHideInNav());
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
        pageManager = resolver.adaptTo(PageManager.class);
        if (CoreUtils.isInternalLink(linkPath) && (null != pageManager.getPage(linkPath))) {
            LOGGER.debug("Link Path received is: {}" + linkPath);
            linkTitle = pageManager.getPage(linkPath).getTitle();
        } else {
            linkTitle = linkPath;
        }
        LOGGER.debug("Link Title for Link Path: " + linkPath + " is : "  + linkPath);
        return linkTitle;
    }
}
