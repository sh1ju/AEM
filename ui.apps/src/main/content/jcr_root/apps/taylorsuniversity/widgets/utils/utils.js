var SlingSettingsService = Packages.org.apache.sling.settings.SlingSettingsService;
var Jsoup = Packages.org.jsoup.Jsoup;

"use strict";
use(function(){

    var hashCode = function(s){
          return s.split("").reduce(function(a,b){a=((a<<5)-a)+b.charCodeAt(0);return a&a},0).toString().replace('-','');
        }
    return {
        getHashCode: hashCode
    };
});