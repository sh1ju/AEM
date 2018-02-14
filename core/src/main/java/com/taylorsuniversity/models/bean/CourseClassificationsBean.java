/* Copyright Taylors University */

package com.taylorsuniversity.models.bean;

/**
 * This bean class is used to set and retrieve the course
 * classifications properties configured in the home page.
 *
 */

public final class CourseClassificationsBean {

    private String levelOfStudy;

    private ProgrammesBean[] programmes;

    public String getLevelOfStudy() {
        return levelOfStudy;
    }

    public ProgrammesBean[] getProgrammes() {
        return programmes;
    }
}
