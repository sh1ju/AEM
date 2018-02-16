/* Copyright Taylors University */

package com.taylorsuniversity.models.bean;

import javax.inject.Inject;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;

/**
 * @author This bean class has getters and setters for tile listing component
 *
 */
@Model(adaptables = Resource.class)
public final class FeatureCarouselModelBean {

    /**
     * The Resolver
     */
    @Inject
    @Optional
    private String desktopImage;

    @Inject
    @Optional
    private String mobileImage;

    @Inject
    @Optional
    private String title;

    @Inject
    @Optional
    private String text;

    @Inject
    @Optional
    private String ctaText;

    @Inject
    @Optional
    private String ctaLink;

    public String getDesktopImage() {
        return desktopImage;
    }
    public String getMobileImage() {
        return mobileImage;
    }
    public String getTitle() {
        return title;
    }
    public String getText() {
        return text;
    }
    public String getCtaText() {
        return ctaText;
    }
    public String getCtaLink() {
        return ctaLink;
    }

}
