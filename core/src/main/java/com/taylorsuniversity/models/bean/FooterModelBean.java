/* Copyright Taylors University */

package com.taylorsuniversity.models.bean;

/**
 * This bean class is used to set and retrieve the link name and link path
 * for the url entered by the user in Footer component of the home-page template.
 *
 */
public final class FooterModelBean {

    private String pageLinkItem;
    private String pageNameItem;

    /**
     * @param pageLink PageLink
     * @param pageName PageName
     */
    public FooterModelBean(final String pageLink, final String pageName) {
        super();
        this.pageLinkItem = pageLink;
        this.pageNameItem = pageName;
    }

    public String getPageLinkItem() {
        return pageLinkItem;
    }
    public String getPageNameItem() {
        return pageNameItem;
    }
}
