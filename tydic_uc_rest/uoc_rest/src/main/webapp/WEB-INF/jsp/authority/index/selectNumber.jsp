<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>销售 - 选号</title>
<link href="<%=fullPath%>css/selectNumber.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=fullPath%>js/selectNumber.js"></script>
<script type="text/javascript">
//号码预占
function numOccupy(params){
//	   alert("numoccupy");
//	   alert("num:"+params);
	//   alert("11111:"+$.trim( $("#12").text() ));
	   var num = params.split("@")[0];
	   var fee = params.split("@")[1];
	   var teleType = params.split("@")[2];
	   var good_type = params.split("@")[4];
	   var ser_type = params.split("@")[5];
	  // var douFee = parseFloat(fee) * 10;
	  // alert(":::"+douFee);
	   // var ofrSubType = params.split("@")[3];
	   //alert("num:"+num+"fee:"+fee+"teleType:"+teleType+"good_type:"+good_type+"ser_type:"+ser_type);
	   var essKey=$("#essKey").val();
	   var ofrSubType = $("#ofr_sub_type_3g").val();
	   var URL = application.fullPath + 'authority/selectNumber/numberOccupy';
	   $.ajax({
			url:URL,
			data:{"acc_nbr":num,"tele_type":teleType,"essKey":essKey,"ofr_sub_type_3g":ofrSubType,"ser_type":ser_type},
			dataType:'json',
			type:'post',
			waitMsg:"正在预占号码！",
//			error:function(message){
//				alert(message.content+"!");
//			}
			success:function(message){
			 if(teleType=='3G'||teleType=='4G'){
				//var BackData='';//-----------message.args.Back;
				
				if("" == message.args.occupySuccess || message.args.occupySuccess == null){
					$.alert(message.args.occupyError);
				 }else{
					 window.location.href=application.fullPath+"authority/sale/saleSelectedCode?acc_nbr="+num+"&fee="+fee+"&tele_type="+teleType+"&essKey="+essKey+"&ofr_sub_type_3g="+ofrSubType+"&pay_flag="+ser_type;	 
				 }
			}else if(teleType=='2G'){
					// alert("message args"+ message.args.occupySuccess);
				if("" == message.args.occupySuccess || message.args.occupySuccess == null){
						$.alert(message.args.occupyError);
				}else{
					window.location.href= application.fullPath+"authority/sale/saleSelectedCode?acc_nbr="+num+"&fee="+fee+"&tele_type="+teleType+"&good_type="+good_type+"&ofr_sub_type_3g="+ofrSubType+"&pay_flag="+ser_type;
				}
				}
			}
		});
 }
</script>
</head>
<body>
<input type="hidden"  id="essKey"  value="${essKey}"/>
<input type="hidden"  id="ofr_sub_type_3g"  value="${ofr_sub_type_3g}"/>
<input type="hidden" id = "teleTypeId" value="${tele_type}" />
<input type="hidden" id = "goodTypeId" value="${good_type}" />
<input type="hidden" id = "serTypeId" value="${ser_type}" />
<div class="crumbs">当前位置: 销售(${tele_type}) > 选号</div>	
<div class="sale_content">	

<div class="right">
<div class="select_code">
	<div class="search_blur"><!--<input type="text" class="s" value="模糊查询" /><a href="#" class="s_btn"></a>-->过滤条件</div>		
	<dl class="define">
		<dt>模糊查询：</dt>
		<dd>
			<dl class="search_code">
				<dt><input type="text" class="text" id="fuzzyQueryValue"  value="${fuzzy_query}" /></dt>
				<dd style="width:68px;"><input id="search_num" type="text" value="搜索" class="btn" /></dd>
				<!-- <input id="lastNumBox" type="checkbox" checked="true" class="div3G"><span class="div3G">尾号选号</span> -->
			</dl>
		</dd>
		<div class="clear"></div>
	</dl>

		
		
