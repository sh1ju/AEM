package com.taylorsuniversity.core.components;

import java.util.ArrayList; 
import java.util.List;   

import org.apache.sling.commons.json.JSONObject; 
import org.slf4j.Logger; 
import org.slf4j.LoggerFactory;

import com.taylorsuniversity.core.beans.*;
import com.adobe.cq.sightly.WCMUsePojo;

public class TouchMultiCompositeFieldComponentUse extends WCMUsePojo{
	@SuppressWarnings("deprecation") 
	private static final Logger LOG = LoggerFactory.getLogger(TouchMultiCompositeFieldComponentUse.class);
		
	private List<TouchMultiCompositeFieldBean> col1 = new ArrayList<>();
	private List<TouchMultiCompositeFieldBean> col2 = new ArrayList<>();
	private List<TouchMultiCompositeFieldBean> col3 = new ArrayList<>();
	
	public List<TouchMultiCompositeFieldBean> getCol1() {
		return col1;
	}
	public List<TouchMultiCompositeFieldBean> getCol2() {
		return col2;
	}
	public List<TouchMultiCompositeFieldBean> getCol3() {
		return col3;
	}
	
	@Override
	public void activate() throws Exception {
		setMultiCompositeFieldItems();		
	}
	private void setMultiCompositeFieldItems() {	
		JSONObject jObj; 
		try{ 
			String[] listProps = getProperties().get("list", String[].class); 
			String noOfColumns = getProperties().get("columns", String.class); 
			int mobileView = getProperties().get("mobiledefaultshow", Integer.class); 
			
			if (listProps != null)
			{
				int column2And3Bullet = 0;
				int column1Bullet = 0;
				if (noOfColumns != null){
					column2And3Bullet = listProps.length/Integer.parseInt(noOfColumns);
					column1Bullet = (listProps.length % Integer.parseInt(noOfColumns))+column2And3Bullet;					
					if(noOfColumns.equalsIgnoreCase("2")){
						for (int i = 0; i < column1Bullet; i++) 
						{  
							jObj = new JSONObject(listProps[i]);
							TouchMultiCompositeFieldBean lists = new TouchMultiCompositeFieldBean(); 
							TextListColumnsBean textList = new TextListColumnsBean(); 
							String title = jObj.getString("title_t"); 
							String text = jObj.getString("text_t"); 
							if (mobileView > 0) {
								if(i>=mobileView){
									lists.setMobileCSS("showmore-content hidden-mobile");
								}
							}
							lists.setTitle(title); 	
							lists.setText(text);
							col1.add(lists);
						}
						for (int i = column1Bullet; i < listProps.length; i++) 
						{  
							jObj = new JSONObject(listProps[i]);
							TouchMultiCompositeFieldBean lists = new TouchMultiCompositeFieldBean(); 
							TextListColumnsBean textList = new TextListColumnsBean(); 
							String title = jObj.getString("title_t"); 
							String text = jObj.getString("text_t"); 
							if (mobileView > 0) {
								if(i>=mobileView){
									lists.setMobileCSS("showmore-content hidden-mobile");
								}
							}
							lists.setTitle(title); 	
							lists.setText(text);
							col2.add(lists);
						}
					}
					if(noOfColumns.equalsIgnoreCase("3")){
						for (int i = 0; i < column1Bullet; i++) 
						{  
							jObj = new JSONObject(listProps[i]);
							TouchMultiCompositeFieldBean lists = new TouchMultiCompositeFieldBean(); 
							TextListColumnsBean textList = new TextListColumnsBean(); 
							String title = jObj.getString("title_t"); 
							String text = jObj.getString("text_t"); 
							if (mobileView > 0) {
								if(i>=mobileView){
									lists.setMobileCSS("showmore-content hidden-mobile");
								}
							}
							lists.setTitle(title); 	
							lists.setText(text);
							col1.add(lists);
						}
						for (int i = column1Bullet; i < (column1Bullet+column2And3Bullet); i++) 
						{  
							jObj = new JSONObject(listProps[i]);
							TouchMultiCompositeFieldBean lists = new TouchMultiCompositeFieldBean(); 
							TextListColumnsBean textList = new TextListColumnsBean(); 
							String title = jObj.getString("title_t"); 
							String text = jObj.getString("text_t"); 
							if (mobileView > 0) {
								if(i>=mobileView){
									lists.setMobileCSS("showmore-content hidden-mobile");
								}
							}
							lists.setTitle(title); 	
							lists.setText(text);
							col2.add(lists);							
						}
						for (int i = (column1Bullet+column2And3Bullet); i < listProps.length; i++) 
						{  
							jObj = new JSONObject(listProps[i]);
							TouchMultiCompositeFieldBean lists = new TouchMultiCompositeFieldBean(); 
							TextListColumnsBean textList = new TextListColumnsBean(); 
							String title = jObj.getString("title_t"); 
							String text = jObj.getString("text_t"); 
							if (mobileView > 0) {
								if(i>=mobileView){
									lists.setMobileCSS("showmore-content hidden-mobile");
								}
							}
							lists.setTitle(title); 	
							lists.setText(text);
							col3.add(lists);							
						}
					}		
				}
		    } 
		}catch(Exception e){ 
				LOG.error("Exception while Multifield data {}", e.getMessage(), e); 
				} 
		
		LOG.info("col1:"+col1);
		
		}   
	

}
