/* Copyright Taylors University */

package com.taylorsuniversity.models;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taylorsuniversity.constants.Constants;
import com.taylorsuniversity.utils.CoreUtils;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
/**
 * This class is used to retrieve the values to be used in sightly.
 */
@Model(adaptables = Resource.class)
public class SightlyUtilsModel {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /** The Resolver. */
    @Inject
    private ResourceResolver resolver;

    @Inject
    private Resource resource;
    
    public String getHomePagePath() {
      logger.debug("Resolver is : {}", resolver);
      PageManager pageManager = resolver.adaptTo(PageManager.class);
      logger.debug("PageManager is : {}", pageManager);
      Page currentPage = pageManager == null ? null : pageManager.getContainingPage(resource);
      logger.debug("CurrentPage is : {}", currentPage);
      Page homePage = CoreUtils.getParentPage(currentPage, Constants.HOMEPAGE_LEVEL);
      logger.debug("HomePage is : {}", homePage);
      return homePage != null ? homePage.toString() : "";
    }
}
