package com.tydic.unicom.uoc.rest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tydic.unicom.crm.web.uss.constants.ControllerMappings;
import com.tydic.unicom.crm.web.uss.constants.UrlsMappings;
import com.tydic.unicom.uoc.business.common.vo.ParaVo;
import com.tydic.unicom.uoc.business.order.service.interfaces.ArtificialTaskServDu;
import com.tydic.unicom.uoc.business.order.service.vo.ArtificialTaskVo;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocException;
import com.tydic.unicom.webUtil.UocMessage;

@Controller
@RequestMapping(value = ControllerMappings.ARTIFICIAL_TASK_REST)
public class ArtificialTaskRest {

	Logger logger = Logger.getLogger(ArtificialTaskRest.class);

	@Autowired
	private ArtificialTaskServDu artificialTaskServDu;

	@RequestMapping(value = UrlsMappings.CREATE_ARTIFICIAL_TASK , method = RequestMethod.POST)
	@ResponseBody
	public UocMessage createHandingArtificialTask(ArtificialTaskVo vo){
		logger.info("rest-----------createHandingArtificialTask");
		UocMessage message =new UocMessage();
		try {
			message = artificialTaskServDu.createHandingArtificialTask(vo);
		} catch (UocException e) {
			e.printStackTrace();
			return e.getUocMessage();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("人工任务处理异常");
			return message;
		}

		return message;

	}



	@RequestMapping(value = UrlsMappings.GET_ARTIFICIAL_TASK_LIST , method = RequestMethod.POST)
	@ResponseBody
	public UocMessage getArtificialTaskList(ArtificialTaskVo vo){
		logger.info("rest-----------getArtificialTaskList");
		UocMessage message =new UocMessage();
		try {
			message = artificialTaskServDu.getArtificialTaskList(vo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("人工任务列表获取异常");
			return message;

		}
		return message;

	}

	/**
	 * UOC0034 任务分配
	 * @param jsession_id	  登陆验证串
     * @param order_no		  订单编号
	 * @param oper_type	  操作类型：100部门共享任务锁定,200部门共享任务解锁,300分配任务给目标工号,400 分配任务给目标部门
	 * @param json_info{target_oper,target_depart}	 target_oper,oper_type =300时必填,target_depart oper_type =400时必填
	 */
	@RequestMapping(value = UrlsMappings.CREATE_TASK_ASSIGNMENT , method = RequestMethod.POST)
	@ResponseBody
	public UocMessage createTaskAssignment(ParaVo paraVo){
		logger.info("rest-----------createTaskAssignment");
		UocMessage message =new UocMessage();
		try {
			message = artificialTaskServDu.createTaskAssignment(paraVo);
		} catch (UocException e) {
			e.printStackTrace();
			return e.getUocMessage();
		} catch (Exception e) {
			e.printStackTrace();
			message.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			message.setContent("任务分配接口异常");
			return message;
		}

		return message;
	}

	/**
	 * UOC0036 获取任务详情
	 * @param jsession_id	  登陆验证串
     * @param order_no		  订单编号
	 */
	@RequestMapping(value = UrlsMappings.GET_TASK_DETAILINFO , method = RequestMethod.POST)
	@ResponseBody
	public UocMessage getTaskDetailInfo(ParaVo paraVo){
		logger.info("rest-----------getTaskDetailInfo");
		UocMessage message =new UocMessage();
		try {
			message = artificialTaskServDu.getTaskDetailInfo(paraVo.getJsession_id(), paraVo.getOrder_no());
		} catch (Exception e) {
			e.printStackTrace();
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("获取任务详情失败！");
			return message;
		}
		return message;
	}

	/**
	 * UOC0032 根据type_code查询业务（operCode）、产品 （prodCode）
	 * @param jsession_id	  登陆验证串
	 * @param type_code
	 */
	@RequestMapping(value = UrlsMappings.GET_TASK_QUERY_INFO , method = RequestMethod.POST)
	@ResponseBody
	public UocMessage getTaskQueryInfo(String jsession_id,String type_code){
		logger.info("rest-----------getTaskQueryInfo");
		UocMessage message =new UocMessage();
		try {
			message = artificialTaskServDu.getTaskQueryInfo(jsession_id, type_code);
		} catch (Exception e) {
			e.printStackTrace();
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("获取数据失败！");
			return message;
		}
		return message;
	}

	/**
	 * UOC0046 获取可选任务包
	 * @param jsession_id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = UrlsMappings.GET_OPTIONAL_TASK_PKG_LIST, method = RequestMethod.POST)
	@ResponseBody
	public UocMessage getOptionalTaskPkg(ParaVo paraVo) {
		logger.info("rest-----------getTaskQueryInfo");
		UocMessage message = new UocMessage();
		try {
			message = artificialTaskServDu.getOptionalTaskPkgList(paraVo);
		} catch (Exception e) {
			e.printStackTrace();
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("获取可选任务包失败！");
			return message;
		}
		return message;
	}

	/**
	 * UOC0047 领取任务包
	 * @param jsession_id
	 * @param pkg_id
	 * @param pkg_num
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = UrlsMappings.GET_TASK_PACKAGE, method = RequestMethod.POST)
	@ResponseBody
	public UocMessage getTaskPackage(String jsession_id, String pkg_id, String pkg_num) {
		logger.info("rest-----------getTaskQueryInfo");
		UocMessage message = new UocMessage();
		try {
			message = artificialTaskServDu.getTaskPackage(jsession_id, pkg_id, pkg_num);
		} catch (UocException e) {
			e.printStackTrace();
			return e.getUocMessage();
		} catch (Exception e) {
			e.printStackTrace();
			message.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			message.setContent("领取任务包接口异常");
			return message;
		}
		return message;
	}


	@RequestMapping(value = UrlsMappings.TASK_STATISTICAL, method = RequestMethod.POST)
	@ResponseBody
	public UocMessage taskStatistics(ArtificialTaskVo vo) {
		logger.info("rest-----------taskStatistics");
		UocMessage message = new UocMessage();
		try {
			message = artificialTaskServDu.getTaskGroupByTacheCode(vo);
		} catch (UocException e) {
			e.printStackTrace();
			return e.getUocMessage();
		} catch (Exception e) {
			e.printStackTrace();
			message.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			message.setContent("任务环节统计异常");
			return message;
		}
		return message;
	}
}
