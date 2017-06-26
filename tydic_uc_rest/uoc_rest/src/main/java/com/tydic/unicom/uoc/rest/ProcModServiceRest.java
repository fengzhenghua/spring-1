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
import com.tydic.unicom.uoc.business.order.service.interfaces.OrderModServDu;
import com.tydic.unicom.uoc.business.order.service.interfaces.ProcModServDu;
import com.tydic.unicom.uoc.business.order.service.interfaces.SendPhotoServDu;
import com.tydic.unicom.webUtil.UocMessage;

@Controller
@RequestMapping(value = ControllerMappings.PROC_MOD_SERVICE)
public class ProcModServiceRest {

	Logger logger=Logger.getLogger(ProcModServiceRest.class);
	@Autowired
	private OrderModServDu orderModServDu;
	@Autowired
	private ProcModServDu procModServDu;
	
	@Autowired
	private SendPhotoServDu sendPhotoServDu;

	/*
	 * 流程模板维护 UOC0017
	 */
	@RequestMapping(value = UrlsMappings.PROC_MOD_MAINTEN , method = RequestMethod.POST)
	@ResponseBody
	public UocMessage procModMainten(ParaVo  paraVo){
		UocMessage uocMessage = new UocMessage();
		try {
			uocMessage = procModServDu.syncProcOrdApp(paraVo);
		} catch (Exception e) {
			e.printStackTrace();
			return uocMessage;
		}
		return uocMessage;
	}

	/*
	 * 环境配置维护 UOC0018
	 */
	@RequestMapping(value = UrlsMappings.PROC_MOD_TACHE_MAINTEN , method = RequestMethod.POST)
	@ResponseBody
	public UocMessage procModTacheMainten(ParaVo  paraVo){
		UocMessage uocMessage = new UocMessage();
		try {
			uocMessage = procModServDu.syncProcModTache(paraVo);
		} catch (Exception e) {
			e.printStackTrace();
			return uocMessage;
		}
		return uocMessage;
	}

	/*
	 * 流程应用表查询 UOC0030
	 */
	@RequestMapping(value = UrlsMappings.GET_PROC_MOD_APP , method = RequestMethod.POST)
	@ResponseBody
	public UocMessage getProcModApp(ParaVo  paraVo){
		UocMessage uocMessage = new UocMessage();
		try {
			uocMessage = procModServDu.searchProcModApp(paraVo);
		} catch (Exception e) {
			e.printStackTrace();
			return uocMessage;
		}
		return uocMessage;
	}

	/*
	 * 环节查询 UOC0031
	 */
	@RequestMapping(value = UrlsMappings.GET_TACHE_MOD , method = RequestMethod.POST)
	@ResponseBody
	public UocMessage getTacheMod(ParaVo  paraVo){
		UocMessage uocMessage =  new UocMessage();
		try {
			uocMessage = procModServDu.searchProcMod(paraVo);
		} catch (Exception e) {
			e.printStackTrace();
			return uocMessage;
		}
		return uocMessage;
	}

	/*
	 * 获取流程图信息 UOC0037
	 * jsession_id,order_no,order_type 100销售订单，101服务订单
	 */
	@RequestMapping(value = UrlsMappings.GET_PROC_VIEW_INFO , method = RequestMethod.POST)
	@ResponseBody
	public UocMessage getProcViewInfo(ParaVo  paraVo){
		UocMessage uocMessage = new UocMessage();
		try {
			uocMessage = procModServDu.getProcViewInfo(paraVo);
		} catch (Exception e) {
			e.printStackTrace();
			return uocMessage;
		}
		return uocMessage;
	}
	
	/*
	 * 开户证件上传 UOC0071
	 *
	 */
	@RequestMapping(value = UrlsMappings.SEND_PHOTO_SERV , method = RequestMethod.POST)
	@ResponseBody
	public UocMessage sendPhotoServ(String jsession_id,String serv_order_no){
		UocMessage uocMessage = new UocMessage();
		try {
			uocMessage = sendPhotoServDu.getSendPhoto(jsession_id, serv_order_no);
		} catch (Exception e) {
			e.printStackTrace();
			return uocMessage;
		}
		return uocMessage;
	}
}
