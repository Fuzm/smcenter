var InterValObj; //timer变量，控制时间
	var count = 60; //要求时间
	var curCount;	 //当前剩余秒数
	$(document).ready(function(){
		
		
		
		
		
		$(".nav-tabs a").click(function(){
				$("#username").val("");
				$("#password").val("");
				$("#verifycode").val("");
				$("#pinCode").val("");
				
				 $(this).addClass("active");
				 $(this).siblings().removeClass();
			
				if($(this).text()=='手机'){
					 $("#tag_pwd").hide();
					  $("#tag_verify_code").show();
					 $("#username").attr('placeholder','手机号');
					 $("#verifycode").attr('placeholder','短信码');
					 $("#userType").val("3")
				}else if($(this).text()=='用户名'){
					 $("#tag_pwd").show();
					 $("#tag_verify_code").hide();			
					 $("#username").attr('placeholder','用户名');
					 $("#password").attr('placeholder','密码');
					 $("#userType").val("2") 
				}else{
					 $("#tag_pwd").show();
					 $("#tag_verify_code").hide();
					 $("#username").attr("placeholder","MIP帐号");
					 $("#password").attr('placeholder','MIP密码');
					 $("#userType").val("1")
				}
			});
		
		//刷新验证码
		 $(".verify-img").click(function() {
			 this.src=$("#contextPath").val()+'/servlet/pinServlet.png?t='+Math.random();
		 }); 
		 
		//发送手机
		 $("#send_sms").click(function() {
			 var txt=$("#send_sms").text();
			 if(txt.indexOf("秒内输入")>0){
				 return;
			 }

			var mobile = $("#username").val();
				if (!isMobile(mobile)) {
				$("#username").focus();
				alertify.alert("请输入正确的手机号码");
				return;
			}  
			$.ajax({
					type : 'post',
					url : $("#contextPath").val() + '/user/getsms.do',
					data : {
						'phoneNum' : mobile
					},
					cache : false,
					dataType : 'json',
					success : function(data) {
						if (data.result == true) {
							curCount=count;
							$("#send_sms").text(curCount + "秒内输入");
							InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
							alertify.alert("发送验证码短信成功");
						} else {
							alertify.alert("data.msg");
						}
					},
					error : function() {
						alertify.alert("发送验证码异常，请刷新重试...");
					}
				});	 
		  });

		 	
		 	$(document).keydown(function(event){ 
		 		 if(event.keyCode == "13")    
	            {
	            	$('#login').trigger("click");
	            }
		 	}); 
		 	

		  	$("#login").click(function() {
				
				var username=$("#username").val();
				var pwd=$("#password").val();
				var verifycode=$("#verifycode").val();
				var pincode=$("#pinCode").val();
				if(isNullOrEmpty(username)){
					$("#username").focus();
					alertify.alert("请输入"+$("#username").attr('placeholder'));
					return false;
				}

				if($("#userType").val()=='3'){
					if(isNullOrEmpty(verifycode)){
						$("#verifycode").focus();
						alertify.alert("请输入"+$("#verifycode").attr('placeholder'));
						return false;
					}
					var verifycode=$("#verifycode").val();
					$("#password").val(verifycode);	//特殊处理，手机号登录时，将短信验证码赋值给password，后台是根据password取的值
				}else{
					
					if(isNullOrEmpty(pwd)){
						$("#password").focus();
						alertify.alert("请输入"+$("#password").attr('placeholder'));
						return false;
					}
					
				}
				if(isNullOrEmpty(pincode)){
						$("#pinCode").focus();
						alertify.alert("请输入"+$("#pinCode").attr('placeholder'));
						return false;
				}
				
			
				
				var ajaxCallUrl=$('#user_form').attr('action');
				
				$.ajax({
	                cache: true,
	                type: "POST",
	                url:ajaxCallUrl,
	                data:$('#user_form').serialize(),// 你的formid
	                async: false,
	                error: function(request) {
	                	alertify.alert("Connection error");
	                },
	                success: function(data) {
	            		
	                	//var obj=JSON.parse(data);
	                	if(data.isSuccess){ 
	                		
	                		var oamid = data.data.oamid;
		            		var mipCookieUrl = data.data.mipCookieUrl;
		            		var setMipCookieUrl = mipCookieUrl + encodeURIComponent(oamid);
		            		$.ajax({  
		            	        type:'GET',      
		            	        url: setMipCookieUrl,
		            	        async: false,
		            	        data:'',  
		            	        cache:false,  
		            	        dataType:'jsonp',
		            	        jsonp: 'callback',
		            	        success:function(data){
		            	        	
		            	        },
		            	        error: function(XMLHttpRequest, textStatus, errorThrown) {
		            	        	
		            	        }
		            	    });
		            		
		            		var mipssotoken = data.data.mipssotoken;
		            		var mipssotokenurl =data.data.mipSsoTokenUrl;
		            		var setMipssotokenurl = mipssotokenurl + encodeURIComponent(mipssotoken);
		            		$.ajax({  
		            	        type:'POST',      
		            	        url: setMipssotokenurl,
		            	        async: false,
		            	        data:'',  
		            	        cache:false,  
		            	        dataType:'jsonp',
		            	        jsonp: 'callback',
		            	        success:function(data){
		            	        	
		            	        },
		            	        error: function(XMLHttpRequest, textStatus, errorThrown) {
		            	        	
		            	        }
		            	    });
	                		
	                		window.location.href=data.data.proxyurl;
	                	}else{
	                		$(".verify-img").trigger("click");
	                		alertify.alert(data.message);
	                	}
	                }
	            });
				
			});


	});
	
	function isNullOrEmpty(strVal) {
		if (strVal == '' || strVal == null || strVal == undefined) {
			return true;
		} else {
			return false;
		}
	}

	function isMobile(mobile) {
		
		var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
		if (myreg.test(mobile)) {
			return true;
		} else {
			return false;
		}
	}
 
	function SetRemainTime() {
            if (curCount == 0) {                
                window.clearInterval(InterValObj);
                $("#send_sms").removeAttr("disabled");
                $("#send_sms").text("重新发送");
            }
            else {
                curCount--;
                $("#send_sms").text(curCount + "秒内输入");
            }
      }
	reset ();
	function reset () {
			$("#toggleCSS").attr("href", "css/alertify.default.css");
			alertify.set({
				labels : {
					ok     : "确定",
					cancel : "取消"
				},
				delay : 5000,
				buttonReverse : false,
				buttonFocus   : "确定"
			});
		}
