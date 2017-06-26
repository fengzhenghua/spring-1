package com.tydic.unicom.upc.service.pay.impl;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.SimpleFormatter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alipay.api.response.AlipayDataDataserviceBillDownloadurlQueryResponse;
import com.tydic.unicom.upc.alipay.model.ExtendParams;
import com.tydic.unicom.upc.alipay.model.GoodsDetail;
import com.tydic.unicom.upc.alipay.model.builder.AlipayTradeDownloadBillRequestBuilder;
import com.tydic.unicom.upc.alipay.model.builder.AlipayTradePayRequestBuilder;
import com.tydic.unicom.upc.alipay.model.builder.AlipayTradePrecreateRequestBuilder;
import com.tydic.unicom.upc.alipay.model.builder.AlipayTradeRefundRequestBuilder;
import com.tydic.unicom.upc.alipay.model.result.AlipayF2FDownloadBillResult;
import com.tydic.unicom.upc.alipay.model.result.AlipayF2FPayResult;
import com.tydic.unicom.upc.alipay.model.result.AlipayF2FPrecreateResult;
import com.tydic.unicom.upc.alipay.model.result.AlipayF2FRefundResult;
import com.tydic.unicom.upc.alipay.service.AlipayTradeService;
import com.tydic.unicom.upc.alipay.service.impl.AlipayTradeServiceImpl;
import com.tydic.unicom.upc.alipay.service.impl.AlipayTradeWithHBServiceImpl;
import com.tydic.unicom.upc.alipay.utils.Utils;
import com.tydic.unicom.upc.base.database.inst.interfaces.TransAliPayServ;
import com.tydic.unicom.upc.base.database.po.inst.TransAliPayPo;
import com.tydic.unicom.upc.service.activemq.interfaces.CustNotifyActivemqServDu;
import com.tydic.unicom.upc.service.code.interfaces.InfoPayParaAttrServDu;
import com.tydic.unicom.upc.service.inst.interfaces.InfoOrderServDu;
import com.tydic.unicom.upc.service.inst.interfaces.OrderPayTransServDu;
import com.tydic.unicom.upc.service.pay.interfaces.AliPayServDu;
import com.tydic.unicom.upc.vo.code.InfoPayParaAttrValueVo;
import com.tydic.unicom.upc.vo.inst.InfoOrderVo;
import com.tydic.unicom.upc.vo.inst.OrderPayTransVo;
import com.tydic.unicom.webUtil.UocMessage;

import net.sf.json.JSONObject;

public class AliPayServiceImpl implements AliPayServDu {

	private static Logger logger = Logger.getLogger(AliPayServiceImpl.class);
	
	@Autowired
	private InfoOrderServDu infoOrderServDu;
	
	@Autowired
	private InfoPayParaAttrServDu infoPayParaAttrServDu;
	
	@Autowired
	private OrderPayTransServDu orderPayTransServDu;
	
	@Autowired
	private CustNotifyActivemqServDu custNotifyActivemqServDu;
	
	@Autowired
	private TransAliPayServ transAliPayServ;
	
