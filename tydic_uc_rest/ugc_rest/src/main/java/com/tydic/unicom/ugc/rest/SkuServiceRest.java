package com.tydic.unicom.ugc.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tydic.unicom.crm.web.uss.constants.ControllerMappings;
import com.tydic.unicom.crm.web.uss.constants.UrlsMappings;
import com.tydic.unicom.ugc.business.common.interfaces.SkuServDu;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;
/**
 * 商品中心服务-sku相关REST接口<br>
 * UGC0005-商品维护<br>
 * UGC0006-商品查询<br>
 * UGC0007-sku查询<br>
 * UGC0008-sku维护<br>
 * @create ChenKang by 2017-03-31
 * @modify ZhangCheng by 2017-06-05
 */
@Controller
@RequestMapping(value = ControllerMappings.SKU_REST)
public class SkuServiceRest {

	@Autowired
	private SkuServDu skuServDu;

	private static Logger logger = LoggerFactory.getLogger(SkuServiceRest.class);

	/**
	 * UGC0005-商品维护
	 * @param jsession_id
	 * @param json_info goods_define表字段
	 * @param oper_type
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = UrlsMappings.OPERATE_GOODS_DEFINE, method = RequestMethod.POST)
	@ResponseBody
	public UocMessage operateGooodsSku(String jsession_id, String json_info, String oper_type) {
		UocMessage message =new UocMessage();
		try {
			message = skuServDu.createOperateGooodsDefine(jsession_id, json_info, oper_type);
		} catch (Exception e) {
			e.printStackTrace();
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("商品维护接口异常");
			return message;
		}

		return message;
	}

	/**
	 * UGC0006-商品查询
	 * @param jsession_id
	 * @param json_info goods_define表字段
	 * @param oper_type
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = UrlsMappings.QUERY_GOODS_DEFINE_LIST, method = RequestMethod.POST)
	@ResponseBody
	public UocMessage queryGoodsDefineList(String jsession_id, String json_info) {
		UocMessage message =new UocMessage();
		try {
			message = skuServDu.queryGoodsDefineList(jsession_id, json_info);
		} catch (Exception e) {
			e.printStackTrace();
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("商品查询接口异常");
			return message;
		}

		return message;
	}
	/**
	 * UGC0007-sku查询
	 * @param jsession_id
	 * @param json_info
	 * @param oper_type
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	@RequestMapping(value = UrlsMappings.QUERY_SKUDEFINE_SKUATTR, method = RequestMethod.POST)
	@ResponseBody
	public UocMessage querySkuDefineAndSkuAttr(String jsession_id, String json_info){
		UocMessage message =new UocMessage();
		try {
			message = skuServDu.querySkuDefineAndSkuAttr(jsession_id, json_info);
		} catch (Exception e) {
			e.printStackTrace();
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("商品查询接口异常");
			return message;
		}

		return message;
	}
	/**
	 * UGC0007-sku查询
	 * @param jsession_id
	 * @param json_info
	 * @param oper_type
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = UrlsMappings.QUERY_SKUDEFINE_SKUATTR_NEW, method = RequestMethod.POST)
	@ResponseBody
	public UocMessage querySkuDefineAndSkuAttr(String jsession_id,String oper_sku, String json_info){
		UocMessage message =new UocMessage();
		try {
			message = skuServDu.querySkuDefineAndSkuAttr(jsession_id, oper_sku, json_info);
		} catch (Exception e) {
			e.printStackTrace();
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("sku查询服务异常");
			return message;
		}
		return message;
	}

	/**
	 * UGC0008-sku维护
	 * @param jsession_id
	 * @param json_info
	 * @param oper_type
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	@RequestMapping(value = UrlsMappings.OPERATE_SKU, method = RequestMethod.POST)
	@ResponseBody
	public UocMessage operateSku(String jsession_id, String json_info,String oper_type){
		UocMessage message =new UocMessage();
		try {
			message = skuServDu.createOperateSku(jsession_id, json_info, oper_type);
		} catch (Exception e) {
			e.printStackTrace();
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("sku维护服务异常");
			return message;
		}

		return message;
	}

	/**
	 * UGC0008-sku维护
	 */
	@RequestMapping(value = UrlsMappings.OPERATE_SKU_NEW, method = RequestMethod.POST)
	@ResponseBody
	public UocMessage operateSku(String jsession_id, String json_info,String oper_sku,String oper_type){
		UocMessage respMessage =new UocMessage();
		try {
			respMessage = skuServDu.createOperateSku(jsession_id, json_info, oper_sku, oper_type);
		} catch (Exception e) {
			logger.warn("[UGC0008REST] 系统异常：{}",e);
			respMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			respMessage.setContent("sku维护接口异常");
			return respMessage;
		}
		return respMessage;
	}
}
