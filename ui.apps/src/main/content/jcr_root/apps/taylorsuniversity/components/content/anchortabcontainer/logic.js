"use strict";

use([], function () {

    var data = {};
	var childproperties = new Array();
    granite.resource.resolve(granite.resource.path+'/anchorpar').then(function(res){
        data.path = res.path;
		data.children = res.nativeResource.listChildren();
        data.length = res.nativeResource.listChildren().length;
        for(var i = 0;i<res.nativeResource.listChildren().length;i++) {
            if(res.nativeResource.listChildren()[i].properties['title_t']){
	        	childproperties.push(res.nativeResource.listChildren()[i].properties['title_t']);
            }
        }
    }); 
	data.childproperties = childproperties;
	return data;
});
