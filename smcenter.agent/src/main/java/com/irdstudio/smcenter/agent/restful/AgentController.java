package com.irdstudio.smcenter.agent.restful;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.irdstudio.smcenter.agent.boot.AgentConstant;
import com.irdstudio.smcenter.agent.restful.vo.ResponseVO;
import com.irdstudio.smcenter.core.init.AgentInstInfo;
import com.irdstudio.smcenter.core.schedule.ScheduleJobUtil;

/**
 * 用于接收控制台的控制指令
 * @author ligm
 * @date 2018-06-15
 */
@RestController
@RequestMapping("/agent")
public class AgentController {
	
	/**
	 * 激活代理,加载定时调度
	 * @param agentId
	 * @return
	 */
	@RequestMapping(value = "/active", method = RequestMethod.GET)
	public ResponseVO activeAgent(@RequestParam("agentId") String agentId) {
		
		// 接收参数并判断是否传值
		ResponseVO vo = new ResponseVO();

		// 判断实例中的AngentId是否为空，如果为空则以传入的AgentId为当前Agent的标识
		if (StringUtils.isEmpty(AgentInstInfo.AGENT_ID)) {
			AgentInstInfo.AGENT_ID = agentId;
		}

		// 加载需要在Agent执行的定时任务
		ScheduleJobUtil.loadJobsForAgent();

		// 设定Agent实例为“待激活”
		AgentInstInfo.AGENT_STATE = AgentConstant.AGENT_STATE_ACTIVE;
		vo.setFlag("success");
		vo.setMsg("激活成功!");

		return vo;
	}
	
	/**
	 * 停止代理
	 * @param agentId
	 * @return
	 */
	@RequestMapping(value = "/stop", method = RequestMethod.GET)
	public ResponseVO stopAgent(@RequestParam("agentId") String agentId) {
		// 接收参数并判断是否传值
		ResponseVO vo = new ResponseVO();

		// 判断实例中的AngentId是否等于传入的agentId
		if(AgentInstInfo.AGENT_ID.equals(agentId)) {
			// 加载需要在Agent执行的定时任务
			ScheduleJobUtil.stopJobsForAgent();
			// 设定Agent实例为“停止”
			AgentInstInfo.AGENT_STATE = AgentConstant.AGENT_STATE_UNKNOW;
			vo.setFlag("success");
			vo.setMsg("停止成功!");
		} else {
			//agent标识不对
			vo.setFlag("fail");
			vo.setMsg("停止失败，agentId不是当前节点标识!");
		}

		return vo;
	}

}
