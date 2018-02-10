$(document).on("dialog-ready", function() {    
    var pathReadonly = function() {        
    	$(".path-readonly").find(".coral-Textfield").each(function() {                
            $(this).attr("readonly", "readonly");            
        });    
    }    
    pathReadonly();
});

$(document).on("dialog-ready", function () {
	$(".coral-Button").click(function() {
		var field = $(this).parent();    
        setTimeout(function() {
            field.find(".coral-Textfield").each(function() {
                $(this).attr("readonly", "readonly");
            });
        }, 250);
	});
});