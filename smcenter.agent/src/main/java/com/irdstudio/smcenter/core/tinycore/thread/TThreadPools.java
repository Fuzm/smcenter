package com.irdstudio.smcenter.core.tinycore.thread;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Top ThreadPools
 * 最顶层的线程池管理类
 * @author guangming.li
 * @version 1.0
 * @date 2014-07-07
 */
public class TThreadPools {
	
	/* 线程池管理容器 (Java 5中支持高并发高吞吐量的线程安全的HashMap实现) */
	private static ConcurrentMap<String, ExecutorService> threadPools = new ConcurrentHashMap<String, ExecutorService>(); 
	

	/**
	 * 根据线池程的名称返回线程池
	 * @param poolName
	 * @return
	 */
	public static ExecutorService getThreadPool(String threadPoolName) {
		if (threadPools.get(threadPoolName) == null||threadPools.get(threadPoolName).isTerminated()) {
			registerCachedThreadPool(threadPoolName);
		}
		return threadPools.get(threadPoolName);
	}
	
	/**
	 * 注册线程池(将名字与线程池关联)
	 * @param threadPoolName
	 * @param cachedThreadPool
	 */
	private static void registerCachedThreadPool(String threadPoolName) {
		threadPools.put(threadPoolName, Executors.newCachedThreadPool());
	}
	
	/**
	 * 根据线程池名称关闭线程池
	 * @param threadPoolName
	 */
	public static void showdownThreadPool(String threadPoolName) {
		ExecutorService executorService = threadPools.get(threadPoolName);
		if(executorService!=null) {
			executorService.shutdown();
			threadPools.remove(threadPoolName);
		}
	}

	/**
	 * 返回线程池管理容器
	 * 可用于监控并显示所有线程池中的线程情况
	 * @return
	 */
	public static ConcurrentMap<String, ExecutorService> getLocalStoreMap() {
		return threadPools;
	}
}
