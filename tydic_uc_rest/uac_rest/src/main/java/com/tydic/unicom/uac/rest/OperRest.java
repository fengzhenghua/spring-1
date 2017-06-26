package com.tydic.unicom.uac.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.tydic.unicom.crm.web.commTools.JsonToBean;
import com.tydic.unicom.crm.web.uss.constants.Constants;
import com.tydic.unicom.crm.web.uss.constants.ControllerMappings;
import com.tydic.unicom.crm.web.uss.constants.UrlsMappings;
import com.tydic.unicom.crm.web.uss.listener.GlobalConfig;
import com.tydic.unicom.uac.business.common.interfaces.InfoAgentDevelopersServDu;
import com.tydic.unicom.uac.business.common.interfaces.InfoBaseOperServDu;
import com.tydic.unicom.uac.business.common.interfaces.InfoMenuServDu;
import com.tydic.unicom.uac.business.common.interfaces.OperServDu;
import com.tydic.unicom.uac.business.common.interfaces.RuleOperRoleServDu;
import com.tydic.unicom.uac.business.common.vo.InfoAgentDevelopersVo;
import com.tydic.unicom.uac.business.common.vo.InfoAuthorityVo;
import com.tydic.unicom.uac.business.common.vo.InfoMenuVo;
import com.tydic.unicom.uac.business.common.vo.InfoOperVo;
import com.tydic.unicom.uac.business.common.vo.OperRelationVo;
import com.tydic.unicom.uac.business.common.vo.RuleOperRoleVo;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

@RestController
@RequestMapping(value = ControllerMappings.OPER_REST)
public class OperRest {

	private static Logger logger = Logger.getLogger(OperRest.class);

	@Autowired
	private OperServDu operServDu;

	@Autowired
	private RuleOperRoleServDu ruleOperRoleServDu;

	@Autowired
	private InfoMenuServDu infoMenuServDu;

	@Autowired
	private InfoAgentDevelopersServDu infoAgentDevelopersServDu;

	@Autowired
	private InfoBaseOperServDu infoBaseOperServDu;

