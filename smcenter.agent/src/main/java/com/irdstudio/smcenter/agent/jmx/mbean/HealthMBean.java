package com.irdstudio.smcenter.agent.jmx.mbean;

public interface HealthMBean {

	public abstract void startup();

	public abstract void await();

	public abstract String preload();

	public abstract boolean shutdown();
}
