package com.tydic.unicom.apc.business.common.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.tydic.unicom.apc.base.pub.interfaces.CodeListServ;
import com.tydic.unicom.apc.base.pub.po.CodeListPo;
import com.tydic.unicom.apc.business.common.interfaces.CodeListServDu;
import com.tydic.unicom.apc.business.common.vo.CodeListVo;
import com.tydic.unicom.apc.service.common.interfaces.ApcRedisServiceServDu;
import com.tydic.unicom.apc.service.common.interfaces.GetIsLoginServDu;
import com.tydic.unicom.apc.service.common.vo.ApcRedisData;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

public class CodeListServDuImpl implements CodeListServDu{

	Logger logger = LoggerFactory.getLogger(CodeListServDuImpl.class);
	@Autowired
	private CodeListServ codeListServ;
	@Autowired
	private GetIsLoginServDu getIsLoginServDu;
	@Autowired
	private ApcRedisServiceServDu apcRedisServiceServDu;
	
	@Override
	public UocMessage queryCodeListByTypeCode(String jsession_id,String typeCode) throws Exception {
		
		if(logger.isInfoEnabled()){
			logger.info("==========>请求参数:jsession_id:{},typeCode:{}",jsession_id,typeCode);
		}
		
		UocMessage resultUocMessage = new UocMessage();
		
		// 参数校验
		if(StringUtils.isEmpty(jsession_id)){
			resultUocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			resultUocMessage.setContent("请求参数中jsession_id不能为空");
			return resultUocMessage;
		}
		
		// 校验是否登录
		resultUocMessage = getIsLoginServDu.GetIsLoginByJsessionId(jsession_id);
		if( !(RespCodeContents.SUCCESS.equals(resultUocMessage.getRespCode())) ){
			return resultUocMessage;
		}
		
		String redisKey = "apc_code_list"+typeCode;
		String dataKey = "code_list";
		// 从Redis读取数据
		UocMessage redisMessage = apcRedisServiceServDu.query(redisKey);
		if(RespCodeContents.SUCCESS.equals(redisMessage.getRespCode())){
			return redisMessage;
		}
		
		// 获取数据
		List<CodeListVo> codeListVos = new ArrayList<CodeListVo>();
		List<CodeListPo> codeListPoOut = codeListServ.queryCodeListByTypeCode(typeCode);
		
		resultUocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
		resultUocMessage.setContent("数据为空，请确认TypeCode是否正确");
		
		// 校验获取数据结果
		if(codeListPoOut!=null && codeListPoOut.size()>0){
			for(CodeListPo codeListPo : codeListPoOut){
				CodeListVo codeListVo = new CodeListVo();
				BeanUtils.copyProperties(codeListPo, codeListVo);
				codeListVos.add(codeListVo);
			}
			resultUocMessage.setRespCode(RespCodeContents.SUCCESS);
			resultUocMessage.setContent("获取CodeList成功");
			resultUocMessage.addArg("code_list", codeListVos);
			
			// 将数据写入缓存
			ApcRedisData apcRedisData = new ApcRedisData();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put(dataKey, codeListVos);
			apcRedisData.setMap(map);
			apcRedisData.setId(redisKey);
			apcRedisServiceServDu.create(apcRedisData);
			
			// 从Redis读取数据
			redisMessage = apcRedisServiceServDu.query(redisKey);
			if(RespCodeContents.SUCCESS.equals(redisMessage.getRespCode())){
				return redisMessage;
			}
		}
		
		return resultUocMessage;
	}
}