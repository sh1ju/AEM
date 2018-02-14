/* Copyright Taylors University */
package com.taylorsuniversity.models.bean;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;

/**
 * This bean class has getters for Course Tiles component
 */
@Model(adaptables = Resource.class)
public final class CourseTilesModelBean {

    @Inject
    @Optional
    private String linkPath;

    public String getLinkPath() {
        return linkPath;
    }
}
