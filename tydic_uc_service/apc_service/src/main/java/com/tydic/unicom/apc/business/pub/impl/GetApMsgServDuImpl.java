package com.tydic.unicom.apc.business.pub.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.tydic.unicom.apc.business.pub.interfaces.GetApMsgServDu;
import com.tydic.unicom.apc.business.pub.vo.ApDefineVo;
import com.tydic.unicom.apc.business.pub.vo.ApDeveloperVo;
import com.tydic.unicom.apc.business.pub.vo.FeeInfoVo;
import com.tydic.unicom.apc.business.pub.vo.InfoGoodsFeeVo;
import com.tydic.unicom.apc.business.pub.vo.TariffInfoVo;
import com.tydic.unicom.apc.business.pub.vo.TariffShowDetailVo;
import com.tydic.unicom.apc.service.common.interfaces.ApcAbilityPlatformServDu;
import com.tydic.unicom.apc.service.pub.interfaces.ApDefineServDu;
import com.tydic.unicom.apc.service.pub.interfaces.ApDeveloperServDu;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

public class GetApMsgServDuImpl implements GetApMsgServDu{

	Logger logger = Logger.getLogger(GetApMsgServDuImpl.class);

	@Autowired
	private ApDefineServDu apDefineServDu;

	@Autowired
	private ApDeveloperServDu apDeveloperServDu;

	@Autowired
	private ApcAbilityPlatformServDu apcAbilityPlatformServDu;



