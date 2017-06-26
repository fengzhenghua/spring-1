<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="common.jsp" %>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<title>1元流量王</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=yes" />
<link href="../../css/pc/public.css" rel="stylesheet" type="text/css" />
<link href="../../css/pc/common/oppoPublic.css" rel="stylesheet" type="text/css" />
<link href="../../css/pc/oppo.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../../js/pc/common/jquery-1.11.1.min.js" ></script>
<script type="text/javascript" src="../../js/pc/common/WebUtil.js"></script>
<script type="text/javascript" src="../../js/pc/common/public.js" ></script>
<script type="text/javascript" src="../../js/pc/oppocard/ofrs.js" ></script>
<%
	String ap_id = request.getParameter("ap_id");
	String oper_no = request.getParameter("oper_no");
	String reward_oper_no = request.getParameter("reward_oper_no");
	String ap_type = request.getParameter("ap_type");
%>
</head>
<input type="hidden" id="ap_id" value="<%=ap_id %>"/>
<input type="hidden" id="oper_no" value="<%=oper_no %>"/>
<input type="hidden" id="reward_oper_no" value="<%=reward_oper_no %>"/>
<input type="hidden" id="ap_type" value="<%=ap_type %>"/>
<body>
<div class="container">
	<div class="c-main clearfix">
		<!-- left 业务选项 -->
			<div class="l l-width l-ie6">
				<div class="l-bao clearfix">
				    <div class="lt-top">
				    	<img class="lr-img" src="" alt="" width="100%" height="100%"/>
				    </div>
				    <div class="lt-bottom clearfix">
				    	<div class="l zuo zuo-ie6 l-ie6">
				    		<i></i>
				    	</div>
				    	<div class="l zhong l-ie6">
				    	<!-- lt-ul的宽度width=li的总个数 * 111 ie7有变-->
				    		<ul class="lt-ul clearfix" id="tariff_info">
				    			
				    		</ul>
				    	</div>
				    	<div class="l you  you-ie6 l-ie6"><i></i></div>
				    </div>
				</div>			
			</div>
			<!-- left 业务选项end -->
			<!-- right 资费详情 -->
			<div class="r r-width  r-ie6">
				<div class="r-bao">
					<div class="tip">套餐资费</div>
					<div>
						<ul class="r-ul">
							<li class="clearfix">
								<div class="l one">月&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;租</div>
								<div class="l two" id="rent"></div>
							</li>
							<li class="clearfix">
								<div class="l one">省内流量计费</div>
								<div class="l two" id="province_flow_charge"></div>
							</li>
							<li class="clearfix">
								<div class="l one">国内流量计费</div>
								<div class="l two" id="country_flow_charge"></div>
							</li>
							<li class="clearfix">
								<div class="l one">省内通话主被叫</div>
								<div class="l two" id="province_minutes_charge"></div>
							</li>
							<li class="clearfix">
								<div class="l one">国内通话主被叫</div>
								<div class="l two" id="country_minutes_charge"></div>
							</li>
							<li class="clearfix">
								<div class="l one">省内免费分钟数</div>
								<div class="l two" id="province_free_minutes"></div>
							</li>
							<li class="clearfix">
								<div class="l one">国内免费分钟数</div>
								<div class="l two" id="country_free_minutes"></div>
							</li>
							<li class="clearfix">
								<div class="l one">省内免费流量</div>
								<div class="l two" id="province_free_flow"></div>
							</li>
							<li class="clearfix">
								<div class="l one">国内免费流量</div>
								<div class="l two" id="country_free_flow"></div>
							</li>
							<li class="clearfix">
								<div class="l one">其&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;他</div>
								<div class="l two" id="other_info"></div>
							</li>
						</ul>
					</div>
				</div>
				<div class="tton">
					<div id="next_flow_step">立即申请</div>
				</div>
			</div>
			<!-- right 资费详情end -->		
		</div>
	</div>
</div>
</body>
</html>