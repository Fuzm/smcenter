package com.irdstudio.smcenter.core.assembly.socket.log;
/**
 * 所有交易总线的交易日志服务
 * @author 李广民
 * @version 1.0
 * @date 2008-11-04
 */
public class BusiLogWriterThread extends Thread{
	/** 检查间隔数，每次1000ms,即1秒 */
	private int interval = 5 * 60;
	/** 是否停止 */
	private boolean stop = false;
	/**需要写日志的日志服务对象**/
	private BusiLogService logService = null;
	/** 
	 * 构造函数   
	 * @param logService 需要写日志的日志服务对象
	 * @param interval 日志缓冲区池检查时间间隔，单位s
	 */
	public BusiLogWriterThread(BusiLogService logService, int interval) {
		this.logService = logService;
		this.interval = interval;
	}
	
	/** 线程运行 */
	public void run() {
		while (!stop) {
			interval = logService.doWrite();
			try {
				for (int i = 0; i < interval; i++) {
					if (stop) break;
					sleep(1000);
				}
			} catch (Exception e) {}
		}
		logService = null;
	}
	
	/** 是否停止 */
	public boolean isStop() {
		return stop;
	}
	
	/** 是否停止 */
	public void setStop(boolean stop) {
		this.stop = stop;
	}	
}
