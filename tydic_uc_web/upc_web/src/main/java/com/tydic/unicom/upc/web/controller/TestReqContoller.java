package com.tydic.unicom.upc.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tydic.unicom.upc.service.code.interfaces.InfoBusiServDu;
import com.tydic.unicom.upc.vo.code.InfoBusiVo;
import com.tydic.unicom.upc.vo.inst.PaySettleTransVo;
import com.tydic.unicom.upc.web.constants.ControllerMappings;
import com.tydic.unicom.upc.web.vo.BusiReqDataVo;
import com.tydic.unicom.util.RsaEncodeUtil;
import com.tydic.unicom.util.UpcEncodeUtil;
import com.tydic.unicom.util.UpcEncodeUtilForJson;
import com.tydic.unicom.webUtil.Message;
import com.tydic.unicom.webUtil.Message.Type;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 用来测试请求的发起
 * @author 吴川
 *
 */
@Controller
@RequestMapping(value = ControllerMappings.TEST_REQ_CONTROLLER)
public class TestReqContoller {
	
	private static Logger logger = Logger.getLogger(TestReqContoller.class);
	
	@Autowired
	private InfoBusiServDu infoBusiServDu;

	@RequestMapping(value = "testPayReq" , method = RequestMethod.POST)
	@ResponseBody
	public Message testPayReq(){
		String busi_id = "10000000";
		Message message = new Message();
		try {
			InfoBusiVo infoBusiVo = new InfoBusiVo();
			infoBusiVo.setBusi_id(busi_id);;
			infoBusiVo = infoBusiServDu.queryByBusiId(infoBusiVo);
			if(infoBusiVo != null){
				
				//必传参数校验
				String order_type = "01";
				String req_way = "PC";
				String out_order_id = System.currentTimeMillis()+"";
				float real_fee = 0.01f;
				String detail_name = "这只是一个支付测试";
				String redirect_url = "";
				String remark = "请返回给我原值"; 
				
				
				JSONArray jsonArray = new JSONArray();
				JSONObject goodsJson1 = new JSONObject();
				goodsJson1.put("goods_id", "11111111");
				goodsJson1.put("goods_name", "测试商品1");
				goodsJson1.put("goods_num", 1);
				goodsJson1.put("goods_price", 0.01);
				
				jsonArray.add(goodsJson1);
				
	/*			JSONObject goodsJson2 = new JSONObject();
				goodsJson2.put("goods_id", "22222222");
				goodsJson2.put("goods_name", "测试商品2");
				goodsJson2.put("goods_num", 1);
				goodsJson2.put("goods_price", 0.01);
				
				jsonArray.add(goodsJson2);*/
				
				Map<String, Object> contentMap = new HashMap<>();
				//contentMap.put("busi_id", busi_id);
				contentMap.put("order_type", order_type);
				contentMap.put("req_way", req_way);
				contentMap.put("out_order_id", out_order_id);
				contentMap.put("real_fee", real_fee);
				contentMap.put("detail_name", detail_name);
				contentMap.put("redirect_url", redirect_url);
				contentMap.put("remark", remark);
				contentMap.put("goods_detail", jsonArray.toString());
				
				String paramStr =RsaEncodeUtil.getJsonFromMap(contentMap);
				
				String content = "";
				if("1".equals(infoBusiVo.getEncrypt())){// 需要加密
					 content = UpcEncodeUtilForJson.publicEncode(paramStr, infoBusiVo.getRsa_public_key(), infoBusiVo.getSign_key());
				}else{//不需要加密
					content = paramStr;
				}
				//String content = UpcEncodeUtil.publicEncode(paramStr, infoBusiVo.getRsa_public_key(), infoBusiVo.getSign_key());
/*				String xmldata = "<xml><busi_id>BUSI_ID</busi_id><content>CONTENT</content></xml>";
				
				xmldata = xmldata.replace("BUSI_ID", busi_id).replace("CONTENT",content);*/
				JSONObject json = new JSONObject();
				json.put("busi_id", busi_id);
				json.put("content", content);
				message.setType(Message.Type.success);
				//message.addArg("data", UpcEncodeUtilForJson.transcodeUtf(json.toString()));
				//message.addArg("busi_id",busi_id);
				message.addArg("data", json.toString());
				return message;
			}
			else{
				message.setType(Message.Type.error);
				message.setContent("测试支付数据失败");
				return message;
			}
		} catch (Exception e) {
			logger.error("测试支付数据异常!"+e.getMessage(), e);
			message.setType(Message.Type.error);
			message.setContent("程序异常");
			return message;
		}
	}
	
	
	/**
	 * 测试接收通知内容
	 * @return
	 */
	@RequestMapping(value = "testPayNotify" , method = RequestMethod.POST)
	@ResponseBody
	public void testPayNotify(HttpServletRequest request,HttpServletResponse response){
		try{
			String xmldata = request.getParameter("data");
			//String busi_id = request.getParameter("busi_id");
			logger.info("收到的请求返回内容为:"+xmldata);
			
			BusiReqDataVo busiReqDataVo = validReqData(xmldata);
			if(busiReqDataVo.isSign()){
				//返回结果
				String result = "SUCCESS";
				String msg = "OK";
				
				/*String responseStr = "<xml>"
				  +"<return_code><![CDATA["+result+"]]></return_code>"
				  +"<return_msg><![CDATA["+msg+"]]></return_msg>"
				  +"</xml>";*/
				
				String responseStr = "{\"return_code\":'"+result+"',\"return_msg\":'"+ msg +"'}";
				
				response.getOutputStream().write(responseStr.getBytes());
			}
			else{
				logger.error("签名校验失败!");
			}
			
			
		}catch(Exception e){
			
		}
	}
	
	
	@RequestMapping(value = "testRefund" , method = RequestMethod.POST)
	@ResponseBody
	public Message testRefund(String order_id, String total_fee, String real_fee){
		String busi_id = "10000000";
		Message message = new Message();
		try {
			InfoBusiVo infoBusiVo = new InfoBusiVo();
			infoBusiVo.setBusi_id(busi_id);;
			infoBusiVo = infoBusiServDu.queryByBusiId(infoBusiVo);
			if(infoBusiVo != null){
				
				//必传参数校验
				String order_type = "02";
				String req_way = "APP";
				String out_order_id = System.currentTimeMillis()+"";
				String remark = "请返回给我原值";
				
				
				Map<String, Object> contentMap = new HashMap<>();
				contentMap.put("busi_id", busi_id);
				contentMap.put("order_type", order_type);
				contentMap.put("req_way", req_way);
				contentMap.put("out_order_id", out_order_id);
				contentMap.put("out_refund_id", order_id);
				contentMap.put("total_fee", total_fee);
				contentMap.put("real_fee", real_fee);
				contentMap.put("remark", remark);
				
				String paramStr = RsaEncodeUtil.getJsonFromMap(contentMap);
				
				String content = "";
				if("1".equals(infoBusiVo.getEncrypt())){// 需要加密
					 content = UpcEncodeUtilForJson.publicEncode(paramStr, infoBusiVo.getRsa_public_key(), infoBusiVo.getSign_key());
					 //content = UpcEncodeUtilForJson.transcodeUtf(content);
				}else{//不需要加密
					content = UpcEncodeUtilForJson.transcodeUtf(paramStr);
				}
				/*
				String xmldata = "<xml><busi_id>BUSI_ID</busi_id><content>CONTENT</content></xml>";
				
				xmldata = xmldata.replace("BUSI_ID", busi_id).replace("CONTENT",content);*/
				
				String xmldata = "{'busi_id':'BUSI_ID', 'content':'CONTENT'}";
				xmldata = xmldata.replace("BUSI_ID", busi_id).replace("CONTENT",content);
				message.setType(Message.Type.success);
				message.addArg("data", xmldata );
				message.addArg("busi_id",busi_id);
				return message;
				
			}
			else{
				message.setType(Message.Type.error);
				message.setContent("测试退款数据失败");
				return message;
			}
		}catch(Exception e){
			logger.error("测试退款数据异常!"+e.getMessage(), e);
			message.setType(Message.Type.error);
			message.setContent("程序异常");
			return message;
		}
	}
	
