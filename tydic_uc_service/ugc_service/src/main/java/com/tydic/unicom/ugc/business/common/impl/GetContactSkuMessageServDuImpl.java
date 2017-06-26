package com.tydic.unicom.ugc.business.common.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.tydic.unicom.ugc.base.dataBase.interfaces.ApGoodsServ;
import com.tydic.unicom.ugc.base.dataBase.interfaces.GoodsDefineServ;
import com.tydic.unicom.ugc.base.dataBase.interfaces.GoodsFeeServ;
import com.tydic.unicom.ugc.base.dataBase.interfaces.GoodsSkuServ;
import com.tydic.unicom.ugc.base.dataBase.interfaces.SkuAttrServ;
import com.tydic.unicom.ugc.base.dataBase.interfaces.SkuDefineServ;
import com.tydic.unicom.ugc.base.dataBase.po.ApGoodsPo;
import com.tydic.unicom.ugc.base.dataBase.po.GoodsDefinePo;
import com.tydic.unicom.ugc.base.dataBase.po.GoodsFeePo;
import com.tydic.unicom.ugc.base.dataBase.po.GoodsInfo;
import com.tydic.unicom.ugc.base.dataBase.po.GoodsSkuPo;
import com.tydic.unicom.ugc.base.dataBase.po.SkuAttrPo;
import com.tydic.unicom.ugc.base.dataBase.po.SkuDefinePo;
import com.tydic.unicom.ugc.business.common.interfaces.GetContactSkuMessageServDu;
import com.tydic.unicom.ugc.business.common.vo.GoodsFeeVo;
import com.tydic.unicom.ugc.business.common.vo.GoodsInfoVo;
import com.tydic.unicom.ugc.business.common.vo.InfoSkuDefineVo;
import com.tydic.unicom.ugc.business.common.vo.SkuAttrVo;
import com.tydic.unicom.ugc.service.common.impl.ToolSpring;
import com.tydic.unicom.ugc.service.common.interfaces.JsonToBeanServDu;
import com.tydic.unicom.ugc.service.common.interfaces.UgcAbilityPlatformServDu;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

public class GetContactSkuMessageServDuImpl implements GetContactSkuMessageServDu{

	Logger logger = Logger.getLogger(GetContactSkuMessageServDuImpl.class);

	@Autowired
	private ApGoodsServ apGoodsServ;

	@Autowired
	private GoodsFeeServ goodsFeeServ;

	@Autowired
	private GoodsSkuServ goodsSkuServ;

	@Autowired
	private SkuDefineServ skuDefineServ;

	@Autowired
	private UgcAbilityPlatformServDu ugcAbilityPlatformServDu;

	@Autowired
	private GoodsDefineServ goodsDefineServ;

	@Autowired
	private JsonToBeanServDu jsonToBeanServDu;

	@Autowired
	private SkuAttrServ skuAttrServ;

	private void getBeanDu(){
		if(jsonToBeanServDu == null){
			logger.info("====================jsonToBeanServDu is null============================"+jsonToBeanServDu);
			jsonToBeanServDu = (JsonToBeanServDu) ToolSpring.getBean("JsonToBeanServDu");
		}
		if(apGoodsServ == null){
			logger.info("====================apGoodsServ is null============================"+apGoodsServ);
			apGoodsServ = (ApGoodsServ) ToolSpring.getBean("ApGoodsServ");
		}
		if(goodsFeeServ == null){
			logger.info("====================goodsFeeServ is null============================"+goodsFeeServ);
			goodsFeeServ = (GoodsFeeServ) ToolSpring.getBean("GoodsFeeServ");
		}
		if(goodsSkuServ == null){
			logger.info("====================goodsSkuServ is null============================"+goodsSkuServ);
			goodsSkuServ = (GoodsSkuServ) ToolSpring.getBean("GoodsSkuServ");
		}
		if(skuDefineServ == null){
			logger.info("====================skuDefineServ is null============================"+skuDefineServ);
			skuDefineServ = (SkuDefineServ) ToolSpring.getBean("SkuDefineServ");
		}
		if(goodsDefineServ ==null){
			logger.info("====================goodsDefineServ is null============================"+goodsDefineServ);
			goodsDefineServ = (GoodsDefineServ) ToolSpring.getBean("GoodsDefineServ");
		}
		if(ugcAbilityPlatformServDu == null){
			logger.info("====================ugcAbilityPlatformServDu is null============================"+ugcAbilityPlatformServDu);
			ugcAbilityPlatformServDu = (UgcAbilityPlatformServDu) ToolSpring.getBean("UgcAbilityPlatformServDu");
		}
		if(skuAttrServ == null){
			logger.info("====================skuAttrServ is null============================"+skuAttrServ);
			skuAttrServ = (SkuAttrServ) ToolSpring.getBean("SkuAttrServ");
		}
	}
	/**
	 * 获取触点sku信息异常
	 * @param json_in
	 * @return
	 * @throws Exception
	 */
	public UocMessage queryContactSkuForAbilityPlatform(String  json_in) throws Exception{

		if(jsonToBeanServDu == null){
			logger.info("====================jsonToBeanServDu is null============================"+jsonToBeanServDu);
			jsonToBeanServDu = (JsonToBeanServDu) ToolSpring.getBean("JsonToBeanServDu");
		}
		Map<String, Object> map =jsonToBeanServDu.jsonToMap(json_in);

		String ap_id =(String) map.get("ap_id");
		//String query_type =(String) map.get("query_type");
		UocMessage message =queryContactSku(ap_id);
		return message;
	}

