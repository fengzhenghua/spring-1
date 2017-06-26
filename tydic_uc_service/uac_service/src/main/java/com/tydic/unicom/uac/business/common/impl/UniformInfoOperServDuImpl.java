package com.tydic.unicom.uac.business.common.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

//import com.tydic.unicom.service.sequence.service.interfaces.SequenceGenServ;
import com.tydic.unicom.uac.base.database.interfaces.InfoAgentServ;
import com.tydic.unicom.uac.base.database.interfaces.InfoOperServ;
import com.tydic.unicom.uac.base.database.interfaces.LogUniformInfoOperServ;
import com.tydic.unicom.uac.base.database.interfaces.UniformInfoOperServ;
import com.tydic.unicom.uac.base.database.interfaces.ZBAgentRelationServ;
import com.tydic.unicom.uac.base.database.interfaces.ZBAgentServ;
import com.tydic.unicom.uac.base.database.po.InfoAgentPo;
import com.tydic.unicom.uac.base.database.po.InfoOperPo;
import com.tydic.unicom.uac.base.database.po.LogUniformInfoOperPo;
import com.tydic.unicom.uac.base.database.po.UniformInfoOperPo;
import com.tydic.unicom.uac.base.database.po.ZBInfoAgentPo;
import com.tydic.unicom.uac.base.database.po.ZBInfoAgentRelationPo;
import com.tydic.unicom.uac.business.common.interfaces.UniformInfoOperServDu;
import com.tydic.unicom.uac.business.common.vo.InfoOperVo;
import com.tydic.unicom.uac.business.common.vo.UniformInfoOperVo;
import com.tydic.unicom.uac.service.interfaces.RedisServiceServDu;
import com.tydic.unicom.uac.service.vo.RedisDataVo;
import com.tydic.unicom.util.EncodeUtil;
import com.tydic.unicom.webUtil.UocMessage;

public class UniformInfoOperServDuImpl implements UniformInfoOperServDu{

	private static String LOGIN_FLAG_IN = "1";//登录操作
	private static String LOGIN_FLAG_OUT = "0";//登出操作
	private static String LOGIN_FLAGING = "2";//已经登录
	private static String LOGIN_UPDPW = "3";//修改密码

	private static Logger logger = Logger.getLogger(UniformInfoOperServDuImpl.class);

	@Autowired
	private UniformInfoOperServ uniformInfoOperServ;

	@Autowired
	private LogUniformInfoOperServ logUniformInfoOperServ;

	/*@Autowired
	private SequenceGenServ sequenceGenServ;*/

	@Autowired
	private RedisServiceServDu redisServiceServDu;

	@Autowired
	private InfoOperServ infoOperServ;

	@Autowired
	private InfoAgentServ infoAgentServ;

	@Autowired
	private ZBAgentRelationServ zBAgentRelationServ;

	@Autowired
	private ZBAgentServ zBAgentServ;

	@Override
	public UniformInfoOperVo findUniformLogin(UniformInfoOperVo uniformInfoOperVo) throws Exception {
		logger.debug("==============统一工号登陆===============");
		UniformInfoOperPo uniformInfoOperPo = new UniformInfoOperPo();
		BeanUtils.copyProperties(uniformInfoOperVo, uniformInfoOperPo);
		UniformInfoOperPo uniformInfoOperPoResult = uniformInfoOperServ.uniformLoginIn(uniformInfoOperPo);
		if(uniformInfoOperPoResult == null){
			logger.info("============未查询到统一工号信息=============");
			UniformInfoOperVo vo = new UniformInfoOperVo();
			vo.setError_type("1");
			return vo;
		}
		else{
			uniformInfoOperVo.setUniform_info_oper(uniformInfoOperPoResult.getUniform_info_oper());
			uniformInfoOperVo.setOper_no(uniformInfoOperPoResult.getOper_no());
			//密码加密
			if(uniformInfoOperVo.getOper_pwd().length() == 32){
				uniformInfoOperVo.setOper_pwd( uniformInfoOperVo.getOper_pwd() );
			}
			else{
				uniformInfoOperVo.setOper_pwd(EncodeUtil.encode(uniformInfoOperVo.getOper_pwd()));
			}
			// 如果不是自动登录，验证密码
			if (!"autoLogin".equals(uniformInfoOperVo.getUniform_oper_role_type())) {
				String pwd = uniformInfoOperVo.getOper_pwd().trim();
				logger.debug("=================输入密码:" + pwd);
				String oper_pwd = uniformInfoOperPoResult.getOper_pwd().trim();
				logger.debug("=================数据库存储密码：" + oper_pwd);
				if(!(oper_pwd.equals(pwd))){
					logger.debug("=========================验证密码失败!!");
					UniformInfoOperVo vo = new UniformInfoOperVo();
					vo.setError_type("2");
					return vo;
				}
			}

			// 记录登进信息
			LogUniformInfoOperPo logUniformInfoOperPo = new LogUniformInfoOperPo();
			Long seq = System.currentTimeMillis();
			logUniformInfoOperPo.setLog_id(seq.toString());
			logUniformInfoOperPo.setLog_flag(LOGIN_FLAG_IN);
			logUniformInfoOperPo.setUniform_info_oper(uniformInfoOperPoResult.getUniform_info_oper());
			logUniformInfoOperPo.setJsessionid(uniformInfoOperPoResult.getJsessionid());
			logUniformInfoOperPo.setOper_no(uniformInfoOperPoResult.getOper_no());
			boolean logResult = logUniformInfoOperServ.addLogUniformInfoOper(logUniformInfoOperPo);
			logger.debug("=======================记录登进信息结果：" + logResult);
			BeanUtils.copyProperties(uniformInfoOperPoResult, uniformInfoOperVo);
			return uniformInfoOperVo;
		}

	}

