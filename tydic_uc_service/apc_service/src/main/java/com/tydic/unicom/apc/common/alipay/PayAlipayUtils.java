package com.tydic.unicom.apc.common.alipay;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import sun.misc.BASE64Encoder;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeCancelRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeCancelResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.tydic.unicom.apc.common.utils.MatrixToImageWriter;
import com.tydic.unicom.apc.common.utils.ParaTool;
import com.tydic.unicom.apc.common.utils.readPropertiesUtils;

public class PayAlipayUtils {
	
	private static Logger logger = Logger.getLogger(PayAlipayUtils.class);
    
	public static String Call_Alipay_Service_phone(Map<String,String> map) throws Exception {
		System.out.println("进入Call_Alipay_Service_phone---------------");
		Map<String,String> sParaTemp = new HashMap<String,String>();
		System.out.println("--------------读取配置"+readPropertiesUtils.getPropertiesForUrl("alipay_gateway_batch"));
		//支付宝提供的新的开发平台网关
		String ALIPAY_GATEWAY_NEW = getAlipayPropertiesValue("alipay_gateway_batch");
		// 把请求参数打包成数组
		sParaTemp.put("service", getAlipayPropertiesValue("service_phone"));
		String mchtFlag = map.get("mcht_flag");
		//必传参数
        sParaTemp.put("partner",  getAlipayPropertiesValue("partner", mchtFlag));
        //必传参数
        sParaTemp.put("seller_id", getAlipayPropertiesValue("partner", mchtFlag));//seller_id就是partner 以2088开头的16位数字
        //必传参数
        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
        //必传参数
        sParaTemp.put("payment_type", "1");//手机网页支付目前只支持1
		//不必传参数，但是我们的功能需要这个参数来响应支付宝通知，必须传
        sParaTemp.put("notify_url", getAlipayPropertiesValue("notify_url_phone"));
        //不必传参数，但是我们的功能需要这个参数，必须传
        sParaTemp.put("return_url", getAlipayPropertiesValue("return_url_phone"));
        //必传参数
        sParaTemp.put("out_trade_no", map.get("out_trade_no"));
        //必传参数
        sParaTemp.put("subject", map.get("subject"));
        //必传参数
        sParaTemp.put("total_fee", map.get("total_fee"));
        if(map.containsKey("show_url") && map.get("show_url").length() > 0){
	        //不必传参数
	        sParaTemp.put("show_url", map.get("show_url"));
        }
        if(map.containsKey("body") && map.get("body").length() > 0){
        	//不必传参数
        	sParaTemp.put("body", map.get("body"));
        }
        if(map.containsKey("it_b_pay") && map.get("it_b_pay").length() > 0){
        	//不必传参数 设置未付款交易的超时时间，一旦超时，该笔交易就会自动被关闭。1m-15d
        	sParaTemp.put("it_b_pay", map.get("it_b_pay"));
        }
		// 建立请求
		String sHtmlText = AlipaySubmit.buildRequest(ALIPAY_GATEWAY_NEW, sParaTemp, "get", "确认");
		return sHtmlText;
	}

	public static String getAlipayPropertiesValue(String key, String flag) {
		if(flag != null && !"".equals(flag)){
			key += "_"+flag;
        }
		return readPropertiesUtils.getPropertiesForUrl(key);
	}
	
	public static String getAlipayPropertiesValue(String key) {
		return getAlipayPropertiesValue(key, null);
	}
	
