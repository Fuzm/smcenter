// 针对EASYUI进行扩展的辅助类，用于更简捷的操作EASYUI
var JLEUtil = JLEUtil || {};
(function(jQuery) {

	/**
	 * <p>
	 * <ul>提供基于iframe的dialog实现，保证引入页面的可用性</ul>
	 * </p>
	 * <ul>将iframe的高度改为99%，防止重复出现滚动条问题</ul>
	 * <ul>如果要顶层打开窗口，请使用top.JLEUtil.createwin</ul>
	 * @param options为配置参数对象{id:id,title:'标题',url:'url地址',width:800,height:300,modal:true,draggable:true,resizable:true,maximized:false,maximizable：true,toolbar}
	 * 参考dialog属性和方法
	 * id:唯一标识
	 * title:弹出窗口的标题
	 * url:弹出窗口的内容
	 * width:窗口宽度，默认600
	 * height:窗口高度，默认300
	 * modal:是否模态窗口,默认true
	 * draggable:是否可拖动，默认true
	 * resizable:是否可改变大小，默认false
	 * maximized:是否以最大化窗口的方式打开，默认true
	 * maximizable:是否有最大化窗口的按钮
	 * toolbar:工具栏
	 */
	JLEUtil.createwin=function(options) {
		var top = $(window).scrollTop();
		var opts=$.extend({
				width : 600,
				height : 300,
				modal:true,
				maximized:false,
				maximizable:true,
				onClose : function() {
					$(this).dialog('destroy');
				}
		},options);
		if (options.url) {
			opts.content = '<iframe id="" src="" allowTransparency="true" scrolling="auto" width="100%" height="99.5%" frameBorder="0" name=""></iframe>';
		}
		win=$('<div/>');
		var dia = win.dialog(opts);
		var diaWin = dia.dialog('window');
		if(opts.maximized){
			//修正以最大化窗口的方式打开时，如果父页面出现滚动条时则引起打开窗口错位的问题
			diaWin.css('top',top);
		}
		dia.find("iframe").attr("src",options.url);
		/*******************修改弹出框中button按钮的样式，开始，添加于20140902*********************/
		dia.find('.dialog-button').addClass('new_btn').css("margin-top","-20px");
		/*******************修改弹出框中button按钮的样式，结束*********************/
		return dia;
	};
	
	JLEUtil.createModalWin=function(title,url) {
		var w = document.documentElement.offsetWidth || document.body.offsetWidth;
		var h = document.documentElement.offsetHeight || document.body.offsetHeight;
		var ob={title:title,url:url,width:parseInt(w*0.9),height:parseInt(h*0.9),draggable:false,modal:true};
		JLEUtil.createwin(ob);
	};
	
	JLEUtil.createFullModalWin=function(title,url) {
		var ob={title:title,url:url,width:1000,height:500,draggable:false,modal:true,maximized:true};
		JLEUtil.createwin(ob);
	};	
	
	// 关闭打开的窗口
	JLEUtil.closewin=function(){
		win.dialog('close');
	};
	
	// 提交保存数据的AJAX请求,callback不传则默认处理
	JLEUtil.doSaveRecord=function(formName,url,callback){
		var param = $('#' + formName).serialize();
		if(!callback){
			JLEUtil.doAjax(url,param,function(data){
				alert('记录保存成功!');
				window.parent.JLEUtil.closewin();				
			});	
		} else {
			JLEUtil.doAjax(url,param,callback);	
		}
		
	};
	
	// 提交AJAX请求操作,successCall为交易成功后处理函数,failureCall交易失败为失败默认处理,不传则默认处理
	JLEUtil.doAjax=function(url,param,successCall,failureCall){
		$.ajax({ 
			type: "POST", 
			url: url,
			data: param,
			async:false,
			success: function(data) {
				try {
					var jsonstr = eval("("+data+")");
					var flag = jsonstr.ec;
					if(flag=='0'){
						if(!successCall){
							alert('操作成功!');
						} else {
							successCall(data);
						}
					 	return true;
					}else{
						if(!failureCall){
							alert(jsonstr.em);
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
	
	// 根据指定form的条件与指定的datagrid及指定的URL进行查询
	JLEUtil.queryForm=function(formName,dgName,url){
		var queryUrl = url.indexOf("?") > 0 
				? url + "&" + $('#' + formName).serialize() 
				: url + "?" + $('#' + formName).serialize();
		$('#' + dgName).datagrid('options').url = queryUrl;
		$('#' + dgName).datagrid('load');
	};
	
	// 直接根据URL来进行查询数据
	JLEUtil.queryOnly=function(dgName,url){
		$('#' + dgName).datagrid('options').url = url;
		$('#' + dgName).datagrid('load');
	};	
	
	// 清空(重置)指定form填写的内容
	JLEUtil.clearForm=function(formName){
		$('#' + formName).form('clear');
	};
	
	// 根据选项值得到选项描述
	JLEUtil.formatter_dict = function(value,dictObj){
		for(var i=0;i<dictObj.length;i++){
			if(value == dictObj[i].enname)
				return dictObj[i].cnname;
		}
	};
	
	JLEUtil.paramUrl2Obj = function(url) {
        var search = url.split('?')[1]
        if (!search) {
            return {}
        }
        return JSON.parse('{"' + decodeURIComponent(search).replace(/"/g, '\\"').replace(/&/g, '","').replace(/=/g, '":"').replace(/\n/g, '\\n') + '"}')
    };

})(jQuery);

$.ajaxSetup ({
	   cache: false //关闭AJAX相应的缓存
});