<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/jsp/oppocard/common.jsp" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>触点生成二维码</title>
    <link rel="stylesheet" type="text/css" href="../css/pc/public.css" />
    <link rel="stylesheet" type="text/css" href="../css/pc/contact.css" />    
    <script type="text/javascript" src="../js/Plugin/jquery/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="../js/Plugin/qrCode/jquery.qrcode.min.js"></script>
    <script type="text/javascript" src="../js/pc/common/public.js"></script>
    <script type="text/javascript" src="../js/pc/common/WebUtil.js"></script>
    <script type="text/javascript" src="../js/pc/contactBuildQrcode.js"></script>
</head>
<body>
	<div class="container">
		<div class="detail_item">
			<ul class="detail_info_body mtop15">
				<li class="line">
					<div class="width100per">
						<span class="bold mright5">ID</span>
						<span class="input_box width150"><input type="text" id="searchApId"/></span>
						
						<span class="bold mleft10 mright5">名称</span>
						<span class="input_box"><input type="text" id="searchApName"/></span>
						
						<span class="bold mleft10 mright5">商品</span>
						<span class="input_box"><input type="text" class="cs_pointer" id="searchProduct" no="" placeholder="请选择商品" readonly/></span>
						
						<span class="bold mleft10 mright5">触点类型</span>
						<span class="input_box"><input type="text" class="cs_pointer" id="modalApType" no="" placeholder="请选择触点类型" readonly/></span>

					</div>
				</li>
				<li class="line mtop5">
					<div class="width100per">
						<span class="bold mright5">地区</span>
						<span class="input_box width150"><input type="text" class="cs_pointer" id="searchArea" no="" placeholder="请选择地区" readonly/></span>
						
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
			</ul>
		</div>
		<table class="info_list">
			<thead>
				<tr>
					<th width="10%">名称</th>
					<th width="8%">描述</th>
					<th width="10%">商品</th>
					<th width="10%">触点链接</th>
					<th width="8%">地区</th>
					<th width="8%">渠道</th>
					<th width="9%">发展人</th>
					<th width="9%">绑定工号</th>
					<th width="12%">创建时间</th>
					<th width="8%">创建人</th>
					<th width="8%">操作</th>

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
					<td class="text_center">
						<div class="btn btn_link" name="contactAddBtn">生成二维码</div>
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
					<td class="text_center">
						<div class="btn btn_link" name="contactAddBtn">生成二维码</div>
					</td>
				</tr> -->
			</tbody>
		</table>
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
	<div class="modal_layer"  belong="mask" id="qrcodeModal">
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
				<!-- <div class="btn btn_primary" id="qrcodeSaveAsBtn">另存为...</div> -->
				<div class="btn btn_default mleft10" id="qrcodeCloseBtn">关 闭</div>
			</div>
		</div>
	</div>
	<!-- 生成二维码-模态框 -->
	<div class="modal_layer" belong="mask" id="contactModal">
		<div class="bg_cover"></div>
		<div class="modal_box">
			<div class="modal_top">
				<span class="modal_title">生成二维码</span><span class="modal_close">x</span>
			</div>
			<div class="modal_middle">
				<div class="detail_item">
					<ul class="detail_info_body">
						<li class="line">
							<div class="width100per">
								<span class="bold mright5">触点名称</span>
								<span id="modalApName"></span>
							</div>
						</li>
						<li class="line">
							<div class="width100per">
								<span class="bold mright5">触点链接</span>
								<span id="modalApUrl"></span>
							</div>
						</li>
						<li class="line mtop5">
							<div class="width100per">
								<span class="bold mright5">发展人</span>
								<span class="input_box width300"><input type="text" class="cs_pointer" id="modalDeveloper" no="" placeholder="请选择发展人" readonly/></span>
							</div>
						</li>
						<li class="line mtop5" style="display:block;">
							<div class="width100per">
								<span class="bold mright15">即时激励发展人</span>
								<span class="input_box width300"><input type="text" class="cs_pointer" id="modalJiLiDeveloper" no="" placeholder="请选择即时激励发展人" readonly/></span>
							</div>
						</li>
					</ul>
				</div>
			</div>
			<div class="modal_bottom">
				<div class="btn btn_primary" name="viewQrcodeBtn" id="contactConfirmBtn">生成二维码</div>
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
	<!-- 选择地区-模态框 -->
	<div class="modal_layer" belong="mask" id="areaModal">
		<div class="bg_cover" style="z-index:1002;"></div>
		<div class="modal_box" style="z-index:1003;">
			<div class="modal_top">
				<span class="modal_title">可选地区</span><span class="modal_close">x</span>
			</div>
			<div class="modal_middle" style="margin-top:0;">
				<div class="detail_last_item">
					<ul class="detail_info_body">
						 <li class="line">
							<div class="width100per text_center">
								<span class="input_box width150"><input type="text" id="searchModalArea" placeholder="请输入关键字进行搜索"/></span>
								<div class="btn btn_primary mleft10" id="areaSearchBtn">查询</div>
							</div>
						</li> 
						<li class="line">
							<div class="option_box" type="radio" id="areaList">
								<!-- <span class="one_third"><a no="area_id1">地区1</a></span><span class="one_third"><a no="area_id2">地区2</a></span><span class="one_third"><a no="area_id3">地区3</a></span><span class="one_third"><a no="prod_id4">地区4</a></span> -->
							</div>
						</li>
					</ul>
				</div>
			</div>
			<div class="modal_bottom">
				<div class="btn btn_primary" id="areaConfirmBtn">确 定</div>
				<div class="btn btn_default" id="areaCancelBtn">取 消</div>
			</div>
		</div>
	</div>
	<!-- 选择渠道-模态框 -->
	<div class="modal_layer" belong="mask" id="channelModal">
		<div class="bg_cover" style="z-index:1002;"></div>
		<div class="modal_box" style="z-index:1003;">
			<div class="modal_top">
				<span class="modal_title">可选渠道</span><span class="modal_close">x</span>
			</div>
			<div class="modal_middle" style="margin-top:0;">
				<div class="detail_last_item">
					<ul class="detail_info_body">
						 <li class="line">
							<div class="width100per text_center">
								<span class="input_box width150"><input type="text" id="searchModalChannel" placeholder="请输入关键字进行搜索"/></span>
								<div class="btn btn_primary mleft10" id="channelSearchBtn">查询</div>
							</div>
						</li> 
						<li class="line">
							<div class="option_box" type="radio" id="channelList">
								<!-- <span class="one_third"><a no="channel_id1">渠道1</a></span><span class="one_third"><a no="channel_id2">渠道2</a></span><span class="one_third"><a no="channel_id3">渠道3</a></span><span class="one_third"><a no="channel_id4">渠道4</a></span> -->
							</div>
						</li>
					</ul>
				</div>
			</div>
			<div class="modal_bottom">
				<div class="btn btn_primary" id="channelConfirmBtn">确 定</div>
				<div class="btn btn_default" id="channelCancelBtn">取 消</div>
			</div>
		</div>
	</div>
	<!-- 绑定发展人-模态框 -->
	<div class="modal_layer" belong="mask" id="bindingDeveloperModal">
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
								<span class="input_box width150"><input type="text" id="bindingSearchModalDeveloper" placeholder="请输入关键字进行搜索"/></span>
								<div class="btn btn_primary mleft10" id="bindingDeveloperSearchBtn">查询</div>
							</div>
						</li>
						<li class="line">
							<div class="option_box" type="checkbox" id="bindingDeveloperList">
								<!-- <span class="one_third"><a no="developer1">发展人1</a></span><span class="one_third"><a no="developer2">发展人2</a></span><span class="one_third"><a no="developer3">发展人3</a></span><span class="one_third"><a no="developer3">发展人4</a></span> -->
							</div>
						</li>
					</ul>
				</div>
			</div>
			<div class="modal_bottom">
				<div class="btn btn_primary" id="bindingDeveloperConfirmBtn">确 定</div>
				<div class="btn btn_default" id="bindingDeveloperCancelBtn">取 消</div>
			</div>
		</div>
	</div>
	<!-- 绑定即时激励发展人-模态框 -->
	<div class="modal_layer" belong="mask" id="bindingJiLiDeveloperModal">
		<div class="bg_cover" style="z-index:1002;"></div>
		<div class="modal_box" style="z-index:1003;">
			<div class="modal_top">
				<span class="modal_title">即时激励发展人</span><span class="modal_close">x</span>
			</div>
			<div class="modal_middle" style="margin-top:0;">
				<div class="detail_last_item">
					<ul class="detail_info_body">
						<li class="line" style="display:none;">
							<div class="width100per text_center">
								<span class="input_box width150"><input type="text" id="bindingJiLiSearchModalDeveloper" placeholder="请输入关键字进行搜索"/></span>
								<div class="btn btn_primary mleft10" id="bindingJiLiDeveloperSearchBtn">查询</div>
							</div>
						</li>
						<li class="line">
							<div class="option_box" type="checkbox" id="bindingJiLiDeveloperList">
								<!-- <span class="one_third"><a no="developer1">发展人1</a></span><span class="one_third"><a no="developer2">发展人2</a></span><span class="one_third"><a no="developer3">发展人3</a></span><span class="one_third"><a no="developer3">发展人4</a></span> -->
							</div>
						</li>
					</ul>
				</div>
			</div>
			<div class="modal_bottom">
				<div class="btn btn_primary" id="bindingJiLiDeveloperConfirmBtn">确 定</div>
				<div class="btn btn_default" id="bindingJiLiDeveloperCancelBtn">取 消</div>
			</div>
		</div>
	</div>
</body>
</html>