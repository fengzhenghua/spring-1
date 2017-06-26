package com.tydic.unicom.ugc.service.code.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.tydic.unicom.service.cache.service.redis.interfaces.RedisServiceServ;
import com.tydic.unicom.service.cache.service.redis.po.RedisData;
import com.tydic.unicom.service.cache.service.redis.po.UgcCodeList;
import com.tydic.unicom.ugc.code.po.CodeListPo;
import com.tydic.unicom.ugc.code.service.CodeListServ;
import com.tydic.unicom.ugc.service.code.interfaces.CodeListServDu;
import com.tydic.unicom.ugc.service.code.vo.CodeListVo;
import com.tydic.unicom.webUtil.UocMessage;

@Service("CodeListServDu")
public class CodeListServDuImpl implements CodeListServDu{

	Logger logger = Logger.getLogger(CodeListServDuImpl.class);
	@Autowired
	private CodeListServ codeListServ;
	@Autowired
	private RedisServiceServ redisServiceServ;

	@Override
	public CodeListVo queryCodeListByTypeCode(CodeListVo codeListVo)
			throws Exception {
		CodeListPo codeListPo = new CodeListPo();
		BeanUtils.copyProperties(codeListVo, codeListPo);
		codeListPo = codeListServ.queryCodeListByTypeCode(codeListPo);
		CodeListVo codeListVoOut = new CodeListVo();
		BeanUtils.copyProperties(codeListPo, codeListVoOut);
		return codeListVoOut;
	}

	@Override
	public List<UgcCodeList> queryCodeListByTypeCodeFromRedis(UgcCodeList codeList)
			throws Exception {
		UocMessage message = redisServiceServ.queryDataFromRedis("ugc_code_list");
		RedisData redisData = (RedisData) message.getArgs().get("RedisDataResult");
		Map<String,Object> dataMap = redisData.getMap();
		String key = codeList.getType_code();
		if (StringUtils.isNotEmpty(codeList.getParent_code_id())) {
			key = key + "_" + codeList.getParent_code_id();
		}
		@SuppressWarnings("unchecked")
		List<UgcCodeList> list = (List<UgcCodeList>) dataMap.get(key);

		return list;
	}

	@Override
	public UgcCodeList queryCodeListByCodeIdFromRedis(UgcCodeList codeList)
			throws Exception {
		UocMessage message = redisServiceServ.queryDataFromRedis("ugc_code_list");
		RedisData redisData = (RedisData) message.getArgs().get("RedisDataResult");
		Map<String,Object> dataMap = redisData.getMap();
		String code_id =codeList.getCode_id();
		codeList = (UgcCodeList) dataMap.get(code_id);

		return codeList;

	}
}