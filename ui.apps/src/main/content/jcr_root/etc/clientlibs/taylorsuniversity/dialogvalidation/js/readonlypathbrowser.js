$(document).on("dialog-ready", function() {    
    var pathReadonly = function() {        
    	$(".path-readonly").find(".coral-InputGroup-input").each(function() {                
            $(this).attr("readonly", "readonly");            
        });    
    }    
    pathReadonly();
});



$(document).on("dialog-ready", function () {
	$(".js-coral-Multifield-list").click(function() {
		var pathReadonly = function() {        
            $(".path-readonly").find(".coral-InputGroup-input").each(function() {                
                $(this).attr("readonly", "readonly");            
            });    
        }    
        pathReadonly();
	});
});



$(document).on("dialog-ready", function () {
	$(".coral-Button").click(function() {
		var field = $(this).parent();    
        setTimeout(function() {
            field.find(".path-readonly").each(function() {
                $(this).find(".coral-Textfield").each(function() {
                    $(this).attr("readonly", "readonly");
                });
            });
        }, 250);
	});
});