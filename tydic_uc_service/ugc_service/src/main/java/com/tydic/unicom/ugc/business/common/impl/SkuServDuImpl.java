package com.tydic.unicom.ugc.business.common.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.tydic.unicom.ugc.base.dataBase.interfaces.GoodsDefineServ;
import com.tydic.unicom.ugc.base.dataBase.interfaces.GoodsSkuServ;
import com.tydic.unicom.ugc.base.dataBase.interfaces.SkuAttrServ;
import com.tydic.unicom.ugc.base.dataBase.interfaces.SkuDefineServ;
import com.tydic.unicom.ugc.base.dataBase.po.GoodsDefinePo;
import com.tydic.unicom.ugc.base.dataBase.po.GoodsSkuPo;
import com.tydic.unicom.ugc.base.dataBase.po.SkuAttrPo;
import com.tydic.unicom.ugc.base.dataBase.po.SkuDefinePo;
import com.tydic.unicom.ugc.business.common.constants.Constants;
import com.tydic.unicom.ugc.business.common.constants.OperType;
import com.tydic.unicom.ugc.business.common.interfaces.SkuServDu;
import com.tydic.unicom.ugc.business.common.vo.GoodsDefineVo;
import com.tydic.unicom.ugc.business.common.vo.GoodsSkuVo;
import com.tydic.unicom.ugc.business.common.vo.SkuAttrVo;
import com.tydic.unicom.ugc.business.common.vo.SkuDefineVo;
import com.tydic.unicom.ugc.service.common.impl.ToolSpring;
import com.tydic.unicom.ugc.service.common.interfaces.JsonToBeanServDu;
import com.tydic.unicom.ugc.service.common.interfaces.OperServDu;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocException;
import com.tydic.unicom.webUtil.UocMessage;

/**
 * 商品中心服务-sku相关接口默认实现类<br>
 * UGC0005-商品维护<br>
 * UGC0006-商品查询<br>
 * UGC0007-sku查询<br>
 * UGC0008-sku维护<br>
 * @create ChenKang by 2017-03-31
 * @modify ZhangCheng by 2017-06-05
 */
public class SkuServDuImpl implements SkuServDu {
	@Autowired
	private OperServDu operServDu;
	@Autowired
	private JsonToBeanServDu jsonToBeanServDu;
	@Autowired
	private GoodsSkuServ goodsSkuServ;
	@Autowired
	private SkuAttrServ skuAttrServ;
	@Autowired
	private SkuDefineServ skuDefineServ;
	@Autowired
	private GoodsDefineServ goodsDefineServ;

	private static Logger logger = LoggerFactory.getLogger(SkuServDuImpl.class);

	private void getBeanDu() {
		if (operServDu == null) {
			logger.info("====================operServDu is null============================" + operServDu);
			operServDu = (OperServDu) ToolSpring.getBean("OperServDu");
		}
		if (jsonToBeanServDu == null) {
			logger.info("====================jsonToBeanServDu is null============================" + jsonToBeanServDu);
			jsonToBeanServDu = (JsonToBeanServDu) ToolSpring.getBean("JsonToBeanServDu");
		}
		if (goodsSkuServ == null) {
			logger.info("====================goodsSkuServ is null============================" + goodsSkuServ);
			goodsSkuServ = (GoodsSkuServ) ToolSpring.getBean("GoodsSkuServ");
		}
		if (goodsDefineServ == null) {
			logger.info("====================apGoodsServ is null============================" + goodsDefineServ);
			goodsDefineServ = (GoodsDefineServ) ToolSpring.getBean("GoodsDefineServ");
		}
		if (skuAttrServ == null) {
			logger.info("====================skuAttrServ is null============================" + skuAttrServ);
			skuAttrServ = (SkuAttrServ) ToolSpring.getBean("SkuAttrServ");
		}
		if (skuDefineServ == null) {
			logger.info("====================skuDefineServ is null============================" + skuDefineServ);
			skuDefineServ = (SkuDefineServ) ToolSpring.getBean("SkuDefineServ");
		}
	}

