/*!
 * 检测cookie是否启用
 */

/**
 * 检测cookie是否开启
 */
	function isCookieEnable()
	{
        var isEnabled = true;
		if(!(document.cookie || navigator.cookieEnabled))
		{
	        isEnabled = false;
		}
		return isEnabled;
	}

/**
 * 检测客户端是否开启cookie,返回对象包含是否开启标识和提示语言
 */
    function checkCookieStatus()
    {
       var cookieInfo ={};
       cookieInfo.isEnabled = true;    
       if(!(document.cookie || navigator.cookieEnabled))
		{
    	   cookieInfo.isEnabled = false;
    	   cookieInfo.tips = "浏览器禁用了COOKIE, 请先启用！";
		}
       return cookieInfo;
	}

