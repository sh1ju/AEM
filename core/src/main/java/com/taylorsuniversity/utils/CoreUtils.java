/* Copyright Taylors University */

package com.taylorsuniversity.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.Template;
import com.taylorsuniversity.constants.Constants;

/**
 * The Class CoreUtil.
 */
public final class CoreUtils {

    /** The Constant LOG. */
    public static final Logger LOGGER = LoggerFactory.getLogger(CoreUtils.class);

    /**
     * Instantiates a new core utils.
     */
    private CoreUtils() {

    }

    /**
     * Gets the parent page.
     *
     * @param currentPage the current page
     * @param absoluteLevel the absolute level
     * @return the parent page
     */
    public static Page getParentPage(final Page currentPage, final int absoluteLevel) {
        Page parentPage = null;
        if (null != currentPage) {
            parentPage = currentPage.getAbsoluteParent(absoluteLevel);
        }
        return parentPage;
    }

    /**
     * Gets the multi value property.
     *
     * @param <T> the generic type
     * @param rootPage the root page
     * @param property the property
     * @param clazz the clazz
     * @return the multi value property
     */
    public static <T> List<T> getMultiValueProperty(final Page rootPage, final String property, final Class<T> clazz) {
        Resource multifieldResource = null;
        List<T> modelBeanList = null;
        if (null != rootPage) {
            multifieldResource = rootPage.getContentResource(property);
            LOGGER.debug("Multifield resource is : {}", multifieldResource);
            if (null != multifieldResource) {
                modelBeanList = getNodeProperties(multifieldResource, clazz);
            }
        }
        return modelBeanList;
    }

    /**
     * Gets the node properties.
     *
     * @param <T> the generic type
     * @param resource the resource
     * @param clazz the clazz
     * @return the node properties
     */
    public static <T> List<T> getNodeProperties(final Resource resource, final Class<T> clazz) {
        List<T> modelBeanList = new ArrayList<>();
        Iterator<Resource> childResources = resource.listChildren();

        LOGGER.debug("ChildResources is : {}", childResources);

        while (childResources.hasNext()) {
            Resource currentResource = childResources.next();
            if (null != currentResource) {
                T modelBean = currentResource.adaptTo(clazz);
                modelBeanList.add(modelBean);
            }
        }
        LOGGER.debug("Following ChildResoures are added to the list : {}", modelBeanList);
        return modelBeanList;
    }


    /**
     * Gets the page by resource.
     *
     * @param resource the resource
     * @return the page by resource
     */
    public static Page getPageByResource(final Resource resource) {
        Page page = null;
        if (null != resource) {
            page = resource.adaptTo(Page.class);
        }
        return page;
    }

    /**
     * Gets the multi value dropdown property.
     *
     * @param rootPage
     *            the root page
     * @param property
     *            the property
     * @return the multi value dropdown property
     */
    public static List<String> getMultiValueDropdownProperty(final Page rootPage, final String property) {
        if (null != rootPage && StringUtils.isNotBlank(property)) {
            ValueMap properties = rootPage.getProperties();
            return Arrays.asList(properties.get(property, new String[] {}));
        }
        return Collections.emptyList();

    }

    /**
     * Gets the template name.
     *
     * @param resource the resource
     * @return the template name
     */
    public static String getTemplateName(final Resource resource) {
        Page page = null;
        if (resource == null) {
            return "";
        }

        page = resource.adaptTo(Page.class);

        if (page == null) {
            return "";
        }

        Template template = page.getTemplate();

        if (template == null) {
            return "";
        }

        return template.getName();
    }

    /**
    * Intercepts the linkUrl builds it appropriately.
    * Internal url's will be appended with the appropriate extension.
    * External url's will undergo no modification.
    *
    * @param resolver
    *           resource resolver
    * @param linkUrl
    *            the authored link
    * @return a mapped link consistent with the ResourceResolver mappings
    */
    public static String getQualifiedLink(final ResourceResolver resolver, final String linkUrl) {
        String url = StringUtils.EMPTY;
        if (StringUtils.isNotBlank(linkUrl) && linkUrl.startsWith(Constants.CONTENT_ROOT)
            && !linkUrl.contains(Constants.HTML_EXTN) && !linkUrl.startsWith(Constants.DAM_ROOT)) {
            int paramIndex = linkUrl.indexOf('?');
            if (paramIndex > -1) {
                url = resolver.map(linkUrl.substring(0, paramIndex)).concat(Constants.HTML_EXTN)
                      .concat(linkUrl.substring(paramIndex));
            } else {
                url = resolver.map(linkUrl).concat(Constants.HTML_EXTN);
            }
        }
        return StringUtils.isNotEmpty(url) ? url : linkUrl;
    }

    /**
     * Intercepts the linkUrl and checks if external or internal
     * @param linkUrl the authored link
     * @return boolean
     */
    public static boolean isInternalLink(final String linkUrl) {
        return (StringUtils.isNotBlank(linkUrl) && linkUrl.startsWith(Constants.CONTENT_ROOT));
    }
}
