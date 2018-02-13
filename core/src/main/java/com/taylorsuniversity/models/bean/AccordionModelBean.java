/* Copyright Taylors University */
package com.taylorsuniversity.models.bean;


/**
 * This bean class is used to set and retrieve the title
 * for the accordion that entered by the user in Accordion component.
 */
public final class AccordionModelBean {

    /**
     *
     * @param accordiontitlestInput value entered
     */
    public AccordionModelBean(final String accordiontitlestInput) {
        super();
        this.accordiontitlest = accordiontitlestInput;
    }

    /**
     *
     */
    private String accordiontitlest;

    /**
     *
     * @return will return the value
     */
    public String getAccordiontitlest() {
        return accordiontitlest;
    }
}
