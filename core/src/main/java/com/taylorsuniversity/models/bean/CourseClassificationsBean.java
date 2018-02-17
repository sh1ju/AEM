/* Copyright Taylors University */

package com.taylorsuniversity.models.bean;

import java.util.List;

/**
 * This bean class is used to set and retrieve the course
 * classifications properties configured in the home page.
 *
 */

public final class CourseClassificationsBean {

    private String levelOfStudy;

    private List<ProgrammesBean> programmes;

    public String getLevelOfStudy() {
        return levelOfStudy;
    }

    public List<ProgrammesBean> getProgrammes() {
        return programmes;
    }
}
