/* Copyright Taylors University */

package com.taylorsuniversity.models.bean;
import java.util.List;

/**
 *
 * This bean class is used to set and retrieve the social links and title properties
 * configured in the footer component of the home-page template.
 *
 */
public class FooterModelLinksBean {

    private String footerLinksTitle;
    private List<FooterModelLinkPathBean> footerLinksPages;

    public final String getFooterLinksTitle() {
        return footerLinksTitle;
    }
    public final List<FooterModelLinkPathBean> getFooterLinksPages() {
        return footerLinksPages;
    }
}

