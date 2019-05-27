package com.irdstudio.smcenter.agent.job;

import java.sql.Connection;

import com.irdstudio.smcenter.core.schedule.SSrvsCronConf;

public interface ScheduleJob {

	public void doExcetue(Connection conn, SSrvsCronConf conf);
		
}
