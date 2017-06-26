package com.tydic.unicom.upc.vo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UpcPropertiesVo {

	/**********支付宝配置文件********/
	// 付宝openapi域名
	@Value("${openApiDomain}")
	private String openApiDomain;
	
	// 支付宝mcloudmonitor域名
	@Value("${mcloudApiDomain}")
	private String mcloudApiDomain;
	
	//支付宝的签名方式
	@Value("${signType}") 
	private String signType;
	
	//支付宝异步扫码通知签名校验的类型 RSA2
	@Value("${Signature}")
	private String Signature;

	/******** 微信配置文件**********/
	
	//以下是几个API的路径：
	//1）被扫支付API
	@Value("${PAY_API}")
	private String PAY_API;

	//2）被扫支付查询API
	@Value("${PAY_QUERY_API}")
	private String PAY_QUERY_API;

	//3）退款API
	@Value("${REFUND_API}")
	private String REFUND_API;

	//4）退款查询API
	@Value("${REFUND_QUERY_API}")
	private String REFUND_QUERY_API;

	//5）撤销API
	@Value("${REVERSE_API}")
	private String REVERSE_API;

	//6）下载对账单API
	@Value("${DOWNLOAD_BILL_API}")
	public String DOWNLOAD_BILL_API;

	//7) 统计上报API
	@Value("${REPORT_API}")
	private String REPORT_API;
	
	//统一下单
	@Value("${UNIFIED_ORDER_API}")
	private String UNIFIED_ORDER_API;

	@Value("${HttpsRequestClassName}")
	private String  HttpsRequestClassName;
	
	//微信证书路径
	@Value("${wx_cert_path}")
	private String wx_cert_path;
	
	
	public String getOpenApiDomain() {
		return openApiDomain;
	}


	public String getMcloudApiDomain() {
		return mcloudApiDomain;
	}


	public String getSignType() {
		return signType;
	}
	
	public String getSignature() {
		return Signature;
	}


	public String getPAY_API() {
		return PAY_API;
	}


	public String getPAY_QUERY_API() {
		return PAY_QUERY_API;
	}


	public String getREFUND_API() {
		return REFUND_API;
	}


	public String getREFUND_QUERY_API() {
		return REFUND_QUERY_API;
	}


	public String getREVERSE_API() {
		return REVERSE_API;
	}


	public String getDOWNLOAD_BILL_API() {
		return DOWNLOAD_BILL_API;
	}


	public String getREPORT_API() {
		return REPORT_API;
	}


	public String getUNIFIED_ORDER_API() {
		return UNIFIED_ORDER_API;
	}

	public String getHttpsRequestClassName() {
		return HttpsRequestClassName;
	}


	public String getWx_cert_path() {
		return wx_cert_path;
	}
}
