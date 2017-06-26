package com.tydic.unicom.uac.rest;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.tydic.unicom.crm.web.uss.constants.ControllerMappings;
import com.tydic.unicom.crm.web.uss.constants.UrlsMappings;
import com.tydic.unicom.uac.business.common.interfaces.InfoAgentServDu;
import com.tydic.unicom.uac.business.common.interfaces.InfoBaseOperServDu;
import com.tydic.unicom.uac.business.common.interfaces.InfoDeptServDu;
import com.tydic.unicom.uac.business.common.interfaces.InfoOperServDu;
import com.tydic.unicom.uac.business.common.interfaces.InfoRegionServDu;
import com.tydic.unicom.uac.business.common.vo.InfoDeptVo;
import com.tydic.unicom.uac.business.common.vo.InfoOperVo;
import com.tydic.unicom.uac.business.common.vo.InfoRegionVo;
import com.tydic.unicom.uac.business.common.vo.ZBInfoAgentCodeAndNameVo;
import com.tydic.unicom.uac.business.common.vo.ZBInfoAgentVo;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

@RestController
@RequestMapping(value = ControllerMappings.GET_OTHER_INFO_REST)
public class GetOtherInfoRest {
	
	private static Logger logger = Logger.getLogger(GetOtherInfoRest.class);
	
	@Autowired
	private InfoDeptServDu infoDeptServDu;
	
	@Autowired
	private InfoOperServDu infoOperServDu;
	
	@Autowired
	private InfoRegionServDu infoRegionServDu;
	
	@Autowired
	private InfoBaseOperServDu infoBaseOperServDu;
	
	@Autowired
	private InfoAgentServDu infoAgentServDu;
	
	@RequestMapping(value = UrlsMappings.GET_BUSINESS_HALL_INFO,method=RequestMethod.POST)
	@ResponseBody
	public UocMessage getBusinessHallInfo(String jsessionId,String areaId,String departNo,String departName){
		logger.info("===============获取可选营业厅信息（uac）===========");
		UocMessage uocMessage = new UocMessage();
		try {
			uocMessage = infoDeptServDu.getBusinessHallInfo(jsessionId, areaId, departNo, departName);
		} catch (Exception e) {
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("获取可选营业厅信息异常");
			return uocMessage;
		}
		return uocMessage;
		
	}
	
	@RequestMapping(value = UrlsMappings.GET_DEPART_INFO,method=RequestMethod.POST)
	@ResponseBody
	public UocMessage getDepartInfo(String currDepartNo,String departNo,String departName){
		logger.info("=============获取可选部门信息(uac)===============");
		UocMessage uocMessage = new UocMessage();
		
		if(StringUtils.isEmpty(currDepartNo)){			
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("当前部门号不能为空");
			return uocMessage;
		}
		
		try {
			List<InfoDeptVo> list = infoDeptServDu.queryInfoDeptByDeptNoAndDeptName(currDepartNo, departNo, departName);
			if(list != null && list.size()>0){	
				uocMessage.setRespCode(RespCodeContents.SUCCESS);
				uocMessage.addArg("DepartInfo", list);
			}
			else{
				uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
				uocMessage.setContent("没有查询到数据");
			}
			return uocMessage;
		} catch (Exception e) {
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("获取可选工号异常");
			return uocMessage;
		}
	}
	
	@RequestMapping(value = UrlsMappings.GET_OPER_INFO,method=RequestMethod.POST)
	@ResponseBody
	public UocMessage getOperInfo(String currDepartNo,String operNo,String operName){
		logger.info("=============获取可选工号信息(uac)===============");
		UocMessage uocMessage = new UocMessage();
		if(currDepartNo.isEmpty()){
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("当前部门号不能为空");
			return uocMessage;
		}
		try {
				List<InfoOperVo> list = infoOperServDu.queryInfoOperByOperNoAndOperName(currDepartNo, operNo, operName);
				if(list != null && list.size()>0){
					uocMessage.setRespCode(RespCodeContents.SUCCESS);
					uocMessage.addArg("OperInfo", list);
				}
				else{
					uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
					uocMessage.setContent("没有查询到数据");
				}
				return uocMessage;
		} catch (Exception e) {
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("获取可选工号信息异常");
			return uocMessage;
		}
	}
	
	@RequestMapping(value = UrlsMappings.GET_DEPART_INFO_BY_OPER_NO,method=RequestMethod.POST)
	@ResponseBody
	public UocMessage getDepartInfoByOperNo(String operNo){
		logger.info("==============根据工号获取对应部门信息(uac)================");
		UocMessage uocMessage = new UocMessage();
		try {
			InfoDeptVo infoDeptVo = infoDeptServDu.queryInfoDeptByOperNo(operNo);
			if(infoDeptVo != null){
				uocMessage.setRespCode(RespCodeContents.SUCCESS);
				uocMessage.addArg("depart_no", infoDeptVo.getDept_no());
				uocMessage.addArg("depart_name", infoDeptVo.getDept_name());
			}
			else{
				uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
				uocMessage.setContent("获取工号对应部门信息失败");
			}
			return uocMessage;
		} catch (Exception e) {
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("根据工号获取对应部门信息异常");
			return uocMessage;
		}
	}
	
