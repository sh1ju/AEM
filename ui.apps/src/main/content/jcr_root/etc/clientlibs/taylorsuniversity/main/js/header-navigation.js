$(function() {
	$('.t-nav-dropdown > button').on(
			'click',
			function() {
				if ($(this).closest('.t-nav-dropdown').hasClass('active')) {
					$('.t-nav-dropdown').removeClass('active');
					$(this).find('i').removeClass('fa-angle-up').addClass(
							'fa-angle-down');

				} else {
					$('.t-nav-dropdown').removeClass('active');
					$(this).closest('.t-nav-dropdown').addClass('active');
					$('.t-nav-dropdown i.fa-angle-up').removeClass(
							'fa-angle-up').addClass('fa-angle-down');
					$(this).find('i').removeClass('fa-angle-down').addClass(
							'fa-angle-up');

				}
			});

	$('.t-nav-sub-dropdown > button').on(
			'click',
			function() {
				if ($(this).closest('.t-nav-sub-dropdown').hasClass('active')) {
					$('.t-nav-sub-dropdown').removeClass('active');
					$(this).find('i.fa-angle-up').removeClass('fa-angle-up')
							.addClass('fa-angle-down');
					$(this).find('i.fa-angle-left')
							.removeClass('fa-angle-left').addClass(
									'fa-angle-right');

				} else {
					$('.t-nav-sub-dropdown').removeClass('active');
					$(this).closest('.t-nav-sub-dropdown').addClass('active');
					$('.t-nav-sub-dropdown i.fa-angle-up').removeClass(
							'fa-angle-up').addClass('fa-angle-down');
					$('.t-nav-sub-dropdown i.fa-angle-left').removeClass(
							'fa-angle-left').addClass('fa-angle-right');
					$(this).find('i.fa-angle-down')
							.removeClass('fa-angle-down').addClass(
									'fa-angle-up');
					$(this).find('i.fa-angle-right').removeClass(
							'fa-angle-right').addClass('fa-angle-left');

				}
			});

	$(document)
			.on(
					'click',
					function(e) {
						var taraget = $('.t-nav-dropdown')
						if (!taraget.is(e.target)
								&& taraget.has(e.target).length === 0) {
							$('.t-nav-dropdown').removeClass('active');
							$('.t-nav-dropdown i.fa-angle-up').removeClass(
									'fa-angle-up').addClass('fa-angle-down');
						}
					});

	var triggerStickyNav = 119;
	var triggerMobileNav = 5;
	$(window).on(
			'scroll',
			function(e) {
				var scrollTop = window.pageYOffset
						|| document.documentElement.scrollTop;
				if (window.matchMedia("(min-width: 992px)").matches
						&& triggerStickyNav < scrollTop) {
					$('.sticky-nav').addClass('docked');
					$('.sticky-left-buttons').addClass('minimized')
				} else {
					$('.sticky-nav.docked').removeClass('docked');
					$('.sticky-left-buttons').removeClass('minimized')
				}

				if (window.matchMedia("(max-width: 992px)").matches
						&& triggerMobileNav < scrollTop) {
					$('.mobile-transparent.mobile-nav').addClass('t-nav-red');
				} else {
					$('.mobile-transparent.mobile-nav')
							.removeClass('t-nav-red');
				}
			});

	$('.action-btn').on('click', function(e) {
		var action = $(this).data('action');
		if (action) {
			$(window).trigger(action);
		}
	})

	$(window).on('mobilemenu', function(e) {
		$('.t-nav').show();
	});
	$(window).on('mobilemenu-close', function(e) {
		$('.t-nav').hide();
	});
});