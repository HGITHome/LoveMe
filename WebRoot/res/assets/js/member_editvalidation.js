var FormValidation = function () {
    // basic validation
    var handleValidation1 = function () {
        // for more info visit the official plugin documentation: 
        // http://docs.jquery.com/Plugins/Validation

        var form = $('#update_form');
        var error = $('.alert-danger', form);
        var success = $('.alert-success', form);
        jQuery.validator.addMethod("isdate",function (value, element) {
            var matches = /(\d{4})[-](\d{2})[-](\d{2})/;
            return this.optional(element) || (matches.test(value));
        },"请输入格式为yyyy-MM-dd出生日期");
        jQuery.validator.addMethod("isMobile", function(value, element) {  
            var length = value.length;  
            var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;  
            return this.optional(element) || (length == 11 && mobile.test(value));  
        }, "请正确填写您的手机号码");
        form.validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block help-block-error', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            rules: {
                //basic
            	age:{
            		required:false,
            		range:[18,50] 
            	},
               birthday:{
            	   required:false,
            	   date:true,
            	   isdate:true
               },
        
                photo: {
                    required: true,
                    extension: "gif|png|jpg|jpeg|bmp"
                },
                email:{
                	required:true,
                	email:true
                },
                mobile:{
                	required:true,
                	minlength : 11,  
                    isMobile : true  
                }
            },

            messages: { // custom messages for radio buttons and checkboxes
             age:{
            	required:"请输入年龄岁数",
            	age:"请输入正确的年龄岁数"
            },
              birthday:{
            	required:"请输入出生日期",
            	birthday:"请输入格式为yyyy-MM-dd的出生日期"
              },
                photo: {
                    required: "上传文件不允许为空",
                    extension: "文件类型错误，请上传gif,bmp,png,jpg,jpeg文件"
                },
                email: {
                	required:"邮箱不能为空",
                	   email:"请检查电子邮件的格式"
                },
                mobile:{
                	required : "联系方式不能为空",  
                    minlength : "确认手机不能小于11个字符",  
                    isMobile : "请正确填写您的手机号码"
                }
               
            },

       
            errorPlacement: function (error, element) { // render error placement for each input type
                if (element.attr("name") == "sex") { // for uniform radio buttons, insert the after the given container
                    error.insertAfter("#form_sex_error");
                } else {
                    error.insertAfter(element); // for other inputs, just perform default behavior
                }
            },


            invalidHandler: function (event, validator) { //display error alert on form submit
                success.hide();
                error.show();
                App.scrollTo(error, -200);
            },

            highlight: function (element) { // hightlight error inputs
                $(element)
                    .closest('.form-group').removeClass('has-success').addClass('has-error'); // set error class to the control group
            },

            unhighlight: function (element) { // revert the change done by hightlight
                $(element)
                    .closest('.form-group').removeClass('has-error'); // set error class to the control group
            },

            success: function (label) {
                if (label.attr("for") == "sex" || label.attr("for") == "payways[]") { // for checkboxes and radio buttons, no need to show OK icon
                    label
                        .closest('.form-group').removeClass('has-error').addClass('has-success');
                    label.remove(); // remove error label here
                } else { // display success icon for other inputs
                    label
                        .addClass('valid') // mark the current input as valid and display OK icon
                        .closest('.form-group').removeClass('has-error').addClass('has-success'); // set success class to the control group
                }
            },
            submitHandler: function (form) {
                success.show();
                error.hide();
                form[0].submit();
                //add here some ajax code to submit your form or just call form.submit() if you want to submit the form without ajax
            }
        });
        var displayConfirm = function () {
            var record_date = $("#age").val();
            //alert(record_date);
            $("#ageInfo").val(record_date);
            $('#tab4 .form-control-static', form).each(function () {
                var input = $('[name="' + $(this).attr("data-display") + '"]', form);
                if (input.is(":radio")) {
                    input = $('[name="' + $(this).attr("data-display") + '"]:checked', form);
                }

                if (input.is(":text") || input.is("textarea")) {
                    $(this).html(input.val());
                }
                else if (input.is("select")) {
                    $(this).html(input.find('option:selected').text());
                }
                else if (input.is(":radio") && input.is(":checked")) {
                    $(this).html(input.attr("data-title"));
                }
                else if ($(this).attr("data-display") == 'labels[]') {
                    var labels = [];
                    $('[name="labels[]"]:checked', form).each(function () {
                    	labels.push($(this).attr('data-title'));
                    });
                    if ($('#checkboxA1_29').is(':checked') && $("#label_others").val() != null) {
                        labels.push($("#label_others").val());
                    }
                    $(this).html(labels.join("<br>"));
                }
                else if ($(this).attr("data-display") == "characters[]") {
                    var characters = [];
                    $('[name="characters[]"]:checked', form).each(function () {
                    	characters.push($(this).attr('data-title'));
                    });
                    if ($('#checkboxA2_5').is(':checked') && $("#character_others").val() != null) {
                    	characters.push($("#character_others").val());
                    }
                    $(this).html(characters.join("<br>"));
                }
                else if ($(this).attr("data-display") == "sports[]") {
                    var sports = [];
                    $('[name="sports[]"]:checked', form).each(function () {
                    	sports.push($(this).attr('data-title'));
                    });
                    if ($('#checkboxA3_27').is(':checked') && $("#sport_others").val() != null) {
                        sports.push($("#sport_others").val());
                    }
                    $(this).html(sports.join("<br>"));
                }
                else if ($(this).attr("data-display") == "music[]") {
                    var music = [];
                    $('[name="music[]"]:checked', form).each(function () {
                        music.push($(this).attr('data-title'));
                    });
                    if ($('#checkboxA4_11').is(':checked') && $("#music_others").val() != null) {
                        music.push($("#music_others").val());
                    }
                    $(this).html(music.join("<br>"));
                }

                else if ($(this).attr("data-display") == "foods[]") {
                    var foods = [];
                    $('[name="foods[]"]:checked', form).each(function () {
                        foods.push($(this).attr('data-title'));
                    });
                    if ($('#checkboxA5_11').is(':checked') && $("#food_others").val() != null) {
                        foods.push($("#food_others").val());
                    }
                    $(this).html(foods.join("<br>"));
                }

                else if ($(this).attr("data-display") == "films[]") {
                    var films = [];
                    $('[name="films[]"]:checked', form).each(function () {
                        films.push($(this).attr('data-title'));
                    });
                    if ($('#checkboxA6_6').is(':checked') && $("#film_others").val() != null) {
                        films.push($("#film_others").val());
                    }
                    $(this).html(films.join("<br>"));
                }
                
                else if ($(this).attr("data-display") == "books[]") {
                    var books = [];
                    $('[name="books[]"]:checked', form).each(function () {
                        books.push($(this).attr('data-title'));
                    });
                    if ($('#checkboxA7_6').is(':checked') && $("#book_others").val() != null) {
                        books.push($("#book_others").val());
                    }
                    $(this).html(books.join("<br>"));
                }
                
                else if ($(this).attr("data-display") == "travels[]") {
                    var travels = [];
                    $('[name="travels[]"]:checked', form).each(function () {
                    	travels.push($(this).attr('data-title'));
                    });
                    if ($('#checkboxA8_6').is(':checked') && $("#travel_others").val() != null) {
                        travels.push($("#travel_others").val());
                    }
                    $(this).html(travels.join("<br>"));
                }

            });
        };

        var handleTitle = function (tab, navigation, index) {
            var total = navigation.find('li').length;
            var current = index + 1;
            // set wizard title
            $('.step-title', $('#form_wizard_1')).text('Step ' + (index + 1) + ' of ' + total);
            // set done steps
            jQuery('li', $('#form_wizard_1')).removeClass("done");
            var li_list = navigation.find('li');
            for (var i = 0; i < index; i++) {
                jQuery(li_list[i]).addClass("done");
            }

            if (current == 1) {
                $('#form_wizard_1').find('.button-previous').hide();
            } else {
                $('#form_wizard_1').find('.button-previous').show();
            }

            if (current >= total) {
                $('#form_wizard_1').find('.button-next').hide();
                $('#form_wizard_1').find('.button-submit').show();
                displayConfirm();
            } else {
                $('#form_wizard_1').find('.button-next').show();
                $('#form_wizard_1').find('.button-submit').hide();
            }
            App.scrollTo($('.page-title'));
        };

        // default form wizard
        $('#form_wizard_1').bootstrapWizard({
            'nextSelector': '.button-next',
            'previousSelector': '.button-previous',
            onTabClick: function (tab, navigation, index, clickedIndex) {
                return false;

                success.hide();
                error.hide();
                if (form.valid() == false) {
                    return false;
                }

                handleTitle(tab, navigation, clickedIndex);
            },
            onNext: function (tab, navigation, index) {
                success.hide();
                error.hide();

                if (form.valid() == false) {
                    return false;
                }

                handleTitle(tab, navigation, index);
            },
            onPrevious: function (tab, navigation, index) {
                success.hide();
                error.hide();

                handleTitle(tab, navigation, index);
            },
            onTabShow: function (tab, navigation, index) {
                var total = navigation.find('li').length;
                var current = index + 1;
                var $percent = (current / total) * 100;
                $('#form_wizard_1').find('.progress-bar').css({
                    width: $percent + '%'
                });
            }
        });

        $('#form_wizard_1').find('.button-previous').hide();
        $('#form_wizard_1 .button-submit').click(function () {
        }).hide();

    };
    
    
    
    


    return {
        //main function to initiate the module
        init: function () {

            if (!jQuery().bootstrapWizard) {
                return;
            }

            function format(state) {
                if (!state.id) return state.text; // optgroup
                return "<img class='flag' src='../../assets/global/img/flags/" + state.id.toLowerCase() + ".png'/>&nbsp;&nbsp;" + state.text;
            }

            handleValidation1();
          

        }

    };

}();

