/* Copyright Taylors University */
package com.taylorsuniversity.models.bean;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

/**
 * This bean class is used to set and retrieve the title
 * for the accordion that entered by the user in Accordion component.
 */

@Model(adaptables = Resource.class)
public final class TextListModelBean {

    @Inject
    @Named("text_t")
    private String textt;

    @Inject
    @Named("title_t")
    private String titlet;

    public String getTextt() {
        return textt;
    }

    public String getTitlet() {
        return titlet;
    }
}
