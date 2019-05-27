/**
 * <p>Title: 数据库连接参数</p>
 * <p>Description: 数据库连接参数</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 北京易初电子技术有限公司</p>
 * @author 黄瑞文
 * @version 1.0
 * 2006-11-22
 */

package com.irdstudio.smcenter.core.assembly.imdbcp.datasource;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;


/**
 * 数据库连接参数
 * @author hrw
 * 2006-11-26
 */
public class DataSourceParm
{
	/** 连接类型，1 WebSphere连接池，2 本地连接池，3 不使用连接池 */
	private int connectionType = 2;
	/** 数据源名称 */
	private String dataSourceName = null;
	/** 事务隔离级别
	 * 参阅Connection.setTransactionIsolation,
	 * 0 TRANSACTION_NONE ,
	 * 1 TRANSACTION_READ_UNCOMMITTED,
	 * 2 TRANSACTION_READ_COMMITTED,
	 * 4 TRANSACTION_REPEATABLE_READ,
	 * 8 TRANSACTION_SERIALIZABLE
	 */
	private int transation = Connection.TRANSACTION_READ_COMMITTED;
	/** 连接用的fetchSize，-1 表示不设置 */
	private int fetchSize = -1;
	/** 字符集 */
	private String charSet = null;
	/** 转码方式 0 不转码，1 转码， 2 优化转码 */
	private int encodeType = 0;
	/** 连接数据库用的driver */
	private String driver = null;
	/** 连接数据库用的url */
	private String url = null;
	/** 连接数据库用的urser */
	private String user = null;
	/** 连接数据库用的password */
	private String password = null;
	/** 获取连接最大等待时间,单位S */
	private int maxWaitTime = 30;
	/** 最小连接数 */
	private int minConnection = 1;
	/** 最大连接数 */
	private int maxConnection = 5;
	/** 极限连接数,-1表示不限制 */
	private int limitConnection = -1;
	/** 健康时间，是指最后一条SQL执行的时间超过该值未释放视为不健康，单位S */
	private int healthTime = 600;
	/** 检查连接的SQL */
	private String checkSQL = null;
	/** 连接池检查时间间隔，单位s */
	private int checkInterval = 5 * 60;
	/** 数据库扩展属性 */
	private Properties extendProperties = new Properties();

	/** 是否打印Debug信息 */
	private boolean isWriteDebug = false;

	public DataSourceParm(){

	}

