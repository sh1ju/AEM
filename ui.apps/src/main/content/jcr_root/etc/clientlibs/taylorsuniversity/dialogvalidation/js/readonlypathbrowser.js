$(document).on("dialog-ready", function() {    
    var pathReadonly = function() {        
    	$(".path-readonly").find(".coral-Textfield").each(function() {                
            $(this).attr("readonly", "readonly");            
        });    
    }    
    pathReadonly();
});

$(document).on("click", ".coral-Button", function (e) {    
    var pathReadonly = function() {        
    	$(".path-readonly").find(".coral-Textfield").each(function() {                
            $(this).attr("readonly", "readonly");            
        });    
    }    
    pathReadonly();
});