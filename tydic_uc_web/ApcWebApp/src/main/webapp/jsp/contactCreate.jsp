<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/jsp/oppocard/common.jsp" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>触点创建</title>
    <link rel="stylesheet" type="text/css" href="../css/pc/public.css" />
    <link rel="stylesheet" type="text/css" href="../css/pc/contact.css" />    
    <script type="text/javascript" src="../js/Plugin/jquery/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="../js/Plugin/qrCode/jquery.qrcode.min.js"></script>
    <script type="text/javascript" src="../js/Plugin/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="../js/pc/common/public.js"></script>
    <script type="text/javascript" src="../js/pc/common/WebUtil.js"></script>
    <script type="text/javascript" src="../js/pc/contactCreate.js"></script>
</head>
<body>
	<div class="container">
		<!-- 菜单栏 -->
		<div class="detail_item">
			<ul class="detail_info_body mtop15">
				<li class="line">
					<div class="width100per">
						<span class="bold mright5">名称</span>
						<span class="input_box width150"><input type="text" id="searchApName" placeholder="请输入名称"/></span>
						
						<span class="bold mleft10 mright5">商品</span>
						<span class="input_box"><input type="text" class="cs_pointer" id="searchProduct" no="" placeholder="请选择商品" readonly/></span>
						
						<span class="bold mleft10 mright15">类型</span>
						<span class="input_box"><input type="text" class="cs_pointer" id="searchType" no="" placeholder="请选择触点类型" readonly/></span>
						
						<span class="bold mleft10 mright5">标识</span>
						<span class="input_box">
							<select id="searchStatus">
								<option value="0">生效</option>
								<option value="1">失效</option>
							</select>
						</span>
					</div>
				</li>
				<li class="line mtop5">
					<div class="width100per">
						<span class="bold mright5">地区</span>
						<span class="input_box width150"><input type="text" class="cs_pointer" id="searchRegion" no="" placeholder="请选择地区" readonly/></span>
						
						<span class="bold mleft10 mright5">渠道</span>
						<span class="input_box"><input type="text" class="cs_pointer" id="searchChannel" no="" placeholder="请选择渠道" readonly/></span>
						
						<span class="bold mleft10 mright5">发展人</span>
						<span class="input_box"><input type="text" class="cs_pointer" id="searchDeveloper" no="" placeholder="请选择发展人" readonly/></span>
						
						<span class="bold mleft10 mright5">绑定工号</span>
						<span class="input_box"><input type="text" class="cs_pointer" id="searchOperNo" no="" placeholder="请选择绑定工号" readonly/></span>
						
						<div class="btn btn_primary mleft10" id="searchBtn">查询</div>
						<div class="btn btn_default mleft5" id="resetBtn">重置</div>
					</div>
				</li>
				<li class="line mtop5">
					<div class="width100per">
						<div class="btn btn_info mleft5" id="contactAddBtn">新增方案</div>
					</div>
				</li>
			</ul>
		</div>
		<!-- 信息展示列表 -->
		<table class="info_list">
			<thead>
				<tr>
					<th width="8%">名称</th>
					<th width="8%">描述</th>
					<th width="8%">商品</th>
					<th width="8%">触点链接</th>
					<th width="8%">地区</th>
					<th width="8%">渠道</th>
					<th width="8%">发展人</th>
					<th width="8%">绑定工号</th>
					<th width="8%">创建时间</th>
					<th width="8%">创建人</th>
					<th width="8%">生效时间</th>
					<th width="8%">失效时间</th>
					<th width="8%">标识</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody id="contactList">
				<!-- <tr ap_id="1">
					<td>测试触点1</td>
					<td>这里是描述1</td>
					<td>商品AAA</td>
					<td>www.aaaa.ccc</td>
					<td class="text_center">张三</td>
					<td class="text_center">TEST01</td>
					<td class="text_center">2017-02-28 10:23:55</td>
					<td class="text_center">李四</td>
					<td class="text_center">失效</td>
					<td class="text_center">
						<div class="btn btn_link" name="viewQrcodeBtn">查看二维码</div>
						<div class="btn btn_link" name="enableBtn">生效</div>
					</td>
				</tr>
				<tr ap_id="2">
					<td>测试触点2</td>
					<td>这里是描述2</td>
					<td>商品BBB</td>
					<td>www.bbbb.ccc</td>
					<td class="text_center">张三</td>
					<td class="text_center">TEST01</td>
					<td class="text_center">2017-02-28 10:23:55</td>
					<td class="text_center">李四</td>
					<td class="text_center">生效</td>
					<td class="text_center">
						<div class="btn btn_link" name="viewQrcodeBtn">查看二维码</div>
						<div class="btn btn_link" name="disableBtn">失效</div>
					</td>
				</tr> -->
			</tbody>
		</table>
		<!-- 分页组件 -->
		<div class="pagination">
			<span class="mright10">共<b class="mleft5 mright5" id="totalCount">0</b>条</span>			
			<span class="input_box width40" >
				<select id="pageSize" value="">
					<option value="5">5</option>
					<option value="10">10</option>
					<option value="20">20</option>
				</select>				
			</span>
			<span class="page_turn" id="pageFirst">&lt;&lt;</span>
			<span class="page_turn" id="pageUp">&lt;</span>
			<span class="input_box width40"><input type="text" class="text_center" id="pageNo" value="1"/></span>
			<span class="page_turn" id="pageDown">&gt;</span>
			<span class="page_turn" id="pageLast">&gt;&gt;</span>
			<span class="mright10">共<b class="mleft5 mright5" id="totalPage">0</b>页</span>	
			<div class="btn btn_default mleft25" id="exportBtn">导出</div>
		</div>
	</div>
	<!-- 二维码-模态框 -->
	<div class="modal_layer" belong="mask" id="qrcodeModal">
		<div class="bg_cover"></div>
		<div class="modal_box">
			<div class="modal_top">
				<span class="modal_title">二维码</span><span class="modal_close">x</span>
			</div>
			<div class="modal_middle">
				<div class="qr_code" id="qrCode">
					<p>正在加载...</p>
				</div>
			</div>
			<div class="modal_bottom">
				<!-- <div class="btn btn_info" id="qrcodeSaveAsBtn">另存为...</div> -->
				<div class="btn btn_default mleft10" id="qrcodeCloseBtn">关 闭</div>
			</div>
		</div>
	</div>
	<!-- 新增触点-模态框 -->
	<div class="modal_layer" belong="mask" id="contactModal">
		<div class="bg_cover"></div>
		<div class="modal_box">
			<div class="modal_top">
				<span class="modal_title">新增方案</span><span class="modal_close">x</span>
			</div>
			<div class="modal_middle">
				<div class="detail_item">
					<ul class="detail_info_body mtop15">
						<li class="line">
							<div class="width100per">
								<span class="bold mright5" style="margin-left:12px;"><font style="color:red">*</font>名称</span>
								<span class="input_box width150"><input type="text" id="modalApName" placeholder="请输入名称"/></span>
								
								<span class="bold mleft10 mright5"><font style="color:red">*</font>方案描述</span>
								<span class="input_box width200"><input type="text" id="modalApDesc" placeholder="请输入描述"/></span>
								
								<span class="bold mleft10 mright5"><font style="color:red">*</font>触点链接</span>
								<span class="input_box width200"><input type="text" id="modalApUrl" placeholder="请输入链接"/></span>
							</div>
						</li>
						<li class="line">
							<div class="width100per">
								<span class="bold mright5" style="margin-left:12px;">&nbsp;类型</span>
								<span class="input_box width150"><input type="text" class="cs_pointer" id="modalApType" no="" placeholder="请选择类型" readonly/></span>
								
								<span class="bold mleft10 mright5">&nbsp;生效时间</span>
								<span class="input_box width200"><input type="text" id="modalApStartTime" onclick="WdatePicker();" placeholder="请选择生效时间" readonly/></span>
								
								<span class="bold mleft10 mright5">&nbsp;失效时间</span>
								<span class="input_box width200"><input type="text" id="modalApEndTime" onclick="WdatePicker();" placeholder="请选择失效时间" readonly/></span>
							</div>
						</li>
						<li class="line">
							<div class="width100per">
								<span class="bold mright5" style="margin-left:12px;">&nbsp;地区</span>
								<span class="input_box width150"><input type="text" class="cs_pointer" id="modalApRegion" no="" placeholder="请选择地区" readonly/></span>
								
								<span class="bold mleft10 mright5">&nbsp;选择渠道</span>
								<span class="input_box width200"><input type="text" class="cs_pointer" id="modalApChannel" no="" placeholder="请选择渠道" readonly/></span>
								
								<span class="bold mleft10 mright15">&nbsp;发展人</span>
								<span class="input_box width200"><input type="text" class="cs_pointer" id="modalDeveloper" no="" placeholder="请选择发展人" readonly/></span>
							</div>
						</li>
						<li class="line mtop5">
							<div class="width100per">
								<span class="bold mright5" style="margin-left:12px;"><font style="color:red">*</font>商品</span>
								<span class="input_box width150"><input type="text" class="cs_pointer" id="modalProduct" no="" placeholder="请选择商品" readonly/></span>
								
								<span class="bold mleft10 mright5"><font style="color:red">*</font>绑定工号</span>
								<span class="input_box width200"><input type="text" class="cs_pointer" id="modalOperNo" no="" placeholder="请选择绑定工号" readonly/></span>
							</div>
						</li>
					</ul>
				</div>
			</div>
			<div class="modal_bottom">
				<div class="btn btn_primary" id="contactConfirmBtn">保 存</div>
				<div class="btn btn_default mleft10" id="contactCancelBtn">取 消</div>
			</div>
		</div>
	</div>
	<!-- 发展人-模态框 -->
	<div class="modal_layer" belong="mask" id="developerModal">
		<div class="bg_cover" style="z-index:1002;"></div>
		<div class="modal_box" style="z-index:1003;">
			<div class="modal_top">
				<span class="modal_title">发展人</span><span class="modal_close">x</span>
			</div>
			<div class="modal_middle" style="margin-top:0;">
				<div class="detail_last_item">
					<ul class="detail_info_body">
						<li class="line">
							<div class="width100per text_center">
								<span class="input_box width150"><input type="text" id="searchModalDeveloper" placeholder="请输入关键字进行搜索"/></span>
								<div class="btn btn_primary mleft10" id="developerSearchBtn">查询</div>
							</div>
						</li>
						<li class="line">
							<div class="option_box" type="checkbox" id="developerList">
								<!-- <span class="one_third"><a no="developer1">发展人1</a></span><span class="one_third"><a no="developer2">发展人2</a></span><span class="one_third"><a no="developer3">发展人3</a></span><span class="one_third"><a no="developer3">发展人4</a></span> -->
							</div>
						</li>
					</ul>
				</div>
			</div>
			<div class="modal_bottom">
				<div class="btn btn_primary" id="developerConfirmBtn">确 定</div>
				<div class="btn btn_default" id="developerCancelBtn">取 消</div>
			</div>
		</div>
	</div>
	<!-- 绑定工号-模态框 -->
	<div class="modal_layer" belong="mask" id="operNoModal">
		<div class="bg_cover" style="z-index:1002;"></div>
		<div class="modal_box" style="z-index:1003;">
			<div class="modal_top">
				<span class="modal_title">绑定工号</span><span class="modal_close">x</span>
			</div>
			<div class="modal_middle" style="margin-top:0;">
				<div class="detail_last_item">
					<ul class="detail_info_body">
						<li class="line">
							<div class="width100per text_center">
								<span class="input_box width150"><input type="text" id="searchModalOperNo" placeholder="请输入关键字进行搜索"/></span>
								<div class="btn btn_primary mleft10" id="operNoSearchBtn">查询</div>
							</div>
						</li>
						<li class="line">
							<div class="option_box" type="radio" id="operNoList">
								<!-- <span class="one_third"><a no="oper_no1">工号1</a></span><span class="one_third"><a no="oper_no2">工号2</a></span><span class="one_third"><a no="oper_no3">工号3</a></span><span class="one_third"><a no="oper_no4">工号4</a></span> -->
							</div>
						</li>
					</ul>
				</div>
			</div>
			<div class="modal_bottom">
				<div class="btn btn_primary" id="operNoConfirmBtn">确 定</div>
				<div class="btn btn_default" id="operNoCancelBtn">取 消</div>
			</div>
		</div>
	</div>
	<!-- 选择商品-模态框 -->
	<div class="modal_layer" belong="mask" id="prodModal">
		<div class="bg_cover" style="z-index:1002;"></div>
		<div class="modal_box" style="z-index:1003;">
			<div class="modal_top">
				<span class="modal_title">可选商品</span><span class="modal_close">x</span>
			</div>
			<div class="modal_middle" style="margin-top:0;">
				<div class="detail_last_item">
					<ul class="detail_info_body">
						<!-- <li class="line">
							<div class="width100per text_center">
								<span class="input_box width150"><input type="text" id="searchModalProd" placeholder="请输入关键字进行搜索"/></span>
								<div class="btn btn_primary mleft10" id="prodSearchBtn">查询</div>
							</div>
						</li> -->
						<li class="line">
							<div class="option_box" type="checkbox" id="prodList">
								<!-- <span class="one_third"><a no="prod_id1">商品1</a></span><span class="one_third"><a no="prod_id2">商品2</a></span><span class="one_third"><a no="prod_id3">商品3</a></span><span class="one_third"><a no="prod_id4">商品4</a></span> -->
							</div>
						</li>
					</ul>
				</div>
			</div>
			<div class="modal_bottom">
				<div class="btn btn_primary" id="prodConfirmBtn">确 定</div>
				<div class="btn btn_default" id="prodCancelBtn">取 消</div>
			</div>
		</div>
	</div>
	<!-- 选择地区-模态框 -->
	<div class="modal_layer" belong="mask" id="apRegionModal">
		<div class="bg_cover" style="z-index:1002;"></div>
		<div class="modal_box" style="z-index:1003;">
			<div class="modal_top">
				<span class="modal_title">选择地区</span><span class="modal_close">x</span>
			</div>
			<div class="modal_middle" style="margin-top:0;">
				<div class="detail_last_item">
					<ul class="detail_info_body">
						<li class="line">
							<div class="width100per text_center">
								<span class="input_box width150">
									<input type="text" id="apRegionSearchModal" placeholder="请输入地区名称"/>
								</span>
								<div class="btn btn_primary mleft10" id="apRegionSearchBtn">查询</div>
							</div>
						</li>
						<li class="line">
							<div class="option_box" type="radio" id="apRegionList">
							</div>
						</li>
					</ul>
				</div>
			</div>
			<div class="modal_bottom">
				<div class="btn btn_primary" id="apRegionConfirmBtn">确 定</div>
				<div class="btn btn_default" id="apRegionCancelBtn">取 消</div>
			</div>
		</div>
	</div>
	<!-- 选择渠道-模态框 -->
	<div class="modal_layer" belong="mask" id="apChnlModal">
		<div class="bg_cover" style="z-index:1002;"></div>
		<div class="modal_box" style="z-index:1003;">
			<div class="modal_top">
				<span class="modal_title">选择渠道</span><span class="modal_close">x</span>
			</div>
			<div class="modal_middle" style="margin-top:0;">
				<div class="detail_last_item">
					<ul class="detail_info_body">
						<li class="line">
							<div class="width100per text_center">
								<span class="input_box width150">
									<input type="text" id="apChnlSearchModal" placeholder="请输入渠道名称"/>
								</span>
								<div class="btn btn_primary mleft10" id="apChnlSearchBtn">查询</div>
							</div>
						</li>
						<li class="line">
							<div class="option_box" type="radio" id="apChnlList">
							</div>
						</li>
					</ul>
				</div>
			</div>
			<div class="modal_bottom">
				<div class="btn btn_primary" id="apChnlConfirmBtn">确 定</div>
				<div class="btn btn_default" id="apChnlCancelBtn">取 消</div>
			</div>
		</div>
	</div>
	<!-- 选择类型-模态框 -->
	<div class="modal_layer" belong="mask" id="apTypeModal">
		<div class="bg_cover" style="z-index:1002;"></div>
		<div class="modal_box" style="z-index:1003;">
			<div class="modal_top">
				<span class="modal_title">选择类型</span><span class="modal_close">x</span>
			</div>
			<div class="modal_middle" style="margin-top:0;">
				<div class="detail_last_item">
					<ul class="detail_info_body">
						<li class="line">
							<div class="option_box" type="radio" id="apTypeList">
							</div>
						</li>
					</ul>
				</div>
			</div>
			<div class="modal_bottom">
				<div class="btn btn_primary" id="apTypeConfirmBtn">确 定</div>
				<div class="btn btn_default" id="apTypeCancelBtn">取 消</div>
			</div>
		</div>
	</div>
</body>
</html>