jQuery(document).ready(function () {
    $('#checkboxA1_29').click(function () {
        if ($('#checkboxA1_29').is(':checked')) {
            // do something
            $("#label_others").attr("disabled", false);
            /*alert(123);
             alert("其他="+$("#income_others").val());*/
            $("#label_others").attr("data-title", "其他个性标签:" + $("#label_others").val());
        }
        else {

            $("#label_others").attr("disabled", true);
        }
    });

    $('#checkboxA2_5').click(function () {
        if ($('#checkboxA2_5').is(':checked')) {
            // do something
            $("#character_others").attr("disabled", false);
            $("#character_others").attr("data-title", "其他性格类型:" + $("#character_others").val());
        }
        else {

            $("#character_others").attr("disabled", true);
        }
    });

    $('#checkboxA3_27').click(function () {
        if ($('#checkboxA3_27').is(':checked')) {
            // do something
            $("#sport_others").attr("disabled", false);
            $("#sport_others").attr("data-title", "其他运动类型:" + $("#sport_others").val());
        }
        else {

            $("#sport_others").attr("disabled", true);
        }
    });

    $('#checkboxA4_11').click(function () {
        if ($('#checkboxA4_11').is(':checked')) {
            // do something

            $("#music_others").attr("disabled", false);
            $("#music_others").attr("data-title", "其他音乐类型:" + $("#music_others").val());
        }
        else {

            $("#music_others").attr("disabled", true);
        }
    });

    $('#checkboxA5_11').click(function () {
        if ($('#checkboxA5_11').is(':checked')) {
            // do something
            $("#food_others").attr("disabled", false);
            $("#food_others").attr("data-title", "其他食物类型:" + $("#food_others").val());
        }
        else {

            $("#food_others").attr("disabled", true);
        }
    });
    
    $('#checkboxA6_6').click(function () {
        if ($('#checkboxA6_6').is(':checked')) {
            // do something
            $("#film_others").attr("disabled", false);
            $("#film_others").attr("data-title", "其他电影类型:" + $("#film_others").val());
        }
        else {

            $("#film_others").attr("disabled", true);
        }
    });
    
    $('#checkboxA7_6').click(function () {
        if ($('#checkboxA7_6').is(':checked')) {
            // do something
            $("#book_others").attr("disabled", false);
            $("#book_others").attr("data-title", "其他书籍类型:" + $("#book_others").val());
        }
        else {

            $("#book_others").attr("disabled", true);
        }
    });
    
    $('#checkboxA8_6').click(function () {
        if ($('#checkboxA8_6').is(':checked')) {
            // do something
            $("#travel_others").attr("disabled", false);
            $("#travel_others").attr("data-title", "其他旅行足迹:" + $("#travel_others").val());
        }
        else {

            $("#book_others").attr("disabled", true);
        }
    });
    FormValidation.init();
});