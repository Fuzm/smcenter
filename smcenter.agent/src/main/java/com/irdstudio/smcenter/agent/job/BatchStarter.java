package com.irdstudio.smcenter.agent.job;

import java.sql.Connection;

import org.apache.commons.lang3.StringUtils;

import com.irdstudio.smcenter.agent.restful.vo.ResponseVO;
import com.irdstudio.smcenter.agent.utils.AnsiLog;
import com.irdstudio.smcenter.core.batch.BatchRunEngine;
import com.irdstudio.smcenter.core.schedule.SSrvsCronConf;

public class BatchStarter implements ScheduleJob {

	@Override
	public void doExcetue(Connection conn, SSrvsCronConf conf) {
		AnsiLog.info("日终服务执行调用开始");
		ResponseVO vo = new ResponseVO();
		try {
			if(conf != null && StringUtils.isNotEmpty(conf.getJobCode())) {
				String defaultAction = "run";
				String batchId = conf.getJobCode();
				
				AnsiLog.info("日终服务执行开始, 批次ID：" + batchId + ", 批次动作：" + defaultAction);
				BatchRunEngine batchEngine = new BatchRunEngine(defaultAction, batchId);
				batchEngine.start();
				
				vo.setFlag("success");
				vo.setMsg("成功发起批次");
			} else {
				AnsiLog.error("日终服务执行出错, 没有指定定时批次！");
			}
			 	
		} catch(Exception e) { 
			e.printStackTrace();
			AnsiLog.error("日终服务执行出错！", e);
		}
		AnsiLog.info("日终服务执行调用结束");
		
	}
}
