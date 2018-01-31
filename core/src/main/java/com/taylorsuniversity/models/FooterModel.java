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
	@Optional
	@Via("resource")
	private String[] social;
	
	@Inject
	@Optional
	@Via("resource")
	private String[] footerLinks;

	/*
	 * Initializing variable to be used
	 */
	private List<FooterModelSocialBean> socialItems = null;
	private List<FooterModelBean> footerLinksPages = null;
	private LinkedHashMap<String, List<FooterModelBean>> footerLinksMap = null;
	private Page page;
	private String footerLinksTitle;
	private String linkTitle;
	private String linkUrlPath = "";
	private String linkName = "";
	private String link = "";

	@PostConstruct
	protected void init() {
		socialProps();
		footerLinkProps();
	}

	
	/**
	 * Reads the authored footer links properties and adds to bean
	 * 
	 */
	private void footerLinkProps() {
		
		if (null != footerLinks && ArrayUtils.isNotEmpty(footerLinks)) {
			
			this.footerLinksMap = new LinkedHashMap<>();
			for (String footerLinksJsonString : footerLinks) {
				
				Gson gson = new Gson();
				FooterModelLinksBean fmb = gson.fromJson(footerLinksJsonString, FooterModelLinksBean.class);
				LOGGER.info("Footer model bean is : {}" + fmb);
				
				if (StringUtils.isNotBlank(fmb.getFooterLinksTitle())) {
					
					LOGGER.info("Footer model bean title is : {}" + fmb.getFooterLinksTitle());
					List<FooterModelLinkPathBean> pages = fmb.getFooterLinksPages();
					footerLinksPages = new ArrayList<>();
					if (null != pages && ArrayUtils.isNotEmpty(pages.toArray())) {
						
						for (FooterModelLinkPathBean li: pages) {
							if(StringUtils.isNotBlank(li.getLinkPath())) {
								
								link = li.getLinkPath();
								LOGGER.info("Link is : {}" + link);
								
								if(CoreUtils.isInternalLink(link)) {
									linkName = pageManager.getPage(link).getTitle();
								}
								else {
									linkName = link;
								}
								
								LOGGER.info("Link name is : {}" + linkName);
								
								linkUrlPath = CoreUtils.getQualifiedLink(request.getResourceResolver(), link);
								LOGGER.info("Link Url Path is : {}" + linkUrlPath);
								
							}
							
							footerLinksPages.add(new FooterModelBean(linkUrlPath, linkName));
							LOGGER.info("Link Url Path is : {}" + linkUrlPath);
							
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
		
		Gson gson = new Gson();
		socialItems = new ArrayList<>();
		
		if (null != social && ArrayUtils.isNotEmpty(social)) {
			for(String si : social) {
				LOGGER.info("Social item is: : {}", si);
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
		return socialItems;
	}

	
	/**
	 * @return Map<String, List<FooterModelLinksBean>>
	 */
	public Map<String, List<FooterModelBean>> getFooterLinksMap() {
		LOGGER.info("Footer Links Map being sent is: : {}", footerLinksMap);
		return footerLinksMap;
	}
}
