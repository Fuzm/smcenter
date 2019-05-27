if(!jslib) var jslib = {};

/* jquery ajax 封装,需要依赖jquery */
if (!jslib.jqueryajax) {
	
	jslib.jqueryajax = {
		/**
		 * 使用jquery发起ajax请求,返回json数据
		 * @url 请求的url
		 * @callback 调用成功后的处理
		 * @errorProcess 异常处理(不传则使用默认处理方式)
		 */
		ajaxJson : function(url,param,callback,errorProcess){
		    $.ajax({
	  	      url: url,
	  	      type: "POST",
	  	      dataType: "json",
	  	      data:param,
	  	      async: false,
	  	      success: function(data) {  
			    	//服务器返回响应，根据响应结果，分析是否登录成功；
					if (data.ec != "0") {
						// 返回码不为0，说明逻辑错误,进行处理
						if (errorProcess) {
							errorProcess(data);
						} else {
							alert("" + data.em);
						}
					} else {
						// 说明成功,调用回调函数进行处理
						callback(data);
					}  
	  	      },
	  	      error: function() {    	        
					//异常处理；
					alert("error!");
	  	      }
	  	    });
		}
	
	};
};

(function($) {
	
	$.getUrlParam = function(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r != null)
			return decodeURI(r[2]);
		return null;
	};
	
    $.fn.serializeJson=function(){    
        var serializeObj={};    
        var array=this.serializeArray();
        //解决disabled属性无法serialize
        $(':disabled[name]', this).each(function () { 
        	array.push({ name: this.name, value: $(this).val() });
        });
        
        var str=this.serialize();    
        $(array).each(function(){    
            if(serializeObj[this.name]){    
                if($.isArray(serializeObj[this.name])){    
                    serializeObj[this.name].push(this.value);    
                }else{    
                    serializeObj[this.name]=[serializeObj[this.name],this.value];    
                }    
            }else{    
                serializeObj[this.name]=this.value;     
            }    
        });    
        return serializeObj;    
    };

	$.restfulPostForm = function(url, param, successCall, failureCall) {

	};

	$.restfulPostJson = function(url, jsonData, successCall, failureCall) {
		$.ajax({ 
			url:url,
			type:"POST",
			data:JSON.stringify(jsonData),
			contentType:"application/json;charset=utf-8",
			dataType:"json",
			async:false,
			success: function(data) {
				try {
					var flag = data.code;
					if(flag=='0'){
						if(!successCall){
							alert('操作成功!');
						} else {
							successCall(data);
						}
					 	return true;
					}else{
						if(!failureCall){
							alert(data.message);
						} else {
							failureCall(data);
						}
						return false;
					}
				} catch(e) {
					alert(e);
					return false;
				}
			} 
		});
	};

	$.restfulGet = function(url, param, successCall, failureCall) {
		$.ajax({
			type : "GET",
			url : url,
			data : param,
			async : false,
			success : function(data) {
				try {
					var flag = data.code;
					if (flag == '0') {
						if (!successCall) {
							alert('操作成功!');
						} else {
							successCall(data);
						}
						return true;
					} else {
						if (!failureCall) {
							alert(data.message);
						} else {
							failureCall(data);
						}
						return false;
					}
				} catch (e) {
					alert(e);
					return false;
				}
			}
		});
	};

	$.restfulPut = function(url, jsonData, successCall, failureCall) {
		$.ajax({ 
			url:url,
			type:"PUT",
			data:JSON.stringify(jsonData),
			contentType:"application/json;charset=utf-8",
			dataType:"json",
			async:true,
			success: function(data) {
				try {
					var flag = data.code;
					if(flag=='0'){
						if(!successCall){
							alert('更新成功!');
						} else {
							successCall(data);
						}
					 	return true;
					}else{
						if(!failureCall){
							alert(data.message);
						} else {
							failureCall(data);
						}
						return false;
					}
				} catch(e) {
					alert(e);
					return false;
				}
			} 
		});
	};

	$.restfulDelete = function(url, jsonData, successCall, failureCall) {
		$.ajax({
			url:url,
			type:"DELETE",
			data:JSON.stringify(jsonData),
			contentType:"application/json;charset=utf-8",
			dataType:"json",
			async:false,
			success: function(data) {
				try {
					var flag = data.code;
					if(flag=='0'){
						if(!successCall){
							alert('删除成功!');
						} else {
							successCall(data);
						}
					 	return true;
					}else{
						if(!failureCall){
							alert(data.message);
						} else {
							failureCall(data);
						}
						return false;
					}
				} catch(e) {
					alert(e);
					return false;
				}
			} 
		});
	};
})(jQuery);

var JLAjax = jslib.jqueryajax;

$.ajaxSetup({
	statusCode: {401: function() {
		var win = window;
		var count = 5;
		while(typeof(win.topWin) == 'undefined') {
			win = win.parent;
			count--;
			if(count < 0) {
				break;
			}
		}
		
		if(win != null) {
			$(win.location).attr('href', '../../login.html');
		} else {
			$(window.location).attr('href', '../../login.html');
		}
	}}
});
