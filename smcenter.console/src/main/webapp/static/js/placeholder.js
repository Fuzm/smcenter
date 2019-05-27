(function ($) {
  $.support.placeholder = ('placeholder' in document.createElement('input'));
  if (!$.support.placeholder) {
    $("[placeholder]").addClass('placeholder');
    $("[placeholder]").focus(function () {
      if ($(this).val() == $(this).attr("placeholder")) {
        $(this).val("");
      }
    }).blur(function () {
      if ($(this).val() == "") {
        $(this).val($(this).attr("placeholder")).addClass('placeholder');
      }
    }).blur();

    $("[placeholder]").parents("form").submit(function () {
       $(this).find('[placeholder]').each(function() {
           if ($(this).val() == $(this).attr("placeholder")) {
               $(this).val("");
           }
       });
    });
 }
})(jQuery)