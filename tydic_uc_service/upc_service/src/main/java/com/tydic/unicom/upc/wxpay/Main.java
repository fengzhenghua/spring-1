package com.tydic.unicom.upc.wxpay;


import com.tydic.unicom.upc.wxpay.business.DownloadBillBusiness.ResultListener;
import com.tydic.unicom.upc.wxpay.business.RefundBusiness;
import com.tydic.unicom.upc.wxpay.business.ScanPayBusiness;
import com.tydic.unicom.upc.wxpay.common.Configure;
import com.tydic.unicom.upc.wxpay.common.Signature;
import com.tydic.unicom.upc.wxpay.common.Util;
import com.tydic.unicom.upc.wxpay.protocol.downloadbill_protocol.DownloadBillReqData;
import com.tydic.unicom.upc.wxpay.protocol.downloadbill_protocol.DownloadBillResData;
import com.tydic.unicom.upc.wxpay.protocol.pay_protocol.ScanPayReqData;
import com.tydic.unicom.upc.wxpay.protocol.pay_protocol.ScanPayResData;
import com.tydic.unicom.upc.wxpay.protocol.pay_query_protocol.ScanPayQueryReqData;
import com.tydic.unicom.upc.wxpay.protocol.pay_query_protocol.ScanPayQueryResData;
import com.tydic.unicom.upc.wxpay.protocol.refund_protocol.RefundReqData;
import com.tydic.unicom.upc.wxpay.protocol.refund_protocol.RefundResData;
import com.tydic.unicom.upc.wxpay.protocol.refund_query_protocol.RefundQueryReqData;
import com.tydic.unicom.upc.wxpay.protocol.refund_query_protocol.RefundQueryResData;
import com.tydic.unicom.upc.wxpay.protocol.unified_order_protocol.UnifiedOrderReqData;
import com.tydic.unicom.upc.wxpay.protocol.unified_order_protocol.UnifiedOrderResData;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Main {

    public static void main(String[] args) {

        try {

            //--------------------------------------------------------------------
            //温馨提示，第一次使用该SDK时请到com.tencent.common.Configure类里面进行配置
            //--------------------------------------------------------------------



            //--------------------------------------------------------------------
            //PART One:基础组件测试
            //--------------------------------------------------------------------

            //1）https请求可用性测试
            //HTTPSPostRquestWithCert.test();

            //2）测试项目用到的XStream组件，本项目利用这个组件将Java对象转换成XML数据Post给API
            //XStreamTest.test();


            //--------------------------------------------------------------------
            //PART Two:基础服务测试
            //--------------------------------------------------------------------

            //1）测试被扫支付API
            //PayServiceTest.test();

            //2）测试被扫订单查询API
            //PayQueryServiceTest.test();

            //3）测试撤销API
            //温馨提示，测试支付API成功扣到钱之后，可以通过调用PayQueryServiceTest.test()，将支付成功返回的transaction_id和out_trade_no数据贴进去，完成撤销工作，把钱退回来 ^_^v
            //ReverseServiceTest.test();

            //4）测试退款申请API
            //RefundServiceTest.test();

            //5）测试退款查询API
            //RefundQueryServiceTest.test();

            //6）测试对账单API
            //DownloadBillServiceTest.test();


            //本地通过xml进行API数据模拟的时候，先按需手动修改xml各个节点的值，然后通过以下方法对这个新的xml数据进行签名得到一串合法的签名，最后把这串签名放到这个xml里面的sign字段里，这样进行模拟的时候就可以通过签名验证了
           // Util.log(Signature.getSignFromResponseString(Util.getLocalXMLString("/test/com/tencent/business/refundqueryserviceresponsedata/refundquerysuccess2.xml")));

            //Util.log(new Date().getTime());
            //Util.log(System.currentTimeMillis());
        	
        	
        	
        	testScanPay();
        	
        	//testUnifiedOrder();
        	
        	//testScanQuery();
        	
        	//testRefund();
        	
        	//testRefundQuery();
        	
        	//testDownload();
        	
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void testScanPay() throws Exception{
    	
    	String appid="wx581fd45b644746c1";
    	String mchid = "1249429501";
    	String key = "p2nj1c7q9rp4qyu1t11plw4ah0a9izsf";
    	
    	//条码测试
    	String authCode = "130143583182291544";
    	String body = "这只是一个支付测试";
    	String attach = "";
    	String outTradeNo = "";
    	int totalFee = 1;
    	String deviceInfo = "88888888";
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    	//A755FB0FFD19CECE4263B7909DE97EA7
    	//29DFE7B9CDCCEF843700503CB26D8B41
    	
    	
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(new Date());
    	cal.add(Calendar.MINUTE, 1);		//默认三分钟失效
    	
    	String timeStart = sdf.format(new Date());
    	String timeExpire = sdf.format(cal.getTime());
    	
    	outTradeNo = "18365610004710605170";
    	timeStart = "20170213183656";
    	timeExpire = "20170213183956";
    	
    	//A755FB0FFD19CECE4263B7909DE97EA7
    	//29DFE7B9CDCCEF843700503CB26D8B41
    	
    	ScanPayReqData scanPayReqData = new ScanPayReqData(appid, mchid, key, authCode, body,attach,outTradeNo,totalFee,deviceInfo, Configure.getIP(),timeStart, timeExpire, "");
    	WXPay.doScanPayBusiness(scanPayReqData,new ScanPayBusiness.ResultListener() {
			
			@Override
			public void onSuccess(ScanPayResData scanPayResData) {
				System.out.println("支付成功!"+scanPayResData.getTransaction_id());
			}
			
			@Override
			public void onFailBySignInvalid(ScanPayResData scanPayResData) {
				System.out.println("支付失败："+scanPayResData.getReturn_msg());
			}
			
			@Override
			public void onFailByReturnCodeFail(ScanPayResData scanPayResData) {
				System.out.println("支付失败："+scanPayResData.getReturn_msg());
			}
			
			@Override
			public void onFailByReturnCodeError(ScanPayResData scanPayResData) {
				System.out.println("支付失败："+scanPayResData.getReturn_msg());
			}
			
			@Override
			public void onFailByMoneyNotEnough(ScanPayResData scanPayResData) {
				System.out.println("支付失败："+scanPayResData.getReturn_msg());
			}
			
			@Override
			public void onFailByAuthCodeInvalid(ScanPayResData scanPayResData) {
				System.out.println("支付失败："+scanPayResData.getErr_code_des());
			}
			
			@Override
			public void onFailByAuthCodeExpire(ScanPayResData scanPayResData) {
				System.out.println("支付失败："+scanPayResData.getErr_code_des());
			}
			
			@Override
			public void onFail(ScanPayResData scanPayResData) {
				System.out.println("支付失败："+scanPayResData.getErr_code_des());
			}
		});
    }
    
    public static void testScanQuery() throws Exception{
    	//测试订单查询
    	String appid="wx581fd45b644746c1";
    	String mchid = "1249429501";
    	String key = "p2nj1c7q9rp4qyu1t11plw4ah0a9izsf";
    	
    	String outTradeNo = "11111120170110145219";
    	String transactionID = "";
    	
    	ScanPayQueryReqData scanPayQueryReqData = new ScanPayQueryReqData(appid, mchid, key, transactionID, outTradeNo);
    	
    	ScanPayQueryResData scanPayQueryResData = WXPay.requestScanPayQueryService(scanPayQueryReqData);
    }
    
    public static void testRefund() throws Exception{
    	
    	//测试退款
    	String appid="wx581fd45b644746c1";
    	String mchid = "1249429501";
    	String key = "p2nj1c7q9rp4qyu1t11plw4ah0a9izsf";
    	
    	String transactionID = "";
    	String outTradeNo = "11111120170110145219";
    	String deviceInfo = "";
    	String outRefundNo = "RN"+System.currentTimeMillis();
    	int totalFee = 2;
    	int refundFee = 1;
    	String opUserID = mchid;
    	String refundFeeType = Configure.getFeeType();
    	
    	String certPath = "/wxcert/apiclient_cert.p12";
    	String certPassword = mchid;
    	
    	
    	RefundReqData refundReqData = new RefundReqData(appid, mchid, key, transactionID, outTradeNo, deviceInfo, outRefundNo, totalFee, refundFee, opUserID, refundFeeType);
    	
    	RefundResData refundResData = WXPay.requestRefundService(refundReqData, certPath, certPassword);
    }
    
    public static void testRefundQuery() throws Exception{
    	
    	//测试退款
    	String appid="wx581fd45b644746c1";
    	String mchid = "1249429501";
    	String key = "p2nj1c7q9rp4qyu1t11plw4ah0a9izsf";
    	
    	String transactionID = "";
    	String outTradeNo = "11111120170110145219";
    	String deviceInfo = "";
    	String outRefundNo = "";
    	
    	String refundID = "";
    	
    	
    	RefundQueryReqData refundQueryReqData = new RefundQueryReqData(appid, mchid, key, transactionID, outTradeNo, deviceInfo, outRefundNo, refundID);
    	
    	RefundQueryResData RefundQueryResData = WXPay.requestRefundQueryService(refundQueryReqData);
    }
    
    public static void testUnifiedOrder() throws Exception{
    	String appid="wx581fd45b644746c1";
    	String mchid = "1249429501";
    	String key = "p2nj1c7q9rp4qyu1t11plw4ah0a9izsf";
    	
    	//扫码测试
    	String body = "测试商品";
    	String attach = "";
    	String outTradeNo = "";
    	int totalFee = 1;
    	String deviceInfo = "123456";
    	
    	String trade_type = "NATIVE";
    	String openid = "";
    	String notifyUrl = "test";
    	
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    	
    	String timeStart = sdf.format(new Date());
    	
    	outTradeNo = "111111"+timeStart;
    	
    	String productId = "PID"+outTradeNo;
    	int expireTime = 3;
    	
    	UnifiedOrderReqData unifiedOrderReqData = new UnifiedOrderReqData(appid, mchid, key, deviceInfo, body, outTradeNo, totalFee, Configure.getIP(), productId, expireTime, trade_type, openid, notifyUrl);
    	
    	UnifiedOrderResData unifiedOrderResData = WXPay.requestUnifiedOrderService(unifiedOrderReqData);
    }
    
    
    public static void testDownload() throws Exception{
    	String appid="wx581fd45b644746c1";
    	String mchid = "1249429501";
    	String key = "p2nj1c7q9rp4qyu1t11plw4ah0a9izsf";
    	
    	String deviceInfo = "";
    	String billDate = "20170109";
    	String billType = "ALL";
    	
    	
    	DownloadBillReqData downloadBillReqData = new DownloadBillReqData(appid, mchid, key, deviceInfo, billDate, billType);
    	WXPay.doDownloadBillBusiness(downloadBillReqData, new ResultListener() {
			
			@Override
			public void onFailByReturnCodeFail(DownloadBillResData downloadBillResData) {
				System.out.println("对帐单下载失败："+downloadBillResData.getReturn_msg());
			}
			
			@Override
			public void onFailByReturnCodeError(DownloadBillResData downloadBillResData) {
				System.out.println("对帐单下载失败："+downloadBillResData.getReturn_msg());
			}
			
			@Override
			public void onDownloadBillSuccess(String response) {
				System.out.println("对帐单下载成功："+response);
			}
			
			@Override
			public void onDownloadBillFail(String response) {
				System.out.println("对帐单下载失败："+response);
			}
		});
    }
}
