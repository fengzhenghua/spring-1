<%@ page contentType="text/html; charset=GBK" session="false"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<%@ page import="javax.servlet.http.Cookie"%>
<%
System.out.println("KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK");
response.setHeader("Pragma","No-cache");
response.setHeader("Cache-Control","no-cache");
response.setDateHeader("Expires",0);
//一般情况下调用本页面只清除客户端会话标示
Cookie[]   cookies=request.getCookies();
String csName=request.getParameter("cs_name");//从参数获得当前系统的jsessionid的name
if(null==csName||"".equals(csName)){//如果没有，使用默认值
	csName="jsessionid_uss";//该值应该与对应子系统在info_sub_sys表中的jsessionid_name字段一致
}
  //cookies不为空，则清除
  if(cookies!=null)
  {
          String   tempuid_1="";
          for(int   i=0;i<cookies.length;i++)
          {
              tempuid_1=cookies[i].getName();
              if(csName.equalsIgnoreCase(tempuid_1)){
              cookies[i].setMaxAge(0);
              response.addCookie(cookies[i]);
              }
          }
  }

//如果需要同步清除服务器端session的话，需要加request参数CSSC=1
if("1".equals(request.getParameter("CSSC"))){
//清除服务器端会话
try{
HttpSession hsession=request.getSession(false);
if(hsession!=null){
hsession.invalidate();
}else{
}
}catch(Exception e){
}
}
%>
