package com.irdstudio.smcenter.core.assembly.etl.conf;
/**
 * @docRoot:
 * 	全局配置文件对应的存放类,全部使用静态变量,供全局使用
 * @author 李广明
 * @version 1.0
 * Modify
 * 	2006-05-27 cky 增加智能抽取存放信息部分
 *
 */
public class GlobalConf {
	//智能抽取存放信息
	public static String RAR_FILE_PATH		= null; //导入增量文件时,指定压缩包文件所在位置,使用如下配置
	public static String RAR_BAK_PATH_TMP	= null;	//临时备份增量文件路径，指定位置
	public static String RAR_DECOMP_CMD		= null; //解压缩命令
	public static String RAR_MOVE_CMD		= null; //移动文件命令
	
	//定义存放目录信息的静态变量,
	public static String ROOT_PATH 		= null;		//指定etl所有的根目录路径
	public static String FILE_PATH		= null;		//指定需导入的文件的所在路径
	public static String BACKUP_PATH	= null;		//指定备份文件所存放的路径
	public static String LOG_PATH		= null;		//指定日志文件所存放的路径
	public static String TABLECONF_PATH = null;		//表配置文件所在的目录
	
	//定义用于存放连接信息的配置
	public static String CONN_CLASSNAME = null;		//连接所用到的类
	public static String CONN_URL		= null;		//URL
	public static String CONN_USER		= null;		//用于连接数据库的用户名
	public static String CONN_PASS		= null;		//用于连接数据库的密码
	public static int CONN_MIN			= 5;		//最小连接数(默认值)
	public static int CONN_MAX			= 10;		//最大连接数(默认值)
	public static boolean START_ON_INIT = true;		//是否在初始化的时候就初始化连接池 
	
	//配置用于ftp服务器的信息
	public static String FTP_IP			= null;		//ftpIP
	public static String FTP_USER		= null;		//用于连接ftp的密码
	public static String FTP_PASS		= null;		//用于连接ftp的密码
	public static String FTP_PATH		= null;		//ftp归档目录
	
	//有关于各操作系统的区别,而作的配置
	public static String PATH_SEPARATOR	= "\\";		//路径描述符号,windows为\,unix中为/
	
	//与性能相关的一些配置
	public static boolean CHECK_CONTENT = false;	//是否检查数据内容
	
	//一些开关选项
	public static boolean CHECK_MODE	= false;	//表明是否使用检查数据模式运行
	public static boolean IS_BACKUP		= false;	//是否备份源文件
	
	//指定程序运行间隔
	public static int INTERVAL=-1;					//指定程序运行强制时间间隔，可以不指定
	
	//指定数据库的编码方式
	public static String CHAR_SET	= null;
	
	//指定整个流程中是否使用事务
	public static boolean isAllOnTrans=false;
	
	//指定是否在月末表处理中使用事务机制（鉴于实际情况，一般为不使用，但需人工保持数据完整）
	public static boolean isOnTrans = false;
}
