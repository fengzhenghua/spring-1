package com.tydic.unicom.apc.common.wxpay;

import java.lang.reflect.InvocationTargetException;


public class RefundService extends BaseService {
	
	public RefundService(String certPath, String certPassword,String api) throws IllegalAccessException, InstantiationException, ClassNotFoundException, SecurityException, IllegalArgumentException, NoSuchMethodException, InvocationTargetException,Exception {
        super(api, certPath, certPassword);
    }

    /**
     * 请求退款服务
     * @param refundReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @return API返回的XML数据
     * @throws Exception
     */
    public String request(RefundReqData refundReqData) throws Exception {

        //--------------------------------------------------------------------
        //发送HTTPS的Post请求到API地址
        //--------------------------------------------------------------------
        String responseString = sendPost(refundReqData);

        return responseString;
    }
}
