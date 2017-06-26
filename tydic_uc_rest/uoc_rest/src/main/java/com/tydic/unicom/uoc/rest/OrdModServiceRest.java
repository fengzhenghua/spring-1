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
import com.tydic.unicom.uoc.business.order.service.interfaces.JoinOrExitDepartTaskForceServDu;
import com.tydic.unicom.uoc.business.order.service.interfaces.OrderDetailServDu;
import com.tydic.unicom.uoc.business.order.service.interfaces.OrderEnquiryContactServDu;
import com.tydic.unicom.uoc.business.order.service.interfaces.OrderModServDu;
import com.tydic.unicom.uoc.business.order.service.interfaces.ServiceOrderServDu;
import com.tydic.unicom.uoc.business.order.service.vo.GetServOrderNoVo;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocException;
import com.tydic.unicom.webUtil.UocMessage;

@Controller
@RequestMapping(value = ControllerMappings.SERVICE_ORDER_REST)
public class OrdModServiceRest {

	Logger logger=Logger.getLogger(OrdModServiceRest.class);
	@Autowired
	private OrderModServDu orderModServDu;
	@Autowired
	private ServiceOrderServDu serviceOrderServDu;
	@Autowired
	private OrderDetailServDu orderDetailServDu;
	@Autowired
	private OrderEnquiryContactServDu orderEnquiryContactServDu;
	@Autowired
	private JoinOrExitDepartTaskForceServDu joinOrExitDepartTaskForceServDu;
	
	/*
	 *  UOC0001  服务订单状态关系维护
	 */
	@RequestMapping(value = UrlsMappings.OPERATE_ORD_MOD_STATE_RULE , method = RequestMethod.POST)
	@ResponseBody
	public UocMessage operateOrdModStateRule(ParaVo paraVo){
		UocMessage uocMessage = UocMessage.error( "","common.error");
		try {
			uocMessage = orderModServDu.syncOrdModStateRule(paraVo);
		} catch (Exception e) {
			e.printStackTrace();
			return uocMessage;
		}
		return uocMessage;
	}

	/*
	 * UOC0002 服务订单环节关系维护
	 */
	@RequestMapping(value = UrlsMappings.OPERATE_ORD_MOD_TACHE_RULE , method = RequestMethod.POST)
	@ResponseBody
	public UocMessage operateOrdModTacheRule(ParaVo paraVo){
		UocMessage uocMessage = UocMessage.error("","common.error");
		try {
			uocMessage = orderModServDu.syncOrdModTacheRule(paraVo);
		} catch (Exception e) {
			e.printStackTrace();
			return uocMessage;
		}
		return uocMessage;
	}

	/*
	 * UOC0026 服务订单状态关系查询，分页
	 */
	@RequestMapping(value = UrlsMappings.GET_ORD_MOD_STATE_RULE , method = RequestMethod.POST)
	@ResponseBody
	public UocMessage getOrdModStateRule(ParaVo paraVo){
		UocMessage uocMessage = UocMessage.error("","common.error");
		try {
			logger.info(paraVo.getJsession_id());
			logger.info("paraVo.toString()>>>>>>>>>>>>>>>>>>>>"+paraVo.getOper_type()+paraVo.getJson_info());
//			logger.info("param>>>>>>>>>>>>>>>>>>>>"+param);
//			logger.info("param>>>>>>>>>>>>>>>>>>>>"+param.toString());
			uocMessage = orderModServDu.searchOrdModStateRuleList(paraVo);
		} catch (Exception e) {
			e.printStackTrace();
			return uocMessage;
		}
		return uocMessage;
	}
	/*
	 *  UOC0027 服务订单环节关系查询，分页
	 */
	@RequestMapping(value = UrlsMappings.GET_ORD_MOD_TACHE_RULE , method = RequestMethod.POST)
	@ResponseBody
	public UocMessage getOrdModTacheRule(ParaVo paraVo){
		UocMessage uocMessage = UocMessage.error("","common.error");
		try {
			uocMessage = orderModServDu.searchOrdModTacheRuleList(paraVo);
		} catch (Exception e) {
			e.printStackTrace();
			return uocMessage;
		}
		return uocMessage;
	}

	/*
	 * 服务订单处理 UOC0008
	 */
	@RequestMapping(value = UrlsMappings.SERVICE_ORDER_OPERATE , method = RequestMethod.POST)
	@ResponseBody
	public UocMessage serviceOrderOperate(ParaVo  paraVo){
		UocMessage uocMessage = UocMessage.error("","common.error");
		try {
			uocMessage = serviceOrderServDu.syncNoneServiceOrder(paraVo);
		} catch (Exception e) {
			e.printStackTrace();
			return uocMessage;
		}
		return uocMessage;

	}

