/* Copyright Taylors University */

package com.taylorsuniversity.models;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.Via;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import com.taylorsuniversity.models.bean.TileListingModelBean;

/**
 * This class is used to retrieve the properties configured in the Tile Listing and Course Tiles
 * components
 *
 */
@Model(adaptables = SlingHttpServletRequest.class)
public final class TileListingModel {

    @Optional
    @Inject
    @Via("resource")
    private List<Resource> tileListing;

    private List<TileListingModelBean> tileListingItems = null;
    private static final Logger LOGGER = LoggerFactory.getLogger(TileListingModel.class);

    /**
     * @return a list of java beans that define the properties required by the CourseTile and TileListing components
    */
    public List<TileListingModelBean> getTileListingItems() {
        if (null != tileListing && !tileListing.isEmpty()) {
            tileListingItems = new ArrayList<>();

            for (Resource tile : tileListing) {
                LOGGER.debug("Tile items are :", tile);
                tileListingItems.add(tile.adaptTo(TileListingModelBean.class));
            }
        }
        LOGGER.debug("Tile Listing items are : {}", tileListingItems);
        return tileListingItems;
    }
}
