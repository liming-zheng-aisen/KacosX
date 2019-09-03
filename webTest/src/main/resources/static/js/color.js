/* -------------------------------------------------------------
	Color Trigger JS
     ------------------------------------------------------------- */
jQuery(document).ready(function ($) {


    var $els = $(".primary-colors li").click(function (e) {
        if (!$(this).hasClass("active")) {
            // if other menus are open remove open class and add closed
            $els.not(this).filter('.active').removeClass("active").addClass("closed");
        }
        $(this).toggleClass("active").removeClass('closed');
    });



    $('.col-trigger').on('click', function(){
       $('.color-picker-wrap').toggleClass('visible-color-wrap');
    });

    $('.primary-colors li.red').on('click', function(){
      $('body').addClass('red-color').removeClass('green-color blue-color yellow-color');
    });

    $('.primary-colors li.green').on('click', function(){
      $('body').addClass('green-color').removeClass('red-color blue-color yellow-color');
    });

    $('.primary-colors li.blue').on('click', function(){
      $('body').addClass('blue-color').removeClass('red-color green-color yellow-color');
    });

    $('.primary-colors li.yellow').on('click', function(){
      $('body').addClass('yellow-color').removeClass('red-color blue-color green-color');
    });


	//Secondary Color
	$('.secondary-color li.red').on('click', function(){
	  $('body').addClass('s-color-one').removeClass('s-color-two');
	});

	$('.secondary-color li.green').on('click', function(){
	  $('body').addClass('s-color-two').removeClass('s-color-one');
	});

});