	/*
	 * 服务订单修改 UOC0009
	 */
	@RequestMapping(value = UrlsMappings.SERVICE_ORDER_CHANGE , method = RequestMethod.POST)
	@ResponseBody
	public UocMessage serviceOrderChange(ParaVo  paraVo){
		UocMessage uocMessage = UocMessage.error("","common.error");
		try {
			uocMessage = serviceOrderServDu.changeServiceOrder(paraVo);
		} catch (Exception e) {
			e.printStackTrace();
			return uocMessage;
		}
		return uocMessage;

	}

	/*
	 * 订单模板维护 UOC0012
	 */
	@RequestMapping(value = UrlsMappings.ORDER_MOD_MAINTAN , method = RequestMethod.POST)
	@ResponseBody
	public UocMessage orderModMainten(ParaVo  paraVo){
		UocMessage uocMessage = UocMessage.error("","common.error");
		try {
			uocMessage = orderModServDu.syncOrdMod(paraVo);
		} catch (Exception e) {
			e.printStackTrace();
			return uocMessage;
		}
		return uocMessage;
	}

	/*
	 * 订单模板应用表维护 UOC0013
	 */
	@RequestMapping(value = UrlsMappings.ORDER_MOD_APP_MAINTAN , method = RequestMethod.POST)
	@ResponseBody
	public UocMessage orderModAppMainten(ParaVo  paraVo){
		UocMessage uocMessage = UocMessage.error("","common.error");
		try {
			uocMessage = orderModServDu.syncOrdModApp(paraVo);
		} catch (Exception e) {
			e.printStackTrace();
			return uocMessage;
		}
		return uocMessage;
	}

	/*
	 * 任务包维护 UOC0061
	 */
	@RequestMapping(value = UrlsMappings.CODE_TASK_PKG_MAINTAN , method = RequestMethod.POST)
	@ResponseBody
	public UocMessage codeTaskPkgMainten(ParaVo paraVo){
		UocMessage uocMessage = UocMessage.error("","common.error");
		try {
			uocMessage = orderModServDu.syncCodeTaskPkg(paraVo);
		} catch (UocException e) {
			e.printStackTrace();
			return e.getUocMessage();
		} catch (Exception e) {
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("任务包维护异常");
			return uocMessage;
		}
		return uocMessage;
	}

	/*
	 * 任务包查询 分页 UOC00XX
	 */
	@RequestMapping(value = UrlsMappings.GET_CODE_TASK_PKG , method = RequestMethod.POST)
	@ResponseBody
	public UocMessage getCodeTaskPkg(ParaVo paraVo){
		UocMessage uocMessage = UocMessage.error("","common.error");
		try {
			uocMessage = orderModServDu.searchCodeTaskPkg(paraVo);
		} catch (Exception e) {
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("任务包查询异常");
			return uocMessage;
		}
		return uocMessage;
	}


	/*
	 * 订单模板查询 UOC0028
	 */
	@RequestMapping(value = UrlsMappings.GET_ORD_MOD , method = RequestMethod.POST)
	@ResponseBody
	public UocMessage getOrdMod(ParaVo  paraVo){
		UocMessage uocMessage = UocMessage.error("","common.error");
		try {
			uocMessage = orderModServDu.searchOrdMod(paraVo);
		} catch (Exception e) {
			e.printStackTrace();
			return uocMessage;
		}
		return uocMessage;
	}

	/*
	 * 订单模板应用表查询 UOC0029
	 */
	@RequestMapping(value = UrlsMappings.GET_ORD_MOD_APP , method = RequestMethod.POST)
	@ResponseBody
	public UocMessage getOrdModApp(ParaVo  paraVo){
		UocMessage uocMessage = UocMessage.error("","common.error");
		try {
			uocMessage = orderModServDu.searchOrdModAppList(paraVo);
		} catch (Exception e) {
			e.printStackTrace();
			return uocMessage;
		}
		return uocMessage;
	}

	/*
	 * UOC0010
	 */
	@RequestMapping(value = "getOrderDetail" , method = RequestMethod.POST)
	@ResponseBody
	public UocMessage getOrderDetail(ParaVo  paraVo){
		UocMessage uocMessage = UocMessage.error("","common.error");
		try {
			uocMessage = orderDetailServDu.getOrderDetail(paraVo);
		} catch (Exception e) {
			e.printStackTrace();
			return uocMessage;
		}
		return uocMessage;
	}


