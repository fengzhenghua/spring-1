package com.tydic.unicom.upc.alipay;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;

import com.alipay.api.domain.TradeFundBill;
import com.alipay.api.response.AlipayDataDataserviceBillDownloadurlQueryResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.tydic.unicom.upc.alipay.model.ExtendParams;
import com.tydic.unicom.upc.alipay.model.GoodsDetail;
import com.tydic.unicom.upc.alipay.model.builder.AlipayTradeDownloadBillRequestBuilder;
import com.tydic.unicom.upc.alipay.model.builder.AlipayTradePayRequestBuilder;
import com.tydic.unicom.upc.alipay.model.builder.AlipayTradePrecreateRequestBuilder;
import com.tydic.unicom.upc.alipay.model.builder.AlipayTradeQueryRequestBuilder;
import com.tydic.unicom.upc.alipay.model.builder.AlipayTradeRefundRequestBuilder;
import com.tydic.unicom.upc.alipay.model.result.AlipayF2FDownloadBillResult;
import com.tydic.unicom.upc.alipay.model.result.AlipayF2FPayResult;
import com.tydic.unicom.upc.alipay.model.result.AlipayF2FPrecreateResult;
import com.tydic.unicom.upc.alipay.model.result.AlipayF2FQueryResult;
import com.tydic.unicom.upc.alipay.model.result.AlipayF2FRefundResult;
import com.tydic.unicom.upc.alipay.service.AlipayTradeService;
import com.tydic.unicom.upc.alipay.service.impl.AlipayTradeServiceImpl;
import com.tydic.unicom.upc.alipay.service.impl.AlipayTradeWithHBServiceImpl;
import com.tydic.unicom.upc.alipay.utils.Utils;

import net.sf.json.JSONObject;

public class MainTest {
	
	private static Logger logger = Logger.getLogger(MainTest.class);

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//Configs.init("zfbinfo.properties");
		
		String appid = "2015111700819307";
		String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALx9uDvWYPB1K7mDQaC5b2U459gtd/M1eaMJl1gYbDzM0PrArHPxesD+hpxE2/L8xp9bCZiN5M4wNewxxVRbl/eJlviw6WZ71Ue1EF+ljtE7nqU32XPsmaeRaT8qYT4ocdsp0tBqgmAk1GOg0ALYvGnbc06hYm98egguKTumeMZRAgMBAAECgYAGLYiwTWJ3/x8jwW2ROwKvqqHOsA7SZdpKwbG4LXS+uWfQECiW1D9VvAHldUxQNoC8EFvMKDprl6Ds0OTK0wW0BNQRSArbRHd2K3mfbnzkkJO/gcVeA1nlyxsTPCykgdlddvlmNNj7G8UJM74Ih0Dqr9rkO8+Dvhey4EsN0k9wUQJBAPEraILg/V5QLHcI2dDgkTsejVZmLUPQJXqNGLB/W0bV2JNpa/MR4SiqulnAomD5a83YSO//Lq4flOn3PQvLieMCQQDIFQZk1unpO2B+aWGWMPwh1WqmO4Z0W7m/Ch0H9dPOUT8XQx0qyLHnpX5HoL60nCWHdVnAMjQ4QJd/jjoILDU7AkEAkTscwsQOkD8jp8MyuDCkBMAPSRpa491y1GwnlBH9u5iHxa3UjhoUR1MYyXfR0SginWFD+xLm34CxQnvazUGTFQJAdI2S4PE057G4H0uCFnzIk2kALH/mbif0xy28BaQVU02O5B0rg33P7PJB4rXoAcwoLIWW19gnBVHTLvIKKjKeewJAWFsZOKN+EDG8UgvJo/yz4PYJSY7kIaR9G/myabP4szbwljChvlFNbyci/tMSJPba8deXJesJtV2GAeLK9Z8fpQ==";
		String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
		MainTest main = new MainTest();
		//main.test_trade_precreate(appid, privateKey, publicKey);
		//main.test_trade_pay(appid, privateKey, publicKey);
		//main.test_trade_query(appid, privateKey, publicKey);
		//main.test_trade_refund(appid, privateKey, publicKey);
		
