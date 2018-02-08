/* Copyright Taylors University */

package com.taylorsuniversity.models.bean;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;

/**
 * The Class HeaderComponentModelBean.
 */
@Model(adaptables = Resource.class)
public class HeaderComponentModelBean {

    @Inject
    @Optional
    private String switchText;

    @Inject
    @Optional
    private String switchPath;

    @Inject
    @Optional
    private String logoTitle;

    @Inject
    @Optional
    private String loginPagePath;

    @Inject
    @Optional
    private String fileReference;

    @Inject
    @Optional
    private String searchResultsPagePath;

    /**
     * Gets the switchText.
     *
     * @return the switchText
     */
    public String getSwitchText() {
        return switchText;
    }

    /**
     * Gets the switchPath.
     *
     * @return the switchPath
     */
    public String getSwitchPath() {
        return switchPath;
    }

    /**
     * Gets the logoTitle.
     *
     * @return the logoTitle
     */
    public String getLogoTitle() {
        return logoTitle;
    }

    /**
     * Gets the loginPagePath.
     *
     * @return the loginPagePath
     */
    public String getLoginPagePath() {
        return loginPagePath;
    }

    /**
     * Gets the fileReference.
     *
     * @return the fileReference
     */
    public String getFileReference() {
        return fileReference;
    }

    /**
     * Gets the searchResultsPagePath.
     *
     * @return the searchResultsPagePath
     */
    public String getSearchResultsPagePath() {
        return searchResultsPagePath;
    }
}