	@Override
	public UocMessage queryContactSku(String ap_id) throws Exception{

		UocMessage uocMessage = new UocMessage();
		getBeanDu();
		if (StringUtils.isEmpty(ap_id) || StringUtils.isEmpty(ap_id)) {
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("ap_id不能为空");
			return uocMessage;
		}

		List<Map<String, Object>> goodList = new ArrayList<Map<String, Object>>();
		//根据触点ID查询触点商品表，得到对应的商品ID
		ApGoodsPo apGoodsPo = new ApGoodsPo();
		apGoodsPo.setAp_id(ap_id);
		List<ApGoodsPo> poList = apGoodsServ.queryApGoodsByPo(apGoodsPo);
		if(poList==null || poList.size()==0){
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("无触点商品数据");
			return uocMessage;
		}else{
			for(ApGoodsPo  po :poList){
				Map<String, Object> goodMap = new HashMap<String, Object>();
				String goodsId=po.getGoods_id();
				logger.info("------goodsId------"+goodsId);
				if(goodsId!=null && !"".equals(goodsId)){
					goodMap.put("goods_id", goodsId);
					//根据商品ID 查询商品费用表
					GoodsFeePo goodsFeePo = new GoodsFeePo();
					goodsFeePo.setGoods_id(goodsId);
					List<GoodsFeePo> goodsFeeList=goodsFeeServ.queryGoodsFeeByGoodsId(goodsFeePo);
					List<GoodsFeeVo> goodsFeeVoList= new ArrayList<GoodsFeeVo>();
					if(goodsFeeList!=null && goodsFeeList.size()>0){
						for(GoodsFeePo goodsFeePoRes:goodsFeeList){
							GoodsFeeVo goodsFeeVo = new GoodsFeeVo();
							goodsFeeVo.setFee_item_code(goodsFeePoRes.getFee_item_code());
							goodsFeeVo.setFee_item_name(goodsFeePoRes.getFee_item_name());
							goodsFeeVo.setFee_item_type(goodsFeePoRes.getFee_item_type());
							goodsFeeVo.setTotal_fee(goodsFeePoRes.getTotal_fee());
							goodsFeeVo.setMax_discount_fee(goodsFeePoRes.getMax_discount_fee());
							goodsFeeVoList.add(goodsFeeVo);
						}
					}
					if(goodsFeeVoList!=null && goodsFeeVoList.size()>0){
						goodMap.put("goods_fee_list", goodsFeeVoList);
					}


					//根据商品ID 查询商品Sku表
					GoodsSkuPo goodsSkuPo = new GoodsSkuPo();
					goodsSkuPo.setGoods_id(goodsId);
					List<GoodsSkuPo> goodsSkuPoList=goodsSkuServ.queryGoodsSkuByGoodsId(goodsSkuPo);
					List<InfoSkuDefineVo> skuDefineVoList= new ArrayList<InfoSkuDefineVo>();
					if(goodsSkuPoList!=null && goodsSkuPoList.size()>0){
						for(GoodsSkuPo skuPo : goodsSkuPoList){
							//根据sku_id查询sku定义表
							InfoSkuDefineVo infoSkuDefineVo = new InfoSkuDefineVo();
							infoSkuDefineVo.setSku_id(skuPo.getSku_id());
							infoSkuDefineVo.setSku_type(skuPo.getSku_type());
							infoSkuDefineVo.setBind_flag(skuPo.getBind_flag());
							SkuDefinePo skuDefinePo = new SkuDefinePo();
							skuDefinePo.setSku_id(skuPo.getSku_id());
							SkuDefinePo skuDefinePoRes=skuDefineServ.querySkuDefineBySkuId(skuDefinePo);
							if(skuDefinePoRes!=null){
								infoSkuDefineVo.setSku_name(skuDefinePoRes.getSku_name());
								infoSkuDefineVo.setSku_desc(skuDefinePoRes.getSku_desc());
							}
							
							SkuAttrPo skuAttrPo =new SkuAttrPo();
							skuAttrPo.setSku_id(skuPo.getSku_id());
							List<SkuAttrPo>skuAttrPos =skuAttrServ.querySkuAttrBySkuId(skuAttrPo);
							List<SkuAttrVo> skuAttrVos =new ArrayList<SkuAttrVo>();
							if(skuAttrPos !=null && skuAttrPos.size()>0){
								for(SkuAttrPo po2:skuAttrPos){
									SkuAttrVo vo =new SkuAttrVo();
									BeanUtils.copyProperties(po2, vo);
									skuAttrVos.add(vo);
								}
							}
							infoSkuDefineVo.setAttr_list(skuAttrVos);
							skuDefineVoList.add(infoSkuDefineVo);
						}
					}

					if(skuDefineVoList!=null && skuDefineVoList.size()>0){
						goodMap.put("goods_sku_list", skuDefineVoList);

					}
				}

				if(goodMap!=null){
					goodList.add(goodMap);
				}

			}
		}

		uocMessage.setRespCode(RespCodeContents.SUCCESS);
		uocMessage.setContent("获取触点sku信息成功");
		uocMessage.addArg("goods_list", goodList);
		return uocMessage;
	}