	@RequestMapping(value ="queryOperInfo" ,method=RequestMethod.POST)
	@ResponseBody
	public UocMessage queryOperInfo(String operNo){
		logger.info("---------通过工号获取工号信息---------");
		UocMessage uocMessage = new UocMessage();
		if(StringUtils.isEmpty(operNo)){
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("工号不能为空");
			return uocMessage;
		}
		try {
			InfoOperVo qryedOperVo = operServDu.queryInfoOperByLoginCode(operNo);
			if(qryedOperVo == null){
				uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
				uocMessage.setContent("获取用户信息失败");
				return uocMessage;
			}
			List<RuleOperRoleVo> roleVos = ruleOperRoleServDu.queryRuleOperRoleByInfoOper(qryedOperVo);
			String role_id ="";
			if(roleVos !=null && roleVos.size()>0){
				for(RuleOperRoleVo vo:roleVos){
            		role_id+=vo.getRole_id()+",";
            	}
			}
			if(!"".equals(role_id)){
            	role_id =role_id.substring(0, role_id.length()-1);
            }
			//获取菜单信息
			List<InfoMenuVo> infoMenuList = null;
			if(qryedOperVo != null){
				InfoAuthorityVo infoAuth = new InfoAuthorityVo();
            	infoAuth.setAuthority_id(GlobalConfig.newInstance().get("systemId").toString());
            	infoMenuList = infoMenuServDu.queryMenuByInfoOper(qryedOperVo, infoAuth);
			}
			//过滤菜单
            if(infoMenuList!=null && infoMenuList.size()>0){
            	for(int i=0;i<infoMenuList.size();){
            		if(!"2".equals(infoMenuList.get(i).getMenu_ctrl())){
            			infoMenuList.remove(i);
            		}else{
            			i++;
            		}
            	}
            }
            Map<String,Object> map =new HashMap<String,Object>();
			map.put("oper_no", qryedOperVo.getOper_no());
			map.put("oper_name", qryedOperVo.getOper_name());
			map.put("role_id",role_id);
			map.put("province_code", qryedOperVo.getZbInfoAgentVo().getProvince_code());
			map.put("area_code", qryedOperVo.getZbInfoAgentVo().getCity_code());
			map.put("depart_no", qryedOperVo.getDept_no());
			map.put("depart_name", qryedOperVo.getInfoDeptVo().getDept_name());
			map.put("channel_id",  qryedOperVo.getZbInfoAgentVo().getChnl_code());
			map.put("channel_type", qryedOperVo.getZbInfoAgentVo().getChnl_kind_id());
			map.put("district", qryedOperVo.getZbInfoAgentVo().getManager_area_code());
			uocMessage.setRespCode(RespCodeContents.SUCCESS);
			uocMessage.addArg("oper_info", map);
			uocMessage.addArg("infoMenuList", infoMenuList);
			return uocMessage;
		} catch (Exception e) {
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("获取工号信息异常");
			return uocMessage;
		}

	}
	/**
	 * 工号角色表维护UAC0001
	 * @param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value =UrlsMappings.OPERATE_RULE_OPER_ROLE ,method=RequestMethod.POST)
	@ResponseBody
	public UocMessage operateRuleOperRole(String jsession_id, String json_info, String oper_type) {
		UocMessage message = new UocMessage();
		Map<String, Object> oper_info = new HashMap<String, Object>();
		// 校验jsession_id
		try {
			UocMessage loginMes = infoBaseOperServDu.getOperInfoByJsessionId(jsession_id);
			if (!"0000".equals(loginMes.getRespCode())) {
				logger.info("------------" + loginMes.getContent() + "------------");
				message.setRespCode(RespCodeContents.SERVICE_FAIL);
				message.setContent("请先登录");
				return message;
			}
			oper_info = (Map<String, Object>) loginMes.getArgs().get("oper_info");
		} catch (Exception e1) {
			e1.printStackTrace();
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("登录校验异常");
				 return message;
			}
		String create_operator_id = (String) oper_info.get("oper_no");
		boolean operateResult = false;

		try {
			RuleOperRoleVo ruleOperRoleVo = (RuleOperRoleVo) JsonToBean.jsonInfoToBean(json_info, RuleOperRoleVo.class);
			if (ruleOperRoleVo != null) {
				if (Constants.ADD.equals(oper_type)) {
					RuleOperRoleVo paramVo = new RuleOperRoleVo();
					paramVo.setOper_id(ruleOperRoleVo.getOper_id());
					paramVo.setRole_id(ruleOperRoleVo.getRole_id());
					List<RuleOperRoleVo> roleVos = ruleOperRoleServDu.queryRuleOperRoleByOperNoOrRoleId(paramVo);
					if (roleVos != null && roleVos.size() > 0) {
						message.setRespCode(RespCodeContents.SERVICE_FAIL);
						message.setContent("该工号角色已经存在，不能新增");
						return message;
					}
					ruleOperRoleVo.setOper_role_id(ruleOperRoleVo.getOper_id() + ruleOperRoleVo.getRole_id());
					ruleOperRoleVo.setCreate_operator_id(create_operator_id);
					operateResult = ruleOperRoleServDu.createRuleOperRole(ruleOperRoleVo);
				} else if (Constants.DELETE.equals(oper_type)) {
					operateResult = ruleOperRoleServDu.deleteRuleOperRole(ruleOperRoleVo);
				}
			} else {
				message.setRespCode(RespCodeContents.SERVICE_FAIL);
				message.setContent("josn_info参数不对");
				return message;
			}
			if (operateResult) {
				message.setRespCode(RespCodeContents.SUCCESS);
				message.setContent("操作成功");
				return message;
			} else {
				message.setRespCode(RespCodeContents.SERVICE_FAIL);
				message.setContent("操作失败");
				return message;
				}

		} catch (Exception e) {
			e.printStackTrace();
			message.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			message.setContent("工号角色表操作异常");
				return message;
		}

	}

	/**
	 * 工号角色表查询UAC0002
	 * @param
	 * @return
	 */
	@RequestMapping(value =UrlsMappings.QUERY_RULE_OPER_ROLE ,method=RequestMethod.POST)
	@ResponseBody
	public UocMessage queryRuleOperRole(String jsession_id,String oper_no,String role_id){
		UocMessage message =new UocMessage();
		List<OperRelationVo> oper_relation_list =new ArrayList<OperRelationVo>();
		try {
			UocMessage loginMes = operServDu.isLogin(jsession_id);
			if (!"0000".equals(loginMes.getRespCode())) {
				logger.info("------------" + loginMes.getContent() + "------------");
				message.setRespCode(RespCodeContents.SERVICE_FAIL);
				message.setContent("请先登录");
				return message;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("登录校验异常");
			return message;
		}

		try {
			RuleOperRoleVo ruleOperRoleVo = new RuleOperRoleVo();
			ruleOperRoleVo.setOper_id(oper_no);
			ruleOperRoleVo.setRole_id(role_id);
			List<RuleOperRoleVo> roleVos = ruleOperRoleServDu.queryRuleOperRoleByOperNoOrRoleId(ruleOperRoleVo);
			if (roleVos != null && roleVos.size() > 0) {
				for (RuleOperRoleVo vo : roleVos) {
					OperRelationVo oper_relation = new OperRelationVo();
					oper_relation.setOper_no(vo.getOper_id());
					oper_relation.setRole_id(vo.getRole_id());
					oper_relation.setOper_name(vo.getOper_name());
					oper_relation.setRole_name(vo.getRole_name());
					oper_relation_list.add(oper_relation);
				}
			}
			message.setRespCode(RespCodeContents.SUCCESS);
			message.addArg("oper_relation_list", oper_relation_list);
			message.setContent("工号角色表查询成功");
			return message;
		} catch (Exception e) {
			e.printStackTrace();
			message.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			message.setContent("工号角色表查询异常");
			return message;
		}
	}

	/**
	 * 获取可选发展人UAC0004
	 * @param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value =UrlsMappings.QUERY_INFO_AGENT_DEVELOPERS ,method=RequestMethod.POST)
	@ResponseBody
	public UocMessage queryInfoAgentDevelopers(String jsession_id,String developer_no,String developer_name,String region,String chnl_code){
		UocMessage message =new UocMessage();
		InfoAgentDevelopersVo infoAgentDevelopersVo =new InfoAgentDevelopersVo();
		Map<String, Object> oper_info = new HashMap<String, Object>();
		//校验jsession_id
		try {
			UocMessage loginMes = infoBaseOperServDu.getOperInfoByJsessionId(jsession_id);
			if(!"0000".equals(loginMes.getRespCode())){
				 logger.info("------------"+loginMes.getContent()+"------------");
			  	 message.setRespCode(RespCodeContents.SERVICE_FAIL);
				 message.setContent("请先登录");
				 return message;
			}
			oper_info =(Map<String, Object>) loginMes.getArgs().get("oper_info");
		} catch (Exception e1) {
			e1.printStackTrace();
			 message.setRespCode(RespCodeContents.SERVICE_FAIL);
			 message.setContent("登录校验异常");
			 return message;
		}

		if(developer_no !=null && !"".equals(developer_no)){
			infoAgentDevelopersVo.setDev_code(developer_no);
		}
		if(developer_name !=null && !"".equals(developer_name)){
			infoAgentDevelopersVo.setDev_name(developer_name);
		}
		if(region !=null && !"".equals(region)){
			infoAgentDevelopersVo.setRegion(region);
		}
		if(chnl_code !=null && !"".equals(chnl_code)){
			infoAgentDevelopersVo.setChnl_code(chnl_code);
		}
		String area_code =(String) oper_info.get("area_code");
		infoAgentDevelopersVo.setLocal_net(area_code);
		List<InfoAgentDevelopersVo> developer_list =new ArrayList<InfoAgentDevelopersVo>();
		try {
			developer_list =infoAgentDevelopersServDu.queryInfoAgentDevelopers(infoAgentDevelopersVo);
		} catch (Exception e) {
			e.printStackTrace();
			message.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			message.setContent("获取可选发展人异常");
			return message;
		}

		message.setRespCode(RespCodeContents.SUCCESS);
		message.addArg("developer_list",developer_list);
		message.setContent("获取可选发展人成功");
		return message;
	}

}
