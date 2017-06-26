<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.tydic.unicom.util.DateUtil" %>
<%@ taglib prefix="t" uri="http://tydic.com/jsp/core" %>
<%@include file="../common/common.jsp"%>
<%@ page import="com.tydic.unicom.service.cache.service.interfaces.MemQueryServ" %>
<%@ page import="com.tydic.unicom.service.cache.po.CodeType" %>
<%@ page import="com.tydic.unicom.webUtil.SpringBean" %>
<%@ page import="com.tydic.unicom.service.cache.po.CodeList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.tydic.unicom.crm.uss.service.pub.interfaces.CodeListServDu" %>

<%
    //用于获取img_url
    MemQueryServ memQueryServ = SpringBean.getBean("MemQueryServ", MemQueryServ.class);
	String imgUrl="";
    CodeType imgUrlCode = memQueryServ.queryCodeType("code_type_url_type");
    List<CodeList> codeList = imgUrlCode.getCodeList();
	for (CodeList listw : codeList) {
		if ("img_download_url".equals(listw.getCode_name().trim())) {
			imgUrl=listw.getCode_id().trim();
		}
	}
%>



<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>产品活动维护</title>

<link href="<%=fullPath%>css/share1.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/card.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/shareNew.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/showOrder.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/public_pc.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/pre_accept.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" >
var imgUrlPath="<%=imgUrl%>";
var fullPath="<%=fullPath%>";
</script>
<script type="text/javascript" src="<%=fullPath%>js/productAndActivity.js"></script>

