package com.tydic.unicom.apc.business.pub.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.tydic.unicom.apc.business.common.interfaces.GetOptionalAgentServDu;
import com.tydic.unicom.apc.business.common.interfaces.GetOptionalDeveServDu;
import com.tydic.unicom.apc.business.common.interfaces.GetOptionalRegionServDu;
import com.tydic.unicom.apc.business.common.vo.InfoAgentVo;
import com.tydic.unicom.apc.business.common.vo.InfoRegionVo;
import com.tydic.unicom.apc.business.constants.Constants;
import com.tydic.unicom.apc.business.pub.interfaces.ApServDu;
import com.tydic.unicom.apc.business.pub.vo.ApDefineVo;
import com.tydic.unicom.apc.business.pub.vo.ApDeveloperVo;
import com.tydic.unicom.apc.business.pub.vo.ApDisplayInfoVo;
import com.tydic.unicom.apc.business.pub.vo.ApParaVo;
import com.tydic.unicom.apc.common.utils.JsonToBean;
import com.tydic.unicom.apc.service.common.interfaces.ApcAbilityPlatformServDu;
import com.tydic.unicom.apc.service.pub.interfaces.ApDefineServDu;
import com.tydic.unicom.apc.service.pub.interfaces.ApDeveloperServDu;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocException;
import com.tydic.unicom.webUtil.UocMessage;

public class ApServDuImpl implements ApServDu {

