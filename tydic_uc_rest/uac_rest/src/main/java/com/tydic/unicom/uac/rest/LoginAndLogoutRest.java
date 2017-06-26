package com.tydic.unicom.uac.rest;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tydic.unicom.crm.web.commTools.PropertiesUtil;
import com.tydic.unicom.crm.web.uss.constants.ControllerMappings;
import com.tydic.unicom.crm.web.uss.constants.UrlsMappings;
import com.tydic.unicom.uac.business.common.interfaces.OperServDu;
import com.tydic.unicom.uac.business.common.interfaces.UniformInfoOperServDu;
import com.tydic.unicom.uac.business.common.vo.UniformInfoOperVo;
import com.tydic.unicom.util.EncodeUtil;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

@Controller
@RequestMapping(value = ControllerMappings.LOGIN_AND_LOGOUT_REST)
public class LoginAndLogoutRest {

	private static Logger logger = Logger.getLogger(LoginAndLogoutRest.class);
	
	@Autowired
	private OperServDu operServDu;
	
	@Autowired
	private UniformInfoOperServDu uniformInfoOperServDu;
	
	@RequestMapping(value = UrlsMappings.LOGOUT , method = RequestMethod.POST)
	@ResponseBody
	public UocMessage logout(UniformInfoOperVo uniformInfoOperVo){
		logger.info("===============登出（rest）===============");
		UocMessage uocMessage = new UocMessage();
		boolean isok = false;
		try {
			isok = uniformInfoOperServDu.deleteRestLoginIn(uniformInfoOperVo);
			if(isok){
				uocMessage.setRespCode(RespCodeContents.SUCCESS);
				uocMessage.setContent("登出成功");
			}
			else{
				uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
				uocMessage.setContent("登出失败");
			}
			return uocMessage;
		} catch (Exception e) {
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("登出异常");
			return uocMessage;
		}	
	}
	
	@RequestMapping(value = UrlsMappings.LOGIN , method = RequestMethod.POST)
	@ResponseBody
	public UocMessage login(UniformInfoOperVo uniformInfoOperVo,HttpSession session){
		logger.info("================登陆(rest)=============");
		UocMessage uocMessage = new UocMessage();
		uniformInfoOperVo.setJsessionid(session.getId());
		String province_code = uniformInfoOperVo.getProvince_code();
		if(province_code==null||province_code.equals("")){
        	province_code=PropertiesUtil.getPropertiesForUrl("province_code");
        }
		uniformInfoOperVo.setProvince_code(province_code);
		if(null == uniformInfoOperVo.getLogin_flag()){
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("login_flag必须传参数");
			return uocMessage;
		}
		if(null == uniformInfoOperVo.getOper_pwd()){
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("oper_pwd必须传参数");
			return uocMessage;
		}
		/*if(null == uniformInfoOperVo.getCID()){
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("CID必须传参数");
			return uocMessage;
		}*/
		if ("0".equals(uniformInfoOperVo.getLogin_flag())) {
            if (StringUtils.isEmpty(uniformInfoOperVo.getOper_no())) {
            	uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
    			uocMessage.setContent("请输入工号");
    			return uocMessage;
            }
        }
		if ("1".equals(uniformInfoOperVo.getLogin_flag())) {
            if (StringUtils.isEmpty(uniformInfoOperVo.getOper_phone())) {
            	uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
    			uocMessage.setContent("请输入手机号");
    			return uocMessage;
            }
        }
		
		try {
			UniformInfoOperVo uniformInfoOperVoOut = uniformInfoOperServDu.findRestUniformLogin(uniformInfoOperVo);
			if (uniformInfoOperVoOut != null){
				String pwd = "";
				 if (!StringUtils.isEmpty(uniformInfoOperVo.getOper_no()) && !StringUtils.isEmpty(uniformInfoOperVo.getUniform_info_oper())) {
	                	pwd = uniformInfoOperVo.getOper_pwd();
	                }
				 else{
					 pwd = uniformInfoOperVo.getOper_pwd().trim();
				 }
				 if("cq".equals(province_code)){
	                	pwd = EncodeUtil.encode(uniformInfoOperVo.getOper_pwd()).trim();
	                }
				 logger.info("输入密码" + pwd);
	             String oper_pwd = uniformInfoOperVoOut.getOper_pwd().trim();
	             logger.info("db密码" + oper_pwd);
	             if (!(oper_pwd.equals(pwd))) {
	                    uniformInfoOperServDu.deleteUniformInfoOperRedis(uniformInfoOperVo);
	                    uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
	                    uocMessage.setContent("密码错误");
	                    return uocMessage;
	                }
	             else{
	            	 uocMessage.setRespCode(RespCodeContents.SUCCESS);
	            	 Map<String, Object> map = new HashMap<String, Object>();
	            	 map.put("jsessionid", uniformInfoOperVoOut.getJsessionid());
	            	 uocMessage.addArg("oper_login_vo", map);
	             }

			}
		} catch (Exception e) {
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("登陆异常");
			return uocMessage;
		}
		return uocMessage;		
	}
	
	@RequestMapping(value = UrlsMappings.GET_JSESSION_ID , method = RequestMethod.POST)
	@ResponseBody
	public UocMessage getJsessionId(HttpSession httpSession){
		logger.debug("================获取jsessionId===============");
		UocMessage uocMessage = new UocMessage();
		String jsessionId = httpSession.getId();
		logger.debug("=============获取的sessionid（httpSession）============"+jsessionId);
		String i = "0";
		try {
			for(;;){
				UocMessage uocMessagResult = operServDu.getOperInfoFromRedis(jsessionId);
				if(!"0000".equals(uocMessagResult.getRespCode())){
					break;
				}
				else{
					logger.debug("==============发现重复的jseesionid:============="+jsessionId);
					jsessionId = i + jsessionId.substring(i.length());
					logger.debug("============拼接新的jsessionId:=========="+jsessionId);
					i = String.valueOf(Integer.parseInt(i) + 1);
				}
			}
			uocMessage.setRespCode(RespCodeContents.SUCCESS);
			uocMessage.addArg("jsessionId", jsessionId);
			return uocMessage;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("=============异常流jsessionid============="+jsessionId);
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.addArg("jsessionId", jsessionId);
			return uocMessage;
		}
	}
}
