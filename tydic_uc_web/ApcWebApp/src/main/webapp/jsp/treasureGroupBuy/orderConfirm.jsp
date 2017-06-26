<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../oppocard/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<title>订单确认</title>
<link href="../../css/pc/public.css" rel="stylesheet" type="text/css" />
<link href="../../css/pc/common/oppoPublic.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../../js/Plugin/jquery/jquery-1.11.1.min.js" ></script>
<script type="text/javascript" src="../../js/pc/common/public.js" ></script>
<script type="text/javascript" src="../../js/pc/common/WebUtil.js"></script>
<script type="text/javascript" src="../../js/pc/treasureGroupBuy/orderConfirm.js" ></script>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=yes" />
<%
	String ap_id = request.getParameter("ap_id");
	String tariff_id = request.getParameter("tariff_id");
	String tb_account = request.getParameter("tb_account");
	String receive_name = request.getParameter("receive_name");
	String receive_phone = request.getParameter("receive_phone");
	String receive_address = request.getParameter("receive_address");
	String accept_no = request.getParameter("accept_no");
	String ywb_id = request.getParameter("ywb_id");
	
	if(tb_account == null || "".equals(tb_account)){
		tb_account = "";
	}
	if(receive_phone == null || "".equals(receive_phone)){
		receive_phone = "";
	}
	if(accept_no == null || "".equals(accept_no)){
		accept_no = "";
	}
	if(receive_address == null || "".equals(receive_address)){
		receive_address = "";
	}
	else{
		receive_address = new String(receive_address.getBytes("ISO-8859-1"),"UTF-8");
	}
	if(receive_name == null || "".equals(receive_name)){
		receive_name = "";
	}
	else{
		receive_name = new String(receive_name.getBytes("ISO-8859-1"),"UTF-8");
	}
%>
</head>
<body>
<input type="hidden" id="ap_id" value="<%=ap_id %>"/>
<input type="hidden" id="tariff_id" value="<%=tariff_id %>"/>
<input type="hidden" id="tb_account" value="<%=tb_account %>"/>
<input type="hidden" id="receive_name" value="<%=receive_name %>"/>
<input type="hidden" id="receive_phone" value="<%=receive_phone %>"/>
<input type="hidden" id="receive_address" value="<%=receive_address %>"/>
<input type="hidden" id="accept_no" value="<%=accept_no %>"/>
<input type="hidden" id="ywb_id" value="<%=ywb_id %>"/>
<div class="main-page" style="display:block;" id="main-page">
	<ul class="content char4">
		<li class="c-li">
			<span class="c-one ">1</span>
            <span class="c-shuru ">根据国家实名制要求，请准确提供身份证信息</span>
        </li>
        <li class="c-li clearfix background">
			<div class="lianxi l ml-3 ml-ie6">姓名：<input class="input-ie6" type="text" placeholder="" value="" id="idName"></div>
			<div class="lianxi l">身份证：<input class="input-ie6" type="text" placeholder="" value="" id="idNumber"></div>
			<div class="lianxi l">联系电话：<input class="input-ie6" type="text" placeholder="" value="" id="connectPhone"></div>
		</li>
		
		<li class="c-li">
			<span class="c-one ">2</span>
            <span class="c-shuru">请选择号码</span>
        </li>
        <li class="c-li clearfix background">
			<div class="lianxi l lx-ml lx-ie6" id="selectNum">选择号码：
				<span class="fs phone" id="selected_number"></span>
				<span class="icon-right icon-more i-ml i-ml-right"></span>
			</div>
		</li>
		
		<li class="c-li">
			<span class="c-one ">3</span>
            <span class="c-shuru ">配送地址</span>
        </li>
		<li class="c-li clearfix background">
			<div class="lianxi l" id="addressDefault">收货人地址：
				<input class="input-ie6" type="text" placeholder="" value="" id="receiveAddress">
				<span class="i-hide" style="display:none;padding:0 5px;"></span>
				<span class="btn btn_primary" style="font-size:14px;padding:0 5px;" id="modifyBtn">修改</span>
			</div>
			<div id="addressChoice">
				<span class="lianxi ml-3">选择地区：</span>
	        	<select  id="provinceid">
	        		<option value="">请选择</option>
	        	</select>
	        	
	        	<select id="cityid">
	        		<option value="">请选择</option>
	        	</select>
	        	
	        	<select id="countyid">
	        		<option value="">请选择</option>
	        	</select>
        		<span class="addr addr-ie6"><input class="input-ie6 width-ie6" type="text" placeholder="请输入配送地址" value="" id="connect_addr"></span>
        		<span class="btn btn_primary" style="font-size:14px;" id="saveBtn">确定</span>
				<span class="btn btn_primary" style="font-size:14px;" id="cancleBtn">取消</span>
			</div>
		</li>
		<!-- <li class="c-li clearfix background">
			<div class="btn btn_primary" style="width:7%;" id="modifyBtn">修改</div>
			<div class="btn btn_primary" style="width:7%;" id="saveBtn">保存</div>
			<div class="btn btn_primary" style="width:7%;" id="cancleBtn">取消</div>
		</li> -->
		<li class="clear"></li>
		<li class="bottom_blank"></li>
	</ul>
	
	<ul class="content">
        <li class="list noborder relative-ie6" style=" padding-left:5px;">
            <div class="icon-left icon-add icon-left-ie6" id="check_contract"></div>
			<div class="mid">
				<div class="holder text-small">
                	<div class="agree agree-ie6">我已经阅读并同意<span class="red" id="btn_service_protocol">《客户入网服务协议》</span></div>
                </div>
			</div>
		</li>

		<li class="clear"></li>
		<li class="bottom_blank"></li>
	</ul>
	<div class="bbtn wc_width wc_ml">
		<div id="next_flow_step">完&nbsp;&nbsp;&nbsp;成</div>
	</div>
	
