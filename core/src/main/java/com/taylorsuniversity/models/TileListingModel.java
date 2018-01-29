package com.taylorsuniversity.models;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import com.taylorsuniversity.models.bean.TileListingModelBean;

@Model(adaptables = SlingHttpServletRequest.class)
public class TileListingModel {

	@Inject
	ResourceResolver resolver;
	
	@Optional
	@Inject
	List<Resource> tileListing;

	private List<TileListingModelBean> tileListingItems = null;
	Logger LOGGER = LoggerFactory.getLogger(TileListingModel.class);

	@PostConstruct
	protected void init() {

		tileListingProps();
	}

	private void tileListingProps() {
		
		 try {
		      if (null != tileListing && !tileListing.isEmpty()) {
		        tileListingItems = new ArrayList<TileListingModelBean>();
		        
		        for (Resource tile : tileListing) {
		        	LOGGER.debug("Tile items are : {}", tile);
		        	tileListingItems.add(tile.adaptTo(TileListingModelBean.class));
		        }
		      }
		    } catch (Exception exception) {
		      LOGGER.error("Unable to parse the tile list ", exception);
		    }
	}

	public List<TileListingModelBean> getTileListingItems() {
		LOGGER.debug("Tile Listing items are : {}", tileListingItems);
		return tileListingItems;
	}

}
