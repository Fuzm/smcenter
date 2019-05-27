package com.irdstudio.smcenter.core.assembly.etl;

import com.irdstudio.smcenter.core.assembly.etl.conf.GlobalConf;
import com.irdstudio.smcenter.core.assembly.etl.conf.ReadProperties;
import com.irdstudio.smcenter.core.util.pub.Convert;

/**
 * @docRoot:
 * 完成加载全局配置及所有表属性配置的工作
 * @author guangming.li
 * Modify
 * 	2006-05-19 cky 增加了只能导入中的一些字段
 *
 */
public class ETLConfigure {
	
	private static ETLConfigure instance = new ETLConfigure();
	
	/**
	 * 将采用单实例模式来调用Confi
	 * 该构造函数为私有的
	 */
	private ETLConfigure(){}
	
	/**
	 * 单实例模式,只能通过这个方法获取对象实例
	 * @return
	 */
	public static ETLConfigure getInstance(){	
		return instance;
	}
	
	/**
	 * 加载全局配置文件
	 * @return
	 */
	public boolean loadGlobal(){
		
		//判断是否已经装载过配置文件了,如已装载,则退出
		if(GlobalConf.FILE_PATH != null) return true;
		
		//实例化读属性文件的对象
		ReadProperties read = new ReadProperties();
		boolean isOk = true;		
		
		//打开属性文件并读取属性到全局配置类
		System.out.println("Loading global config...");
		switch(read.openFile("etl.conf")){
			case ReadProperties.STATE_OK:
				//增量文件信息
				GlobalConf.RAR_FILE_PATH	= read.get("etl.RarFilePath");
				GlobalConf.RAR_BAK_PATH_TMP	= read.get("etl.RarBakPathTmp");
				GlobalConf.RAR_DECOMP_CMD	= read.get("etl.RarDecompCmd");
				
				//加载配置文件中的配置到全局配置类
				GlobalConf.ROOT_PATH 		= read.get("etl.RootPath");
				GlobalConf.FILE_PATH 		= read.get("etl.FilePath");
				GlobalConf.BACKUP_PATH		= read.get("etl.BackupPath");
				GlobalConf.LOG_PATH			= read.get("etl.LogPath");
				GlobalConf.TABLECONF_PATH	= read.get("etl.tableconf");
				
				//数据库连接配置
				GlobalConf.CONN_CLASSNAME	= read.get("service.pool.classname");
				GlobalConf.CONN_URL			= read.get("service.pool.datasource.url");
				GlobalConf.CONN_USER		= read.get("service.pool.datasource.user");
				GlobalConf.CONN_PASS		= read.get("service.pool.datasource.pass");
				GlobalConf.CONN_MIN 		= Convert.StrToInt(read.get("service.pool.connection.min"), 5);
				GlobalConf.CONN_MAX 		= Convert.StrToInt(read.get("service.pool.connection.max"), 10);
				GlobalConf.START_ON_INIT 	= Convert.StrToBoolean(read.get("service.pool.start_on_init"));
				
				//FTP服务器的配置信息
				GlobalConf.FTP_IP			= read.get("ftp.ip");
				GlobalConf.FTP_USER			= read.get("ftp.user");
				GlobalConf.FTP_PASS			= read.get("ftp.pass");
				GlobalConf.FTP_PATH			= read.get("ftp.filePath");
				
				//有关于各操作系统的区别,而作的配置
				GlobalConf.PATH_SEPARATOR	= read.get("path.separator");
				
				//ETL的一些开关选项
				GlobalConf.CHECK_CONTENT	= Convert.StrToBoolean(read.get("Import.CheckContent"));
				GlobalConf.IS_BACKUP		= Convert.StrToBoolean(read.get("etl.backup"));
				
				//下次运行时间间隔
				GlobalConf.INTERVAL			= Convert.StrToInt(read.get("etl.interval"),-1);
				
				//character set
				String tmp=read.get("etl.CharacterSet");
				GlobalConf.CHAR_SET 		= (tmp==null||tmp.equals("")?"ISO8859-1":tmp);//默认时就iso
				
				//指定整个处理过程是否使用事务机制
				GlobalConf.isAllOnTrans 	= Convert.StrToBoolean(read.get("Logic.isAllOnTrans"));
				
				//指定是否在月末表处理中使用事务机制（鉴于实际情况，一般为不使用，但需人工保持数据完整）
				GlobalConf.isOnTrans		= Convert.StrToBoolean(read.get("Logic.isOnTrans"));
						
				break;
			case ReadProperties.STATE_NOEXIST:
				//将异常记录到日志中
				isOk = false;
				System.out.println("没有找到etl.conf文件!");
				break;
			case ReadProperties.STATE_IOEXCEPTION:
				//将异常记录记录到日志中
				isOk = false;
				System.out.println("io异常!");
				break;
				
		}
		
		//关闭配置文件
		read.closeFile();
		
		//返回值,表明加载全局配置文件是否成功
		return isOk;
	}
}
