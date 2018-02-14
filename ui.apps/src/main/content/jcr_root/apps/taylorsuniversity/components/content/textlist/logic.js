"use strict";

/**
 * Text foundation component JS backing script
 */
use(['../../../widgets/utils/utils.js'], function (Utils) {
    var props = granite.resource.properties;
    var colStyle = "";
    var isViewMore = false;
    var hash = Utils.getHashCode(granite.resource.path).toString();

	if(props["columns"]!=null){
    	if (props["columns"]== 2) {
			colStyle = "6";
        }else if(props["columns"]== 3) {
			colStyle = "4";
        }else{
            colStyle = "12";
        }
    }

	props["mobiledefaultshow"] > 0? isViewMore = true: false;
    return {
        colStyle: colStyle,
        hideBullets: props["hidebullets"],
        mobileShow: props["mobiledefaultshow"],
        viewMore: isViewMore,
        id: 'tl-'+hash
    }

});