	@RequestMapping(value = "testPayQuery" , method = RequestMethod.POST)
	@ResponseBody
	public Message testPayQuery(String order_id){
		String busi_id = "10000000";
		Message message = new Message();
		try {
			InfoBusiVo infoBusiVo = new InfoBusiVo();
			infoBusiVo.setBusi_id(busi_id);;
			infoBusiVo = infoBusiServDu.queryByBusiId(infoBusiVo);
			if(infoBusiVo != null){
				
				//必传参数校验
				String order_type = "03";
				String req_way = "APP";
				String out_order_id = System.currentTimeMillis()+"";
				
				
				Map<String, Object> contentMap = new HashMap<>();
				contentMap.put("order_type", order_type);
				contentMap.put("req_way", req_way);
				contentMap.put("out_order_id", out_order_id);
				contentMap.put("ori_order_id", order_id);
				
				String paramStr = RsaEncodeUtil.getJsonFromMap(contentMap);

				String content = "";
				if("1".equals(infoBusiVo.getEncrypt())){// 需要加密
					 content = UpcEncodeUtilForJson.publicEncode(paramStr, infoBusiVo.getRsa_public_key(), infoBusiVo.getSign_key());
/*					
*/				}else{//不需要加密
					content = paramStr;
				}
				// content = UpcEncodeUtilForJson.transcodeUtf(content);
				JSONObject json = new JSONObject();
				json.put("busi_id", busi_id);
				json.put("content", content);
			//	String data = json.toString();
				message.setType(Message.Type.success);
				message.addArg("data", json.toString());
				return message;
				
			}
			else{
				message.setType(Message.Type.error);
				message.setContent("测试查询数据失败");
				return message;
			}
		}catch(Exception e){
			logger.error("测试查询数据异常!"+e.getMessage(), e);
			message.setType(Message.Type.error);
			message.setContent("程序异常");
			return message;
		}
	}
	
	
	@RequestMapping(value = "dealPayQuery" , method = RequestMethod.POST)
	@ResponseBody
	public Message dealPayQuery(String data){
		Message message = new Message();
		try{
			
			logger.info("收到的查询请求返回内容为:"+ data);
			
			BusiReqDataVo busiReqDataVo = validReqData(data);
			if(busiReqDataVo.isSign()){
				
				message.addArg("data", busiReqDataVo.getContentMap());
				
				
				message.setType(Type.success);
				return message;
			}
			else{
				logger.error("签名校验失败!");
				message.setType(Message.Type.error);
				message.setContent("签名校验失败!");
				return message;
			}
			
			
		}catch(Exception e){
			logger.error("处理查询数据异常!"+e.getMessage(), e);
			message.setType(Message.Type.error);
			message.setContent("处理查询数据异常!"+e.getMessage());
			return message;
		}
	}
	
