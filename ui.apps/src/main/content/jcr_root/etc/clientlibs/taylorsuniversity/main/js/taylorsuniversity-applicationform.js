$('#malaysian-levelOfStudy').change(function() {

    $("#malaysian-programmes").removeAttr("disabled");

    $('#malaysian-programmes').children('option').hide();

    $("#malaysian-programmes :selected").removeAttr("selected");

    var levelOfStudy = $('#malaysian-levelOfStudy').val();

    $( "option[name='malaysian-" + levelOfStudy + "']" ).show();

    if($( "option[name='malaysian-" + levelOfStudy + "']" ).size() > 0) {
		$( "option[name='malaysian-" + levelOfStudy + "']:first" ).attr("selected", "selected");
        $("#malaysian-programmes").val($("option[name='malaysian-" + levelOfStudy + "']:first").val());

    } else {
        $('#malaysian-programmes').children('option').hide();
        $("#malaysian-programmes :selected").removeAttr("selected");
        $("#malaysian-programmes").prop('disabled', 'disabled');
    }

});



$(window).on("load", function () {

    $("#malaysian-programmes").removeAttr("disabled");

	$('#malaysian-programmes').children('option').hide();

    $("#malaysian-programmes :selected").removeAttr("selected");

    var levelOfStudy = $('#malaysian-levelOfStudy').val();

    $( "option[name='malaysian-" + levelOfStudy + "']" ).show();

    if($( "option[name='malaysian-" + levelOfStudy + "']" ).size() > 0) {
		$( "option[name='malaysian-" + levelOfStudy + "']:first" ).attr("selected", "selected");
        $("#malaysian-programmes").val($("option[name='malaysian-" + levelOfStudy + "']:first").val());
    } else {
        $('#malaysian-programmes').children('option').hide();
        $("#malaysian-programmes :selected").removeAttr("selected");
        $("#malaysian-programmes").prop('disabled', 'disabled');
    }
});



$('#international-levelOfStudy').change(function() {

    $("#international-programmes").removeAttr("disabled");

    $('#international-programmes').children('option').hide();

    $("#international-programmes :selected").removeAttr("selected");

    var levelOfStudy = $('#international-levelOfStudy').val();

    $( "option[name='international-" + levelOfStudy + "']" ).show();

    if($( "option[name='international-" + levelOfStudy + "']" ).size() > 0) {
		$( "option[name='international-" + levelOfStudy + "']:first" ).attr("selected", "selected");
        $("#international-programmes").val($("option[name='international-" + levelOfStudy + "']:first").val());
    } else {
        $('#international-programmes').children('option').hide();
        $("#international-programmes :selected").removeAttr("selected");
        $("#international-programmes").prop('disabled', 'disabled');
    }

});



$(window).on("load", function () {

    $("#international-programmes").removeAttr("disabled");

	$('#international-programmes').children('option').hide();

    $("#international-programmes :selected").removeAttr("selected");

    var levelOfStudy = $('#international-levelOfStudy').val();

    $( "option[name='international-" + levelOfStudy + "']" ).show();

    if($( "option[name='international-" + levelOfStudy + "']" ).size() > 0) {
		$( "option[name='international-" + levelOfStudy + "']:first" ).attr("selected", "selected");
        $("#international-programmes").val($("option[name='international-" + levelOfStudy + "']:first").val());
    } else {
        $('#international-programmes').children('option').hide();
        $("#international-programmes :selected").removeAttr("selected");
        $("#international-programmes").prop('disabled', 'disabled');
    }
});


var tabContainerId = '#tab-container-2';
$(tabContainerId + '.t-tab-container .t-tabs > .tab-item').on('click', function(e) {
    var container = $(this).closest('.t-tab-container');
    $('.t-tabs > .tab-item', container).removeClass('active');
    $(this).addClass('active');
    var tab = $(this).data('tab') || '';


    $('.t-tab-content > .t-tab-content-item', container).removeClass('active');
    $('.t-tab-content > .t-tab-content-item[data-tab-content="'+ tab +'"]', container).addClass('active');

});


$('#malaysian_email').on('input', function() {
	var input=$(this);
	var re = /^[+a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
	var is_email=re.test(input.val());
	if(is_email) {
		input.removeClass("invalid").addClass("valid");
	}
	else {
		input.removeClass("valid").addClass("invalid");
	}
});

$(function() {
    //initial
    $('.t-custom-checkbox:not(.checked) + [type="checkbox"]').prop('checked', false);
    $('.t-custom-checkbox.checked + [type="checkbox"]').prop('checked', true);
    //event handle
    $('.t-custom-checkbox').on('click', function() {
        if ($(this).hasClass('checked')) {
            $(this).removeClass('checked');
            $(this).next('[type="checkbox"]').prop('checked', false);
        } else {
            $(this).addClass('checked');
            $(this).next('[type="checkbox"]').prop('checked', true);
        }
    });
});