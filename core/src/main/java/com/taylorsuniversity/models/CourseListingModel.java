/* Copyright Taylors University */

package com.taylorsuniversity.models;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.Via;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.google.common.collect.Lists;
import com.taylorsuniversity.utils.CoreUtils;

/**
 * This class is used to retrieve the properties configured under the Course
 * Listing component
 *
 */
@Model(adaptables = SlingHttpServletRequest.class)
public final class CourseListingModel {
	@Inject
	private ResourceResolver resolver;
	@Optional
	@Inject
	@Via("resource")
	private String undergradpath;

	@Optional
	@Inject
	@Via("resource")
	private String postgradpath;

	private List<Page> underGradCourseList = null;
	private List<Page> postGradCourseList = null;

	private Logger logger = LoggerFactory.getLogger(CourseListingModel.class);

	/**
	 * @return List of Undergrad Courses
	 */
	public List<Page> getUnderGradCourseList() {
		if (null != undergradpath) {
			underGradCourseList = new ArrayList<>();
			Resource contentResource = resolver.getResource(undergradpath);
			Page pageRoot = CoreUtils.getPageByResource(contentResource);

			underGradCourseList = Lists.newArrayList(pageRoot.listChildren());
			logger.info(pageRoot.toString());

		}
		logger.debug("Course List items are : {}", underGradCourseList);

		return underGradCourseList;
	}

	/**
	 * @return List of Postgrad Courses
	 */
	public List<Page> getPostGradCourseList() {
		if (null != postgradpath) {
			postGradCourseList = new ArrayList<>();
			Resource contentResource = resolver.getResource(postgradpath);

			Page pageRoot = CoreUtils.getPageByResource(contentResource);
			postGradCourseList = Lists.newArrayList(pageRoot.listChildren());
			logger.info(pageRoot.toString());

		}
		logger.debug("Course List items are : {}", postGradCourseList);

		return postGradCourseList;
	}
}