	@RequestMapping(value = "dealrefund" , method = RequestMethod.POST)
	@ResponseBody
	public Message dealrefund(String data){
		Message message = new Message();
		try{
			
			logger.info("收到的查询请求返回内容为:"+ data);
			
			BusiReqDataVo busiReqDataVo = validReqData(data);
			if(busiReqDataVo.isSign()){
				//message.addArg("data", busiReqDataVo.getContentMap());
				
				Map<String,Object> contentMap = busiReqDataVo.getContentMap();
				String trade_time = (String)contentMap.get("trade_time");
				String transactions_id = (String)contentMap.get("transactions_id");
				logger.info("transactions_id = " + transactions_id);
				logger.info("trade_time="+ trade_time);
				message.setType(Type.success);
				return message;
			}
			else{
				logger.error("签名校验失败!");
				message.setType(Message.Type.error);
				message.setContent("签名校验失败!");
				return message;
			}
			
			
		}catch(Exception e){
			logger.error("处理查询数据异常!"+e.getMessage(), e);
			message.setType(Message.Type.error);
			message.setContent("处理查询数据异常!"+e.getMessage());
			return message;
		}
	}
	@RequestMapping(value = "testDownloadBill" , method = RequestMethod.POST)
	@ResponseBody
	public Message testDownloadBill(String date){
		Message message = new Message();
		String busi_id = "10000000";
		try {
			InfoBusiVo infoBusiVo = new InfoBusiVo();
			infoBusiVo.setBusi_id(busi_id);;
			infoBusiVo = infoBusiServDu.queryByBusiId(infoBusiVo);
			if(infoBusiVo != null){
				
				//必传参数校验
				String order_type = "05";
				String req_way = "APP";
				String out_order_id = System.currentTimeMillis()+"";
				String remark = "返回已经对账的账单";
				
				Map<String, Object> contentMap = new HashMap<>();
				contentMap.put("order_type", order_type);
				contentMap.put("req_way", req_way);
				contentMap.put("out_order_id", out_order_id);
				contentMap.put("bill_date", date);
				contentMap.put("remark", remark);
				String paramStr = RsaEncodeUtil.getJsonFromMap(contentMap);

				String content = "";
				if("1".equals(infoBusiVo.getEncrypt())){// 需要加密
					 content = UpcEncodeUtilForJson.publicEncode(paramStr, infoBusiVo.getRsa_public_key(), infoBusiVo.getSign_key());
/*					
*/				}else{//不需要加密
					content = paramStr;
				}
				JSONObject json = new JSONObject();
				json.put("busi_id", busi_id);
				json.put("content", content);
			message.setType(Type.success);
			message.setContent("账单请求封装成功");
			message.addArg("data", json.toString());
			}
		}catch(Exception e){
				logger.info("账单下载查询异常");
				e.printStackTrace();
				message.setType(Type.error);
				message.setContent(e.getMessage());
			}
		return message;
	}
	