	@Override
	public void paymentScanPay(String appid, String privateKey, String publicKey, String outTradeNo, String order_id, String subject,
			String totalAmount, String authCode, String undiscountableAmount, String sellerId, String body,
			String operatorId, String storeId, ExtendParams extendParams, String timeoutExpress,
			List<GoodsDetail> goodsDetailList, String pay_type) {
		AlipayTradeService service = new AlipayTradeWithHBServiceImpl.ClientBuilder().build(appid, privateKey,publicKey);


        // 创建条码支付请求builder，设置请求参数
        AlipayTradePayRequestBuilder builder = new AlipayTradePayRequestBuilder()
            //            .setAppAuthToken(appAuthToken)
            .setOutTradeNo(outTradeNo).setSubject(subject).setAuthCode(authCode)
            .setTotalAmount(totalAmount).setStoreId(storeId)
            .setUndiscountableAmount(undiscountableAmount).setBody(body).setOperatorId(operatorId)
            .setExtendParams(extendParams).setSellerId(sellerId)
            .setGoodsDetailList(goodsDetailList).setTimeoutExpress(timeoutExpress);

        // 调用tradePay方法获取当面付应答
        AlipayF2FPayResult result = service.tradePay(builder);
        
        OrderPayTransVo orderPayTransVo = new OrderPayTransVo();
		orderPayTransVo.setPay_order_id(outTradeNo);
		orderPayTransVo = orderPayTransServDu.queryByPayOrderId(orderPayTransVo);
		if(orderPayTransVo == null){
			logger.error("找不到支付订单号为"+outTradeNo+"的数据");
			return;
		}
        
        InfoOrderVo infoOrderVo = new InfoOrderVo();
		infoOrderVo.setOrder_id(order_id);
		
		infoOrderVo = infoOrderServDu.queryInfoOrderByOrderId(infoOrderVo);
		if(infoOrderVo == null){
			logger.error("找不到订单号为"+outTradeNo+"的数据");
		}
		else{
			String trade_time =new SimpleDateFormat("yyyyMMddHHmmss").format(result.getResponse().getGmtPayment()); // 交易时间
	        switch (result.getTradeStatus()) {
	            case SUCCESS:
	            	infoOrderVo.setPay_notify_code("0");
					infoOrderVo.setPay_notify_trans_id(result.getResponse().getTradeNo());
					infoOrderVo.setOrder_status("A10");
					infoOrderVo.setTrade_time(trade_time);
	            	break;
	
	            case FAILED:
	            	infoOrderVo.setPay_notify_code("1");
					infoOrderVo.setPay_notify_msg(result.getResponse().getSubMsg());
					infoOrderVo.setOrder_status("A20");
					infoOrderVo.setTrade_time(trade_time);
	                break;
	
	            case UNKNOWN:
	            	infoOrderVo.setPay_notify_code("1");
					infoOrderVo.setPay_notify_msg(result.getResponse().getSubMsg());
					infoOrderVo.setOrder_status("A20");
					infoOrderVo.setTrade_time(trade_time);
	                break;
	
	            default:
	            	infoOrderVo.setPay_notify_code("1");
					infoOrderVo.setPay_notify_msg("交易返回异常");
					infoOrderVo.setOrder_status("A20");
					infoOrderVo.setTrade_time(trade_time);
	                break;
	        }
	        
	        infoOrderVo.setPay_order_id(outTradeNo);
	        infoOrderVo.setCust_notify_code("-");
	        infoOrderVo.setPay_type(pay_type);
			infoOrderServDu.updateInfoOrder(infoOrderVo);
			
			
			//更新OrderPayTrans， 用来提供给支付状态查询
			orderPayTransVo.setOrder_status(infoOrderVo.getOrder_status());
			orderPayTransVo.setPay_msg(infoOrderVo.getPay_notify_msg());
			orderPayTransServDu.updateOrderPayTrans(orderPayTransVo);
			
			//发起通知回调
			logger.info("发起消息队列"+order_id);
			UocMessage msg = new UocMessage();
			msg.addArg("order_id", order_id);
			int flag = custNotifyActivemqServDu.sendMessage(msg);
			if(flag == 0){
				logger.info("发起消息队列"+order_id+", 成功!");
			}
			else{
				logger.error("发起消息队列"+order_id+", 失败!"+flag);
			}
		}

	}

