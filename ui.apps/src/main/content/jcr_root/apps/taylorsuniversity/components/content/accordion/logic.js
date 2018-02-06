"use strict";
use(function (MarginUtils, utils){

	var props = granite.resource.properties;
   // var data = {};

   // data.children = props["listItems"].get("listItems", var{});
    var test = props["listItems"].split(":");
    //.then(function(res){
    //    return res.nativeResource.listChildren();
    //    }).fail(function(error) {
    //    return [{
    //        title:'No list items set'
    //    }];
    //});     
	//String[] value = props.get("listItems", new String[] {});
     //data.children.each(function (i, fieldSet) {
     //               test = data.children.find("[accordiontitles_t]");
     //}
        return {test: test[0]}
});