</div>

<!-- 用户协议 -->
<div class="pop-page" id="service_protocol_txt" style="display:none;">
	<div class="top">
		<div id="protocol_back" class="next next_blank">&lt;点此返回</div>
	</div>
	<ul class="content">
    	<div class="faq">
            <div class="faq_answer">
                我已经阅读并同意《客户入网服务协议》
            </div>
        </div>
        <div class="clear"></div>
        <div class="faq">
            <div class="faq_answer">
                立即提交，免费送货上门
本次活动由中国联通赞助，名额限时限量发放，若本阶段名额已满，
敬请谅解，一旦发现有恶意批量下单情况，我们将不予发货。
中国联通客户入网服务协议
            </div>
        </div>
        <div class="clear"></div>
        <div class="faq">
            <div class="faq_answer">
                甲方：______________________
            </div>
        </div>
        <div class="clear"></div>
        <div class="faq">
            <div class="faq_answer">
                乙方：______________________
            </div>
        </div>
		<div class="clear"></div>
        <div class="faq">
            <div class="faq_answer">
                根据《中华人民共和国合同法》、《中华人民共和国电信条例》等有关法律法规的规定，甲乙双方在平等、自愿、公平、诚信的基础上，基于对乙方通信服务的了解和需求，甲方自愿申请成为乙方客户，并达成协议如下：
