/* Copyright Taylors University */

package com.taylorsuniversity.models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.Session;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taylorsuniversity.constants.Constants;
import com.taylorsuniversity.models.bean.HeaderComponentModelBean;
import com.taylorsuniversity.models.bean.NavigationSectionModelBean;
import com.taylorsuniversity.utils.CoreUtils;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

import org.apache.sling.api.resource.ValueMap;

import com.google.gson.JsonObject;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;

/**
 * This class is used to retrieve the properties configured in the header component of the home-page
 * template.
 */
@Model(adaptables = Resource.class)
public class HeaderComponentModel {

  private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

  @Inject
  private ResourceResolver resolver;

  @Inject
  private Resource resource;
  
  @Inject
  @Optional
  QueryBuilder queryBuilder;

  private Page homePage;

  private HeaderComponentModelBean headerComponentModelBean;

  private List<NavigationSectionModelBean> navigationSectionModelBeanList;
  
  private List<JsonObject> schoolAndCourseListJson;

  /**
   * Post construct.
   */
  @PostConstruct
  public void postConstruct() {
    LOGGER.error("Resolver is : {}", resolver);
    PageManager pageManager = resolver.adaptTo(PageManager.class);
    LOGGER.debug("PageManager is  : {}", pageManager);
    Page currentPage = pageManager == null ? null : pageManager.getContainingPage(resource);
    LOGGER.debug("CurrentPage is : {}", currentPage);
    if(currentPage == null) {
      LOGGER.debug("currentPage is null");
      return;
    }
    homePage = CoreUtils.getParentPage(currentPage, Constants.HOMEPAGE_LEVEL);
    if(homePage == null) {
      LOGGER.debug("HomePage is null. CurrentPage is : {}", currentPage.getPath());
      return;
    }
    LOGGER.debug("HomePage is : {}", homePage.getPath());
    Resource resource =
        resolver.getResource(homePage.getPath() + Constants.HEADER_COMPONENT_RELATIVE_PATH);
    LOGGER.debug("resource is :  {}", resource);
    if (null == resource) {
      LOGGER.debug("resource in postConstruct is null");
      return;
    }
    ValueMap valMap = resource.adaptTo(ValueMap.class);
    if (null == valMap) {
      LOGGER.debug("the value map in postConstruct for the resource is null");
      return;
    }
    headerComponentModelBean = new HeaderComponentModelBean();
    headerComponentModelBean.setSwitchText(valMap.get("switchText", String.class));
    headerComponentModelBean.setSwitchPath(valMap.get("switchPath", String.class));
    headerComponentModelBean.setLogoTitle(valMap.get("logoTitle", String.class));
    headerComponentModelBean.setLoginPagePath(valMap.get("loginPagePath", String.class));
    headerComponentModelBean.setFileReference(valMap.get("fileReference", String.class));
    headerComponentModelBean
        .setSearchResultsPagePath(valMap.get("searchResultsPagePath", String.class));
    resource = resolver.getResource(
        homePage.getPath() + Constants.HEADER_COMPONENT_RELATIVE_PATH + "/navigationSections");
    if (resource != null) {
      navigationSectionModelBeanList =
          CoreUtils.getNodeProperties(resource, NavigationSectionModelBean.class);
    }
  }

  /**
   * Gets the navigation section bean list.
   *
   * @return the navigation section icon bean list
   */
  public List<Page> getNavigationSectionModelBean() {
    LOGGER.debug("List of Navigation Sections are : {} ", navigationSectionModelBeanList);
    List<Page> sectionPages = new ArrayList<>();
    Iterator<NavigationSectionModelBean> navigationSectionModelBeanIterator =
        navigationSectionModelBeanList.iterator();
    while (navigationSectionModelBeanIterator.hasNext()) {
      try {
        NavigationSectionModelBean navigationSectionModelBean =
            navigationSectionModelBeanIterator.next();
        
        if (navigationSectionModelBean == null
            || navigationSectionModelBean.getSectionPath() == null) {
          return Collections.emptyList();
        }
        
        PageManager pageManager = resolver.adaptTo(PageManager.class);
        LOGGER.debug("PageManager is  : {}", pageManager);
        if (pageManager == null) {
          LOGGER.error("PageManager is null in getNavigationSectionModelBean ...");
          return Collections.emptyList();
        }
        Page currentPage = pageManager.getPage(navigationSectionModelBean.getSectionPath());
        sectionPages.add(currentPage);

        String showCourseSearch = navigationSectionModelBean.getShowCourseSearch() != null
            ? navigationSectionModelBean.getShowCourseSearch() : "";
        LOGGER.debug("showCourseSearch before : {}", showCourseSearch);
        
        Resource contentResource = pageManager
            .getContainingPage(navigationSectionModelBean.getSectionPath()).getContentResource();
        Node node = contentResource.adaptTo(Node.class);
        if (node == null) {
          LOGGER.error("Node is null in getNavigationSectionModelBean ...");
          return Collections.emptyList();
        }
        LOGGER.debug("Node path is : {}", node.getPath());
        node.setProperty(Constants.SHOW_COURSES_SEARCH, showCourseSearch);
        LOGGER.debug("showCourseSearch after : {}", node.getProperty(Constants.SHOW_COURSES_SEARCH));
        Session session = resolver.adaptTo(Session.class);
        if (session == null) {
          LOGGER.error("Session is null in getNavigationSectionModelBean ...");
          return Collections.emptyList();
        }
        session.save();
        LOGGER.debug("showCourseSearch is : {}", node.getProperty("showCourseSearch"));
      } catch (Exception e) {
        LOGGER.error(
            "Error while getting the navigation section model bean in HeaderComponentModel : {}}",
            e);
      }
    }

    return sectionPages;
  }

