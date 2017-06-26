/*
 * core.js仅由main.jsp（父级iframe）引入，需注意
 * 子iframe调用父页面中的全局变量：window.parent.xxx;
 * 子iframe调用父页面的js事件：window.parent.xxx();
 * 子iframe访问父页面元素：$('#id', window.parent.document);
 */
ADC = {
	// 本地缓存对象
	_LocalCache: {},
	/*
	 * 取本地缓存code_list
	 * 使用方式:
	 * ADC.LocalCache({type_code, default_value}).fnGet(key);
	 * 如 ADC.LocalCache({type_code:typeCode, default_value:'未定义'}).fnGet(key);
	 */
	LocalCache: function(obj) {
		if (!this._LocalCache[obj.type_code]) {
			this.RemoteFetchCache(obj.type_code, false);
		}
		var codeList = this._LocalCache[obj.type_code];

		return {
			'fnGet': function(key) {
				if (key) { // 返回一个String
					var value = null;
					if (codeList) {
						$.each(codeList, function(i, item) {
							if (key == item.code_id){value = item.code_name;}
						});
					}
					return value || obj.default_value || '';
				} else { // 返回一个Array
					if (codeList) {
						return codeList || [];
					}
					return [];
				}
			}
		};
	},
	/*
	 * 预加载本地缓存
	 */
	PreLoadLocalCache: function() {
		$.each(Constant.typeCodePreLoadList, function(i, typeCode) {
			ADC.RemoteFetchCache(typeCode, true);
		});
	},
	/*
	 * 从服务器取code_list缓存到本地
	 */
	RemoteFetchCache: function(typeCode, async) {
		var data = {
			jsession_id: operInfo.jsession_id,
			type_code: typeCode
		};
		$.ajax({
			type: 'post',
			url:window.parent.operInfo.rest_host + 'rest/ArtificialTaskRest/getTaskQueryInfo',
			contentType: 'application/x-www-form-urlencoded; charset=utf-8',
			async: async,
			data: data,
			dataType: 'json',
			crossDomain: true == !(document.all),
			success: function(data) {
				if (data.respCode == '0000') {
					var args = data.args;
					if (args && args.code_list && args.code_list.length > 0) {
						ADC._LocalCache[typeCode] = args.code_list;
					} else {
						if (!async){$.message.info('code_list[type_code=' + typeCode + ']为空');}
					}
				} else {
					if (!async){$.message.error('获取服务器code_list[type_code=' + typeCode + ']失败：' + data.content);}
				}
			},
			error: function(data) {
				if (!async){$.message.error('获取服务器code_list[type_code=' + typeCode + ']失败');}
			}
		});
	}
};
