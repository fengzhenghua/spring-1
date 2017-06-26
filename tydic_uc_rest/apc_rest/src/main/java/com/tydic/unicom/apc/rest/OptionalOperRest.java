package com.tydic.unicom.apc.rest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.tydic.unicom.apc.business.common.interfaces.GetOptionalAgentServDu;
import com.tydic.unicom.apc.business.common.interfaces.GetOptionalDeveServDu;
import com.tydic.unicom.apc.business.common.interfaces.GetOptionalGoodsServDu;
import com.tydic.unicom.apc.business.common.interfaces.GetOptionalOperServDu;
import com.tydic.unicom.apc.business.common.interfaces.GetOptionalRegionServDu;
import com.tydic.unicom.apc.business.common.vo.InfoAgentVo;
import com.tydic.unicom.apc.business.common.vo.InfoRegionVo;
import com.tydic.unicom.crm.web.uss.constants.ControllerMappings;
import com.tydic.unicom.crm.web.uss.constants.UrlsMappings;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

/**
 * <p>
 * 获取可选信息REST
 * </p>
 * APC0002-获取可选工号-REST<br>
 * APC0003-获取可选发展人-REST<br>
 * APC0004-获取可选商品-REST<br>
 * APC0012-获取可选渠道-REST<br>
 * APC0013-获取可选地区-REST<br>
 * @author ZhangCheng
 * @date 2017-03-07
 * @version V1.0
 */
@RestController
@RequestMapping(value = ControllerMappings.OPTIONAL_OPER_REST)
public class OptionalOperRest {

	private static Logger LOGGER = Logger.getLogger(OptionalOperRest.class);

	@Autowired
	private GetOptionalOperServDu getOptionalOperServDu;

	@Autowired
	private GetOptionalDeveServDu getOptionalDeveServDu;

	@Autowired
	private GetOptionalGoodsServDu getOptionalGoodsServDu;

	@Autowired
	private GetOptionalAgentServDu getOptionalAgentServDu;

	@Autowired
	private GetOptionalRegionServDu getOptionalRegionServDu;

	/**
	 * APC0002-获取可选工号-REST
	 *
	 * @param jsessionId
	 *            验证字符串
	 * @param operNo
	 *            工号
	 * @param operName
	 *            名称
	 * @return 可选工号
	 */
	@RequestMapping(value = UrlsMappings.GET_OPTIONAL_OPER_INFO, method = RequestMethod.POST)
	public UocMessage getOptionalOperInfo(String jsessionId, String operNo, String operName) {
		UocMessage resultUocMessage = new UocMessage();

		LOGGER.info("APC0002-获取可选工号-REST-jsessionId:" + jsessionId + ",operNo:" + operNo + ",operName:" + operName);

		// 参数校验
		if (StringUtils.isEmpty(jsessionId)) {
			resultUocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			resultUocMessage.setContent("工号或验证字符串不能为空");
			return resultUocMessage;
		}
		// 调接口
		try {
			resultUocMessage = getOptionalOperServDu.getOptionalOperInfoString(jsessionId, operNo, operName);
			return resultUocMessage;
		} catch (Exception e) {
			LOGGER.info("APC0002-获取可选工号-REST-异常");
			e.printStackTrace();
			resultUocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			resultUocMessage.setContent("REST->业务接口-获取可选工号异常");
			return resultUocMessage;
		}
	}

	/**
	 * APC0003-获取可选发展人-REST
	 *
	 * @param jsessionId
	 *            验证字符串
	 * @param developerNo
	 *            发展人编号
	 * @param developerName
	 *            发展人名称
	 * @return 可选发展人
	 */
	@RequestMapping(value=UrlsMappings.GET_OPTIONAL_DEVE_INFO,method=RequestMethod.POST)
	public UocMessage getOptionalDeveInfo(String jsessionId, String developerNo, String developerName,String region,String chnl_code) {

		UocMessage resultUocMessage = new UocMessage();

		LOGGER.debug("[APC0003REST]==========>获取可选发展人开始<==========");
		LOGGER.info("[APC0003REST]==========>请求参数：jsessionId：" + jsessionId
			+ ",developerNo：" + developerNo + ",developerName：" + developerName);

		// 参数校验
		if (StringUtils.isEmpty(jsessionId)) {
			resultUocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			resultUocMessage.setContent("工号或验证字符串不能为空");
			return resultUocMessage;
		}

		// 调接口
		try {
			resultUocMessage = getOptionalDeveServDu.getOptionalDeveInfo(jsessionId,
				developerNo, developerName,region,chnl_code);
		} catch (Exception e) {
			LOGGER.warn("[APC0003REST]==========>调获取可选发展人服务异常：");
			e.printStackTrace();

			resultUocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			resultUocMessage.setContent("调获取可选发展人服务异常");
		}

		LOGGER.info("[APC0003REST]==========>响应参数：" + resultUocMessage.toString());
		LOGGER.debug("[APC0003REST]==========>获取可选工号结束<==========");

		// 返回结果
		return resultUocMessage;

	}