	Logger logger = Logger.getLogger(ApServDuImpl.class);
	@Autowired
	private ApDefineServDu apDefineServDu;
	@Autowired
	private ApDeveloperServDu apDeveloperServDu;
	@Autowired
	private ApcAbilityPlatformServDu apcAbilityPlatformServDu;
	@Autowired
	private GetOptionalRegionServDu getOptionalRegionServDu;
	@Autowired
	private GetOptionalAgentServDu getOptionalAgentServDu;
	@Autowired
	private GetOptionalDeveServDu getOptionalDeveServDu;
	/**
	 * 6	触点维护APC0005
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public UocMessage createOperateAp(ApParaVo vo) throws Exception {
		UocMessage uocMessage = new UocMessage();

		String jsession_id =vo.getJsession_id();
		String json_info =vo.getJson_info();
		String oper_type =vo.getOper_type();
		// 1、通过APCBS0001能力平台服务调用认证中心服务UAC0005获取登陆信息，校验是否登陆
		String json = "{\"SERVICE_NAME\":\"getBaseOperInfo\",\"param\":{\"jsession_id\":\"" + jsession_id + "\"}}";
		uocMessage = apcAbilityPlatformServDu.CallApcAbilityPlatform(json, "", "UAC");
		if (!"0000".equals(uocMessage.getRespCode())) {
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("请先登录");
			return uocMessage;
		}
		String json_info_out = (String) uocMessage.getArgs().get("json_info");
		Map<String, Object> map = JsonToBean.jsonToMap(json_info_out);
		Map<String, Object> oper_info = (Map<String, Object>) map.get("oper_info");
		if (oper_info == null) {
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("获取用户信息失败");
			return uocMessage;
		}
		String province_code = (String) oper_info.get("province_code");
		String area_code = (String) oper_info.get("area_code");
		String oper_no = (String) oper_info.get("oper_no");

		Map<String, Object> mapObj = JsonToBean.jsonToMap(json_info);

		Set<Entry<String, Object>> entries = mapObj.entrySet();
		if (entries == null) {
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("条件不足");
			return uocMessage;
		}

		for (Entry<String, Object> entry : entries) {
			if (entry.getKey().equalsIgnoreCase(Constants.AP_DEFINE)) {
			// 触点基本信息
				Map<String, Object> mapVos = (Map<String, Object>) entry.getValue();
				String mapStr = JsonToBean.mapToJson(mapVos);
				ApDefineVo apDefineVo = (ApDefineVo) JsonToBean.jsonInfoToBean(mapStr, ApDefineVo.class);
				if (oper_type.equals(Constants.ADD)) {
				// 新增触点基本信息
					apDefineVo.setArea_code(area_code);
					apDefineVo.setProvince_code(province_code);
					apDefineVo.setCreate_staff(oper_no);
					apDefineVo.setState("0");
					boolean result = apDefineServDu.addApDefine(apDefineVo);
					if (!result) {
						UocMessage uocExceptionMsg = new UocMessage();
						uocExceptionMsg.setRespCode(RespCodeContents.SERVICE_FAIL);
						uocExceptionMsg.setContent("新增失败");
						throw new UocException(uocExceptionMsg);
					}
				} else if (oper_type.equals(Constants.DELETE)) {
				// 删除触点基本信息
					boolean result = apDefineServDu.deleteApDefine(apDefineVo);
					if (!result) {
						UocMessage uocExceptionMsg = new UocMessage();
						uocExceptionMsg.setRespCode(RespCodeContents.SERVICE_FAIL);
						uocExceptionMsg.setContent("删除失败");
						throw new UocException(uocExceptionMsg);
					}
				} else if (oper_type.equals(Constants.UPDATE)) {
				// 修改触点基本信息
					// 前台只修改state字段
					String state = apDefineVo.getState();
					ApDefineVo updateVo = apDefineServDu.getApDefine(apDefineVo);
					updateVo.setState(state);
					boolean result = apDefineServDu.updateApDefine(updateVo);
					if (!result) {
						UocMessage uocExceptionMsg = new UocMessage();
						uocExceptionMsg.setRespCode(RespCodeContents.SERVICE_FAIL);
						uocExceptionMsg.setContent("修改失败");
						throw new UocException(uocExceptionMsg);
					}
				}
			} else if (entry.getKey().equalsIgnoreCase(Constants.AP_DEVELOPER)) {
			// 触点发展人信息
				Map<String, Object> mapVos = (Map<String, Object>) entry.getValue();
				String mapStr = JsonToBean.mapToJson(mapVos);
				ApDeveloperVo apDeveloperVo = (ApDeveloperVo) JsonToBean.jsonInfoToBean(mapStr, ApDeveloperVo.class);
				if("".equals(apDeveloperVo.getDeveloper_name())){
					// 触点发展人为空
				}else{
					// 触点发展人不为空
					if (oper_type.equals(Constants.ADD)) {
					// 新增触点发展人
						if (apDeveloperVo.getDeveloper_no().contains(",")) {
						// 保存多个触点发展人
							String[] developerNos = apDeveloperVo.getDeveloper_no().split(",");
							String[] developerNms = apDeveloperVo.getDeveloper_name().split(",");
							for (int i = 0; i < developerNos.length; i++) {
								ApDeveloperVo addVo = new ApDeveloperVo();
								addVo.setId(System.currentTimeMillis() + "");
								addVo.setDeveloper_no(developerNos[i].trim());
								addVo.setDeveloper_name(developerNms[i].trim());
								addVo.setAp_id(apDeveloperVo.getAp_id());

								boolean result = apDeveloperServDu.addApDeveloper(addVo);
								if (!result) {
									UocMessage uocExceptionMsg = new UocMessage();
									uocExceptionMsg.setRespCode(RespCodeContents.SERVICE_FAIL);
									uocExceptionMsg.setContent("新增失败");
									throw new UocException(uocExceptionMsg);
								}
							}
						} else {
						// 保存一个触点发展人
							apDeveloperVo.setId(System.currentTimeMillis() + "");
							boolean result = apDeveloperServDu.addApDeveloper(apDeveloperVo);
							if (!result) {
								UocMessage uocExceptionMsg = new UocMessage();
								uocExceptionMsg.setRespCode(RespCodeContents.SERVICE_FAIL);
								uocExceptionMsg.setContent("新增失败");
								throw new UocException(uocExceptionMsg);
							}
						}
					} else if (oper_type.equals(Constants.DELETE)) {
					// 删除触点发展人
						boolean result = apDeveloperServDu.deleteApDeveloper(apDeveloperVo);
						if (!result) {
							UocMessage uocExceptionMsg = new UocMessage();
							uocExceptionMsg.setRespCode(RespCodeContents.SERVICE_FAIL);
							uocExceptionMsg.setContent("删除失败");
							throw new UocException(uocExceptionMsg);
						}
					} else if (oper_type.equals(Constants.UPDATE)) {
					// 修改触点发展人

						//TODO [前台不需要修改][2017-04-27]
					}
				}
			} else if (entry.getKey().equalsIgnoreCase(Constants.AP_GOODS)){
			// 触点商品信息
				if( oper_type.equals(Constants.ADD)) {
					// 新增触点商品
					Map<String, Object> mapVos = (Map<String, Object>) entry.getValue();
					String mapStr = JsonToBean.mapToJson(mapVos);
					ApParaVo goodsParaVo = new ApParaVo();
					goodsParaVo.setJsession_id(jsession_id);
					goodsParaVo.setOper_type(oper_type);
					goodsParaVo.setJson_info(mapStr);
					uocMessage = createOperateApGoods(goodsParaVo);
					if (!"0000".equals(uocMessage.getRespCode())) {
						return uocMessage;
					}
				}else if( oper_type.equals(Constants.DELETE )){
				// 修改触点商品

					//TODO [功能待完善][2017-04-27]
				}else if(oper_type.equals(Constants.UPDATE )) {
				// 删除触点商品

					//TODO [功能待完善][2017-04-27]
				}
			}
		}

		uocMessage.setRespCode(RespCodeContents.SUCCESS);
		uocMessage.setContent("操作成功");
		return uocMessage;
  }


	/**
	 * 7	触点商品维护 APC0006
	 * @return
	 * @throws Exception
	 */
	@Override
	public UocMessage createOperateApGoods(ApParaVo vo) throws Exception {
		UocMessage uocMessage = new UocMessage();

		String jsession_id =vo.getJsession_id();
		String json_info =vo.getJson_info();
		String oper_type =vo.getOper_type();
		// 1、通过APCBS0001能力平台服务直接调用商品中心服务UGC0003
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("SERVICE_NAME", "operateApGoods");
		Map<String,String> map = new HashMap<String,String>();
		map.put("jsession_id", jsession_id);
		map.put("json_info", json_info);
		map.put("oper_type", oper_type);
		jsonObj.put("params", map);
		uocMessage = apcAbilityPlatformServDu.CallApcAbilityPlatform(jsonObj.toString(), "", "UGC");

		return uocMessage;
	}

