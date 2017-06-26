package com.tydic.unicom.upc.service.activemq.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.tydic.unicom.upc.service.activemq.interfaces.CustNotifyActivemqDealServDu;
import com.tydic.unicom.upc.service.activemq.interfaces.CustNotifyActivemqServDu;
import com.tydic.unicom.upc.service.capacity.impl.SendMessageToCapacityDu;
import com.tydic.unicom.upc.service.code.interfaces.InfoBusiPayRelationServDu;
import com.tydic.unicom.upc.service.code.interfaces.InfoBusiServDu;
import com.tydic.unicom.upc.service.inst.interfaces.InfoOrderServDu;
import com.tydic.unicom.upc.vo.code.InfoBusiPayRelationVo;
import com.tydic.unicom.upc.vo.code.InfoBusiVo;
import com.tydic.unicom.upc.vo.inst.InfoOrderVo;
import com.tydic.unicom.util.HttpUtil;
import com.tydic.unicom.util.RsaEncodeUtil;
import com.tydic.unicom.util.UpcEncodeUtil;
import com.tydic.unicom.util.UpcEncodeUtilForJson;
import com.tydic.unicom.webUtil.UocMessage;


/**
 * 接收处理消息
 * @author 吴川
 *
 */
public class CustNotifyActivemqDealServDuImpl implements CustNotifyActivemqDealServDu {

	private static final Logger logger = Logger.getLogger(CustNotifyActivemqDealServDuImpl.class);
	
	@Autowired
	private InfoBusiServDu infoBusiServDu;
	
	@Autowired
	private InfoOrderServDu infoOrderServDu;
	
	@Autowired
	private CustNotifyActivemqServDu custNotifyActivemqServDu;
	
	@Autowired
	private SendMessageToCapacityDu sendMessageToCapacityDu;
	
	@Override
	public void receiveMessage(Object message){
		UocMessage msg = (UocMessage)message;
		
		String order_id = ""+msg.getArgs().get("order_id");
		
		logger.info("收到消息, order_id="+order_id);
		
		int retry_times = 0;
		if(msg.getArgs().containsKey("retry_times")){
			retry_times = (int)msg.getArgs().get("retry_times");
		}
		
		if(retry_times > 3){
			return;
		}
		
		//获取订单数据
		InfoOrderVo infoOrderVo = new InfoOrderVo();
		infoOrderVo.setOrder_id(order_id);
		infoOrderVo = infoOrderServDu.queryInfoOrderByOrderId(infoOrderVo);
		if(infoOrderVo == null){
			logger.error("找不到"+order_id+"对应的支付订单号数据!");
			return;
		}
		
		try{
			
			//有可能是spring事务还没提交， 就接收到了消息，所以在这里加一个判断
			if(infoOrderVo.getPay_type() == null || infoOrderVo.getPay_type().equals("")){
				
				msg.addArg("retry_times", ++retry_times);
				custNotifyActivemqServDu.sendMessage(msg);
				return;
			}
			
			
			String busi_id = infoOrderVo.getBusi_id();
			
			
			//获取业务参数配置
			InfoBusiVo infoBusiVo = new InfoBusiVo();
			infoBusiVo.setBusi_id(busi_id);
			
			infoBusiVo = infoBusiServDu.queryByBusiId(infoBusiVo);
			
			if(infoBusiVo == null){
				throw new Exception("发起通知失败，不存在busi_id="+busi_id+"的数据");
			}
			
			String notifyUrl =  infoBusiVo.getNotify_url();
			if(notifyUrl == null || notifyUrl.equals("")){
				throw new Exception("通知地址未配置，无法发起");
			}
			
			String result_code = "0".equals(infoOrderVo.getPay_notify_code()) ? "SUCCESS" : "FAIL";
			//对通知内容进行加密和签名
			//通知的格式依然是 <xml><busi_id>BUSI_ID</busi_id><content>CONTENT</content></xml>
			Map<String, Object> contentMap = new HashMap<>();
			contentMap.put("result_code", result_code);
			contentMap.put("result_msg", infoOrderVo.getPay_notify_msg());
			contentMap.put("req_type", infoOrderVo.getOrder_type());
			contentMap.put("req_way", infoOrderVo.getReq_way());
			contentMap.put("out_order_id", infoOrderVo.getOut_order_id());
			contentMap.put("pay_type", infoOrderVo.getPay_type());
			contentMap.put("total_fee", infoOrderVo.getTotal_fee());
			contentMap.put("real_fee", infoOrderVo.getReal_fee());
			contentMap.put("remark", infoOrderVo.getOut_remark());
			
			//如果成功，返回支付流水号
			/*if("0".equals(infoOrderVo.getPay_notify_code())){
				contentMap.put("transactions_id", infoOrderVo.getPay_notify_trans_id());
			}*/
			contentMap.put("transactions_id", infoOrderVo.getOrder_id());
			contentMap.put("trade_time", infoOrderVo.getTrade_time());

			String paramStr = RsaEncodeUtil.getJsonFromMap(contentMap);
			String content = "";
			if("1".equals(infoBusiVo.getEncrypt())){
				content = UpcEncodeUtilForJson.privateEncode(paramStr, infoBusiVo.getRsa_private_key(), infoBusiVo.getSign_key());
			}else{
				content = paramStr;
				//加密
				//String xmldata = "<xml><busi_id>BUSI_ID</busi_id><content>CONTENT</content></xml>";
				
				//xmldata = xmldata.replace("BUSI_ID", infoBusiVo.getBusi_id()).replace("CONTENT",content);
			}
			if("0".equals(infoBusiVo.getEncrypt())){
				// 调交易
				sendMessageToCapacityDu.sendMessage(content, busi_id);
			}else{
				//content = UpcEncodeUtilForJson.transcodeUtf(content);
				JSONObject json = new JSONObject();
				json.put("busi_id",infoBusiVo.getBusi_id());
				json.put("content", content);
				notifyUrl = notifyUrl.trim();
				logger.info("发起通知回调，地址："+notifyUrl);
				Map<String, String> postParams = new HashMap<>();
				postParams.put("data", json.toString());
				
				String result = HttpUtil.sendPost(notifyUrl, postParams);
				
				logger.info("post result :\n"+result);
			}

			infoOrderVo.setCust_notify_code("0");
			infoOrderVo.setCust_notify_msg("SUCCESS");
			infoOrderVo.setCust_notify_time(new Date());
		}catch(Exception e){
			logger.error("发起支付回调通知失败!"+e.getMessage(), e);
			
			infoOrderVo.setCust_notify_code("1");
			infoOrderVo.setCust_notify_msg(e.getMessage());
			infoOrderVo.setCust_notify_time(new Date());
		}
		
		infoOrderServDu.updateInfoOrder(infoOrderVo);
		
		logger.info(order_id+"发起支付回调通知处理完成");
	}

}
