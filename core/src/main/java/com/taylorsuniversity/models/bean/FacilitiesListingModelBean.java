/* Copyright Taylors University */

package com.taylorsuniversity.models.bean;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;

/**
 * @author This bean class has getters and setters for facilities listing
 *         component
 *
 */
@Model(adaptables = Resource.class)
public final class FacilitiesListingModelBean {

	@Inject
	@Optional
	private String image;

	@Inject
	@Optional
	private String imagealignment;

	@Inject
	@Optional
	@Named("title_t")
	private String title;

	@Inject
	@Optional
	private String description;

	@Inject
	@Optional
	private int column;

	private String newColumn;

    /**
     * This processed the value of columns
    */
	@PostConstruct
	protected void initialize() {
		newColumn = setColumnValue(String.valueOf(column));
	}

	public String getImage() {
		return image;
	}

	public String getImagealignment() {
		return imagealignment;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public int getColumn() {
		return column;
	}

	public String getNewColumn() {
		return newColumn;
	}

	private String setColumnValue(final String columnValue) {
		String newColumnValue = null;
		if (columnValue.matches("2")) {
			newColumnValue = "6";
		} else if (columnValue.matches("3")) {
			newColumnValue = "8";
		} else if (columnValue.matches("4")) {
			newColumnValue = "12";
		} else {
			newColumnValue = "3";
		}
		return newColumnValue;
	}
}
