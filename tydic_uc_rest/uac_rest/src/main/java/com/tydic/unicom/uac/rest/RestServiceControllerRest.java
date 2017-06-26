package com.tydic.unicom.uac.rest;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tydic.unicom.crm.web.commTools.JsonToBean;
import com.tydic.unicom.crm.web.uss.constants.ControllerMappings;
import com.tydic.unicom.crm.web.uss.constants.UrlsMappings;
import com.tydic.unicom.uac.business.common.interfaces.GetOptionalOperServDu;
import com.tydic.unicom.uac.business.common.interfaces.InfoInputOrderServDu;
import com.tydic.unicom.uac.business.common.interfaces.OperRelationServDu;
import com.tydic.unicom.uac.business.common.interfaces.OperServDu;
import com.tydic.unicom.uac.business.common.interfaces.UniformInfoOperServDu;
import com.tydic.unicom.uac.business.common.vo.InfoOperVo;
import com.tydic.unicom.uac.business.common.vo.OperRelationVo;
import com.tydic.unicom.uac.business.common.vo.RewardOrderInfoVo;
import com.tydic.unicom.uac.business.common.vo.UniformInfoOperVo;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

@RestController
@RequestMapping(value = ControllerMappings.REST_SERVICE_CONTROLLER_REST)
public class RestServiceControllerRest {

	private static Logger logger = Logger.getLogger(RestServiceControllerRest.class);

	@Autowired
	private UniformInfoOperServDu uniformInfoOperServDu;

	@Autowired
	private OperRelationServDu operRelationServDu;

	@Autowired
	private OperRest operRest;

	@Autowired
	private GetOtherInfoRest getOtherInfoRest;

	@Autowired
	private LoginAndLogoutRest loginAndLogoutRest;

	@Autowired
	private OperServDu operServDu;

	@Autowired
	private BaseOperRest baseOperRest;

	@Autowired
	private GetOptionalOperServDu getOptionalOperServDu;

	@Autowired
	private InfoInputOrderServDu infoInputOrderServDu;


