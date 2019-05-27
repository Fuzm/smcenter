if(!jslib) var jslib = {};

/* 常用辅助js */
if (!jslib.util) {
	
	jslib.util = {
	
		/**用于居中打开页面(指定高度与宽度)*/
		woInCenter : function(url,height,width){
			var top = parseInt(((window.screen.availHeight-30) - height) / 2);
			var left = parseInt(((window.screen.availWidth-30) - width) / 2);
			window.open(url,'_blank','height=' + height
					 + ',width=' + width + ',left=' + left + ',top=' + top + ',scrollbars=yes');	
		},
		
		/**用于居中打开页面(自动判断宽度与高度)*/
		woInCenterEx : function(url){
			var height = parseInt((window.screen.availHeight-30) * 0.6);
			var width  = parseInt((window.screen.availWidth-10) * 0.6);
			this.woInCenter(url,height,width);
		},
		
		/**全屏幕打开*/
		woInFull : function(url){
			window.open(url,'_blank','height=' + (window.screen.availHeight-30)
					 + ',width=' + (window.screen.availWidth-10) + ',left=0,top=0,scrollbars=no,toolbar=no,menubar=no,location=no,status=yes');
		},
		
		/**以链接的形式打开新页面*/
		linkNewPage : function(url){
			var commonLinkObj = document.getElementById("commonLink");
			if(!commonLinkObj){
				commonLinkObj = document.createElement("a");
				commonLinkObj.target = "_blank";
			}
			commonLinkObj.href = url;
			commonLinkObj.click();
		},
		
		/**标准提示信息框*/
		doConfirm : function (msg) { 
			if (confirm(msg)==true){ 
				return true; 
			}else{ 
				return false; 
			}
		}
	
	};
};

var JLUtil = jslib.util;


