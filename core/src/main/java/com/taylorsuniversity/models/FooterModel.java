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
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.taylorsuniversity.models.bean.FooterModelBean;
import com.taylorsuniversity.models.bean.FooterModelLinksBean;
import com.taylorsuniversity.utils.CoreUtils;

/**
 * @author prajput
 *
 */
@SuppressWarnings("deprecation")
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
	private List<FooterModelBean> socialItems = null;
	private List<FooterModelLinksBean> footerLinksPages = null;
	private LinkedHashMap<String, List<FooterModelLinksBean>> footerLinksMap = null;
	private Page page;
	private String footerLinksTitle;
	private String linkName;

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
				JSONObject jsonObjectTitle;
				try {
					jsonObjectTitle = new JSONObject(footerLinksJsonString);
					footerLinksTitle = jsonObjectTitle.getString("footerLinksTitle") != null ? jsonObjectTitle.getString("footerLinksTitle") : "";
					LOGGER.debug("Footer Links title is: : {}", footerLinksTitle);
					
					if (StringUtils.isNotBlank(footerLinksTitle)) {
						if (StringUtils.isNotBlank(jsonObjectTitle.getString("footerLinksPages"))) {
							JSONArray jsonArrayPages = jsonObjectTitle.getJSONArray("footerLinksPages");
							footerLinksPages = new ArrayList<>();
							
							for (int i = 0; i < jsonArrayPages.length(); i++) {
								JSONObject pages = jsonArrayPages.getJSONObject(i);
								LOGGER.debug("Page link is " + pages.getString("linkPath"));
								
								if(CoreUtils.isInternalLink(pages.getString("linkPath"))) {
									page = pageManager.getPage(pages.getString("linkPath"));
									linkName = page.getTitle();
								}
								else {
									linkName = pages.getString("linkPath");
								}
								
								footerLinksPages.add(new FooterModelLinksBean(
										CoreUtils.getQualifiedLink(request.getResourceResolver(), pages.getString("linkPath")),
										linkName));
							}
							footerLinksMap.put(footerLinksTitle, footerLinksPages);
						}
					} else {
						LOGGER.error("Error occured in accesing the foooterlinks");
					}
					
				} catch (JSONException e) {
					LOGGER.error("Exception occured in FooterModel class: " + e.getMessage());
					e.printStackTrace();
				}
				
			}
		}
	}

	/**
	 * Reads the authored social properties and adds to bean
	 * 
	 */
	private void socialProps() {
		
		if (null != social && ArrayUtils.isNotEmpty(social)) {
			socialItems = new ArrayList<>();
			for (String si : social) {
				LOGGER.debug("Social item is: : {}", si);
				try {
					JSONObject jsonObject = new JSONObject(si);
					socialItems.add(new FooterModelBean(
							jsonObject.get("socialIcon").toString(),
							jsonObject.get("socialLink").toString(), 
							jsonObject.get("socialText").toString()));

				} catch (JSONException e) {
					LOGGER.error(e.getMessage());
				}
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
	public List<FooterModelBean> getSocialItems() {
		return socialItems;
	}

	
	/**
	 * @return Map<String, List<FooterModelLinksBean>>
	 */
	public Map<String, List<FooterModelLinksBean>> getFooterLinksMap() {
		return footerLinksMap;
	}
}
