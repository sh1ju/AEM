$('input[name="email"]').on('change', function() {    
	var input=$(this);
	var pattern = /^[+a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
   	//return pattern.test(input.val());
   	var is_email=pattern.test(input.val());
   	console.log(is_email);
   	if(is_email) {
   		input.removeClass("invalid").addClass("valid");
   	}
	else {
		input.removeClass("valid").addClass("invalid");
	}
});
