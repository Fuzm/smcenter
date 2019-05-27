package com.irdstudio.smcenter.core.assembly.socket.element;
/**
 * 文件传输类交易模式
 * @author 李广明
 * @version 1.0
 * @date 2008-06-19
 */
public class FileBusiMode {
	
	public final static int DIRECT 			= 0x01;		//直接传送
	public final static int CHANGE_TRAINS	= 0x02;		//中转传送
	
	/**
	 * 根据传输模式的英文描述得到数字值
	 * @param modeName
	 * @return
	 */
	public final static int getMode(String modeName) {
		if ("change_trains".equals(modeName.toLowerCase())) {
			return CHANGE_TRAINS;
		} else if ("direct".equals(modeName.toLowerCase())) {
			return DIRECT;
		} else {
			return DIRECT;
		}
	}

}