第一条 入网要求及业务办理
（一）甲方办理入网、变更手续时，应提交以下登记资料：
1、个人客户：提供个人有效身份证件原件，委托他人办理的应同时提交本人及代理人有效身份证件原件及授权委托书。利用个人临时身份证件原件办理的，须同时提供公安部门出具的身份证领取凭证、身份信息辅助证件（驾照、医保卡或户口本等），不得委托他人办理。
2、单位客户：提供单位有效证件原件（或加盖公章的单位有效证件复印件）、经办人有效身份证件原件、加盖公章的单位介绍信等。
3、甲方户籍所在地（以有效身份证件的住址为准）或法定住所地（单位有效证件上单位注册地）不在本地的，应该按照乙方的要求办理相应的担保手续。
（二）甲方应使用国家给予入网许可标志的终端设备，终端设备应具备支持所选服务的相应功能，如无法支持所选服务，甲方应自行承担后果，并向乙方全额支付其所选服务的全部费用。
（三）甲方欲将业务号码过户时，应先交清所有费用，过户时须由双方持有效身份证件原件办理。如有特殊情况，甲方可委托他人办理，但应同时提交本人及代理人有效身份证件原件及授权委托书。
（四）在甲方通过过户成为新机主的情形下，如因原机主未亲自到场办理过户而导致原机主就此提出异议，甲方应无条件放弃因过户产生的全部权益，并承担由此对原机主及对乙方造成的一切损失。过户代理人对此承担连带责任。
（五）甲方采用担保人方式入网的，办理过户业务时，甲方须提交担保人出具的书面文件，说明担保人同意继续担保或要求终止担保。新机主应符合本协议第一条第（一）款的条件。
（六）甲方本人未到场，由代理人、经办人办理各类业务的，甲方应承担由此产生的责任与义务；代理人、经办人承担连带责任。
第二条 费用标准和费用交纳
（一）乙方应在国家电信资费主管部门允许的范围内设定资费标准、向客户明码标价、公告交费期限信息；甲方应在乙方明示的期限内足额交纳各项通信费用。
（二）甲方使用乙方提供的资费套餐方案，套餐有效期为一年（双方特殊约定的除外）。套餐到期后，双方如无异议，有效期逐年自动顺延。如无特殊约定，甲方在有效期内或到期后可更换套餐。
（三）如遇国家统一调整通信费用标准，则按国家统一规定执行。如遇乙方发布、调整资费，自乙方公告确定的生效日起执行新的资费标准。在乙方公告确定的生效日前，甲方未提出异议的视为同意，协议继续履行；甲方提出异议且未能与乙方达成一致的，甲方向乙方结清全部未付款项后本协议自动终止。
（四）若甲方为后付费用户，甲方应按时交纳通信费用。甲方未在约定期限内足额交纳通信费用的，乙方每日加收所欠费用的３‰作为违约金，并有权暂停甲方服务；甲方交清欠费和违约金后，除甲方明确提出不开通或已销号外，乙方应在24小时内恢复甲方服务。对前述情形，乙方将保留追缴欠费、违约金及向征信机构提供用户欠费信息的权利，也可用通知单、委托第三方等形式追缴欠费。
（五）若甲方为预付费用户，则必须保证账户上有充足的款项。如甲方账户满足消费条件的余额不足，乙方有权限制或停止向甲方提供服务，且乙方可不再另行通知。
（六）甲方定制第三方增值业务或其它收费业务，乙方可以代第三方向甲方收取信息费、功能费等，甲方使用第三方提供的增值业务或其它收费业务由第三方制定收费标准并公布。
（七）甲方如需开通国际业务、港澳台业务，应按乙方规定交纳相应费用。
（八）甲方不应以不知晓终端产生网络流量的原因（例如终端软件自动升级）为由，拒绝支付或要求减免相应费用。
第三条 客户权益
（一）网络服务
1、乙方在现有技术条件下的网络覆盖范围内向甲方提供通信服务。
2、乙方提供的网络服务应符合国家规定的通信质量标准。
（二）客户服务
1、乙方在承诺的网络覆盖范围内按照不低于《电信服务规范》的标准向客户提供服务。
2、乙方向甲方提供客户服务电话10010、网上营业厅www.10010.com等渠道，以便甲方了解乙方各项服务。乙方还应向甲方免费提供通话所在地（仅限大陆地区，不含港澳台）火警119、匪警110、医疗急救120、交通事故报警122等公益性电信服务。
3、乙方向甲方提供需要甲方支付月功能费的服务项目时，应征得甲方同意；乙方开通服务项目让甲方进行体验时，不收取体验服务项目月功能费。
4、对于甲方通信服务开通/关闭申请，乙方应在承诺的时限内操作完成（双方另有约定的除外）。乙方超过时限未及时开通/关闭的，应减免甲方由此产生的通信费用。
5、乙方依法保证甲方的信息资料安全、通信自由和通信秘密，但以下情形不应视为乙方违反本条约定：（1）因追缴欠费需要，向第三方机构和征信机构提供用户个人相应信息及欠费信息的；（2）为向甲方提供更好的服务，通过短信、彩信、wappush、电话、电子邮件、信函、微博、微信等方式向甲方发送业务服务信息或进行互动沟通的；（3）法律法规另有规定的。
第四条 风险与责任
（一）甲方应保证入网、变更登记资料真实有效、准确完整，并有义务配合乙方对登记资料进行查验。甲方登记资料如有变更，应主动办理变更手续。因甲方提供的客户资料不详、不实或变更后未及时通知乙方等原因，使乙方无法向甲方提供服务或甲方无法享受到乙方提供的相关服务，乙方无需向甲方承担任何责任。如乙方发现因甲方登记资料失实或者甲方未配合及时更正，乙方有权暂停、终止甲方服务，且乙方无需向甲方承担任何责任。
（二）甲方应妥善保管自己的用户号码、通信卡、终端、宽带账号，若发现丢失或被盗用，可及时拨打客户服务电话或到乙方营业网点办理暂时停机或修改账号密码手续；并可向公安机关报案，乙方应配合公安机关调查，但乙方不承担上述情形对甲方所造成的后果；如甲方将名下号码交予他人使用，因此引起的义务与责任仍由甲方承担。
（三）甲方应妥善管理其服务密码。服务密码是甲方办理业务的重要凭证，甲方入网后应立即修改初始服务密码。凡使用服务密码定制、变更或终止业务的行为均被视为甲方或甲方授权的行为，因此引起的义务与责任均由甲方承担。
（四）乙方按照现有技术标准为甲方提供通信服务，但乙方无法控制第三方利用各种手段从事违法行为等情况的发生，如因第三方的恶意行为造成甲方的损失，乙方对此将不承担相关责任。
（五）甲方使用固网及宽带业务时，未经有关部门批准许可，不得自行更改其使用性质，不得开设各类服务站点，不得利用计算机互联网络进行任何经营性服务活动，否则乙方有权停止服务，依法追缴各项费用和违约金，并不承担任何责任。
（六）甲方退网时，甲方租赁或乙方免费提供给甲方使用的终端设备，应归还乙方或按照甲乙双方相关约定处理。
（七）甲方在欠费情况下，乙方有权拒绝为甲方开办其他业务（含移网、固网、宽带等所有业务），直至甲方补交全部欠费及违约金。
（八）乙方在受理业务（服务）后，将进行线路资源核查，对于不具备开通条件的，在乙方告知甲方后，本协议自动终止，乙方退还甲方已交纳费用，但不承担其他责任。
（九）在固定电话开通并正常使用前（含新装、移机、改号等），甲方不能将已选中号码通知他人或进行宣传，否则由此所造成的后果及损失，由甲方自行承担。
（十）甲方所办理的数据业务下行速率标称值仅为乙方提供的数据业务下行速率最高值，乙方不能保证在任何情况下均能达到标称值，甲方对此表示知悉并认可。
（十一）按照公平使用原则，乙方将对甲方的移动数据流量进行封顶限制，甲方每月的移动数据流量达到或超出流量封顶额度时，乙方可暂停甲方当月的上网服务，次月自动恢复开通。
（十二）甲方未付的通信费用达到信用额度时（信用额度是指用户可以用于透支消费的最高通信费用额度），应及时补充交纳通信费用；当甲方未付的通信费用超过信用额度时，乙方有权暂停甲方网络服务（超过信用额度停机不受约定交费期限的限制）。
（十三）甲方发送违法或其他违反公序良俗内容的信息，或未经接收客户同意大量发送商业广告信息的，乙方有权依据行业主管部门、有关行政机关或客户举报、投诉关闭甲方信息发送功能，或暂停服务。
（十四）因甲方原因造成的通信卡密码丢失、锁卡或被他人获取造成的损失，乙方不承担责任，甲方不能以此为由拒绝按本协议约定支付通信费用。
第五条 违约责任
（一）一方违约给对方造成损失的，应当依法承担赔偿责任，但违约方应承担的赔偿损失的责任范围不包括守约方未实现的预期利润或利益、商业信誉的损失、丢失的数据本身及因数据丢失引起的损失、守约方对第三方的责任及其他间接损失。
（二）因不可抗力导致本协议部分或全部不能履行的，双方可部分或全部免除责任。
第六条 协议的变更与解除
（一）乙方在本协议外以公告、使用手册、资费单页等书面形式公开做出的服务承诺，甲方办理各类业务所签署的表单、业务协议、须知等均自动成为本协议的补充协议；与本协议冲突部分以补充协议为准，补充协议中未约定部分以本协议为准。
（二）甲方要求终止服务（双方另有约定的除外）申请办理拆机或销户的，如甲方预存费用不足，则应按照乙方要求预存一定数额的通信费，次月按照乙方业务规定和双方约定结清相关费用。
（三）有下列情形之一的，乙方有权单方解除协议，收回号码或账号并终止服务，并保留追究甲方违约责任的权利：
1、甲方提供的有效证件（包括代理人/经办人提供的有效身份证件）虚假、不实；
2、甲方自行安装未取得入网许可或可能影响网络安全或网络服务质量设备的；
3、甲方以担保等方式取得号码使用权的，如担保人违反保证条款或有确切证据证明担保人无能力履行保证责任的；
4、甲方未办理相关手续，自行改变电信业务使用性质或私自转让租用权的；
5、甲方欠费停机后3个月仍未交清通信费用和违约金的;
6、业务（服务）超过约定有效期的；
7、该号码被国家司法机关认定用于违法犯罪活动或其它不当用途的；
8、乙方收到国家行政管理部门通知要求停止甲方服务的；
9、预付费产品在约定期限内未激活的；
10、法律法规规定的其他情形。
（四）因技术进步或国家政策等原因导致本协议（部分或全部）无法继续履行的，乙方保留对电信业务（服务）做出调整的权利，调整前乙方应按照有关规定发布公告并提出相应解决方案。甲方可就解决方案与乙方协商，但不得要求乙方继续履行本协议。
第七条 协议争议
有关协议争议，双方可沟通协商解决；协商不成的，甲方可向当地通信管理局或消费者协会申请进行调解；任何一方均可向乙方住所地的人民法院起诉。
第八条 协议生效
本协议自业务受理之日起生效，有效期一年。协议到期后，双方如无异议，有效期以一年为周期自动顺延。
            </div>
        </div>

		<div class="clear"></div>
        <div class="faq">
            <div class="faq_answer">
                中国联通客户移网业务优惠活动业务协议
            </div>
        </div>
		<div class="clear"></div>
        <div class="faq">
            <div class="faq_answer">
                甲方：______________________
            </div>
        </div>
        <div class="clear"></div>
		<div class="faq">
            <div class="faq_answer">
                乙方：中国联合网络通信有限公司XX分公司
            </div>
        </div>
        <div class="clear"></div>
		<div class="faq">
            <div class="faq_answer">
                本协议作为《中国联通客户入网服务协议》的补充协议，甲乙双方在平等自愿、公平诚信的基础上，基于对乙方移网通信服务的了解和需求，达成协议如下：
