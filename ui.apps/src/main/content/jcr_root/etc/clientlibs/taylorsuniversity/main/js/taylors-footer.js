$(window).on('back-to-top', function() {
        $('html,body').animate({ scrollTop: 0 }, "slow", 'swing');
});

var triggerBackToTop = 50;
$(window).on('scroll',function(e) {
    var scrollTop = window.pageYOffset || document.documentElement.scrollTop;
    if (triggerBackToTop < scrollTop) {
        $('.back-to-top-btn').show();
    } else {
        $('.back-to-top-btn').hide();
    }
});
