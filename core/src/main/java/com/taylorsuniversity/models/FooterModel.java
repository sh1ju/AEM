package com.taylorsuniversity.models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;

import com.google.gson.Gson;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.taylorsuniversity.models.bean.FooterModelSocialBean;
import com.taylorsuniversity.constants.Constants;
import com.taylorsuniversity.models.bean.FooterModelBean;
import com.taylorsuniversity.models.bean.FooterModelLinkPathBean;
import com.taylorsuniversity.models.bean.FooterModelLinksBean;
import com.taylorsuniversity.utils.CoreUtils;

/**
 * This class is used to retrieve the properties configured 
 * in the footer component of the home-page template.
 */
@Model(adaptables = SlingHttpServletRequest.class)
public class FooterModel {
	Logger LOGGER = LoggerFactory.getLogger(FooterModel.class);

	/** The Resolver. */
	@Self
	private SlingHttpServletRequest request;
	
	@Inject
	private PageManager pageManager;
	@Inject
	private Page currentPage;

	/*
	 * Initializing variable to be used
	 */
	private String[] footerLinks;
	private String[] social;
	private List<FooterModelSocialBean> socialItems = null;
	private List<FooterModelBean> footerLinksPages = null;
	private LinkedHashMap<String, List<FooterModelBean>> footerLinksMap = null;
	private String linkUrlPath = StringUtils.EMPTY;
	private String linkName = StringUtils.EMPTY;
	private String link = StringUtils.EMPTY;
	private Page rootPage;

	@PostConstruct
	protected void init() {
		rootPage = CoreUtils.getParentPage(currentPage, Constants.HOMEPAGE_LEVEL);
		LOGGER.debug("RootPage value is: {} "+ rootPage);
		footerLinkProps();
		socialProps();
	}

	/**
	 * Reads the authored footer links properties and adds to bean
	 * 
	 */
	private void footerLinkProps() {
						
		footerLinks = rootPage.getContentResource("footer").getValueMap().get("footerLinks",String[].class);
		LOGGER.debug("FooterLinks value is: {} " + footerLinks);
	
		if (null != footerLinks && ArrayUtils.isNotEmpty(footerLinks)) {
			
			this.footerLinksMap = new LinkedHashMap<>();
			for (String footerLinksJsonString : footerLinks) {
				
				Gson gson = new Gson();
				FooterModelLinksBean fmb = gson.fromJson(footerLinksJsonString, FooterModelLinksBean.class);
				LOGGER.debug("Footer model bean is : {}" + fmb);
				
				if (StringUtils.isNotBlank(fmb.getFooterLinksTitle())) {
					
					LOGGER.debug("Footer model bean title is : {}" + fmb.getFooterLinksTitle());
					List<FooterModelLinkPathBean> pages = fmb.getFooterLinksPages();
					footerLinksPages = new ArrayList<>();
					
					if (null != pages && ArrayUtils.isNotEmpty(pages.toArray())) {
						
						for (FooterModelLinkPathBean li: pages) {
							
							if(StringUtils.isNotBlank(li.getLinkPath())) {
								
								link = li.getLinkPath();
								LOGGER.debug("Link is : {}" + link);
								
								if(CoreUtils.isInternalLink(link) && (null != pageManager.getPage(link))) {
									linkName = pageManager.getPage(link).getTitle();
								}
								else {
									linkName = link;
								}
								LOGGER.debug("Link name is : {}" + linkName);
								
								linkUrlPath = CoreUtils.getQualifiedLink(request.getResourceResolver(), link);
								LOGGER.debug("Link Url Path is : {}" + linkUrlPath);
							}
							footerLinksPages.add(new FooterModelBean(linkUrlPath, linkName));
							LOGGER.debug("Link Url Path is : {}" + linkUrlPath);
						}
					}
					footerLinksMap.put(fmb.getFooterLinksTitle(), footerLinksPages );
				}	
			}
		}
	}

	/**
	 * Reads the authored social properties and adds to bean
	 * 
	 */
	private void socialProps() {
		social = rootPage.getContentResource("footer").getValueMap().get("social",String[].class);
		LOGGER.debug("Social value is: {} " + social);
		
		Gson gson = new Gson();
		socialItems = new ArrayList<>();
		
		if (null != social && ArrayUtils.isNotEmpty(social)) {
			for(String si : social) {
				LOGGER.debug("Social item is: : {}", si);
				FooterModelSocialBean fmb = gson.fromJson(si, FooterModelSocialBean.class);
				socialItems.add(fmb);
			}
		}
	}

	/**
	 * Gets the current year.
	 *
	 * @return the current year
	 **/
	public int getCurrentYear() {
		Calendar cal = Calendar.getInstance();
		LOGGER.debug("Current Year is : {} ", cal.get(Calendar.YEAR));
		return cal.get(Calendar.YEAR);
	}
	
	/**
	 * @return List<FooterModelBean>
	 */
	public List<FooterModelSocialBean> getSocialItems() {
		LOGGER.debug("Socail Links list being sent is: : {}", socialItems);
		return socialItems;
	}
	
	/**
	 * @return Map<String, List<FooterModelLinksBean>>
	 */
	public Map<String, List<FooterModelBean>> getFooterLinksMap() {
		LOGGER.debug("Footer Links Map being sent is: : {}", footerLinksMap);
		return footerLinksMap;
	}
}
