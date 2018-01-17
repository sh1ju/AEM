/* Copyright Taylors University */

package com.taylorsuniversity.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;

/**
 * The Class CoreUtil.
 */
public final class CoreUtils {

    /** The Constant LOG. */
    public static final Logger LOG = LoggerFactory.getLogger(CoreUtils.class);

    /**
     * Instantiates a new core util.
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
}
