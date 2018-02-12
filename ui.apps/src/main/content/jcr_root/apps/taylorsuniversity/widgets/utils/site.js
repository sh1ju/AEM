"use strict";
use(function(){
    var getSiteCode = function(path) {
            var pages = getPages(path);
            var code = "";

        	if(pages.length >= 3)
                code = replaceAllHyphen(pages[2]);

        	return code;
        },
        getCountryCode = function(path) {
            var pages = getPages(path);
            var code = "";

        	if(pages.length >= 4)
                code = replaceAllHyphen(pages[3]);

        	return code;
        },
        getAccurateCountryCode = function(path) {
            var pages = getPages(path);
            var code = "";

            if(pages.length >= 4)
                code = replaceAllHyphen(pages[3]);
            return code;
        },
        getLanguageCode = function(path){
            var pages = getPages(path);

        	if(pages.length >= 5)
                return pages[4];
//                return replaceAllHyphen(pages[3]);
        	return "";
        },
        getCategoryCode = function(path){
            var pages = getPages(path);

        	if(pages.length >= 6)
                return replaceAllHyphen(pages[5]);
        	return "";
        },
        getProductId = function(path){
            var pages = getPages(path);

        	if(pages.length >= 7)
                return replaceAllHyphen(pages[6]);
        	return "";
        },
        replaceAllHyphen = function(str) {
            return str;
//            return str.split("-").join("_");
        },
        getLinkURL = function(str, linkTarget, isNullable) {
            if(typeof isNullable == 'undefined') isNullable = false;
            var linkURL = (str != null && str != "") ? str : (isNullable ? null : "#");
                                                var isModal = (linkTarget && linkTarget.indexOf('modal') > -1);

            if(linkURL != null && str && !isModal && str.indexOf("/") == 0
                && ((typeof str == 'string' && (str.lastIndexOf(".") < (str.length - 5) || str.lastIndexOf(".") >= (str.length - 1)))
                || (typeof str == 'object' && (str.lastIndexOf(".") < (str.length() - 5) || str.lastIndexOf(".") >= (str.length() - 1)))
                )){
                    if(linkURL.indexOf('#')>-1||linkURL.indexOf('?')>-1){
						return linkURL;
                    }else{
                		linkURL += ".html";
                    }
                }
            return linkURL;
        };

    function getPages(path) {
        var curPath = path;
        if(!curPath)
            curPath = currentPage.getPath();

        var pages = curPath.split("/");
        return pages;
    }

    return {
        getSiteCode: getSiteCode,
        getCountryCode: getCountryCode,
        getAccurateCountryCode: getAccurateCountryCode,
        getLanguageCode: getLanguageCode,
        getCategoryCode: getCategoryCode,
        getProductId: getProductId,
        replaceAllHyphen: replaceAllHyphen,
        getLinkURL: getLinkURL
    };
});