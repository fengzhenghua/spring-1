package com.tydic.unicom.apc.common.wxpay;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.tydic.unicom.apc.service.common.vo.PropertiesParamVo;

/**
 * 扫码支付模式二
 * 统计下单NATIVE方式
 * @author 吴川
 *
 */
public class UnifiedOrderNativeService extends BaseService {
//	@Autowired
//	@Qualifier("propertiesParamVo")
//	private static PropertiesParamVo propertiesParamVo;
	public UnifiedOrderNativeService(String api) throws ClassNotFoundException,
			IllegalAccessException, InstantiationException, SecurityException, IllegalArgumentException, NoSuchMethodException, InvocationTargetException, UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException {
		super(api);
	}

	/**
     * 请求支付服务
     * @param scanPayReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @return API返回的数据
     * @throws Exception
     */
    public String request(UnifiedOrderNativeReqData unifiedOrderNativeReqData) throws Exception {

        //--------------------------------------------------------------------
        //发送HTTPS的Post请求到API地址
        //--------------------------------------------------------------------
        String responseString = sendPost(unifiedOrderNativeReqData);

        return responseString;
    }
	
	
}
