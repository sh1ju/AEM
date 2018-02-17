/* Copyright Taylors University */

package com.taylorsuniversity.models;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;

import com.taylorsuniversity.models.bean.FeatureCarouselModelBean;

/**
 * This class is used to retrieve the properties configured in the Tile Listing
 * component
 *
 */
@Model(adaptables = SlingHttpServletRequest.class)
public final class FeatureCarouselModel {

    /** The Resolver. */
    @Self
    private SlingHttpServletRequest request;

    @Optional
    @Inject
    @Via("resource")
    private List<Resource> carouselItems;

    private List<FeatureCarouselModelBean> featureCarouselItems = null;
    private Logger logger = LoggerFactory.getLogger(FeatureCarouselModel.class);

    /**
     * @return List
    */
    public List<FeatureCarouselModelBean> getFeatureCarouselItems() {
        if (null != carouselItems && !carouselItems.isEmpty()) {
            featureCarouselItems = new ArrayList<>();

            for (Resource feature : carouselItems) {
                logger.debug("Tile items are :", feature);
                featureCarouselItems.add(feature.adaptTo(FeatureCarouselModelBean.class));
            }
        }
        logger.debug("Feature Carousel items are : {}", featureCarouselItems);
        return featureCarouselItems;
    }
}
