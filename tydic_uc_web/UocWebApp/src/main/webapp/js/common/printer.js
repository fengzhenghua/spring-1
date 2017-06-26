$.extend({
	// 打印机
	printer: {
		// 自动打印回调函数的变量
		_autoPrintCallback: null,
		/*
		 * 自动打印
		 * option: {page:'jsp页面', param:'url(?号后面)参数'}
		 * callback: 回调函数
		 */
		autoPrint: function(option, callback) {
			var apLayer = $('body .auto_print_layer');
			if (apLayer.length == 0) {
				apLayer = $('<div class="auto_print_layer" style="display:none;"></div>');
				$('body').append(apLayer);
			}
			if (!option) {
				$.message.error('缺少必要参数：page');
				return;
			}
			if (!option.page) {
				$.message.error('缺少必要参数：page');
				return;
			}
			if (callback) {
				this._autoPrintCallback = callback
			}
			var url = option.page + (option.param ? '?' + option.param : '');
			apLayer.html('<iframe src="' + url + '" scrolling="yes" frameborder="0" style="width:100%; height:100%;"></iframe>');
		},
		/*
		 * 自动打印回调函数
		 * args: {type:bool型, content:String型}
		 */
		autoPrintCallback: function(args) {
			if (this._autoPrintCallback) {
				this._autoPrintCallback(args);
			}
		}
	}
});