    /**
     * 获取数据源参数
     * @return DataSourceParm[]
     */
	public static DataSourceParm[] getDataSourceParm(Properties configs) throws Exception{
		if (configs == null) {
			throw new NullPointerException("数据库参数为空！");
		}

		ArrayList<DataSourceParm> list = new ArrayList<DataSourceParm>();
		for (int i = 0; i<10; i++) {
			String key = null;
			key = "ds_"+i+"_DataSourceName";
			if (configs.get(key) != null) {
				DataSourceParm parm = new DataSourceParm();
				key = "ds_"+i+"_DataSourceName";
				String value = configs.getProperty(key);
				if (value != null) parm.setDataSourceName(value);

				key = "ds_"+i+"_ConnectionType";
				value = configs.getProperty(key);
				if (value != null) parm.setConnectionType(Integer.parseInt(value));

				key = "ds_"+i+"_Transation";
				value = configs.getProperty(key);
				if (value != null) parm.setTransation(Integer.parseInt(value));
				
				key = "ds_"+i+"_FetchSize";
				value = configs.getProperty(key);
				if (value != null) parm.setFetchSize(Integer.parseInt(value));

				key = "ds_"+i+"_CharSet";
				value = configs.getProperty(key);
				if (value != null) parm.setCharSet(value);

				key = "ds_"+i+"_EncodeType";
				value = configs.getProperty(key);
				if (value != null) parm.setEncodeType(Integer.parseInt(value));

				key = "ds_"+i+"_Driver";
				value = configs.getProperty(key);
				if (value != null) parm.setDriver(value);

				key = "ds_"+i+"_Url";
				value = configs.getProperty(key);
				if (value != null) parm.setUrl(value);

				key = "ds_"+i+"_User";
				value = configs.getProperty(key);
				if (value != null) parm.setUser(value);

				key = "ds_"+i+"_Password";
				value = configs.getProperty(key);
				if (value != null) parm.setPassword(value);

				key = "ds_"+i+"_MaxWaitTime";
				value = configs.getProperty(key);
				if (value != null) parm.setMaxWaitTime(Integer.parseInt(value));

				key = "ds_"+i+"_MinConnection";
				value = configs.getProperty(key);
				if (value != null) parm.setMinConnection(Integer.parseInt(value));

				key = "ds_"+i+"_MaxConnection";
				value = configs.getProperty(key);
				if (value != null) parm.setMaxConnection(Integer.parseInt(value));
				
				key = "ds_"+i+"_LimitConnection";
				value = configs.getProperty(key);
				if (value != null) parm.setLimitConnection(Integer.parseInt(value));

				key = "ds_"+i+"_HealthTime";
				value = configs.getProperty(key);
				if (value != null) parm.setHealthTime(Integer.parseInt(value));

				key = "ds_"+i+"_CheckSQL";
				value = configs.getProperty(key);
				if (value != null) parm.setCheckSQL(value);

				key = "ds_"+i+"_CheckInterval";
				value = configs.getProperty(key);
				if (value != null) parm.setCheckInterval(Integer.parseInt(value));

				key = "ds_"+i+"_WriteDebug";
				value = configs.getProperty(key);
				if (value != null) parm.setWriteDebug("true".equalsIgnoreCase(value));

				key = "ds_"+i+"_ext_";
				for (Iterator<?> iterator = configs.keySet().iterator(); iterator.hasNext(); ) {
					String st_key = (String) iterator.next();
					String st_val = configs.getProperty(st_key);
					if (st_key != null && st_key.startsWith(key)) {
						st_key = st_key.substring(key.length());
						parm.setExtendProperty(st_key,st_val);
					}
				}
				list.add(parm);
				//sSystem.err.println("[parm]ds=" + parm.toString());
			}
		}

		DataSourceParm[] parms = new DataSourceParm[list.size()];
		list.toArray(parms);

		return parms;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("数据库参数配置：\r\n");
		sb.append("DataSourceName=").append(dataSourceName).append("\r\n");
		sb.append("ConnectionType=").append(this.connectionType).append("\r\n");
		sb.append("transation=").append(transation).append("\r\n");
		sb.append("FetchSize=").append(fetchSize).append("\r\n");
		sb.append("charSet=").append(charSet).append("\r\n");
		sb.append("encodeType=").append(encodeType).append("\r\n");
		sb.append("driver=").append(driver).append("\r\n");
		sb.append("url=").append(url).append("\r\n");
		sb.append("user=").append(user).append("\r\n");
		sb.append("password=").append(password).append("\r\n");
		sb.append("maxWaitTime=").append(maxWaitTime).append("\r\n");
		sb.append("minConnection=").append(minConnection).append("\r\n");
		sb.append("maxConnection=").append(maxConnection).append("\r\n");
		sb.append("limitConnection=").append(limitConnection).append("\r\n");
		sb.append("healthTime=").append(healthTime).append("\r\n");
		sb.append("checkSQL=").append(checkSQL).append("\r\n");
		sb.append("checkInterval=").append(checkInterval).append("\r\n");
		sb.append("isWriteDebug=").append(isWriteDebug).append("\r\n");

		for (Iterator<?> iterator = extendProperties.keySet().iterator();iterator.hasNext();) {
			String key = (String)iterator.next();
			String value = extendProperties.getProperty(key);
			sb.append("extend:").append(key).append("=").append(value).append("\r\n");
		}

		return sb.toString();
	}

	/** 字符集 */
	public String getCharSet() {
		return charSet;
	}

	/** 字符集 */
	public void setCharSet(String charSet) {
		this.charSet = charSet;
	}

	/** 连接类型，1 WebSphere连接池，2 本地连接池，3 不使用连接池 */
	public int getConnectionType() {
		return connectionType;
	}
	/** 连接类型，1 WebSphere连接池，2 本地连接池，3 不使用连接池 */
	public void setConnectionType(int connectionType) {
		this.connectionType = connectionType;
	}

	/** 数据源名称 */
	public String getDataSourceName() {
		return dataSourceName;
	}

