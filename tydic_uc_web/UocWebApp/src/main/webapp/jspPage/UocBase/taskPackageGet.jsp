<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>领取任务包</title>
    <link rel="stylesheet" type="text/css" href="../../style/css/public.css" />
    <link rel="stylesheet" type="text/css" href="../../style/css/taskPackageGet.css" />
    <script type="text/javascript" src="../../js/common/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="../../js/common/WebUtil.js"></script>
    <script type="text/javascript" src="../../js/common/public.js"></script>
    <script type="text/javascript" src="../../js/UocBaseJs/taskPackageGet.js"></script>
</head>
<body>
	<div class="container">
		<div>
			<!-- <div class="btn btn_link font14" id="close_task_package">&lt;&lt;返回</div> -->
			<div class="font14 back" id="close_task_package"><span>返回</span></div>
		</div>
		<!-- 选择任务包 -->
		<header>
			<div class="banner">						
				<p class="arrow arrow-left" id="prev"></p>
				<p class="arrow arrow-right" id="next"></p>
				<!-- 需在流程节点加载完后设置ul宽度：ul宽度 = (203 * 节点数)px -->
				<ul class="margin-center clear bg-white" id="package_title">
					<li class="task-package-item">
						<p class="border-blue" package_id="1" >
							<span>任务包1</span>
							<u class="valign-middle"></u>
							<txt class="pkg_name">XXXX任务包</txt>
							<i class="selected"></i>
						</p>				
					</li>
					<li class="task-package-item">
						<p class="border-gray" package_id="2" >
							<span>任务包2</span>
							<u class="valign-middle"></u>
							<txt class="pkg_name">YYYY任务包</txt>
							<i></i>
						</p>				
					</li>
					<li class="task-package-item">
						<p class="border-gray" package_id="3" >
							<span>任务包3</span>
							<u class="valign-middle"></u>
							<txt class="pkg_name">XXXX任务包</txt>
							<i></i>
						</p>				
					</li>
					<li class="task-package-item">
						<p class="border-gray" package_id="4" >
							<span>任务包4</span>
							<u class="valign-middle"></u>
							<txt class="pkg_name">XXXX任务包任务包任务包任务包任务包</txt>
							<i></i>
						</p>				
					</li>
					<li class="task-package-item">
						<p class="border-gray" package_id="5" >
							<span>任务包5</span>
							<u class="valign-middle"></u>
							<txt class="pkg_name">XXXX任务包</txt>
							<i></i>
						</p>				
					</li>
					<li class="task-package-item">
						<p class="border-gray" package_id="6" >
							<span>任务包6</span>
							<u class="valign-middle"></u>
							<txt class="pkg_name">XXXX任务包</txt>
							<i></i>
						</p>				
					</li>			
					<li class="task-package-item">
						<p class="border-gray" package_id="7" >
							<span>任务包7</span>
							<u class="valign-middle"></u>
							<txt class="pkg_name">XXXX任务包XXXX任务包XXXX任务包XXXX任务包XXXX任务包</txt>
							<i></i>
						</p>				
					</li>			
				</ul>
			</div>
		</header>
		<!-- 任务包详情-->
		<div class="package-list" id="package_detail">	
			<!-- XXXX任务包 -->
			<table class="package-details" package_id="1" >
				<thead class="bg-graye6">
					<tr class="border-gray">
						<th><i class="icon icon-firm"></i><span class="tab-tip">业务</span></th>
						<th><i class="icon icon-pro"></i><span class="tab-tip">产品</span></th>
						<th><i class="icon icon-link"></i><span class="tab-tip">环节</span></th>
						<th><i class="icon icon-num"></i><span class="tab-tip">数量</span></th>
					</tr>
				</thead>
				<tbody class="bg-grayf7">								
					<tr class="border-gray-dashed">
						<td>4G开户</td>
						<td>腾讯大王卡</td>
						<td>写卡</td>
						<td>1</td>
					</tr>
					<tr class="border-gray-dashed">
						<td>4G开户</td>
						<td>腾讯大王卡</td>
						<td>写卡</td>
						<td>1</td>								
					</tr>
				</tbody>
			</table>																								
			<table class="package-details" package_id="2">
				<thead class="bg-graye6">
					<tr class="border-gray">
						<th><i class="icon icon-firm"></i><span class="tab-tip">业务</span></th>
						<th><i class="icon icon-pro"></i><span class="tab-tip">产品</span></th>
						<th><i class="icon icon-link"></i><span class="tab-tip">环节</span></th>
						<th><i class="icon icon-num"></i><span class="tab-tip">数量</span></th>
					</tr>
				</thead>
				<tbody class="bg-grayf7">								
					<tr class="border-gray-dashed">
						<td>4G开户</td>
						<td>腾讯大王卡</td>
						<td>写卡</td>
						<td>2</td>
					</tr>
					<tr class="border-gray-dashed">
						<td>4G开户</td>
						<td>腾讯大王卡</td>
						<td>写卡</td>
						<td>2</td>								
					</tr>
				</tbody>
			</table>																								
			<table class="package-details" package_id="3">
				<thead class="bg-graye6">
					<tr class="border-gray">
						<th><i class="icon icon-firm"></i><span class="tab-tip">业务</span></th>
						<th><i class="icon icon-pro"></i><span class="tab-tip">产品</span></th>
						<th><i class="icon icon-link"></i><span class="tab-tip">环节</span></th>
						<th><i class="icon icon-num"></i><span class="tab-tip">数量</span></th>
					</tr>
				</thead>
				<tbody class="bg-grayf7">								
					<tr class="border-gray-dashed">
						<td>4G开户</td>
						<td>腾讯大王卡</td>
						<td>写卡</td>
						<td>3</td>
					</tr>
					<tr class="border-gray-dashed">
						<td>4G开户</td>
						<td>腾讯大王卡</td>
						<td>写卡</td>
						<td>3</td>								
					</tr>
					<tr class="border-gray-dashed">
						<td>4G开户</td>
						<td>腾讯大王卡</td>
						<td>写卡</td>
						<td>3</td>								
					</tr>
				</tbody>
			</table>																								
		</div>
		<!-- 领取任务包数量 -->
		<div class="text-center">请输入领取任务包个数：<input type="text" class="pkg-num" id="pkg_num" placeholder="不输入默认为1"/></div>
		<!-- 按钮组 -->
		<div class="btn-group text-center">
			<div class="btn btn_primary" id="get_task_package">领取</div>
		</div>		
	</div>
</body>
</html>