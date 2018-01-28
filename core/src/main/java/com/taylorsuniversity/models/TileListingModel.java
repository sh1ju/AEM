package com.taylorsuniversity.models;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

import com.taylorsuniversity.models.bean.TileListingModelBean;
import com.taylorsuniversity.utils.CoreUtils;

@SuppressWarnings("deprecation")
@Model(adaptables = Resource.class)
public class TileListingModel {

	@Inject
	ResourceResolver resolver;

	@Inject
	@Optional
	private String[] tileListing;

	private List<TileListingModelBean> tileListingItems = null;
	Logger LOGGER = LoggerFactory.getLogger(TileListingModel.class);

	@PostConstruct
	protected void init() {

		tileListingProps();
	}

	private void tileListingProps() {
		if (ArrayUtils.isNotEmpty(tileListing)) {
			tileListingItems = new ArrayList<TileListingModelBean>();
			for (String tl : tileListing) {
				try {
					JSONObject jsonObject = new JSONObject(tl);
					tileListingItems.add(new TileListingModelBean(
							jsonObject.getString("image"),
							jsonObject.getString("altText"), 
							jsonObject.getString("title"),
							CoreUtils.getQualifiedLink(resolver, jsonObject.getString("titleLink")),
							String.valueOf(CoreUtils.isInternalLink(jsonObject.getString("titleLink"))),
							jsonObject.getString("description")));

				} catch (JSONException e) {
					LOGGER.error(e.getMessage());
				}
			}
		}
	}

	public List<TileListingModelBean> getTileListingItems() {
		LOGGER.debug("Tile Listing items are : {}", tileListingItems);
		return tileListingItems;
	}

}
