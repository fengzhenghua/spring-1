package com.tydic.unicom.ugc.business.common.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.tydic.unicom.service.cache.service.redis.po.UgcCodeList;
import com.tydic.unicom.ugc.business.common.interfaces.GetCodeListServDu;
import com.tydic.unicom.ugc.service.code.interfaces.CodeListServDu;
import com.tydic.unicom.ugc.service.common.impl.ToolSpring;
import com.tydic.unicom.ugc.service.common.interfaces.OperServDu;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

public class GetCodeListServDuImpl implements GetCodeListServDu{

	Logger logger = Logger.getLogger(GetCodeListServDuImpl.class);

	@Autowired
	private OperServDu operServDu;
	@Autowired
	private CodeListServDu codeListServDu;

	@Override
	public UocMessage getCodeListData(String jsession_id, String type_code)
			throws Exception {

		UocMessage message = new UocMessage();
		//能力平台获取登陆信息服务，校验是否登陆，以及取出相应的工号信息
		if (operServDu == null) {
			logger.info("====================operServDu is null============================" + operServDu);
			operServDu = (OperServDu) ToolSpring.getBean("OperServDu");
		}

		UocMessage loginMessage = operServDu.isLogin(jsession_id);
		if (!"0000".equals(loginMessage.getRespCode().toString())) {
			logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>需要登陆<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("需要登陆");
			return message;
		}
		if ("9999".equals(loginMessage.getArgs().get("code"))) {
			message.setRespCode(RespCodeContents.ABILITY_PLATFORM_FAIL);
			message.setContent("能力平台调用失败！");
			return message;
		}
		if("".equals(type_code) || type_code==null){
			message.setContent("type_code不能为空");
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			return message;
		}
		//查询code_list参数表，获取数据
		UgcCodeList codeList = new UgcCodeList();
		codeList.setType_code(type_code);
		List<UgcCodeList> codeLists = codeListServDu.queryCodeListByTypeCodeFromRedis(codeList);

		if(codeLists!=null&&codeLists.size()>0){
			message.setContent("查询成功");
			message.setRespCode(RespCodeContents.SUCCESS);
			message.addArg("code_list", codeLists);
		}else{
			message.setContent("未查询到数据");
			message.setRespCode(RespCodeContents.SUCCESS);
		}
		return message;
	}

}