第一条 甲方所选择的移网业务套餐及相关资费、移网业务优惠活动及协议期限等内容详见《中国联合网络通信有限公司XX分公司综合业务受理单》。
第二条 甲方自愿参加乙方的移网业务优惠活动。移网业务优惠活动是指乙方通过“提供专属优惠权益、存费送费、赠送业务或礼品”等方式向甲方提供各类优惠通信产品。专属优惠权益包括一次性话费赠送，特定条件下定向流量免费等。只有甲方在协议期内（协议期详见综合业务受理单）连续在网使用乙方通信服务的情况下，甲方方可享受乙方提供的各项优惠政策、优惠权益赠送。甲方每月套餐内、外上网流量累计使用到达40GB时，乙方将自动关闭上网功能，次月自动打开。
第三条 使用条件
（一）本协议中甲方预存款可延期消费。乙方赠送的话费用于抵扣甲方消费的通信费用（不含国际业务、港澳台业务和第三方合作SP业务所产生的费用），可延期消费，不可退还。
（二）协议期内甲方有套餐变更需求时，变更后的套餐须不低于本协议中约定的套餐，不低于甲方在本协议中承诺的月最低消费（如有）。套餐变更时，甲方需到乙方指定营业渠道办理套餐变更。
第四条 违约责任及承担
（一）甲方违约责任
1、甲方在协议期内发生欠费停机、销户等行为时，则甲方构成违约。
2、对于因逾期欠费导致停机满3个月的，乙方有权对甲方用户号码进行销户。
3、甲乙双方一致确认，甲方出现本协议所述的违约情形时，甲方应向乙方支付违约金以补偿乙方的损失，乙方保留对甲方追缴欠费和违约金的权利。违约金包括但不限于：因用户欠费发生的逾期付款违约金，以及乙方在本协议中所给予甲方的优惠等。
（二）乙方违约责任
乙方构成违约的，应按《中华人民共和国电信条例》及其他有关规定，承担违约责任。
第五条 协议生效
本协议自业务受理之日起生效，有效期一年。本协议到期时，如甲乙双方均未提出对套餐资费的修改要求，将继续执行本协议约定的套餐资费。
（甲方承诺：本人已经充分、完整阅读并理解本协议所述全部条款及条件。）
            </div>
        </div>
		<div class="clear"></div>
		<div class="faq">
            <div class="faq_answer">
                乙方：中国联合网络通信有限公司XX分公司
            </div>
        </div>
        <div class="clear"></div>
		<div class="faq">
            <div class="faq_answer">
                甲方代理人或经办人：（签字或盖章）
            </div>
        </div>
        <div class="clear"></div>
		<div class="faq">
            <div class="faq_answer">
                甲方用户号码：
            </div>
        </div>
        <div class="clear"></div>
		<div class="faq">
            <div class="faq_answer">
                签署日期： 年 月 日    签署日期： 年 月 日
            </div>
        </div>

		<li class="clear"></li>
		<li class="padding_blank"></li>
	</ul>
