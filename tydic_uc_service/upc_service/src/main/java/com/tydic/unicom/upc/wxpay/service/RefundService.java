package com.tydic.unicom.upc.wxpay.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

import com.tydic.unicom.upc.wxpay.common.Configure;
import com.tydic.unicom.upc.wxpay.common.HttpsRequest;
import com.tydic.unicom.upc.wxpay.common.RandomStringGenerator;
import com.tydic.unicom.upc.wxpay.common.Signature;
import com.tydic.unicom.upc.wxpay.protocol.refund_protocol.RefundReqData;

/**
 * User: rizenguo
 * Date: 2014/10/29
 * Time: 16:04
 */
public class RefundService extends BaseService{

    public RefundService(String certPath, String certPassword) throws IllegalAccessException, InstantiationException, ClassNotFoundException, UnrecoverableKeyException, KeyManagementException, SecurityException, NoSuchMethodException, IllegalArgumentException, NoSuchAlgorithmException, KeyStoreException, InvocationTargetException, IOException {
        super(Configure.REFUND_API, certPath, certPassword);
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
