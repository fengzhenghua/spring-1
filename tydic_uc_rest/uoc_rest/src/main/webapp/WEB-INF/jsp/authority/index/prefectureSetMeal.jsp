<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
 <%@include file="../common/common.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>套餐专区</title>
<link href="<%=fullPath%>css/sale.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
/*mod haolong200801*/
$(document).ready(function(){
	$("#3G").mouseenter(function(){
     $('.position').show();
     $("#3G").addClass("hover");
    });
	$("#3G").mouseleave(function(){
	 $('.position').hide();
	 $("#3G").removeClass("hover");
	})
})
</script>
</head>
<body>
	<div class="sale_content sale_content_none">
		<div class="sale_shop">
			<div class="sale_package">
				<div class="sale_package_left">
					<h2>套餐专区</h2>
					<ul>
						<li id ="3G">沃3G<!--加上 class="hover"可以实现高亮处理。如<li class="hover">-->
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
						<li>4G/3G一体化套餐</li>
						<li>自由组合</li>
						<li>宽带</li>
						<li>组合业务</li>
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