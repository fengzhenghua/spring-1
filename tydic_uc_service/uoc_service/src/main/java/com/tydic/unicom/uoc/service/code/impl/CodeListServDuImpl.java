package com.tydic.unicom.uoc.service.code.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.tydic.unicom.service.cache.service.redis.interfaces.RedisServiceServ;
import com.tydic.unicom.service.cache.service.redis.po.CodeList;
import com.tydic.unicom.service.cache.service.redis.po.RedisData;
import com.tydic.unicom.uoc.base.uoccode.po.CodeListPo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.CodeListServ;
import com.tydic.unicom.uoc.service.code.interfaces.CodeListServDu;
import com.tydic.unicom.uoc.service.code.vo.CodeListVo;
import com.tydic.unicom.webUtil.UocMessage;

@Service("CodeListServDu")
public class CodeListServDuImpl implements CodeListServDu{

	Logger logger = Logger.getLogger(CodeListServDuImpl.class);
	@Autowired
	private CodeListServ codeListServ;
	@Autowired
	private RedisServiceServ redisServiceServ;

	@Override
	public CodeListVo queryCodeListByTypeCode(CodeListVo codeListVo) throws Exception {
		CodeListPo codeListPo = new CodeListPo();
		BeanUtils.copyProperties(codeListVo, codeListPo);
		codeListPo = codeListServ.queryCodeListByTypeCode(codeListPo);
		CodeListVo codeListVoOut = new CodeListVo();
		BeanUtils.copyProperties(codeListPo, codeListVoOut);
		return codeListVoOut;
	}

	@Override
	public List<CodeListVo> queryCodeListAll() throws Exception {
		List<CodeListPo> list = codeListServ.queryCodeListAll();
		if(list != null && list.size()>0){
			List<CodeListVo> listOut = new ArrayList<CodeListVo>();
			for(int i=0;i<list.size();i++){
				CodeListVo codeListVo = new CodeListVo();
				BeanUtils.copyProperties(list.get(i), codeListVo);
				listOut.add(codeListVo);
			}
			return listOut;
		}
		else{
			return null;
		}
	}

	@Override
	public List<CodeList> queryCodeListByTypeCodeFromRedis(CodeList codeList) throws Exception {
		UocMessage message = redisServiceServ.queryDataFromRedis("code_list");
		RedisData redisData = (RedisData) message.getArgs().get("RedisDataResult");
		Map<String,Object> dataMap = redisData.getMap();
		String key = codeList.getType_code();
		if (StringUtils.isNotEmpty(codeList.getParent_code_id())) {
			key = key + "_" + codeList.getParent_code_id();
		}
		@SuppressWarnings("unchecked")
		List<CodeList> list = (List<CodeList>) dataMap.get(key);

		return list;
	}


@Override
public CodeList queryCodeListByCodeIdFromRedis(CodeList codeList) throws Exception {
	UocMessage message = redisServiceServ.queryDataFromRedis("code_list");
	RedisData redisData = (RedisData) message.getArgs().get("RedisDataResult");
	Map<String,Object> dataMap = redisData.getMap();
	String code_id =codeList.getCode_id();
	codeList =(CodeList)dataMap.get(code_id);

	return codeList;
 }

}