</div>

<!-- 选择号码-模态框 -->
	<div class="modal_layer" belong="mask" id="selectNumber">
		<div class="bg_cover"></div>
		
		<div class="modal_box">
			<div class="modal_top" style="padding:10px 0;border-bottom:0;">
				<span class="modal_title" style="margin-left:26px;">选择号码</span><span class="modal_close">x</span>
				<div style="background:#f2f2f2;height:70px;margin-top:10px;text-align:center;line-height:70px;border-top:1px solid #ccc;border-bottom:1px solid #ccc;">
					<input id="searchNumberCondition" style="border:1px solid black;border-radius:4px;padding-left:5px;background:#fff;border:1px solid #ccc;" type="text" maxlength="6" value="" placeholder="请输入0-6位数字查询"/>
					<a href="javascript:void(0);" id="searchNumber" class="btn btn_primary" style="height:30px;line-height:30px;padding:0 5px;">查询</a>
				</div>
			</div>
			<div class="modal_middle" style="bottom:10px;top:92px;z-index:-1;">
				<div class="detail_item">
					<ul class="detail_info_body mtop15">
						<li class="line">
							<div class="width100per">
								<ul class="number clearfix" id="number">
								   
								</ul>
							</div>
						</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<!-- 选择号码-模态框end -->
	
	<!-- 微信支付 -->
	<div class="modal_layer" belong="mask" id="WXPay">
		<div class="modal_bg"></div>
		
		<div class="modal_box">
			<div class="modal_top">
				<span class="modal_title">微信支付</span><!-- <span class="modal_close">x</span> -->
			</div>
			<div class="modal_middle" style="bottom:10px;">
				<div class="QR_code">
	        		<img src="" id="PIC_QR"/>
	        	</div>
	        	<div class="padding_blank10"></div>
		        <div class="float_center">
		        	<div class="red">微信支付：<span><span id="fee_all"></span> 元</span></div>
		            <div class="padding-lr" id="tip1">请用手机的微信扫描屏幕上的二维码支付；</div>
		            <div class="padding-lr" id="tip2">支付完成前请不要关闭此页面。</div>
		            <div id="wx_btn" class="next next_blank">重新下单</div>
		        </div>
		        <!-- <div class="bottom" style="display:none" id="re_do">
					<div id="re_create_order" class="next next_blank">重新下单</div>
				</div> -->
			</div>
		</div>
	</div>
