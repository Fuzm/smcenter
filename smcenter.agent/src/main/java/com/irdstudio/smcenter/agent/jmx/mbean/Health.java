package com.irdstudio.smcenter.agent.jmx.mbean;

import java.lang.management.ManagementFactory;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

public class Health implements HealthMBean {

	private String status;

	private Health() {
		this.status = "starting";
	}

	public void startup() {
		this.status = "success";
	}

	public void await() {
		try {
			LATCH.await();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public String preload() {
		return this.status;
	}

	public boolean shutdown() {
		LATCH.countDown();
		return true;
	}

	private static CountDownLatch LATCH = new CountDownLatch(1);
	private static AtomicBoolean INIT = new AtomicBoolean(false);
	private static Health INSTANCE = new Health();

	public static Health getInstance() {
		return INSTANCE;
	}

	public static void registMBean() {
		if (INIT.compareAndSet(false, true)) {
			try {
				MBeanServer mBeanServer = getMBeanServer();
				mBeanServer.registerMBean(INSTANCE, getHealthName());
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}

	public static void markStartup() {
		try {
			MBeanServer mBeanServer = getMBeanServer();
			mBeanServer.invoke(getHealthName(), "startup", new Object[0], new String[0]);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	public static void markAwait() throws InterruptedException {
		try {
			MBeanServer mBeanServer = getMBeanServer();
			mBeanServer.invoke(getHealthName(), "await", new Object[0], new String[0]);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	private static MBeanServer getMBeanServer() {
		MBeanServer mBeanServer;
		if (MBeanServerFactory.findMBeanServer(null).size() > 0) {
			mBeanServer = (MBeanServer) MBeanServerFactory.findMBeanServer(null).get(0);
		} else {
			mBeanServer = ManagementFactory.getPlatformMBeanServer();
		}
		return mBeanServer;
	}

	private static ObjectName getHealthName() throws MalformedObjectNameException {
		ObjectName name = new ObjectName("com.gdrcu.lp:type=Health");
		return name;
	}

}
