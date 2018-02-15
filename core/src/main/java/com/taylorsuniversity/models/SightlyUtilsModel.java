/* Copyright Taylors University */

package com.taylorsuniversity.models;

import com.adobe.acs.commons.genericlists.GenericList;
import com.adobe.acs.commons.genericlists.GenericList.Item;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.google.gson.Gson;
import com.taylorsuniversity.constants.Constants;
import com.taylorsuniversity.models.bean.CourseClassificationsBean;
import com.taylorsuniversity.models.bean.ProgrammesBean;
import com.taylorsuniversity.utils.CoreUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is used to retrieve the values to be used in sightly.
 */
@Model(adaptables = SlingHttpServletRequest.class)
public final class SightlyUtilsModel {

	/** The Constant LOG. */
	public static final Logger LOGGER = LoggerFactory.getLogger(SightlyUtilsModel.class);

	/** The Resolver. */
	@Inject
	private ResourceResolver resolver;

	/** The Resolver. */
	@Self
	private SlingHttpServletRequest request;

	@Inject
	@Optional
	@Via("resource")
	private PageManager pageManager;

	@Inject
	@Optional
	private Page currentPage;

	@Inject
	@Optional
	private String linkTarget;

	private String link = StringUtils.EMPTY;
	private String linkTitle = StringUtils.EMPTY;

	/**
	 * Helper method for link checker
	 *
	 * @return String String
	 */
	public String getLink() {
		if (StringUtils.isNotBlank(linkTarget)) {
			LOGGER.debug("Link Target is : {}", linkTarget);
			link = CoreUtils.getQualifiedLink(resolver, linkTarget);
		} else {
			LOGGER.debug("Link entered is invalid");
		}
		LOGGER.debug("Link being sent is : {}", link);
		return link;
	}

	/**
	 * Helper method for checking the type of link i.e. internal or external
	 *
	 * @return String String
	 */
	public String getTitleLinkType() {
		if (StringUtils.isBlank(linkTarget)) {
			LOGGER.debug("Link Type is : {}", linkTarget);
			return StringUtils.EMPTY;
		}
		return String.valueOf(CoreUtils.isInternalLink(linkTarget));
	}

	/**
	 * Helper method for hide in navigation for all the child pages of a given
	 * page
	 *
	 * @return String String
	 */
	public String getHideInNavForAllPages() {
		String hideInNavForAllPages = "true";
		if (StringUtils.isNotBlank(linkTarget)) {
			LOGGER.debug("getHideInNavForAllPages Link Target is : {}", linkTarget);
			LOGGER.debug("PageManager is  : {}", pageManager);
			if (pageManager == null) {
				LOGGER.error("PageManager is null in getNavigationSectionModelBean ...");
				return "";
			}
			Page currentPage1 = pageManager.getPage(linkTarget);
			LOGGER.debug("Current page in getHideInNavForAllPages is : {}", currentPage1.getPath());
			Iterator<Page> itr = currentPage1.listChildren();
			while (itr.hasNext()) {
				Page page = itr.next();
				LOGGER.debug("Current child of the page {} in getHideInNavForAllPages is : {}",
						currentPage1.getPath(),
						page);
				if (!page.isHideInNav()) {
					LOGGER.debug(
							"isHideInNav of the Current child {] of the page {} "
									+ "in getHideInNavForAllPages is : {}",
							page, currentPage1.getPath(), page.isHideInNav());
					hideInNavForAllPages = "false";
				}
			}

		} else {
			LOGGER.debug("getHideInNavForAllPages LinkTarget is invalid");
		}
		LOGGER.debug("hideInNavForAllPages is : {}", hideInNavForAllPages);
		return hideInNavForAllPages;
	}

	/**
	 * Helper method for getting the title of an internal page
	 *
	 * @return String String
	 */
	public String getLinkTitle() {
		if (null != pageManager) {
			if (CoreUtils.isInternalLink(linkTarget) && (null != pageManager.getPage(linkTarget))) {
				LOGGER.debug("Link Path received is: {} ", linkTarget);
				linkTitle = pageManager.getPage(linkTarget).getTitle();
			} else {
				LOGGER.debug("Link title not available for: {} ", linkTarget);
				linkTitle = linkTarget;
			}
		} else {
			LOGGER.debug("Page Manager is null.");
		}
		LOGGER.debug("Link Title for Link Path: {}  is {} : ", linkTarget, linkTitle);
		return linkTitle;
	}

