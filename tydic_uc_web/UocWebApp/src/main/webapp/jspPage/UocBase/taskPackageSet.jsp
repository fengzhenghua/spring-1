<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>任务包设置</title>
    <link rel="stylesheet" type="text/css" href="../../style/css/public.css" />
    <link rel="stylesheet" type="text/css" href="../../style/css/taskPackageSet.css" />
    <script type="text/javascript" src="../../js/common/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="../../js/common/WebUtil.js"></script>
    <script type="text/javascript" src="../../js/common/public.js"></script>
    <script type="text/javascript" src="../../js/UocBaseJs/taskPackageSet.js"></script>
</head>
<body>
	<div class="container">
		<div class="mtop10">
			<div class="font14 back" id="close_task_package"><span>返回</span></div>
		</div>
		<!-- 搜索任务包 -->
		<div class="search">
			<ul class="filter">
				<li>
					<label>任务包名称:</label>
					<div class="select">
						<input type="text" class="text-box" placeholder="请输入任务包名称" id="pkg_name"/>
					</div>
				</li>
				<li>
					<label>业务名称:</label>
					<div class="select">
						<input type="text" placeholder="— 请选择 —" readonly id="oper"/>
							<b class="caret caret-down" query_type="oper"></b>						
						<ul>
						</ul>
					</div>
				</li>
				<li>
					<label>产品名称:</label>
					<div class="select">
						<input type="text" placeholder="— 请选择 —" readonly id="prod"/>
						<b class="caret caret-down" query_type="prod"></b>
						<ul>
						</ul>
					</div>
				</li>
				<li>
					<label>环节名称:</label>
					<div class="select">
						<input type="text" placeholder="— 请选择 —" readonly id="tache"/>
						<b class="caret caret-down" query_type="tache"></b>
						<ul>
						</ul>
					</div>
				</li>
				<li>
					<div class="btn btn_primary" id="searchBtn">查询</div>
					<div class="btn btn_default mleft5" id="resetBtn">重置</div>
				</li>
			</ul>			
		</div>
		<!-- 新增任务包 -->
		<div class="new-task-package">
			<div class="add">
				<span class="arrow-right arrow-down jia">任务包</span> 
			</div>	
			<!-- 任务包注释 -->	
			<!-- <ul>
				<li>
					<b><i></i>任务包名称：</b>
					<input class="task-package-name" placeholder="点我输入任务包名称哦" id="newTaskPackageName"/>
				</li>
				<li class="task">
					<table class="add-task" id="newTaskPackageList">
						<thead>
							<tr>
								<th><i></i>业务</th>
								<th><i></i>产品</th>
								<th><i></i>环节</th>
								<th><i></i>数量</th>
								<th></th>
							</tr>
						</thead>
						<tbody>								
							<tr>
								<td>
									<div class="select">
										<input placeholder="— 请选择 —" readonly/>
										<b class="caret caret-down"></b>
										<ul>
											<li>4G开户</li>
											<li>3G开户</li>
										</ul>
									</div>								
								</td>
								<td>
									<div class="select">
										<input placeholder="— 请选择 —" readonly/>
										<b class="caret caret-down"></b>
										<ul>
											<li>腾讯大王卡</li>
										</ul>
									</div>
								</td>
								<td>
									<div class="select">
										<input placeholder="— 请选择 —" readonly/>
										<b class="caret caret-down"></b>
										<ul>
											<li>写卡</li>
											<li>审核</li>
										</ul>
									</div>
								</td>
								<td>
									<div class="number">
										<span class="minus-btn">-</span>
										<input class="num" value="1"/>
										<span class="add-btn">+</span>
									</div>
								</td>
								<td>
									<i class="del-btn"></i>
								</td>
							</tr>
							<tr>
								<td>
									<div class="select">
										<input placeholder="— 请选择 —" readonly/>
										<b class="caret caret-down"></b>
										<ul>
											<li>4G开户</li>
											<li>3G开户</li>
										</ul>
									</div>								
								</td>
								<td>
									<div class="select">
										<input placeholder="— 请选择 —" readonly/>
										<b class="caret caret caret-down"></b>
										<ul>
											<li>腾讯大王卡</li>
										</ul>
									</div>
								</td>
								<td>
									<div class="select">
										<input placeholder="— 请选择 —" readonly/>
										<b class="caret caret-down"></b>
										<ul>
											<li>写卡</li>
											<li>审核</li>
										</ul>
									</div>
								</td>
								<td>
									<div class="number">
										<span class="minus-btn">-</span>
										<input class="num" value="1"/>
										<span class="add-btn">+</span>
									</div>
								</td>
								<td>
									<i class="del-btn"></i>
								</td>
							</tr>
						</tbody>
					</table>
					新增任务包-右边部分
					<div class="role role1 clearfix">
						左边-详情
						
						<div class="left rl">
							注意：每增加一个li ，rl-ul的宽就增加30px
							<ul class="rl-ul">
								<li class="rl-li gray-border">1</li>
								<li class="rl-li gray-border">2</li>
								<li class="rl-li gray-border">3</li>
								<li class="rl-li gray-border">4</li>
								<li class="rl-li gray-border">5</li>
								<li class="rl-li gray-border">6</li>
								<li class="rl-li gray-border">7</li>
								<li class="rl-li gray-border">8</li>
							</ul>
						</div>
						左边-详情 end
						右边-分页
						<div class="right rr">
							<div class="rr-top"></div>
							<div class="rr-bottom"></div>
						</div>
						右边-分页end
					</div>	
					新增任务包-右边部分end												
				</li>
				<li>
					<span class="add-list"><i></i></span>
				</li>
				<li class="last-item">			
					<span class="save">保存</span>
					<span  class="cancel">取消</span>					
				</li>
			</ul>	 -->
			<!-- 任务包注释end -->	
			
					 
		</div>
		<!-- 任务包列表 -->
		<div class="task-package" id="taskpackageList">
		
		<!-- 任务包列表注释 -->
			<!-- <ul class="task-list">				
				<li>
					<i class="edit-btn"></i>					
					<span>XXXXX任务包1</span>
					<i class="del-btn"></i>				
				</li>
				<li class="task">
					<table class="add-task">
						<thead>
							<tr>
								<th><i></i>业务</th>
								<th><i></i>产品</th>
								<th><i></i>环节</th>
								<th><i></i>数量</th>
								<th></th>
							</tr>
						</thead>
						<tbody>								
							<tr>
								<td>4G开户</td>
								<td>腾讯大王卡</td>
								<td>写卡</td>
								<td>1</td>
							</tr>
							<tr>
								<td>4G开户</td>
								<td>腾讯大王卡</td>
								<td>写卡</td>
								<td>2</td>								
							</tr>
						</tbody>
					</table>
					<div class="role clearfix">
						左边-详情
							<div class="left rl">
								<ul class="rl-ul">
									<li class="rl-li">1</li>
									<li class="rl-li">2</li>
									<li class="rl-li">3</li>
									<li class="rl-li">4</li>
									<li class="rl-li">5</li>
									<li class="rl-li">6</li>
									<li class="rl-li">7</li>
								</ul>
							</div>
							左边-详情 end
							右边-分页
							<div class="right rr">
								<div class="rr-top"></div>
								<div class="rr-bottom"></div>
							</div>
							右边-分页
					</div>																
				</li>				
			</ul> -->
			<!-- 任务包列表注释 end-->
			
		</div>
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
		</div>
	</div>
</body>
</html>