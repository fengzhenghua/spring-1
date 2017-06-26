package com.tydic.unicom.ugc.business.common.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.tydic.unicom.ugc.base.dataBase.interfaces.ApGoodsServ;
import com.tydic.unicom.ugc.base.dataBase.interfaces.GoodsDefineServ;
import com.tydic.unicom.ugc.base.dataBase.po.ApGoodsPo;
import com.tydic.unicom.ugc.base.dataBase.po.GoodsDefinePo;
import com.tydic.unicom.ugc.business.common.constants.Constants;
import com.tydic.unicom.ugc.business.common.interfaces.ApGoodsServDu;
import com.tydic.unicom.ugc.business.common.vo.ApGoodsVo;
import com.tydic.unicom.ugc.service.common.impl.ToolSpring;
import com.tydic.unicom.ugc.service.common.interfaces.JsonToBeanServDu;
import com.tydic.unicom.ugc.service.common.interfaces.OperServDu;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

public class ApGoodsServDuImpl implements ApGoodsServDu {

	Logger logger = Logger.getLogger(ApGoodsServDuImpl.class);

	@Autowired
	private OperServDu operServDu;
	@Autowired
	private JsonToBeanServDu jsonToBeanServDu;
	@Autowired
	private ApGoodsServ apGoodsServ;
	@Autowired
	private GoodsDefineServ goodsDefineServ;

	private void getBeanDu() {
		if (jsonToBeanServDu == null) {
			logger.info("====================jsonToBeanServDu is null============================" + jsonToBeanServDu);
			jsonToBeanServDu = (JsonToBeanServDu) ToolSpring.getBean("JsonToBeanServDu");
		}
		if (apGoodsServ == null) {
			logger.info("====================apGoodsServ is null============================" + apGoodsServ);
			apGoodsServ = (ApGoodsServ) ToolSpring.getBean("ApGoodsServ");
		}
		if (operServDu == null) {
			logger.info("====================operServDu is null============================" + operServDu);
			operServDu = (OperServDu) ToolSpring.getBean("OperServDu");
		}
		if (goodsDefineServ == null) {
			logger.info("====================goodsDefineServ is null============================" + goodsDefineServ);
			goodsDefineServ = (GoodsDefineServ) ToolSpring.getBean("GoodsDefineServ");
		}
	}

	public UocMessage queryApGoodsListForAbilityPlatform(String json_in) throws Exception {
		getBeanDu();
		Map<String, Object> map = jsonToBeanServDu.jsonToMap(json_in);

		String jsession_id = (String) map.get("jsession_id");
		@SuppressWarnings("unchecked")
		String json_info = jsonToBeanServDu.mapToJson((Map<String, Object>) map.get("json_info"));
		UocMessage message = queryApGoodsList(jsession_id, json_info);
		return message;
	}

	public UocMessage commitOperateApGoodsForAbilityPlatform(String json_in) throws Exception {
		getBeanDu();
		Map<String, Object> map = jsonToBeanServDu.jsonToMap(json_in);

		String jsession_id = (String) map.get("jsession_id");
		String oper_type = (String) map.get("oper_type");
		@SuppressWarnings("unchecked")
		String json_info = jsonToBeanServDu.mapToJson((Map<String, Object>) map.get("json_info"));
		UocMessage message = commitOperateApGoods(jsession_id, json_info, oper_type);
		return message;
	}