	public UocMessage createOperateGooodsSkuForAbilityPlatform(String json_in) throws Exception {
		getBeanDu();
		Map<String, Object> map = jsonToBeanServDu.jsonToMap(json_in);

		String jsession_id = (String) map.get("jsession_id");
		String oper_type = (String) map.get("oper_type");
		@SuppressWarnings("unchecked")
		String json_info = jsonToBeanServDu.mapToJson((Map<String, Object>) map.get("json_info"));
		UocMessage message = createOperateGooodsDefine(jsession_id, json_info, oper_type);
		return message;
	}

	public UocMessage queryGoodsAndGoodsSkuForAbilityPlatform(String json_in) throws Exception {
		getBeanDu();
		Map<String, Object> map = jsonToBeanServDu.jsonToMap(json_in);

		String jsession_id = (String) map.get("jsession_id");
		@SuppressWarnings("unchecked")
		String json_info = jsonToBeanServDu.mapToJson((Map<String, Object>) map.get("json_info"));
		UocMessage message = queryGoodsDefineList(jsession_id, json_info);
		return message;
	}

	public UocMessage querySkuDefineAndSkuAttrForAbilityPlatform(String json_in) throws Exception {
		getBeanDu();
		Map<String, Object> map = jsonToBeanServDu.jsonToMap(json_in);

		String jsession_id = (String) map.get("jsession_id");
		@SuppressWarnings("unchecked")
		String json_info = jsonToBeanServDu.mapToJson((Map<String, Object>) map.get("json_info"));
		UocMessage message = querySkuDefineAndSkuAttr(jsession_id, json_info);
		return message;
	}

	public UocMessage createOperateSkuForAbilityPlatform(String json_in) throws Exception {
		getBeanDu();
		Map<String, Object> map = jsonToBeanServDu.jsonToMap(json_in);

		String jsession_id = (String) map.get("jsession_id");
		String oper_type = (String) map.get("oper_type");
		@SuppressWarnings("unchecked")
		String json_info = jsonToBeanServDu.mapToJson((Map<String, Object>) map.get("json_info"));
		UocMessage message = createOperateSku(jsession_id, json_info,oper_type);
		return message;
	}