	/*
	 * 服务环节接口调用 UOC0052
	 */
	@RequestMapping(value = UrlsMappings.GET_ORDER_TACHE_CALL, method = RequestMethod.POST)
	@ResponseBody
	public UocMessage getOrderTacheCall(String jsession_id, String serv_order_no, String tache_code) {
		UocMessage uocMessage = new UocMessage();
		try {
			uocMessage = serviceOrderServDu.getOrderTacheCall(jsession_id, serv_order_no, tache_code);
		} catch (Exception e) {
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("环节接口调用异常");
			return uocMessage;
		}
		return uocMessage;
	}
	/*
	 * 获取服务订单号 UOC0060
	 */
	@RequestMapping(value = UrlsMappings.GET_SERV_ORDER_NO, method = RequestMethod.POST)
	@ResponseBody
	public UocMessage getServiceOrderNo(GetServOrderNoVo vo){
		UocMessage uocMessage = new UocMessage();
		try{
			uocMessage=serviceOrderServDu.getServiceOrderNo(vo);
		}catch(Exception e){
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("获取服务订单号异常");
			return uocMessage;
		}
		return uocMessage;
	}

	/*
	 * 任务默认分配规则维护 UOC0065
	 */
	@RequestMapping(value = UrlsMappings.TASK_ASSIGN_RULE_MAINTEN , method = RequestMethod.POST)
	@ResponseBody
	public UocMessage taskAssignRuleMainten(ParaVo paraVo){
		UocMessage uocMessage = UocMessage.error("","common.error");
		try {
			uocMessage = orderModServDu.syncTaskAssignRule(paraVo);
		} catch (UocException e) {
			e.printStackTrace();
			return e.getUocMessage();
		} catch (Exception e) {
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("任务默认分配规则维护异常");
			return uocMessage;
		}
		return uocMessage;
	}

	/*
	 * 任务默认分配规则查询 UOC0066
	 */
	@RequestMapping(value = UrlsMappings.GET_TASK_ASSIGN_RULE , method = RequestMethod.POST)
	@ResponseBody
	public UocMessage getTaskAssignRule(ParaVo paraVo){
		UocMessage uocMessage = UocMessage.error("","common.error");
		try {
			uocMessage = orderModServDu.searchTaskAssignRule(paraVo);
		} catch (Exception e) {
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("任务默认分配规则查询异常");
			return uocMessage;
		}
		return uocMessage;
	}

	/*
	 * 任务默认分配规则查询工号组
	 */
	@RequestMapping(value = UrlsMappings.QUERY_OPER_NO_GROUP, method = RequestMethod.POST)
	@ResponseBody
	public UocMessage queryOperNoGroup(String rule_id) {
		UocMessage uocMessage = new UocMessage();
		try {
			uocMessage = orderModServDu.queryOperNoGroup(rule_id);
		} catch (Exception e) {
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("任务默认分配规则查询工号组异常");
			return uocMessage;
		}
		return uocMessage;
	}

	/*
	 * 服务订单详情查询（触点专用） UOC0073
	 */
	@RequestMapping(value = UrlsMappings.GET_ORDER_ENQUIRY_CONTACT , method = RequestMethod.POST)
	@ResponseBody
	public UocMessage getOrderEnquiryContact(String jsession_id,String  serv_order_no){
		UocMessage uocMessage = new UocMessage();
		try {
			uocMessage = orderEnquiryContactServDu.queryOrderEnquiryContact(jsession_id, serv_order_no);
		} catch (Exception e) {
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("任务默认分配规则查询异常");
			return uocMessage;
		}
		return uocMessage;
	}
	
	
	/*
	 * 任务默认分配规则查询 UOC0079
	 */
	@RequestMapping(value = UrlsMappings.JOIN_OR_EXIT_DEPART_TASK_FORCE , method = RequestMethod.POST)
	@ResponseBody
	public UocMessage joinOrExitDepartTaskForce(String jsession_id,String oper_type,String quit_type){
		UocMessage uocMessage = UocMessage.error("","common.error");
		try {
			uocMessage = joinOrExitDepartTaskForceServDu.createJoinOrExitDepartTaskForce(jsession_id, oper_type, quit_type);
		} catch (Exception e) {
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("加入/退出部门任务组异常");
			return uocMessage;
		}
		return uocMessage;
	}
}