	public static Map<String, Object> getQrcode(Map<String,String> map) throws Exception{
		logger.info("新增打印_________11111111");
		Map<String, Object> result = new HashMap<String, Object>();
		StringBuilder sb = new StringBuilder();
		sb.append("{\"out_trade_no\":\"" + map.get("out_trade_no") + "\",");
		sb.append("\"total_amount\":\""+map.get("total_amount")+ "\",");
		//sb.append("\"subject\":\""+subject+"\",");
		sb.append("\"subject\":\""+map.get("subject")+"\"}");
		logger.info("新增打印_________222222222");
		logger.info(sb.toString());
		
		String mchtFlag = map.get("mcht_flag");
		String app_id_scan = getAlipayPropertiesValue("app_id_scan", mchtFlag);
		String ali_public_key_scan = getAlipayPropertiesValue("ali_public_key_scan", mchtFlag);
		String private_key = getAlipayPropertiesValue("private_key", mchtFlag);
		AlipayTradePrecreateResponse response = null;
		logger.info("新增打印_________333333333");
		try{
		//不同窗体appid不同,支付宝公钥不同，在上传RSA公钥的时候，任何窗体上传同一个！
		AlipayClient alipayClient = getAlipayClient(app_id_scan,ali_public_key_scan,private_key);
		logger.info("新增打印_________"+alipayClient.toString());
		
		
		// 使用SDK，构建群发请求模型
		AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
		request.setBizContent(sb.toString());
		//map中包含isPc字段，且为true,则取notify_url_scan_pc
		if(map.containsKey("isPc") && "true".equals(map.get("isPc"))){
		    	if( StringUtils.isEmpty(getAlipayPropertiesValue("notify_url_scan_pc")) ){
		    	request.setNotifyUrl("notify_url_scan_pc=http://121.31.40.19:8090/uss_web/noauthority/verifyCode/alipayScanNotify");
		    	}
		    	else{
		    	request.setNotifyUrl(getAlipayPropertiesValue("notify_url_scan_pc"));
		    	}
		    	result.put("notify_url_scan_pc", request.getNotifyUrl());
		    	System.out.println("notify_url_scan_pc"+request.getNotifyUrl());
		    	System.out.println("notify_url_scan_pc"+request.getNotifyUrl());
		}else{
			request.setNotifyUrl(getAlipayPropertiesValue("notify_url_scan"));
		}
		logger.info("新增打印_________444444444");
//		request.putOtherTextParam("ws_service_url", "http://unitradeprod.t15032aqcn.alipay.net:8080");
			// 使用SDK，调用交易下单接口
		response = alipayClient
				.execute(request);
		result.put("response_Body", response.getBody());
		result.put("response_Msg", response.getMsg());
//		System.out.println(response.getBody());
//		System.out.println(response.isSuccess());
//		System.out.println(response.getMsg());
		logger.info("新增打印_________"+response.getBody());
		logger.info("新增打印_________"+response.getMsg());
		}catch(Exception e){
			logger.info("新增打印_________");
			e.printStackTrace();
		}
		if (null != response && response.isSuccess()) {
			if (response.getCode().equals("10000")) {
				System.out.println("商户订单号："+response.getOutTradeNo());
				System.out.println("二维码值："+response.getQrCode());//商户将此二维码值生成二维码，然后展示给用户，用户用支付宝手机钱包扫码完成支付
				//二维码的生成，网上有许多开源方法，可以参看：http://blog.csdn.net/feiyu84/article/details/9089497
				int width = 300;
				int height = 300;
				// 二维码的图片格式
				String format = "JPG";
				Hashtable hints = new Hashtable();
				// 内容所使用编码
				hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
				BitMatrix bitMatrix = null;
				bitMatrix = new MultiFormatWriter().encode(response.getQrCode(),
						BarcodeFormat.QR_CODE, width, height, hints);
				// 生成二维码
//				 File outputFile = new File("d:"+File.separator+"new.gif");
//				 MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);

				BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);
				InputStream is=ParaTool.BufferedImage2InputStream(image);
				byte[] mybyte = ParaTool.input2byte(is);
				BASE64Encoder encoder = new BASE64Encoder();
				String content = encoder.encode(mybyte);
				System.out.println(content);
				
				result.put("msg", true);
				result.put("content", content);
				return result;
			} else {
			//打印错误码
			System.out.println("错误码："+response.getSubCode());
			System.out.println("错误描述："+response.getSubMsg());
			result.put("msg", false);
			result.put("content", response.getBody());
			return result;
			}
		}
		
