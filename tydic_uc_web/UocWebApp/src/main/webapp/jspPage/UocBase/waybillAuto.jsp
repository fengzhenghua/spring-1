<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="utf-8">
	<title>快递免填单自动打印</title>
    <script type="text/javascript" src="../../js/common/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="../../js/common/public.js"></script>
    <script type="text/javascript" src="../../js/Plugin/jquery-barcode.js"></script>
	<script type="text/javascript" src="../../js/Plugin/jquery.PrintArea.js"></script>
    <script type="text/javascript" src="../../js/common/WebUtil.js"></script>
    <script type="text/javascript" src="../../js/UocBaseJs/waybillAuto.js"></script>
    
    <link rel="stylesheet" type="text/css" href="../../style/css/public.css" />
    <link rel="stylesheet" type="text/css" href="../../style/css/waybillAuto.css" />

	<!-- 插入打印控件 -->
	<OBJECT  ID="jatoolsPrinter" CLASSID="CLSID:B43D3361-D075-4BE2-87FE-057188254255" codebase="jatoolsPrinter.cab#version=8,6,0,0" width="0" height="0"></OBJECT>
</head>

<body>
	<div id="print_area">
		<!-- 快递单联 -->
		<div class="waybill" id="page1">
			<!-- 第一联 -->
			<div class="top">
				<!-- 第一行 -->
				<ul class="tr1">
					<!-- <li class="td1">
						<img id="SF_logo" src="../../style/images/SF_logo.png"/>
					</li> -->
					<!-- <li class="td2">
						<img id="SF_tel" src="../../style/images/SF_Tel.png"/>
					</li> -->
					<li class="td3" id="cod" style="display:none;">
						<span class="text1">COD</span>
					</li>
				</ul>
				<!-- 第二行 -->
				<ul class="tr2">
					<li class="td1">
						<div>
							<div id="barCode1"></div>
						</div>
						<div>
							<span id="barCodeNumber1"></span>
						</div>						
					</li>
					<li class="td2">
						<div class="business-type" id="businessType">顺丰次日</div>
						<div class="collection-on-delivery" style="display:none;" id="collectionOnDelivery"> 
							<div class="card_label">
								<span>代收货款</span>
							</div>
							<div class="card_no">
								<span>卡号：</span><span id="creditCardNumber"></span>
							</div>
							<div class="card_money">
								<span>￥</span><span id="collectionAmount">0</span><span>元</span>
							</div>
						</div>
					</li>
				</ul>
				<!-- 第三行 -->
				<ul class="tr3">
					<li class="td1">
						<p>目</p>
						<p>的</p>
						<p>地</p>
					</li>
					<li class="td2" id="destination"></li>
				</ul>
				<!-- 第四行 -->
				<ul class="tr4">
					<li class="td1">
						<p>收</p>
						<p>件</p>
						<p>人</p>
					</li>
					<li class="td2">
						<div class="comm"></div>
						<div class="comm-bao">
							<div class="line-height">
								<span class="receive-name" id="contactName1"></span>
								<span class="receive-tel" id="contactTel1"></span>
							</div>
							<div class="receive-addr" id="contactAddress1"></div>
						</div>
					</li>
				</ul>
				<!-- 第五行 -->
				<ul class="tr5">
					<li class="td1">
						<p>寄</p>
						<p>件</p>
						<p>人</p>
					</li>
					<li class="td2">
						<div class="table">
							<div class="cell">
								<div>
									<span class="send-name" id="sendName1"></span>
									<span class="send-tel" id="sendTel1"></span>
								</div>
								<div class="send-addr" id="sendAddr1"></div>
							</div>
						</div>
					</li>
					<li class="td3">					
						<span>自寄</span>
						<span>自取</span>						
					</li>
				</ul>
				<!-- 第六行 -->
				<ul class="tr6">
					<li class="td1">
						<div>
							<span>付款方式：</span><span>寄付月结</span>
						</div>
						<div>
							<span>月结账号：</span><span id="monthlyAccount"></span>
						</div>
						<div>
							<span>第三方地区：</span><span id="thirdPartyArea"></span>
						</div>
						<div>
							<span>实际重量：</span><span id="actualWeight"></span>
						</div>
					</li>
					<li class="td2">
						<div>
							<span>计费重量：</span><span id="chargingWeight"></span>
						</div>
						<div>
							<span>声明价值：</span><span id="declaredValue"></span>
						</div>
						<div>
							<span>保价费用：</span><span id="quotedPrice"></span>
						</div>
						<div>
							<span>包装费用：</span><span id="packagingCosts"></span>
						</div>
					</li>
					<li class="td3">					
						<div>
							<span>运费：</span><span id="freight"></span>
						</div>
						<div>
							<span>费用合计：</span><span id="totalCost"></span>
						</div>
						<div>
							<span>签回单号：</span><span id="returnLogisNo"></span>
						</div>
						<div>
							<span>转寄协议客户：</span><span id="forwardingProtocolClient"></span>
						</div>
					</li>
				</ul>
				<!-- 第七行 -->
				<ul class="tr7">
					<li class="td1">
						<p>托</p>
						<p>寄</p>
						<p>物</p>
					</li>
					<li class="td2">
						<div class="item-type" id="mailGoods1"></div>
					</li>
					<li class="td3">					
						<div>
							<span>收件员：</span><span></span>
						</div>
						<div>
							<span>寄件日期：</span><span class="send-date" id="sendDate"></span>
						</div>
						<div>
							<span>派件员：</span><span id="Courier"></span>
						</div>
					</li>
					<li class="td4">					
						<p class="text1">签名：</p>
						<p class="text2">月&nbsp;&nbsp;日</p>
					</li>
				</ul>
				<!-- 第八行 -->
				<ul class="tr8">
					<li id="prodInfo"></li>
				</ul>
			</div>
			<!-- 第二联 -->
			<div class="bottom">
				<!-- 第二联 第一行 -->
				<ul class="my-tr1">
					<li class="td1">
						<img src="../../style/images/SF_logo.png"/><br/>
						<img src="../../style/images/SF_Tel.png"/>	
					</li>
					<li class="td2">
						<div class="text1">
							<div id="barCode2"></div>
						</div>
						<div class="text2">
							<span id="barCodeNumber2"></span>
						</div>						
					</li>
				</ul>
				<!-- 第二联  第二行 -->
				<ul class="my-tr2">
					<li class="td1">
						<p>收</p>
						<p>件</p>
						<p>人</p>
					</li>
					<li class="td2">
						<div class="comm"></div>
						<div class="comm-bao">
							<div class="line-height">
								<span class="receive-name" id="contactName2"></span>
								<span class="receive-tel" id="contactTel2"></span>
							</div>
							<div class="receive-addr" id="contactAddress2"></div>
						</div>
					</li>
				</ul>
				<!-- 第二联 第三行 -->
				<ul class="my-tr3">
					<li class="td1">
						<p>寄</p>
						<p>件</p>
						<p>人</p>
					</li>
					<li class="td2">
						<div class="comm"></div>
						<div class="comm-bao">
							<div class="line-height">
								<span class="send-name" id="sendName2"></span>
								<span class="send-tel" id="sendTel2"></span>
							</div>
							<div class="send-addr" id="sendAddr2"></div>
						</div>
						
					</li>
				</ul>
				<!-- 第二联 第四行 -->
				<ul class="my-tr4" id="customArea">
				</ul>
				<!-- 第二联 第五行 -->
				<ul class="my-tr5">
					<li id="page1ProdInfo"></li>
				</ul>
			</div>
		</div>
		<!-- 回执单联 -->
		<div class="waybill" id="page2" style="display:none;">
			<!-- 第一联 -->
			<div class="top">
				<!-- 第一行 -->
				<ul class="tr1">
					<!-- <li class="td1">
						<img id="SF_logo" src="../../style/images/SF_logo.png"/>
					</li> -->
					<!-- <li class="td2">
						<img id="SF_tel" src="../../style/images/SF_Tel.png"/>
					</li> -->
					<li class="td3">
						<span class="text1">POD</span>
					</li>
				</ul>
				<!-- 第二行 -->
				<ul class="tr2">
					<li class="td1">
						<div>
							<div id="reBarCode1"></div>
						</div>
						<div>
							<span id="reBarCodeNumber1"></span>
						</div>						
					</li>
					<li class="td2">
						<div class="business-type" id="businessType">签单返还</div>
						<!-- <div class="collection-on-delivery" style="display:none;" id="collectionOnDelivery"> 
							<div class="card_label">
								<span>代收货款</span>
							</div>
							<div class="card_no">
								<span>卡号：</span><span id="creditCardNumber"></span>
							</div>
							<div class="card_money">
								<span>￥</span><span id="collectionAmount">0</span><span>元</span>
							</div>
						</div> -->
					</li>
				</ul>
				<!-- 第三行 -->
				<ul class="tr3">
					<li class="td1">
						<p>目</p>
						<p>的</p>
						<p>地</p>
					</li>
					<li class="td2" id="reDestination"></li>
				</ul>
				<!-- 第四行 -->
				<ul class="tr4">
					<li class="td1">
						<p>收</p>
						<p>件</p>
						<p>人</p>
					</li>
					<li class="td2">
						<div class="comm"></div>
						<div class="comm-bao">
							<div class="line-height">
								<span class="receive-name" id="reContactName1"></span>
								<span class="receive-tel" id="reContactTel1"></span>
							</div>
							<div class="receive-addr" id="reContactAddress1"></div>
						</div>
					</li>
				</ul>
				<!-- 第五行 -->
				<ul class="tr5">
					<li class="td1">
						<p>寄</p>
						<p>件</p>
						<p>人</p>
					</li>
					<li class="td2">
						<div class="table">
							<div class="cell">
								<div>
									<span class="send-name" id="reSendName1"></span>
									<span class="send-tel" id="reSendTel1"></span>
								</div>
								<div class="send-addr" id="reSendAddr1"></div>
							</div>
						</div>
					</li>
					<li class="td3">					
						<span>自寄</span>
						<span>自取</span>						
					</li>
				</ul>
				<!-- 第六行 -->
				<ul class="tr6">
					<li class="td1">
						<div>
							<span>付款方式：</span><span>寄付月结</span>
						</div>
					</li>
					<li class="td2">
						<div>
							<span>主单号：</span><span id="parentLogisNo"></span>
						</div>
					</li>
				</ul>
				<!-- 第七行 -->
				<ul class="tr7">
					<li class="td1">
						<p>托</p>
						<p>寄</p>
						<p>物</p>
					</li>
					<li class="td2">
						<div class="item-type" id="reMailGoods1"></div>
					</li>
					<li class="td3">					
						<div>
							<span>收件员：</span><span></span>
						</div>
						<div>
							<span>寄件日期：</span><span class="send-date" id="reSendDate"></span>
						</div>
						<div>
							<span>派件员：</span><span id="reCourier"></span>
						</div>
					</li>
					<li class="td4">					
						<p class="text1">签名：</p>
						<p class="text2">月&nbsp;&nbsp;日</p>
					</li>
				</ul>
				<!-- 第八行 -->
				<ul class="tr8">
					<li>腾讯大王卡：17623624886，ICCID：8986000288331767941</li>
				</ul>
			</div>
			<!-- 第二联 -->
			<div class="bottom">
				<!-- 第二联 第一行 -->
				<ul class="my-tr1">
					<li class="td1">
						<img src="../../style/images/SF_logo.png"/><br/>
						<img src="../../style/images/SF_Tel.png"/>	
					</li>
					<li class="td2">
						<div class="text1">
							<div id="reBarCode2"></div>
						</div>
						<div class="text2">
							<span id="reBarCodeNumber2"></span>
						</div>						
					</li>
				</ul>
				<!-- 第二联  第二行 -->
				<ul class="my-tr2">
					<li class="td1">
						<p>收</p>
						<p>件</p>
						<p>人</p>
					</li>
					<li class="td2">
						<div class="comm"></div>
						<div class="comm-bao">
							<div class="line-height">
								<span class="receive-name" id="reContactName2"></span>
								<span class="receive-tel" id="reContactTel2"></span>
							</div>
							<div class="receive-addr" id="reContactAddress2"></div>
						</div>
					</li>
				</ul>
				<!-- 第二联 第三行 -->
				<ul class="my-tr3">
					<li class="td1">
						<p>寄</p>
						<p>件</p>
						<p>人</p>
					</li>
					<li class="td2">
						<div class="comm"></div>
						<div class="comm-bao">
							<div class="line-height">
								<span class="send-name" id="reSendName2"></span>
								<span class="send-tel" id="reSendTel2"></span>
							</div>
							<div class="send-addr" id="reSendAddr2"></div>
						</div>
						
					</li>
				</ul>
				<!-- 第二联 第四行 -->
				<ul class="my-tr4" id="reCustomArea">
				</ul>
				<!-- 第二联 第五行 -->
				<ul class="my-tr5">
					<li id="page2ProdInfo"></li>
				</ul>
			</div>
		</div>
	</div>
</body>
</html>