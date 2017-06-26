<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
<%
String basePath= "http://135.24.252.29:8080/tydic_webBase/";
String fullPath= request.getScheme()+ "://"+ request.getServerName()+ ":"+ request.getServerPort()+ request.getContextPath()+ "/";
%>
<script type="text/javascript" src="<%=basePath%>statics/js/jquerylib/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>statics/js/jquerylib/jqueryui/jquery-ui-1.10.4.custom.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/grid.locale-cn.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jquery.jqGrid.src.js"></script>
<script type="text/javascript" src="<%=basePath%>js/tydic.jqgrid.js"></script>
<script type="text/javascript" src="<%=basePath%>js/tydic.dialog.js"></script>
<script type="text/javascript" src="<%=basePath%>js/tydic.ajax.js"></script>
<script type="text/javascript" src="<%=basePath%>js/tydic.easing.js"></script>
 <script type="text/javascript" src="<%=basePath%>js/jquery.Pages.js"></script>
<script type="text/javascript" src="<%=basePath%>js/tydic.Pages.js"></script> 
<script type="text/javascript" src="<%=basePath%>js/tydic.accordion.js"></script>
<script type="text/javascript" src="<%=basePath%>js/tydic.validate.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jquery.validate.js"></script>
<script type="text/javascript" src="<%=basePath%>js/datePicker/WdatePicker.js"></script><head>
<script src="<%=fullPath%>js/json2.js" type="text/javascript"></script> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>统一销售系统</title>
<style type="text/css" media="screen">
  .center{
   text-align: center;
   padding-top: 250px;
  }
 </style>
 <script type="text/javascript">
  var cust_care_id = '<%=request.getParameter("cust_care_id")%>';
  function page_onload(){
  	//document.getElementById('cust_care_id').value=cust_care_id;
  	document.getElementById("cust_care_id").innerText = cust_care_id;
	var fullPath1='<%=fullPath %>';
	var dataParam={"cust_care_id":cust_care_id,"inc_flag":"wz"};
	$.ajax({  
        loading: '正在加载中...',  
        type: "POST",
        dataType: "json",
        url: fullPath1+"csrest/custRsp/qryCustRspDetail",  
        async: false,
        data: dataParam,  
        success: function (msg,status) {
        	/* document.getElementById('link_name').value=msg.args.custrsp_order.link_name;
        	document.getElementById('link_phone').value=msg.args.custrsp_order.link_phone;
        	document.getElementById('oper_date').value=msg.args.custrsp_order.oper_date;
        	document.getElementById('oper_id').value=msg.args.custrsp_order.oper_id;
        	document.getElementById('oper_phone').value=msg.args.custrsp_order.oper_phone;
        	document.getElementById('oper_name').value=msg.args.custrsp_order.oper_name;
        	document.getElementById('ofr_code').value=msg.args.custrsp_order.ofr_code;
        	document.getElementById('ofr_name').value=msg.args.custrsp_order.ofr_name;
        	document.getElementById('id_number').value=msg.args.custrsp_order.id_number; */
        	
        	document.getElementById("link_name").innerText = msg.args.custrsp_order.link_name;
        	document.getElementById("link_phone").innerText = msg.args.custrsp_order.link_phone;
        	document.getElementById("oper_date").innerText = msg.args.custrsp_order.oper_date;
        	document.getElementById("oper_id").innerText = msg.args.custrsp_order.oper_id;
        	document.getElementById("oper_phone").innerText = msg.args.custrsp_order.oper_phone;
        	document.getElementById("oper_name").innerText = msg.args.custrsp_order.oper_name;
        	var ofr_code=msg.args.custrsp_order.ofr_code;
        	if(ofr_code!=null&&ofr_code!=""){
        		document.getElementById("ofr_code").innerText = msg.args.custrsp_order.ofr_code;
        	}
        	
        	var ofr_name=msg.args.custrsp_order.ofr_name;
        	if(ofr_name!=null&&ofr_name!=""){
        		document.getElementById("ofr_name").innerText = msg.args.custrsp_order.ofr_name;
        	}
        	document.getElementById("id_number").innerText = msg.args.custrsp_order.id_number;

        	//document.getElementById('cust_addr').value=msg.args.custrsp_order.cust_addr;
        	document.getElementById("cust_addr").innerText = msg.args.custrsp_order.cust_addr;
        	/* document.getElementById('cust_name').value=msg.args.custrsp_order.cust_name;
        	document.getElementById('c_sel_date').value=msg.args.custrsp_order.c_sel_date;
        	document.getElementById('develop_id').value=msg.args.custrsp_order.develop_id;
        	document.getElementById('develop_name').value=msg.args.custrsp_order.develop_name;
        	document.getElementById('develop_phone').value=msg.args.custrsp_order.develop_phone;
        	document.getElementById('charge_inc').value=msg.args.custrsp_order.charge_inc;
        	document.getElementById('charge_pre').value=msg.args.custrsp_order.charge_pre;
        	document.getElementById('addr_memo').value=msg.args.custrsp_order.addr_memo;
        	document.getElementById('addr_desc').value=msg.args.custrsp_order.addr_desc; */
        	document.getElementById("cust_name").innerText = msg.args.custrsp_order.cust_name;
        	
        	var c_sel_date=msg.args.custrsp_order.c_sel_date;
        	if(c_sel_date!=null&&c_sel_date!=""){
        		document.getElementById("c_sel_date").innerText = msg.args.custrsp_order.c_sel_date;
        	}
        	var develop_id=msg.args.custrsp_order.develop_id;
        	if(develop_id!=null&&develop_id!=""){
        		document.getElementById("develop_id").innerText = msg.args.custrsp_order.develop_id;
        	}
        	
        	var develop_name=msg.args.custrsp_order.develop_name;
        	if(develop_name!=null&&develop_name!=""){
        		document.getElementById("develop_name").innerText = msg.args.custrsp_order.develop_name;
        	}
        	
        	var develop_phone=msg.args.custrsp_order.develop_phone;
        	if(develop_phone!=null&&develop_phone!=""){
        		document.getElementById("develop_phone").innerText = msg.args.custrsp_order.develop_phone;
        	}
        	
        	var charge_inc=msg.args.custrsp_order.charge_inc;
        	if(charge_inc!=null&&charge_inc!=""){
        		document.getElementById("charge_inc").innerText = msg.args.custrsp_order.charge_inc;
        	}
        	
        	var charge_pre=msg.args.custrsp_order.charge_pre;
        	if(charge_pre!=null&&charge_pre!=""){
        		document.getElementById("charge_pre").innerText = msg.args.custrsp_order.charge_pre;
        	}
        	
        	var addr_memo=msg.args.custrsp_order.addr_memo;
        	if(addr_memo!=null&&addr_memo!=""){
        		document.getElementById("addr_memo").innerText = msg.args.custrsp_order.addr_memo;
        	}
        	
        	var addr_desc=msg.args.custrsp_order.addr_desc;
        	if(addr_desc!=null&&addr_desc!=""){
        		document.getElementById("addr_desc").innerText = msg.args.custrsp_order.addr_desc;
        	}
        	$("#pic_x").attr("src","data:image/jpg;base64,"+msg.args.custrsp_order.pic_x);
        	$("#pic_y").attr("src","data:image/jpg;base64,"+msg.args.custrsp_order.pic_y);
        	$("#pic_z").attr("src","data:image/jpg;base64,"+msg.args.custrsp_order.pic_z);
        },  

        error: function (msg,status) {  
        	alert('查询订单详情失败');
        	
        }  
    }); 
  }