	/**
	 * 触点查询 APC0008
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public UocMessage queryApDefineInfo(ApParaVo vo) throws Exception {
		UocMessage uocMessage = new UocMessage();
		String jsession_id = vo.getJsession_id();
		String json_info = vo.getJson_info();

		if (StringUtils.isEmpty(jsession_id) || StringUtils.isEmpty(json_info)) {
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("缺少必传参数");
			return uocMessage;
		}

		// 1、通过APCBS0001能力平台服务调用认证中心服务UAC0005获取登陆信息，校验是否登陆
		String json = "{\"SERVICE_NAME\":\"getBaseOperInfo\",\"param\":{\"jsession_id\":\"" + jsession_id + "\"}}";
		uocMessage = apcAbilityPlatformServDu.CallApcAbilityPlatform(json, "", "UAC");
		if (!"0000".equals(uocMessage.getRespCode())) {
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("请先登录");
			return uocMessage;
		}
		String json_info_out = (String) uocMessage.getArgs().get("json_info");
		Map<String, Object> map = JsonToBean.jsonToMap(json_info_out);
		Map<String, Object> oper_info = (Map<String, Object>) map.get("oper_info");
		if (oper_info == null) {
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("获取用户信息失败");
			return uocMessage;
		}
		String province_code = (String) oper_info.get("province_code");
		String area_code = (String) oper_info.get("area_code");

		Map<String, Object> mapObj = JsonToBean.jsonToMap(json_info);

		Set<Entry<String, Object>> entries = mapObj.entrySet();
		if (entries == null) {
			uocMessage.setContent("条件不足");
			return uocMessage;
		}

		int pageNo = Integer.parseInt((String) mapObj.get("pageNo"));
		int pageSize = Integer.parseInt((String) mapObj.get("pageSize"));
		StringBuffer sb1 = new StringBuffer();
		List<ApDisplayInfoVo> displayList = new ArrayList<ApDisplayInfoVo>();
		int totalCount = 0;
		int totalPage = 0;
		boolean hasApId = true;
		List<ApDeveloperVo> developVoList = new ArrayList<ApDeveloperVo>();
		List<Map<String, String>> apGoodsList = new ArrayList<Map<String, String>>();

		// 2、关联触点发展人表
		Map<String, Object> mapDevelopVos = (Map<String, Object>) mapObj.get(Constants.AP_DEVELOPER);
		String mapDevelopStr = JsonToBean.mapToJson(mapDevelopVos);
		ApDeveloperVo apDeveloperVo = (ApDeveloperVo) JsonToBean.jsonInfoToBean(mapDevelopStr, ApDeveloperVo.class);
		if (!StringUtils.isEmpty(apDeveloperVo.getDeveloper_no())) {
			String developNoStr = "";
			if (apDeveloperVo.getDeveloper_no().contains(",")) {
				for (String developNo : apDeveloperVo.getDeveloper_no().split(",")) {
					developNoStr += "'" + developNo.trim() + "'" + ",";
				}
				developNoStr = developNoStr.substring(0, developNoStr.length() - 1);
			} else {
				developNoStr = "'" + apDeveloperVo.getDeveloper_no() + "'";
			}
			apDeveloperVo.setDeveloper_no(developNoStr);
			developVoList = apDeveloperServDu.queryApDeveloperByVo(apDeveloperVo);
		}

		// 2、关联触点商品信息(调用APC0009服务根据商品取出对应的触点)对触点定义表进行查询
		ApParaVo apGoodsPara=new ApParaVo();
		Map<String, Object> mapGoodsVos = (Map<String, Object>) mapObj.get(Constants.AP_GOODS);
		String mapGoodsStr = JsonToBean.mapToJson(mapGoodsVos);
		if (!mapGoodsStr.contains("\"\"")) {
			apGoodsPara.setJson_info(mapGoodsStr);
			apGoodsPara.setJsession_id(jsession_id);
			UocMessage apGoodsMsg = queryApGoodsInfo(apGoodsPara);
			if ("0000".equals(apGoodsMsg.getRespCode()) && "200".equals(apGoodsMsg.getArgs().get("code").toString())) {
				Map<String, Object> resultMap = JsonToBean.jsonToMap((String) apGoodsMsg.getArgs().get("json_info"));
				apGoodsList = (List<Map<String, String>>) resultMap.get("apGoodsList");
			}
		}

		if (developVoList == null || apGoodsList == null) {
			hasApId = false;
			logger.info("--------查询条件中无对应ap_id---------");
		} else if (developVoList.size() > 0 && apGoodsList.size() > 0) {
			boolean isExist = false;
			for (ApDeveloperVo developerVo : developVoList) {
				for (Map<String, String> apGoodsVo : apGoodsList) {
					if (developerVo.getAp_id().equals(apGoodsVo.get("ap_id"))) {
						isExist = true;
						hasApId = true;
						sb1.append("'");
						sb1.append(apGoodsVo.get("ap_id"));
						sb1.append("'");
						sb1.append(",");
					} else if (!isExist) {
						hasApId = false;
						logger.info("--------查询条件中无对应ap_id---------");
					}
				}
			}
		} else if (developVoList.size() > 0 && apGoodsList.size() == 0) {
			for (ApDeveloperVo developerVo : developVoList) {
				sb1.append("'");
				sb1.append(developerVo.getAp_id());
				sb1.append("'");
				sb1.append(",");
			}
		} else if (developVoList.size() == 0 && apGoodsList.size() > 0) {
			for (Map<String, String> apGoodsVo : apGoodsList) {
				sb1.append("'");
				sb1.append(apGoodsVo.get("ap_id"));
				sb1.append("'");
				sb1.append(",");
			}
		}

		String apIdsStr = sb1.toString();
		Map<String, Object> mapDefineVos = (Map<String, Object>) mapObj.get(Constants.AP_DEFINE);
		String mapDefineStr = JsonToBean.mapToJson(mapDefineVos);
		ApDefineVo apDefineVo = (ApDefineVo) JsonToBean.jsonInfoToBean(mapDefineStr, ApDefineVo.class);
		apDefineVo.setProvince_code(province_code);
		apDefineVo.setArea_code(area_code);
		if (StringUtils.isEmpty(apDefineVo.getAp_id()) && apIdsStr.length() > 0) {
			apDefineVo.setAp_id(apIdsStr.substring(0, apIdsStr.length() - 1));
		} else if (!StringUtils.isEmpty(apDefineVo.getAp_id())) {
			apDefineVo.setAp_id("'" + apDefineVo.getAp_id() + "'");
		}

		// 获取触点信息
		List<ApDefineVo> defineVoList = new ArrayList<ApDefineVo>();
		if (hasApId) {
			// 无效的触点
			if ("1".equals(apDefineVo.getState())) {
				defineVoList = apDefineServDu.queryInvalidApDefinePage(apDefineVo, pageNo, pageSize);
				totalCount = apDefineServDu.queryInvalidApDefineCount(apDefineVo);
			}
			// 有效的
			else {
				defineVoList = apDefineServDu.queryEffectiveApDefinePage(apDefineVo, pageNo, pageSize);
				totalCount = apDefineServDu.queryEffectiveApDefineCount(apDefineVo);
			}


			totalPage = (totalCount + pageSize - 1) / pageSize;
			if (totalCount > 0) {
				// 封装触点信息
				for (ApDefineVo resultDefine : defineVoList) {
					ApDisplayInfoVo resultVo = new ApDisplayInfoVo();
					BeanUtils.copyProperties(resultDefine, resultVo);

					String ap_id = resultDefine.getAp_id();
					ApDeveloperVo paraDevelopVo = new ApDeveloperVo();
					paraDevelopVo.setAp_id(ap_id);
					// 获取发展人信息
					List<ApDeveloperVo> resultList = apDeveloperServDu.queryApDeveloperByVo(paraDevelopVo);
					if (resultList != null && resultList.size() > 0) {
						String developNms = "";
						String developNos = "";
						for (ApDeveloperVo resultDevelop : resultList) {
							developNms += resultDevelop.getDeveloper_name() + ",";
							developNos += resultDevelop.getDeveloper_no() + ",";
						}
						resultVo.setDeveloper_name(developNms.substring(0, developNms.length() - 1));
						resultVo.setDeveloper_no(developNos.substring(0, developNos.length() - 1));
					} else {
						resultVo.setDeveloper_name("");
						resultVo.setDeveloper_no("");
					}
					// 获取商品信息
					ApParaVo paraGoodsPara = new ApParaVo();
					paraGoodsPara.setJson_info("{\"ap_id\": \"" + ap_id + "\"}");
					paraGoodsPara.setJsession_id(jsession_id);
					UocMessage apGoodsResultMsg = queryApGoodsInfo(paraGoodsPara);
					resultVo.setGoods_id("");
					if ("0000".equals(apGoodsResultMsg.getRespCode()) && "200".equals(apGoodsResultMsg.getArgs().get("code").toString())) {
						Map<String, Object> resultMap = JsonToBean.jsonToMap((String) apGoodsResultMsg.getArgs().get("json_info"));
						List<Map<String,String>> apGoodsStrList = (List<Map<String,String>>) resultMap.get("apGoodsList");
						if (apGoodsStrList != null && apGoodsStrList.size() > 0) {
							String goodsIds = "";
							String goodsNms = "";
							for (int i = 0; i < apGoodsStrList.size(); i++) {
								goodsIds += apGoodsStrList.get(i).get("goods_id") + ",";
								if (StringUtils.isEmpty(apGoodsStrList.get(i).get("goods_id"))) {
									goodsNms += "" + ",";
								} else {
									goodsNms += apGoodsStrList.get(i).get("goods_name") + ",";
								}
							}
							resultVo.setGoods_id(goodsIds.substring(0, goodsIds.length() - 1));
							resultVo.setGoods_name(goodsNms.substring(0, goodsNms.length() - 1));
						} else {
							resultVo.setGoods_id("");
							resultVo.setGoods_name("");
						}
					}

					// 获取地区信息
					InfoRegionVo infoRegionVo = new InfoRegionVo();
					infoRegionVo.setJsession_id(jsession_id);
					infoRegionVo.setRegion_id(resultDefine.getRegion());
					infoRegionVo.setParent_region_id("");
					infoRegionVo.setRegion_level("");
					infoRegionVo.setRegion_name("");
					UocMessage regionUocMessage = getOptionalRegionServDu.GetOptionalRegionInfo(infoRegionVo);
					if(RespCodeContents.SUCCESS.equals(regionUocMessage.getRespCode())){
						Map<String, Object> regionMap = JsonToBean.jsonToMap((String) regionUocMessage.getArgs().get("json_info"));
						if(regionMap!=null ){
							List<Map<String,String>> regionStrList = (List<Map<String,String>>) regionMap.get("region_list");
							if (regionStrList != null && regionStrList.size() > 0) {
								resultVo.setRegion_name(regionStrList.get(0).get("region_name").toString());
								resultVo.setRegion_id(regionStrList.get(0).get("region_id").toString());
							}
						}
					}

					// 获取渠道信息
					InfoAgentVo infoAgentVo = new InfoAgentVo();
					infoAgentVo.setJsession_id(jsession_id);
					infoAgentVo.setChnl_code(resultDefine.getChnl_code());
					infoAgentVo.setChnl_name("");
					infoAgentVo.setRegion_id("");
					UocMessage chnlUocMessage = getOptionalAgentServDu.GetOptionalAgentInfo(infoAgentVo);
					if(RespCodeContents.SUCCESS.equals(regionUocMessage.getRespCode())){
						Map<String, Object> chnlMap = JsonToBean.jsonToMap((String) chnlUocMessage.getArgs().get("json_info"));
						if(chnlMap!=null){
							List<Map<String,String>> chnlStrList = (List<Map<String,String>>) chnlMap.get("chnl_list");
							if (chnlStrList != null && chnlStrList.size() > 0) {
								resultVo.setChnl_name(chnlStrList.get(0).get("chnl_name").toString());
								resultVo.setChnl_code(chnlStrList.get(0).get("chnl_code").toString());
							}
						}
					}

					displayList.add(resultVo);
				}
			}
		}

		uocMessage.addArg("ap_list", displayList);
		uocMessage.addArg("page_no", pageNo);
		uocMessage.addArg("page_size", pageSize);
		uocMessage.addArg("total_num", totalCount);
		uocMessage.addArg("total_page", totalPage);
		uocMessage.setRespCode(RespCodeContents.SUCCESS);
		uocMessage.setContent("触点查询成功");
		return uocMessage;
	}

	/**
	 * 触点商品查询 APC0009
	 * @return
	 * @throws Exception
	 */
	@Override
	public UocMessage queryApGoodsInfo(ApParaVo vo) throws Exception {
		UocMessage uocMessage = new UocMessage();

		String jsession_id = vo.getJsession_id();
		String json_info = vo.getJson_info();

		logger.info("--------通过APCBS0001能力平台服务直接调用商品中心服务UGC0004----------");
		if (StringUtils.isEmpty(jsession_id) || StringUtils.isEmpty(json_info)) {
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("缺少必传参数");
			return uocMessage;
		}

		// 通过APCBS0001能力平台服务直接调用商品中心服务UGC0004
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("SERVICE_NAME", "queryApGoodsList");
		Map<String, String> map = new HashMap<String, String>();
		map.put("jsession_id", jsession_id);
		map.put("json_info", json_info);
		jsonObj.put("params", map);
		uocMessage = apcAbilityPlatformServDu.CallApcAbilityPlatform(jsonObj.toString(), "", "UGC");

		return uocMessage;
	}


