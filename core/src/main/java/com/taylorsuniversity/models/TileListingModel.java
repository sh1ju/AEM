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

import com.taylorsuniversity.models.bean.TileListingBean;

@SuppressWarnings("deprecation")
@Model(adaptables = Resource.class)
public class TileListingModel {

	Logger LOG = LoggerFactory.getLogger(TileListingModel.class);

	@Inject
	@Optional
	private String[] tileListing;
	
	private List<TileListingBean> tileListingItems = null;

	@PostConstruct
	protected void init() {

		tileListingProps();
	}

	private void tileListingProps() {
		if (ArrayUtils.isNotEmpty(tileListing)) {
			tileListingItems = new ArrayList<TileListingBean>();
			for (String tl : tileListing) {
				try {
					JSONObject jsonObject = new JSONObject(tl);
					tileListingItems.add(new TileListingBean (
							jsonObject.getString("image"),
							jsonObject.getString("altText"),
							jsonObject.getString("title"),
							jsonObject.getString("titleLink"),
							jsonObject.getString("description")));
					
				} catch (JSONException e) {
					LOG.error(e.getMessage());
				}	
			}
		}
	}
	
	public List<TileListingBean> getTileListingItems() {
		return tileListingItems;
	}

}