	@SuppressWarnings("unchecked")
	@RequestMapping(value = UrlsMappings.REST_CONTROLLER_SERVICE,method=RequestMethod.POST)
	@ResponseBody
	public UocMessage restControllerService(String json_info,HttpSession session){
		UocMessage uocMessage = new UocMessage();
		try {
			json_info=URLDecoder.decode(json_info,"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("json_info请求格式不对");
			return uocMessage;
		}
		logger.info("==============通过能力平台调用云销售系统开始==============");
		if(json_info==null || "".equals(json_info)){
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("入参为空");
			return uocMessage;
		}
		JSONObject json = JSONObject.fromObject(json_info);
		String service_name=json.get("SERVICE_NAME").toString();

		if(service_name==null || "".equals(service_name)){
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("服务名为空");
			return uocMessage;
		}
		String json_in=json.get("param").toString();
		if(json_in==null || "".equals(json_in)){
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("服务接口入参为空");
			return uocMessage;
		}
		if("queryOperInfo".equals(service_name)){
			JSONObject jsonRes = JSONObject.fromObject(json_in);
			String jsession_id=jsonRes.get("jsession_id").toString();
			if(jsession_id==null || "".equals(jsession_id)){
				uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
				uocMessage.setContent("jsession_id不能为空");
				return uocMessage;
			}
			UniformInfoOperVo uniformInfoOperVo=new UniformInfoOperVo();
//			uniformInfoOperVo.setJsessionid(jsession_id);
			try {
				uniformInfoOperVo = uniformInfoOperServDu.getRedisUniformInfoOper(jsession_id);
			} catch (Exception e) {
				e.printStackTrace();
				uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
				uocMessage.setContent("获取工号信息异常");
				return uocMessage;
			}
			if(uniformInfoOperVo != null){
				OperRelationVo operRelationVo = new OperRelationVo();
				operRelationVo.setUniform_info_oper(uniformInfoOperVo.getUniform_info_oper());
				List<OperRelationVo> list = operRelationServDu.queryOperRelationByUniformInfoOper(operRelationVo);
				InfoOperVo infoOperVo = new InfoOperVo();
				infoOperVo.setOper_no(list.get(0).getInfo_oper());
				@SuppressWarnings("unused")
				InfoOperVo infoOperVoOut = null;
				UocMessage mesg = operRest.queryOperInfo(list.get(0).getInfo_oper());
				if("0000".equals(mesg.getRespCode())){
					uocMessage.setRespCode(RespCodeContents.SUCCESS);
					uocMessage.addArg("oper_info", mesg.getArgs().get("oper_info"));
					uocMessage.addArg("infoMenuList", mesg.getArgs().get("infoMenuList"));
				}
				else{
					uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
					uocMessage.setContent(mesg.getContent().toString());
				}
			}
			else{
				UocMessage mesg = operRest.queryOperInfo(jsession_id);
				if("0000".equals(mesg.getRespCode())){
					uocMessage.setRespCode(RespCodeContents.SUCCESS);
					uocMessage.addArg("oper_info", mesg.getArgs().get("oper_info"));
					uocMessage.addArg("infoMenuList", mesg.getArgs().get("infoMenuList"));
				}
				else{
					uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
					uocMessage.setContent(mesg.getContent().toString());
				}
			}
		}
		else if("getDepartInfo".equals(service_name)){
			JSONObject jsonRes = JSONObject.fromObject(json_in);
			String currDepartNo =jsonRes.get("currDepartNo").toString();
			String departNo=jsonRes.get("departNo").toString();
			String departName=jsonRes.get("departName").toString();
			UocMessage mesg = getOtherInfoRest.getDepartInfo(currDepartNo, departNo, departName);
			if("0000".equals(mesg.getRespCode())){
				uocMessage.setRespCode(RespCodeContents.SUCCESS);
				uocMessage.setContent("获取部门信息成功");
				uocMessage.addArg("DepartInfo", mesg.getArgs().get("DepartInfo"));
			}
			else{
				uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
				uocMessage.setContent(mesg.getContent().toString());
				return uocMessage;
			}
		}
		else if("queryDepartInfo".equals(service_name)){
			JSONObject jsonRes = JSONObject.fromObject(json_in);
			String jsession_id =jsonRes.get("jsession_id").toString();
			String region_id=jsonRes.get("region_id").toString();
			String depart_no=jsonRes.get("depart_no").toString();
			String depart_name=jsonRes.get("depart_name").toString();
			String query_type=jsonRes.get("query_type").toString();
			UocMessage mesg = getOtherInfoRest.queryDepartInfo(jsession_id, region_id, depart_no, depart_name, query_type);
			if("0000".equals(mesg.getRespCode())){
				uocMessage.setRespCode(RespCodeContents.SUCCESS);
				uocMessage.setContent("获取部门信息成功");
				uocMessage.addArg("depart_list", mesg.getArgs().get("depart_list"));
			}
			else{
				uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
				uocMessage.setContent(mesg.getContent().toString());
				return uocMessage;
			}
		}
		else if("getOperInfo".equals(service_name)){
			JSONObject jsonRes = JSONObject.fromObject(json_in);
			String currDepartNo =jsonRes.get("currDepartNo").toString();
			String operNo=jsonRes.get("operNo").toString();
			String operName=jsonRes.get("operName").toString();
			UocMessage mesg = getOtherInfoRest.getOperInfo(currDepartNo, operNo, operName);
			if("0000".equals(mesg.getRespCode())){
				uocMessage.setRespCode(RespCodeContents.SUCCESS);
				uocMessage.setContent("获取工号信息成功");
				uocMessage.addArg("OperInfo", mesg.getArgs().get("OperInfo"));
			}
			else{
				uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
				uocMessage.setContent(mesg.getContent().toString());
				return uocMessage;
			}
		}
		else if("getDepartInfoByOperNo".equals(service_name)){
			JSONObject jsonRes = JSONObject.fromObject(json_in);
			String operNo=jsonRes.get("operNo").toString();
			UocMessage mesg = getOtherInfoRest.getDepartInfoByOperNo(operNo);
			if("0000".equals(mesg.getRespCode())){
				uocMessage.setRespCode(RespCodeContents.SUCCESS);
				uocMessage.setContent("根据工号获取部门信息成功");
				uocMessage.addArg("depart_no", mesg.getArgs().get("depart_no"));
				uocMessage.addArg("depart_name", mesg.getArgs().get("depart_name"));
			}
			else{
				uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
				uocMessage.setContent(mesg.getContent().toString());
				return uocMessage;
			}
		}
		else if("login".equals(service_name)){
			JSONObject jsonRes = JSONObject.fromObject(json_in);
			String oper_no=jsonRes.get("oper_no").toString();
			String password=jsonRes.get("password").toString();
			UniformInfoOperVo vo = new UniformInfoOperVo();
			vo.setOper_no(oper_no);
			vo.setOper_pwd(password);
			vo.setLogin_flag("0");
			vo.setCID("");
			vo.setPhone_flag("1");
			vo.setMac_type("");
			vo.setMemo("uoc");
			UocMessage mesg = loginAndLogoutRest.login(vo, session);
			if("0000".equals(mesg.getRespCode())){
				Map<String, Object> operLogin = new HashMap<String, Object>();
				operLogin = (Map<String, Object>) mesg.getArgs().get("oper_login_vo");
				String jsessionid=operLogin.get("jsessionid").toString();
				uocMessage.setRespCode(RespCodeContents.SUCCESS);
				uocMessage.setContent("登录成功");
				uocMessage.addArg("jession_id", jsessionid);
			}
			else{
				uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
				uocMessage.setContent(mesg.getContent().toString());
				return uocMessage;
			}
		}
		else if("logout".equals(service_name)){
			JSONObject jsonRes = JSONObject.fromObject(json_in);
			String jession_id=jsonRes.get("jession_id").toString();
			UniformInfoOperVo vo = new UniformInfoOperVo();
			vo.setJsessionid(jession_id);
			UocMessage mesg = loginAndLogoutRest.logout(vo);
			if("0000".equals(mesg.getRespCode())){
				uocMessage.setRespCode(RespCodeContents.SUCCESS);
				uocMessage.setContent("登出成功");
			}
			else{
				uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
				uocMessage.setContent("登出失败");
			}
		}
		else if("isLogin".equals(service_name)){
			JSONObject jsonRes = JSONObject.fromObject(json_in);
			String jession_id=jsonRes.get("jsession_id").toString();
			try {
				UocMessage mesg = operServDu.isLogin(jession_id);
				if("0000".equals(mesg.getRespCode())){
					uocMessage.setRespCode(RespCodeContents.SUCCESS);
					uocMessage.setContent("已登录");
				}
				else{
					uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
					uocMessage.setContent("未登录");
				}
			} catch (Exception e) {
				e.printStackTrace();
				uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
				uocMessage.setContent("验证是否登录异常");
				return uocMessage;
			}
		}
		// UAC0003-获取可选工号-能力平台
		else if("getOptionalOperInfo".equals(service_name)){
			JSONObject jsonRes = JSONObject.fromObject(json_in);
			String jsession_id=jsonRes.get("jsession_id").toString();
			String oper_no = jsonRes.get("oper_no").toString();
			String oper_name = jsonRes.get("oper_name").toString();

			UocMessage mesg = baseOperRest.getOperInfoByDepartNo(jsession_id, oper_no, oper_name);
			if("0000".equals(mesg.getRespCode())){
				return mesg;
			}
			else{
				uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
				uocMessage.setContent(mesg.getContent());
				return uocMessage;
			}
		}
		// UAC0004-获取可选发展人-能力平台
		else if("queryInfoAgentDevelopers".equals(service_name)){

			JSONObject jsonRes = JSONObject.fromObject(json_in);
			String jsessionId=jsonRes.get("jsession_id").toString();
			String developer_no=jsonRes.get("developer_no").toString();
			String developer_name=jsonRes.get("developer_name").toString();
			String region=jsonRes.get("region").toString();
			String chnl_code=jsonRes.get("chnl_code").toString();
			UocMessage mesg = operRest.queryInfoAgentDevelopers(jsessionId, developer_no, developer_name,region,chnl_code);
			if("0000".equals(mesg.getRespCode())){
				return mesg;
			}
			else{
				uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
				uocMessage.setContent(mesg.getContent().toString());
				return uocMessage;
			}
		}
		// UAC0005-获取登录信息-能力平台
		else if("getBaseOperInfo".equals(service_name)){
			JSONObject jsonRes = JSONObject.fromObject(json_in);
			String jsessionId=jsonRes.get("jsession_id").toString();
			UocMessage mesg = baseOperRest.queryOperInfoByJsessionId(jsessionId);
			if("0000".equals(mesg.getRespCode())){
				return mesg;
			}
			else{
				uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
				uocMessage.setContent(mesg.getContent().toString());
				return uocMessage;
			}
		}
		// UAC0006-获取可选营业厅信息-能力平台
		else if("getBusinessHallInfo".equals(service_name)){
			JSONObject jsonRes = JSONObject.fromObject(json_in);
			String jsessionId = jsonRes.get("jsessionId").toString();
			String areaId = jsonRes.get("areaId").toString();
			String departNo = jsonRes.get("departNo").toString();
			String departName = jsonRes.get("departName").toString();
			UocMessage mesg = getOtherInfoRest.getBusinessHallInfo(jsessionId, areaId, departNo, departName);
			if("0000".equals(mesg.getRespCode())){
				uocMessage = mesg;
			}
			else{
				uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
				uocMessage.setContent(mesg.getContent());
			}
		}
		// UAC0008-获取可选渠道-能力平台
		else if("queryZbInfoAgents".equals(service_name)){
			JSONObject jsonRes = JSONObject.fromObject(json_in);
			String jsession_id = jsonRes.get("jsession_id").toString();
			String chnl_code = jsonRes.get("chnl_code").toString();
			String chnl_name = jsonRes.get("chnl_name").toString();
			String region_id = jsonRes.get("region_id").toString();
			UocMessage mesg = getOtherInfoRest.queryZbInfoAgents(jsession_id,chnl_code,chnl_name,region_id);
			if("0000".equals(mesg.getRespCode())){
				uocMessage = mesg;
			}
			else{
				uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
				uocMessage.setContent(mesg.getContent());
			}
		}
		// UAC0009-获取可选地区-能力平台
		else if("queryRegionInfo".equals(service_name)){
			JSONObject jsonRes = JSONObject.fromObject(json_in);
			String jsession_id = jsonRes.get("jsession_id").toString();
			String region_id = jsonRes.get("region_id").toString();
			String region_name = jsonRes.get("region_name").toString();
			String parent_region_id = jsonRes.get("parent_region_id").toString();
			String region_level = jsonRes.get("region_level").toString();
			UocMessage mesg = getOtherInfoRest.queryRegionInfo(jsession_id, region_id, region_name, parent_region_id, region_level);
			if("0000".equals(mesg.getRespCode())){
				uocMessage = mesg;
			}
			else{
				uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
				uocMessage.setContent(mesg.getContent());
			}
		}
		// UAC0010-获取可选即时激励工号
		else if ("getOptionalRewardOperInfo".equals(service_name)) {
			JSONObject jsonRes = JSONObject.fromObject(json_in);
			String jsession_id = jsonRes.get("jsession_id").toString();
			String developer_no = jsonRes.get("developer_no").toString();
			try {
				uocMessage = getOptionalOperServDu.getOptionalRewardOperInfo(jsession_id, developer_no);
			} catch (Exception e) {
				e.printStackTrace();
				uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
				uocMessage.setContent("获取可选即时激励工号异常");
				return uocMessage;
			}
		}
		// UAC0011-即时激励数据写入
		else if ("createRewardOrderInfo".equals(service_name)) {
			RewardOrderInfoVo vo = (RewardOrderInfoVo) JsonToBean.jsonInfoToBean(json_in, RewardOrderInfoVo.class);
			try {
				uocMessage = infoInputOrderServDu.createRewardOrderInfo(vo);
			} catch (Exception e) {
				e.printStackTrace();
				uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
				uocMessage.setContent("即时激励数据写入异常");
				return uocMessage;
			}
		}

		else{
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("服务名错误");
			return uocMessage;
		}
		return uocMessage;
	}
}
