/* Copyright Taylors University */

package com.taylorsuniversity.models;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.acs.commons.genericlists.GenericList;
import com.adobe.acs.commons.genericlists.GenericList.Item;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.google.gson.Gson;
import com.taylorsuniversity.constants.Constants;
import com.taylorsuniversity.models.bean.CourseClassificationsBean;
import com.taylorsuniversity.models.bean.ProgrammesBean;
import com.taylorsuniversity.utils.CoreUtils;

/**
 * This class is used to retrieve the properties configured in the header component of the home-page
 * template.
 */
@Model(adaptables = SlingHttpServletRequest.class)
public final class ApplicationFormComponentModel {

    /** The Constant LOG. */
    public static final Logger LOGGER = LoggerFactory.getLogger(ApplicationFormComponentModel.class);

	@Self
	private SlingHttpServletRequest request;

    @Inject
	@Optional
	@Via("resource")
	private PageManager pageManager;

    @Inject
    @Optional
    private Page currentPage;

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
		Map<String, List<String>> courseAndProgramMap = new TreeMap<>();
		try {
			uri = new URI(request.getHeader("referer"));
			String pagePath = uri.getPath();
			LOGGER.debug("URI is : {}", uri);
			if (StringUtils.endsWith(uri.getPath(), Constants.HTML_EXTN)) {
				pagePath = StringUtils.substringBefore(uri.getPath(), Constants.HTML_EXTN);
			}

			LOGGER.debug("Page Manager in getCourseAndProgrameMap is : {}", pageManager);
			if (CoreUtils.isInternalLink(uri.getPath()) && null != pageManager
					&& null != pageManager.getPage(pagePath)
					&& pageManager.getPage(pagePath).getTemplate().getPageTypePath()
							.equalsIgnoreCase(Constants.COURSES_PAGE_TEMPLATE)) {
				LOGGER.debug("Template path is : {}",
						pageManager.getPage(pagePath).getTemplate().getPageTypePath());
				LOGGER.debug("Document Referrer Page Path received is : {}", pagePath);
				ValueMap vm = pageManager.getPage(pagePath).getProperties();
				List<String> prog = new ArrayList<>();
				prog.add(vm.get("programme", String.class));
				courseAndProgramMap.put(vm.get("levelOfStudy", String.class),
						prog);
				LOGGER.debug("Level of Study is : {} , Programme is : {}",
						vm.get("levelOfStudy", String.class),
						prog.get(0));
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
				if (null != ccb.getProgrammes()) {
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

	/**
     * Gets the list of previous, current & next year.
     *
     * @return the list of previous, current & next year
     **/
    public List<String> getPrevCurNextYears() {
    	List<String> years = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        LOGGER.debug("Previous Year is : {} ", cal.get(Calendar.YEAR) - 1);
        years.add(Integer.toString(cal.get(Calendar.YEAR) - 1));
        LOGGER.debug("Current Year is : {} ", cal.get(Calendar.YEAR));
        years.add(Integer.toString(cal.get(Calendar.YEAR)));
        LOGGER.debug("Next Year is : {} ", cal.get(Calendar.YEAR) + 1);
        years.add(Integer.toString(cal.get(Calendar.YEAR) + 1));
        return years;
    }
}
