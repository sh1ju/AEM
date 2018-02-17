$(function() {
    $('.t-accordion').accordion({
        transitionSpeed: 400
        /*singleOpen: false,*/
    });

    $('.t-accordion [data-control]').trigger('accordion.refresh');
});

