//
// CROUSEL CODE
//
//init listener can only assign before the slick initialization
 $(document).ready(function(){
   
     var carouselID = $('#carouselType').val() === 'quote'?'#t-carousel-1':'#t-carousel-2';
        
     $(carouselID + ' .t-carousel').on('init', function(event, slick) {
         var indicator = $('.t-carousel-controls .indicator', $(this).parent());
        indicator.html('1/' + slick.$slides.length);
        console.log(slick);
        if (slick.$slides.length <= 1) {
            console.log($('.t-carousel-controls', $(this).parent()));
            $('.t-carousel-controls', $(this).parent()).attr("style", "display: none !important");
        }
     });
     $(carouselID + ' .t-carousel').slick({
        autoplay: true,
        autoplaySpeed: 4000,
        cssEase: 'cubic-bezier(0.4, 0, 0.2, 1)'
     });

     $(carouselID + ' .t-carousel').on('afterChange', function(event, slick, currentSlide, nextSlide) {
        var indicator = $('.t-carousel-controls .indicator');
        indicator.html((currentSlide + 1) + '/' + slick.$slides.length);
     });
     $(carouselID + ' .t-carousel-controls .next').on('click', function() {
        $(this).closest('.t-carousel-container').find('.t-carousel').slick('slickNext');
     });
     $(carouselID + ' .t-carousel-controls .prev').on('click', function() {
        $(this).closest('.t-carousel-container').find('.t-carousel').slick('slickPrev');
     });
});
