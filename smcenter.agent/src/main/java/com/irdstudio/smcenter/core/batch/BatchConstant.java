package com.irdstudio.smcenter.core.batch;
/**
 * 批次服务中的常量定义
 * @author guangming.li
 * @version 1.0
 * @date 2014-04-25
 */
public class BatchConstant {
	
	/* 任务状态-未执行 */
	public final static int TASK_STATE_UNRUN = 0;
	/* 任务状态-待执行 */
	public final static int TASK_STATE_WTRUN = 1;
	/* 任务状态-执行中 */
	public final static int TASK_STATE_RUNNING = 2;
	/* 任务状态-执行中(超过) */
	public final static int TASK_STATE_OTRUNING = 3;	
	/* 任务状态-执行中(有警告) */
	public final static int TASK_STATE_WRNRUNING = 4;	
	/* 任务状态-执行成功 */
	public final static int TASK_STATE_SUCCESS = 6;
	/* 任务状态-执行失败 */
	public final static int TASK_STATE_FAILD = 7;	
	
	/* 批次状态-出错 */
	public final static int BATCH_STATE_ERROR = 1;
	/* 批次状态-警告 */
	public final static int BATCH_STATE_WARN = 2;
	/* 批次状态-运行中 */
	public final static int BATCH_STATE_RUNNING = 3;
	/* 批次状态-空闲 */
	public final static int BATCH_STATE_IDLE = 4;
	/* 批次状态-初始 */
	public final static int BATCH_STATE_INIT = 8;
	/* 批次状态-结束 */
	public final static int BATCH_STATE_FINISHED = 9;
	
	/* 任务出错跳过策略－自动(直接跳过) */
	public final static int TASK_SKIP_TACTIC_AUTO = 0;
	/* 任务出错跳过策略－手动(不直接跳过，需手工置过) */
	public final static int TASK_SKIP_TACTIC_MANUAL = 1;
	/* 任务出错跳过策略－禁止(失败则批次中止) */
	public final static int TASK_SKIP_TACTIC_FORBID = 2;
	
	/* 任务干预状态-未干预 */
	public final static int TASK_INTERVENE_STATE_NO = 0;
	/* 任务干预状态-置过 */
	public final static int TASK_INTERVENE_STATE_PASS = 1;
	/* 任务干预状态-挂起 */
	public final static int TASK_INTERVENE_STATE_SUSPEND = 2;
	/* 任务干预状态-退出 */
	public final static int TASK_INTERVENE_STATE_QUIT = 3;
	/* 任务干预状态-未干预(字符串) */
	public final static String TASK_INTERVENE_STATE_NO_S = "0";
	/* 任务干预状态-置过(字符串) */
	public final static String TASK_INTERVENE_STATE_PASS_S = "1";
	
	/* 定期任务－定期类型－季末 */
	public static final String BAT_CYCLE_TYPE_S = "S";
	/* 定期任务－定期类型－月末 */
	public static final String BAT_CYCLE_TYPE_M = "M";
	/* 定期任务－定期类型－年末 */
	public static final String BAT_CYCLE_TYPE_Y = "Y";
	/* 定期任务－定期类型－周末 */
	public static final String BAT_CYCLE_TYPE_W = "W";	
	
}
