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
    
    $('#login_type_tab a').click(function (e) {
    	  e.preventDefault();
    	  $(this).tab('show');
    });

var doc=document,
    inputs=doc.getElementsByTagName('input'),
    supportPlaceholder='placeholder'in doc.createElement('input'),
    placeholder=function(input){
     var text=input.getAttribute('placeholder'),
     defaultValue=input.defaultValue;
     if(defaultValue==''){
        input.value=text
     }
     input.onfocus=function(){
        if(input.value===text)
        {
            this.value=''
        }
      };
     input.onblur=function(){
        if(input.value===''){
            this.value=text
        }
      }
  };
  
  if(!supportPlaceholder){
     for(var i=0,len=inputs.length;i<len;i++){
          var input=inputs[i],
          text=input.getAttribute('placeholder');
          if(input.type==='text'&&text){
             placeholder(input)
          }
      }
  }


$("#lg-btn").click(function () {
	var username = $("#CustomLoginForm #username").val();
	var password = $("#CustomLoginForm #password").val();
	var pinCode = $("#CustomLoginForm #pinCode").val();
	if (isNull(username) || isNull(password) || isNull(pinCode)) {
		return;
	}
	$("#CustomLoginForm").submit();
});

$("#lg-btn1").click(function () {
	var username = $("#LdapLoginForm #username").val();
	var password = $("#LdapLoginForm #password").val();
	var pinCode = $("#LdapLoginForm #pinCode").val();
	if (isNull(username) || isNull(password) || isNull(pinCode)) {
		return;
	}
	$("#LdapLoginForm").submit();
});

$("#mobile_login_butt").click(function () {
	var username = $("#sms_login_form #username").val();
	var password = $("#sms_login_form #password").val();
	var pinCode = $("#sms_login_form #pinCode").val();
	if (isNull(username) || isNull(password) || isNull(pinCode)) {
		return;
	}
	$("#sms_login_form").submit();
});

$("#send_sms_butt").click(function () {
	var mobile = $("#sms_login_form #username").val();
	if(!isMobile(mobile)){
		alert("请输入正确的手机号码！");
		return;
	}
	$('#sms_pin_code_img').attr("src", contextPath + "/servlet/pinServlet.png?" + Math.random());
	$('#send_sms_panel').modal('show');
});

$("#modal_panel_send_sms_butt").click(function () {
	var mobile = $("#sms_login_form #username").val();
	var pinCode = $("#send_sms_panel #sms_pin_code").val();
	if(pinCode == ""){
		alert("验证码不能为空!");
		return;
	}
	$.ajax({  
        type:'post', 
        url: contextPath + '/user/getsms.do',  
        data:{
        	'phoneNum':mobile,
        	'pinCode':pinCode
        },
        cache:false,  
        dataType:'json',  
        success:function(data){
        	if(data.result == true){
        		alert("发送验证码短信成功!");
        		$('#send_sms_panel').modal('hide');
        	} else {
        		alert(data.msg);
        	}
        },
        error:function(){
        	alert("send sms is error!");
        }
    });
});


function isNull(str) {
	return str == null || str == '';
}


function isMobile(mobile) {
	var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
	if(myreg.test(mobile)){
		return true;
	} else {
		return false;
	}
}

});