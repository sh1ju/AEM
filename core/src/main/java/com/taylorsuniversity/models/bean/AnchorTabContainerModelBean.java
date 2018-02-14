/* Copyright Taylors University */
package com.taylorsuniversity.models.bean;


/**
 * This bean class is used to set and retrieve the title
 * for the accordion that entered by the user in Accordion component.
 */
public final class AnchorTabContainerModelBean {
	 /**
    *
    */
   private String text;
   private String link;


    /**
     *
     * @param textP Text
     * @param linkP Link
     */
    public AnchorTabContainerModelBean(final String textP, final String linkP) {
        super();
        this.text = textP;
        this.link = linkP;
    }


    /**
     *
     * @return will return the value
     */
    public String getText() {
        return text;
    }

    /**
    *
    * @return will return the value
    */
   public String getLink() {
       return link;
   }
}
