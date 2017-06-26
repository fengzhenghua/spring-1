<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>套餐关联信息</title>
    <link rel="stylesheet" type="text/css" href="./css/common/public.css" />
    <link rel="stylesheet" type="text/css" href="./css/common/common.css" />
    <link rel="stylesheet" type="text/css" href="./css/pc/relateInfo.css" />
    <script type="text/javascript" src="./js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="./js/public.js"></script>
</head>
<%@ include file="include.jsp"%>
<script>
	var requerdata = '';
	//1、判断是否登录，如果登录了就不需要显示 logindiv ，如果没有登录就显示
	var myGrid;
	$(document).ready(function() {
		getlistfromrest();
		$('#btn_update').on('click',function(){
			var postjson = "{ ";
			postjson+="'updateskuuuid':'"+$('#updateskuuuid').val()+"',";
			postjson+="'updateskuname':'"+$('#updateskuname').val()+"',";
			postjson+="'updateskuid':'"+$('#updateskuid').val()+"',";
			postjson+="'updateskustats':'"+$('#updateskustats').val()+"',";
			postjson+="'updateopercode':'"+$('#updateopercode').val()+"'";
			postjson += " }";
			
			console.log(postjson);
			$.loading.show('请等待，正在保存结果！');
			$.ajax({
						url : BASEURL
								+ '/rest/crawlerServiceRest/updateSKU',
						crossDomain : true == !(document.all),
						type : 'POST', //GET
						async : true, //或false,是否异步
						data : {
							json_info : postjson
						},
						timeout : TIMEOUT, //超时时间
						dataType : 'json', //返回的数据格式：json/xml/html/script/jsonp/text
						beforeSend : function(xhr) {
							console.log(xhr);
							console.log('发送前');
							//等待查询信息
						},
						success : function(data, textStatus, jqXHR) {
							if (data.content != null) {
								if(data.respCode=='0000'){
									getlistfromrest();
								}else{
									showerr(data);
								}
							} else {
								showerr({'content':'放回数据错误'});
							}

						},
						error : function(xhr, textStatus) {
							showerr({'content':'更新数据错误'});
							$.loading.hide();
						},
						complete : function() {
							console.log('结束')
							$.loading.hide();
							updatecancel();
						}
					});
			
		});
		
		//点击新增
		$('#btn_insert').on('click',function(){
			var postjson = "{ ";
			postjson+="'insertskuname':'"+$('#insertskuname').val()+"',";
			postjson+="'insertskuid':'"+$('#insertskuid').val()+"',";
			postjson+="'insertskustats':'"+$('#insertskustats').val()+"',";
			postjson+="'insertopercode':'"+$('#insertopercode').val()+"'";
			postjson += " }";
			
			$.loading.show('请等待，正在保存结果！');
			$.ajax({
						url : BASEURL
								+ '/rest/crawlerServiceRest/insSKU',
						crossDomain : true == !(document.all),
						type : 'POST', //GET
						async : true, //或false,是否异步
						data : {
							json_info : postjson
						},
						timeout : TIMEOUT, //超时时间
						dataType : 'json', //返回的数据格式：json/xml/html/script/jsonp/text
						beforeSend : function(xhr) {
							console.log(xhr);
							console.log('发送前');
							//等待查询信息
						},
						success : function(data, textStatus, jqXHR) {
							if (data.content != null) {
								if(data.respCode=='0000'){
									getlistfromrest();
								}else{
									showerr(data);
								}
							} else {
								showerr({'content':'放回数据错误'});
							}

						},
						error : function(xhr, textStatus) {
							showerr({'content':'插入数据异常'});
							$.loading.hide();
						},
						complete : function() {
							console.log('结束')
							$.loading.hide();
						}
					});
			
		});
		
		$('#btn_find').on('click',function(){
			finditem();
		});
		
		$('#btn_reset').on('click',function(){
			getlistfromrest();
		});
		$('.cancel').on('click',function(){
			updatecancel();
		});
		
		//dj
		//动态添加高度
		var allH = parseInt($(window).height());
	    var itemH = parseInt($('.detail_info_body').height());
	    var mtH = parseInt($('.detail_info_body').css('margin-top'));
	    var H =( allH  - itemH - mtH - 100 )+'px';
	    var MT = ( allH - itemH - mtH - 80)+'px';
	    $('.box').css('height',H);
		//djend
		
	});
	
	function finditem(){
		
//		if($('#insertskuname').val() == '' && $('#insertskuid').val() == '' && $('#insertskustats').val()=='' ){
//			return;
//		}
		$.loading.show('等待查询结果！');
		var postjson = "{ ";
		postjson+="'insertskuname':'"+$('#insertskuname').val()+"',";
		postjson+="'insertskuid':'"+$('#insertskuid').val()+"',";
		postjson+="'insertskustats':'"+$('#insertskustats').val()+"',";
		postjson+="'insertopercode':'"+$('#insertopercode').val()+"'";
		postjson += " }";
		
		$.ajax({
					url : BASEURL
							+ '/rest/crawlerServiceRest/getSKUList',
					crossDomain : true == !(document.all),
					type : 'POST', //GET
					async : true, //或false,是否异步
					data : {
						json_info : postjson
					},
					timeout : TIMEOUT, //超时时间
					dataType : 'json', //返回的数据格式：json/xml/html/script/jsonp/text
					beforeSend : function(xhr) {
						console.log(xhr);
						console.log('发送前');
						//等待查询信息
					},
					success : function(data, textStatus, jqXHR) {
						if (data.content != null) {
							if(data.respCode=='0000'){
								showlist(data);
							}else{
								showerr(data);
							}
						} else {
							showerr({'content':'查询数据错误'});
						}

					},
					error : function(xhr, textStatus) {
						console.log('错误')
						console.log(xhr);
						$.loading.hide();
					},
					complete : function() {
						console.log('结束')
						$.loading.hide();
					}
				});
	}
	
	function showerr(data){
		$.message.error(data.content);
	}
	
	function updatecancel(){
		$('#updateskuuuid').val('');
		$('#updateskuname').val('');
		$('#updateskuid').val('');
		$('#updateskustats').val('');
		$('.item').show();
		$('.edit_item').hide();
	}
	
	function getlistfromrest(){
		$.loading.show('等待查询结果！');
		$.ajax({
					url : BASEURL
							+ '/rest/crawlerServiceRest/getSKUList',
					crossDomain : true == !(document.all),
					type : 'POST', //GET
					async : true, //或false,是否异步
					data : {
						json_info : "{\"1\":\"1"+ "\"}"
					},
					timeout : TIMEOUT, //超时时间
					dataType : 'json', //返回的数据格式：json/xml/html/script/jsonp/text
					beforeSend : function(xhr) {
						console.log(xhr);
						console.log('发送前');
						//等待查询信息
					},
					success : function(data, textStatus, jqXHR) {
						if (data.content != null) {
							if(data.respCode=='0000'){
								showlist(data);
							}else{
								showerr(data);
							}
						} else {
							showerr({'content':'得到列表数据错误'});
						}

					},
					error : function(xhr, textStatus) {
						console.log('错误')
						console.log(xhr);
						$.loading.hide();
					},
					complete : function() {
						console.log('结束')
						$.loading.hide();
					}
				});
	}
	function showlist(data){
		var html = '';
		$("#info_listbody").html('');
		if (data.args.listjson == "") {
			return;
		}
		var resjson = data.args.list;
		if(resjson==null)
			return;
		if (typeof(resjson) == "undefined"){ 
			var resjson = eval('(' + data.args.list + ')');
		}
		requerdata = resjson;
		console.log(resjson);
		
		for(i = 0;i< resjson.length;i++){
			addList('#info_listbody',resjson[i]);
		}

		//td
		$('#info_list tbody tr').each(function(index,element){
			if(index%2==1)
			 $(this).css('background','rgba(192,192,192,0.2)');
		}); 

		$('#info_list').on('mousemove','tbody tr',function(){
			$this = $(this);
			$this.css('background','#e1e1e1');
		});

		$('#info_list').on('mouseover','tbody tr',function(){
			$('.info_list tbody tr').each(function(index,element){
				if(index%2==1){
				  $(this).css('background','rgba(192,192,192,0.2)');
				}
				if(index % 2 == 0){
				  $(this).css('background','#fff');
				}
			}); 
		});


		//点击编辑
		$('.edit').on('click',function(){
			$this = $(this);
			updateshowitem($this.attr('val'));

		});
		
	}
	function updateshowitem(id){
		for(i = 0;i< requerdata.length;i++){
			console.log(requerdata[i] + "  " + id + " " + (requerdata[i].cra_sku_uuid == id))
			if(requerdata[i].cra_sku_uuid == id){
				$('.item').hide();
				$('.edit_item').show();
				
				$('#updateskuuuid').val(requerdata[i].cra_sku_uuid);
				$('#updateskuname').val(requerdata[i].cra_sku_name);
				$('#updateskuid').val(requerdata[i].sku_id);
				$('#updateskustats').val(requerdata[i].cra_sku_stats);
				$('#updateopercode').val(requerdata[i].oper_code);
			}
		}
		
	}
	
	function addList(className,item) {
		var html = '';
		var imgcon='';
		if(getstr(item.uid) != null ){
		html += '<tr id="'+item.cra_sku_uuid+'">'
				+ '<td class="td2">'+getstr(item.cra_sku_name)+'</td>'
				+ '<td class="td3">'+getstr(item.sku_id)+'</td>'
				+ '<td class="td4">'+getstr(item.cra_sku_stats)+'</td>'
				+ '<td class="td4">'+getstr(item.oper_code)+'</td>'
				+ '<td><i title="编辑" class="cursor edit" val='+item.cra_sku_uuid+'></i></td>'
				+ '</tr>';
		$(className).append(html);
		}	

	
	}
	
	function getstr(obj){
		if (typeof(obj) == "undefined"){
			return "&nbsp;"
		}else{
			return obj;
		}
	}
