/* -------------------------------------------------------------
	Btheme main js file
	Author: Btheme
	Version:1.0
     ------------------------------------------------------------- */
(function ($) {
    "use strict";

    jQuery(document).ready(function ($) {

        //Search Box Trigger
        $('.search-trigger').on('click', function () {
            $('.search-form').toggleClass('visible-search-form');
        });

        //Project Layout{Masonry}
        if ($.fn.masonry) {
            $('.project-wrapper').masonry({
                itemSelector: '.sin-project',
                columnWidth: 1
            });
        }

        //Clients Reviews Slider{owlCarousel}
        if ($.fn.owlCarousel) {
            $('.customer-stament-wrapper').owlCarousel({
                items: 2,
                nav: true,
                margin: 30,
                navText: ['<i class="icofont icofont-long-arrow-left"></i>', '<i class="icofont icofont-long-arrow-right"></i>'],
                smartSpeed: 600,
                responsive: {
                    992: {
                        items: 2
                    },
                    768: {
                        items: 1
                    },
                    320: {
                        items: 1
                    }
                }
            });
        }

        //Services Gallery{owlCarousel}
        if ($.fn.owlCarousel) {
            $('.service-details-gallery-wrap').owlCarousel({
                items: 2,
                nav: true,
                margin: 30,
                navText: ['<i class="icofont icofont-long-arrow-left"></i>', '<i class="icofont icofont-long-arrow-right"></i>'],
                smartSpeed: 600,
                responsive: {
                    992: {
                        items: 2
                    },
                    768: {
                        items: 1
                    },
                    320: {
                        items: 1
                    }
                }
            });
        }

        //Video Play Trigger
        $('.video-play-btn').on('click', function (ev) {
            $('.video-play-btn').fadeOut();
            $('.video-cover-img').css('opacity', '0');
            $("#video").css('z-index', '99999');
            $("#video")[0].src += "&autoplay=1";
            ev.preventDefault();
        });


        //Calculation hero area height
        var windowHeight = $(window).height();
        var windowWidth = $(window).width();
        var objh = $('.header-height');
        if (windowWidth <= 768) {
            objh.css('height', '750px');
        } else {
            objh.css('height', windowHeight);
        }

        //Menu Configuration
        var windowWid = $(window).width();
        if (windowWid < 768) {
            $('.nav.navbar-nav li > ul').hide();
            $('.nav.navbar-nav li > ul > li').children('ul').css('display', 'block');
            $('.nav.navbar-nav li').on('click', function () {
                $(this).children('ul').slideToggle();
            });
        }

        //Hero area slider with animation{owl Carousel}
        var HeroSlide = $('.slider-wrapper');
        HeroSlide.owlCarousel({
            items: 1,
            autoplay: true,
            singleItem: true,
            animateIn: 'fadeIn',
            animateOut: 'fadeOut'
        });

        HeroSlide.on('translate.owl.carousel', function () {
            $(this).find('.owl-item .item .slide-content h1').removeClass('slideInRight animated').css('opacity', '0');
        });
        HeroSlide.on('translated.owl.carousel', function () {
            $(this).find('.owl-item.active .item .slide-content h1').addClass('slideInRight animated').css('opacity', '1');
        });

        HeroSlide.on('translate.owl.carousel', function () {
            $(this).find('.owl-item .item .slide-content p').delay(2000).removeClass('fadeInUp animated').css('opacity', '0');
        });
        HeroSlide.on('translated.owl.carousel', function () {
            $(this).find('.owl-item.active .item .slide-content p').delay(2000).addClass('fadeInUp animated').css('opacity', '1');
        });

        HeroSlide.on('translate.owl.carousel', function () {
            $(this).find('.owl-item .item .slide-content a').delay(2000).removeClass('fadeInUp animated').css('opacity', '0');
        });
        HeroSlide.on('translated.owl.carousel', function () {
            $(this).find('.owl-item.active .item .slide-content a').delay(2000).addClass('fadeInUp animated').css('opacity', '1');
        });




        //Btheme Custom Accordion
        var dd = $('dd');
        dd.filter(':nth-child(n+4)').hide();

        //STYLE THREE AND FOUR
        $('dl.accordion.style3').on('click', 'dt', function () {
            $(this)
                .toggleClass('active')
                .siblings('dt')
                .removeClass('active');

            $(this)
                .next()
                .slideToggle(100)
                .siblings('dd')
                .slideUp(200);

        });


        //Btheme Section Animation
        if ($.fn.waypoint) {

            $('.a_fu').css('opacity', '0');
            $('.a_fu').waypoint(function () {
                $(this).addClass('fadeInUp');
                $('.a_fu.fadeInUp').css({
                    opacity: 1
                });
            }, {
                offset: '95%'
            });



            $('.a_f').css('opacity', '0');
            $('.a_f').waypoint(function () {
                $(this).addClass('fadeIn');
                $('.a_f.fadeIn').css({
                    opacity: 1
                });
            }, {
                offset: '95%'
            });
        }

    });

    //Site Preloader
    $(window).on('load', function () {
        $('.preloader-wrapper').fadeOut('slow', function () {
            $(this).remove();
        });
    });

}(jQuery));