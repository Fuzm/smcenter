/*
 * macms.menu.js
 * ����װ�ز˵���
 */

/* ��ǰһ���˵�ID */
var curFirstLevelMenuId = "";

/* 
 * ����ָ����һ���˵���ʶ��ʾ�Ӳ˵���Ϣ�ڵ�����
 * ����Ѿ�������һ���˵����Ӳ˵�����ֱ����ʾ,�������� 
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
 * ����һ���˵��µ��Ӳ˵���Ϣ(Ĭ��չ��)
 */
function generateTreeMenuBar(firstLevelMenuId){
	
	
}


