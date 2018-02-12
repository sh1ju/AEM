/* Copyright Taylors University */

package com.taylorsuniversity.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.Via;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.dam.api.Asset;
import com.day.cq.dam.commons.util.DamUtil;

/**
 * This Model class helps in retrieving the attributes for Hero-Banner component.
 *
 */
@Model(adaptables = SlingHttpServletRequest.class)
public final class HeroBannerModel {

    /**
     * The Resolver
     */
    @Inject
    private ResourceResolver resourceResolver;

    @Inject
    @Optional
    @Via("resource")
    private String desktopReference;

    private Logger logger = LoggerFactory.getLogger(HeroBannerModel.class);
    private String damType = "--";

    /**
     * The init method
     */
    @PostConstruct
    protected void init() {

        if (StringUtils.isNotBlank(desktopReference)) {
            Resource resource = resourceResolver.getResource(desktopReference);

            if (StringUtils.isNotBlank(resource.toString())) {
                Asset asset = resource.adaptTo(Asset.class);

                if (StringUtils.isNotBlank(asset.toString())) {

                    if (DamUtil.isImage(asset)) {
                        damType = "image";
                        logger.debug("Found image at: {}", asset.getPath());
                    }
                    if (DamUtil.isVideo(asset)) {
                        damType = "video";
                        logger.debug("Found video at: {}", asset.getPath());
                    }
                }
            }
        } else {
            logger.debug("Found desktop asset variable as null, empty or invalid");
        }
    }

    /**
     * @return damType
     */
    public String getDamType() {
        logger.debug("Dam type is : {}", damType);
        return damType;
    }

}
