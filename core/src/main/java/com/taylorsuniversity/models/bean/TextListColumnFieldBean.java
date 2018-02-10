/* Copyright Taylors University */
package com.taylorsuniversity.models.bean;

/**
 * This bean class is used to set and retrieve the title, text and set mobileCSS
 * for the TextList entered by the user in TextList component.
 *
 */
public final class TextListColumnFieldBean {

    private String title;

    private String text;

    private String mobileCSS;

    /**
     * @param titleP Title
     * @param textP Text
     * @param mobileCSSP MobileCSS
     */
    public TextListColumnFieldBean(final String titleP, final String textP, final String mobileCSSP) {
        super();
        this.title = titleP;
        this.text = textP;
        this.mobileCSS = mobileCSSP;
    }

    /**
     * Gets the text.
     *
     * @return the text
     */
    public String getText() {
        return text;
    }


    /**
     * Gets the title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the mobileCSS.
     *
     * @return the mobileCSS
     */
    public String getMobileCSS() {
        return mobileCSS;
    }
}
