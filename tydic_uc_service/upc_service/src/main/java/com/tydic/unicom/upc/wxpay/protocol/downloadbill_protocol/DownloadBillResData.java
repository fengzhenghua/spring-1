package com.tydic.unicom.upc.wxpay.protocol.downloadbill_protocol;

import java.io.Serializable;

/**
 * User: rizenguo
 * Date: 2014/10/25
 * Time: 16:48
 */
public class DownloadBillResData  implements Serializable {

	private static final long serialVersionUID = -2586607717277116574L;
	//协议层
    private String return_code = "";
    private String return_msg = "";
    private String error_code = "";

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }

	public String getError_code() {
		return error_code;
	}

	public void setError_code(String error_code) {
		this.error_code = error_code;
	}
    
    
}