	/**
	 * UGC0005-商品维护
	 * @param jsession_id
	 * @param json_info goods_define表字段
	 * @param oper_type
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public UocMessage createOperateGooodsDefine(String jsession_id, String json_info, String oper_type) throws Exception {
		UocMessage message = new UocMessage();
		// 1、通过UGCBS0001能力平台服务调用认证中心服务UAC0005，校验是否登陆
		UocMessage loginMessage = operServDu.isLogin(jsession_id);
		if (!"0000".equals(loginMessage.getRespCode().toString())) {
			logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>需要登陆<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("需要登陆");
			return message;
		}
		@SuppressWarnings("unchecked")
		Map<String, Object> oper_info = (Map<String, Object>) loginMessage.getArgs().get("oper_info");
		String province_code = oper_info.get("province_code").toString();
		String area_code = oper_info.get("area_code").toString();
		String oper_no = oper_info.get("oper_no").toString();

		// 2、解析服务解析传入的JSON串，支持对触点商品表进行增、删、改操作
		Map<String, Object> map = jsonToBeanServDu.jsonToMap(json_info);
		boolean operateResult = false;
		try {
			if (map.containsKey(Constants.GOODS_DEFINE)) {
				if (map.get(Constants.GOODS_DEFINE) == null || StringUtils.isEmpty(map.get(Constants.GOODS_DEFINE))) {
					message.setRespCode(RespCodeContents.SUCCESS);
					message.setContent("goods_define参数为空");
					return message;
				}

				Map<String, Object> mapVo = (Map<String, Object>) map.get(Constants.GOODS_DEFINE);
				GoodsDefinePo goodsDefinePo = new GoodsDefinePo();
				org.apache.commons.beanutils.BeanUtils.populate(goodsDefinePo, mapVo);
				goodsDefinePo.setArea_code(area_code);
				goodsDefinePo.setProvince_code(province_code);

				if (Constants.ADD.equals(oper_type)) {
					goodsDefinePo.setCreate_staff(oper_no);
					operateResult = goodsDefineServ.addGoodsDefine(goodsDefinePo);
				} else if (Constants.UPDATE.equals(oper_type)) {
					List<GoodsDefinePo> existPo = goodsDefineServ.queryGoodsDefinePoByPo(goodsDefinePo);
					if (existPo != null && existPo.size() > 0) {
						goodsDefinePo.setCreate_staff(existPo.get(0).getCreate_staff());
						goodsDefinePo.setCreate_time(existPo.get(0).getCreate_time());
					}
					operateResult = goodsDefineServ.updateGoodsDefine(goodsDefinePo);
				} else if (Constants.DELETE.equals(oper_type)) {
					operateResult = goodsDefineServ.deleteGoodsDefine(goodsDefinePo);
				}
			}

			if (map.containsKey(Constants.GOODS_SKU)) {
				if (map.get(Constants.GOODS_SKU) == null || StringUtils.isEmpty(map.get(Constants.GOODS_SKU))) {
					message.setRespCode(RespCodeContents.SUCCESS);
					message.setContent("goods_sku参数为空");
					return message;
				}

				Map<String, Object> mapVo = (Map<String, Object>) map.get(Constants.GOODS_SKU);
				GoodsSkuPo goodsSkuPo = new GoodsSkuPo();
				org.apache.commons.beanutils.BeanUtils.populate(goodsSkuPo, mapVo);

				if (Constants.ADD.equals(oper_type)) {
					String[] sku_ids = goodsSkuPo.getSku_id().split(",");
					String[] sku_types = goodsSkuPo.getSku_type().split(",");
					for (int i = 0; i < sku_ids.length; i++) {
						GoodsSkuPo addGoodsSkuPo = new GoodsSkuPo();
						addGoodsSkuPo.setId(System.currentTimeMillis() + "");
						addGoodsSkuPo.setBind_flag("10");
						addGoodsSkuPo.setGoods_id(goodsSkuPo.getGoods_id());
						addGoodsSkuPo.setSku_id(sku_ids[i]);
						addGoodsSkuPo.setSku_type(sku_types[i]);
						operateResult = goodsSkuServ.addGoodsSku(addGoodsSkuPo);
					}
				} else if (Constants.UPDATE.equals(oper_type)) {
					// 删除后再新增
					operateResult = goodsSkuServ.deleteGoodsSku(goodsSkuPo);

					String[] sku_ids = goodsSkuPo.getSku_id().split(",");
					String[] sku_types = goodsSkuPo.getSku_type().split(",");
					for (int i = 0; i < sku_ids.length; i++) {
						GoodsSkuPo addGoodsSkuPo = new GoodsSkuPo();
						addGoodsSkuPo.setId(System.currentTimeMillis() + "");
						addGoodsSkuPo.setBind_flag("10");
						addGoodsSkuPo.setGoods_id(goodsSkuPo.getGoods_id());
						addGoodsSkuPo.setSku_id(sku_ids[i]);
						addGoodsSkuPo.setSku_type(sku_types[i]);
						operateResult = goodsSkuServ.addGoodsSku(addGoodsSkuPo);
					}
				} else if (Constants.DELETE.equals(oper_type)) {
					operateResult = goodsSkuServ.deleteGoodsSku(goodsSkuPo);
				}
			}


			if (!operateResult) {
				message.setRespCode(RespCodeContents.SERVICE_FAIL);
				message.setContent("操作失败");
				return message;
			}
		} catch (Exception e) {
			e.printStackTrace();
			UocMessage uocExceptionMsg = new UocMessage();
			uocExceptionMsg.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocExceptionMsg.setContent("商品维护操作异常");
			throw new UocException(uocExceptionMsg);
		}

		message.setRespCode(RespCodeContents.SUCCESS);
		message.setContent("商品维护操作成功");
		return message;
	}

	/**
	 * UGC0006-商品查询
	 * @param jsession_id
	 * @param json_info  goods_define表字段
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public UocMessage queryGoodsDefineList(String jsession_id, String json_info) throws Exception {
		UocMessage message = new UocMessage();
		// 1、通过UGCBS0001能力平台服务调用认证中心服务UAC0005，校验是否登陆
		UocMessage loginMessage = operServDu.isLogin(jsession_id);
		if (!"0000".equals(loginMessage.getRespCode().toString())) {
			logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>需要登陆<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("需要登陆");
			return message;
		}
		Map<String, Object> oper_info = (Map<String, Object>) loginMessage.getArgs().get("oper_info");
		String province_code = oper_info.get("province_code").toString();
		String area_code = oper_info.get("area_code").toString();

		JSONObject params = JSONObject.fromObject(json_info);
		int page_no = Integer.parseInt(params.getString("page_no"));
		int page_size = Integer.parseInt(params.getString("page_size"));
		int total_num = 0;
		int total_page = 0;
		// 2、解析服务解析传入的JSON串，支持对触点商品表进行查询操作
		Map<String, Object> map = jsonToBeanServDu.jsonToMap(json_info);
		List<GoodsDefineVo> goodsDefineVos = new ArrayList<GoodsDefineVo>();
		List<GoodsSkuVo> goodsSkuVos = new ArrayList<GoodsSkuVo>();
		try {
			if (map.containsKey(Constants.GOODS_DEFINE)) {
				if (map.get(Constants.GOODS_DEFINE) != null && !StringUtils.isEmpty(map.get(Constants.GOODS_DEFINE))) {
					Map<String, Object> mapVo = (Map<String, Object>) map.get(Constants.GOODS_DEFINE);
					GoodsDefinePo goodsDefinePo = new GoodsDefinePo();
					org.apache.commons.beanutils.BeanUtils.populate(goodsDefinePo, mapVo);
					goodsDefinePo.setArea_code(area_code);
					goodsDefinePo.setProvince_code(province_code);
					List<GoodsDefinePo> goodsDefineList = goodsDefineServ.queryGoodsDefineByPage(goodsDefinePo, page_no, page_size);

					if (goodsDefineList != null && goodsDefineList.size() > 0) {
						total_num = goodsDefineServ.queryGoodsDefineCount(goodsDefinePo);
						total_page = (total_num + page_size - 1) / page_size;
						for (GoodsDefinePo po : goodsDefineList) {
							GoodsDefineVo vo = new GoodsDefineVo();
							BeanUtils.copyProperties(po, vo);
							goodsDefineVos.add(vo);
						}
					}
				}
			}

			if (map.containsKey(Constants.GOODS_SKU)) {
				if (map.get(Constants.GOODS_SKU) != null && !StringUtils.isEmpty(map.get(Constants.GOODS_SKU))) {
					Map<String, Object> mapVo = (Map<String, Object>) map.get(Constants.GOODS_SKU);
					GoodsSkuPo goodsSkuPo = new GoodsSkuPo();
					org.apache.commons.beanutils.BeanUtils.populate(goodsSkuPo, mapVo);

					List<GoodsSkuPo> goodsSkuList = goodsSkuServ.queryGoodsSkuByGoodsId(goodsSkuPo);

					if (goodsSkuList != null && goodsSkuList.size() > 0) {
						for (GoodsSkuPo po : goodsSkuList) {
							GoodsSkuVo vo = new GoodsSkuVo();
							BeanUtils.copyProperties(po, vo);
							goodsSkuVos.add(vo);
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			message.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			message.setContent("商品查询异常");
			return message;
		}

		message.addArg("goods_define_list", goodsDefineVos);
		message.addArg("goods_sku_list", goodsSkuVos);
		message.addArg("page_no", page_no);
		message.addArg("page_size", page_size);
		message.addArg("total_num", total_num);
		message.addArg("total_page", total_page);
		message.setRespCode(RespCodeContents.SUCCESS);
		message.setContent("商品查询成功");

		return message;
	}

	/**
	 * UGC0007-sku查询
	 * @param jsession_id
	 * @param json_info
	 * @return
	 */
	@Override
	@Deprecated
	public UocMessage querySkuDefineAndSkuAttr(String jsession_id, String json_info) throws Exception {
		logger.info("[UGC0007]请求参数：jsession_id={},json_info={}",jsession_id,json_info);
		UocMessage message = new UocMessage();
		// 1、通过UGCBS0001能力平台服务调用认证中心服务UAC0005，校验是否登陆
		UocMessage loginMessage = operServDu.isLogin(jsession_id);
		if (!"0000".equals(loginMessage.getRespCode().toString())) {
			logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>需要登陆<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("需要登陆");
			return message;
		}
		// 2、解析服务解析传入的JSON串，支持对触点商品表进行查询操作
		Map<String, Object> map = jsonToBeanServDu.jsonToMap(json_info);
		List<SkuAttrVo> skuAttrVos = new ArrayList<SkuAttrVo>();
		try {
			if (map.containsKey(Constants.SKU_ATTR)) {
				String json_bean = map.get(Constants.SKU_ATTR).toString();
				SkuAttrPo skuAttrPo = (SkuAttrPo) jsonToBeanServDu.jsonInfoToBean(json_bean, SkuAttrPo.class);
				List<SkuAttrPo> skuAttrPos = skuAttrServ.querySkuAttrBySkuId(skuAttrPo);
				if (skuAttrPos != null && skuAttrPos.size() > 0) {
					for (SkuAttrPo po : skuAttrPos) {
						SkuAttrVo vo = new SkuAttrVo();
						BeanUtils.copyProperties(po, vo);
						skuAttrVos.add(vo);
					}
				}
				message.addArg("skuAttrVos", skuAttrVos);
			}
			if (map.containsKey(Constants.SKU_DEFINE)) {
				String json_bean = map.get(Constants.SKU_DEFINE).toString();
				SkuDefinePo skuDefinePo = (SkuDefinePo) jsonToBeanServDu.jsonInfoToBean(json_bean, SkuDefinePo.class);
				skuDefinePo = skuDefineServ.querySkuDefineBySkuId(skuDefinePo);
				if (skuDefinePo != null) {
					SkuDefineVo vo = new SkuDefineVo();
					BeanUtils.copyProperties(skuDefinePo, vo);
					message.addArg("skuDefineVo", vo);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			message.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			message.setContent("sku查询异常");
			return message;
		}

		message.setRespCode(RespCodeContents.SUCCESS);
		message.setContent("sku查询返回成功");
		return message;
	}
	/**
	 * UGC0007-sku查询
	 */
	@Override
	public UocMessage querySkuDefineAndSkuAttr(String jsession_id, String oper_sku, String json_info) throws Exception {
		logger.info("[UGC0007]请求参数：jsession_id={},json_info={}",jsession_id,json_info);
		UocMessage respMessage = new UocMessage();

		// 登录校验
		UocMessage loginMessage = operServDu.isLogin(jsession_id);
		if (!"0000".equals(loginMessage.getRespCode().toString())) {
			logger.info("[UGC0007]需要登录");
			respMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			respMessage.setContent("需要登陆");
			return respMessage;
		}

		// sku定义表
		if(Constants.OPER_SKU_DEFINE.equals(oper_sku)){
			SkuDefinePo requSkuDefinePo = (SkuDefinePo) jsonToBeanServDu.jsonInfoToBean(json_info, SkuDefinePo.class);
			List<SkuDefinePo> SkuDefinePoList = skuDefineServ.querySkuDefineBySkuIdOrSkuName(requSkuDefinePo);
			List<SkuDefineVo> SkuDefineVoList = new ArrayList<SkuDefineVo>();
			if(SkuDefinePoList == null || SkuDefinePoList.size() < 1){
				respMessage.setRespCode(RespCodeContents.PARAM_ERROR);
				respMessage.setContent("sku查询为空");
				return respMessage;
			}

			// 封装数据
			for(SkuDefinePo skuDefinePoTemp : SkuDefinePoList){
				SkuDefineVo skuDefineVoTemp = new SkuDefineVo();
				BeanUtils.copyProperties(skuDefinePoTemp, skuDefineVoTemp);
				SkuDefineVoList.add(skuDefineVoTemp);
			}
			respMessage.setRespCode(RespCodeContents.SUCCESS);
			respMessage.addArg("sku_define_list", SkuDefineVoList);
			respMessage.setContent("sku查询成功");
			return respMessage;
		}

		// sku属性表
		if(Constants.OPER_SKU_ATTR.equals(oper_sku)){
			SkuAttrPo requSkuAttrPo = (SkuAttrPo) jsonToBeanServDu.jsonInfoToBean(json_info, SkuAttrPo.class);
			List<SkuAttrPo> skuAttrPoList = skuAttrServ.querySkuAttrBySkuId(requSkuAttrPo);
			List<SkuAttrVo> skuAttrVoList = new ArrayList<SkuAttrVo>();
			if(skuAttrPoList == null || skuAttrPoList.size() < 1){
				respMessage.setRespCode(RespCodeContents.PARAM_ERROR);
				respMessage.setContent("sku查询为空");
				return respMessage;
			}

			// 封装数据
			for(SkuAttrPo skuAttrPoTemp : skuAttrPoList){
				SkuAttrVo skuAttrVoTemp = new SkuAttrVo();
				BeanUtils.copyProperties(skuAttrPoTemp, skuAttrVoTemp);
				skuAttrVoList.add(skuAttrVoTemp);
			}
			respMessage.setRespCode(RespCodeContents.SUCCESS);
			respMessage.addArg("sku_attr_list", skuAttrVoList);
			respMessage.setContent("sku查询成功");
			return respMessage;
		}

		logger.info("[UGC0007] 暂不支持该 oper_sku 方式，oper_sku={}",oper_sku);
		respMessage.setRespCode(RespCodeContents.PARAM_ERROR);
		respMessage.setContent("暂不支持该 oper_sku 方式");
		return respMessage;
	}

	/**
	 * UGC0008-sku维护
	 * @param jsession_id
	 * @param json_info
	 * @param oper_type
	 * @return
	 * @throws Exception
	 */
	@Override
	@Deprecated
	public UocMessage createOperateSku(String jsession_id, String json_info,
			String oper_type) throws Exception {

		UocMessage message = new UocMessage();
		// 1、通过UGCBS0001能力平台服务调用认证中心服务UAC0005，校验是否登陆
		UocMessage loginMessage = operServDu.isLogin(jsession_id);
		if (!"0000".equals(loginMessage.getRespCode().toString())) {
			logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>需要登陆<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("需要登陆");
			return message;
		}

		// 2、解析服务解析传入的JSON串，支持对触点商品表进行查询操作
		Map<String, Object> map = jsonToBeanServDu.jsonToMap(json_info);
//		String goods_id = map.containsKey("goods_id") ? map.get("goods_id").toString() : "";
//		String sku_type = map.containsKey("sku_type") ? map.get("sku_type").toString() : "";
//		String sku_id = map.containsKey("sku_id") ? map.get("sku_id").toString() : "";
//		String bind_flag = map.containsKey("bind_flag") ? map.get("bind_flag").toString() : "";

		boolean operateResult =false;
		try{
			 if(map.containsKey(Constants.SKU_ATTR)){
				 String json_bean =(String) map.get(Constants.SKU_ATTR);
				 SkuAttrPo skuAttrPo =(SkuAttrPo) jsonToBeanServDu.jsonInfoToBean(json_bean, SkuAttrPo.class);
				if (Constants.ADD.equals(oper_type)) {
					skuAttrPo.setId(System.currentTimeMillis() + "");
					operateResult =skuAttrServ.addSkuAttrPo(skuAttrPo);

				}else if(Constants.UPDATE.equals(oper_type)){

					operateResult =skuAttrServ.updateSkuAttrPo(skuAttrPo);

				}else if(Constants.DELETE.equals(oper_type)){

					operateResult =skuAttrServ.deleteSkuAttrPo(skuAttrPo);

				}

				if (!operateResult) {
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("操作失败");
					return message;
				}
			 }

			 if(map.containsKey(Constants.SKU_DEFINE)){
				 	String json_bean =(String) map.get(Constants.SKU_DEFINE);
					SkuDefinePo skuDefinePo =(SkuDefinePo) jsonToBeanServDu.jsonInfoToBean(json_bean, SkuDefinePo.class);
				 if (Constants.ADD.equals(oper_type)) {

						operateResult =skuDefineServ.addSkuDefinePo(skuDefinePo);

					}else if(Constants.UPDATE.equals(oper_type)){

						operateResult =skuDefineServ.updateSkuDefinePo(skuDefinePo);

					}else if(Constants.DELETE.equals(oper_type)){

						operateResult =skuDefineServ.deleteSkuDefinePo(skuDefinePo);

					}

			 }

		}catch(Exception e){
			e.printStackTrace();
			message.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			message.setContent("sku维护 操作异常");
			return message;
		}

		message.setRespCode(RespCodeContents.SUCCESS);
		message.setContent("sku维护 操作成功");
		return message;
	}
	/**
	 * UGC0008-sku维护
	 */
	@Override
	public UocMessage createOperateSku(String jsession_id, String json_info, String oper_sku, String oper_type)
			throws Exception {
		logger.info("[UGC0008] 请求参数：jsession_id={},oper_sku={},oper_type={},json_info={}",jsession_id,oper_sku,oper_type,json_info);
		UocMessage respMessage = new UocMessage();

		// 登录校验
		UocMessage loginMessage = operServDu.isLogin(jsession_id);
		if (!"0000".equals(loginMessage.getRespCode().toString())) {
			logger.info("[UGC0008] 需要登录");
			respMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			respMessage.setContent("需要登陆");
			return respMessage;
		}

		// sku定义表
		if(Constants.OPER_SKU_DEFINE.equals(oper_sku)){
			SkuDefinePo requSkuDefinePo = (SkuDefinePo) jsonToBeanServDu.jsonInfoToBean(json_info, SkuDefinePo.class);

			if(OperType.SAVE.equals(oper_type)){
				if(skuDefineServ.addSkuDefinePo(requSkuDefinePo)){
					respMessage.setRespCode(RespCodeContents.SUCCESS);
					respMessage.setContent("新增成功");
					return respMessage;
				}
				respMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
				respMessage.setContent("新增失败");
				return respMessage;
			}
			if(OperType.UPDATE.equals(oper_type)){
				if(skuDefineServ.updateSkuDefinePo(requSkuDefinePo)){
					respMessage.setRespCode(RespCodeContents.SUCCESS);
					respMessage.setContent("修改成功");
					return respMessage;
				}
				respMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
				respMessage.setContent("修改失败");
				return respMessage;
			}
			if(OperType.REMOVE.equals(oper_type)){
				if(skuDefineServ.deleteSkuDefinePo(requSkuDefinePo)){
					respMessage.setRespCode(RespCodeContents.SUCCESS);
					respMessage.setContent("删除成功");
					return respMessage;
				}
				respMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
				respMessage.setContent("删除失败");
				return respMessage;
			}

			logger.info("[UGC0008] 暂不支持该 oper_type 方式，oper_type={}",oper_type);
			respMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			respMessage.setContent("暂不支持该 oper_type 方式");
			return respMessage;
		}

		// sku属性表
		if(Constants.OPER_SKU_ATTR.equals(oper_sku)){
			SkuAttrPo requSkuAttrPo = (SkuAttrPo) jsonToBeanServDu.jsonInfoToBean(json_info, SkuAttrPo.class);

			if(OperType.SAVE.equals(oper_type)){
				if(skuAttrServ.addSkuAttrPo(requSkuAttrPo)){
					respMessage.setRespCode(RespCodeContents.SUCCESS);
					respMessage.setContent("新增成功");
					return respMessage;
				}
				respMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
				respMessage.setContent("新增失败");
				return respMessage;
			}
			if(OperType.UPDATE.equals(oper_type)){
				if(skuAttrServ.updateSkuAttrPo(requSkuAttrPo)){
					respMessage.setRespCode(RespCodeContents.SUCCESS);
					respMessage.setContent("修改成功");
					return respMessage;
				}
				respMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
				respMessage.setContent("修改失败");
				return respMessage;
			}
			if(OperType.REMOVE.equals(oper_type)){
				if(skuAttrServ.deleteSkuAttrPo(requSkuAttrPo)){
					respMessage.setRespCode(RespCodeContents.SUCCESS);
					respMessage.setContent("删除成功");
					return respMessage;
				}
				respMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
				respMessage.setContent("删除失败");
				return respMessage;
			}

			logger.info("[UGC0008] 暂不支持该 oper_type 方式，oper_type={}",oper_type);
			respMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			respMessage.setContent("暂不支持该 oper_type 方式");
			return respMessage;
		}

		logger.info("[UGC0008] 暂不支持该 oper_sku 方式，oper_sku={}",oper_sku);
		respMessage.setRespCode(RespCodeContents.PARAM_ERROR);
		respMessage.setContent("暂不支持该 oper_sku 方式");
		return respMessage;
	}

}
