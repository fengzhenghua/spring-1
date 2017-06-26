<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../common/common.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>销售</title>
<link href="<%=fullPath%>css/sale.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript">
	$(function() {
		//$(".position").hide();
	});
</script>
</head>
<body>
	 <div class="sale_content sale_content_none">
		<div class="sale_shop margin_10">
			<div class="sale_active sale_active_bottom">



				<div class="sale_active_left">
					<h2>配件专区</h2>
					<ul>
						<li>耳机<!--加上 class="hover"可以实现高亮处理。如<li class="hover">-->
							<div class="position" style="display: none">
								<dl>
									<dt>A计划</dt>
									<dd>
										<a href="#">66</a> <a href="#">96</a> <a href="#">126</a> <a
											href="#">156</a> <a href="#">186</a> <a href="#">226</a> <a
											href="#">286</a>
									</dd>
								</dl>
								<dl>
									<dt>B计划</dt>
									<dd>
										<a href="#">66</a> <a href="#">96</a> <a href="#">126</a> <a
											href="#">156</a> <a href="#">186</a> <a href="#">226</a> <a
											href="#">286</a>
									</dd>
								</dl>
								<dl>
									<dt>C计划</dt>
									<dd>
										<a href="#">66</a> <a href="#">96</a> <a href="#">126</a> <a
											href="#">156</a> <a href="#">186</a> <a href="#">226</a> <a
											href="#">286</a>
									</dd>
								</dl>
								<div class="more">
									<a href="#">更多>></a>
								</div>
							</div>


						</li>
						<li>充电宝</li>
					</ul>

				</div>
				<div class="sale_more">
					<a href="#">更多>></a>
				</div>
			</div>
		</div>
	</div> 


</body>
</html>