	/**
	 * APC0004-获取可选商品-REST
	 *
	 * @param jsession_id
	 *            验证字符串
	 * @return 可选商品
	 */
	@RequestMapping(value=UrlsMappings.GET_OPTIONAL_GOODS_INFO,method=RequestMethod.POST)
	public UocMessage getOptionalGoodsServDu(String jsession_id){

		LOGGER.debug("DEBUG[APC0004REST]==========>获取可选商品服务开始");
		LOGGER.info("INFO [APC0004REST]==========>请求参数：[jsession_id："+jsession_id+"]");

		UocMessage respUocMessage = new UocMessage();

		// 参数校验
		if (StringUtils.isEmpty(jsession_id)) {
			respUocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			respUocMessage.setContent("验证字符串不能为空");
			return respUocMessage;
		}

		// 调接口
		try {
			respUocMessage = getOptionalGoodsServDu.getOptionalGoodsInfo(jsession_id);
		} catch (Exception e) {
			LOGGER.warn("WARN [APC0004REST]==========>调获取可选商品服务异常：");
			e.printStackTrace();

			respUocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			respUocMessage.setContent("调获取可选商品服务异常");
		}

		LOGGER.info("INFO [APC0004REST]==========>响应参数：["+respUocMessage.toString()+"]");
		LOGGER.debug("DEBUG[APC0004REST]==========>获取可选商品服务结束");

		return respUocMessage;

	}

	/**
	 * APC0012-获取可选渠道-REST
	 * */
	@RequestMapping(value=UrlsMappings.GET_OPTIONAL_AGENT_INFO,method=RequestMethod.POST)
	public UocMessage getOptionalAgentInfo(String jsession_id,String chnl_code,String chnl_name,String region_id){

		UocMessage respUocMessage = new UocMessage();

		InfoAgentVo infoAgentVo = new InfoAgentVo();
		infoAgentVo.setJsession_id(jsession_id);
		infoAgentVo.setChnl_code(chnl_code);
		infoAgentVo.setChnl_name(chnl_name);
		infoAgentVo.setRegion_id(region_id);

		// 调接口
		try {
			respUocMessage = getOptionalAgentServDu.GetOptionalAgentInfo(infoAgentVo);
		} catch (Exception e) {
			LOGGER.warn("WARN [APC0012REST]==========>调获取可选渠道服务异常：");
			e.printStackTrace();
			respUocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			respUocMessage.setContent("调获取可选渠道服务异常");
		}

		return respUocMessage;
	}

	/**
	 * APC0013-获取可选地区-REST
	 * */
	@RequestMapping(value=UrlsMappings.GET_OPTIONAL_REGION_INFO,method=RequestMethod.POST)
	public UocMessage getOptionalRegionInfo(String jsession_id,String region_id,String region_name,
		String parent_region_id,String region_level){

		UocMessage respUocMessage = new UocMessage();

		InfoRegionVo infoRegionVo = new InfoRegionVo();
		infoRegionVo.setJsession_id(jsession_id);
		infoRegionVo.setParent_region_id(parent_region_id);
		infoRegionVo.setRegion_id(region_id);
		infoRegionVo.setRegion_name(region_name);
		infoRegionVo.setRegion_level(region_level);

		// 调接口
		try {
			respUocMessage = getOptionalRegionServDu.GetOptionalRegionInfo(infoRegionVo);
		} catch (Exception e) {
			LOGGER.warn("WARN [APC0013REST]==========>调获取可选渠道地区异常：");
			e.printStackTrace();
			respUocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			respUocMessage.setContent("调获取可选地区服务异常");
		}

		return respUocMessage;
	}

	/**
	 * APC0017-获取可选激励发展人
	 * */
	@RequestMapping(value = UrlsMappings.GET_REWARD_DEVELOP_INFO, method = RequestMethod.POST)
	public UocMessage getOptionalRegionInfo(String jsession_id, String developer_no) {
		UocMessage respUocMessage = new UocMessage();

		try {
			respUocMessage = getOptionalDeveServDu.getRewardDevelopInfo(jsession_id, developer_no);
		} catch (Exception e) {
			LOGGER.warn("WARN [APC0017REST]==========>获取可选激励发展人异常：");
			e.printStackTrace();
			respUocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			respUocMessage.setContent("获取可选激励发展人异常");
		}

		return respUocMessage;
	}
}