	/**
	 * 获取可选部门UAC0007
	 * @param
	 * @return
	 */
	@RequestMapping(value =UrlsMappings.QUERY_DEPART_INFO ,method=RequestMethod.POST)
	@ResponseBody
	public UocMessage queryDepartInfo(String jsession_id,String region_id,String depart_no,String depart_name,String query_type){
		UocMessage uocMessage =new UocMessage();
	
		
		if(StringUtils.isEmpty(jsession_id)){			
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("jsession_id不能为空");
			return uocMessage;
		}
		
		try {		
			List<InfoDeptVo> list = infoDeptServDu.queryInfoDepts( jsession_id, region_id, depart_no, depart_name, query_type);
			if(list != null && list.size()>0){	
				uocMessage.setRespCode(RespCodeContents.SUCCESS);
				uocMessage.addArg("depart_list", list);
			}
			else{
				uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
				uocMessage.setContent("没有查询到数据");
			}
			return uocMessage;
		} catch (Exception e) {
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("获取可选工号异常");
			return uocMessage;
		}
	}
	
	/**
	 * UOC0008REST-获取可选渠道
	 * @param
	 * @return
	 */
	@RequestMapping(value =UrlsMappings.QUERY_ZB_INFO_AGENTS ,method=RequestMethod.POST)
	@ResponseBody
	public UocMessage queryZbInfoAgents(String jsession_id,String chnl_code,
		String chnl_name,String region_id){
		UocMessage respUocMessage = new UocMessage();
		
		// 校验jsession_id
		if( jsession_id==null || "".equals(jsession_id)){
			respUocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			respUocMessage.setContent("jsession_id不能为空");
			return respUocMessage;
		}
		try {
			UocMessage loginMes = infoBaseOperServDu.getOperInfoByJsessionId(jsession_id);
			if(!"0000".equals(loginMes.getRespCode())){
				 respUocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
				 respUocMessage.setContent("请先登录");
				 return respUocMessage;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			respUocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			respUocMessage.setContent("登录校验异常");
			return respUocMessage;
		}
		
		// 封装请求参数
		ZBInfoAgentVo zBInfoAgentVo = new ZBInfoAgentVo();
		zBInfoAgentVo.setChnl_code(chnl_code);
		zBInfoAgentVo.setChnl_name(chnl_name);
		zBInfoAgentVo.setManager_area_code(region_id);
		
		List<ZBInfoAgentCodeAndNameVo> zBInfoAgentVos = null;
		try{
			zBInfoAgentVos = infoAgentServDu.queryZbInfoAgents(zBInfoAgentVo);
		}catch(Exception e){
			respUocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			respUocMessage.setContent("获取可选渠道异常");
			return respUocMessage;
		}
		
		if( zBInfoAgentVos != null && zBInfoAgentVos.size() > 0){
			respUocMessage.setRespCode(RespCodeContents.SUCCESS);
			respUocMessage.setContent("获取可选渠道正常");
			respUocMessage.addArg("chnl_list", zBInfoAgentVos);
			return respUocMessage;
		}
		
		respUocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
		respUocMessage.setContent("数据为空");
		return respUocMessage;
	}
	
	/**
	 * UOC0009REST-获取可选地区
	 * @param
	 * @return
	 */
	@RequestMapping(value =UrlsMappings.QUERY_REGION_INFO ,method=RequestMethod.POST)
	@ResponseBody
	public UocMessage queryRegionInfo(String jsession_id,String region_id,
		String region_name,String parent_region_id,String region_level){
		UocMessage respUocMessage = new UocMessage();
		
		// 校验jsession_id
		if( jsession_id==null || "".equals(jsession_id)){
			respUocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			respUocMessage.setContent("jsession_id不能为空");
			return respUocMessage;
		}
		try {
			UocMessage loginMes = infoBaseOperServDu.getOperInfoByJsessionId(jsession_id);
			if(!"0000".equals(loginMes.getRespCode())){
				 respUocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
				 respUocMessage.setContent("请先登录");
				 return respUocMessage;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			respUocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			respUocMessage.setContent("登录校验异常");
			return respUocMessage;
		}
		
		// 封装请求参数
		InfoRegionVo infoRegionVo = new InfoRegionVo();
		infoRegionVo.setRegion_id(region_id);
		infoRegionVo.setRegion_name(region_name);
		infoRegionVo.setRegion_level(region_level);
		infoRegionVo.setParent_region_id(parent_region_id);
		
		List<InfoRegionVo> infoRegionVos = null;
		try{
			infoRegionVos = infoRegionServDu.queryInfoRegions(infoRegionVo);
		}catch(Exception e){
			respUocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			respUocMessage.setContent("获取可选地区异常");
			return respUocMessage;
		}
		
		if( infoRegionVos != null && infoRegionVos.size() > 0){
			respUocMessage.setRespCode(RespCodeContents.SUCCESS);
			respUocMessage.setContent("获取可选地区正常");
			respUocMessage.addArg("region_list", infoRegionVos);
			return respUocMessage;
		}
		
		respUocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
		respUocMessage.setContent("数据为空");
		return respUocMessage;
	}
	
}