  /**
   * Gets the header component bean.
   *
   * @return the header component bean
   */
  public HeaderComponentModelBean getHeaderComponentModelBean() {
    LOGGER.debug("List of header components are : {} ", headerComponentModelBean);
    return headerComponentModelBean;
  }

  /**
   * Gets the home page content path.
   *
   * @return the home page content path
   */
  public String getHomePageContentPath() {
    LOGGER.debug("HomePage content path is : {}", homePage);
    return homePage != null ? homePage.getPath() + "/jcr:content" : "";
  }

  /**
   * Gets the current year.
   *
   * @return the current year
   */
  public int getCurrentYear() {
    Calendar cal = Calendar.getInstance();
    LOGGER.debug("Current Year is : {} ", cal.get(Calendar.YEAR));
    return cal.get(Calendar.YEAR);
  }

  /**
   * Gets the auto complete suggestion.
   *
   * @return the auto complete suggestion
   */
  public String getSearchSuggestJson() {
    
    LOGGER.debug("Get Search Suggest method invoked !!!");
    
    schoolAndCourseListJson = new ArrayList<>();
    
    PageManager pageManager = resolver.adaptTo(PageManager.class);
    LOGGER.debug("PageManager is : {}", pageManager);
    Page page = pageManager == null ? null : pageManager.getContainingPage(resource);
    
    if(page == null) {
      LOGGER.debug("Page is null");
      return "";
    }
    
    createPredicateMapToFindSchoolAndCourses(page.getPath(), Constants.SCHOOL_PAGE_TEMPLATE);
    createPredicateMapToFindSchoolAndCourses(page.getPath(), Constants.COURSES_PAGE_TEMPLATE);
    
    String schoolAndCoursesJson = schoolAndCourseListJson.toString();
    
    LOGGER.debug("Json list for school and courses is : {}", schoolAndCoursesJson);
    
    return schoolAndCoursesJson;
  }
  
  private void createPredicateMapToFindSchoolAndCourses(String homePagePath, String templatePath) {

    Map<String, String> predicateMap = new HashMap<>();
  
    predicateMap.put(Constants.TYPE, Constants.CQ_PAGE);
    predicateMap.put(Constants.PATH, homePagePath);
  
    predicateMap.put(Constants.FIRST_PROPERTY_KEY, Constants.JCR_CONTENT_RESOURCE_TYPE);
    predicateMap.put(Constants.FIRST_PROPERTY_VALUE, templatePath);

    LOGGER.debug("Predicate map is : {}", predicateMap);
    
    List<String> searchResults = getSchoolAndCoursesSearchResults(predicateMap);
    
    if(searchResults != null && !searchResults.isEmpty()) {
      Iterator<String> itr = searchResults.iterator();
      while(itr.hasNext()) {
        String nextItem = itr.next();
        LOGGER.debug("Search result next item is : {} ", nextItem);
        PageManager pageManager = resolver.adaptTo(PageManager.class);
        LOGGER.debug("PageManager is : {}", pageManager);
        Page page = pageManager == null ? null
            : pageManager.getPage(nextItem);
        if(page == null) {
          return;
        }
        JsonObject obj = new JsonObject();
        obj.addProperty("name", page.getTitle());
        obj.addProperty("link", page.getPath() + ".html");
        this.schoolAndCourseListJson.add(obj);
      }
    }
  }
  
  private List<String> getSchoolAndCoursesSearchResults(
      Map<String, String> predicateMap) {

    Session session = null;
    List<String> pagePaths = null;
    try {
        session = resolver.adaptTo(Session.class);
  
        Query queryObj = this.queryBuilder.createQuery(
                PredicateGroup.create(predicateMap), session);
        String searchQuery = queryObj.getPredicates().toString();
        LOGGER.debug("Search Query : {}", searchQuery);
  
        SearchResult searchResults = queryObj.getResult();
  
        if (searchResults != null) {
            LOGGER.debug("Total number of search matches are: "
                    + searchResults.getTotalMatches());
            final List<Hit> hitsList = searchResults.getHits();
            if (hitsList != null && !hitsList.isEmpty()) {
              pagePaths = new ArrayList<>();
                for (Hit hit : searchResults.getHits()) {
                  pagePaths.add(hit.getPath());
                }
            }
        }
    } catch (Exception e) {
        LOGGER.error("Exception at getWebToPrintSearchResults(): {}", e);
    }
    return pagePaths;
  
  }
}
