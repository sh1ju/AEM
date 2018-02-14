"use strict";
use([], function () {
	var props = granite.resource.properties;

    return {
        firstContent:  props["firstcontent"],
        firstColor: props["firstcolor"],
        secondContent: props["secondcontent"],
        secondColor: props["secondcolor"],
        disclaimer: props["disclaimer"],
        scholarshipText: props["scholarshiptetx_t"],
        linkText: props["scholarshiplinktext_t"],
        link: props["scholarshiplink"]
    }
});