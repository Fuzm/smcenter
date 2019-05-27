package com.irdstudio.smcenter.agent.restful;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.irdstudio.smcenter.agent.restful.vo.ResponseVO;
import com.irdstudio.smcenter.agent.utils.AnsiLog;
import com.irdstudio.smcenter.core.batch.BatchRunEngine;

@RestController
@RequestMapping("/agent")
public class BatchController {

	@RequestMapping(value = "/batch/start", method = RequestMethod.GET)
	public ResponseVO startBatch(@RequestParam("batchId") String batchId, @RequestParam("batchAction") String batchAction) {
		AnsiLog.info("日终服务执行调用开始");
		ResponseVO vo = new ResponseVO();
		try {
			AnsiLog.info("日终服务执行开始, 批次ID：" + batchId + ", 批次动作：" + batchAction);
			BatchRunEngine batchEngine = new BatchRunEngine(batchAction, batchId);
			batchEngine.start();
			
			vo.setFlag("success");
			vo.setMsg("成功执行批次");
		} catch(Exception e) { 
			e.printStackTrace();
			AnsiLog.error("日终服务执行出错！", e);
		}
		AnsiLog.info("日终服务执行调用结束");
		return vo;
	}
}
