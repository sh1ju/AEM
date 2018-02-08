$(document).on("dialog-ready", function() {    
    var pathReadonly = function() {        
        setTimeout(function() {            
            $(".path-readonly").find(".coral-Textfield").each(function() {                
            $(this).attr("readonly", "readonly");            
        });        
        }, 250);    
        }    
    pathReadonly();
});