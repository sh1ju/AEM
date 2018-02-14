$('#malaysian-levelOfStudy').change(function() {

    $('#malaysian-programmes').children('option').hide();

    $("#malaysian-programmes :selected").removeAttr("selected");

    var levelOfStudy = $('#malaysian-levelOfStudy').val();

    $( "option[name='" + levelOfStudy + "']" ).show();

    if($( "option[name='" + levelOfStudy + "']" ).size() > 1) {
		$( "option[name='" + levelOfStudy + "']:first" ).attr("selected", "selected");
    } else {
        $( "option[name='Select Programme']" ).show();
		$( "option[name='Select Programme']" ).attr("selected", "selected");
    }

});



$(window).on("load", function () {

	$('#malaysian-programmes').children('option').hide();

    var levelOfStudy = $('#malaysian-levelOfStudy').val();

    $( "option[name='" + levelOfStudy + "']" ).show();

    if($( "option[name='" + levelOfStudy + "']" ).size() > 1) {
		$( "option[name='" + levelOfStudy + "']:first" ).attr("selected", "selected");
    } else {
        $( "option[name='Select Programme']" ).show();
		$( "option[name='Select Programme']" ).attr("selected", "selected");
    }
});