	/**
	 * 数据源名称
	 * @param dataSourceName 数据源名称
	 */
	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}

	/** 连接数据库用的driver */
	public String getDriver() {
		return driver;
	}

	/** 连接数据库用的driver */
	public void setDriver(String driver) {
		this.driver = driver;
	}

	/** 转码方式 0 不转码，1 转码， 2 优化转码 */
	public int getEncodeType() {
		return encodeType;
	}

	/** 转码方式 0 不转码，1 转码， 2 优化转码 */
	public void setEncodeType(int encodeType) {
		this.encodeType = encodeType;
	}

	/** 健康时间，是指最后一条SQL执行的时间超过该值未释放视为不健康，单位S */
	public int getHealthTime() {
		return healthTime;
	}

	/** 健康时间，是指最后一条SQL执行的时间超过该值未释放视为不健康，单位S */
	public void setHealthTime(int healthTime) {
		this.healthTime = healthTime;
	}

	/** 最大连接数 */
	public int getMaxConnection() {
		return maxConnection;
	}

	/** 最大连接数 */
	public void setMaxConnection(int maxConnection) {
		this.maxConnection = maxConnection;
	}

	/** 最小连接数 */
	public int getMinConnection() {
		return minConnection;
	}

	/** 最小连接数 */
	public void setMinConnection(int minConnection) {
		this.minConnection = minConnection;
	}

	/** 连接数据库用的password */
	public String getPassword() {
		return password;
	}

	/** 连接数据库用的password */
	public void setPassword(String password) {
		this.password = password;
		extendProperties.setProperty("password", password);
	}

	/** 事务隔离级别
	 * 参阅Connection.setTransactionIsolation,
	 * 0 TRANSACTION_NONE ,
	 * 1 TRANSACTION_READ_UNCOMMITTED,
	 * 2 TRANSACTION_READ_COMMITTED,
	 * 4 TRANSACTION_REPEATABLE_READ,
	 * 8 TRANSACTION_SERIALIZABLE
	 */
	public int getTransation() {
		return transation;
	}

	/** 事务隔离级别
	 * 参阅Connection.setTransactionIsolation,
	 * 0 TRANSACTION_NONE ,
	 * 1 TRANSACTION_READ_UNCOMMITTED,
	 * 2 TRANSACTION_READ_COMMITTED,
	 * 4 TRANSACTION_REPEATABLE_READ,
	 * 8 TRANSACTION_SERIALIZABLE
	 */
	public void setTransation(int transation) {
		this.transation = transation;
	}

	/** 连接数据库用的url */
	public String getUrl() {
		return url;
	}

	/** 连接数据库用的url */
	public void setUrl(String url) {
		this.url = url;
	}

	/** 连接数据库用的urser */
	public String getUser() {
		return user;
	}

	/** 连接数据库用的urser */
	public void setUser(String user) {
		this.user = user;
        this.extendProperties.setProperty("user", user);
	}

	/** 是否打印Debug信息 */
	public boolean isWriteDebug() {
		return isWriteDebug;
	}

	/** 是否打印Debug信息 */
	public void setWriteDebug(boolean isWriteDebug) {
		this.isWriteDebug = isWriteDebug;
	}

	/** 获取连接最大等待时间,单位S */
	public int getMaxWaitTime() {
		return maxWaitTime;
	}

	/** 获取连接最大等待时间,单位S */
	public void setMaxWaitTime(int maxWaitTime) {
		this.maxWaitTime = maxWaitTime;
	}

	/** 检查连接的SQL */
	public String getCheckSQL() {
		return checkSQL;
	}

	/** 检查连接的SQL */
	public void setCheckSQL(String checkSQL) {
		this.checkSQL = checkSQL;
	}

	/** 连接池检查时间间隔，单位s */
	public int getCheckInterval() {
		return checkInterval;
	}

	/** 连接池检查时间间隔，单位s */
	public void setCheckInterval(int checkInterval) {
		this.checkInterval = checkInterval;
	}

	/** 设置扩展参数 */
	public void setExtendProperty(String key,String value) {
		this.extendProperties.setProperty(key, value);
	}

	/** 数据库扩展属性 */
	public Properties getExtendProperties() {
		return extendProperties;
	}

	/** 数据库扩展属性 */
	public void setExtendProperties(Properties extendProperties) {
		this.extendProperties = extendProperties;
	}

	/** 连接用的fetchSize，-1 表示不设置 */
	public int getFetchSize() {
		return fetchSize;
	}

	/** 连接用的fetchSize，-1 表示不设置 */
	public void setFetchSize(int fetchSize) {
		this.fetchSize = fetchSize;
	}

	/** 极限连接数 */
	public int getLimitConnection() {
		return limitConnection;
	}

	/** 极限连接数 */
	public void setLimitConnection(int limitConnection) {
		this.limitConnection = limitConnection;
	}
}
