"use strict";

/**
 * Text foundation component JS backing script
 */
use(["/libs/wcm/foundation/components/utils/AuthoringUtils.js",
     "/apps/hlb/components/structure/common/datasources/margin/MarginUtils.js", 
     "/apps/hlb/widgets/colorpicker/server_side_script/ColorPickerUtil.js", 
     "/apps/hlb/widgets/utils/site.js",
     "/apps/hlb/widgets/utils/utils.js"], function (AuthoringUtils, MarginUtils, ColorPickerUtil, SiteUtil, utils) {
    
    var CONST = {
        PROP_TEXT: "text",
        PROP_RICH_FORMAT: "textIsRich",
        CONTEXT_TEXT: "text",
        CONTEXT_HTML: "html",
        PROP_FONT_COLOR: "fontColor",
        COLOR_MAPPING: "/cq:dialog/content/items/styling/items/column/items/font-color/mappings"
    };

    var props = granite.resource.properties;

    var text = {};
    text.id = "text-id"+utils.getHashCode(granite.resource.path);

    // The actual text content
    text.text = granite.resource.properties[CONST.PROP_TEXT]
            || "";
    text.isEmpty = ((typeof text.text.length) == 'function' ? text.text.length():0);
    
    // Wether the text contains HTML or not
    text.context = granite.resource.properties[CONST.PROP_RICH_FORMAT]
            ? CONST.CONTEXT_HTML : CONST.CONTEXT_TEXT

    // Set up placeholder if empty
    if (!text.text) {
        text.cssClass = AuthoringUtils.isTouch
                ? "cq-placeholder"
                : "cq-text-placeholder-ipe";
        text.context = CONST.CONTEXT_TEXT;

        // only dysplay placeholder in edit mode
        if (typeof wcmmode != "undefined" && wcmmode.isEdit()) {
            text.text = AuthoringUtils.isTouch
            ? ""
            : "Edit text";
        } else {
            text.text = "";
        }
    }
    

    text.marginCss = MarginUtils.mgnCls.join(" ");    
    text.fontSize = props["fontSize"];
    
    // Adding the constants to the exposed API
    text.CONST = CONST;
    
    var fontColor = props[CONST.PROP_FONT_COLOR];
    text.fontColor = ColorPickerUtil.getFontColor(component.getPath() + CONST.COLOR_MAPPING, fontColor);


    //var doc = org.jsoup.Jsoup.parseBodyFragment(text.text);
    //text.text = doc.body().html();

	function parseList(_list) {
	    if(_list.size() == 0) return new Array();

	    var keywords = new Array();
        for(var i=0;i<_list.size();i++) {
            var item = _list.get(i);
            var obj = {};
            obj.string = item.attributes().get("data-string");
            obj.target = item.attributes().get("data-target");

            keywords.push(obj);
        }

        var tooltipURL = "/content/" + SiteUtil.getAccurateCountryCode() + "/" + SiteUtil.getLanguageCode() + "/tools/tooltips";

        var result = granite.resource.resolve(tooltipURL).then(function(res){
            var list = new Array();
            for(var i = 0;i<res.nativeResource.listChildren().length;i++) {
                list.push(granite.resource.resolve(res.nativeResource.listChildren()[i].path + "/jcr:content").then(function(res){
                    for(var j=0;j<keywords.length;j++) {
                        if(res.properties["jcr:title"].equalsIgnoreCase(keywords[j].string)) {
                            var obj = {};
                            obj.dataId = keywords[j].target;
                            obj.message = res.properties["jcr:description"];

                            return obj;
                        }
                    }
                    return null;
                }).fail(function(error){
                    return null;
                }));
            }
            return list;
        }).fail(function(error) {
            return [{
                title:error.message
            }];
        });
/*
        for(var i=0;i<list.size();i++) {
            var item = list.get(i);
            var obj = {};
            obj.dataId = item.attributes().get("data-target");

            obj.message = granite.resource.resolve(tooltipURL+"/"+item.attributes().get("data-string")).then(function(res){
                return res.properties["tooltipvalue"]!=null ? res.properties["tooltipvalue"] : "";
            }).fail(function(error) {
                return "";
            });

            result.push(obj);
        }
*/
        return result;
    }

    //var list = doc.select("a[role=button][data-toggle=popover]");
    //text.list = parseList(list);

    text.isTouchAuthoring = AuthoringUtils.isTouch;
    
    return text;
    
});