	@RequestMapping(value = "dealBill" , method = RequestMethod.POST)
	@ResponseBody
	public Message dealDownBill(String data){
		Message message = new Message();
		if(data == null || "".equals(data)){
			message.setType(Type.success);
			message.setContent("账单下载成功");
		}else{
			JSONArray array = JSONArray.fromObject(data);
			List<PaySettleTransVo> bill = new ArrayList<PaySettleTransVo>();
			bill = JSONArray.toList(array, PaySettleTransVo.class);
			for(PaySettleTransVo vo :bill){
				System.out.println(vo.getOrder_id());
			}
			message.setType(Type.success);
			message.setContent("账单下载成功");
		}
		return message;
	}
	/**
	 * 请求数据安全性校验
	 * @param model
	 * @param map
	 * @return
	 */
	private BusiReqDataVo validReqData(String data) throws Exception{
		try{
			//Map<String, Object> map = RsaEncodeUtil.getMapFromXML(xmldata);
			if(data == null || "".equals(data)){
				throw new IllegalArgumentException("找不到data的值");
			}
			JSONObject json = JSONObject.fromObject(data);
			if(json.getString("busi_id") == null || json.getString("busi_id").toString().equals("")){
				throw new IllegalArgumentException("找不到busi_id参数");
			}
			String busi_id = json.getString("busi_id").toString();
			
			if(json.getString("content") == null || json.getString("content").toString().equals("")){
				throw new IllegalArgumentException("找不到content参数");
			}
			
			String content = json.getString("content").toString();
			
			InfoBusiVo infoBusiVo = new InfoBusiVo();
			infoBusiVo.setBusi_id(busi_id);
			infoBusiVo = infoBusiServDu.queryByBusiId(infoBusiVo);
			String rsa_public_key = infoBusiVo.getRsa_public_key();
			String sign_key = infoBusiVo.getSign_key();
			String decodeStr = "";
			boolean signResult = false;
			if(!infoBusiVo.getBusi_id().equals(busi_id)){
				throw new Exception("busi_id="+busi_id+"，系统未找到相应的业务配置数据");
			}
			if("1".equals(infoBusiVo.getEncrypt())){
				decodeStr = UpcEncodeUtilForJson.publicDecode(content, rsa_public_key);
				 signResult = UpcEncodeUtilForJson.signValid(decodeStr, sign_key);
				
			}else{
				decodeStr = content;
				signResult = true;
			}

			logger.info("收到通知回调的内容：busi_id="+busi_id+", content="+decodeStr);
			
			BusiReqDataVo busiReqDataVo = new BusiReqDataVo();
			busiReqDataVo.setSign(signResult);
			busiReqDataVo.setBusiId(busi_id);
			busiReqDataVo.setContentMap(RsaEncodeUtil.getMapFromJson(decodeStr));
			
			return busiReqDataVo;
			
		}catch(Exception e){
			logger.error("请求数据校验失败!"+e.getMessage(), e);
			throw new Exception("请求数据校验失败!"+e.getMessage());
		}
	}

}
