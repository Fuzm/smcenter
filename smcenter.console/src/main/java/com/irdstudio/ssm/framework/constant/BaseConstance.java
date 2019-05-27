package com.irdstudio.ssm.framework.constant;

public class BaseConstance {

	// 批次干预状态
	// 0-正常
	// 1-暂停
	public static final String BATCH_INT_STATE_RUN = "0";// 正常
	public static final String BATCH_INT_STATE_STOP = "1";// 暂停
	public static final String BATCH_INT_STATE_END = "2";// 中断
	public static final String BATCH_INT_STATE_RERUN = "3";// 重跑
	
	// 批次执行状态
	// 1-出错
	// 2-警告
	// 3-运行
	// 4-空闲
	// 8-初始
	// 9-结束
	public static final String BATCH_STATE_FAIL = "1";// 出错
	public static final String BATCH_STATE_WARN = "2";// 警告
	public static final String BATCH_STATE_RUN = "3";// 运行
	public static final String BATCH_STATE_FREE = "4";// 空闲
	public static final String BATCH_STATE_INIT = "8";// 初始
	public static final String BATCH_STATE_END = "9";// 结束
	
	//任务干预状态
	//	0-正常
	//	1-置过
	//	2-挂起
	public static final String TASK_INT_STATE_RUN = "0";//正常
	public static final String TASK_INT_STATE_SKIP = "1";//置过
	public static final String TASK_INT_STATE_SUSPEND = "2";//挂起
	public static final String TASK_INT_STATE_EXIT = "3";//退出
	
	public static final String TASK_SKIP_TACTIC= "2";//不允许置过
	public static final String BATCH_RUN_AGAIN_Y= "Y";//允许重跑
	public static final String BATCH_RUN_AGAIN_N= "N";//不允许重跑
}
