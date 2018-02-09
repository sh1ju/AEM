/* Copyright Taylors University */

package com.taylorsuniversity.models;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;

import com.day.cq.wcm.api.PageManager;
import com.google.gson.Gson;
import com.taylorsuniversity.models.bean.SchoolLinksBean;
import com.taylorsuniversity.models.bean.SchoolThemesModelBean;
import com.taylorsuniversity.utils.CoreUtils;

import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is used to retrieve the properties configured
 * in the school themes component.
 */
@Model(adaptables = SlingHttpServletRequest.class)
public final class SchoolThemesModel {
    /**
     * The Logger
     */
    private Logger logger = LoggerFactory.getLogger(SchoolThemesModel.class);

    /** The Resolver. */
    @Self
    private SlingHttpServletRequest request;

    @Inject
    private PageManager pageManager;

    @Inject
    @Optional
    @Via("resource")
    private String[] schoolThemes;

    /*
     * Initializing variable to be used
     */
    private List<SchoolThemesModelBean> schoolThemesList = null;
    private String link = StringUtils.EMPTY;
    private String linkName = StringUtils.EMPTY;

    /**
     * The init method
     */
    @PostConstruct
    protected void init() {
        logger.info("School Themes are  : {}" + schoolThemes);
        schoolThemesProps();
    }

    /**
     * Reads the authored footer links properties and adds to bean
     *
     */
    private void schoolThemesProps() {
        if (null != schoolThemes && ArrayUtils.isNotEmpty(schoolThemes)) {
            this.schoolThemesList = new ArrayList<>();
            for (String themesJsonString : schoolThemes) {
                Gson gson = new Gson();
                SchoolThemesModelBean tmb = gson.fromJson(themesJsonString, SchoolThemesModelBean.class);
                logger.info("School Themes Model bean is : {}" + tmb);
                linkProps(tmb);
                schoolThemesList.add(tmb);
            }
        }
    }

    /**
     * Reads the links properties
     * @param tmb
     */
    private void linkProps(final SchoolThemesModelBean tmb) {

        List<SchoolLinksBean> pages = tmb.getSchoolLinks();
        if (null != pages && ArrayUtils.isNotEmpty(pages.toArray())) {
            for (SchoolLinksBean li : pages) {
                if (StringUtils.isNotBlank(li.getLinkPath())) {
                    link = li.getLinkPath();
                    logger.debug("Link is : {}" + link);
                    if (CoreUtils.isInternalLink(link) && (null != pageManager.getPage(link))) {
                        linkName = pageManager.getPage(link).getTitle();
                    } else {
                        linkName = link;
                    }
                    logger.debug("Link name is : {}" + linkName);
                }
                li.setLinkTitle(linkName);
            }
        }
        tmb.setSchoolLinks(pages);
    }
    /**
     * @return Map<String, List<FooterModelLinksBean>>
     */
    public List<SchoolThemesModelBean> getSchoolThemesList() {
        logger.info("School Themes Links Map being sent is: : {}", schoolThemesList);
        return schoolThemesList;
    }
}