	public UocMessage getOptionalGoodsForAbilityPlatform(String  json_in) throws Exception{
		getBeanDu();
		Map<String, Object> map =jsonToBeanServDu.jsonToMap(json_in);

		String jsession_id =(String) map.get("jsession_id");
		UocMessage message =getOptionalGoods(jsession_id);
		return message;
	}

	@SuppressWarnings("unchecked")
	@Override
	public UocMessage getOptionalGoods(String jsession_id) {
		UocMessage message=new UocMessage();
		/*
		 * 调接口获得 工号信息oper_info
		 */
		Map<String,Object> oper_info =new HashMap<String,Object>();

		String json_info = "{\"SERVICE_NAME\":\"getBaseOperInfo\",\"param\":{\"jsession_id\":\"" + jsession_id + "\"}}";
		try{
			UocMessage res = ugcAbilityPlatformServDu.CallUgcAbilityPlatform(json_info, "", "UAC");
			if (res != null) {
				String code = (String) res.getArgs().get("code");
				if (code != null && "200".equals(code)) {
					String json_info_out = (String) res.getArgs().get("json_info");
					Map<String, Object> map = jsonToBeanServDu.jsonToMap(json_info_out);
					oper_info = (Map<String, Object>) map.get("oper_info");
					if (oper_info == null) {
						logger.info("----------无对应工号信息----------");
						message.setRespCode(RespCodeContents.PARAM_ERROR);
						message.setContent("无对应工号信息");
						return message;
					}
					logger.info("----------oper_info----------" + oper_info.toString());
				} else {
					logger.info("----------能力平台调用失败----------");
					message.setRespCode(RespCodeContents.ABILITY_PLATFORM_FAIL);
					return res;
				}
			}
		}catch(Exception e){
			logger.info("----------能力平台调用异常----------");
			e.printStackTrace();
			message.setRespCode(RespCodeContents.ABILITY_PLATFORM_FAIL);
			message.setContent("能力平台调用异常");
			return message;
		}
		String area_code=(String) oper_info.get("area_code");
		String province_code = (String) oper_info.get("province_code");
		String role_ids = (String) oper_info.get("role_id");

		String roleIds = "";
		GoodsInfo goodsInfo=new GoodsInfo();
		if (role_ids.contains(",")) {
			for (String roleId : role_ids.split(",")) {
				roleIds += "'" + roleId + "'" + ",";
			}
			goodsInfo.setRole_id(roleIds.substring(0, roleIds.length() - 1));
		} else {
			goodsInfo.setRole_id(role_ids);
		}
		goodsInfo.setProvince_code(province_code);
		goodsInfo.setArea_code(area_code);
		List<GoodsInfoVo> goodsInfoList=new ArrayList<GoodsInfoVo>();

		try{
			//根据角色ID关联查询出商品信息
			List<GoodsDefinePo> goodsList=goodsDefineServ.queryInfoGoodsDefineByRoleId(goodsInfo);
			if (goodsList != null && goodsList.size() > 0) {
				for (GoodsDefinePo infoGoodsDefinePo : goodsList) {
					GoodsInfoVo goodsInfoVo = new GoodsInfoVo();
					goodsInfoVo.setGoods_id(infoGoodsDefinePo.getGoods_id());
					goodsInfoVo.setGoods_name(infoGoodsDefinePo.getGoods_name());
					goodsInfoVo.setGoods_type(infoGoodsDefinePo.getGoods_type());
					goodsInfoVo.setGoods_desc(infoGoodsDefinePo.getGoods_desc());
					goodsInfoList.add(goodsInfoVo);
				}
			}
		}catch(Exception e){
			logger.info("----------商品信息查询异常----------");
			e.printStackTrace();
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("商品信息查询异常");
			return message;
		}

		message.addArg("goods_list", goodsInfoList);
		message.setContent("取可选商品信息成功");
		message.setRespCode(RespCodeContents.SUCCESS);
		return message;
	}

}
