package com.tydic.unicom.upc.alipay.model.builder;


import org.apache.commons.lang.StringUtils;

import com.google.gson.annotations.SerializedName;

public class AlipayTradeDownloadBillRequestBuilder extends RequestBuilder {

	private BizContent bizContent = new BizContent();

    @Override
    public BizContent getBizContent() {
        return bizContent;
    }

    @Override
    public boolean validate() {
        if (StringUtils.isEmpty(bizContent.bill_type)) {
            throw new NullPointerException("bill_type should not be NULL!");
        }
        if (StringUtils.isEmpty(bizContent.bill_date)) {
            throw new NullPointerException("bill_date should not be NULL!");
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AlipayTradeDownloadBillRequestBuilder{");
        sb.append("bizContent=").append(bizContent);
        sb.append(", super=").append(super.toString());
        sb.append('}');
        return sb.toString();
    }

    @Override
    public AlipayTradeDownloadBillRequestBuilder setAppAuthToken(String appAuthToken) {
        return (AlipayTradeDownloadBillRequestBuilder) super.setAppAuthToken(appAuthToken);
    }

    @Override
    public AlipayTradeDownloadBillRequestBuilder setNotifyUrl(String notifyUrl) {
        return (AlipayTradeDownloadBillRequestBuilder) super.setNotifyUrl(notifyUrl);
    }
    
    public static class BizContent {
		// 账单类型，商户通过接口或商户经开放平台授权后其所属服务商通过接口可以获取以下账单类型：
    	//trade、signcustomer；trade指商户基于支付宝交易收单的业务账单；
    	//signcustomer是指基于商户支付宝余额收入及支出等资金变动的帐务账单；
 
    	@SerializedName("bill_type")
        private String bill_type;

        //账单时间：日账单格式为yyyy-MM-dd，月账单格式为yyyy-MM。
        @SerializedName("bill_date")
        private String bill_date;

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("BizContent{");
            sb.append("bill_type='").append(bill_type).append('\'');
            sb.append(", bill_date='").append(bill_date).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }
    
    public String getBill_type() {
		return bizContent.bill_type;
	}

	public AlipayTradeDownloadBillRequestBuilder setBill_type(String bill_type) {
		bizContent.bill_type = bill_type;
		
		return this;
	}

	public String getBill_date() {
		return bizContent.bill_date;
	}

	public AlipayTradeDownloadBillRequestBuilder setBill_date(String bill_date) {
		bizContent.bill_date = bill_date;
		
		return this;
	}
    
    
}