</script>


<body>
	<div class="container">
	    <!-- 搜索 -->
		<div class="detail_item br">
			<ul class="detail_info_body mtop15item item">
				<li class="line">
					<div class="width100per">
						<input type="hidden" id='insertskuuuid' name='insertskuuuid'/>
						<span class="bold mright5">商城商品名称：</span>
						<span class="input_box width150">
							<input type="text" id='insertskuname' name='insertskuname'/>
						</span>

						<span class="bold mright5 mleft10">套餐ID：</span>
						<span class="input_box width150">
							<input type="text" id='insertskuid' name='insertskuid'/>
						</span>

						<span class="bold mright5 mleft10">状态：</span>
						<span class="input_box width150">
							<input type="text" id='insertskustats' name='insertskustats'/>
						</span>
						
						<span class="bold mright5 mleft10">开户系统：</span>
						<span class="input_box width150">
							<select type="text" id='insertopercode' name='insertopercode'>
							<option value="">全部</option>
								<option value="openLocal4G">本地商品</option>
								<option value="openCq01">总部商品</option>
							</select>
						</span>
						<div class="btn btn_primary mleft10" id="btn_find">查询</div>
						<div class="btn btn_default mleft5" id="btn_reset">重置</div>
						<div class="btn btn_primary mleft10" id="btn_insert">新增</div>
					</div>
						
				</li>
			</ul>

			<!-- 编辑区域 -->
			<ul class="detail_info_body br edit_item" style="display:none">
				<li class="line">
					<div class="width100per">
						<input type="hidden" id='updateskuuuid' name='updateskuuuid'/>
						<span class="bold mright5">商城商品名称：</span>
						<span class="input_box edit_input1 width150">
							<input type="text" id='updateskuname' name='updateskuname'/>
						</span>

						<span class="bold mright5 mleft10">套餐ID：</span>
						<span class="input_box edit_input2 width150">
							<input type="text" id='updateskuid' name='updateskuid'/>
						</span>

						<span class="bold mright5 mleft10">状态：</span>
						<span class="input_box edit_input3 width150">
							<input type="text" id='updateskustats' name='updateskustats'/>
						</span>
						
						<span class="bold mright5 mleft10">开户系统：</span>
						<span class="input_box edit_input3 width150">
							<select type="text" id='updateopercode' name='updateopercode'>
								<option value="openLocal4G">本地商品</option>
								<option value="openCq01">总部商品</option>
							</select>
						</span>
						
						<div class="btn btn_primary mleft10 keep" id="btn_update">保存</div>
						<div class="btn btn_default mleft5 cancel">取消</div>
					</div>
				</li>
			</ul>
			<!-- 编辑区域end -->
		<!-- 搜索end -->

		<!-- 信息列表 -->
		<div class="box" style="overflow-y:auto;">
			<table class="info_list" id="info_list">
				<thead>
					<tr>
						<th width="15%">商城商品名称</th>
						<th width="15%">套餐ID</th>
						<th width="10%">状态</th>
						<th width="10%">开户系统</th>
						<th width="10%">操作</th>
					</tr>
				</thead>
				<tbody class="info_listbody" id="info_listbody">
				</tbody>
			</table>
		</div>
		<!-- 信息列表end -->
	</div>
	<div class="page cursor">
		<span class="page1">第<b>1</b>页</span>
		<span class="page1">共<b>1</b>页</span>
		<span class="page1">共<b>1</b>条</span>
		<span class="page1">第<b>一</b>页</span>
		<span class="page1">上<b>一</b>页</span>
		<span class="page1">下<b>一</b>页</span>
		<span class="page1">最后页</span>
		<span class="page2">跳转到 <input type="number" /> 页</span>
		<span class="page3 br">GO</span>
	</div>
</body>
</html>