	@Override
	public UocMessage queryApMsg(String ap_id) throws Exception{
		UocMessage message = new UocMessage();

		if (null == ap_id || "".equals(ap_id)) {
			message.setContent(RespCodeContents.PARAM_ERROR);
			message.setContent("ap_id不能为空");
			return message;
		}

		List<TariffInfoVo> tariffInfoList = new ArrayList<TariffInfoVo>();
		JSONArray ywbArray=new JSONArray();
		
		//根据触点ID 获取触点信息
		ApDefineVo apDefineVo = new ApDefineVo();
		apDefineVo.setAp_id(ap_id); 
		ApDefineVo defineVo = new ApDefineVo();
		defineVo=apDefineServDu.getApDefine(apDefineVo);

		//根据触点ID 获取触点发展人信息表
		ApDeveloperVo apDeveloperVo = new ApDeveloperVo();
		apDeveloperVo.setAp_id(ap_id);
		List<ApDeveloperVo> apDeveloperVoList = new ArrayList<ApDeveloperVo>();
		apDeveloperVoList=apDeveloperServDu.queryApDeveloperByVo(apDeveloperVo);

		//通过APCBS0001能力平台服务调用商品中心UGC0001服务
		String json_info ="{\"SERVICE_NAME\":\"queryContactSku\",\"params\":{\"ap_id\":\""+ap_id+"\"}}";
		UocMessage uocMessage=apcAbilityPlatformServDu.CallApcAbilityPlatform(json_info, "", "UGC");

		if(!"0000".equals(uocMessage.getRespCode().toString())){
			return uocMessage;
		}else{
			String code=uocMessage.getArgs().get("code").toString();
			logger.info("===============调用能力平台（获取触点Sku信息）返回报文================="+code);
			String resultStr = (String) uocMessage.getArgs().get("json_info");
			logger.info("===============调用能力平台（获取触点Sku信息）返回报文================="+resultStr);


			if(!"200".equals(code)){
				uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
				uocMessage.setContent("获取触点Sku信息失败");
				return uocMessage;
			}

			JSONObject json = JSONObject.fromObject(resultStr); 
			String goodList=json.get("goods_list").toString();
			if(goodList!=null && !"".equals(goodList)){
				JSONArray jsonArray = JSONArray.fromObject(goodList);		
				for(int i=0;i<jsonArray.size();i++){
					JSONObject object = jsonArray.getJSONObject(i);
					if(object!=null && !object.isNullObject()){
						String goodId=object.get("goods_id").toString();
						JSONArray myJsonArray = JSONArray.fromObject(object.get("goods_sku_list"));
						for(int j=0;j<myJsonArray.size();j++){
							JSONObject myjObject = myJsonArray.getJSONObject(j);
							if(myjObject!=null && !myjObject.isNullObject()){
								String skuType=myjObject.get("sku_type").toString();
								String bindFlag=myjObject.get("bind_flag").toString();
								if("01".equals(skuType) && "10".equals(bindFlag)){
									String skuId=myjObject.get("sku_id").toString();
									String skuName=myjObject.get("sku_name").toString();
									String skuDesc=myjObject.get("sku_desc").toString();
									TariffInfoVo tariffInfoVo = new TariffInfoVo();
									tariffInfoVo.setTariff_id(skuId);
									tariffInfoVo.setTariff_name(skuName);
									tariffInfoVo.setTariff_desc(skuDesc);
									String attrList=myjObject.get("attr_list").toString();
									TariffShowDetailVo tariffShowDetailVo =new TariffShowDetailVo();
									if(attrList!=null && !"".equals(attrList)){
										JSONArray attrArray = JSONArray.fromObject(attrList);	

										for(int n=0;n<attrArray.size();n++){
											JSONObject attrObject = attrArray.getJSONObject(n);
											if(attrObject!=null && !attrObject.isNullObject()){
												String attr_id=attrObject.get("attr_id").toString();
												String attr_value=attrObject.get("attr_value").toString();
												if("pic_url".equals(attr_id)){
													tariffInfoVo.setPic_url(attr_value);
												}else if("tele_type".equals(attr_id)){
													tariffInfoVo.setTele_type(attr_value);
												}else if("pay_flag".equals(attr_id)){
													tariffInfoVo.setPay_flag(attr_value);
												}else if("oper_code".equals(attr_id)){
													tariffInfoVo.setOper_code(attr_value);
												}else if("activity_id".equals(attr_id)){
													tariffInfoVo.setActivity_id(attr_value);
												}else if("product_id".equals(attr_id)){
													tariffInfoVo.setProduct_id(attr_value);
												}else if("good_num_flag".equals(attr_id)){
													tariffInfoVo.setGood_num_flag(attr_value);
												}else if("receive_area_flag".equals(attr_id)){
													tariffInfoVo.setReceive_area_flag(attr_value);
												}else if("send_type0_flag".equals(attr_id)){
													tariffInfoVo.setSend_type0_flag(attr_value);
												}else if("send_type1_flag".equals(attr_id)){
													tariffInfoVo.setSend_type1_flag(attr_value);
												}else if("send_type2_flag".equals(attr_id)){
													tariffInfoVo.setSend_type2_flag(attr_value);
												}else if("rent".equals(attr_id)){
													tariffShowDetailVo.setRent(attr_value);
												}else if("province_flow_charge".equals(attr_id)){
													tariffShowDetailVo.setProvince_flow_charge(attr_value);
												}else if("country_flow_charge".equals(attr_id)){
													tariffShowDetailVo.setCountry_flow_charge(attr_value);
												}else if("province_minutes_charge".equals(attr_id)){
													tariffShowDetailVo.setProvince_minutes_charge(attr_value);
												}else if("country_minutes_charge".equals(attr_id)){
													tariffShowDetailVo.setCountry_minutes_charge(attr_value);
												}else if("province_free_minutes".equals(attr_id)){
													tariffShowDetailVo.setProvince_free_minutes(attr_value);
												}else if("country_free_minutes".equals(attr_id)){
													tariffShowDetailVo.setCountry_free_minutes(attr_value);
												}else if("province_free_flow".equals(attr_id)){
													tariffShowDetailVo.setProvince_free_flow(attr_value);
												}else if("country_free_flow".equals(attr_id)){
													tariffShowDetailVo.setCountry_free_flow(attr_value);
												}else if("other_info".equals(attr_id)){
													tariffShowDetailVo.setOther_info(attr_value);
												}
											}

										}

									}

									tariffInfoVo.setTariff_show_detail(tariffShowDetailVo);
									JSONArray youJsonArray = JSONArray.fromObject(object.get("goods_fee_list"));
									List<FeeInfoVo> fee_list = new ArrayList<FeeInfoVo>();

									for(int m=0;m<youJsonArray.size();m++){
										JSONObject youjObject = youJsonArray.getJSONObject(m);
										if(youjObject!=null && !youjObject.isNullObject()){
											InfoGoodsFeeVo infoGoodsFeeVo =(InfoGoodsFeeVo)JSONObject.toBean(youjObject, InfoGoodsFeeVo.class);									
											if(infoGoodsFeeVo!=null){
												FeeInfoVo feeInfoVo = new FeeInfoVo();
												feeInfoVo.setFee_item_code(infoGoodsFeeVo.getFee_item_code());
												feeInfoVo.setFee_item_name(infoGoodsFeeVo.getFee_item_code());
												feeInfoVo.setFee_item_type(infoGoodsFeeVo.getFee_item_type());
												feeInfoVo.setTotal_fee(infoGoodsFeeVo.getTotal_fee());
												feeInfoVo.setMax_discount_fee(infoGoodsFeeVo.getMax_discount_fee());
												fee_list.add(feeInfoVo);
											}
										}
									}

									tariffInfoVo.setFee_list(fee_list);
									tariffInfoVo.setGoods_id(goodId);
									tariffInfoList.add(tariffInfoVo);

								}else if("02".equals(skuType)){
									String attrList=myjObject.get("attr_list").toString();
									if(attrList!=null && !"".equals(attrList)){
										JSONObject ywbJsonObject=new JSONObject();
										JSONArray attrArray = JSONArray.fromObject(attrList);
										for(int n=0;n<attrArray.size();n++){
											JSONObject attrObject = attrArray.getJSONObject(n);
											if(attrObject!=null && !attrObject.isNullObject()){
												String attr_id=attrObject.get("attr_id").toString();
												String attr_value=attrObject.get("attr_value").toString();
												if("ywb_code".equals(attr_id)){
													ywbJsonObject.put("ywb_code", attr_value);
												}else if ("element_id".equals(attr_id)) {
													ywbJsonObject.put("element_id", attr_value);
												}else if ("element_type".equals(attr_id)) {
													ywbJsonObject.put("element_type", attr_value);
												}
											}
												
										}
										ywbArray.add(ywbJsonObject);
									}
								}
							}
						}

					}
				}

			}
		}


		message.setRespCode(RespCodeContents.SUCCESS);;
		message.setContent("获取触点信息成功");
		message.addArg("ap_info", defineVo);
		message.addArg("tariff_info_list", tariffInfoList);
		message.addArg("developer_list", apDeveloperVoList);
		message.addArg("ywb_info_list", ywbArray);
		return message;

	}


}

