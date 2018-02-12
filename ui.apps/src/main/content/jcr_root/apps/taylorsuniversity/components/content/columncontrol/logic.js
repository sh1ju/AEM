"use strict";
use([], function () {
	var props = granite.resource.properties;
    var col1 = "";
    var col2 = "";

    var isSingleColumn = false;
    if(props["columns"]!=null){
        if (props["columns"]== 1) {
            isSingleColumn = true;
        }else{
            var colcol = props["columns"].split("\\|");  
            col1 = colcol[0];
            col2 = colcol[1];
    
            isSingleColumn = false;
        }
    }

    props["columns"] == 1? isSingleColumn = true: '';
    return {
        isSingleColumn:  isSingleColumn,
        col1: "col-"+col1,
        col2: "col-"+col2
    }
});