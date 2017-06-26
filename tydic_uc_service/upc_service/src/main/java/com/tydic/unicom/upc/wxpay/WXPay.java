package com.tydic.unicom.upc.wxpay;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import org.apache.log4j.Logger;

import com.tydic.unicom.upc.wxpay.business.DownloadBillBusiness;
import com.tydic.unicom.upc.wxpay.business.RefundBusiness;
import com.tydic.unicom.upc.wxpay.business.RefundQueryBusiness;
import com.tydic.unicom.upc.wxpay.business.ScanPayBusiness;
import com.tydic.unicom.upc.wxpay.common.Configure;
import com.tydic.unicom.upc.wxpay.common.Signature;
import com.tydic.unicom.upc.wxpay.common.Util;
import com.tydic.unicom.upc.wxpay.protocol.downloadbill_protocol.DownloadBillReqData;
import com.tydic.unicom.upc.wxpay.protocol.pay_protocol.ScanPayReqData;
import com.tydic.unicom.upc.wxpay.protocol.pay_protocol.ScanPayResData;
import com.tydic.unicom.upc.wxpay.protocol.pay_query_protocol.ScanPayQueryReqData;
import com.tydic.unicom.upc.wxpay.protocol.pay_query_protocol.ScanPayQueryResData;
import com.tydic.unicom.upc.wxpay.protocol.refund_protocol.RefundReqData;
import com.tydic.unicom.upc.wxpay.protocol.refund_protocol.RefundResData;
import com.tydic.unicom.upc.wxpay.protocol.refund_query_protocol.RefundQueryReqData;
import com.tydic.unicom.upc.wxpay.protocol.refund_query_protocol.RefundQueryResData;
import com.tydic.unicom.upc.wxpay.protocol.reverse_protocol.ReverseReqData;
import com.tydic.unicom.upc.wxpay.protocol.reverse_protocol.ReverseResData;
import com.tydic.unicom.upc.wxpay.protocol.unified_order_protocol.UnifiedOrderReqData;
import com.tydic.unicom.upc.wxpay.protocol.unified_order_protocol.UnifiedOrderResData;
import com.tydic.unicom.upc.wxpay.service.DownloadBillService;
import com.tydic.unicom.upc.wxpay.service.RefundQueryService;
import com.tydic.unicom.upc.wxpay.service.RefundService;
import com.tydic.unicom.upc.wxpay.service.ReverseService;
import com.tydic.unicom.upc.wxpay.service.ScanPayQueryService;
import com.tydic.unicom.upc.wxpay.service.ScanPayService;
import com.tydic.unicom.upc.wxpay.service.UnifiedOrderService;

public class WXPay {
	
	private static Logger logger = Logger.getLogger(WXPay.class);
	
	static{
		Configure.setIp(getIpAddress());
	}
    
    private static String getIpAddress(){
    	try{
			Enumeration allNetInterfaces = NetworkInterface.getNetworkInterfaces();
			InetAddress ip = null;
			while (allNetInterfaces.hasMoreElements()) {
				NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
				Enumeration addresses = netInterface.getInetAddresses();
				while (addresses.hasMoreElements()) {
					ip = (InetAddress) addresses.nextElement();
					if (ip != null && ip instanceof Inet4Address) {
						return ip.getHostAddress();
					}
				}
			}
    	}catch(Exception e){
    		
    	}
    	return "127.0.0.1";
		
      }

    /**
     * 请求支付服务
     * @param scanPayReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @return API返回的数据
     * @throws Exception
     */
    public static ScanPayResData requestScanPayService(ScanPayReqData scanPayReqData) throws Exception{
        String resultString = new ScanPayService().request(scanPayReqData);
        logger.info("---------请求返回结果----------");
		logger.info(resultString);
			
		
		//转成结果对象
		ScanPayResData resData = (ScanPayResData) Util.getObjectFromXML(resultString, ScanPayResData.class);
		//判断返回情况
		if(resData == null || resData.getReturn_code() == null){
			throw new Exception("获取不到返回信息");
		}
		else if(resData.getReturn_code().equals("SUCCESS")){
			if(resData.getResult_code().equals("SUCCESS")){
				
				if(!Signature.checkIsSignValidFromResponseString(resultString, scanPayReqData.getKey())){
					throw new Exception("签名验证失败");
				}
				
    		    return resData;
    		}
    		else{
    			throw new Exception(resData.getErr_code_des());
    		}
		}
		else{
			throw new Exception(resData.getReturn_msg());
    	}
    }

