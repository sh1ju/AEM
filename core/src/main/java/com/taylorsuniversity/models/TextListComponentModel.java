/* Copyright Taylors University */

package com.taylorsuniversity.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taylorsuniversity.core.beans.TouchMultiCompositeFieldBean;
import com.taylorsuniversity.models.bean.TextListColumnFieldBean;
import com.taylorsuniversity.models.bean.TextListModelBean;


/**
 * This Model class helps in retrieving the attributes for accordion component.
 *
 */
@Model(adaptables = Resource.class)
public class TextListComponentModel {

	 /** The Constant LOG. */
    public static final Logger LOGGER = LoggerFactory.getLogger(TextListComponentModel.class);
    private List<TextListModelBean> textListModelBeanList = new ArrayList<>();    
    private List<TextListColumnFieldBean> col1 = new ArrayList<>();
	private List<TextListColumnFieldBean> col2 = new ArrayList<>();
	private List<TextListColumnFieldBean> col3 = new ArrayList<>();
 
    /**
     * The init method
     */
    @PostConstruct
    protected void init() {
        
    	LOGGER.error("In TextListComponentModel init method");
    	getTextListItems();
    }    

    @Inject @Optional
    private Resource list;
    
    @Inject @Optional
    private String columns;
    
    @Inject @Optional
    private int mobiledefaultshow;
    
    private void getTextListItems() {	
    	LOGGER.debug("In getTextListItems()");
		try{			
			if (list != null)
			{
				int column2And3Bullet = 0;
				int column1Bullet = 0;
				int listNodeCount = 0;				
				
				Iterator<Resource> listChildren = list.listChildren();
				while(listChildren.hasNext()){
					listNodeCount++;	
					TextListModelBean modelBean = listChildren.next().adaptTo(TextListModelBean.class);
					if(modelBean != null){
						textListModelBeanList.add(modelBean);
					}else{
						LOGGER.debug("modelBean is null"); 
					}
				}
				if (columns != null){
					column2And3Bullet = listNodeCount/Integer.parseInt(columns);
					column1Bullet = (listNodeCount % Integer.parseInt(columns))+column2And3Bullet;	
					LOGGER.error("column2And3Bullet = "+ column2And3Bullet +"; columns = "+columns+"; column1Bullet = "+column1Bullet);
					if(columns.equalsIgnoreCase("2")){
						for (int i = 0; i < column1Bullet; i++) 
						{
							LOGGER.debug("In 1st column of 2 columns text list population.");
							String hiddenStyle="";
							if (mobiledefaultshow > 0) {
								if(i>=mobiledefaultshow){
									hiddenStyle= "showmore-content hidden-mobile";
								}else{
									LOGGER.debug("Else"); 
								}
							}else{
								LOGGER.debug("Else"); 
							}
							TextListColumnFieldBean lists = new TextListColumnFieldBean(textListModelBeanList.get(i).getTitle_t(),
									textListModelBeanList.get(i).getText_t(),hiddenStyle); 
							col1.add(lists);
						}
						for (int i = column1Bullet; i < listNodeCount; i++) 
						{  
							LOGGER.debug("In 2nd column of 2 columns text list population.");
							String hiddenStyle="";
							if (mobiledefaultshow > 0) {
								if(i>=mobiledefaultshow){
									hiddenStyle= "showmore-content hidden-mobile";
								}else{
									LOGGER.debug("Else"); 
								}
							}else{
								LOGGER.debug("Else"); 
							}
							TextListColumnFieldBean lists = new TextListColumnFieldBean(textListModelBeanList.get(i).getTitle_t(),
									textListModelBeanList.get(i).getText_t(),hiddenStyle); 
							col2.add(lists);
						}
					}
					if(columns.equalsIgnoreCase("3")){
						for (int i = 0; i < column1Bullet; i++) 
						{ 
							LOGGER.debug("In 1st column of 3 columns text list population.");
							String hiddenStyle="";
							if (mobiledefaultshow > 0) {
								if(i>=mobiledefaultshow){
									hiddenStyle= "showmore-content hidden-mobile";
								}else{
									LOGGER.debug("Else"); 
								}
							}else{
								LOGGER.debug("Else"); 
							}
							TextListColumnFieldBean lists = new TextListColumnFieldBean(textListModelBeanList.get(i).getTitle_t(),
									textListModelBeanList.get(i).getText_t(),hiddenStyle); 	
							col1.add(lists);
						}
						for (int i = column1Bullet; i < (column1Bullet+column2And3Bullet); i++) 
						{  
							LOGGER.debug("In 2nd column of 3 columns text list population.");
							String hiddenStyle="";
							if (mobiledefaultshow > 0) {
								if(i>=mobiledefaultshow){
									hiddenStyle="showmore-content hidden-mobile";
								}else{
									LOGGER.debug("Else"); 
								}
							}else{
								LOGGER.debug("Else"); 
							}
							TextListColumnFieldBean lists = new TextListColumnFieldBean(textListModelBeanList.get(i).getTitle_t(),
									textListModelBeanList.get(i).getText_t(),hiddenStyle); 	
							col2.add(lists);						
						}
						for (int i = (column1Bullet+column2And3Bullet); i < listNodeCount; i++) 
						{
							LOGGER.debug("In 3rd column of 3 columns text list population."); 
							String hiddenStyle="";
							if (mobiledefaultshow > 0) {
								if(i>=mobiledefaultshow){
									hiddenStyle="showmore-content hidden-mobile";
								}else{
									LOGGER.debug("Else"); 
								}
							}else{
								LOGGER.debug("Else"); 
							}
							TextListColumnFieldBean lists = new TextListColumnFieldBean(textListModelBeanList.get(i).getTitle_t(),
									textListModelBeanList.get(i).getText_t(),hiddenStyle); 													
							col3.add(lists);							
						}
					}		
				}
		    } 
		}catch(Exception e){ 
			LOGGER.error("Exception while getTextListItems data {}", e.getMessage(), e); 
		} 
    }
    
    public List<TextListColumnFieldBean> getCol1() {
		return col1;
	}
	public List<TextListColumnFieldBean> getCol2() {
		return col2;
	}
	public List<TextListColumnFieldBean> getCol3() {
		return col3;
	}
	
}