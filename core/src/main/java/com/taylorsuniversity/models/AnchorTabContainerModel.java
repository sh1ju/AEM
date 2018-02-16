/* Copyright Taylors University */

package com.taylorsuniversity.models;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taylorsuniversity.models.bean.AnchorTabContainerModelBean;

/**
 * This Model class helps in retrieving the attributes for accordion component.
 */
@Model(adaptables = SlingHttpServletRequest.class)
public class AnchorTabContainerModel {

	/**
	 * The Constant LOG.
	 */
	public static final Logger LOGGER = LoggerFactory.getLogger(AnchorTabContainerModel.class);
	/**
	 *
	 */
	@Inject
	private Resource resource;


	private List<AnchorTabContainerModelBean> anchorTabItems = new ArrayList<>();

	/**
	 *this would populate the value
	 *@return list of AnchorTabContainerModelBean
	 */
	public final List<AnchorTabContainerModelBean> getAnchorTabItems() {
		ResourceResolver resourceResolver = resource.getResourceResolver();

		if (resourceResolver != null) {
			LOGGER.debug("getAnchorTabItems resource : " + resource.getPath());
			Node anchorTabContainerNode = resourceResolver.resolve(resource.getPath()).adaptTo(Node.class);
			try {
				if (anchorTabContainerNode != null && anchorTabContainerNode.hasNode("anchorpar")) {
					LOGGER.debug("getAnchorTabItems has anchorpar");
					Node anchorParNode = anchorTabContainerNode.getNode("anchorpar");
					if (anchorParNode != null && anchorParNode.hasNodes()) {
						LOGGER.debug("getAnchorTabItems has anchorParNode has NODE.");
						NodeIterator nodeIterator =
								anchorParNode.getNodes();
						while (nodeIterator.hasNext()) {
							LOGGER.debug("getAnchorTabItems nodeIterator has NEXT.");
							AnchorTabContainerModelBean modelBean = null;
							Node  childNode
							= (Node) nodeIterator.next();
							if (childNode != null
									&& childNode.hasProperty("anchor")) {
								LOGGER.debug("getAnchorTabItems has title.");
								String title =
										childNode.getProperty("title")
										.getValue().getString();
								LOGGER.debug("getAnchorTabItems title IS -->" + title);
								String anchor =
										childNode.getProperty("anchor")
										.getValue().getString();
								modelBean =
										new AnchorTabContainerModelBean(title,
												anchor);
								anchorTabItems.add(modelBean);
							} else {
								LOGGER.debug("child node is null");
							}
						}
					} else {
						LOGGER.debug("container node is null");
					}
				}
			} catch (RepositoryException e) {
				LOGGER.error("RepositoryException in AnchorTabContainertModel: {}", e);
			}
		}
		return anchorTabItems;
	}
}
