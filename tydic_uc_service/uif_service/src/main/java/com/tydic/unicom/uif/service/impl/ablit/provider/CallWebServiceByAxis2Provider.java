package com.tydic.unicom.uif.service.impl.ablit.provider;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.impl.builder.StAXOMBuilder;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tydic.unicom.uif.service.interfaces.IAbilitProvider;
import com.tydic.unicom.uif.service.vo.CallWebServiceVo;

/**
 * 
 * <p>
 * </p>
 * @author heguoyong 2017年5月17日 下午6:44:26
 * @ClassName CallWebServiceByAxis2Ablit
 * @Description 调用webservice能力
 * @version V1.0
 */
public class CallWebServiceByAxis2Provider implements IAbilitProvider<CallWebServiceVo> {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public String callAblit(CallWebServiceVo requestVo) {
		
		String returnXmlStr = new String();
		logger.info(
		        "\n888888888888888888888888888888888888------USS能力平台请求报文------888888888888888888888888888888888888888888888\n"
		                + "请求地址：" + requestVo.getUrl() + "\n" + requestVo.getXmlStr()
		                + "\n888888888888888888888888888888888888------USS能力平台请求报文------888888888888888888888888888888888888888888888\n");
		try {
			EndpointReference endpointReference = new EndpointReference(requestVo.getUrl());
			
			OMElement method = (new StAXOMBuilder(new ByteArrayInputStream(requestVo.getXmlStr().getBytes("utf-8"))))
			        .getDocumentElement();
			
			// 设置参数
			Options options = new Options();
			options.setAction(requestVo.getActionName());
			options.setTo(endpointReference);
			
			options.setTimeOutInMilliSeconds(200000);
			
			ServiceClient sender = new ServiceClient();
			sender.setOptions(options);
			
			// 发送并得到结果，至此，调用成功，并得到了结果
			OMElement result = sender.sendReceive(method);
			
			returnXmlStr = result.toString();
		} catch (AxisFault ex) {
			ex.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info(
		        "\n888888888888888888888888888888888888------USS能力平台返回报文------888888888888888888888888888888888888888888888\n"
		                + returnXmlStr
		                + "\n888888888888888888888888888888888888------USS能力平台返回报文------888888888888888888888888888888888888888888888\n");
		
		returnXmlStr.replace("__", "_").replace("&lt;", '<'+"").replace("&gt;", '>'+"").replace("&quot;", '"'+"");
		byte[] bs = returnXmlStr.getBytes();
		try {
			returnXmlStr=new String(bs,"utf-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(),e);
		}
		logger.info("==============能力平台返回结果报文===============");
		logger.info(returnXmlStr);
		logger.info("==============能力平台返回结果报文===============");
		return returnXmlStr;
	}
	
}
