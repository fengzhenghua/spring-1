package com.tydic.unicom.uoc.business.common.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.tydic.unicom.service.cache.service.redis.interfaces.RedisServiceServ;
import com.tydic.unicom.service.cache.service.redis.po.ProcModTacheLogin;
import com.tydic.unicom.service.cache.service.redis.po.RedisData;
import com.tydic.unicom.uoc.business.common.interfaces.GetOptionalTacheServDu;
import com.tydic.unicom.uoc.business.common.vo.ProcModTacheVo;
import com.tydic.unicom.uoc.service.common.impl.StrUtil;
import com.tydic.unicom.uoc.service.common.interfaces.OperServDu;
import com.tydic.unicom.uoc.service.mod.interfaces.ProcModTacheServDu;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

public class GetOptionalTacheServDuImpl implements GetOptionalTacheServDu{

	private static Logger logger = Logger.getLogger(GetOptionalTacheServDuImpl.class);

	@Autowired
	private ProcModTacheServDu procModTacheServDu;

	@Autowired
	private OperServDu operServDu;

	@Autowired
	private RedisServiceServ redisServiceServ;

	@SuppressWarnings("unchecked")
	@Override
	public UocMessage getOptionalTache(String jsession_id) throws Exception {
		logger.info("============获取可选环节============jsession_id:"+jsession_id);
		UocMessage uocMessage = new UocMessage();
		//获取登陆信息服务，校验是否登陆
		UocMessage uocMessageLogin = operServDu.isLogin(jsession_id);
		if(!"0000".equals(uocMessageLogin.getRespCode())){
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent(uocMessageLogin.getContent());
			return uocMessage;
		}

		Map<String,Object> operInfoMap =(Map<String, Object>) uocMessageLogin.getArgs().get("oper_info");
		String roleIdList = (String) operInfoMap.get("role_id");
		List<String> role_id_list = StrUtil.strToList(roleIdList);
		List<ProcModTacheVo> list = new ArrayList<ProcModTacheVo>();

		// 从缓存获取订单参数入库定义表配置
		UocMessage paraMessage = redisServiceServ.queryDataFromRedis("proc_tache_login");
		if (!"0000".equals(paraMessage.getRespCode().toString())) {
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("获取环节关联工号表缓存失败");
			return uocMessage;
		}
		RedisData redisDataPara = (RedisData) paraMessage.getArgs().get("RedisDataResult");
		Map<String, Object> map = redisDataPara.getMap();
		List<ProcModTacheLogin> procModTacheLoginPoList = (List<ProcModTacheLogin>) map.get("proc_tache_login");

		if (role_id_list != null && procModTacheLoginPoList != null) {
			for (ProcModTacheLogin po : procModTacheLoginPoList) {
				for (String roleId : role_id_list) {
					if (roleId.equals(po.getRole_id())) {
						ProcModTacheVo paramVo = new ProcModTacheVo();
						paramVo.setTache_code(po.getTache_code());
						ProcModTacheVo result = procModTacheServDu.queryProcModTacheVoByTacheCode(paramVo);
						list.add(result);
					}
				}
			}
		}

		if(list != null && list.size()>0){
			uocMessage.setRespCode(RespCodeContents.SUCCESS);
			uocMessage.addArg("TacheList", list);
		}
		else{
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("获取可选环节失败");
		}
		return uocMessage;
	}

}
