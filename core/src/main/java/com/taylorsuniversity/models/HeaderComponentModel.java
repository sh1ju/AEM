/* Copyright Taylors University */

package com.taylorsuniversity.models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.Session;

import org.apache.sling.api.resource.Resource;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
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

import com.day.cq.search.QueryBuilder;

import com.google.gson.JsonObject;

/**
 * This class is used to retrieve the properties configured in the header component of the home-page
 * template.
 */
@Model(adaptables = SlingHttpServletRequest.class)
public class HeaderComponentModel {

  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @Inject
  private ResourceResolver resolver;

  @Inject
  @Optional
  private QueryBuilder queryBuilder;

  @Inject
  @Optional
  private Page currentPage;

  @Inject
  @Optional
  private String queryParam;

  private Page homePage;

  private HeaderComponentModelBean headerComponentModelBean;

  private List<NavigationSectionModelBean> navigationSectionModelBeanList;

  private List<JsonObject> schoolAndCourseListJson;

  /**
   * Post construct.
   */
  @PostConstruct
  public void postConstruct() {
    logger.error("Resolver is : {}", resolver);
    PageManager pageManager = resolver.adaptTo(PageManager.class);
    logger.debug("PageManager is  : {}", pageManager);
    logger.debug("CurrentPage is : {}", currentPage);
    if (currentPage == null) {
      logger.debug("currentPage is null");
      return;
    }
    homePage = CoreUtils.getParentPage(currentPage, Constants.HOMEPAGE_LEVEL);
    if (homePage == null) {
      logger.debug("HomePage is null. CurrentPage is : {}", currentPage.getPath());
      return;
    }
    logger.debug("HomePage is : {}", homePage.getPath());
    Resource resource =
        resolver.getResource(homePage.getPath() + Constants.HEADER_COMPONENT_RELATIVE_PATH);
    logger.debug("resource is :  {}", resource);
    if (null == resource) {
      logger.debug("resource in postConstruct is null");
      return;
    }

    headerComponentModelBean = resource.adaptTo(HeaderComponentModelBean.class);

    resource = resolver.getResource(
        homePage.getPath() + Constants.HEADER_COMPONENT_RELATIVE_PATH + "/navigationSections");
    logger.debug("Navigation section resource is :  {}", resource);
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
    logger.debug("List of Navigation Sections are : {} ", navigationSectionModelBeanList);
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
        logger.debug("PageManager is  : {}", pageManager);
        if (pageManager == null) {
          logger.error("PageManager is null in getNavigationSectionModelBean ...");
          return Collections.emptyList();
        }
        Page currentPage1 = pageManager.getPage(navigationSectionModelBean.getSectionPath());
        sectionPages.add(currentPage1);

        String showCourseSearch = navigationSectionModelBean.getShowCourseSearch() != null
            ? navigationSectionModelBean.getShowCourseSearch() : "";
        logger.debug("showCourseSearch before : {}", showCourseSearch);

        Resource contentResource = pageManager
            .getContainingPage(navigationSectionModelBean.getSectionPath()).getContentResource();
        Node node = contentResource.adaptTo(Node.class);
        if (node == null) {
          logger.error("Node is null in getNavigationSectionModelBean ...");
          return Collections.emptyList();
        }
        logger.debug("Node path is : {}", node.getPath());
        node.setProperty(Constants.SHOW_COURSES_SEARCH, showCourseSearch);
        logger.debug("showCourseSearch after : {}",
            node.getProperty(Constants.SHOW_COURSES_SEARCH));
        Session session = resolver.adaptTo(Session.class);
        if (session == null) {
          logger.error("Session is null in getNavigationSectionModelBean ...");
          return Collections.emptyList();
        }
        session.save();
        logger.debug("showCourseSearch is : {}", node.getProperty("showCourseSearch"));
      } catch (Exception e) {
        logger.error(
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
    logger.debug("List of header components are : {} ", headerComponentModelBean);
    return headerComponentModelBean;
  }

  /**
   * Gets the home page content path.
   *
   * @return the home page content path
   */
  public String getHomePageContentPath() {
    logger.debug("HomePage content path is : {}", homePage);
    return homePage != null ? homePage.getPath() + "/jcr:content" : "";
  }

  /**
   * Gets the home page path.
   *
   * @return the home page path
   */
  public String getHomePagePath() {
    logger.debug("Resolver is : {}", resolver);
    PageManager pageManager = resolver.adaptTo(PageManager.class);
    logger.debug("Page Manager is : {}", pageManager);
    logger.debug("Current Page is : {}", currentPage);
    Page homePage1 = CoreUtils.getParentPage(currentPage, Constants.HOMEPAGE_LEVEL);
    logger.debug("HomePage is : {}", homePage1);
    if (homePage1 == null) {
      return "";
    }
    logger.debug("HomePagePath is : {}", homePage1.getPath());
    return homePage1.getPath();
  }

  /**
   * Gets the current year.
   *
   * @return the current year
   */
  public int getCurrentYear() {
    Calendar cal = Calendar.getInstance();
    logger.debug("Current Year is : {} ", cal.get(Calendar.YEAR));
    return cal.get(Calendar.YEAR);
  }

  /**
   * Gets the auto complete suggestion.
   *
   * @return the auto complete suggestion
   */
  public String getSearchSuggestJson() {

    logger.debug("Get Search Suggest method invoked !!!");

    logger.debug("Query Params in the Search Suggest Json is : {}", queryParam);

    schoolAndCourseListJson = new ArrayList<>();

    logger.debug("Calling Xpath query ...");
    getQueryString(currentPage.getPath(), Constants.SCHOOL_PAGE_TEMPLATE);
    getQueryString(currentPage.getPath(), Constants.COURSES_PAGE_TEMPLATE);
    logger.debug("Calling Xpath query finished ...");

    String schoolAndCoursesJson = schoolAndCourseListJson.toString();

    logger.debug("Json list for school and courses is : {}", schoolAndCoursesJson);

    return schoolAndCoursesJson;
  }

  private void getQueryString(final String homePagePath, final String templatePath) {
    StringBuilder jcrQueryFilter = new StringBuilder("/jcr:root");
    jcrQueryFilter.append(homePagePath);
    jcrQueryFilter.append("//element(*, ");
    jcrQueryFilter.append("cq:PageContent");
    jcrQueryFilter.append(")");
    jcrQueryFilter.append("[");
    jcrQueryFilter.append("(sling:resourceType='");
    jcrQueryFilter.append(templatePath);
    jcrQueryFilter.append("' and jcr:like(fn:upper-case(");
    jcrQueryFilter.append(Constants.JCR_TITLE);
    jcrQueryFilter.append("), '");
    jcrQueryFilter.append(StringUtils.upperCase(queryParam));
    jcrQueryFilter.append(Constants.PERCENTAGE);
    jcrQueryFilter.append("')");
    jcrQueryFilter.append(")");
    jcrQueryFilter.append("]");

    logger.debug("Xpath query is : {}", jcrQueryFilter);

    Iterator<Resource> resourceIter = resolver.findResources(jcrQueryFilter.toString(), "xpath");

    while (resourceIter.hasNext()) {
      Resource res = resourceIter.next();
      logger.debug("Xpath query findResource is : {}", res.getPath());
      PageManager pageManager = resolver.adaptTo(PageManager.class);
      logger.debug("PageManager is : {}", pageManager);
      String pathToPage = res.getPath().substring(0, res.getPath().indexOf("/jcr:content"));
      Page page = pageManager == null ? null : pageManager.getPage(pathToPage);
      if (page == null) {
        continue;
      }
      JsonObject obj = new JsonObject();
      obj.addProperty("name", page.getTitle());
      obj.addProperty("link", page.getPath() + ".html");
      this.schoolAndCourseListJson.add(obj);
    }
  }
}