</script>
</head>
<body onload="page_onload()">
  <!-- <OBJECT  id=WebBrowser  classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2  height=0  width=0 VIEWASTEXT> 
  </OBJECT>  -->
<form id="fromdata"  name="fromdata" method="post" action="<%=fullPath%>authority/ReportCs/queryInfoCustCareOrder">
  <table  border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
		<table  border="0" align="center" cellpadding="0" cellspacing="0">
          	<tr >
              <td width="15%"  height="24" align="right"><strong>订单编号：</strong></td>
              <td width="35%" align="left"><span  id="cust_care_id" /></td>
 			  <td width="15%"  height="24" align="right"><strong>操作时间：</strong></td>
              <td width="35%" align="left"><span  id="oper_date"  /></td>           
			</tr>
			<tr>
              <td  height="24" align="right"><strong>操作员工号：</strong></td>
              <td  align="left"><span  id="oper_id"   /></td>
              <td  height="24" align="right"><strong>操作员名称：</strong></td>
              <td  align="left"><span  id="oper_name"  /></td>
             </tr>
            <tr> 
              <td   height="24" align="right"><strong>操作员电话：</strong></td>
              <td   align="left"><span  id="oper_phone"   /></td>
              <td   width="150"  height="24" align="right"><strong>发展人工号：</strong></td>
              <td   align="left"><span  id="develop_id"   /></td>
            </tr>
            <tr>   
              <td   height="24" align="right"><strong>发展人名称：</strong></td>
              <td   align="left"><span  id="develop_name"  /></td>
              <td   style="display:none" height="24" align="right"><strong>发展人电话：</strong></td>
              <td   style="display:none" align="left"><span  id="develop_phone"  /></td>
            </tr> 
            <tr>
              <td   height="24" align="right"><strong>产品编号：</strong></td>
              <td   align="left"><span  id="ofr_code"  /></td>
              <td   height="24" align="right"><strong>产品名称：</strong></td>
              <td   align="left"><span  id="ofr_name"  /></td>
           </tr>
           <tr>
              <td  height="24" align="right"><strong>接入费：</strong></td>
              <td  align="left"><span  id="charge_inc"  /></td>
              <td  height="24" align="right"><strong>长期预存款：</strong></td>
              <td  align="left"><span  id="charge_pre"  /></td>
            </tr>
           <tr>
              <td   height="24" align="right"><strong>联系人名称：</strong></td>
              <td   align="left"><span  id="link_name"    /></td>
              <td   height="24" align="right"><strong>联系人电话：</strong></td>
              <td   align="left"><span  id="link_phone"    /></td>
            </tr>
            <tr>
              <td   height="24" align="right"><strong>客户名称：</strong></td>
              <td   align="left"><span  id="cust_name"   /></td>
              <td   height="24" align="right"><strong>证件失效时间：</strong></td>
              <td   align="left"><span   id="c_sel_date"   /></td>
            </tr>
            <tr>
              <td   height="24" align="right"><strong>证件号码：</strong></td>
              <td   align="left"><span  id="id_number"   /></td>
           	  <td   height="22" align="right"><strong>证件地址：</strong></td>
              <td   align="left" ><span  id="cust_addr"  /></td>
            </tr>
            <tr>
              <td    height="22" align="right"><strong>装机地址：</strong></td>
              <td   align="left"><span  id="addr_desc"   /></td>
              <td   height="22" align="right"><strong>标准地址：</strong></td>
              <td   align="left"><span  id="addr_memo"  /></td>
            </tr>
          <!--   <tr>
              <td     align="center"><img  id="pic_x"  width="260" /></td>
              <td      align="center"><img  id="pic_y"  width="260"/></td>
              <td     align="center"><img  id="pic_z"  width="260" /></td>
              <td   ></td>
            </tr> -->
        </table>
       </tr>
       <tr>
       <table  border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
              <td     align="center"><img  id="pic_x"  width="260" /></td>
              <td      align="center"><img  id="pic_y"  width="260"/></td>
              <td     align="center"><img  id="pic_z"  width="260" /></td>
            </tr>
        </table>
       </tr>
    </table>


<!-- 	
<input type="button" value="打印"     onclick="javascript:window.print()" class="NOPRINT"/> 

	
<input type="button" value="直接打印 "  onclick="document.all.WebBrowser.ExecWB(6,6)" class="NOPRINT"/> 

	
<input type="button" value="页面设置"  onclick="document.all.WebBrowser.ExecWB(8,1)" class="NOPRINT"/> 

	
<input type="button" value="打印预览"  onclick="document.all.WebBrowser.ExecWB(7,1)" class="NOPRINT"/>   -->
</form>
</body>
</html>