	/**
	 * Helper method for link checker
	 *
	 * @return String String
	 */
	public String getDocumentReferrer() {
		LOGGER.debug("Document Referrer in getDocumentReferrer is : {}", request.getHeader(Constants.REFERRER));
		URI uri = null;
		try {
			uri = new URI(request.getHeader("referer"));
		} catch (URISyntaxException e) {
			LOGGER.error("URISyntaxException while getting the document referrer in SightyUtilsModel : {}",
					e);
		}
		LOGGER.debug("URI of the Document Referrer in getDocumentReferrer is : {}",
				uri);
		return uri == null ? "" : uri.getPath();
	}

	/**
	 * Helper method to check if the document.referrer is course page or not
	 *
	 * @return boolean boolean
	 */
	public boolean isCoursePageReferrer() {
		String path = getDocumentReferrer();
		boolean coursePageReferrer = false;
		LOGGER.debug("Path in isCoursePageReferrer is : {}", path);
		LOGGER.debug("Is internal link in isCoursePageReferrer is : {}", CoreUtils.isInternalLink(path));
		if (StringUtils.endsWith(path, Constants.HTML_EXTN)) {
			path = StringUtils.substringBefore(path, Constants.HTML_EXTN);
		}
		if (CoreUtils.isInternalLink(path) && null != pageManager
				&& null != pageManager.getPage(path)
				&& pageManager.getPage(path).getTemplate().getPageTypePath()
						.equalsIgnoreCase(Constants.COURSES_PAGE_TEMPLATE)) {
			coursePageReferrer = true;
		}
		return coursePageReferrer;
	}

	/**
	 * Helper method to get the course & program map
	 *
	 * @return String String
	 */
	public Map<String, List<String>> getCourseAndProgrameMap() {
		LOGGER.debug("Request object is : {}", request);
		LOGGER.debug("PageManager object is : {}", pageManager);
		LOGGER.debug("Document Referrer is : {}", request.getHeader(Constants.REFERRER));
		URI uri;
		Gson gson = new Gson();
		Map<String, List<String>> courseAndProgramMap = new TreeMap<>();
		try {
			uri = new URI(request.getHeader("referer"));
			String pagePath = uri.getPath();
			LOGGER.debug("URI is : {}", uri);
			if (StringUtils.endsWith(uri.getPath(), Constants.HTML_EXTN)) {
				pagePath = StringUtils.substringBefore(uri.getPath(), Constants.HTML_EXTN);
			}

			if (CoreUtils.isInternalLink(uri.getPath()) && null != pageManager
					&& null != pageManager.getPage(pagePath)
					&& pageManager.getPage(pagePath).getTemplate().getPageTypePath()
							.equalsIgnoreCase(Constants.COURSES_PAGE_TEMPLATE)) {
				LOGGER.debug("Template path is : {}",
						pageManager.getPage(pagePath).getTemplate().getPageTypePath());
				LOGGER.debug("Document Referrer Page Path received is : {}", pagePath);
				ValueMap vm = pageManager.getPage(pagePath).getProperties();
//				courseAndProgramMap.put(vm.get("levelOfStudy", String.class),
//						gson.fromJson(vm.get("programme", String.class),
//								ProgrammesBean[].class));
				List<String> prog = new ArrayList<>();
				prog.add(vm.get("programme", String.class));
				courseAndProgramMap.put(vm.get("levelOfStudy", String.class),
						prog);
				LOGGER.debug("Level of Study is : {} , Programme is : {}",
						vm.get("levelOfStudy", String.class),
						prog.get(0));
				// courseAndProgramMap.put("levelOfStudy",
				// vm.get("levelOfStudy", String.class));
				// courseAndProgramMap.put("programme", vm.get("programme",
				// String.class));
				// courseAndProgramMap.put("interestedCourse",
				// pageManager.getPage(pagePath).getTitle());
			} else {
				LOGGER.debug("Document Referrer Page Path is External : {}", pagePath);
				getCoursesAndProgrammes(courseAndProgramMap);
			}
		} catch (URISyntaxException e) {
			LOGGER.error("URISyntaxException while getting the document referrer in SightyUtilsModel : {}",
					e);
		}
		return courseAndProgramMap;
	}

