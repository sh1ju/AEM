/* Copyright Taylors University */

package com.taylorsuniversity.models.bean;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.injectorspecific.Self;

import com.day.cq.wcm.api.PageManager;
import com.taylorsuniversity.utils.CoreUtils;

/**
 * This bean class is used to set and retrieve the footer links path properties
 * configured in the footer component of the home-page template.
 *
 */
public final class SchoolLinksBean {

    /** The Resolver. */
    @Self
    private SlingHttpServletRequest request;

    @Inject
    private PageManager pageManager;

    private String linkPath;
    private String linkTitle = StringUtils.EMPTY;

    /**
     * @return linkPath
     */
    public String getLinkPath() {
        if (StringUtils.isNotEmpty(linkPath)) {
            linkPath = CoreUtils.getQualifiedLink(request.getResourceResolver(), linkPath);
        }
        return linkPath;
    }

    /**
     * @return linkTitle
     */
    public String getLinkTitle() {
        if (CoreUtils.isInternalLink(linkPath) && (null != pageManager.getPage(linkPath))) {
            linkTitle = pageManager.getPage(linkPath).getTitle();
        }
        return linkTitle;
    }
}
