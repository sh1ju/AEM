/* Copyright Taylors University */

package com.taylorsuniversity.models.bean;

import java.util.List;

/**
 *
 * This bean class is used to set and retrieve the social links and title properties
 * configured in the footer component of the home-page template.
 *
 */
public final class CourseClassificationsBeans {

    private String levelOfStudy;
    private List<Programme> programmes;

    public String getLevelOfStudy() {
        return levelOfStudy;
    }
    public List<Programme> getProgrammes() {
        return programmes;
    }
}

