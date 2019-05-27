package com.irdstudio.smcenter.core.tinycore.jdbc.dbcp;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.irdstudio.smcenter.core.tinycore.jdbc.dbcp.base.IConnPool;
import com.irdstudio.smcenter.core.tinycore.jdbc.dbcp.impl.ConnPoolForDruid;

/**
 * 最顶层的连接池管理类(可管理多个连接池])
 * @author guangming.li
 * @version 2.0
 * @since 1.0
 * @date 2014-05-13
 */
public class TConnPool {

	/* 默认的连接池 (在没有多数据源的情况下,只使用默认的连接池即可) */
	private static IConnPool defaultConnPool = null;
	/* 连接池管理数据存放区(以连接池名称与连接池对象配对进行管理,用于多数据源的情况下) */
	private static ConcurrentMap<String, IConnPool> dbcpMap = new ConcurrentHashMap<String, IConnPool>();

	/**
	 * 获取默认的连接池
	 * 线程安全 
	 * @return
	 */
	public static IConnPool getDefaultPool() {
		if (defaultConnPool == null) {
			initDefaultPool();
		}
		return defaultConnPool;
	}
	
	/**
	 * 初始化默认的连接池
	 * 线程安全(确保只初始化一次)
	 */
	private synchronized static void initDefaultPool(){
		if (defaultConnPool == null) {
			// 默认连接池指向EMP的数据源
			defaultConnPool = new ConnPoolForDruid();
		}		
	}
	
	/**
	 * 设置默认的连接池对象
	 * 供外部类使用，以设置或改变默认数据源
	 * @param connPool
	 */
	public synchronized static void setDefaultPool(IConnPool connPool) {
			defaultConnPool = connPool;
	}

	/**
	 * 获取一个连接池(根据连接池名称)
	 * 使用支持高并发高吞吐量的线程安全的HashMap
	 * @param dbcpName
	 * @return
	 */
	public static IConnPool getPoolInst(String dbcpName) {
		return dbcpMap.get(dbcpName);
	}
	
	/**
	 * 增加一个连接池进入顶级连接池管理
	 * 使用支持高并发高吞吐量的线程安全的HashMap
	 * @param connPool
	 */
	public static void addConnInst(String dbcpName, IConnPool connPool) {
		dbcpMap.put(dbcpName, connPool);
	}
	
	/**
	 * 从顶级连接池管理中删除一个连接池(清除连接资源)
	 * @param dbcpName
	 */
	public static void removeConnInst(String dbcpName) {
		dbcpMap.remove(dbcpName);
	}
	
	/**
	 * 获取所有连接池信息
	 * @return
	 */
	public static Map<String, IConnPool> getDbcpMap() {
		return dbcpMap;
	}	
	
	
}
