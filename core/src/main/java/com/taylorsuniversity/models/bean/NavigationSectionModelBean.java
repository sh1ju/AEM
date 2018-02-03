/* Copyright Taylors University */

package com.taylorsuniversity.models.bean;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;

/**
 * The Class NavigationSectionModelBean.
 */
@Model(adaptables = Resource.class)
public class NavigationSectionModelBean {

    @Inject
    @Optional
    private String sectionPath;

    @Inject
    @Optional
    private String showCourseSearch;

    /**
     * Gets the sectionPath.
     *
     * @return the sectionPath
     */
    public String getSectionPath() {
        return sectionPath;
    }

    /**
     * Gets the showCourseSearch.
     *
     * @return the showCourseSearch
     */
    public String getShowCourseSearch() {
        return showCourseSearch;
    }
}
