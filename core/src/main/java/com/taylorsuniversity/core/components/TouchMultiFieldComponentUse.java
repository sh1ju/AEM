package com.taylorsuniversity.core.components;

import java.util.ArrayList; 
import java.util.List;   

import org.apache.sling.commons.json.JSONObject; 
import org.slf4j.Logger; 
import org.slf4j.LoggerFactory;
import com.taylorsuniversity.core.beans.*;

import com.adobe.cq.sightly.WCMUsePojo;

public class TouchMultiFieldComponentUse extends WCMUsePojo{
	@SuppressWarnings("deprecation") 
	private static final Logger LOG = LoggerFactory.getLogger(TouchMultiFieldComponentUse.class);
	private List<TouchMultiFieldBean> submenuItems = new ArrayList<>();
	
	@Override
	public void activate() throws Exception {
		setMultiFieldItems();		
	}
	private List setMultiFieldItems() {	
		JSONObject jObj; 
		try{ 
			String[] itemsProps = getProperties().get("listItems", String[].class); 
			
			if (itemsProps != null)
			{ 
				for (int i = 0; i < itemsProps.length; i++) 
				{   
					jObj = new JSONObject(itemsProps[i]); 
					TouchMultiFieldBean menuItem = new TouchMultiFieldBean();   
					String title = jObj.getString("accordiontitles_t"); 
					LOG.error("title IS: "+ title); 
					menuItem.setTitle(title); 					
					submenuItems.add(menuItem); 
					} 
				} 
			}catch(Exception e){ 
				LOG.error("Exception while Multifield data {}", e.getMessage(), e); 
				} 
		return submenuItems; 
		}   
	
	public List getMultiFieldItems() { 
		return submenuItems; 
		}	
}