</head>
<body>
	<div name="reader_context" id="reader_context">
  		<div class="show">
		<div class="info">
			<!-- 第1层标题 -->
 			<div class="detail_white">
				<div class="title_tab" style="width:850px;">
					<div class="tab_menu"></div>                
					<div class="tab_menu" style="display: none;"><!-- 暂不开放 -->
						<a href="javascript:void(0);"  class="" id="product_manage">产品维护</a>
					</div>
					<div class="tab_menu">
						<a href="javascript:void(0);"  class="current" id="terminal_manage">终端维护</a><!-- 点击后是<ul id="step2">的内容显示 -->
					</div>         
				</div>    
				<div class="clear"></div> 
       		</div>
			
			<div id="step_terminal">
				<!-- 第2层标题 -->
				<div class="detail_white">
					<div class="line_red_top"></div>
					<div id ="select">
						<div class="handle">
							<div class="handle_btn_left handle_btn_left_clicked" id="commodity_up">
								<a href="javascript:void(0)" onclick="">商品上架</a>
							</div>
							<div class="handle_btn_right " id="commodity_down"><a href="javascript:void(0)">商品下架</a></div>
						</div>				
					</div>       
					<div class="clear"></div>
				</div>
			  
				<!-- 点击商品上架时显示——，第3层(默认显示)，含四小层    上左右下层-->
				<div class="info" id="content_up">
					<div class="detail_white">
						<!-- 1商品选择 -->
						<div class="detail_white" id="goodSelect"><!--style="display: none" height: 400px;-->
						<!-- 标题层 -->
						<div class="title_tab">        
							<span class="text_large24" id="title2" style="font-size:20px;color:#080808">1 商品选择</span>
						</div>
						
						<div class="padding_blank10"></div>
						<!-- 内容层 -->
						<div>
							<!-- 最左边垂直空白层 -->
							<div class="left_blank_vertical" style="float:left;display:block;width:30px;height:330px"></div>
							<!-- 左边，分三个内容层 -->
							<div class="leftClass" style="float:left;width:400px;">
								<!-- 选择上架活动 -->
								<div class="firstClass" style="height:150px;">
								   <div style="float:left;font-size:15px;color:#080808;font-weight:bold;width:150px">
										<span style="float:right;">请选择上架的活动</span>
								   </div>
								   <div style="float:left;margin-left:10px">
										<select id="activity_section" name="activity_section"  onchange="activityChange()" style="width: 155px">
											<option value=""></option>
										</select>
								   </div>
								</div>
								
								<!-- 选择上架终端-->
								<div class="secendClass" style="height:70px;">
								   <div style="float:left;font-size:15px;color:#080808;font-weight:bold;width:150px">
										<span style="float:right;">请选择上架的终端</span>
								   </div>
								   <div style="float:left;margin-left:10px">
										<select id="terminal_section" name="phone_section" onchange="activityChange();" style="width: 155px">
										   <option value="0">app\pc端</option>
										   <option value="1">自助机</option>
										   </select>
								   </div>
								</div>
								
								<!-- 选择上架自助机(默认灰色不可选)-->
								<div class="threeClass">
								   <div style="float:left;font-size:15px;color:#080808;font-weight:bold;">请选择自助机上架范围</div>
								   <div style="float:left;margin-left:10px">
										<select id="auto_section" name="auto_section" style="width: 155px" disabled="disabled" onchange="activityChange();">
										   <option value="" >本厅自助机</option>
										   <option value="*">省份自助机</option>
										</select>
								   </div>
								</div>
							</div>
						
							<!-- 右边的层  手机终端选择 -->
							<div class="rightClass" style="float:left;width: 400px;height:300px">
								<div style="float:left;font-size:15px;color:#080808;font-weight:bold;">
									<div>活动可配置手机终端</div>
									<div>（按首字母排序）</div>
								</div>
								<div id="phoneList" style="float:left;margin-left:10px;height:300px;width: 200px;border:1px solid #cccccc;overflow-y:auto; overflow-x:hidden">
									<ul id="phoneLis_select">
										<li class="list">
											<input type="checkbox" name="phoneList" value="1" disabled="disabled" style="font-size:15px;color:#cccccc;display: none;">
												<div style="display: none;"></div>
											</input>
										</li>
									</ul>
								</div>
								<div class="clear"></div>
							</div>
							<div class="clear"></div>
						</div>
				
						<!-- 下一步按钮 -->
						<div class="next_step">
							<div class="ok_disabled" id="step2_next"><a  href="javascript:void(0)">下一步</a></div>
						</div>
						<div class="clear"></div>
					</div>
					
						<!-- 2 商品信息确认 -->
						<div class="detail_white" id="goodOk" style="display: none"><!-- style="display: none" -->
							<!-- 标题层 -->
							<div class="title_tab">       
								<span class="text_large24" id="title2" style="font-size:20px;color:#080808">2 信息确认</span>
							</div>
							<div class="padding_blank10"></div>
							<!-- 主体内容层 -->
							<!-- <div>-->
								<!-- 最左边垂直空白层
								<div class="left_blank_vertical" style="float:left;display:block;width:30px;height:300px"></div>-->
								<!-- 显示已选信息 -->
								<div id="selectedMes"><!-- style="height: 500px;" -->
									<!-- 左边的层 -->
									<div class="leftClass" style="float:left;width:400px;">
										<!-- 已选择活动 -->
										<div class="firstClass" style="font-size:15px;color:#080808;height:50px;"><!-- style="height:150px;" -->
										   <div style="float:left;font-weight:bold;width:150px">
												<span style="float:right;">已选择活动:</span>
										   </div>
										   <div style="float:left;margin-left:10px">
										   		<input id="activitied" style="border:none"></input>
										   </div>
										</div>
										<!-- 已选择手机 -->
										<div style="font-size:15px;color:#080808;height:100px;"><!-- style="height:150px;" -->
										   <div style="float:left;font-weight:bold;width:150px">
												<span style="float:right;">已选择手机:</span>
										   </div>
										   <div style="float:left;margin-left:10px;margin-left:10px;height:150px;width: 150px;border:1px solid #cccccc;overflow-y:auto; overflow-x:hidden">
										   		<ul id="phoneed">
										   			<li>1.金立手机</li>
										   		</ul>
										   </div>
										</div>
									</div>
									<!-- 右边的层 -->
									<div class="rightClass" style="float:left;width: 400px;">
										<!-- 已选择终端 -->
										<div style="font-size:15px;color:#080808;height:50px;"><!-- style="height:150px;" -->
										   <div style="float:left;font-weight:bold;width:120px;">
												<span style="float:left;">已选择终端:</span>
										   </div>
										   <div style="float:left;margin-left:10px">
										   		<input id="terminaled" style="border:none"></input>
										   </div>
										</div>
										<!-- 自助机上架范围 -->
										<div id="autoRang" style="font-size:15px;color:#080808;display: none;"><!-- style="height:150px;display: none" -->
										   <div style="float:left;font-weight:bold;width:120px">
												<span style="float:left;">自助机上架范围:</span>
										   </div>
										   <div style="float:left;margin-left:10px;">
										   		<input id="autoed" style="border:none"></input>
										   </div>
										</div>
									</div>
									<div class="clear"></div>
								</div>
								<div class="padding_blank10"></div>
								<!-- 机型展示及快速办理所配置的内容，显示手机图片 -->
								<div style="margin-left: 30px;">
									<div style="font-size:15px;color:#080808;font-weight:bold;">机型展示及快速办理所配置的内容：</div>
									<div style="height:250px;width:800px;border:1px solid #cccccc;overflow-y:hidden; overflow-x:auto">
										<table>
											<tr id="phoneImgShow">
												<td style="width:380px;">
													<div style="float:left;border-left: 1px dashed #080808;width:380px;">
														<!-- 图片显示 -->
														<div class="img" style="float:left;height:200px;width:100px;margin:5px 5px 5px 10px;border:1px solid #cccccc">
															<div><img id="img" src="" style="height:190px;width:90px;" /></div>
															<div style="float:left;"><span>机型名称</span></div>
														</div>
														<!-- 内容显示 -->
														<div class="content" style="float: left;margin:0 10px 0 10px;width:300px;">
															<div style="font-size:14px;color:#080808;font-weight:bold;">活动描述：</div>
															<div id="desc" style="height:100px;width:200px;border:1px solid #cccccc;">具体内容显示</div>
															<div style="font-size:14px;color:#080808;font-weight:bold;">添加个性说明：</div>
															<textarea name="personDesc" style="width:100px;height:30px;">这里写内容</textarea>
														</div>
														<div class="clear"></div>
													</div>
												</td>
											</tr>	
										</table>
									</div>
									<div class="clear"></div>
								</div>
								
								<div class="padding_blank10"></div>
								<!-- 确定按钮 -->
								<div class="next_step">
									<div class="ok"><a id="step2" href="javascript:void(0)">完成</a></div>
								</div>
							<!-- </div>-->
							
							<div class="clear"></div>
						</div>
					
					</div>
				<div class="clear"></div>
			</div>
		
		<!-- 点击商品下架时显示，第3层(默认显示)，含四小层    上左右下层-->
		<div class="info" id="content_down" style="display: none">
			<div class="detail_white">
				<!-- 标题层 -->
				<div class="title_tab">        
					<span class="text_large24" id="title2" style="font-size:20px;color:#080808">下架处理</span>
				</div>
				<div class="padding_blank10"></div>
					<!-- 主体内容层 -->
					<!-- <div>-->
					<!-- 显示已选择的上架信息 -->
					<div id="selectedUpMes"><!-- style="height: 500px;" -->
						<!-- 左边的层 -->
						<div class="leftClass" style="float:left;"><!-- width:450px; -->
							<!-- 需下架活动 -->
							<div class="firstClass" style="font-size:15px;color:#080808;"><!-- style="height:150px;" -->
								<div style="float:left;font-weight:bold;margin-left:30px;">
									<span style="float:right;">请选择需要下架的活动:</span>
								</div>
								<div id="activity_Down" style="float:left;margin-left:10px;font-size:12px;height:120px;width: 250px;border:1px solid #cccccc;overflow-y:auto; overflow-x:hidden">
									<ul id="activityDown">
										<li class="list" style="font-size:15px;">
											<input name="downActed" type="radio" value="" style="display: none;"></input><!-- 单选按钮 -->
										</li>
									</ul>	
								</div>
								<div class="clear"></div>
							</div>
							<div class="padding_blank10"></div>
							<!-- 需下架终端 -->
							<div style="font-size:15px;color:#080808;margin-left:30px;"><!-- style="height:150px;" -->
							   <div style="float:left;font-weight:bold;">
									<span style="float:right;">请选择需要下架的终端:</span>
							   </div>
							   <div style="float:left;margin-left:10px;height:50px;width: 250px;border:1px solid #cccccc;overflow-y:auto; overflow-x:hidden">
							   		<ul id="downTerm">
							   			<li>
							   				<input id="app0" name="downTer" type="radio" value="0"  checked="checked" onclick="actTerRadio();">app\pc端</input>
							   			</li>
							   			<li>
							   				<input id="app1" name="downTer" type="radio" value="1" onclick="actTerRadio();">自助机</input>
							   			</li>
							   		</ul>
							   </div>
							   <div class="clear"></div>
							</div>
							
							<!-- 选择自助机下架范围 -->
							<div id="downAuto" style="font-size:15px;color:#080808;margin:10px 0 5px 30px;display: none;"><!-- style="height:150px;" -->
							   <div style="float:left;font-weight:bold;">
									<span style="float:right;">请选择自助机下架范围:</span>
							   </div>
							   <div style="float:left;margin-left:10px;height:50px;width: 250px;border:1px solid #cccccc;overflow-y:auto; overflow-x:hidden;">
							   		<ul id="downAutoContent">
							   			<li>
							   				<input id="downAutoLocal" name="downAuto" type="radio" value=""  checked="checked" onclick="actTerRadio();">本厅自助机</input>
							   			</li>
							   			<li>
							   				<input name="downAuto" type="radio" value="*" onclick="actTerRadio();">省份自助机</input>
							   			</li>
							   		</ul>
							   </div>
							   <div class="clear"></div>
							</div>
							<div class="clear"></div>
						</div>
						<!-- 右边的层 -->
						<div class="rightClass" style="float:left;margin-left:30px;">
							<!-- 需下架的机型 -->
						   <div style="float:left;font-size:15px;color:#080808;font-weight:bold;">
								<div>需下架的机型</div>
								<div>（按首字母排序）</div>
						   </div>
						   <div id="downPhoneList" style="float:left;margin-left:10px;height:230px;width: 250px;border:1px solid #cccccc;overflow-y:auto; overflow-x:auto">
								<ul id="phoneedDown" style="font-size:15px;">
									<li class="list">
										<input type="checkbox" name="downPhoneList" value="1" onclick="" style="display: none;">
											
										</input>
									</li>
								</ul>
							</div>
							<div class="clear"></div>
						</div>
						<div class="clear"></div>
					</div>
					<div class="clear"></div>
					</div>
					<div class="padding_blank10"></div>
					<!-- 机型展示及快速办理所配置的内容，显示手机图片 -->
					<div style="margin-left: 50px;">
						<div style="font-size:10px;color:red;font-weight:bold;font-family:微软雅黑,宋体;">勾选右上方的勾后下方的机型打岔显示</div>
						<div style="height:250px;width:830px;border:1px solid #cccccc;overflow-y:hidden; overflow-x:auto">
							<table>
								<tr id="phoneImgDownShow">
									<td style="width:380px;">
										<div style="float:left;border-left: 1px dashed #080808;width:380px;">
											<!-- 图片显示 -->
											<div class="img" style="float:left;height:200px;width:100px;margin:5px 5px 5px 10px;border:1px solid #cccccc;display: none;">
												<div><img id="img" src="" style="height:190px;width:90px;" /></div>
												<div style="float:left;"><span>机型名称</span></div>
											</div>
											<!-- 内容显示 -->
											<div class="content" style="float: left;margin:0 10px 0 10px;width:300px;display: none;">
												<div style="font-size:14px;color:#080808;font-weight:bold;">活动描述：</div>
												<div id="desc" style="height:100px;width:200px;border:1px solid #cccccc;">具体内容显示</div>
												<div style="font-size:14px;color:#080808;font-weight:bold;">添加个性说明：</div>
												<textarea name="personDesc" style="width:100px;height:30px;">这里写内容</textarea>
											</div>
											<div class="clear"></div>
											<img id="coverImg" src="<%=fullPath%>images/delete_ql.png" style="z-index: 3; position: absolute; top: 0px;display: none;"/>
										</div>
									</td>
								</tr>	
							</table>
						</div>
						<div class="clear"></div>
					</div>
					
					<div class="padding_blank10"></div>
						<!-- 完成按钮 -->
						<div class="next_step">
						<div class="ok"><a id="step" href="javascript:void(0)" onclick="downDeal();">完成</a></div>
					</div>
						<!-- </div>-->
						
					<div class="clear"></div>
				</div>
				
			<div class="clear"></div>
		</div><!-- 商品下架end -->
			
		</div>
		</div>
		</div>
	 </div>
	</div>
  	
	<!-- 遮盖层 -->
	<div class="bg_mask" id="bg_mask"></div>
	<!-- 弹出窗口 商品上架点击"下一步"的提示 begin -->
	<div class="pop_win" id="next1" style="display:none;border:1px solid #d5d5d5;width: 180px;">
   		<div class="padding_blank"></div>
   		<div class="bold" style="text-align:center;">是否确认？</div>
   		<div class="padding_blank"></div>
   		<div class="input_button" id="next1_ok" style="float: left;margin-left: 10px;">确 定</div></a> 
    	<div class="input_button" id="next1_cancel" style="float: left;margin-left: 10px;">返回</div></a>
    </div>
	<!-- 弹出窗口 商品上架点击"下一步"的提示 end -->
	
	<!-- 弹出窗口 商品上架点击"完成"的提示 begin  -->
	<div class="pop_win" id="next2" style="display:none;border:1px solid #d5d5d5;width: 180px;">
   		<div class="padding_blank"></div>
   		<div class="bold" style="text-align:center;width: 170px;">处理成功</div>
   		<div class="padding_blank"></div>
   		<div class="input_button" id="up_ok" style="margin-left: 50px">确 定</div></a> 
    </div>
	<!-- 弹出窗口 商品上架点击"完成"的提示 end -->
	
	<!-- 弹出窗口 商品下架点击"完成"的提示 begin -->
	<div class="pop_win" id="next_down" style="display:none;border:1px solid #d5d5d5;width: 180px;">
   		<div class="padding_blank"></div>
   		<div class="bold" style="text-align:center;width: 170px;">处理成功</div>
   		<div class="padding_blank"></div>
   		<div class="input_button" id="down_ok" style="margin-left: 50px">确 定</div></a>
    </div>
	<!-- 弹出窗口 商品下架点击"完成"的提示 end -->
	 
	
	
	<input type="hidden" id="province_code"  value="${province_code}"/><!--省份标识 --> 
	<input type="hidden" id="write_way"  value="${write_way}"/><!--模拟写卡标识 --> 
	<input type="hidden" id="wt_flag"  value="${wt_flag}"/><!--协同标识 --> 
	<input type="hidden" id="ori_oper_no"  value="${ori_oper_no}"/><!--原生操作员 -->
	
</body>
</html>