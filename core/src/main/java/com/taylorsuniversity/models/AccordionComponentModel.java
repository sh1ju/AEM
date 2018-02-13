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

import com.taylorsuniversity.models.bean.AccordionModelBean;

/**
 * This Model class helps in retrieving the attributes for accordion component.
 */
@Model(adaptables = SlingHttpServletRequest.class)
public final class AccordionComponentModel {

    /**
     * The Constant LOG.
     */
    public static final Logger LOGGER = LoggerFactory.getLogger(AccordionComponentModel.class);
    /**
     *
     */
    @Inject
    private Resource resource;

    /**
     * The init method
     */
    private List<AccordionModelBean> submenuItems = new ArrayList<>();

    /**
     *this would populate the value
     * @return list of modal beans
     */
    public List<AccordionModelBean> getSubmenuItems() {
        ResourceResolver resourceResolver = resource.getResourceResolver();

        if (resourceResolver != null) {
           LOGGER.error("resource::" + resource.getPath());
           Node accordionNode = resourceResolver.resolve(resource.getPath()).adaptTo(Node.class);
           try {
                if (accordionNode != null && accordionNode.hasNode("listItems")) {
                    Node listItemsNode = accordionNode.getNode("listItems");
                    if (listItemsNode != null && listItemsNode.hasNodes()) {
                        NodeIterator nodeIterator = listItemsNode.getNodes();
                        AccordionModelBean modelBean = null;
                        while (nodeIterator.hasNext()) {
                            Node  childNode = (Node) nodeIterator.next();
                            if (childNode != null && childNode.hasProperty("accordiontitles_t")) {
                                String value = childNode.getProperty("accordiontitles_t").getValue().getString();
                                modelBean = new AccordionModelBean(value);
                                submenuItems.add(modelBean);
                            }
                        }
                    }
                   }
            } catch (RepositoryException e) {
                LOGGER.error("RepositoryException in AccordionComponentModel:" + e);
            }
        }
        return submenuItems;
    }


}
