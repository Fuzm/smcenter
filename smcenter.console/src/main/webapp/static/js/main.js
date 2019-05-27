$(document).ready(function() {
	$("#company").removeClass('active');
	$("#person").click(function(event) {
		$("#c-person").show();
		$("#person").addClass('current');
		$("#company").removeClass('current');
		$("#J_Verify").hide();
	});
	$("#company").click(function(event) {
		$("#c-person").hide();
		$("#J_Verify").show();
		$("#company").addClass('current').removeClass('active');
		$("#person").removeClass('current');
	});

	$('#login_type_tab a').click(function(event) {
		event.preventDefault();
		$(this).tab('show');
	});

	var inputs = document.getElementsByTagName('input'), supportPlaceholder = 'placeholder' in document.createElement('input'), placeholder = function(input) {
		var text = input.getAttribute('placeholder'), defaultValue = input.defaultValue;
		if (defaultValue == '') {
			input.value = text
		}
		input.onfocus = function() {
			if (input.value === text) {
				this.value = ''
			}
		};
		input.onblur = function() {
			if (input.value === '') {
				this.value = text
			}
		}
	};

	if (!supportPlaceholder) {
		for (var i = 0, len = inputs.length; i < len; i++) {
			var input = inputs[i], text = input.getAttribute('placeholder');
			if (input.type === 'text' && text) {
				placeholder(input)
			}
		}
	}

	$("#mobile_login").click(function() {
		var username = $("#mobile_form #username").val();
		var password = $("#mobile_form #password").val();
		var pinCode = $("#mobile_form #pinCode").val();
		if (isNull(username) || isNull(password) || isNull(pinCode)) {
			return;
		}
		
		var enableCookie = isCookieEnable();
		if(!enableCookie){
			$("#mobilecookieFlag").val("0");
		}
		$("#mobile_form").submit();
	});

	$("#user_login").click(function() {
		var username = $("#user_form #username").val();
		var password = $("#user_form #password").val();
		var pinCode = $("#user_form #pinCode").val();
		if (isNull(username) || isNull(password) || isNull(pinCode)) {
			return;
		}
		
		var enableCookie = isCookieEnable();
		if(!enableCookie){
			$("#usercookieFlag").val("0");
		}
		
		$("#user_form").submit();
	});

	$("#mip_login").click(function() {
		var username = $("#mip_form #username").val();
		var password = $("#mip_form #password").val();
		var pinCode = $("#mip_form #pinCode").val();
		if (isNull(username) || isNull(password) || isNull(pinCode)) {
			return;
		}
		
		var enableCookie = isCookieEnable();
		if(!enableCookie){
			$("#cookieFlag").val("0");
		}
		$("#mip_form").submit();
	});

	$("#send_sms").click(function() {
		var mobile = $("#mobile_form #username").val();
		if (!isMobile(mobile)) {
			alert("请输入正确的手机号码！");
			return;
		}
		var mobile = $("#mobile_form #username").val();
		$.ajax({
			type : 'post',
			url : contextPath + '/user/getsms.do',
			data : {
				'phoneNum' : mobile
			},
			cache : false,
			dataType : 'json',
			success : function(data) {
				if (data.result == true) {
					alert("发送验证码短信成功!");
					$('#send_sms_panel').modal('hide');
				} else {
					alert(data.msg);
				}
			},
			error : function() {
				alert("send sms is error!");
			}
		});
	});

	function isNull(str) {
		return str == null || str == '';
	}

	function isMobile(mobile) {
		var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
		if (myreg.test(mobile)) {
			return true;
		} else {
			return false;
		}
	}

});