<!-- 		<dl>
				<dt>城　　市：</dt>
				<dd><a href="#" class="over">北京</a></dd>
				<div class="clear"></div>
			</dl>	-->	
			<dl id="teleType" style="display:none">
				<dt>电信类型：</dt>
				<dd>
					<t:li codeType="tele_type"></t:li>
					<!-- <a href="#" class="over" id="tele2G">2G</a>
					<a href="#" class="" id="tele3G">3G</a> -->
				</dd>
				<div class="clear"></div>
			</dl>	
			<dl id="serType" >
				<dt>受理类型：</dt>
				<dd>
					 <t:li codeType="ser_type"  checkIndex="1" checkClass="over"></t:li>
				</dd>
				<div class="clear"></div>
			</dl>	
			<dl id="goodType" class="div2G">
				<dt>号码类型：</dt>
				<dd>
					 <t:li codeType="good_type" checkIndex="0" checkClass="over"></t:li>
				</dd>
				<div class="clear"></div>
			</dl>
			<dl id="mobSection" class="div2G">
				<dt>号　　段：</dt>
				<dd>
					 <t:li codeType="mob_section" ></t:li>
				</dd>
				<div class="clear"></div>
			</dl>
			<dl id="mobSection3G" class="div3G">
				<dt>号　　段：</dt>
				<dd>
					 <t:li codeType="mob_section" ></t:li>
				</dd>
				<div class="clear"></div>
			</dl>
			
			<dl  id="priceRange" class="priceRangeDiv" style="display:none">
				<dt>预存话费：</dt>
				<dd>
					<t:li codeType="price_range2G" ></t:li>
					<!-- <a href="#">0-100</a><a href="#">100-300</a><a href="#">300-500</a><a href="#">500-1000</a><a href="#">1000-5000</a><a href="#">5000-10000</a><a href="#">10000以上</a> -->
				</dd>
				<div class="clear"></div>
			</dl>
			
		<!-- 	<dl id="mobSection3G" class="div3G">
				<dt>号　　段3G：</dt>
				<dd>
					 <t:li codeType="mob_section_3G" ></t:li>
				</dd>
				<div class="clear"></div>
			</dl>	
		 -->
			<dl id="numberKind"  class="div3G" >
				<dt>靓号类型：</dt>
				<dd>
					<t:li codeType="number_kind" ></t:li>
				</dd>
				<div class="clear"></div>
			</dl  class="div3G">
			
			
			
		</div>
		<div class="selected" >
			<dl>
				<dt >您已经选择：</dt>
				<dd>
				  <!-- <a href="#" class="selectedShow" >北京</a> -->
				   <a href="#" class="selectedTeleType no_padding" id="l2" >${tele_type}</a>
				   <a href="#" class="selectedGoodType no_padding" id="l6" >${goodType}</a> 
				   <a href="#" class="selectedSerType no_padding" id="l7" >${serType}</a>
				   <a href="#" class="selectedShowNumber" id="l3" style="display: none">${mob_section}<span>&nbsp;</span></a>
				   <a href="#" class="selectedShowType" id="l5" style="display: none">${perrty_type}<span>&nbsp;</span></a>
				   <a href="#" class="selectedShowMoney" id="l4" style="display: none">${price_range}<span>&nbsp;</span></a>
				   <a href="#" class="clear_condition">清除筛选条件</a>
				  </dd>
				<div class="clear"></div>
			</dl>
		</div>
		<div class="mobile_code">
			<!--
			<table width="998" border="0" cellspacing="0" cellpadding="0">
			  <tr>
				<td width="54" height="43" align="right">排序：</td>
				<td width="350">
				<ul class="sort">
					<li><a href="#">号码升序</a></li>
					<li><a href="#" class="hover">号码降序</a></li>
					<li><a href="#">价格升序</a></li>
					<li><a href="#">价格降序</a></li>
					<div class="clear"></div>
				</ul>
				</td>
				<td style="padding-right:20px;">
				<ul class="page">
					<li class="next"><a href="#"></a></li>
					<li class="num"><span id="this_page">0</span>/<span id="total_page">0</span></li>
					<li class="pre"><a href="#"></a></li>
					<li class="intro">相关商品<span id="totalSize">0</span>款</li>
				</ul>
				</td>
			  </tr>
		  </table>
		-->
			<table width="998" border="0" cellpadding="0" cellspacing="1" bgcolor="#FFFFFF" id="order_head">
			  <tr>
				<td width="231" height="33" align="center" bgcolor="#E1E1E1">号码</td>
				<td width="150" align="center" bgcolor="#E1E1E1">预存话费</td>
				<td width="118" align="center" bgcolor="#E1E1E1">订购</td>
				<td width="231" align="center" bgcolor="#E1E1E1">号码</td>
				<td width="150" align="center" bgcolor="#E1E1E1">预存话费</td>
				<td align="center" bgcolor="#E1E1E1">订购</td>
			  </tr>
		  </table>
		  <div id="showData" ></div>
		  <div id="show3GData" ></div>
			<div class="warp_order">
				<div class="clear"></div>
				<div class="pages" id="layerPaging"></div>		
			</div>
		</div>
	</div>
</div>
</body>
</html>
