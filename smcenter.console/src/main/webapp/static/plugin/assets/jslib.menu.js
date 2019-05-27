/*
 * macms.menu.js
 * 用于装载菜单树
 */

/* 当前一级菜单ID */
var curFirstLevelMenuId = "";

/* 
 * 根据指定的一级菜单标识显示子菜单信息在导航栏
 * 如果已经生成了一级菜单的子菜单，则直接显示,否则生成 
 */
function showTreeMenuBar(firstLevelMenuId){
	if(curFirstLevelMenuId == ""){
		$('#' + firstLevelMenuId ).show();
		curFirstLevelMenuId = firstLevelMenuId;
	} else {
		if(curFirstLevelMenuId == firstLevelMenuId)
			return;
		$('#' + curFirstLevelMenuId).hide();
		$('#' + firstLevelMenuId ).show();
		curFirstLevelMenuId = firstLevelMenuId;
	}
}
/*
 * 生成一级菜单下的子菜单信息(默认展开)
 */
function generateTreeMenuBar(firstLevelMenuId){
	
	
}