	@SuppressWarnings("unchecked")
	@Override
	public UocMessage queryApGoodsList(String jsession_id, String json_info) throws Exception {
		UocMessage uocMessage = new UocMessage();
		if (StringUtils.isEmpty(jsession_id) || StringUtils.isEmpty(json_info)) {
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("必传参数为空");
			return uocMessage;
		}

		// 1、通过UGCBS0001能力平台服务调用认证中心服务UAC0005，校验是否登陆
		UocMessage loginMessage = operServDu.isLogin(jsession_id);
		if (!"0000".equals(loginMessage.getRespCode().toString())) {
			logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>需要登陆<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("需要登陆");
			return uocMessage;
		}

		Map<String, Object> oper_info = (Map<String, Object>) loginMessage.getArgs().get("oper_info");
		String province_code = (String) oper_info.get("province_code");
		String area_code = (String) oper_info.get("area_code");

		// 2、解析服务解析传入的JSON串，支持对触点商品表进行查询操作
		Map<String, Object> map = jsonToBeanServDu.jsonToMap(json_info);

		String goodsIds = map.containsKey("goods_id") ? map.get("goods_id").toString() : "";
		String apId = map.containsKey("ap_id") ? map.get("ap_id").toString() : "";
		String goodsIdStr = "";

		ApGoodsVo apGoodsVo = new ApGoodsVo();
		if (!StringUtils.isEmpty(goodsIds)) {
			for (String goodsId : goodsIds.split(",")) {
				goodsIdStr += "'" + goodsId.trim() + "'" + ",";
			}
			apGoodsVo.setGoods_id(goodsIdStr.substring(0, goodsIdStr.length() - 1));
		}
		apGoodsVo.setProvince_code(province_code);
		apGoodsVo.setArea_code(area_code);
		apGoodsVo.setAp_id(apId);
		ApGoodsPo apGoodsPo = new ApGoodsPo();
		BeanUtils.copyProperties(apGoodsVo, apGoodsPo);
		List<ApGoodsPo> poList = apGoodsServ.queryApGoodsByPo(apGoodsPo);
		List<ApGoodsVo> listOut = new ArrayList<ApGoodsVo>();
		if (poList != null && poList.size() > 0) {
			for (int i = 0; i < poList.size(); i++) {
				ApGoodsVo voTemp = new ApGoodsVo();
				GoodsDefinePo definePo = new GoodsDefinePo();
				definePo.setArea_code(area_code);
				definePo.setProvince_code(province_code);
				definePo.setGoods_id(poList.get(i).getGoods_id());
				List<GoodsDefinePo> goodsDefine = goodsDefineServ.queryGoodsDefinePoByPo(definePo);
				BeanUtils.copyProperties(poList.get(i), voTemp);
				if (goodsDefine != null && goodsDefine.size() > 0) {
					voTemp.setGoods_name(goodsDefine.get(0).getGoods_name());
				}
				listOut.add(voTemp);
			}
		}

		uocMessage.setRespCode(RespCodeContents.SUCCESS);
		uocMessage.setContent("触点商品查询成功");
		uocMessage.addArg("apGoodsList", listOut);
		return uocMessage;
	}
	/**
	 * 4	触点商品维护  UGC0003
	 * @param jsession_id
	 * @param json_info
	 * @param oper_type
	 * @return
	 */
	@Override
	public UocMessage commitOperateApGoods(String jsession_id, String json_info,
			String oper_type) throws Exception {
		UocMessage message = new UocMessage();
		// 1、通过UGCBS0001能力平台服务调用认证中心服务UAC0005，校验是否登陆 TODO
		UocMessage loginMessage = operServDu.isLogin(jsession_id);
		if (!"0000".equals(loginMessage.getRespCode().toString())) {
			logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>需要登陆<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("需要登陆");
			return message;
		}
		@SuppressWarnings("unchecked")
		Map<String, Object> oper_info = (Map<String, Object>) loginMessage.getArgs().get("oper_info");
		String province_code = (String) oper_info.get("province_code");
		String area_code = (String) oper_info.get("area_code");
		// 2、解析服务解析传入的JSON串，支持对触点商品表进行查询操作
		Map<String, Object> map = jsonToBeanServDu.jsonToMap(json_info);

		String goodsIds = map.containsKey("goods_id") ? map.get("goods_id").toString() : "";
		String ap_id = map.containsKey("ap_id") ? map.get("ap_id").toString() : "";

		boolean operateResult =false;
		try {
			for (String goodsId : goodsIds.split(",")) {
				ApGoodsVo apGoodsVo = new ApGoodsVo();
				apGoodsVo.setProvince_code(province_code);
				apGoodsVo.setArea_code(area_code);
				apGoodsVo.setAp_id(ap_id);
				if (Constants.ADD.equals(oper_type)) {
					apGoodsVo.setGoods_id(goodsId.trim());
					apGoodsVo.setId(System.currentTimeMillis() + "");
					operateResult = addApGoods(apGoodsVo);
				} else if (Constants.DELETE.equals(oper_type)) {
					operateResult = deleteApGoods(apGoodsVo);
				} else if (Constants.UPDATE.equals(oper_type)) {
					operateResult = updateApGoods(apGoodsVo);
				}

				if (!operateResult) {
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("操作失败");
					return message;
				}
			}

			message.setRespCode(RespCodeContents.SUCCESS);
			message.setContent("操作成功");
			return message;
		} catch (Exception e) {
			e.printStackTrace();
			message.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			message.setContent("工号角色表操作异常");
			return message;
		}

	}

	@Override
	public boolean addApGoods(ApGoodsVo vo) throws Exception {
		ApGoodsPo apGoodsPo =new ApGoodsPo ();
		BeanUtils.copyProperties(vo,apGoodsPo);

		return apGoodsServ.addApGoods(apGoodsPo);
	}

	@Override
	public boolean deleteApGoods(ApGoodsVo vo) throws Exception {
		ApGoodsPo apGoodsPo =new ApGoodsPo ();
		BeanUtils.copyProperties(vo,apGoodsPo);

		return apGoodsServ.deleteApGoods(apGoodsPo);
	}

	@Override
	public boolean updateApGoods(ApGoodsVo vo) throws Exception {
		ApGoodsPo apGoodsPo =new ApGoodsPo ();
		BeanUtils.copyProperties(vo,apGoodsPo);

		return apGoodsServ.updateApGoods(apGoodsPo);
	}

}
