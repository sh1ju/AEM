/* Copyright Taylors University */

package com.taylorsuniversity.models;

import com.day.cq.wcm.api.Page;
import com.taylorsuniversity.models.bean.TileListingModelBean;
import com.taylorsuniversity.utils.CoreUtils;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.Via;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * This class is used to retrieve the properties configured in the Other Schools/Courses Tiles component
 *
 */
@Model(adaptables = SlingHttpServletRequest.class)
public final class OtherCourseModel {

    @Optional
    @Inject
    @Via("resource")
    private List<Resource> schoolPaths;

    @Inject
    private Page currentPage;

    private static final int MAX_TILES = 3;

    private List<TileListingModelBean> tileListingItems = null;
    private static final Logger LOGGER = LoggerFactory.getLogger(OtherCourseModel.class);

    /**
     * @return a list of java beans that define the properties required by the Other Courses/Schools list
    */
    public List<TileListingModelBean> getTileListingItems() {
        if (null != schoolPaths && !schoolPaths.isEmpty()) {
            tileListingItems = new ArrayList<>();

            for (Resource tile : schoolPaths) {
                LOGGER.debug("Tile items are :", tile);
                tileListingItems.add(tile.adaptTo(TileListingModelBean.class));
            }
        }
        LOGGER.debug("Tile Listing items are : {}", tileListingItems);
        if (tileListingItems.size() < MAX_TILES) {
            List<Page> siblings = this.getSiblings();
            for (Page sibling:siblings) {
                TileListingModelBean tile = new TileListingModelBean();
                tile.setTitle(sibling.getName());
                tile.setDescription(sibling.getDescription());
                tile.setTitleLink(sibling.getPath());
                tileListingItems.add(tile);

                if (tileListingItems.size() == MAX_TILES) {
                    break;
                }

            }

        }

        return tileListingItems;
    }

    /**
     * @return a list of paths to sibling pages
     */
    public List<Page> getSiblings() {
        return CoreUtils.getNextSiblings(currentPage, true, true);
    }

}