	@Override
	public Map<String, String> qrCodePay(String appid, String privateKey, String publicKey, String outTradeNo, String subject,
			String totalAmount, String notifyUrl, String undiscountableAmount, String sellerId, String body,
			String operatorId, String storeId, ExtendParams extendParams, String timeoutExpress,
			List<GoodsDetail> goodsDetailList) {
		
		Map<String, String> map = new HashMap<>();
		
		// 创建扫码支付请求builder，设置请求参数
        AlipayTradePrecreateRequestBuilder builder = new AlipayTradePrecreateRequestBuilder()
            .setSubject(subject).setTotalAmount(totalAmount).setOutTradeNo(outTradeNo)
            .setUndiscountableAmount(undiscountableAmount).setSellerId(sellerId).setBody(body)
            .setOperatorId(operatorId).setStoreId(storeId).setExtendParams(extendParams)
            .setTimeoutExpress(timeoutExpress)
            .setGoodsDetailList(goodsDetailList).setNotifyUrl(notifyUrl);

        AlipayTradeService   tradeService = new AlipayTradeServiceImpl.ClientBuilder().build(appid, privateKey,publicKey);
		
        AlipayF2FPrecreateResult result = tradeService.tradePrecreate(builder);
        
        switch (result.getTradeStatus()) {
            case SUCCESS:
                
                JSONObject json = JSONObject.fromObject(result.getResponse().getBody()).getJSONObject("alipay_trade_precreate_response");
                
                map.put("code", json.getString("code"));
                map.put("qr_code", json.getString("qr_code"));

                break;

            case FAILED:
            	JSONObject json2 = JSONObject.fromObject(result.getResponse().getBody()).getJSONObject("alipay_trade_precreate_response");
                
                map.put("code", json2.getString("code"));
                map.put("msg", json2.getString("msg"));
                break;

            case UNKNOWN:
            	map.put("code", "UNKNOWN");
                map.put("msg", "未能识别的返回错误");
                break;

            default:
            	map.put("code", "ERROR");
                map.put("msg", "交易返回状态异常");
                break;
        }
        
        return map;
		
	}

	@Override
	public Map<String, String> refund(String appid, String privateKey, String publicKey, String outTradeNo,
			String refundAmount, String outRequestNo, String refundReason, String storeId) {
		AlipayTradeService tradeService = new AlipayTradeServiceImpl.ClientBuilder().build(appid, privateKey,publicKey);

        // 创建退款请求builder，设置请求参数
        AlipayTradeRefundRequestBuilder builder = new AlipayTradeRefundRequestBuilder()
            .setOutTradeNo(outTradeNo).setRefundAmount(refundAmount).setRefundReason(refundReason)
            .setOutRequestNo(outRequestNo).setStoreId(storeId);

        AlipayF2FRefundResult result = tradeService.tradeRefund(builder);
        
        logger.info("支付宝退款时间为：" + result.getResponse().getGmtRefundPay());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Map<String, String> map = new HashMap<>();
        map.put("tradeStatus", result.getTradeStatus().toString());
        map.put("tradeNo", result.getResponse().getTradeNo());
        map.put("subMsg", result.getResponse().getSubMsg());
        map.put("trade_time", sdf.format(result.getResponse().getGmtRefundPay()));
        
        return map;
	}