		result.put("msg", false);
		result.put("content", response.getBody());
		return result;
	}
	
	public static AlipayClient getAlipayClient(String appid,String ali_public_key, String private_key){
		return new DefaultAlipayClient(getAlipayPropertiesValue("alipay_gateway_new"), appid, private_key,
				 "json", getAlipayPropertiesValue("input_charset"),ali_public_key);
	}
	
	/**
	 * 交易查询
	 * 
	 * @param out_trade_no
	 * @return
	 * @author jinlong.rhj
	 * @date 2015年5月5日
	 * @version 1.0
	 */
	
	public static String searchAlipayStatus(final String out_trade_no, String mchtFlag) {
		
		String app_id_scan = getAlipayPropertiesValue("app_id_scan", mchtFlag);
		String ali_public_key_scan = getAlipayPropertiesValue("ali_public_key_scan", mchtFlag);
		String private_key = getAlipayPropertiesValue("private_key", mchtFlag);
		
		AlipayClient alipayClient = getAlipayClient(app_id_scan,ali_public_key_scan,private_key);
		AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
		String biz_content = "{\"out_trade_no\":\"" + out_trade_no + "\"}";
		request.setBizContent(biz_content);
		AlipayTradeQueryResponse response = null;
		try {
			response = alipayClient.execute(request);
			System.out.println( response.getBody());
			System.out.println( response.getCode());
			
			
			if (null != response && response.isSuccess()) {
				System.out.println("买家账号：" + response.getBuyerLogonId());
				System.out.println("商户订单号：" + response.getOutTradeNo());
				System.out.println("支付宝交易号：" + response.getTradeNo());
				System.out.println("总金额：" + response.getTotalAmount());
				System.out.println("订单状态：" + response.getTradeStatus());
			}
		} catch (AlipayApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response.getBody();
	}

	
	public static boolean verify(Map<String, String> params) throws Exception {

		return AlipayNotify.verify(params);
	
	}

	public static boolean verifyScan(Map<String, String> params) throws Exception {

		return AlipayNotify.verifyScan(params);
	
	}
	
	public static String cancelOrder(String out_trade_no, String mchtFlag) {
		String app_id_scan = getAlipayPropertiesValue("app_id_scan", mchtFlag);
		String ali_public_key_scan = getAlipayPropertiesValue("ali_public_key_scan", mchtFlag);
		String private_key = getAlipayPropertiesValue("private_key", mchtFlag);
		AlipayClient alipayClient = getAlipayClient(app_id_scan,ali_public_key_scan,private_key);
		AlipayTradeCancelRequest request = new AlipayTradeCancelRequest();
		String biz_content = "{\"out_trade_no\":\"" + out_trade_no + "\"}";
		request.setBizContent(biz_content);
		AlipayTradeCancelResponse response = null;
		try {
			response = alipayClient.execute(request);
			if (null != response && response.isSuccess()) {
				if (response.getCode().equals("10000")) {
					return "cancel_success";
				} 
//				else {
//					if (response.getRetryFlag().equals("Y")) {
//						// 如果重试标识为Y，表示支付宝撤销失败，需要轮询重新发起撤销
//						cancelOrderRetry(out_trade_no);
//					}
//				}
			}
		} catch (AlipayApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	
	}
	
	/**
	 * 
	 * @param trade_no
	 * @author jinlong.rhj
	 * @date 2015年4月27日
	 * @version 1.0
	 * @return
	 */
	
	public static String refundOrder(String trade_no,String refund_amount, String out_request_no, String mchtFlag) {
		
		String app_id_scan = getAlipayPropertiesValue("app_id_scan", mchtFlag);
		String ali_public_key_scan = getAlipayPropertiesValue("ali_public_key_scan", mchtFlag);
		String private_key = getAlipayPropertiesValue("private_key", mchtFlag);
		
		AlipayClient alipayClient = getAlipayClient(app_id_scan,ali_public_key_scan,private_key);
		AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();

		String biz_content = "{\"trade_no\":\""
				+ trade_no
				+ "\",\"refund_amount\":\""
				+ refund_amount
//				+ "\",\"out_request_no\":\""
//				+ out_request_no
				+ "\"}";
		System.out.println(biz_content);
		request.setBizContent(biz_content);

		AlipayTradeRefundResponse response = null;

		try {
			response = alipayClient.execute(request);
			if (null != response && response.isSuccess()) {
				if (response.getCode().equals("10000")) {
					if (response.getFundChange().equals("Y")) {
						// 退款成功,资金有变动,做业务及账务处理
						return "refund_success";
					}else {
						//资金无变动，不必做账务处理
						return null;
					}					
				} 
//				else {
//					// 没有撤销成功，需要重试几次
//					refundOrderRetry(trade_no, refund_amount, out_request_no, 6);
//				}
			}
		} catch (AlipayApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	
	
	/**
	 * 轮询发起撤销重试
	 * 
	 * @param out_trade_no
	 * @author jinlong.rhj
	 * @date 2015年4月28日
	 * @version 1.0
	 */
	/*
	public String cancelOrderRetry(final String out_trade_no) {
		final AlipayClient alipayClient = getAlipayClient(getAlipayPropertiesValue("app_id_scan"),getAlipayPropertiesValue("ali_public_key_scan"));
		final AlipayTradeCancelRequest request = new AlipayTradeCancelRequest();
		String biz_content = "{\"out_trade_no\":\"" + out_trade_no + "\"}";
		request.setBizContent(biz_content);
		String cancelStatus = null;
		// 子线程异步方式，每个10秒钟重试一次，重试5次,加上重试前的1次，总共6次1分钟
		new Thread(new Runnable() {
			int i = 0;
			int n = 5;

			
			public void run() {
				// TODO Auto-generated method stub

				while (++i <= n) {
					try {
						Thread.sleep(10000);
						System.out.println("重试撤销请求 第 " + i + " 次");
						AlipayTradeCancelResponse response = alipayClient
								.execute(request);
						if (null != response && response.isSuccess()) {
							if (response.getCode().equals("10000")
									&& response.getBody().contains(
											"\"retry_flag\":\"N\"")) {
								cancelStatus = "cancel_success";
								break;
							}
						}

						if (i == n) {
							// 处理到最后一次，还是未撤销成功，需要在商户数据库中对此单最标记，人工介入处理

						}

					} catch (AlipayApiException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		}).start();

	}*/
	
	/**
	 * 同一个out_request_no代表是对同一次退款进行重试处理，不同的out_request_no表示再次发起了退款请求，（部分退款时请谨慎）
	 * 
	 * @param trade_no
	 * @param refund_amount
	 * @param out_request_no
	 * @param times
	 * @author jinlong.rhj
	 * @date 2015年5月4日
	 * @version 1.0
	 */
	/*
	public static void refundOrderRetry(String trade_no, String refund_amount,
			String out_request_no, int retryTimes) {
		AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClient();
		AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
		String biz_content = "{\"trade_no\":\""
				+ trade_no
				+ "\",\"refund_amount\":\""
				+ refund_amount
				+ "\",\"out_request_no\":\""
				+ out_request_no
				+ "\",\"refund_reason\":\"reason\",\"store_id\":\"store001\",\"terminal_id\":\"terminal001\"}";
		request.setBizContent(biz_content);

		// 如果有界面等待重试退款的处理结果，建议做异步处理，不要在主线程中等待处理结果,不然主线程可能会无响应或等待超时。
		for (int i = 1; i <= retryTimes; i++) {
			try {
				Thread.sleep(5000);

				System.out.println("重试退款请求 第 " + i + " 次");
				
				AlipayTradeRefundResponse response = alipayClient
						.execute(request);
				if (null != response && response.isSuccess()) {
					if (response.getCode().equals("10000")) {
						
						if (response.getFundChange().equals("Y")) {
							// 退款成功,资金有变动,做业务及账务处理
						}
						
						break;
					}
				}

				if (i == retryTimes) {
					// 处理到最后一次，还是未退款成功，需要在商户数据库中对此单此次退款最标记，人工介入处理

				}

			} catch (AlipayApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}*/
}