<!-- <div class="modal_layer" belong="mask" id="WXPay">
	<div class="top_blank"></div>
	 
	<ul class="content">
    	<div class="padding_blank"></div>
        
    	<div class="QR_code">
        	<img src="" id="PIC_QR"/>
        </div>
        <div class="padding_blank10"></div>
        <div class="float_center">
        	<div class="red">微信支付：<span><span id="fee_all"></span> 元</span></div>
            <div class="padding-lr" id="tip1">请用手机的微信扫描屏幕上的二维码支付；</div>
            <div class="padding-lr" id="tip2">支付完成前请不要关闭此页面。</div>
        </div>
        
        

		<li class="clear"></li>
		<li class="padding_blank"></li>
	</ul>
	<div class="bottom" style="display:none" id="bottom_done">
		<div id="next_flow_step" class="next next_blank">完 成</div>
	</div>
	<div class="bottom" style="display:none" id="re_do">
		<div id="re_create_order" class="next next_blank">重新下单</div>
	</div>
</div> -->
<div class="dialog_small gray-bgcolor" id="qr_code_pop" style="display:none;">
	<ul class="content">
    	<div class="gray-bgcolor">
        	<img src="" id="PUBLIC_QR"/>
		</div>
        <!-- <div class="padding_blank10"></div> -->
        <div class="float_center">
        	<div class="padding-lr" style="font-size:10px;color:red;" id="order_success_tip"></div>
            <div class="padding-lr" style="font-size:10px;color:red;">请扫二维码关注微信公众号，用以完成后续开户激活流程。</div>
            <div class="padding-lr" style="font-size:10px;color:red;">若已关注该公众号，请忽略。</div>
        </div>
        <div class="btn"><a href="###" id="close_all_page">关闭</a></div>
	</ul>
</div>
<div id="mask" class="mask"></div> 
</body>
</html>