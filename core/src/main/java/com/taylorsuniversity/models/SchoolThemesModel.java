/* Copyright Taylors University */

package com.taylorsuniversity.models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;

import com.google.gson.Gson;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.taylorsuniversity.models.bean.FooterModelSocialBean;
import com.taylorsuniversity.constants.Constants;
import com.taylorsuniversity.models.bean.FooterModelBean;
import com.taylorsuniversity.models.bean.FooterModelLinkPathBean;
import com.taylorsuniversity.models.bean.FooterModelLinksBean;
import com.taylorsuniversity.utils.CoreUtils;

/**
 * This class is used to retrieve the properties configured in the footer
 * component of the home-page template.
 */
@Model(adaptables = SlingHttpServletRequest.class)
public final class SchoolThemesModel {
    private Logger logger = LoggerFactory.getLogger(SchoolThemesModel.class);

    /** The Resolver. */
    @Self
    private SlingHttpServletRequest request;

    @Inject
    private PageManager pageManager;

    @Inject
    private Page currentPage;

    /*
     * Initializing variable to be used
     */
    private String[] footerLinks;
    private List<FooterModelSocialBean> socialItems = null;
    private List<FooterModelBean> footerLinksPages = null;
    private LinkedHashMap<String, List<FooterModelBean>> footerLinksMap = null;
    private String linkUrlPath = StringUtils.EMPTY;
    private String linkName = StringUtils.EMPTY;
    private String link = StringUtils.EMPTY;
    private Page rootPage;

    /**
     * The init method
     */
    @PostConstruct
    protected void init() {
        rootPage = CoreUtils.getParentPage(currentPage, Constants.HOMEPAGE_LEVEL);
        logger.debug("RootPage value is: {} " + rootPage);

        if (null != rootPage) {
            footerLinkProps();
        }
    }

    /**
     * Reads the authored footer links properties and adds to bean
     *
     */
    private void footerLinkProps() {

        if (null != rootPage.getContentResource("footer")) {

            footerLinks = rootPage.getContentResource("footer").getValueMap().get("footerLinks", String[].class);
            logger.debug("FooterLinks value is: {} " + footerLinks);

            if (null != footerLinks && ArrayUtils.isNotEmpty(footerLinks)) {
                this.footerLinksMap = new LinkedHashMap<>();
                for (String footerLinksJsonString : footerLinks) {
                    Gson gson = new Gson();
                    FooterModelLinksBean fmb = gson.fromJson(footerLinksJsonString, FooterModelLinksBean.class);
                    logger.debug("Footer model bean is : {}" + fmb);
                    linkProps(fmb);
                }
            }
        }
    }

    /**
     * Reads the links properties
     * @param fmb
     */
    private void linkProps(final FooterModelLinksBean fmb) {
        if (StringUtils.isNotBlank(fmb.getFooterLinksTitle())) {

            logger.debug("Footer model bean title is : {}" + fmb.getFooterLinksTitle());
            List<FooterModelLinkPathBean> pages = fmb.getFooterLinksPages();
            footerLinksPages = new ArrayList<>();

            if (null != pages && ArrayUtils.isNotEmpty(pages.toArray())) {

                for (FooterModelLinkPathBean li : pages) {

                    if (StringUtils.isNotBlank(li.getLinkPath())) {

                        link = li.getLinkPath();
                        logger.debug("Link is : {}" + link);

                        if (CoreUtils.isInternalLink(link) && (null != pageManager.getPage(link))) {
                            linkName = pageManager.getPage(link).getTitle();
                        } else {
                            linkName = link;
                        }
                        logger.debug("Link name is : {}" + linkName);

                        linkUrlPath = CoreUtils.getQualifiedLink(request.getResourceResolver(), link);
                        logger.debug("Link Url Path is : {}" + linkUrlPath);
                    }
                    footerLinksPages.add(new FooterModelBean(linkUrlPath, linkName));
                    logger.debug("Link Url Path is : {}" + linkUrlPath);
                }
            }
            footerLinksMap.put(fmb.getFooterLinksTitle(), footerLinksPages);
        }
    }

    /**
     * @return Map<String, List<FooterModelLinksBean>>
     */
    public Map<String, List<FooterModelBean>> getFooterLinksMap() {
        logger.debug("Footer Links Map being sent is: : {}", footerLinksMap);
        return footerLinksMap;
    }
}
