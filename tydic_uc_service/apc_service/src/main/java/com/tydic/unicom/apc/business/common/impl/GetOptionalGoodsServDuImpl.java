package com.tydic.unicom.apc.business.common.impl;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.tydic.unicom.apc.business.common.interfaces.GetOptionalGoodsServDu;
import com.tydic.unicom.apc.service.common.interfaces.ApcAbilityPlatformServDu;
import com.tydic.unicom.apc.service.common.interfaces.GetIsLoginServDu;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

/**
 * <p>
 * 触点中心服务
 * </p>
 * APC0004-获取可选商品<br>
 *
 * @author ZhangCheng
 * @version V1.0
 * @date 2017-03-09
 */
public class GetOptionalGoodsServDuImpl implements GetOptionalGoodsServDu {

	private static Logger LOGGER = Logger.getLogger(GetOptionalOperServDuImpl.class);

	/**
	 * APCBS0001-能力平台服务
	 */
	@Autowired
	private ApcAbilityPlatformServDu apcAbilityPlatformServDu;

	@Autowired
	private GetIsLoginServDu getIsLoginServDu;

	@Override
	public UocMessage getOptionalGoodsInfo(String jsession_id) throws Exception{

		LOGGER.debug("DEBUG[APC0004]==========>获取可选商品开始");
		LOGGER.info("INFO [APC0004]==========>请求参数：jsession_id："+jsession_id);

		UocMessage respUocMessage = new UocMessage();

		// 参数校验
		if(StringUtils.isEmpty(jsession_id)){
			respUocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			respUocMessage.setContent("验证字符串不能为空！");
			return respUocMessage;
		}

		// 校验是否登录
		respUocMessage = getIsLoginServDu.GetIsLoginByJsessionId(jsession_id);
		if( !(RespCodeContents.SUCCESS.equals(respUocMessage.getRespCode())) ){
			return respUocMessage;
		}

		// 封装请求参数
		JSONObject requestJson = new JSONObject();

		Map<String,String> requestMap = new HashMap<String,String>();
		requestMap.put("jsession_id", jsession_id);

		requestJson.put("params", requestMap);
		requestJson.put("SERVICE_NAME", "getOptionalGoods");

		try{
			// 通过APCBS0001能力平台服务调用商品中心服务UGC0002
			respUocMessage = apcAbilityPlatformServDu.CallApcAbilityPlatform(requestJson.toString(), "", "UGC");
		}catch(Exception e){
			LOGGER.warn("WARN [APC0004]==========>通过能力平台调商品中心获取可选商品接口异常：");
			e.printStackTrace();

			respUocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			respUocMessage.setContent("通过能力平台调商品中心获取可选商品接口异常");
		}

		LOGGER.info( "INFO [APC0004]==========>响应参数："+respUocMessage.toString());
		LOGGER.debug("DEBUG[APC0004]==========>获取可选商品结束<==========");

		return respUocMessage;
	}

}