	@Override
	public boolean qrPayNotify(Map<String, String> params) throws Exception{
		
		if(params.get("trade_status").equals("WAIT_BUYER_PAY")){
			return true;
		}
		String pay_order_id = params.get("out_trade_no");
		String date = params.get("gmt_payment");
		logger.info("-------------------------------------------------------");
		logger.info("交易时间为：date" + date);
		
		OrderPayTransVo orderPayTransVo = new OrderPayTransVo();
		orderPayTransVo.setPay_order_id(pay_order_id);
		orderPayTransVo = orderPayTransServDu.queryByPayOrderId(orderPayTransVo);
		if(orderPayTransVo == null){
			logger.error("找不到支付订单号为"+pay_order_id+"的数据");
			return false;
		}
        String order_id = orderPayTransVo.getOrder_id();
        //获取订单数据
		InfoOrderVo infoOrderVo = new InfoOrderVo();
		infoOrderVo.setOrder_id(order_id);
		infoOrderVo = infoOrderServDu.queryInfoOrderByOrderId(infoOrderVo);
		if(infoOrderVo == null){
			logger.error("找不到订单为"+pay_order_id+"的数据!");
			return false;
		}
		else{
			String busi_id = infoOrderVo.getBusi_id();
			
			InfoPayParaAttrValueVo attrValueVo = infoPayParaAttrServDu.getPayParaValueByPayType(busi_id, orderPayTransVo.getPay_type());
			if(attrValueVo == null){
				logger.error("找不到对应的支付类型参数配置数据!");
				return false;
			}
			if(!Utils.verify(params, attrValueVo.getPublicKey())){
				logger.error("签名验证失败");
				return false;
			}
			
			String trade_no = params.get("trade_no");
			infoOrderVo.setPay_notify_trans_id(trade_no);
			infoOrderVo.setPay_notify_time(new Date());
			infoOrderVo.setPay_type(orderPayTransVo.getPay_type());
			infoOrderVo.setPay_order_id(pay_order_id);
			
			String trade_status = params.get("trade_status");
			
		/*	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			Date trade_time = sdf.parse(params.get("gmt_payment"));*/
			logger.info("支付中心返回时间为" + params.get("gmt_payment"));
			String trade_time = new SimpleDateFormat("yyyyMMddHHmmss").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(params.get("gmt_payment")));
			logger.info("trade_time" + trade_time);
			if(trade_status.equals("TRADE_FINISHED")){
			
				infoOrderVo.setPay_notify_code("0");
				infoOrderVo.setOrder_status("A10");
				infoOrderVo.setTrade_time(trade_time);
			}
			else if (trade_status.equals("TRADE_SUCCESS")){
				infoOrderVo.setPay_notify_code("0");
				infoOrderVo.setOrder_status("A10");
				infoOrderVo.setTrade_time(trade_time);
			}else if(trade_status.equals("TRADE_CLOSED")){
				infoOrderVo.setPay_notify_code("1");
				infoOrderVo.setPay_notify_msg("订单已关闭");
				infoOrderVo.setOrder_status("A20");
				infoOrderVo.setTrade_time(trade_time);
			}else{
				infoOrderVo.setPay_notify_code("1");
				infoOrderVo.setPay_notify_msg(trade_status+"支付失败");
				infoOrderVo.setOrder_status("A20");
				infoOrderVo.setTrade_time(trade_time);
			}
			infoOrderVo.setCust_notify_code("-");
			infoOrderServDu.updateInfoOrder(infoOrderVo);
			
			//更新OrderPayTrans， 用来提供给支付状态查询
			orderPayTransVo.setOrder_status(infoOrderVo.getOrder_status());
			orderPayTransVo.setPay_msg(infoOrderVo.getPay_notify_msg());
			orderPayTransServDu.updateOrderPayTrans(orderPayTransVo);
			
			
			//发起通知回调
			logger.info("发起消息队列"+order_id);
			UocMessage msg = new UocMessage();
			msg.addArg("order_id", order_id);
			int result = custNotifyActivemqServDu.sendMessage(msg);
			if(result == 0){
				logger.info("发起消息队列"+order_id+", 成功!");
			}
			else{
				logger.error("发起消息队列"+order_id+", 失败!"+result);
			}
			
			return true;
		}
	}

	@Override
	public void downloadBill(final String appid, String privateKey, String publicKey, String billType, final String billDate) {
		AlipayTradeService tradeService = new AlipayTradeServiceImpl.ClientBuilder().build(appid, privateKey,publicKey);

        // 创建查询请求builder，设置请求参数
        AlipayTradeDownloadBillRequestBuilder builder = new AlipayTradeDownloadBillRequestBuilder()
        		.setBill_type(billType)
        		.setBill_date(billDate);

        AlipayF2FDownloadBillResult result = tradeService.downloadBill(builder);
        switch (result.getTradeStatus()) {
            case SUCCESS:
            	logger.info("对帐单下载地址获取成功");

            	AlipayDataDataserviceBillDownloadurlQueryResponse response = result.getResponse();
                
                downloadBillFile(appid, billDate, response.getBillDownloadUrl());
                break;

            case FAILED:
            	logger.error("查询返回该订单支付失败或被关闭!!!");
                break;

            case UNKNOWN:
            	logger.error("系统异常，订单支付状态未知!!!");
                break;

            default:
            	logger.error("不支持的交易状态，交易返回异常!!!");
                break;
        }
		
	}
	
	
	private void downloadBillFile(String appid, String billDate, String downloadUrl){
        try {
        	CloseableHttpClient httpclient = HttpClients.createDefault();
        	
	        // 创建httpget.    
	        HttpGet httpget = new HttpGet(downloadUrl);  
	        // 执行get请求.    
	        CloseableHttpResponse response = httpclient.execute(httpget);  
	        // 获取响应实体    
	        HttpEntity result = response.getEntity();
	        
			String path = "./";
			File pathFile = new File(path);
			if(!pathFile.exists()){
				pathFile.mkdirs();
			}
			
			String filename = billDate + "-"+ appid + ".zip";
			String fullpath = path + "/" + filename;
			File file = new File(fullpath);
			BufferedInputStream bis = new BufferedInputStream(result.getContent());
			FileOutputStream fos = new FileOutputStream(file);
			byte[] buf = new byte[8096];
			int size = 0;
			while ((size = bis.read(buf)) != -1)
				fos.write(buf, 0, size);
			fos.close();
			bis.close();
			
			response.close();
			httpclient.close();
			
			//下载完后解压文件
			unzipFile(appid, billDate, fullpath);
			
			//删除文件
			file.delete();
        	
        } catch (Exception e) {  
           logger.error("下载对帐文件异常:"+e.getMessage(), e);
        }
    }
	
	private void unzipFile(String appid, String billDate, String filename) throws IOException{
		ZipFile zf = new ZipFile(filename, Charset.forName("GBK"));  
		FileInputStream in = new FileInputStream(filename);
        ZipInputStream zin = new ZipInputStream(in, Charset.forName("GBK"));
        ZipEntry entry;  
        while((entry = zin.getNextEntry())!=null && !entry.isDirectory()){  
            
        	if(entry.getName().lastIndexOf("业务明细.csv") != -1){
        		long size = entry.getSize();  
                if (size > 0) {  
                	StringBuilder builder = new StringBuilder();
                    BufferedReader br = new BufferedReader(new InputStreamReader(zf.getInputStream(entry), "GBK"));
                    String line;  
                    while ((line = br.readLine()) != null) {  
                        builder.append(line + "\n");
                    }  
                    br.close();  
                    
                    String dataStrs[] = builder.toString().replace("\t", "").split("\n");
                    
                    List<TransAliPayPo> poList = new ArrayList<>();
                    
                    for(int i=5; i<dataStrs.length-4; i++){
                    	
                    	logger.info(dataStrs[i]);
                    	
                    	String strs[] = dataStrs[i].split(",");
                    	
                    	TransAliPayPo po = new TransAliPayPo();
                    	po.setBill_date(billDate.replace("-", ""));
        				po.setBill_check_flag("0");
        				po.setAppid(appid);
        				
        				po.setTrade_no(strs[0]);
        				po.setOut_trade_no(strs[1]);
        				po.setBusi_type(strs[2]);
        				po.setSubject(strs[3]);
        				po.setTrans_time(strs[4]);
        				po.setFinish_time(strs[5]);
        				po.setStore_id(strs[6]);
        				po.setStore_name(strs[7]);
        				po.setOperator_id(strs[8]);
        				po.setTerminal_id(strs[9]);
        				po.setBuyer_logon_id(strs[10]);
        				po.setTotal_amount(strs[11]);
        				po.setReceipt_amount(strs[12]);
        				po.setAlipay_amount(strs[13]);
        				po.setPoint_amount(strs[14]);
        				po.setAlipay_discount_amount(strs[15]);
        				po.setMch_discount_amount(strs[16]);
        				po.setVoucher_amount(strs[17]);
        				po.setVoucher_name(strs[18]);
        				po.setMch_red_amount(strs[19]);
        				po.setCard_amount(strs[20]);
        				po.setRefund_trade_no(strs[21]);
        				po.setMer_fee(strs[22]);
        				po.setFen_amount(strs[23]);
        				
        				if(strs.length > 24){
        					po.setRemark(strs[24]);
        				}
        				
        				//交易状态 + 创建时间+支付宝帐号+商户流水号+支付宝流水号
        				String trans_time = po.getTrans_time().replaceAll("[^0-9]", "");
        				String busi_type = po.getBusi_type().equals("退款") ? "REFUND" : "PAY";
        				po.setBill_trans_id(busi_type + trans_time + appid + po.getOut_trade_no() + po.getTrade_no() );
        				
        				poList.add(po);
                    }
                    
                    transAliPayServ.addTrans(poList);
                    
                    logger.info("支付宝对帐单下载，appid="+appid+", billDate="+billDate+", 下载成功!");
                }  
        	}
            
        }
        zin.closeEntry();
        in.close();
        zf.close();
    }

}
