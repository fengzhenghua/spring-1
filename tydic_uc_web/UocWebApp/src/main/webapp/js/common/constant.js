/*
 * constant.js仅由main.jsp（父级iframe）引入，需注意
 * 子iframe调用父页面中的全局变量：window.parent.xxx;
 * 子iframe调用父页面的js事件：window.parent.xxx();
 * 子iframe访问父页面元素：$('#id', window.parent.document);
 */
// 常量、静态值
var Constant = {
	typeCodePreLoadList: [
		'provinceCode', // 省份编码
		'orderState', // 订单状态
		'operCode', // 产品类型
		'prodCode', // 业务类型
		'payType', // 支付类型
		'acceptSystem', // 受理系统
		'cancelType', // 撤单类型
		'tacheCode', // 环节编码
		'certType',//证件类型
		'card_kind',//卡类型
		'first_month_rent',//首月计费方式
		'selfGetChannel',//自提渠道，
		'modiOrdType',//修订类型,
		'modiOrdState',//状态，
		'taskState'//任务状态

	],
	// 省份编码翻译
	provinceCode: function(key) {
		return ADC.LocalCache({type_code:'provinceCode', default_value:'未定义'}).fnGet(key);
	},
	// 订单状态翻译
	orderState: function(key) {
		return ADC.LocalCache({type_code:'orderState', default_value:'未定义'}).fnGet(key);
	},
	// 产品类型翻译
	operCode: function(key) {
		return ADC.LocalCache({type_code:'operCode', default_value:'未定义'}).fnGet(key);
	},
	// 业务类型翻译
	prodCode: function(key) {
		return ADC.LocalCache({type_code:'prodCode', default_value:'未定义'}).fnGet(key);
	},
	// 支付类型翻译
	payType: function(key) {
		return ADC.LocalCache({type_code:'payType', default_value:'未定义'}).fnGet(key);
	},
	// 受理系统翻译
	acceptSystem: function(key) {
		return ADC.LocalCache({type_code:'acceptSystem', default_value:'未定义'}).fnGet(key);
	},
	// 撤单类型翻译
	cancelType: function(key) {
		return ADC.LocalCache({type_code:'cancelType', default_value:'未定义'}).fnGet(key);
	},
	// 环节编码翻译
	tacheCode: function(key) {
		return ADC.LocalCache({type_code:'tacheCode', default_value:'未定义'}).fnGet(key);
	},
	certType: function(key) {
		return ADC.LocalCache({type_code:'certType', default_value:'未定义'}).fnGet(key);
	},
	card_kind: function(key) {
		return ADC.LocalCache({type_code:'card_kind', default_value:'未定义'}).fnGet(key);
	},
	first_month_rent: function(key) {
		return ADC.LocalCache({type_code:'first_month_rent', default_value:'未定义'}).fnGet(key);
	},
	selfGetChannel: function(key) {
		return ADC.LocalCache({type_code:'selfGetChannel', default_value:'未定义'}).fnGet(key);
	},
	modiOrdType: function(key) {
		return ADC.LocalCache({type_code:'modiOrdType', default_value:'未定义'}).fnGet(key);
	},
	modiOrdState: function(key) {
		return ADC.LocalCache({type_code:'modiOrdState', default_value:'未定义'}).fnGet(key);
	},
	//任务状态翻译
	taskState: function(key) {
		return ADC.LocalCache({type_code:'taskState', default_value:'未定义'}).fnGet(key);
	}
};
