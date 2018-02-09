/* Copyright Taylors University */

package com.taylorsuniversity.models.bean;
import java.util.List;

/**
 *
 * This bean class is used to set and retrieve the social links and title properties
 * configured in the footer component of the home-page template.
 *
 */
public final class SchoolThemesModelBean {

    private String image;
    private String imageAlignment;
    private String themeTitle;
    private String themeText;
    private List<SchoolLinksBean> schoolLinks;

    public String getImage() {
        return image;
    }
    public String getImageAlignment() {
        return imageAlignment;
    }
    public String getThemeTitle() {
        return themeTitle;
    }
    public String getThemeText() {
        return themeText;
    }
    public List<SchoolLinksBean> getSchoolLinks() {
        return schoolLinks;
    }
	public void setSchoolLinks(List<SchoolLinksBean> schoolLinks) {
		this.schoolLinks = schoolLinks;
	}
}