    /**
     * 请求支付查询服务
     * @param scanPayQueryReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @return API返回的XML数据
     * @throws Exception
     */
	public static ScanPayQueryResData requestScanPayQueryService(ScanPayQueryReqData scanPayQueryReqData) throws Exception{
		String resultString = new ScanPayQueryService().request(scanPayQueryReqData);
		
		logger.info("---------请求返回结果----------");
		logger.info(resultString);
			
		
		//转成结果对象
		ScanPayQueryResData resData = (ScanPayQueryResData) Util.getObjectFromXML(resultString, ScanPayQueryResData.class);
		//判断返回情况
		if(resData == null || resData.getReturn_code() == null){
			throw new Exception("获取不到返回信息");
		}
		else if(resData.getReturn_code().equals("SUCCESS")){
			if(resData.getResult_code().equals("SUCCESS")){
				
				if(!Signature.checkIsSignValidFromResponseString(resultString, scanPayQueryReqData.getKey())){
					throw new Exception("签名验证失败");
				}
				
    		    return resData;
    		}
    		else{
    			throw new Exception(resData.getErr_code_des());
    		}
		}
		else{
			throw new Exception(resData.getReturn_msg());
    	}
	}
	
	/**
     * 请求统一下单服务
     * @param scanPayReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @return API返回的数据
     * @throws Exception
     */
    public static UnifiedOrderResData requestUnifiedOrderService(UnifiedOrderReqData unifiedOrderReqData) throws Exception{
        String resultString = new UnifiedOrderService().request(unifiedOrderReqData);
        logger.info("---------请求返回结果----------");
		logger.info(resultString);
			
		
		//转成结果对象
		UnifiedOrderResData resData = (UnifiedOrderResData) Util.getObjectFromXML(resultString, UnifiedOrderResData.class);
		//判断返回情况
		if(resData == null || resData.getReturn_code() == null){
			throw new Exception("获取不到返回信息");
		}
		else if(resData.getReturn_code().equals("SUCCESS")){
			if(resData.getResult_code().equals("SUCCESS")){
				
				if(!Signature.checkIsSignValidFromResponseString(resultString, unifiedOrderReqData.getKey())){
					throw new Exception("签名验证失败");
				}
				
    		    return resData;
    		}
    		else{
    			throw new Exception(resData.getErr_code_des());
    		}
		}
		else{
			throw new Exception(resData.getReturn_msg());
    	}
    }

    /**
     * 请求退款服务
     * @param refundReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @return API返回的XML数据
     * @throws Exception
     */
    public static RefundResData requestRefundService(RefundReqData refundReqData, String certPath, String certPassword) throws Exception{
        String resultString = new RefundService(certPath, certPassword).request(refundReqData);
		
		logger.info("---------请求返回结果----------");
		logger.info(resultString);
			
		
		//转成结果对象
		RefundResData resData = (RefundResData) Util.getObjectFromXML(resultString, RefundResData.class);
		//判断返回情况
		if(resData == null || resData.getReturn_code() == null){
			throw new Exception("获取不到返回信息");
		}
		else if(resData.getReturn_code().equals("SUCCESS")){
			if(resData.getResult_code().equals("SUCCESS")){
				
				if(!Signature.checkIsSignValidFromResponseString(resultString, refundReqData.getKey())){
					throw new Exception("签名验证失败");
				}
				
    		    return resData;
    		}
    		else{
    			throw new Exception(resData.getErr_code_des());
    		}
		}
		else{
			throw new Exception(resData.getReturn_msg());
    	}
    }

