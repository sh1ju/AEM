$('#malaysian-levelOfStudy').change(function() {

    $('#malaysian-programmes').children('option').hide();

    $("#malaysian-programmes :selected").removeAttr("selected");

    var levelOfStudy = $('#malaysian-levelOfStudy').val();

    $( "option[name='malaysian-" + levelOfStudy + "']" ).show();

    if($( "option[name='malaysian-" + levelOfStudy + "']" ).size() > 1) {
		$( "option[name='malaysian-" + levelOfStudy + "']:first" ).attr("selected", "selected");
    } else {
        $( "option[name='malaysian-Select Programme']" ).show();
		$( "option[name='malaysian-Select Programme']" ).attr("selected", "selected");
    }

});



$(window).on("load", function () {

	$('#malaysian-programmes').children('option').hide();

    var levelOfStudy = $('#malaysian-levelOfStudy').val();

    $( "option[name='malaysian-" + levelOfStudy + "']" ).show();

    if($( "option[name='malaysian-" + levelOfStudy + "']" ).size() > 1) {
		$( "option[name='malaysian-" + levelOfStudy + "']:first" ).attr("selected", "selected");
    } else {
        $( "option[name='malaysian-Select Programme']" ).show();
		$( "option[name='malaysian-Select Programme']" ).attr("selected", "selected");
    }
});



$('#international-levelOfStudy').change(function() {

    $('#international-programmes').children('option').hide();

    $("#international-programmes :selected").removeAttr("selected");

    var levelOfStudy = $('#international-levelOfStudy').val();

    $( "option[name='international-" + levelOfStudy + "']" ).show();

    if($( "option[name='international-" + levelOfStudy + "']" ).size() > 1) {
		$( "option[name='international-" + levelOfStudy + "']:first" ).attr("selected", "selected");
    } else {
        $( "option[name='international-Select Programme']" ).show();
		$( "option[name='international-Select Programme']" ).attr("selected", "selected");
    }

});



$(window).on("load", function () {

	$('#international-programmes').children('option').hide();

    var levelOfStudy = $('#international-levelOfStudy').val();

    $( "option[name='international-" + levelOfStudy + "']" ).show();

    if($( "option[name='international-" + levelOfStudy + "']" ).size() > 1) {
		$( "option[name='international-" + levelOfStudy + "']:first" ).attr("selected", "selected");
    } else {
        $( "option[name='international-Select Programme']" ).show();
		$( "option[name='international-Select Programme']" ).attr("selected", "selected");
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