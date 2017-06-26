package com.tydic.unicom.upc.alipay.model.result;

import com.alipay.api.AlipayResponse;
import com.alipay.api.response.AlipayDataDataserviceBillDownloadurlQueryResponse;
import com.tydic.unicom.upc.alipay.model.TradeStatus;

public class AlipayF2FDownloadBillResult implements Result {

	 private TradeStatus tradeStatus;
	    private AlipayDataDataserviceBillDownloadurlQueryResponse response;

	    public AlipayF2FDownloadBillResult(AlipayDataDataserviceBillDownloadurlQueryResponse response) {
	        this.response = response;
	    }

	    public void setTradeStatus(TradeStatus tradeStatus) {
	        this.tradeStatus = tradeStatus;
	    }

	    public void setResponse(AlipayDataDataserviceBillDownloadurlQueryResponse response) {
	        this.response = response;
	    }

	    public TradeStatus getTradeStatus() {
	        return tradeStatus;
	    }

	    public AlipayDataDataserviceBillDownloadurlQueryResponse getResponse() {
	        return response;
	    }

	    @Override
	    public boolean isTradeSuccess() {
	        return response != null &&
	                TradeStatus.SUCCESS.equals(tradeStatus);
	    }

}
