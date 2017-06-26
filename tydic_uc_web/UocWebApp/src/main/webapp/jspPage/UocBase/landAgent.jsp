
<%@ page import="java.net.*,java.util.*,java.lang.*,java.io.*"%>
<%@ page import="java.sql.*" %>
<%@ page language="java" import="java.sql.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/xml;charset=gb2312"%>
<%@ page import="net.sf.json.*" %>
<%@ page import="org.apache.commons.httpclient.*" %>
<%@ page import="org.apache.commons.httpclient.methods.*" %>

<% 

/*JSONObject jSONObject1=new JSONObject();
JSONArray jSONArry=new JSONArray();
for(int i=0;i<17;i++){
	JSONObject jSONObject=new JSONObject();
	jSONObject.put("tache_code_a", 0+i);
	jSONObject.put("tache_code_b","qw"+i);
	jSONObject.put("province_code","dfsafd"+i);
	jSONObject.put("area_code","12"+i);
	jSONArry.add(jSONObject);
}

jSONObject1.put("total", 17);
jSONObject1.put("rows",jSONArry);
System.out.print("jSONArry="+jSONObject1.toString());
out.println(jSONObject1.toString());*/


String url = "http://127.0.0.1:8080/uoc_rest/rest/ordModServiceRest/getOrdModTacheRule";
String host="127.0.0.1:8080";
String port = host.split(":")[1];
host=host.split(":")[0];
HttpClient httpClient = new HttpClient();
	httpClient.getHostConfiguration().setHost(host, Integer.parseInt(port), "http");
	
	JSONObject jsonObject=new JSONObject();
	jsonObject.put("pageNo", 1);
	jsonObject.put("pageSize", 10);
	JSONObject param=new JSONObject();
	param.put("oper_code_a", "AA");
	param.put("tache_code_a", "321");
	param.put("province_code", "83");
	jsonObject.put("param", param);


	PostMethod post = new PostMethod(url);
	post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=gbk");  
	NameValuePair[] data = {
		new NameValuePair("jsession_id","555555555555555555"),
		new NameValuePair("json_info",jsonObject.toString()),
		new NameValuePair("oper_type","103"),
		} ;
	post.setRequestBody(data);
	post.releaseConnection();
	
	
	HttpMethod method = post;
	
	httpClient.executeMethod(method);
	String str =method.getResponseBodyAsString();
	System.out.println("response>>>>" + str);
	
	JSONArray jSONArry=new JSONArray();
	
	JSONObject jSONObjectNew = JSONObject.fromObject(method.getResponseBodyAsString());  
	JSONObject jSONObjectNew1 = JSONObject.fromObject(jSONObjectNew.get("args"));
	JSONObject jSONObjectNew2 = JSONObject.fromObject(jSONObjectNew1.get("json_info"));
	jSONArry = jSONObjectNew2.getJSONArray("ordModTacheRule");

	JSONObject JSONObjectback = new JSONObject();
	
	JSONObjectback.put("total", jSONArry.size());
	JSONObjectback.put("rows",jSONArry);
	
	response.getWriter().write(JSONObjectback.toString());

 





%>