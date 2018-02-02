/* Copyright Taylors University */

package com.taylorsuniversity.models.bean;

import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.api.resource.ResourceResolver;
import com.taylorsuniversity.utils.CoreUtils;

/**
 * @author This bean class has getters and setters for tile listing component
 *
 */
@Model(adaptables = Resource.class)
public final class TileListingModelBean {

    /**
     * The Resolver
     */
    @Inject
    private ResourceResolver resolver;

    @Inject
    @Optional
    private String image;

    @Inject
    @Optional
    private String alt;

    @Inject
    @Optional
    private String title;

    @Inject
    @Optional
    private String titleLink;

    @Inject
    @Optional
    private String description;

    public String getImage() {
        return image;
    }
    public String getAlt() {
        return alt;
    }
    public String getTitle() {
        return title;
    }
    /**
     * @return titleLink
     */
    public String getTitleLink() {
        if (null != titleLink && !titleLink.isEmpty()) {
            return CoreUtils.getQualifiedLink(resolver, titleLink);
        }
        return StringUtils.EMPTY;

    }
    public String getTitleLinkType() {
        return String.valueOf(CoreUtils.isInternalLink(titleLink));
    }
    public String getDescription() {
        return description;
    }
}
