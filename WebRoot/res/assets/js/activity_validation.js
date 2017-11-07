var FormValidation = function () {

    // basic validation
    var handleValidation1 = function() {
        // for more info visit the official plugin documentation: 
        // http://docs.jquery.com/Plugins/Validation

        var form1 = $('#add_activity');
        var error1 = $('.alert-danger', form1);
        var success1 = $('.alert-success', form1);

        form1.validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block help-block-error', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",  // validate all fields including form hidden input
            messages: {

            	queryActivityName:{
                    remote:"活动名称已存在"
                }
            },
            rules: {
            	queryActivityName: {
                    required: true,
                    maxlength:20,
                    remote:{
                        type:"POST",
                        url:"v_checkActivityName.do",
                        data:{
                            veryCode:function(){return $("#queryActivityName").val();}
                        }
                    }
                },
                queryAddress: {
                    required: true,
                    maxlength:32
                }
            },

            invalidHandler: function (event, validator) { //display error alert on form submit
                success1.hide();
                error1.show();
                App.scrollTo(error1, -200);
            },

            highlight: function (element) { // hightlight error inputs
                $(element)
                    .closest('.form.group').addClass('has-error'); // set error class to the control member.group
            },

            unhighlight: function (element) { // revert the change done by hightlight
                $(element)
                    .closest('.form.group').removeClass('has-error'); // set error class to the control member.group
            },

            success: function (label) {
                label
                    .closest('.form.group').removeClass('has-error'); // set success class to the control member.group
            },

            submitHandler: function (form) {
                success1.show();
                error1.hide();
                form[0].submit(); // submit the form
            }
        });


    };
    // basic validation
    var handleValidation2 = function() {
        // for more info visit the official plugin documentation:
        // http://docs.jquery.com/Plugins/Validation

        var form1 = $('#edit_activity');
        var error1 = $('.alert-danger', form1);
        var success1 = $('.alert-success', form1);

        form1.validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block help-block-error', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",  // validate all fields including form hidden input
            messages: {


            },
            rules: {
            	queryActivityName: {
                    required: true,
                    maxlength:20,
                },
                queryAddress: {
                    required: true,
                    maxlength:32
                }
            },

            invalidHandler: function (event, validator) { //display error alert on form submit
                success1.hide();
                error1.show();
                App.scrollTo(error1, -200);
            },

            highlight: function (element) { // hightlight error inputs
                $(element)
                    .closest('.form-member.group').addClass('has-error'); // set error class to the control member.group
            },

            unhighlight: function (element) { // revert the change done by hightlight
                $(element)
                    .closest('.form-member.group').removeClass('has-error'); // set error class to the control member.group
            },

            success: function (label) {
                label
                    .closest('.form-member.group').removeClass('has-error'); // set success class to the control member.group
            },

            submitHandler: function (form) {
                success1.show();
                error1.hide();
                form[0].submit(); // submit the form
            }
        });


    };




    return {
        //main function to initiate the module
        init: function () {
            handleValidation1();
            handleValidation2();
        }

    };

}();

jQuery(document).ready(function() {
    FormValidation.init();
});