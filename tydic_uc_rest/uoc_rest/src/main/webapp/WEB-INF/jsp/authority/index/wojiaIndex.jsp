<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.tydic.unicom.util.DateUtil"%>
<%@ taglib prefix="t" uri="http://tydic.com/jsp/core"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>智慧沃家开户</title>

<link href="<%=fullPath%>css/shareNew.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/card.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/showOrder.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/public_pc.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/pre_accept.css" rel="stylesheet" type="text/css" />
<link href="<%=fullPath%>css/wojiaIndex.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=fullPath%>js/wojiaIndex.js"></script>
<script type="text/javascript" src="<%=fullPath%>js/writeCard.js"></script>
<script type="text/javascript" src="<%=fullPath%>js/posPayFee.js"></script>
<!--[if lt IE 9]>
    <script type="text/javascript" src="<%=fullPath%>js/json2.js"></script>
<![endif]-->

</head>
<body>
	<object classid="clsid:10946843-7507-44FE-ACE8-2B3483D179B7" id="CVR_IDCard" name="CVR_IDCard" width="0" height="0"></object>
	<div name="reader_context" id="reader_context">
		<div class="show" style="height:auto;">
			<div class="handle_wojia" id="tab_taocan">
				<div class="handle_btn_left handle_btn_left_clicked" id="">
					<a href="javascript:void(0)">共享套餐</a>
				</div>
				<div class="handle_btn_right " id="">
					<a href="javascript:void(0)">组合套餐</a>
				</div>				
			</div>
			<ul id="step1">
				<div class="info">
					<div class="title text_big">
						<span class="text_large24">1</span>校验用户资料
					</div>
					<ul class="detail">
						<li class="list">
							<div class="left">
								<div class="left_lable">证件类型：</div>
								<div class="left_data">身份证</div>
							</div>
							<div class="right">
								<div class="left_lable">证件号码：</div>
								<div class="left_data">
									<input type="text" id="id_number" placeholder="" readOnly="true"/>
								</div>
								<a href="javascript:void(0);"><div class="input_button" id="btn_load">读 取</div></a>
								<a href="javascript:void(0);"><div class="input_button" id="btn_load_test">模拟读取</div></a>
							</div>
						</li>
						<li class="list">
							<div class="left">
								<div class="left_lable">证件名称：</div>
								<div class="left_data" id="customer_name"></div>
							</div>
							<div class="right">
								<div class="left_lable">证件地址：</div>
								<div class="left_data" id="auth_adress"></div>
							</div>
						</li>
						<li class="list">
							<div class="left">
								<div class="left_lable">联系电话：</div>
								<div class="left_data">
									<input type="text" placeholder="请输入联系电话" id="connect_phone"/>
								</div>
							</div>
							<div class="right">
								<div class="left_lable">联系地址：</div>
								<div class="left_data">
									<input type="text" placeholder="请输入联系地址" id="connect_addr" class="width_32"/>
								</div>
							</div>
						</li>
						<li class="list">
							<div class="line_dashed_h"></div>
						</li>
						<li class="list text_big">
							<div class="list_title">本人身份证：</div>
						</li>
						<li>
							<table width="938" border="0" align="center" cellpadding="0" cellspacing="0" class="auto">
								<tr>
									<td width="468" align="center" style="border-right: 1px dashed #CCC;">
										<table width="340" border="0" align="center" cellpadding="0" cellspacing="0" class="auto">
											<tr>
												<td height="216" valign="top" class="card_bg_front">
													<table width="340" border="0" cellspacing="0" cellpadding="0">
														<tr>
															<td height="24"></td>
														</tr>
													</table>
													<table width="340" border="0" cellspacing="0" cellpadding="0">
														<tr>
															<td width="55" height="26"><div style="width: 55px;"></div></td>
															<td width="81" height="26" align="left" id="bg_card_name"></td>
															<td width="68" height="26"></td>
															<td colspan="2" rowspan="4" valign="top" width="136" align="left">
																<img src="<%=fullPath%>images/photo.png" width="94" height="118" class="card_img" id="idCradImage" />
															</td>
														</tr>
														<tr>
															<td width="55" height="26"></td>
															<td height="26" align="left" id="bg_card_sex"></td>
															<td height="26" align="left" id="bg_card_nation"></td>
														</tr>
														<tr>
															<td width="55" height="26"></td>
															<td height="26" colspan="2">
																<table width="149" border="0" cellpadding="0" cellspacing="0">
																	<tr>
																		<td width="57" align="left" id="bg_card_born_year"></td>
																		<td width="33" align="left" id="bg_card_born_month"></td>
																		<td width="59" align="left" id="bg_card_born_day"></td>
																	</tr>
																</table>
															</td>
														</tr>
														<tr>
															<td width="55"></td>
															<td height="61" colspan="2" valign="top" class="card_addr">
																<div style="width: 149px; height: 64px; overflow: hidden;" id="bg_card_born_addrss"></div>
															</td>
														</tr>
													</table>
													<table width="340" border="0" cellspacing="0" cellpadding="0">
														<tr>
															<td width="110" height="26">&nbsp;</td>
															<td width="230" align="left" id="bg_card_born_id_number"></td>
														</tr>
													</table>
													<table width="340" border="0" cellspacing="0" cellpadding="0">
														<tr>
															<td>&nbsp;</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</td>
									<td width="468" align="center">
										<table width="340" border="0" align="center" cellpadding="0" cellspacing="0" class="auto">
											<tr>
												<td height="216" valign="top" class="card_bg_reverse">
													<table width="340" border="0" cellspacing="0" cellpadding="0">
														<tr>
															<td height="148">&nbsp;</td>
														</tr>
													</table>
													<table width="340" border="0" cellspacing="0" cellpadding="0">
														<tr>
															<td width="135" height="30">&nbsp;</td>
															<td width="205" align="left" id="bg_card_idcard_addr"></td>
														</tr>
														<tr>
															<td height="26">&nbsp;</td>
															<td align="left" id="bg_card_valid"></td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</li>
						<li class="list"></li>
					</ul>
					<div class="padding_blank"></div>
					<div class="text_large">
						<div class="ok_disabled" id="step1_next_div">
							<a id="step1_next" href="javascript:void(0);">下一步</a>
						</div>
						<div class="ok_disabled" id="step1_modify_div" style="display:none;">
							<a id="step1_modify" href="javascript:void(0);">完 成</a>
						</div>
					</div>
					<div class="padding_blank"></div>
				</div>
			</ul>
			<ul id="step2" style="display:none;">
				<div class="info">
					<div class="title text_big">
						<span class="text_large24">2</span>选择手机号码
					</div>
					<div id="step2_info">
						<ul class="detail">
							<li class="list">
								<div class="left">
									<div class="left_lable bold">号码列表：</div>
								</div>
							</li>
							<li class="list" style="height:auto;">
								<div class="left">
									<div class="left_lable left_lable_bg">主<span class="space5"></span>号：</div>
									<div class="left_data">
										<div class="num_data" name="numbers" id="number_1" first_prepay="" mon_limit="" class_id="" installMode=""></div>
									</div>
									<a><div class="input_button_small" id="number_select_1" onClick="numberSelect(1)">选择新号</div></a>
									<a><div class="input_button_small" id="number_import_1" onClick="numberImport(1)">纳入老号</div></a>
									<a><div class="input_button_small" id="number_modify_1" onClick="numberModify(1)" style="display:none;">重 选</div></a>
									<a><div class="input_button_small" id="number_delete_1" onClick="numberDelete(1)" style="display:none;">放 弃</div></a>
								</div>
								<div class="right">
									<div class="left_lable left_lable_bg">号码6：</div>
									<div class="left_data">
										<div class="num_data" name="numbers" id="number_6" first_prepay="" mon_limit="" class_id="" installMode=""></div>
									</div>
									<a><div class="input_button_small" id="number_select_6" onClick="numberSelect(6)">选择新号</div></a>
									<a><div class="input_button_small" id="number_import_6" onClick="numberImport(6)">纳入老号</div></a>
									<a><div class="input_button_small" id="number_modify_6" onClick="numberModify(6)" style="display:none;">重 选</div></a>
									<a><div class="input_button_small" id="number_delete_6" onClick="numberDelete(6)" style="display:none;">删 除</div></a>
								</div>
								<div class="padding_blank10"></div>
								<div class="left">
									<div class="left_lable left_lable_bg">号码2：</div>
									<div class="left_data">
										<div class="num_data" name="numbers" id="number_2" first_prepay="" mon_limit="" class_id="" installMode=""></div>
									</div>
									<a><div class="input_button_small" id="number_select_2" onClick="numberSelect(2)">选择新号</div></a>
									<a><div class="input_button_small" id="number_import_2" onClick="numberImport(2)">纳入老号</div></a>
									<a><div class="input_button_small" id="number_modify_2" onClick="numberModify(2)" style="display:none;">重 选</div></a>
									<a><div class="input_button_small" id="number_delete_2" onClick="numberDelete(2)" style="display:none;">删 除</div></a>
								</div>
								<div class="right">
									<div class="left_lable left_lable_bg">号码7：</div>
									<div class="left_data">
										<div class="num_data" name="numbers" id="number_7" first_prepay="" mon_limit="" class_id="" installMode=""></div>
									</div>
									<a><div class="input_button_small" id="number_select_7" onClick="numberSelect(7)">选择新号</div></a>
									<a><div class="input_button_small" id="number_import_7" onClick="numberImport(7)">纳入老号</div></a>
									<a><div class="input_button_small" id="number_modify_7" onClick="numberModify(7)" style="display:none;">重 选</div></a>
									<a><div class="input_button_small" id="number_delete_7" onClick="numberDelete(7)" style="display:none;">删 除</div></a>
								</div>
								<div class="padding_blank10"></div>
								<div class="left">
									<div class="left_lable left_lable_bg">号码3：</div>
									<div class="left_data">
										<div class="num_data" name="numbers" id="number_3" first_prepay="" mon_limit="" class_id="" installMode=""></div>
									</div>
									<a><div class="input_button_small" id="number_select_3" onClick="numberSelect(3)">选择新号</div></a>
									<a><div class="input_button_small" id="number_import_3" onClick="numberImport(3)">纳入老号</div></a>
									<a><div class="input_button_small" id="number_modify_3" onClick="numberModify(3)" style="display:none;">重 选</div></a>
									<a><div class="input_button_small" id="number_delete_3" onClick="numberDelete(3)" style="display:none;">删 除</div></a>
								</div>
								<div class="right">
									<div class="left_lable left_lable_bg">号码8：</div>
									<div class="left_data">
										<div class="num_data" name="numbers" id="number_8" first_prepay="" mon_limit="" class_id="" installMode=""></div>
									</div>
									<a><div class="input_button_small" id="number_select_8" onClick="numberSelect(8)">选择新号</div></a>
									<a><div class="input_button_small" id="number_import_8" onClick="numberImport(8)">纳入老号</div></a>
									<a><div class="input_button_small" id="number_modify_8" onClick="numberModify(8)" style="display:none;">重 选</div></a>
									<a><div class="input_button_small" id="number_delete_8" onClick="numberDelete(8)" style="display:none;">删 除</div></a>
								</div>
								<div class="padding_blank10"></div>
								<div class="left">
									<div class="left_lable left_lable_bg">号码4：</div>
									<div class="left_data">
										<div class="num_data" name="numbers" id="number_4" first_prepay="" mon_limit="" class_id="" installMode=""></div>
									</div>
									<a><div class="input_button_small" id="number_select_4" onClick="numberSelect(4)">选择新号</div></a>
									<a><div class="input_button_small" id="number_import_4" onClick="numberImport(4)">纳入老号</div></a>
									<a><div class="input_button_small" id="number_modify_4" onClick="numberModify(4)" style="display:none;">重 选</div></a>
									<a><div class="input_button_small" id="number_delete_4" onClick="numberDelete(4)" style="display:none;">删 除</div></a>
								</div>
								<div class="right">
									<div class="left_lable left_lable_bg">号码9：</div>
									<div class="left_data">
										<div class="num_data" name="numbers" id="number_9" first_prepay="" mon_limit="" class_id="" installMode=""></div>
									</div>
									<a><div class="input_button_small" id="number_select_9" onClick="numberSelect(9)">选择新号</div></a>
									<a><div class="input_button_small" id="number_import_9" onClick="numberImport(9)">纳入老号</div></a>
									<a><div class="input_button_small" id="number_modify_9" onClick="numberModify(9)" style="display:none;">重 选</div></a>
									<a><div class="input_button_small" id="number_delete_9" onClick="numberDelete(9)" style="display:none;">删 除</div></a>
								</div>
								<div class="padding_blank10"></div>
								<div class="left">
									<div class="left_lable left_lable_bg">号码5：</div>
									<div class="left_data">
										<div class="num_data" name="numbers" id="number_5" first_prepay="" mon_limit="" class_id="" installMode=""></div>
									</div>
									<a><div class="input_button_small" id="number_select_5" onClick="numberSelect(5)">选择新号</div></a>
									<a><div class="input_button_small" id="number_import_5" onClick="numberImport(5)">纳入老号</div></a>
									<a><div class="input_button_small" id="number_modify_5" onClick="numberModify(5)" style="display:none;">重 选</div></a>
									<a><div class="input_button_small" id="number_delete_5" onClick="numberDelete(5)" style="display:none;">删 除</div></a>
								</div>
								<div class="right">
									<div class="left_lable left_lable_bg">号码10：</div>
									<div class="left_data">
										<div class="num_data" name="numbers" id="number_10" first_prepay="" mon_limit="" class_id="" installMode=""></div>
									</div>
									<a><div class="input_button_small" id="number_select_10" onClick="numberSelect(10)">选择新号</div></a>
									<a><div class="input_button_small" id="number_import_10" onClick="numberImport(10)">纳入老号</div></a>
									<a><div class="input_button_small" id="number_modify_10" onClick="numberModify(10)" style="display:none;">重 选</div></a>
									<a><div class="input_button_small" id="number_delete_10" onClick="numberDelete(10)" style="display:none;">删 除</div></a>
								</div>
							</li>
							<div class="padding_blank"></div>
						</ul>
						<div class="padding_blank"></div>
						<div class="text_large">
							<div class="ok_disabled" id="step2_next_div">
								<a id="step2_next" href="javascript:void(0);">下一步</a>
							</div>
							<div class="ok_disabled" id="step2_modify_div" style="display:none;">
								<a id="step2_modify" href="javascript:void(0);">完 成</a>
							</div>
						</div>
						<div class="padding_blank"></div>
					</div>
					<div id="new_phone_info" style="display:none;">
			            <div class="detail_white">
			                <div class="line_red_top"></div>
			                <div class="list_white">
			                    <div class="select">
			                        <form id="form1" name="form1" method="post" action="">
			                        <div class="select_list">
			                            <div class="div_float">
			                            <!-- <a id="teleType2G" class="g g_current">2G</a><a class="g" id="teleType3G">3G</a><a class="g"  id="teleType4G" >4G</a> -->
			                            </div>
										<div class="div_float div_float_ie6" id="showMob2G">
											<select id="mob_section" name="mob_section">
												<option value="10000">号段选择</option>
												<option value="185">185</option>
												<option value="186">186</option>
												<option value="145">145</option>
												<option value="175">175</option>
												<option value="176">176</option>
												<option value="156">156</option>
												<option value="155">155</option>
												<option value="132">132</option>
												<option value="131">131</option>
												<option value="130">130</option>
											</select>
										</div>
										<!-- <div class="div_float div_float_ie6" id="showMob3G"> 
		                                   <t:select id="mob_section_3G" name = "mob_section_3G" codeType="mob_section_3G" ></t:select>
		                                </div>
		                                <div class="div_float div_float_ie6" id="showMob4G"> 
		                                   <t:select id="mob_section_4G" name = "mob_section_4G" codeType="mob_section_4G" ></t:select>
		                                </div>-->
										<div class="div_float div_float_ie6" id="show3G" style="display:none;">
			                               <t:select id="perrty_type_pc" name = "perrty_type_pc" codeType="perrty_type_pc" ></t:select>
			                            </div>
			                            <div class="div_float div_float_ie6" id="show2G" style="display:none;">
			                               <t:select id="good_type" name = "good_type" codeType="good_type" ></t:select>
			                            </div>
										<div class="div_float div_float_none">
											<dl>
												<dt>搜索：</dt>
												<dd>
													<input type="text" id="fuzzy_query" value="请输入后1-8位数字查询号码" onfocus="this.value=''" onblur="haoduanOnblur();" class="tel" />
												</dd>
												<dd class="none">
													<a id="query" class="search">查询</a>
												</dd>
												<div class="clear"></div>
											</dl>
										</div>
										<div class="clear"></div>
			                        </div>
			                        </form>
			                    </div>
			              	</div>
						</div>            
						<div class="padding_blank"></div>   
			            <ul class="detail_white">
			            	<div class="wrap" id="list"></div>
						</ul>           
						<ul class="detail_white">
						    <span class="text_large bold red" id="number_title1" style="display:none;">备选号码（点击进行预占）:</span>
							<div class="wrap" id="compareInfo"></div>
						</ul>
						<div class="padding_blank"></div>
						<ul class="detail_white">
							<span class="text_large bold red" id="number_title2" style="display:none;">预占号码:</span>
							<div class="wrap" id="lockInfo"></div>
						</ul>
						<div class="padding_blank"></div>
						<div class="text_large">
							<div class="ok" id="number_cancel_div">
								<a id="number_cancel" href="javascript:void(0);">返 回</a>
							</div>
						</div>
			        </div>
			        <div id="old_phone_info" style="display:none;">
			        	<ul class="detail" style="padding-bottom:30px;">
				        	<div class="title text_big" style="padding:5px 0 0 30px;">用户名下手机</div>
			        		<!-- 动态加载 -->
			        		<li class="list_old_number" product_id="" device_number="18577112567" business_flag="1" tele_type="">
			        			<div>
			        				<div class="inline_block">
			        					<span>手机号码：18577112567</span>
			        					<img src="<%=fullPath%>images/tele_type_4G.png" width="22px" height="22px"/>
			        				</div>
			        				<input type="radio" class="float_right" name="radio_old_phone" />
			        			</div>
			        			<div>
			        				<div class="inline_block">资<span class="space24"></span>费：沃3G-基本套餐A（66元）</div>
			        			</div>
			        		</li>
			        		<li class="list_old_number" product_id="" device_number="13297712513" business_flag="1" tele_type="">
			        			<div>
			        				<div class="inline_block">
			        					<span>手机号码：13297712513</span>
			        					<img src="<%=fullPath%>images/tele_type_2G.png" width="22px" height="22px"/>
			        				</div>
			        				<input type="radio" class="float_right" name="radio_old_phone" />
			        			</div>
			        			<div>
			        				<div class="inline_block">资<span class="space24"></span>费：4G全国套餐-76元</div>
			        			</div>
			        		</li>
			        	</ul>
			        	<div class="padding_blank"></div>
						<div class="text_large text_center">
							<div class="ok_disabled" id="old_phone_confirm_div" style="display:inline;">
								<a id="old_phone_confirm" href="javascript:void(0);">确 定</a>
							</div>
							<div class="ok" id="old_phone_cancel_div" style="display:inline;">
								<a id="old_phone_cancel" href="javascript:void(0);">返 回</a>
							</div>
						</div>
						<div class="padding_blank"></div>
			        </div>
				</div>
			</ul>
			<ul id="step3" style="display:none;">
				<div class="info">
					<div class="title text_big">
						<span class="text_large24">3</span>选择智慧沃家套餐
					</div>
					<ul class="detail" id="liuliangbao_ul">
						<li class="list">
							<div class="left">
								<div class="left_lable bold">
									<span class="red">*</span> 智慧沃家共享流量包（<span class="red">必选包</span>）
								</div>
							</div>
						</li>
						<li class="list" id="liuliangbao_list" style="height:auto;">
							<div class="left">
								<div class="left_lable">正在加载...</div>
							</div>
							<!-- 动态加载 -->
							<!-- <div class="left">
								<div class="left_lable width_35">智慧沃家共享套餐-全国流量包49.9元1GB</div>
								<div class="left_radio">
									<input type="radio" name="liuliangbao" id="" is_mapped="true" />
								</div>
							</div>
							<div class="right">
								<div class="left_lable width_35">智慧沃家共享套餐-全国流量包79.9元2GB</div>
								<div class="left_radio">
									<input type="radio" name="liuliangbao" id="" is_mapped="true" />
								</div>
							</div>
							<div class="left">
								<div class="left_lable width_35">智慧沃家共享套餐-全国流量包99.9元3GB</div>
								<div class="left_radio">
									<input type="radio" name="liuliangbao" id="" is_mapped="true" />
								</div>
							</div>
							<div class="right">
								<div class="left_lable width_35">智慧沃家共享套餐-全国流量包119.9元4GB</div>
								<div class="left_radio">
									<input type="radio" name="liuliangbao" id="" is_mapped="true" />
								</div>
							</div>
							<div class="left">
								<div class="left_lable width_35">智慧沃家共享套餐-全国流量包159.9元6GB</div>
								<div class="left_radio">
									<input type="radio" name="liuliangbao" id="" is_mapped="true" />
								</div>
							</div>
							<div class="right">
								<div class="left_lable width_35">智慧沃家共享套餐-全国流量包199.9元8GB</div>
								<div class="left_radio">
									<input type="radio" name="liuliangbao" id="" is_mapped="true" />
								</div>
							</div>
							<div class="left">
								<div class="left_lable width_35">智慧沃家共享套餐-全国流量包239.9元12GB</div>
								<div class="left_radio">
									<input type="radio" name="liuliangbao" id="" is_mapped="true" />
								</div>
							</div> -->
						</li>
						<div class="padding_blank10"></div>
						<hr class="width_87_4"/>
						<li class="list" id="first_month_fee_list" style="height:auto;">
							<!-- 动态加载 -->
							<!-- <a><div class="radio_button_checked" id="" onclick="selectFirstMonthFee(this)" style="float:left;">首月全量全价</div></a>
							<a><div class="radio_button_unchecked" id="" onclick="selectFirstMonthFee(this)" style="float:left;">首月套餐减半</div></a>
							<a><div class="radio_button_unchecked" id="" onclick="selectFirstMonthFee(this)" style="float:left;">首月按量计费</div></a> -->
						</li>
						<div class="padding_blank10"></div>
					</ul>
					<div class="padding_blank10"></div>
					<ul class="detail" id="yuyindianhua_ul">
						<li class="list">
							<div class="left">
								<div class="left_lable bold">智慧沃家共享语音/可视电话</div>
							</div>
						</li>
						<li class="list" id="yuyindianhua_list" style="height:auto;">
							<div class="left">
								<div class="left_lable">正在加载...</div>
							</div>
							<!-- 动态加载 -->
							<!-- <div class="left">
								<div class="left_lable width_35">全国语音包35元包500分钟</div>
								<div class="left_radio">
									<input type="radio" name="yuyindianhua" id="" is_mapped="true" />
								</div>
							</div>
							<div class="right">
								<div class="left_lable width_35">全国语音包60元包1000分钟</div>
								<div class="left_radio">
									<input type="radio" name="yuyindianhua" id="" is_mapped="true" />
								</div>
							</div>
							<div class="left">
								<div class="left_lable width_35">全国语音包60元包1000分钟</div>
								<div class="left_radio">
									<input type="radio" name="yuyindianhua" id="" is_mapped="true" />
								</div>
							</div> -->
						</li>
						<div class="padding_blank10"></div>
					</ul>
					<div class="padding_blank10"></div>
					<ul class="detail" id="duanxinbao_ul">
						<li class="list">
							<div class="left">
								<div class="left_lable bold">智慧沃家共享点对点短信包</div>
							</div>
						</li>
						<li class="list" id="duanxinbao_list" style="height:auto;">
							<div class="left">
								<div class="left_lable">正在加载...</div>
							</div>
							<!-- 动态加载 -->
							<!-- <div class="left">
								<div class="left_lable width_35">全国短彩信包20元包400条</div>
								<div class="left_radio">
									<input type="radio" name="duanxinbao" id="" is_mapped="true" />
								</div>
							</div>
							<div class="right">
								<div class="left_lable width_35">全国短彩信包30元包600条</div>
								<div class="left_radio">
									<input type="radio" name="duanxinbao" id="" is_mapped="true" />
								</div>
							</div> -->
						</li>
						<div class="padding_blank10"></div>
					</ul>
					<div class="padding_blank10"></div>
					<ul class="detail" id="iptvgongxiang_ul">
						<li class="list">
							<div class="left">
								<div class="left_lable bold">智慧沃家重庆IPTV共享版</div>
							</div>
						</li>
						<li class="list" id="iptvgongxiang_list" style="height:auto;">
							<div class="left">
								<div class="left_lable">正在加载...</div>
							</div>
							<!-- 动态加载 -->
							<!-- <div class="left">
								<div class="left_lable width_35">重庆IPTV共享版融合折扣全免</div>
								<div class="left_radio">
									<input type="radio" name="iptvradio" id="" is_mapped="true" />
								</div>
							</div> -->
						</li>
						<div class="padding_blank10"></div>
					</ul>
					<div class="padding_blank10"></div>
					<ul class="detail" id="iptvtaocan_ul">
						<li class="list">
							<div class="left">
								<div class="left_lable bold">智慧沃家重庆IPTV产品套餐</div>
							</div>
						</li>
						<li class="list" id="iptvtaocan_list" style="height:auto;">
							<div class="left">
								<div class="left_lable">正在加载...</div>
							</div>
							<!-- 动态加载 -->
							<!-- <div class="left">
								<div class="left_lable width_35">重庆IPTV_1标清标准-20元/月</div>
								<div class="left_radio">
									<input type="radio" name="iptvradio" id="" is_mapped="true" />
								</div>
							</div>
							<div class="right">
								<div class="left_lable width_35">重庆IPTV_1标清标准-240元/年</div>
								<div class="left_radio">
									<input type="radio" name="iptvradio" id="" is_mapped="true" />
								</div>
							</div>
							<div class="left">
								<div class="left_lable width_35">IPTV标清标准1</div>
								<div class="left_radio">
									<input type="radio" name="iptvradio" id="" is_mapped="true" />
								</div>
							</div>
							<div class="right">
								<div class="left_lable width_35">IPTV高清标准1</div>
								<div class="left_radio">
									<input type="radio" name="iptvradio" id="" is_mapped="true" />
								</div>
							</div>
							<div class="left">
								<div class="left_lable width_35">重庆IPTV_1标清华为标准-10元/月(IPTV_1)</div>
								<div class="left_radio">
									<input type="radio" name="iptvradio" id="" is_mapped="true" />
								</div>
							</div>
							<div class="right">
								<div class="left_lable width_35">重庆IPTV标清中兴-0元/月(IPTV_1)</div>
								<div class="left_radio">
									<input type="radio" name="iptvradio" id="" is_mapped="true" />
								</div>
							</div>
							<div class="left">
								<div class="left_lable width_35">重庆IPTV_1(联通电视标清版)包月-15元/月</div>
								<div class="left_radio">
									<input type="radio" name="iptvradio" id="" is_mapped="true" />
								</div>
							</div>
							<div class="right">
								<div class="left_lable width_35">重庆IPTV_1(联通电视高清版)包年-240元/年</div>
								<div class="left_radio">
									<input type="radio" name="iptvradio" id="" is_mapped="true" />
								</div>
							</div>
							<div class="left">
								<div class="left_lable width_35">重庆IPTV_1(联通电视标清版)包年-180元/年</div>
								<div class="left_radio">
									<input type="radio" name="iptvradio" id="" is_mapped="true" />
								</div>
							</div>
							<div class="right">
								<div class="left_lable width_35">重庆IPTV高清中兴-0元/月</div>
								<div class="left_radio">
									<input type="radio" name="iptvradio" id="" is_mapped="true" />
								</div>
							</div>
							<div class="left">
								<div class="left_lable width_35">重庆IPTV_1(联通电视高清版)包月-20元/月</div>
								<div class="left_radio">
									<input type="radio" name="iptvradio" id="" is_mapped="true" />
								</div>
							</div> -->
						</li>
						<div class="padding_blank10"></div>
					</ul>
					<div class="padding_blank10"></div>
					<ul class="detail" id="taocanyucun_ul">
						<li class="list">
							<div class="left">
								<div class="left_lable bold">
									<span class="red">*</span> 共享套餐预存300元分12月返还（<span class="red">必选</span>）
								</div>
							</div>
						</li>
						<li class="list" id="taocanyucun_list" style="height:auto;">
							<div class="left">
								<div class="left_lable">正在加载...</div>
							</div>
							<!-- 动态加载 -->
							<!-- <div class="left">
								<div class="left_lable width_35">共享套餐预存300元分12月返还</div>
								<div class="left_radio">
									<input type="radio" name="taocanyucun" id="" />
								</div>
							</div>
							<div class="right">
								<div class="left_lable width_35">预存300元;300元分12月返还;</div>
								<div class="left_radio">
									<input type="radio" name="taocanyucun" id="" />
								</div>
							</div> -->
						</li>
						<div class="padding_blank10"></div>
					</ul>
					<div class="padding_blank"></div>
					<div class="text_large">
						<div class="ok_disabled" id="step3_next_div">
							<a id="step3_next" href="javascript:void(0);">下一步</a>
						</div>
						<div class="ok_disabled" id="step3_modify_div" style="display:none;">
							<a id="step3_modify" href="javascript:void(0);">完 成</a>
						</div>
					</div>
					<div class="padding_blank"></div>
				</div>
			</ul>
			<ul id="step4" style="display:none;">
				<div class="info">
					<div class="title text_big">
						<span class="text_large24">4</span>宽带
						<span class="lan_type_span">
							<input type="radio" name="radio_lan_type" checked="checked"/>新装
						</span>
						/
						<span class="lan_type_span">
							<input type="radio" name="radio_lan_type"/>纳入
						</span>
						/
						<span class="lan_type_span">
							<input type="radio" name="radio_lan_type"/>迁转
						</span>
					</div>
					<div id="new_lan_info">
						<ul class="detail">
							<li class="list" style="height:auto;">
								<c:if test="${province_code=='nx'}">
								<div class="padding_blank10"></div>
								<div class="left">
									<div class="left_lable width_10 text_right bold"><span class="red">*</span> 分局地址：</div>
									<div class="left_data">
										<input type="text" id="fj_select" placeholder="" readOnly="true"/>
									</div>
									<a class="search float_left" onClick="getfjName()">查询</a>
								</div>
								</c:if>
								<div class="padding_blank10"></div>
								<div class="left">
									<div class="left_lable width_10 text_right bold"><span class="red">*</span> 装机地址查询：</div>
									<div class="left_data">
										<input type="text" id="zjdz_select" placeholder="" readOnly="true"/>
									</div>
									<a class="search float_left" onClick="getZjdzName('', true)">查询</a>
								</div>
								<div class="right">
									<div class="left_lable width_10 text_right bold"><span class="red">*</span> 宽带资费名称：</div>
									<div class="left_data">
										<input type="text" id="kdzf_select" placeholder="" readOnly="true"/>
									</div>
									<a><div class="input_button" onClick="getKdzfName('', true)">选 择</div></a>
								</div>
								<div class="padding_blank10"></div>
								<div class="left">
									<div class="left_lable width_10 text_right bold"><span class="red">*</span> 接入方式名称：</div>
									<div class="left_lable">
									<t:select id="jrfs_select" name = "jrfs_select" codeType="access_type" ></t:select>
										<!-- <select id="jrfs_select" name="jrfs_select">
											<option value="">请选择</option>
											<option value="GPON+FTTH">GPON+FTTH业务</option>
											<option value="HFC">HFC业务</option>
											<option value="EPON+FTTB">EPON+FTTB业务</option>
											<option value="GPON+FTTB">GPON+FTTB业务</option>
											<option value="EPON+FTTH">EPON+FTTH业务</option>
											<option value="XDSL">XDSL业务</option>
										</select> -->
									</div>
								</div>
								<div class="right">
									<div class="left_lable width_10 text_right bold"><span class="red">*</span> 客户联系方式：</div>
								</div>
								<div class="padding_blank10"></div>
								<div class="left">
									<div class="left_lable width_10 text_right bold"><span class="red">*</span> 终端提供方式：</div>
									<div class="left_lable">
									<t:select id="zdtg_select" name = "zdtg_select" codeType="machine_provide_type" ></t:select>
										<!-- <select id="zdtg_select" name="zdtg_select">
											<option value="">请选择</option>
											<option value="free">免费使用</option>
											<option value="highFee">终端销售-高档资费</option>
											<option value="middleFee">终端销售-中档资费</option>
										</select> -->
									</div>
								</div>
								<div class="right">
									<div class="left_lable width_10 text_right">联系人：</div>
									<div class="left_data">
										<input type="text" id="kdzf_connect_name" placeholder=""/>
									</div>
								</div>
								<div class="padding_blank10"></div>
								<div class="left">
									<div class="left_lable width_10 text_right bold"><span class="red">*</span> 终端类型：</div>
									<div class="left_lable">
									<t:select id="zdlx_select" name = "zdlx_select" codeType="machine_type" ></t:select>
										<!-- <select id="zdlx_select" name="zdlx_select">
											<option value="">请选择</option>
											<option value="gateway">家庭网关</option>
											<option value="adsl">ADSL MODEM</option>
											<option value="cablle">CABLLE MODEM</option>
											<option value="vdsl">VDSL 终端</option>
										</select> -->
									</div>
								</div>
								<div class="right">
									<div class="left_lable width_10 text_right">联系电话：</div>
									<div class="left_data">
										<input type="text" id="kdzf_connect_phone" placeholder=""/>
									</div>
								</div>
								<div class="padding_blank10"></div>
							</li>
							<div class="padding_blank"></div>
						</ul>
						<div class="padding_blank"></div>
						<div class="text_large">
							<div class="ok_disabled" id="step4_next_div">
								<a id="step4_next" href="javascript:void(0);">下一步</a>
							</div>
							<div class="ok_disabled" id="step4_modify_div" style="display:none;">
								<a id="step4_modify" href="javascript:void(0);">完 成</a>
							</div>
						</div>
						<div class="padding_blank"></div>
					</div>
					<div id="old_lan_info" style="display:none;">
			        	<ul class="detail" style="padding-bottom:30px;">
				        	<div class="title text_big" style="padding:5px 0 0 30px;">用户名下宽带</div>
			        		<!-- 动态加载 -->
			        		<li class="list_old_number" product_id="" device_number="077101125373" business_flag="1" tele_type="" devices_products="宽带公众4M包月90元预付720元赠送360元">
			        			<div>
			        				<div class="inline_block">业务号码：077101125373</div>
			        				<input type="radio" class="float_right" name="radio_old_lan" />
			        			</div>
			        			<div>
			        				<div class="inline_block">资<span class="space24"></span>费：宽带公众4M包月90元预付720元赠送360元</div>
			        			</div>
			        		</li>
			        		<li class="list_old_number" product_id="" device_number="077102202571" business_flag="1" tele_type="" devices_products="宽带公众10M包月120元预付1200元赠送240元">
			        			<div>
			        				<div class="inline_block">业务号码：077102202571</div>
			        				<input type="radio" class="float_right" name="radio_old_lan" />
			        			</div>
			        			<div>
			        				<div class="inline_block">资<span class="space24"></span>费：宽带公众10M包月120元预付1200元赠送240元</div>
			        			</div>
			        		</li>
			        	</ul>
			        	<div class="padding_blank"></div>
						<div class="text_large">
							<div class="ok_disabled" id="old_lan_confirm_div">
								<a id="old_lan_confirm" href="javascript:void(0);">下一步</a>
							</div>
							<div class="ok_disabled" id="old_lan_modify_div" style="display:none;">
								<a id="old_lan_modify" href="javascript:void(0);">完 成</a>
							</div>
						</div>
						<div class="padding_blank"></div>
			        </div>
				</div>
			</ul>
			<ul id="step5" style="display:none;">
				<div class="info">
					<div class="title text_big">
						<span class="text_large24">5</span>确认订单
					</div>
					<ul class="detail">
						<div class="padding_blank10"></div>
						<li class="list" style="height:auto;">
							<div class="left">
								<div class="left_lable bold">用户姓名：</div>
								<div class="left_data" id="customer_name_confirm"><!-- 李志强 --></div>
								<a><div class="input_button_small float_right margin_right_7" id="modify_idcard_info" onClick="">修 改</div></a>
							</div>
							<div class="right">
								<div class="left_lable bold">证件号码：</div>
								<div class="left_data" id="id_number_confirm"><!-- 11204419541220243X --></div>
							</div>
						</li>
						<div class="padding_blank10"></div>
						<hr class="width_87_4"/>
						<li class="list" style="height:auto;">
							<div class="left">
								<div class="left_lable bold">手机号码：</div>
								<a><div class="input_button_small float_right margin_right_7" id="modify_number_info" onClick="">修 改</div></a>
							</div>
							<div class="right">
								<div class="left_lable bold">宽带详情：</div>
								<a><div class="input_button_small float_right margin_right_7" id="modify_kdxq_info" onClick="">修 改</div></a>
							</div>
						</li>
						<li class="list height_17" style="height:auto;">
							<div class="left height_17 padding_top_1">
								<table border="0" align="center" cellpadding="0" cellspacing="0">
									<tr>
										<td class="tb_label">主<span class="space5"></span>号：</td>
										<td class="tb_data" id="number_1_confirm"><!-- 18607710013 --></td>
										<td class="tb_label">号码6：</td>
										<td class="tb_data" id="number_6_confirm"><!-- 18607710014 --></td>
									</tr>
									<tr>
										<td class="tb_label">号码2：</td>
										<td class="tb_data" id="number_2_confirm"><!-- 18607710025 --></td>
										<td class="tb_label">号码7：</td>
										<td class="tb_data" id="number_7_confirm"></td>
									</tr>
									<tr>
										<td class="tb_label">号码3：</td>
										<td class="tb_data" id="number_3_confirm"></td>
										<td class="tb_label">号码8：</td>
										<td class="tb_data" id="number_8_confirm"></td>
									</tr>
									<tr>
										<td class="tb_label">号码4：</td>
										<td class="tb_data" id="number_4_confirm"></td>
										<td class="tb_label">号码9：</td>
										<td class="tb_data" id="number_9_confirm"></td>
									</tr>
									<tr>
										<td class="tb_label">号码5：</td>
										<td class="tb_data" id="number_5_confirm"></td>
										<td class="tb_label">号码10：</td>
										<td class="tb_data" id="number_10_confirm"></td>
									</tr>
								</table>
							</div>
							<div class="right height_2_0">
								<div class="left_lable">宽<span class="space8"></span>带<span class="space8"></span>套<span class="space8"></span>餐：</div>
								<div class="left_data" id="kdtc_confirm"><!-- 广西宽带10M融合内固定资费包月-40元/月 --></div>
							</div>
							<div class="right height_2_0">
								<div class="left_lable">装机详细地址：</div>
								<div class="left_data" id="zjdz_confirm"><!-- 南宁青秀区北区金浦路7号世纪商都-A座3单元304号房 --></div>
							</div>
							<div class="right height_2_0">
								<div class="left_lable">接入方式名称：</div>
								<div class="left_data" id="jrfs_confirm"><!-- FTTH --></div>
							</div>
							<div class="right height_2_0">
								<div class="left_lable">终端提供方式：</div>
								<div class="left_data" id="zdtg_confirm"><!-- 免费使用 --></div>
							</div>
							<div class="right height_2_0">
								<div class="left_lable">终<span class="space8"></span>端<span class="space8"></span>类<span class="space8"></span>型：</div>
								<div class="left_data" id="zdlx_confirm"><!-- ADSL MODEM --></div>
							</div>
							<div class="right margin_top_1">
								<div class="left_lable bold">客户联系方式：</div>
								<!-- <a><div class="input_button_small float_right margin_right_7" id="modify_contact_info" onClick="">修 改</div></a> -->
							</div>
							<div class="right height_2_0">
								<div class="left_lable">联<span class="space18"></span>系<span class="space18"></span>人：</div>
								<div class="left_data" id="connect_name_confirm"><!-- 李志强 --></div>
							</div>
							<div class="right height_2_0">
								<div class="left_lable">联<span class="space8"></span>系<span class="space8"></span>电<span class="space8"></span>话：</div>
								<div class="left_data" id="connect_phone_confirm"><!-- 18607710013 --></div>
							</div>
						</li>
						<div class="padding_blank10"></div>
						<hr class="width_87_4"/>
						<li class="list" style="height:auto;">
							<div class="left">
								<div class="left_lable bold">智慧沃家套餐：</div>
								<a><div class="input_button_small float_right margin_right_7" id="modify_taocan_info" onClick="">修 改</div></a>
							</div>
							<div class="right"></div>
						</li>
						<li class="list" style="height:auto;">
							<div class="left">
								<div class="left_lable">共<span class="space11"></span>享<span class="space11"></span>流<span class="space11"></span>量<span class="space11"></span>包：</div>
								<div class="left_data" id="gxllb_confirm"><!-- 全国流量包99.9元包3GB --></div>
							</div>
							<div class="clear"></div>
							<div class="left">
								<div class="left_lable">共享语音<span class="space1"></span>/<span class="space1"></span>电视电话：</div>
								<div class="left_data" id="gxyydh_confirm"><!-- 全国语音包35元包500分钟 --></div>
							</div>
							<div class="clear"></div>
							<div class="left">
								<div class="left_lable">共<span class="space1"></span>享<span class="space1"></span>点<span class="space1"></span>对<span class="space1"></span>点<span class="space1"></span>短<span class="space1"></span>信<span class="space1"></span>包：</div>
								<div class="left_data" id="gxdxb_confirm"><!-- 全国短彩信包20元包400条 --></div>
							</div>
							<div class="clear"></div>
							<div class="left">
								<div class="left_lable">共<span class="space6"></span>享<span class="space6"></span>套<span class="space6"></span>餐<span class="space6"></span>预<span class="space6"></span>存<span class="space1"></span>：</div>
								<div class="left_data" id="gxtcyc_confirm"><!-- 共享套餐预存300元分12月返还 --></div>
							</div>
						</li>
						<div class="padding_blank10"></div>
					</ul>
					<div class="padding_blank"></div>
					<div class="text_large">
						<div class="ok" id="step5_next_div">
							<a id="step5_next" href="javascript:void(0);">下一步</a>
						</div>
					</div>
					<div class="padding_blank"></div>
				</div>
			</ul>
			<ul id="step6" style="display:none;">
				<div class="info">
					<div class="title text_big">
						<span class="text_large24">6</span>费用确认
					</div>
					<ul class="detail" id="fee_list">
						<!-- 动态加载 -->
						<!-- <div class="padding_blank10 dynamic"></div>
						<li class="list dynamic" style="height:auto;">
							<div class="left fee_confirm_div">
								<div class="bold">调试费</div>
								<div class="left_lable margin_left_1">
									应收：<span id="">100</span>&nbsp;元
								</div>
								<div class="right_data">
									实收：<input type="text" placeholder="0.00" id="charge_item0" code="" name="" data="100" value="100" onChange="CheckChargeItem(this,1);" class="input_text width_8 text_normal_b red">&nbsp;元 
								</div>
							</div>
							<div class="right fee_confirm_div">
								<div class="bold">新功能开户手续费</div>
								<div class="left_lable margin_left_1">
									应收：<span id="">10</span>&nbsp;元
								</div>
								<div class="right_data">
									实收：<input type="text" placeholder="0.00" id="charge_item1" code="" name="" data="10" value="10" onChange="CheckChargeItem(this,1);" class="input_text width_8 text_normal_b red">&nbsp;元 
								</div>
							</div>
						</li>
						<div class="padding_blank10 dynamic"></div>
						<li class="list dynamic" style="height:auto;">
							<div class="left fee_confirm_div">
								<div class="bold">活动预存款</div>
								<div class="left_lable margin_left_1">
									应收：<span id="">240</span>&nbsp;元
								</div>
								<div class="right_data">
									实收：<input type="text" placeholder="0.00" id="charge_item2" code="" name="" data="240" value="240" onChange="CheckChargeItem(this,1);" class="input_text width_8 text_normal_b red">&nbsp;元 
								</div>
							</div>
							<div class="right fee_confirm_div">
								<div class="bold">USIM卡费</div>
								<div class="left_lable margin_left_1">
									应收：<span id="">100</span>&nbsp;元
								</div>
								<div class="right_data">
									实收：<input type="text" placeholder="0.00" id="charge_item3" code="" name="" data="100" value="100" onChange="CheckChargeItem(this,1);" class="input_text width_8 text_normal_b red">&nbsp;元 
								</div>
							</div>
						</li>
						<div class="padding_blank10 dynamic"></div>
						<li class="list dynamic" style="height:auto;">
							<div class="left fee_confirm_div">
								<div class="bold">首次开户预存款</div>
								<div class="left_lable margin_left_1">
									应收：<span id="">100</span>&nbsp;元
								</div>
								<div class="right_data">
									实收：<input type="text" placeholder="0.00" id="charge_item4" code="" name="" data="100" value="100" onChange="CheckChargeItem(this,1);" class="input_text width_8 text_normal_b red">&nbsp;元 
								</div>
							</div>
						</li> -->
						<div class="padding_blank10"></div>
						<div class="line_dashed_h"></div>
						<li class="list bold" style="height:auto;">
							总金额： <span class="bold red" id="fee_all">0</span>&nbsp;元
						</li>
						<div class="padding_blank10"></div>
					</ul>
					<div class="padding_blank"></div>
					<div class="text_large">
						<div class="ok" id="btnGetFeeAgain" style="display:none;">
							<a onClick="getFee();" href="javascript:void(0);">重新获取费用</a>
						</div>
						<div class="ok" id="step6_next_div">
							<a id="step6_next" href="javascript:void(0);">下一步</a>
						</div>
					</div>
				</div>
			</ul>
			<div class="order_info" id="step7" style="display:none;">
				<div class="order_info_title" id="tiaokuan_info">
					<a href="###" class="down" id="shenSuoTiaoKuan">收缩</a><span id="xuhao1">7</span> 条款协议 <!--如果是展开，请用：<a href="###" class="">展开</a>-->
				</div>
				<div class="box" id="box2">
					<form action="" method="">
						<table width="874" border="0" align="center" cellpadding="0"
							cellspacing="0">
							<tr>
								<td height="24" align="left">我已阅读并同意 
									<a href="javascript:void(0)" style="text-decoration: underline;" onclick="openWojia();">《中国联通业务客户入网服务协议》 </a>
								</td>
							</tr>
							<tr>
								<td height="24" align="left">同意签署协议前，请仔细阅读协议条款。根据《中华人民共和国电子签名法》，该电子协议由中国联合网络通信有限公司重庆分公司以电子存档方式保存，具备法律效力。</td>
							</tr>
						</table>
						<div class="line line_per"></div>
						<div class="elc" id="tiaokuanConfirm">
							<a href="javascript:void(0)" onClick="openWojia();">同意协议及电子签名</a>
						</div>
					</form>
				</div>

				<div class="order_info_title" id="feiyong_xinxi">
					<a href="###" class="down" id="shenSuoFeiYong">收缩</a><span id="xuhao2">8</span> 费用信息 <!--如果是展开，请用：<a href="###" class="">展开</a>-->
				</div>
				<div class="box" id="box3">
					<table width="874" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<c:forEach var="infoOrderFeeDetailVo" items="${feeList}"
							varStatus="i">
							<tr>
								<td width="300" height="24" align="left">${infoOrderFeeDetailVo.fee_name }</td>
								<td width="100" align="center">:</td>
								<td width="788">${infoOrderFeeDetailVo.real_fee }元</td>
							</tr>
						</c:forEach>
						<tr>
							<td height="24" width="300" align="left">合计</td>
							<td width="100" align="center">:</td>
							<td><span id="totalFee"></span>元<span class="red" id="getFee"></span></td>
						</tr>
					</table>
					<div class="line line_per" id="linePer"></div>
					<ul class="pay" id="pay">
						<li>支付方式：</li>
						<li>
							<ul id="payTypePc">
								<t:select id="pay_type" codeType="pay_type"></t:select>
							</ul>
						</li>
						<!--  <li><select id="" class="money">
				        	<option>坐席收现</option>
				        	<option>POS机刷卡</option>
				        	<option>支付宝扫码支付</option>
				        	<option>微信支付</option>
				        	<option>营业电子交款(员工)</option>
				        </select></li>-->
						<li><a href="###" onclick="dealFee();" class="view_btn" id="btnGetFee">收费</a></li>
						<li><a href="###" class="view_btn" id="btnDaYinBillData" onclick="paymentOrderBill();">票据打印</a></li>
						<div class="clear"></div>
					</ul>
					<div style="display: none;" class="out_div">
						<img class="pic-idPic" src='' />
						<div style='width: 100%; text-align: left;'>
							<span style='color: #F00; font-size: 20px'>手机支付宝扫描二维码支付
								<button id="closetwocode">关闭</button>
							</span>
						</div>
					</div>
				</div>

				<div class="order_info_title">
					<a href="###" class="down" id="shenSuoKa">收缩</a><span id="xuhao3">9</span> USIM卡 <!--如果是展开，请用：<a href="###" class="">展开</a>-->
				</div>
				<div class="box" id="box4">
					<ul class="usim">
						<c:if test="${order_status=='A30'}">  
			           <li><input type="text"  id="resourcesCode" value="${card_number}" class="text"   />
			             <lable id="number_list_tag">
			            &nbsp;&nbsp;写卡手机号:&nbsp;
				           <select type="select" id="number_list" name="number_list" style="width:150px">
				            <option value="" selected="selected">请选择--</option>
				           </select>
				           </lable>
				           </li>
			             </c:if>
			            <c:if test="${order_status!='A20'&&order_status!='A30'&&writeWay=='0'}">  
			             <li><input type="text" value="请读卡" id="resourcesCode" onfocus="this.value=''" onblur="if(this.value==''){this.value='请读卡'}"  class="text" />
			              <lable id="number_list_tag">
			              &nbsp;&nbsp;写卡手机号:&nbsp;
				           <select type="select" id="number_list" name="number_list" style="width:150px" >
				            <option value="" selected="selected">请选择--</option>
				           </select>
				           </lable>
				           </li>
			             </c:if>
			             <c:if test="${order_status!='A20'&&order_status!='A30'&&writeWay!='0'}">
			            <li><input type="text" value="请读卡" id="resourcesCode" onfocus="this.value=''" onblur="if(this.value==''){this.value='请读卡'}" readonly="readonly" class="text" />
			              <lable id="number_list_tag">&nbsp;&nbsp;写卡手机号:&nbsp;
				           <select type="select" id="number_list" name="number_list" style="width:150px" >
				            <option value="" selected="selected">请选择--</option>
				           </select></lable>
			            </li>
			             </c:if>
						<li><a href="###" class="view_btn" id="readCard">读卡</a> <a href="###" id="writeCard" class="view_btn">一键写卡</a></li>
						<div class="clear"></div>
					</ul>
				</div>
				<div class="order_info_title">
        <a href="###" class="down" id="shenSuoZhiFu">收缩</a>
        <c:if test="${wo_type == '0' || wo_type == '1'}"><span id="xuhao3">5</span> </c:if>
        <c:if test="${wo_type == '2' }"><span id="xuhao3">4</span> </c:if>
                交付方式<!--如果是展开，请用：<a href="###" class="">展开</a>-->
        </div>
        <div class="box" id="box5">
          <ul class="usim">
          <c:choose>
            <c:when  test="${give_type=='1'}">
          	<li>
          	  <input type="radio" name="radio" id="b" value="b" checked="checked"  />
          	  <label for="b">前台</label>
       	    </li>
            <li><input type="radio" name="radio" id="b2" value="b" />
          	  <label for="b2">快递 （只支持EMS或顺丰货到付款方式）</label></li>
            <li class="indent" id="indent1" style="display:none"><input type="text" id="receivePhone" value="请输入收货人号码"  onfocus="this.value=''" onblur="if(this.value==''){this.value='请输入收货人号码'}" class="text short_text" /><input type="text" id="receiveAddress" value="请输入收货人地址" onfocus="this.value=''" onblur="if(this.value==''){this.value='请输入收货人地址'}"  class="text" /></li>
            <li><input type="radio" name="radio" id="b3" value="b" />
          	  <label for="b3">择日自提</label></li>
            <li class="indent"  id="indent2" style="display:none"> <t:date id="end_time" name="end_time" minDateElId="start_time"  value="<%=DateUtil.getCurrentDate() %>"  minDate="<%=DateUtil.getCurrentDate()%>"  ></t:date>
            </li>
             </c:when>
               <c:when test="${give_type=='2'}">
          	<li>
          	  <input type="radio" name="radio" id="b" value="b"   />
          	  <label for="b">前台</label>
       	    </li>
            <li><input type="radio" name="radio" id="b2" value="b" checked="checked"/>
          	  <label for="b2">快递 （只支持EMS或顺丰货到付款方式）</label></li>
            <li class="indent" id="indent1" ><input type="text" value="${receive_phone}" id="receivePhone"  class="text short_text" /><input type="text" id="receiveAddress"  value="${receive_address}"  class="text" /></li>
            <li><input type="radio" name="radio" id="b3" value="b" />
          	  <label for="b3">择日自提</label></li>
            <li class="indent"  id="indent2" style="display:none"> <t:date id="end_time" name="end_time" minDateElId="start_time"  value="<%=DateUtil.getCurrentDate() %>"  minDate="<%=DateUtil.getCurrentDate()%>"  ></t:date>
            </li>
             </c:when>
               <c:when test="${give_type=='3'}">
          	<li>
          	  <input type="radio" name="radio" id="b" value="b"   />
          	  <label for="b">前台</label>
       	    </li>
            <li><input type="radio" name="radio" id="b2" value="b" />
          	  <label for="b2">快递 （只支持EMS或顺丰货到付款方式）</label></li>
            <li class="indent" id="indent1" style="display:none"><input type="text" id="receivePhone" value="请输入收货人号码" onfocus="this.value=''" onblur="if(this.value==''){this.value='请输入收货人号码'}"  class="text short_text" /><input type="text" id="receiveAddress" value="请输入收货人地址" onfocus="this.value=''" onblur="if(this.value==''){this.value='请输入收货人地址'}"  class="text" /></li>
            <li><input type="radio" name="radio" id="b3" value="b" checked="checked"/>
          	  <label for="b3">择日自提</label></li>
            <li class="indent"  id="indent2"> <t:date id="end_time" name="end_time" minDateElId="start_time"  value="${collect_date}"  minDate="<%=DateUtil.getCurrentDate()%>"  ></t:date>
            </li>
             </c:when>
             <c:otherwise> 
             	<li>
          	  <input type="radio" name="radio" id="b" value="b" checked="checked"  />
          	  <label for="b">前台</label>
       	    </li>
            <li><input type="radio" name="radio" id="b2" value="b" />
          	  <label for="b2">快递 （只支持EMS或顺丰货到付款方式）</label></li>
            <li class="indent" id="indent1" style="display:none" ><input type="text" id="receivePhone" value="请输入收货人号码" onfocus="this.value=''" onblur="if(this.value==''){this.value='请输入收货人号码'}"  class="text short_text" /><input type="text" id="receiveAddress" value="请输入收货人地址" onfocus="this.value=''" onblur="if(this.value==''){this.value='请输入收货人地址'}"  class="text" /></li>
            <li><input type="radio" name="radio" id="b3" value="b" />
          	  <label for="b3">择日自提</label></li>
            <li class="indent"  id="indent2" style="display:none"> <t:date id="end_time" name="end_time" minDateElId="start_time"  value="<%=DateUtil.getCurrentDate() %>"  minDate="<%=DateUtil.getCurrentDate()%>"  ></t:date>
            </li>
             </c:otherwise> 
           </c:choose>
          </ul>
    	</div> 
    	 <div class="ok" id="okModule"><a href="###" id="okSubmit" onclick="okSumbit()">完成</a></div>
			</div>
			<div class="clear"></div>
		</div>

		<div class="bg_mask" id="bg_mask">
			<iframe class="bg_mask_iframe"> </iframe>
		</div>

		<!-- 弹出窗口 装机地址选择： -->
		<div class="pop_win" id="zjdz_search" style="display: none;">
			<div class="msgbox">
				<a href="javascript:hidediv('zjdz_search');">
					<div class="msgbox_close"></div>
				</a>
				<ul class="text_big">
					<li><span class="bold">装机地址查询</span></li>
				</ul>
				<div class="band_phone_small">
					<input type="text" class="search_input_small" placeholder="输入地址关键字模糊查询" id="zjdzInput"/>
					<a href="javascript:void(0)">
						<div class="input_button" id="searchZjdz">搜 索</div>
					</a>
				</div>
				<div class="scroll_v" style="position: relative;">
					<ul id="zjdz_list">
						<!-- <li class="list">
							<div class="left">
								<div class="left_lable">XX省XX市XX区</div>
								<div class="right_data">
									<input name="zjdz_search_check" type="radio"/>
								</div>
							</div>
						</li> -->
					</ul>
				</div>
				<div class="line_dashed_h"></div>
				<ul>
					<li class="center">
						<a href="javascript:void(0)" onClick="zjdzConfirm();">
							<div class="input_button">确 定</div>
						</a>
						<a href="javascript:void(0)" onClick="zjdzDelete();">
							<div class="input_button">清 空</div>
						</a>
					</li>
				</ul>
			</div>
		</div>
		
		<!-- 弹出窗口 宽带资费选择： -->
		<div class="pop_win" id="kdzf_search" style="display: none;">
			<div class="msgbox">
				<a href="javascript:hidediv('kdzf_search');">
					<div class="msgbox_close"></div>
				</a>
				<ul class="text_big">
					<li><span class="bold">宽带资费</span></li>
				</ul>
				<div class="band_phone_small">
					<input type="text" class="search_input_small" placeholder="输入资费关键字模糊查询" id="kdzfInput"/>
					<a href="javascript:void(0)">
						<div class="input_button" id="searchKdzf">搜 索</div>
					</a>
				</div>
				<div class="scroll_v" style="position: relative;">
					<ul id="kdzf_list">
						<!-- <li class="list">
							<div class="left">
								<div class="left_lable">宽带10M固定资费包月-50元/月</div>
								<div class="right_data">
									<input name="kdzf_search_check" type="radio"/>
								</div>
							</div>
						</li> -->
					</ul>
				</div>
				<div class="line_dashed_h"></div>
				<ul>
					<li class="center">
						<a href="javascript:void(0)" onClick="kdzfConfirm();">
							<div class="input_button">确 定</div>
						</a>
						<a href="javascript:void(0)" onClick="kdzfDelete();">
							<div class="input_button">清 空</div>
						</a>
					</li>
				</ul>
			</div>
		</div>

		<!-- 弹出窗口 提示信息 -->
		<div class="pop_win" id="pop_win_msg" style="display: none">
			<div class="msgbox">
				<a href="javascript:hidediv('pop_win_msg');">
					<div class="msgbox_close"></div>
				</a>
				<ul class="msg">
					<li>
						<div>
							<span class="space24"></span><span class="text_big" id="err_msg">错误信息在此填写，如：读卡错误,请检查您的控件或驱动是否正确安装最新版本</span>
						</div>
						<div class="line_dashed_h"></div>
						<li></li>
						<li></li>
						<li></li>
						<div class="clear"></div>
						<li class="center">
							<a href="javascript:void(0)" onClick="hidediv('pop_win_msg');">
								<div class="input_button">确 定</div>
							</a>
						</li>
					</li>
				</ul>
			</div>
		</div>
	<!-- 弹出窗口 分局查询： -->
		<div class="pop_win" id="fj_search" style="display: none;">
			<div class="msgbox">
				<a href="javascript:hidediv('fj_search');">
					<div class="msgbox_close"></div>
				</a>
				<ul class="text_big">
					<li><span class="bold">分局地址查询</span></li>
				</ul>
				<div class="band_phone_small">
					<!-- <input type="text" class="search_input_small" placeholder="输入地址关键字模糊查询" id="fjInput"/>
					<a href="javascript:void(0)">
						<div class="input_button" id="searchfj">搜 索</div>
					</a> -->
				</div>
				<div class="scroll_v" style="position: relative;">
					<ul id="fj_list">
						<!-- <li class="list">
							<div class="left">
								<div class="left_lable">XX省XX市XX区</div>
								<div class="right_data">
									<input name="zjdz_search_check" type="radio"/>
								</div>
							</div>
						</li> -->
					</ul>
				</div>
				<div class="line_dashed_h"></div>
				<ul>
					<li class="center">
						<a href="javascript:void(0)" onClick="fjConfirm();">
							<div class="input_button">确 定</div>
						</a>
						<a href="javascript:void(0)" onClick="fjDelete();">
							<div class="input_button">清 空</div>
						</a>
					</li>
				</ul>
			</div>
		</div>

		<input type="hidden" id="customer_sex" value="0">
		<input type="hidden" id="nation_id" value="0">
		<input type="hidden" id="born_date_val" value="0">
		<input type="hidden" id="idcard_addr" value="0">
		<input type="hidden" id="valid" value="0">
		<input type="hidden" id="order_id" value="0">
		<input type="hidden" id="id_type" value="">
		<input type="hidden" id="cust_flag" value="">
		<input type="hidden" name="messageFlag" value="0" id="messageFlag">
		<input type="hidden" name="PhotoBuffer" id="photo_buffer_val">
		<input type="hidden" name="ActivityLFrom" id="start_date_val">
		<input type="hidden" name="ActivityLTo" id="end_date_val">
		<input type="hidden" id="id_number_result" name="id_number"> <!--身份证  -->
		<input type="hidden" id="id_addr_result" name="id_addr"> <!--证件地址  -->
		<input type="hidden" id="id_police_result" name="id_police"> <!--签发地址  -->
		<input type="hidden" id="custom_name_result" name="custom_name"> <!--客户姓名-->
		<input type="hidden" id="custom_sex_result" name="custom_sex"> <!--客户性别  -->
		<input type="hidden" id="custom_birth_result" name="custom_birt"> <!--出生日期  -->
		<input type="hidden" id="custom_nation_result" name="custom_nation"> <!--民族  -->
		<input type="hidden" id="start_date_result" name="start_date"> <!--生效时间  -->
		<input type="hidden" id="end_date_result" name="end_date"> <!--实效时间  -->
		<input type="hidden" id="photo_code_result" name="photo_code"> <!-- 照片编码 -->
		<input type="hidden" id="id_card_image_path" name="id_card_image_path" value="<%=fullPath%>picture/idcard/idCardImag">
		<input type="hidden" id="id_card_mech" name="id_card_mech" value="crvu">
		<input type="hidden" id="oper_id" value="${oper_id}" />
		<input type="hidden" id="area_id" value="${area_id}" />
		<input type="hidden" id="province_code" value="${province_code}" /> <!--省份标识 -->
		<input type="hidden" id="write_way" value="${write_way}" /> <!--模拟写卡标识 -->
		<input type="hidden" id="ms_flag" value="${ms_flag}" /> <!--末梢代理商标识 -->
		<input type="hidden" id="wt_flag" value="${wt_flag}" /> <!--协同标识 -->
		<input type="hidden" id="ori_oper_no" value="${ori_oper_no}" /> <!--原生操作员 --> <!--返回码:-->
		<input type="hidden" size=2 maxlength=2 id="rspCode"> <!--银行行号:-->
		<input type="hidden" size=4 maxlength=4 id="bankCode"> <!--卡号:-->
		<input type="hidden" size=20 maxlength=20 id="cardNo"> <!--有效期:-->
		<input type="hidden" size=4 maxlength=4 id="expr"> <!--批次号:-->
		<input type="hidden" size=6 maxlength=6 id="batch"> <!--流水号:-->
		<input type="hidden" size=6 maxlength=6 id="trace"> <!--支付金额:-->
		<input type="hidden" size=12 maxlength=12 id="rspAmount"> <!--支付金额  隐藏传值,免单位转换:-->
		<input type="hidden" size=12 maxlength=12 id="charge"> <!--返回中文提示:-->
		<input type="hidden" size=40 maxlength=40 id="rspChin"> <!--商户号:-->
		<input type="hidden" size=15 maxlength=15 id="mchtId"> <!--终端号:-->
		<input type="hidden" size=8 maxlength=8 id="termId"> <!--参考号:-->
		<input type="hidden" size=12 maxlength=12 id="reference"> <!--交易日期:-->
		<input type="hidden" size=4 maxlength=4 id="transDate"> <!--交易时间:-->
		<input type="hidden" size=6 maxlength=6 id="transTime"> <!--授权号:-->
		<input type="hidden" size=6 maxlength=6 id="authNo"> <!--结算日期:-->
		<input type="hidden" size=4 maxlength=4 id="settleDate"> <!--增值域响应信息:-->
		<input type="hidden" size=50 maxlength=100 id="appendResField"> <!--备注信息:-->
		<input type="hidden" size=50 maxlength=100 id="memo">
		
		<!--读写卡 ======开始====== -->
		<input type="hidden"  id="progress"  value="${progress}"/><!--记录订单详情进度 -->
		  <input type="hidden"  id="payFlag"  value="${payFlag}"/><!--收费标示 --> 
		  <input type="hidden"  id="payType"  value="${payType}"/><!--是pos机刷卡还是坐席收费标志--> 
		  <input type="hidden"  id="acc_nbr"  value="${master_acc_nbr}"/><!--用户号码 -->   
		  <input type="hidden" id="tele_type" value="${teleType}" />  
		  <input type="hidden" id = "writeWay" value="${writeWay}" /><!--模拟写卡还是正式写卡方式0代表模拟写卡1代表正式写卡-->
		 <input type="hidden"  id="prepayFlag"  value="1"/>
		 <input type="hidden"  id="imsi"  value=""/>
		 <input type="hidden" id = "activeId" value="" />
		 <input type="hidden" id = "cardData" value="" />
		  <input type="hidden" id = "cardType" value="${cardType}" />
		 <input type="hidden" id = "capacityTypeCode" value="" />
		 <input type="hidden" id = "resKindCode" value="" />
		 <input type="hidden" id = "procId" value="" /> 
		  <input type="hidden"  id="order_source"  value="${order_source}"/>
		  <input type="hidden"  id="order_status"  value="${order_status}"/><!--订单状态--> 
		 <input type="hidden"  id="apweb_url"  value="${apweb_url}"/><!--无纸化地址   -->
		 <input type="hidden"  id="good_templateid"  value="${good_templateid}"/><!--靓号模板ID   -->
		  <input type="hidden"  id="preferential_templateid"  value="${preferential_templateid}"/><!--优惠协议模版ID   -->
		 <input type="hidden"  id="apweb_value"  value="1"/> <!--协议及电子签名序号-->
		 <input type="hidden"  id="order_sub_type"  value="${order_sub_type}"/><!--订单类型--> 
		 <input type="hidden"  id="end_open"  value="${end_open}"/>
		 <input type="hidden"  id="write_nubmer"  value="${writeCakList}"/>
		 <input type="hidden"  id="wo_type"  value="${wo_type}" /><!-- 宽带单装 -->
		 <input type="hidden"  id="termsInnetFlag"  value="${termsInnetFlag}"/><!--入网协议   -->
		 <input type="hidden"  id="termsGoodFlag"  value="${termsGoodFlag}"/><!--靓号协议-->
		 <input type="hidden"  id="termsPreferentialFlag"  value="${termsPreferentialFlag}"/><!--优惠协议  -->
		 <input type="hidden"  id="termsWjtFlag"  value="${termsWJTFlag}"/><!--沃家庭协议  -->
		 
		 <input type="hidden"  id="wo_fa_phone_number"  value="${wo_fa_phone_number}"/><!--沃家庭旧手机号-->
		 <input type="hidden"  id="wo_fa_lan_number"  value="${wo_fa_lan_number}"/><!--沃家庭旧宽带业务号码-->		
		<!--读写卡 ======结束====== -->
		
		<div class="copyright"> Copyright © 2014 China unicom All Right Reserved</div>
</body>
</html>