
<%@ page import="java.net.*,java.util.*,java.lang.*,java.io.*"%>
<%@ page import="java.sql.*" %>
<%@ page language="java" import="java.sql.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/xml;charset=gb2312"%>
<%@ page import="net.sf.json.JSONObject;" %>
<%@ page import="net.sf.json.JSONArray;" %>

<% 
String url = null;
StringBuffer Param= new StringBuffer();
Enumeration enu = request.getParameterNames();
int total = 0;
while (enu.hasMoreElements()) {
String paramName=(String)enu.nextElement();
System.out.println(paramName);
if(paramName.equals("url")){
   url=request.getParameter(paramName);
   System.out.println("url="+url);
}else{
	   if(total==0){
		   Param.append(paramName).append("=").append(URLEncoder.encode(request.getParameter(paramName), "UTF-8"));
		   } else {
			   Param.append("&").append(paramName).append("=").append(URLEncoder.encode(request.getParameter(paramName), "UTF-8"));
		   }
		   ++total;
		}
		}

url = url + "?"+ Param.toString();
System.out.println(url);
 if(url != null){
		// 使用GET方式向目的服务器发送请求
			try {
				java.net.URL connect = new java.net.URL(url.toString());
				java.net.URLConnection connection = connect.openConnection();
				connection.connect();
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line;
				String data=null;
				String token=null;
				while((line = reader.readLine()) != null){
					data=line;
				}
			
				JSONObject ojb = JSONObject.fromObject(data);
				token=ojb.getString("token");
				System.out.println("token="+token);
				session.setAttribute("token",token); 
				
				System.out.print("session.token="+session.getAttribute("token"));
				
				
				JSONObject jSONObject=new JSONObject();
				jSONObject.put("code", 0);
				jSONObject.put("codeName","登陆成功");
				
				out.println(jSONObject.toString());
				reader.close();
				
			} catch (Exception e) {
				// TODO: handle exception
			}
	
		}





%>