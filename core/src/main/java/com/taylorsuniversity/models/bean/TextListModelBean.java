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
public class TextListModelBean {

    @Inject
    @Named("text_t")
    private String text_t;

    @Inject
    @Named("title_t")
    private String title_t;

    public String getText_t() {
        return text_t;
    }

    public String getTitle_t() {
        return title_t;
    }
}