	/**
	 * Gets the Courses Map
	 *
	 * @param courseAndProgramMap Map<String, String>
	 */
	private void getCoursesAndProgrammes(final Map<String, List<String>> courseAndProgramMap) {
		LOGGER.debug("Current Page is : {}", currentPage);
		Gson gson = new Gson();
		Page homePage = CoreUtils.getParentPage(currentPage, Constants.HOMEPAGE_LEVEL);
		LOGGER.debug("HomePage is : {}", homePage);
		if (homePage == null) {
			return;
		}
		LOGGER.debug("HomePagePath is : {}", homePage.getPath());

		String[] courseClassifications = homePage.getProperties().get("courseClassifications", String[].class);

		if (null != courseClassifications && ArrayUtils.isNotEmpty(courseClassifications)) {
			LOGGER.debug("Course Classifications is: {} ", ArrayUtils.toString(courseClassifications));
			for (String courseClassification : courseClassifications) {
				CourseClassificationsBean ccb = gson.fromJson(courseClassification,
						CourseClassificationsBean.class);
				LOGGER.debug("Course Classifications model bean is : {}", ccb);
				List<String> prog = new ArrayList<>();
				if (null != ccb && null != ccb.getProgrammes()) {
					for (ProgrammesBean programme : ccb.getProgrammes()) {
						prog.add(programme.getProgramme());
					}
				}
				courseAndProgramMap.put(ccb.getLevelOfStudy(), prog);
			}
		}
		LOGGER.debug("Returning the courseAndProgramMap : {}", courseAndProgramMap);
	}

	/**
	 * Gets the State Map
	 *
	 * @return map of stateMap
	 */
	public Map<String, String> getStateMap() {
		LOGGER.debug("Entering getStateMap in SightlyUtilsModel...");
		Map<String, String> stateMap = new TreeMap<>();
		if (pageManager != null) {
			LOGGER.debug("State generic list path is : {}", Constants.STATE_GENERIC_LIST);
			Page listPage = pageManager.getPage(Constants.STATE_GENERIC_LIST);
			LOGGER.debug("ListPage is : {} ", listPage);

			GenericList list = listPage.adaptTo(GenericList.class);
			LOGGER.debug("List is : {} ", list);
			if (list != null) {
				List<Item> items = list.getItems();
				LOGGER.debug("Size of the state generic list of item is : {}", items.size());
				for (Item item : items) {
					stateMap.put(item.getValue(), item.getTitle());
				}
			}
			LOGGER.debug("Exiting getStateMap...");
		}
		LOGGER.debug("State Map is : {}", stateMap);
		return stateMap;
	}

	/**
	 * Gets the Nationality Map
	 *
	 * @return map of nationalityMap
	 */
	public Map<String, String> getNationalityMap() {
		LOGGER.debug("Entering getNationalityMap in SightlyUtilsModel...");
		Map<String, String> nationalityMap = new TreeMap<>();
		if (pageManager != null) {
			LOGGER.debug("Nationality generic list path is : {}", Constants.NATIONALITY_GENERIC_LIST);
			Page listPage = pageManager.getPage(Constants.NATIONALITY_GENERIC_LIST);
			LOGGER.debug("ListPage is : {}", listPage);

			GenericList list = listPage.adaptTo(GenericList.class);
			LOGGER.debug("List is : {}", list);
			if (list != null) {
				List<Item> items = list.getItems();
				LOGGER.debug("Size of the nationality generic list of item is : {}", items.size());
				for (Item item : items) {
					nationalityMap.put(item.getValue(), item.getTitle());
				}
			}
			LOGGER.debug("Exiting getNationalityMap...");
		}
		LOGGER.debug("Nationality Map is : {}", nationalityMap);
		return nationalityMap;
	}

	/**
	 * Gets the Academic Qualifications Map
	 *
	 * @return map of academicQualificationsMap
	 */
	public Map<String, String> getAcademicQualificationsMap() {
		LOGGER.debug("Entering getAcademicQualificationsMap in SightlyUtilsModel...");
		Map<String, String> academicQualificationsMap = new TreeMap<>();
		if (pageManager != null) {
			LOGGER.debug("Academic Qualifications generic list path is : {}",
					Constants.ACADEMIC_QUALIFICATIONS_GENERIC_LIST);
			Page listPage = pageManager.getPage(Constants.ACADEMIC_QUALIFICATIONS_GENERIC_LIST);
			LOGGER.debug("ListPage is : {}", listPage);

			GenericList list = listPage.adaptTo(GenericList.class);
			LOGGER.debug("List is : {}", list);
			if (list != null) {
				List<Item> items = list.getItems();
				LOGGER.debug("Size of the academic qualifications generic list of item is : {}",
						items.size());
				for (Item item : items) {
					academicQualificationsMap.put(item.getValue(), item.getTitle());
				}
			}
			LOGGER.debug("Exiting getAcademicQualificationsMap...");
		}
		LOGGER.debug("Academic Qualifications Map is : {}", academicQualificationsMap);
		return academicQualificationsMap;
	}
}
