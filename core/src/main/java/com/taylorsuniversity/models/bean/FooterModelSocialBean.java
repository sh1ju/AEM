/* Copyright Taylors University */

package com.taylorsuniversity.models.bean;

/**
 * This bean class is used to set and retrieve the social links properties
 * configured in the footer component of the home-page template.
 *
 */
public class FooterModelSocialBean {

    private String socialIcon;
    private String socialLink;
    private String socialText;

    public final String getSocialIcon() {
        return socialIcon;
    }
    public final String getSocialLink() {
        return socialLink;
    }
    public final String getSocialText() {
        return socialText;
    }
}
