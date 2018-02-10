"use strict";
use(['/apps/hlb/widgets/utils/utils.js'],function(utils){
    return {
        html:utils.getSVG(this.pngPath,this.iconSize,this.title,this.linkURL,this.desc,this.cssClass,this.alt,this.target,this.tabindex,this.fill)
    };
});