    /**
     * 请求退款查询服务
     * @param refundQueryReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @return API返回的XML数据
     * @throws Exception
     */
	public static RefundQueryResData requestRefundQueryService(RefundQueryReqData refundQueryReqData) throws Exception{
		String resultString = new RefundQueryService().request(refundQueryReqData);
		
		logger.info("---------请求返回结果----------");
		logger.info(resultString);
			
		
		//转成结果对象
		RefundQueryResData resData = (RefundQueryResData) Util.getObjectFromXML(resultString, RefundQueryResData.class);
		//判断返回情况
		if(resData == null || resData.getReturn_code() == null){
			throw new Exception("获取不到返回信息");
		}
		else if(resData.getReturn_code().equals("SUCCESS")){
			if(resData.getResult_code().equals("SUCCESS")){
				
				if(!Signature.checkIsSignValidFromResponseString(resultString, refundQueryReqData.getKey())){
					throw new Exception("签名验证失败");
				}
				
    		    return resData;
    		}
    		else{
    			throw new Exception(resData.getErr_code_des());
    		}
		}
		else{
			throw new Exception(resData.getReturn_msg());
    	}
	}

    /**
     * 请求撤销服务
     * @param reverseReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @return API返回的XML数据
     * @throws Exception
     */
	public static ReverseResData requestReverseService(ReverseReqData reverseReqData) throws Exception{
		String resultString = new ReverseService().request(reverseReqData);
		
		logger.info("---------请求返回结果----------");
		logger.info(resultString);
			
		
		//转成结果对象
		ReverseResData resData = (ReverseResData) Util.getObjectFromXML(resultString, ReverseResData.class);
		//判断返回情况
		if(resData == null || resData.getReturn_code() == null){
			throw new Exception("获取不到返回信息");
		}
		else if(resData.getReturn_code().equals("SUCCESS")){
			if(resData.getResult_code().equals("SUCCESS")){
				
				if(!Signature.checkIsSignValidFromResponseString(resultString, reverseReqData.getKey())){
					throw new Exception("签名验证失败");
				}
				
    		    return resData;
    		}
    		else{
    			throw new Exception(resData.getErr_code_des());
    		}
		}
		else{
			throw new Exception(resData.getReturn_msg());
    	}
	}

    /**
     * 请求对账单下载服务
     * @param downloadBillReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @return API返回的XML数据
     * @throws Exception
     */
    public static String requestDownloadBillService(DownloadBillReqData downloadBillReqData) throws Exception{
        return new DownloadBillService().request(downloadBillReqData);
    }

    /**
     * 直接执行被扫支付业务逻辑（包含最佳实践流程）
     * @param scanPayReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @param resultListener 商户需要自己监听被扫支付业务逻辑可能触发的各种分支事件，并做好合理的响应处理
     * @throws Exception
     */
    public static void doScanPayBusiness(ScanPayReqData scanPayReqData, ScanPayBusiness.ResultListener resultListener) throws Exception {
        new ScanPayBusiness().run(scanPayReqData, resultListener);
    }

    /**
     * 调用退款业务逻辑
     * @param refundReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @param resultListener 业务逻辑可能走到的结果分支，需要商户处理
     * @throws Exception
     */
//    public static void doRefundBusiness(RefundReqData refundReqData, RefundBusiness.ResultListener resultListener) throws Exception {
//        new RefundBusiness().run(refundReqData,resultListener);
//    }

    /**
     * 运行退款查询的业务逻辑
     * @param refundQueryReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @param resultListener 商户需要自己监听被扫支付业务逻辑可能触发的各种分支事件，并做好合理的响应处理
     * @throws Exception
     */
    public static void doRefundQueryBusiness(RefundQueryReqData refundQueryReqData,RefundQueryBusiness.ResultListener resultListener) throws Exception {
        new RefundQueryBusiness().run(refundQueryReqData,resultListener);
    }

    /**
     * 请求对账单下载服务
     * @param downloadBillReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @param resultListener 商户需要自己监听被扫支付业务逻辑可能触发的各种分支事件，并做好合理的响应处理
     * @return API返回的XML数据
     * @throws Exception
     */
    public static void doDownloadBillBusiness(DownloadBillReqData downloadBillReqData,DownloadBillBusiness.ResultListener resultListener) throws Exception {
        new DownloadBillBusiness().run(downloadBillReqData,resultListener);
    }


}