	@Override
	public boolean deleteUniformInfoOperRedis(UniformInfoOperVo uniformInfoOperVo) throws Exception {
		logger.info("============删除登陆人信息（redis）============");
		UocMessage uocMessage = redisServiceServDu.deleteDataFromRedis(uniformInfoOperVo.getJsessionid());
		if("0000".equals(uocMessage.getRespCode())){
			return true;
		}
		else{
			return false;
		}
	}

	@Override
	public boolean addUniformInfoOperRedis(UniformInfoOperVo uniformInfoOperVo) throws Exception {
		logger.debug("=============记录登陆人信息到redis=============");
		RedisDataVo redisDataVo = new RedisDataVo();
		redisDataVo.setId(uniformInfoOperVo.getJsessionid());
		redisDataVo.setObj(uniformInfoOperVo);
		UocMessage uocMessage = redisServiceServDu.createDataToRedis(redisDataVo);
		if("0000".equals(uocMessage.getRespCode())){
			return true;
		}
		else{
			return false;
		}
	}

	@Override
	public Map<String, Object> getZbInfoAgentMap(String operNo){
		Map<String,Object> map = new HashMap<String,Object>();
		try{
			InfoOperPo infoOperPo = new InfoOperPo();
			infoOperPo.setOper_no(operNo);
			List<InfoOperPo> infoOperList = infoOperServ.queryInfoOperOperNo(infoOperPo);
			if(infoOperList != null && infoOperList.size()>0){
				InfoAgentPo InfoAgentPo = new InfoAgentPo();
				InfoAgentPo.setDept_no(infoOperList.get(0).getDept_no());
				List<InfoAgentPo> infoAgents = infoAgentServ.queryInfoAgentByDeptNo(InfoAgentPo);
				if(infoAgents != null && infoAgents.size() > 0){
					ZBInfoAgentRelationPo zbInfoAgentRelationPo = new ZBInfoAgentRelationPo();
					zbInfoAgentRelationPo.setAgent_no(infoAgents.get(0).getAgent_no());
					ZBInfoAgentRelationPo zbInfoAgentRelationResult = zBAgentRelationServ.queryZBInfoAgentRelationByAgentNo(zbInfoAgentRelationPo);
					if(zbInfoAgentRelationResult != null ){
						ZBInfoAgentPo zbInfoAgentPo = new ZBInfoAgentPo();
						zbInfoAgentPo.setChnl_code(zbInfoAgentRelationResult.getChnl_code());
						ZBInfoAgentPo zbInfoAgentPoResult = zBAgentServ.queryZBInfoAgentByChnlCode(zbInfoAgentPo);
						map.put("departId", zbInfoAgentPoResult.getChnl_code());
						map.put("eparchyCode", zbInfoAgentPoResult.getCity_code());
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public UniformInfoOperVo getRedisUniformInfoOper(String jsessionId) throws Exception {
		logger.info("=========================根据jsessionId去redis获取已登录的统一工号信息");
		UocMessage uocMessageResult = redisServiceServDu.queryDataFromRedis(jsessionId);
		if("0000".equals(uocMessageResult.getRespCode())){
			RedisDataVo redisDataResult = (RedisDataVo) uocMessageResult.getArgs().get("RedisData");
			UniformInfoOperVo UniformInfoOperVoOut = (UniformInfoOperVo) redisDataResult.getObj();
			return UniformInfoOperVoOut;
		}
		else{
			return null;
		}
	}

	@Override
	public UniformInfoOperVo findRestUniformLogin(UniformInfoOperVo uniformInfoOperVo) throws Exception {
		UniformInfoOperPo uniformInfoOper = new UniformInfoOperPo();
//		InfoOperPo infoOperIn = new InfoOperPo();
		String mac_type = uniformInfoOperVo.getMac_type();
		UniformInfoOperPo uniformInfoOperOut = null;
		if ("0".equals(uniformInfoOperVo.getLogin_flag())){
			BeanUtils.copyProperties(uniformInfoOperVo, uniformInfoOper);
			uniformInfoOper.setUniform_info_oper(uniformInfoOperVo.getOper_no());
			uniformInfoOperOut = uniformInfoOperServ.uniformLoginIn(uniformInfoOper);

			if(uniformInfoOperOut==null){
				String oper_device_number = uniformInfoOper.getUniform_info_oper();
				if ( oper_device_number!=null && oper_device_number.length()==11){
					UniformInfoOperPo uni1 = new UniformInfoOperPo();
					uni1.setOper_device_number(oper_device_number);
					List<UniformInfoOperPo> listuniforms = uniformInfoOperServ.queryUniformInfoOperByUniformOperOrDeviceNumber(uni1);
					if (listuniforms.size()>1) {
						//同一个手机号码有多个工号的，不给登录。错误信息先不透传
					}
					else if (listuniforms.size()==1) {
						uniformInfoOperOut = listuniforms.get(0);
						uniformInfoOperVo.setOper_no(uniformInfoOperOut.getUniform_info_oper());
					}
				}
			}
			if (uniformInfoOperOut != null) {
				String uniform_info_oper = uniformInfoOperVo.getOper_no();
				if (!uniform_info_oper.equals(uniformInfoOperOut.getUniform_info_oper())) {
					return null;
				}
			}
		}
		UniformInfoOperVo result = new UniformInfoOperVo();
		if (uniformInfoOperOut != null){
			LogUniformInfoOperPo logUniformInfoOper = new LogUniformInfoOperPo();
			Long seq = System.currentTimeMillis();
			logUniformInfoOper.setLog_id(seq.toString());
			logUniformInfoOper.setLog_flag(LOGIN_FLAG_IN);
			logUniformInfoOper.setUniform_info_oper(uniformInfoOperOut.getUniform_info_oper());
			logUniformInfoOper.setOper_no(uniformInfoOperOut.getOper_no());
			logUniformInfoOper.setJsessionid(uniformInfoOperVo.getJsessionid());
			logUniformInfoOper.setMac_type(mac_type);
			logUniformInfoOperServ.addLogUniformInfoOper(logUniformInfoOper);
			BeanUtils.copyProperties(uniformInfoOperOut, result);
			result.setJsessionid(uniformInfoOperVo.getJsessionid());
			result.setCID(uniformInfoOperVo.getCID());
			result.setPhone_flag(uniformInfoOperVo.getPhone_flag());

			RedisDataVo redisDataVo = new RedisDataVo();
			redisDataVo.setId(result.getJsessionid());
			redisDataVo.setObj(result);
			redisServiceServDu.createDataToRedis(redisDataVo);

			if(uniformInfoOperOut.getOper_no() != null){
				InfoOperPo infoOper = new InfoOperPo();
				InfoOperVo infoOperVo = new InfoOperVo();
				infoOper.setJsessionid(uniformInfoOperVo.getJsessionid());
				infoOper.setOper_no(uniformInfoOperOut.getOper_no());
				InfoOperPo infoOpers = infoOperServ.getInfoOperByLoginCode(infoOper);
				if(infoOpers != null){
					BeanUtils.copyProperties(infoOpers, infoOperVo);
					result.setInfoOperVo(infoOperVo);
				}
			}
		}
		else{
			return null;
		}
		return result;
	}

	@Override
	public boolean deleteRestLoginIn(UniformInfoOperVo uniformInfoOperVo) throws Exception {
		UocMessage uocMessage = redisServiceServDu.queryDataFromRedis(uniformInfoOperVo.getJsessionid());
		if("0000".equals(uocMessage.getRespCode())){
			RedisDataVo redisData = (RedisDataVo) uocMessage.getArgs().get("RedisData");
			UniformInfoOperVo uniformInfoOperVoQuery = (UniformInfoOperVo) redisData.getObj();
			LogUniformInfoOperPo logUniformInfoOper = new LogUniformInfoOperPo();
			Long seq = System.currentTimeMillis();
			logUniformInfoOper.setLog_id(seq.toString());
			logUniformInfoOper.setLog_flag(LOGIN_FLAG_OUT);
			logUniformInfoOper.setUniform_info_oper(uniformInfoOperVoQuery.getUniform_info_oper());
			logUniformInfoOper.setOper_no(uniformInfoOperVoQuery.getOper_no());
			logUniformInfoOper.setJsessionid(uniformInfoOperVo.getJsessionid());
			logUniformInfoOperServ.addLogUniformInfoOper(logUniformInfoOper);
		}
		UocMessage uocMessageDelete = redisServiceServDu.deleteDataFromRedis(uniformInfoOperVo.getJsessionid());
		if("0000".equals(uocMessageDelete.getRespCode())){
			return true;
		}
		else{
			return false;
		}
	}

}
