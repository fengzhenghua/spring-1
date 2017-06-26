package com.tydic.unicom.ugc.business.common.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.tydic.unicom.ugc.base.dataBase.interfaces.SkuDefineServ;
import com.tydic.unicom.ugc.base.dataBase.po.SkuDefinePo;
import com.tydic.unicom.ugc.business.common.interfaces.GetOptionalSkuDefineServDu;
import com.tydic.unicom.ugc.service.common.interfaces.OperServDu;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

/**
 * <p>UGC商品中心业务服务默认实现类</p>
 * UGC0010-获取可选sku服务
 * @author ZhangCheng
 * @date 2017-03-31
 * @version V1.0
 */
public class GetOptionalSkuDefineServDuImpl implements GetOptionalSkuDefineServDu {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GetOptionalSkuDefineServDuImpl.class);
	
	@Autowired
	private SkuDefineServ skuDefineServ;
	@Autowired
	private OperServDu operServDu;
	
	@Override
	public UocMessage getSkuDefineBySkuIdOrSkuName(String jsession_id, String sku_id, String sku_name)
			throws Exception {
		
		LOGGER.debug("[UGC0010]==========>获取可选sku服务 begin<==========");
		LOGGER.info("[UGC0010]==========>请求参数：jsession_id:{},sku_id:{},sku_name:{}",jsession_id,sku_id,sku_name);
		
		UocMessage uocMessage = new UocMessage();
		
		//参数校验
		if(StringUtils.isEmpty(jsession_id)){
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("请求参数jsession_id不能为空!");
			return uocMessage;
		}
		
		//登录校验
		UocMessage loginMessage = operServDu.isLogin(jsession_id);
		if (!"0000".equals(loginMessage.getRespCode().toString())) {
			LOGGER.info("[UGC0010]==========>获取可选sku服务 需要登录<==========");
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("未登录或验证字符串无效！");
			return uocMessage;
		}
		
		//查询数据
		SkuDefinePo requestPo = new SkuDefinePo();
		requestPo.setSku_id(sku_id);
		requestPo.setSku_name(sku_name);
		List<SkuDefinePo> listPo= skuDefineServ.querySkuDefineBySkuIdOrSkuName(requestPo);
		
		//封装数据
		if( listPo == null ){
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("查询数据为空!");
			return uocMessage;
		}
		List<Map<String,String>> listVo = new ArrayList<Map<String,String>>();//可选sku集
		for(int i=0;i<listPo.size();i++){
			Map<String,String> mapVo = new HashMap<String,String>();
			mapVo.put("sku_id", listPo.get(i).getSku_id());
			mapVo.put("sku_name", listPo.get(i).getSku_name());
			mapVo.put("sku_type", listPo.get(i).getSku_type());
			mapVo.put("sku_desc", listPo.get(i).getSku_desc());
			listVo.add(mapVo);
		}
		
		//返回数据
		uocMessage.setRespCode(RespCodeContents.SUCCESS);
		uocMessage.setContent("获取可选sku服务成功!");
		uocMessage.addArg("sku_list", listVo);
		
		LOGGER.info("[UGC0010]==========>响应参数：respcode:{},content:{},arg:{}",
			uocMessage.getRespCode(),uocMessage.getContent(),uocMessage.getArgs());
		LOGGER.debug("[UGC0010]==========>获取可选sku服务  end <==========");
		
		return uocMessage;
	}
}
