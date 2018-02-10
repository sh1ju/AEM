"use strict";
use(['/apps/hlb-my/widgets/utils/utils.js'],function(utils){

    var text = this.text;

    text = utils.stripHtml(text);
    
    return {
        text:text
    };
});