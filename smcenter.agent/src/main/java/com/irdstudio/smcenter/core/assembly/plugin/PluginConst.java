package com.irdstudio.smcenter.core.assembly.plugin;
/**
 * 插件常量定义
 * @author guangming.li
 * @version 1.0
 * @date 2013-10-24
 */
public class PluginConst {
	
	/* 应用插件配置数据来源方式：XML配置文件 */
	public final static int CONFIG_DATA_FROM_XML = 2;
	/* 应用插件配置数据来源方式：DB数据库 */
	public final static int CONFIG_DATA_FROM_DB = 1;	
	
	/* 子系统信息变量定义 */
	/* 子系统代码 */
	public final static String SUBS_CODE = "subs_code";
	/* 子系统当前数据日期-10位 */
	public final static String SUBS_DATA_DATE = "subs_data_date";
	/* 子系统当前数据日期-8位 */
	public final static String SUBS_DATA_DATE_8 = "subs_data_date_8";	
	/* 子系统上一数据日期 */
	public final static String SUBS_LAST_DATA_DATE = "subs_last_data_date";
	/* 子系统数据装载完成日期 */
	public final static String SUBS_LOAD_DATE = "subs_load_date";
	/* 子系统批次完成日期 */
	public final static String SUBS_BAT_DATE = "subs_bat_date";
	/* 子系统当前数据日期的下一日期 */
	public static final String SUBS_NEXT_DATA_DATE = "subs_next_data_date";	
	
	/* 当前数据源变量定义 */
	/* 当前数据库名称 */
	public final static String CUR_DB_NAME = "cur_db_name";
	/* 当前连接字符串 */
	public final static String CUR_DB_CONN_STR = "cur_db_conn_str";
	/* 当前数据库用户 */
	public final static String CUR_DB_USER_ID = "cur_db_user_id";
	/* 当前数据库用户密码(加密后的密码) */
	public final static String CUR_DB_USER_PWD = "cur_db_user_pwd";
	/* 当前数据库用户密码(解密cur_db_user_pwd后的值) */
	public final static String CUR_DB_USER_PWD_D = "cur_db_user_pwd_d";
	/* 当前数据库模式 */
	public final static String CUR_DB_SCHEMA = "cur_db_schema";
	
	/* 数据源变量后缀名称定义 */
	/* 数据源的数据库名称 */
	public final static String DB_NAME = "db_name";
	/* 数据源的连接字符串 */
	public final static String DB_CONN_STR = "db_conn_str";
	/* 数据源的数据库用户 */
	public final static String DB_USER_ID = "db_user_id";
	/* 数据源的数据库用户密码(加密后的密码) */
	public final static String DB_USER_PWD = "db_user_pwd";
	/* 数据源的数据库用户密码(解密db_user_pwd后的值) */
	public final static String DB_USER_PWD_D = "db_user_pwd_d";
	/* 数据源的数据库模式 */
	public final static String DB_SCHEMA = "db_schema";
	
	/* 日志标志-成功 */
	public final static String LOG_FLAG_SUCCESS = "1";
	/* 日志标志-警告 */
	public final static String LOG_FLAG_WARNING = "2";
	/* 日志标志-失败 */
	public final static String LOG_FLAG_FAILD = "3";
	
	/* 失败处理策略-忽略(但记一条警告日志到插件日志表) */
	public final static String FAILD_DEAL_SKIP = "1";

	/* 失败处理策略-中止(插件运行结束) */
	public final static String FAILD_DEAL_OVER = "2";

	/* 任务状态-执行中 */
	public final static String TASK_STATE_RUNNING = "2";
	/* 任务状态-成功 */
	public final static String TASK_STATE_SUCCESS = "6";
	/* 任务状态-失败 */
	public final static String TASK_STATE_FAILD = "7";
	/* 任务干预状态-置过 */
	public static final String TASK_INT_STATE_SKIP = "1";//置过
	/* 任务干预状态-退出 */
	public static final String TASK_INT_STATE_EXIT = "3";
	
	/* 公共-是 */
	public final static String STD_Y = "Y";
	/* 公共-否 */
	public final static String STD_N = "N";
	
	/* 参数使用范围-全局 */
	public final static String PARAM_SCOPE_ALL = "1";
	/* 参数使用范围-子系统 */
	public final static String PARAM_SCOPE_SUBS = "2";
	/* 参数使用范围-应用插件 */
	public final static String PARAM_SCOPE_PLUGIN = "3";
	
	/* 数据源类型-ORACLE */
	public final static String S_SUBS_DS_TYPE_ORACLE = "01";
	/* 数据源类型-DB2 */
	public final static String S_SUBS_DS_TYPE_DB2 = "02";
	/* 数据源类型-SYBASE */
	public final static String S_SUBS_DS_TYPE_SYBASE = "03";
	/* 数据源类型-MSSQL */
	public final static String S_SUBS_DS_TYPE_MSSQL = "04";
	/* 数据源类型-MYSQL */
	public final static String S_SUBS_DS_TYPE_MYSQL = "05";
	/* 数据源类型-ACCESS */
	public final static String S_SUBS_DS_TYPE_ACCESS = "06";
	
	/* 导出模式-按表名导出 */
	public final static String EXPORT_MODE_BY_TABLE = "01";
	/* 导出模式-按自定义SQL导出 */
	public final static String EXPORT_MODE_BY_SQL = "02";	
}
