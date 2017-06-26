package com.tydic.unicom.apc.base.pub.impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.impl.builder.StAXOMBuilder;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tydic.unicom.apc.base.pub.interfaces.BssService;
import com.tydic.unicom.util.DateUtil;

@Service("BssService")
public class BssServiceImpl implements BssService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BssServiceImpl.class);
	
	@Override
	public String callWebServiceByAxis2(String URL, String actionName, String xmlStr, int maxLogLength) {
		String returnXmlStr = "";
		
		String logXmlStr = xmlStr;
		try {
		//传0的话就是不限制长度的
		if(maxLogLength > 0){
			SAXReader sax=new SAXReader();
		    Document document;
			document = sax.read(new ByteArrayInputStream(xmlStr.getBytes("UTF-8")));
			
		    Element root=document.getRootElement();
		    getNodes(root, maxLogLength);
		    
		    logXmlStr = document.asXML();
		}
		
		LOGGER.info("\n888888888888888888888888888888888888------USS能力平台请求报文------888888888888888888888888888888888888888888888\n"
		        +"请求地址："+URL+"\n"
				+ logXmlStr
		        + "\n888888888888888888888888888888888888------USS能力平台请求报文------888888888888888888888888888888888888888888888\n");
		
			EndpointReference endpointReference = new EndpointReference(URL);
			
			OMElement method = (new StAXOMBuilder(new ByteArrayInputStream(xmlStr.getBytes("utf-8")))).getDocumentElement();
			
			// 设置参数
			Options options = new Options();
			options.setAction(actionName);
			options.setTo(endpointReference);
			
			options.setTimeOutInMilliSeconds(200000);
			
			ServiceClient sender = new ServiceClient();
			sender.setOptions(options);
			
			// 发送并得到结果，至此，调用成功，并得到了结果
			OMElement result = sender.sendReceive(method);
			
			returnXmlStr = result.toString();
		
		String logReturnXmlStr = returnXmlStr;
		if(maxLogLength > 0){
			SAXReader sax=new SAXReader();
		    Document document;
			document = sax.read(new ByteArrayInputStream(logReturnXmlStr.getBytes("UTF-8")));
			
		    Element root=document.getRootElement();
		    getNodes(root, maxLogLength);
		    
		    logReturnXmlStr = document.asXML();
		}
		LOGGER.info("\n888888888888888888888888888888888888------USS能力平台返回报文------888888888888888888888888888888888888888888888\n"
				+ logReturnXmlStr
				+ "\n888888888888888888888888888888888888------USS能力平台返回报文------888888888888888888888888888888888888888888888\n");
		return returnXmlStr;
		} catch (AxisFault ex) {
			ex.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnXmlStr;
	}
	
	@Override
	public String callWebServiceByAxis2(String URL, String actionName, String xmlStr) {
		return callWebServiceByAxis2(URL, actionName, xmlStr, 0);
	}
	
	private void getNodes(Element node, int maxlen){
		String text = node.getTextTrim();
	    if(maxlen > 0 && text.length() > maxlen*2){
	    	text = text.substring(0, maxlen) + "\n..中间忽略..\n"+text.substring(text.length()-maxlen);
	    	node.setText(text);
        }
	      
	    //递归遍历当前节点所有的子节点  
	    List<Element> listElement=node.elements();//所有一级子节点的list  
	    for(Element e:listElement){//遍历所有一级子节点  
	        getNodes(e, maxlen);//递归  
	    }
	}  
	
	@Override
	public String callHttpServiceByHttpClient(String URL, String xmlStr, Map<String, Object> inMap) {
		String returnXmlStr = "";
		
		// 拼接xml报文
		xmlStr = contactHttpXmlStr(xmlStr, inMap);
		
		LOGGER.info("\n888888888888888888888888888888888888------MINI厅请求报文------888888888888888888888888888888888888888888888\n"
		        + xmlStr
		        + "\n888888888888888888888888888888888888------MINI厅请求报文------888888888888888888888888888888888888888888888\n");
		
		// 创建默认的客户端实例
		HttpClient httpCLient = new DefaultHttpClient();
		
		HttpPost httppost = new HttpPost(URL);
		
		// 设置参数
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("xmlmsg", xmlStr));
		httppost.addHeader("Content-type", "application/x-www-form-urlencoded");
		
		// 设置编码格式,防止中文乱码
		try {
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
		try {
			// 客户端执行get请求 返回响应实体
			HttpResponse response = httpCLient.execute(httppost);
			
			// 服务器响应状态行
			// System.out.println("@@@"+response.getStatusLine());
			
			// Header[] heads = response.getAllHeaders();
			// 打印所有响应头
			// for(Header h:heads){
			// System.out.println(h.getName()+":"+h.getValue());
			// }
			
			// 获取响应消息实体
			HttpEntity entity = response.getEntity();
			
			if (entity != null) {
				// 报文转换
				java.net.URLDecoder ud = new java.net.URLDecoder();
				returnXmlStr = EntityUtils.toString(entity);
				returnXmlStr = ud.decode(returnXmlStr, "UTF-8");
				// 响应内容长度
				// System.out.println("响应内容长度："+entity.getContentLength());
			}
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			httpCLient.getConnectionManager().shutdown();
		}
		LOGGER.info("\n888888888888888888888888888888888888------MINI厅返回报文------888888888888888888888888888888888888888888888\n"
		        + returnXmlStr
		        + "\n888888888888888888888888888888888888------MINI厅返回报文------888888888888888888888888888888888888888888888\n");
		return returnXmlStr;
	}
	
	@Override
	public String contactHttpXmlStr(String xmlStr, Map<String, Object> inMap) {
		String transIDC = Lpad((String)inMap.get("transIDC"), 5);
		// 当前日期时间字符串
		String nowStr = DateUtil.getSysDateString("yyyyMMddHHmmss");
		String today = DateUtil.getSysDateString("yyyyMMdd");
		String uessStr = "UESSGX" + nowStr + transIDC;
		String ProcID = inMap.containsKey("ProcID") ? inMap.get("ProcID").toString() : uessStr;	
		//String ProcID = uessStr;
		String TransIDO = uessStr;
		String TransIDC = uessStr;
		String ConvID = uessStr;
		String RouteType = inMap.containsKey("RouteType") ? inMap.get("RouteType").toString() : "59";
		String TestFlag = inMap.containsKey("TestFlag") ? inMap.get("TestFlag").toString() : "0";
		String MsgSender = inMap.containsKey("MsgSender") ? inMap.get("MsgSender").toString() : "1101";
		String MsgReceiver = inMap.containsKey("MsgReceiver") ? inMap.get("MsgReceiver").toString() : "1100";
		String SvcContVer = inMap.containsKey("SvcContVer") ? inMap.get("SvcContVer").toString() : "0100";
		String OrigDomain = inMap.containsKey("OrigDomain") ? inMap.get("OrigDomain").toString() : "GUSS";
		String HomeDomain = inMap.containsKey("HomeDomain") ? inMap.get("HomeDomain").toString() : "UCRM";
		String OSNDUNS = inMap.containsKey("OSNDUNS") ? inMap.get("OSNDUNS").toString() : "0002";
		String HSNDUNS = inMap.containsKey("HSNDUNS") ? inMap.get("HSNDUNS").toString() : "1100";
		
		
		String ProcessTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		// 拼接报文
		String reqXmlStr = "";
		reqXmlStr += "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		reqXmlStr += "<UniBSS>";
		reqXmlStr += "<OrigDomain>"+OrigDomain+"</OrigDomain>";
		reqXmlStr += "    <HomeDomain>"+HomeDomain+"</HomeDomain>";
		reqXmlStr += "    <BIPCode>" + inMap.get("BIPCode") + "</BIPCode>";
		reqXmlStr += "    <BIPVer>0100</BIPVer>";
		reqXmlStr += "    <ActivityCode>" + inMap.get("ActivityCode") + "</ActivityCode>";
		reqXmlStr += "    <ActionCode>0</ActionCode>";
		reqXmlStr += "    <ActionRelation>0</ActionRelation>";
		reqXmlStr += "    <Routing>";
		reqXmlStr += "        <RouteType>"+RouteType+"</RouteType>";
		String routeValue = (String)(inMap.get("RouteValue") == null || inMap.get("RouteValue").equals("")
		        || inMap.get("RouteValue").equals("null") ? "59" : inMap.get("RouteValue") + "");
		reqXmlStr += "        <RouteValue>" + routeValue + "</RouteValue>";
		reqXmlStr += "    </Routing>";
		reqXmlStr += "    <ProcID>" + ProcID + "</ProcID>";
		reqXmlStr += "    <TransIDO>" + TransIDO + "</TransIDO>";
		reqXmlStr += "    <ProcessTime>" + ProcessTime + "</ProcessTime>";
		reqXmlStr += "    <SPReserve>";
		reqXmlStr += "        <TransIDC>" + TransIDC + "</TransIDC>";
		reqXmlStr += "        <CutOffDay>" + today + "</CutOffDay>";
		reqXmlStr += "        <OSNDUNS>"+OSNDUNS+"</OSNDUNS>";
		reqXmlStr += "        <HSNDUNS>"+HSNDUNS+"</HSNDUNS>";
		reqXmlStr += "        <ConvID>" + ConvID + "</ConvID>";
		reqXmlStr += "    </SPReserve>";
		reqXmlStr += "    <TestFlag>"+TestFlag+"</TestFlag>";
		reqXmlStr += "    <MsgSender>"+MsgSender+"</MsgSender>";
		reqXmlStr += "    <MsgReceiver>"+MsgReceiver+"</MsgReceiver>";
		reqXmlStr += "    <SvcContVer>"+SvcContVer+"</SvcContVer>";
		reqXmlStr += "    <SvcCont><![CDATA[<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		reqXmlStr += xmlStr;
		reqXmlStr += "        ]]>";
		reqXmlStr += "    </SvcCont>";
		reqXmlStr += "</UniBSS>";
		
		return reqXmlStr;
	}
	
	public static String Lpad(String str, int length) {
		String lpadStr = "";
		for (int i = 0; i < (length - str.length()); i++) {
			lpadStr += "0";
		}
		return lpadStr + str;
	}
	
	@Override
	public String callHttpServiceByHttpServlet(String url, String query) throws Exception {
		URL restURL = new URL(url);
		HttpURLConnection conn = (HttpURLConnection)restURL.openConnection();
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		conn.setAllowUserInteraction(false);
		PrintStream ps = new PrintStream(conn.getOutputStream());
		ps.print(query);
		ps.close();
		BufferedReader bReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line, resultStr = "";
		while (null != (line = bReader.readLine())) {
			resultStr += line;
		}
		bReader.close();
		return resultStr;
	}

	@Override
	public String hnCallWebServiceByAxis2(String URL, String actionName, String xmlStr) {
		// TODO Auto-generated method stub

		String returnXmlStr = "";
		LOGGER.info("\n888888888888888888888888888888888888------沃支付账户注册接口------888888888888888888888888888888888888888888888\n"
		        + xmlStr
		        + "\n888888888888888888888888888888888888------沃支付账户注册接口-----888888888888888888888888888888888888888888888\n");
		try {
			EndpointReference endpointReference = new EndpointReference(URL);
			
			OMElement method = (new StAXOMBuilder(new ByteArrayInputStream(xmlStr.getBytes("utf-8")))).getDocumentElement();
			
			// 设置参数
			Options options = new Options();
			options.setAction(actionName);
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
		if( returnXmlStr.length()>200) {
			LOGGER.info("\n888888888888888888888888888888888888------沃支付账户注册接口------888888888888888888888888888888888888888888888\n"
					+ returnXmlStr.substring(0,200).trim()
					+ "\n888888888888888888888888888888888888------沃支付账户注册接口------888888888888888888888888888888888888888888888\n");
		}else {
			LOGGER.info("\n888888888888888888888888888888888888------沃支付账户注册接口------888888888888888888888888888888888888888888888\n"
					+ returnXmlStr
					+ "\n888888888888888888888888888888888888------沃支付账户注册接口------888888888888888888888888888888888888888888888\n");
		}
		return returnXmlStr;
	
	}
	
}
