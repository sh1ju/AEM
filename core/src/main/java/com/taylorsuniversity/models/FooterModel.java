package com.taylorsuniversity.models;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;

import com.taylorsuniversity.models.bean.FooterModelBean;
import com.taylorsuniversity.models.bean.FooterModelLinksBean;
import com.taylorsuniversity.utils.CoreUtils;

@Model(adaptables = Resource.class)
public class FooterModel {
	Logger LOGGER = LoggerFactory.getLogger(FooterModelBean.class);

	/** The Resolver. */
	@Inject
	private ResourceResolver resolver;
	
	@Inject
	@Optional
	private String[] social;
	
	@Inject
	@Optional
	private String[] footerLinks;

	private List<FooterModelBean> socialItems = null;
	
	String footerLinksTitle;
	List<FooterModelLinksBean> footerLinksPages;
	private LinkedHashMap<String, List<FooterModelLinksBean>> footerLinksMap = null;
	

	@PostConstruct
	protected void init() {

		socialProps();
		footerLinkProps();
	}

	private void footerLinkProps() {
		LOGGER.info("links" + footerLinks);
		if (null != footerLinks) {
			this.footerLinksMap = new LinkedHashMap<String, List<FooterModelLinksBean>>();
			for (String footerLinksJsonString : footerLinks) {
				JSONObject jsonObjectTitle;
				try {
					jsonObjectTitle = new JSONObject(footerLinksJsonString);
					footerLinksTitle = jsonObjectTitle.getString("footerLinksTitle") != null ? jsonObjectTitle.getString("footerLinksTitle") : "";
					if (StringUtils.isNotEmpty(footerLinksTitle)) {
						if (StringUtils.isNotEmpty(jsonObjectTitle.getString("footerLinksPages"))) {
							JSONArray jsonArrayPages = jsonObjectTitle.getJSONArray("footerLinksPages");
							footerLinksPages = new ArrayList<>();
							for (int i = 0; i < jsonArrayPages.length(); i++) {
								JSONObject points = jsonArrayPages.getJSONObject(i);

										
								footerLinksPages.add(new FooterModelLinksBean(
										CoreUtils.getQualifiedLink(resolver, points.getString("linkPath"))));
							}
							footerLinksMap.put(footerLinksTitle, footerLinksPages);
						}
					} else {
						LOGGER.info("Paragraph map");
					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		
	}

	private void socialProps() {
		if (ArrayUtils.isNotEmpty(social)) {
			socialItems = new ArrayList<FooterModelBean>();
			for (String si : social) {
				try {
					JSONObject jsonObject = new JSONObject(si);
					socialItems.add(new FooterModelBean(jsonObject.get("socialIcon").toString(),
							jsonObject.get("socialLink").toString(), jsonObject.get("socialText").toString()));

				} catch (JSONException e) {
					LOGGER.error(e.getMessage());
				}
			}
		}
	}

	public List<FooterModelBean> getSocialItems() {
		return socialItems;
	}

	public Map<String, List<FooterModelLinksBean>> getFooterLinksMap() {
		return footerLinksMap;
	}
}