	@SuppressWarnings("unchecked")
	@Override
	public UocMessage queryApBulidQrcodeDevelopers(ApParaVo vo) throws Exception {
		UocMessage uocMessage = new UocMessage();
		ApDisplayInfoVo resultVo = new ApDisplayInfoVo();
		String jsession_id = vo.getJsession_id();
		String json_info = vo.getJson_info();
		Map<String, Object> mapObj = JsonToBean.jsonToMap(json_info);

		if (StringUtils.isEmpty(jsession_id) || StringUtils.isEmpty(json_info) || mapObj.get("selected_ap_id") == null) {
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("缺少必传参数");
			return uocMessage;
		}

		String ap_id = (String) mapObj.get("selected_ap_id");
		ApDeveloperVo paraDevelopVo = new ApDeveloperVo();
		paraDevelopVo.setAp_id(ap_id);
		// 获取发展人信息
		List<ApDeveloperVo> resultList = apDeveloperServDu.queryApDeveloperByVo(paraDevelopVo);
		if (StringUtils.isEmpty(jsession_id) || resultList != null && resultList.size() > 0) {
			String developNms = "";
			String developNos = "";
			for (ApDeveloperVo resultDevelop : resultList) {
				developNms += resultDevelop.getDeveloper_name() + ",";
				developNos += resultDevelop.getDeveloper_no() + ",";
			}
			resultVo.setDeveloper_name(developNms.substring(0, developNms.length() - 1));
			resultVo.setDeveloper_no(developNos.substring(0, developNos.length() - 1));
		} else {
			String developerName = "";
			String region = "";
			String chnl_code = "";
			if (mapObj.get("developer_name") != null) {
				developerName = (String) mapObj.get("developer_name");
			}
			if (mapObj.get("selected_chnl_code") != null && !StringUtils.isEmpty(mapObj.get("selected_chnl_code"))) {
				chnl_code = (String) mapObj.get("selected_chnl_code");
			} else if (mapObj.get("selected_region_id") != null && !StringUtils.isEmpty(mapObj.get("selected_region_id"))) {
				region = (String) mapObj.get("selected_region_id");
			}
			List<Map<String, String>> developer_list = new ArrayList<Map<String, String>>();
			UocMessage resultUocMessage = getOptionalDeveServDu.getOptionalDeveInfo(jsession_id, "", developerName, region, chnl_code);
			if ("0000".equals(resultUocMessage.getRespCode())) {
				Map<String, Object> resultMap = JsonToBean.jsonToMap((String) resultUocMessage.getArgs().get("json_info"));
				if (resultMap.get("developer_list") != null) {
					developer_list = (List<Map<String, String>>) resultMap.get("developer_list");
					String developNms = "";
					String developNos = "";
					for (Map<String, String> develop : developer_list) {
						developNms += develop.get("dev_name") + ",";
						developNos += develop.get("dev_code") + ",";
					}
					resultVo.setDeveloper_name(developNms.substring(0, developNms.length() - 1));
					resultVo.setDeveloper_no(developNos.substring(0, developNos.length() - 1));
				}
			} else {
				resultVo.setDeveloper_name("");
				resultVo.setDeveloper_no("");
			}
		}

		uocMessage.addArg("developsInfo", resultVo);
		uocMessage.setRespCode(RespCodeContents.SUCCESS);
		uocMessage.setContent("选择发展人查询成功");
		return uocMessage;
	}

}
