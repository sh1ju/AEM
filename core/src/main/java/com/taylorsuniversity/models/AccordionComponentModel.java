/* Copyright Taylors University */

package com.taylorsuniversity.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taylorsuniversity.models.bean.AccordionModelBean;

/**
 * This Model class helps in retrieving the attributes for accordion component.
 *
 */
@Model(adaptables = Resource.class)
public class AccordionComponentModel {

	/** The Constant LOG. */
	public static final Logger LOGGER = LoggerFactory.getLogger(AccordionComponentModel.class);

	 /**
     * The Resolver
     */
	@Inject @Optional
    private Resource listItems;

	// variable to hold the submenuItems list items
	private List<AccordionModelBean> submenuItems = new ArrayList<>();

	public final List<AccordionModelBean> getSubmenuItems() {
		LOGGER.error("In getSubmenuItems()");
		try {
			if (listItems != null) {
				Iterator<Resource> listChildren = listItems.listChildren();
				while (listChildren.hasNext()) {
					LOGGER.debug("In loop listItems()");
					AccordionModelBean modelBean = listChildren.next().adaptTo(AccordionModelBean.class);
						if (modelBean != null) {
							submenuItems.add(modelBean);
						} else {
							LOGGER.debug("Else");
						}
					}
			} else {
				LOGGER.error("Else");
			}
			LOGGER.error("submenuItems" + submenuItems);
		} catch (Exception e) {
			LOGGER.error("Exception while setMultiFieldItems data {}", e.getMessage(), e);
		}
		return submenuItems;
	}
}