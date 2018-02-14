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

import com.taylorsuniversity.models.bean.FacilitiesListingModelBean;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;

/**
 * This class is used to retrieve the properties configured in the Facilities
 * Listing component
 *
 */
@Model(adaptables = SlingHttpServletRequest.class)
public final class FacilitiesListingModel {

	@Optional
	@Inject
	@Via("resource")
	private List<Resource> facilities;

	private List<FacilitiesListingModelBean> facilitiesListingItems = null;
	private Logger logger = LoggerFactory.getLogger(FacilitiesListingModelBean.class);

	/**
	 * @return List
	 */
	public List<FacilitiesListingModelBean> getFacilitiesListingItems() {
		if (null != facilities && !facilities.isEmpty()) {
			facilitiesListingItems = new ArrayList<>();

			for (Resource facility : facilities) {
				logger.debug("Facility items are :", facility);
				facilitiesListingItems.add(facility.adaptTo(FacilitiesListingModelBean.class));
			}
		}
		logger.debug("Tile Listing items are : {}", facilitiesListingItems);
		return facilitiesListingItems;
	}
}