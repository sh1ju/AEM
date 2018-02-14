/* Copyright Taylors University */
package com.taylorsuniversity.models;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.Via;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taylorsuniversity.models.bean.CourseTilesModelBean;

/**
 *This class is used to retrieve the properties configured in
 * the Course Tiles component
 *
 */
@Model(adaptables = SlingHttpServletRequest.class)
public final class CourseTilesModel {

    @Optional
    @Inject
    @Via("resource")
    private List<Resource> coursePaths;

    private List<CourseTilesModelBean> coursePathsItems;
    private Logger logger = LoggerFactory.getLogger(CourseTilesModel.class);

    /**
     * @return List
    */
    public List<CourseTilesModelBean> getCoursePathsItems() {
        if (null != coursePaths && !coursePaths.isEmpty()) {
            coursePathsItems = new ArrayList<>();

            for (Resource tile : coursePaths) {
                logger.debug("Tile items are :", tile);
                coursePathsItems.add(tile.adaptTo(CourseTilesModelBean.class));
            }
        }
        logger.debug("Course Tile items are : {}", coursePathsItems);
        return coursePathsItems;
    }
}