		//main.test_download_bill(appid, privateKey, publicKey);
		main.test_sign(publicKey);
	}

	// 测试当面付2.0生成支付二维码
    public void test_trade_precreate(String appid, String privateKey, String publicKey) {
        // (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
        // 需保证商户系统端不能重复，建议通过数据库sequence生成，
        String outTradeNo = "tradeprecreate" + System.currentTimeMillis()
                            + (long) (Math.random() * 10000000L);

        // (必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店当面付扫码消费”
        String subject = "xxx品牌xxx门店当面付扫码消费";

        // (必填) 订单总金额，单位为元，不能超过1亿元
        // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
        String totalAmount = "0.01";

        // (可选) 订单不可打折金额，可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段
        // 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】
        String undiscountableAmount = "0";

        // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
        // 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
        String sellerId = "";

        // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品2件共15.00元"
        String body = "购买商品3件共20.00元";

        // 商户操作员编号，添加此参数可以为商户操作员做销售统计
        String operatorId = "test_operator_id";

        // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
        String storeId = "test_store_id";

        // 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
        ExtendParams extendParams = new ExtendParams();
        extendParams.setSysServiceProviderId("2088100200300400500");

        // 支付超时，定义为120分钟
        String timeoutExpress = "120m";

        // 商品明细列表，需填写购买商品详细信息，
        List<GoodsDetail> goodsDetailList = new ArrayList<GoodsDetail>();
        // 创建一个商品信息，参数含义分别为商品id（使用国标）、名称、单价（单位为分）、数量，如果需要添加商品类别，详见GoodsDetail
        GoodsDetail goods1 = GoodsDetail.newInstance("goods_id001", "xxx小面包", 1000, 1);
        // 创建好一个商品后添加至商品明细列表
        goodsDetailList.add(goods1);

        // 继续创建并添加第一条商品信息，用户购买的产品为“黑人牙刷”，单价为5.00元，购买了两件
        GoodsDetail goods2 = GoodsDetail.newInstance("goods_id002", "xxx牙刷", 500, 2);
        goodsDetailList.add(goods2);
        
        
        String notifyUrl = "";

        // 创建扫码支付请求builder，设置请求参数
        AlipayTradePrecreateRequestBuilder builder = new AlipayTradePrecreateRequestBuilder()
            .setSubject(subject).setTotalAmount(totalAmount).setOutTradeNo(outTradeNo)
            .setUndiscountableAmount(undiscountableAmount).setSellerId(sellerId).setBody(body)
            .setOperatorId(operatorId).setStoreId(storeId).setExtendParams(extendParams)
            .setTimeoutExpress(timeoutExpress)
            //                .setNotifyUrl("http://www.test-notify-url.com")//支付宝服务器主动通知商户服务器里指定的页面http路径,根据需要设置
            .setGoodsDetailList(goodsDetailList).setNotifyUrl(notifyUrl);

        AlipayTradeService   tradeService = new AlipayTradeServiceImpl.ClientBuilder().build(appid, privateKey,publicKey);
		
        AlipayF2FPrecreateResult result = tradeService.tradePrecreate(builder);
        switch (result.getTradeStatus()) {
            case SUCCESS:
            	logger.info("支付宝预下单成功: )");

                AlipayTradePrecreateResponse response = result.getResponse();
                
                logger.info(response.getBody());
                
                JSONObject json = JSONObject.fromObject(response.getBody()).getJSONObject("alipay_trade_precreate_response");
                
                System.out.println(json.getString("code"));

//                // 需要修改为运行机器上的路径
//                String filePath = String.format("/Users/sudo/Desktop/qr-%s.png",
//                    response.getOutTradeNo());
//                log.info("filePath:" + filePath);
                //                ZxingUtils.getQRCodeImge(response.getQrCode(), 256, filePath);
                break;

            case FAILED:
            	logger.error("支付宝预下单失败!!!");
                break;

            case UNKNOWN:
            	logger.error("系统异常，预下单状态未知!!!");
                break;

            default:
            	logger.error("不支持的交易状态，交易返回异常!!!");
                break;
        }
    }
    
    
 // 测试当面付2.0支付
    public void test_trade_pay(String appid, String privateKey, String publicKey) {
        // (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
        // 需保证商户系统端不能重复，建议通过数据库sequence生成，
        String outTradeNo = "tradepay" + System.currentTimeMillis()
                            + (long) (Math.random() * 10000000L);

        // (必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店消费”
        String subject = "xxx品牌xxx门店当面付消费";

        // (必填) 订单总金额，单位为元，不能超过1亿元
        // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
        String totalAmount = "0.02";

        // (必填) 付款条码，用户支付宝钱包手机app点击“付款”产生的付款条码
        String authCode = "283525438633513700"; // 条码示例，286648048691290423
        // (可选，根据需要决定是否使用) 订单可打折金额，可以配合商家平台配置折扣活动，如果订单部分商品参与打折，可以将部分商品总价填写至此字段，默认全部商品可打折
        // 如果该值未传入,但传入了【订单总金额】,【不可打折金额】 则该值默认为【订单总金额】- 【不可打折金额】
        //        String discountableAmount = "1.00"; //

        // (可选) 订单不可打折金额，可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段
        // 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】
        String undiscountableAmount = "0.0";

        // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
        // 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
        String sellerId = "";

        // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品3件共20.00元"
        String body = "购买商品3件共20.00元";

        // 商户操作员编号，添加此参数可以为商户操作员做销售统计
        String operatorId = "test_operator_id";

        // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
        String storeId = "test_store_id";

        // 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
        String providerId = "2088100200300400500";
        ExtendParams extendParams = new ExtendParams();
        extendParams.setSysServiceProviderId(providerId);

        // 支付超时，线下扫码交易定义为5分钟
        String timeoutExpress = "5m";

        // 商品明细列表，需填写购买商品详细信息，
        List<GoodsDetail> goodsDetailList = new ArrayList<GoodsDetail>();
        // 创建一个商品信息，参数含义分别为商品id（使用国标）、名称、单价（单位为分）、数量，如果需要添加商品类别，详见GoodsDetail
        GoodsDetail goods1 = GoodsDetail.newInstance("goods_id001", "xxx面包", 1000, 1);
        // 创建好一个商品后添加至商品明细列表
        goodsDetailList.add(goods1);

        // 继续创建并添加第一条商品信息，用户购买的产品为“黑人牙刷”，单价为5.00元，购买了两件
        GoodsDetail goods2 = GoodsDetail.newInstance("goods_id002", "xxx牙刷", 500, 2);
        goodsDetailList.add(goods2);

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
        switch (result.getTradeStatus()) {
            case SUCCESS:
            	logger.info("支付宝支付成功: )");
            	break;

            case FAILED:
            	logger.error("支付宝支付失败!!!");
                break;

            case UNKNOWN:
            	logger.error("系统异常，订单状态未知!!!");
                break;

            default:
            	logger.error("不支持的交易状态，交易返回异常!!!");
                break;
        }
    }

 // 测试当面付2.0查询订单
    public void test_trade_query(String appid, String privateKey, String publicKey) {
        // (必填) 商户订单号，通过此商户订单号查询当面付的交易状态
        String outTradeNo = "tradepay14840331412632113699";
        
        AlipayTradeService tradeService = new AlipayTradeServiceImpl.ClientBuilder().build(appid, privateKey,publicKey);

        // 创建查询请求builder，设置请求参数
        AlipayTradeQueryRequestBuilder builder = new AlipayTradeQueryRequestBuilder()
            .setOutTradeNo(outTradeNo);

        AlipayF2FQueryResult result = tradeService.queryTradeResult(builder);
        switch (result.getTradeStatus()) {
            case SUCCESS:
            	logger.info("查询返回该订单支付成功: )");

                AlipayTradeQueryResponse response = result.getResponse();

                logger.info(response.getTradeStatus());
                if (Utils.isListNotEmpty(response.getFundBillList())) {
                    for (TradeFundBill bill : response.getFundBillList()) {
                    	logger.info(bill.getFundChannel() + ":" + bill.getAmount());
                    }
                }
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

 // 测试当面付2.0退款
    public void test_trade_refund(String appid, String privateKey, String publicKey) {
        // (必填) 外部订单号，需要退款交易的商户外部订单号
        String outTradeNo = "tradepay14840331412632113699";

        // (必填) 退款金额，该金额必须小于等于订单的支付金额，单位为元
        String refundAmount = "0.01";

        // (可选，需要支持重复退货时必填) 商户退款请求号，相同支付宝交易号下的不同退款请求号对应同一笔交易的不同退款申请，
        // 对于相同支付宝交易号下多笔相同商户退款请求号的退款交易，支付宝只会进行一次退款
        String outRequestNo = "RN"+System.currentTimeMillis();

        // (必填) 退款原因，可以说明用户退款原因，方便为商家后台提供统计
        String refundReason = "正常退款，用户买多了";

        // (必填) 商户门店编号，退款情况下可以为商家后台提供退款权限判定和统计等作用，详询支付宝技术支持
        String storeId = "test_store_id";
        
        AlipayTradeService tradeService = new AlipayTradeServiceImpl.ClientBuilder().build(appid, privateKey,publicKey);

        // 创建退款请求builder，设置请求参数
        AlipayTradeRefundRequestBuilder builder = new AlipayTradeRefundRequestBuilder()
            .setOutTradeNo(outTradeNo).setRefundAmount(refundAmount).setRefundReason(refundReason)
            .setOutRequestNo(outRequestNo).setStoreId(storeId);

        AlipayF2FRefundResult result = tradeService.tradeRefund(builder);
        switch (result.getTradeStatus()) {
            case SUCCESS:
            	logger.info("支付宝退款成功: )");
                break;

            case FAILED:
            	logger.error("支付宝退款失败!!!");
                break;

            case UNKNOWN:
            	logger.error("系统异常，订单退款状态未知!!!");
                break;

            default:
            	logger.error("不支持的交易状态，交易返回异常!!!");
                break;
        }
    }

    
    // 对帐单下载
    public void test_download_bill(String appid, String privateKey, String publicKey) {
        String bill_type = "trade";
        
        String bill_date = "2016-10-07";
        
        AlipayTradeService tradeService = new AlipayTradeServiceImpl.ClientBuilder().build(appid, privateKey,publicKey);

        // 创建查询请求builder，设置请求参数
        AlipayTradeDownloadBillRequestBuilder builder = new AlipayTradeDownloadBillRequestBuilder()
        		.setBill_type(bill_type)
        		.setBill_date(bill_date);

        AlipayF2FDownloadBillResult result = tradeService.downloadBill(builder);
        switch (result.getTradeStatus()) {
            case SUCCESS:
            	logger.info("对帐单下载地址获取成功");

            	AlipayDataDataserviceBillDownloadurlQueryResponse response = result.getResponse();

                System.out.println(response.getBillDownloadUrl());
                
                downloadBillFile(response.getBillDownloadUrl());
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
    
    private void downloadBillFile(String downloadUrl){
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
			
			String filename = "123.zip";
			String fullpath = path + "/" + filename;
			
			BufferedInputStream bis = new BufferedInputStream(result.getContent());
			FileOutputStream fos = new FileOutputStream(new File(fullpath));
			byte[] buf = new byte[8096];
			int size = 0;
			while ((size = bis.read(buf)) != -1)
				fos.write(buf, 0, size);
			fos.close();
			bis.close();
			
			response.close();
			httpclient.close();
			
			
			unzipFile(fullpath);
        	
        } catch (Exception e) {  
           logger.error("下载对帐文件异常:"+e.getMessage(), e);
        }
    }
    
    private void unzipFile(String filename){
    	try{
    		ZipFile zf = new ZipFile(filename, Charset.forName("GBK"));  
    		FileInputStream in = new FileInputStream(filename);
            ZipInputStream zin = new ZipInputStream(in, Charset.forName("GBK"));
            ZipEntry entry;  
            while((entry = zin.getNextEntry())!=null && !entry.isDirectory()){  
                
            	if(entry.getName().lastIndexOf("业务明细.csv") != -1){
            		long size = entry.getSize();  
                    if (size > 0) {  
                        BufferedReader br = new BufferedReader(new InputStreamReader(zf.getInputStream(entry), "GBK"));
                        String line;  
                        while ((line = br.readLine()) != null) {  
                            System.out.println(line);  
                        }  
                        br.close();  
                    }  
            	}
                
            }  
            zin.closeEntry(); 
    		
    	}catch (Exception e) {  
            logger.error("请求异常:"+e.getMessage(), e);
        }
    }
    
    public void test_sign(String publicKey){
    	String str = "body=这只是一个支付测试&open_id=20880065359376446496955620419636&subject=这只是一个支付测试&sign_type=RSA&buyer_logon_id=wu2***@163.com&auth_app_id=2015111700819307&notify_type=trade_status_sync&out_trade_no=2017020922305310000161113151&version=1.0&point_amount=0.00&fund_bill_list=[{\"amount\":\"0.01\",\"fundChannel\":\"ALIPAYACCOUNT\"}]&buyer_id=2088202817049364&total_amount=0.01&trade_no=2017020921001004360274513875&notify_time=2017-02-09 22:31:07&charset=utf-8&invoice_amount=0.01&gmt_payment=2017-02-09 22:31:07&trade_status=TRADE_SUCCESS&sign=BwsRmDSljKu1fGGDQRdKjOt/X4qBB7SEW4Im7AeaB/Hy78K0e0/zQp4dlhp0ZS58FXyTRmOBDcrmp1pX5D+NpLmH8vz0jXhSjOrVJI8YT6K4zE/qGLLRmcPoek++1mg1sdR1T0mwjn+tFyF0mDgsSpmrJfSFKyMDmLqdXNGYafY=&gmt_create=2017-02-09 22:31:03&buyer_pay_amount=0.01&receipt_amount=0.01&app_id=2015111700819307&seller_id=2088811332954653&notify_id=58dd98b5bb80af6599029ff56194681is2&seller_email=gxltds@163.com";
    	Map<String, String> params = new HashMap<>();
    	String[] strs = str.split("&");
    	
    	for(String s : strs){
    		System.out.println(s);
    		params.put(s.split("=")[0], s.split("=")[1]);
    	}
    	
    	
    	
        System.out.println(verify(params, publicKey));
    }
    
    public static boolean verify(Map<String, String> params, String publicKey){
    	//过滤空值、sign与sign_type参数
    	String sign = params.get("sign");
    	//String sign_type = params.get("sign_type");
    	params.remove("sign");
    	params.remove("sign_type");
        //获取待签名字符串
        String preSignStr = createLinkString(params);
        //获得签名验证结果
        return verify(preSignStr, sign, publicKey, params.get("charset"));
    }
    
    public static String createLinkString(Map<String, String> params) {

        List<String> keys = new ArrayList<String>(params.keySet());
        //字典排序，在签名验证签名过程中都必须经过字典排序
        Collections.sort(keys);

        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);

            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }

        return prestr;
    }
    
    public static boolean verify(String content, String sign, String ali_public_key, String input_charset)
	{
		try 
		{
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	        byte[] encodedKey = Base64.decodeBase64(ali_public_key);
	        PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

		
			java.security.Signature signature = java.security.Signature.getInstance("SHA1WithRSA");
		
			signature.initVerify(pubKey);
			signature.update( content.getBytes(input_charset) );
		
			boolean bverify = signature.verify( Base64.decodeBase64(sign) );
			return bverify;
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return false;
	}
}
