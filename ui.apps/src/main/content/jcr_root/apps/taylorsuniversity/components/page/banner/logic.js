"use strict";
use(function (MarginUtils, utils) {
	var props = granite.resource.properties;
    var isPageTitle = false;

	if (new String(props["title_t"]).length == 0) {
		isPageTitle = true;
    }else{
        isPageTitle = false;
    }
    var xt = props["title_t"];

    return { 
        isPageTitle: isPageTitle,
        titlelength : xt
    }
});
