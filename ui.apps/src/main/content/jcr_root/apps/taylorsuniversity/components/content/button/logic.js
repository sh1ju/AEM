"use strict";

use(['/apps/taylorsuniversity/widgets/utils/site.js'], function (site) {
	var props = granite.resource.properties;

    var data = {};
    data.isLocal = false;

    data.text = props["text"] || "Click Here";
	data.alignment = props["alignment"] || "pull-left";
    data.link = props["link"]; 

    if(data.link != null && (data.link.indexOf('/') === 0 || data.link.indexOf('#') === 0)) {
        data.isLocal = true;
    }

    if (data.link && data.isLocal){
        data.site = site.getLinkURL(data.link,data.target,false);
    }else if (data.link && !data.isLocal){
        data.site = props["link"];
        data.target = '_new';
    }

    return data;

});
