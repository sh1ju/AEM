/* Copyright Taylors University */

package com.taylorsuniversity.models;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.sling.api.SlingHttpServletRequest;

import com.google.gson.Gson;
import com.taylorsuniversity.models.bean.SchoolThemesModelBean;
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
    @Optional
    @Via("resource")
    private String[] schoolThemes;

    /*
     * Initializing variable to be used
     */
    private List<SchoolThemesModelBean> schoolThemesList;

    /**
     * @return Map<String, List<FooterModelLinksBean>>
     */
    public List<SchoolThemesModelBean> getSchoolThemesList() {
        logger.debug("School Themes are  : {}" + schoolThemes);
        if (null != schoolThemes && ArrayUtils.isNotEmpty(schoolThemes)) {
            this.schoolThemesList = new ArrayList<>();
            for (String themesJsonString : schoolThemes) {
                Gson gson = new Gson();
                SchoolThemesModelBean tmb = gson.fromJson(themesJsonString, SchoolThemesModelBean.class);
                logger.debug("School Themes Model bean is : {}" + tmb);
                schoolThemesList.add(tmb);
            }
        }
        logger.debug("School Themes Links Map being sent is: : {}", schoolThemesList);
        return schoolThemesList;
    }
}
