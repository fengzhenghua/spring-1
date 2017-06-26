/**
* 系统报表控件
* Author chenly 
* 
*/

if (typeof (ReportManagers) == "undefined") ReportManagers = {};
(function($)
{
    $.fn.getReportManager = function()
    {
        return ReportManagers[this[0].id + "_Report"];
    };
    
    $.reportDefaults = $.reportDefaults || {};
    $.reportDefaults.Sets = {
        title: null,
        width: 'auto',                          //宽度值
        dataid:'id',
        columnWidth: null,                      //默认列宽度
        resizable: true,                        //table是否可伸缩
        single: false,							//单选
        url: false,                             //ajax url
        usePager: true,                         //是否分页
        pageBarStyle: 'default',                //分页工具栏样式，现取值：default/simple
        page: 1,                                //默认当前页
        total: 1,                               //总页面数
        pageSize: 10,                           //每页默认的结果数
        pageSizeOptions: [10, 50,100,1000,5000,10000],  //可选择设定的每页结果数
        parms: [],                         //提交到服务器的参数
        columns: [],                          //数据源
        onBeforeShowData: null,                  //显示数据前事件，可以通过reutrn false阻止操作
        onAfterShowData: null,                 //显示完数据事件
        onError: function(data){
            if(typeof ajaxErrCallBack != 'undefined') {
                ajaxErrCallBack(data);
            } else {
                alert("GRID数据加载出现错误，缺少错误处理函数。");
            }
        },                      
        renderDate: function(value)
        {
            var da;
            if (!value) return null;
            if (typeof value == 'object')
            {
                return value;
            }
            if (value.indexOf('Date') > -1)
            {
                da = eval('new ' + value.replace('/', '', 'g').replace('/', '', 'g'));
            } else
            {
                da = eval('new Date("' + value + '");');
            }
            return da;
        }
    };
    $.reportDefaults.ReportString = {
        errorMessage: '发生错误',
        pageTextMessage: 'Page',
        loadingMessage: '加载中...',
        findTextMessage: '查找',
        //pageStatMessage: '显示{from}-{to}，总数 {total}。每页：{pagesize}',
        pageStatMessage_default: '显示：{from}-{to} 总数：{total}',
        pageStatMessage_simple: '总数：{total}',
        initNoRecordMessage_default: '没有数据需要显示',
        initNoRecordMessage_simple: '',
        noRecordMessage: '没有符合条件的记录存在',
        isContinueByDataChanged: '数据已经改变,如果继续将丢失数据,是否继续?'
    };
    ///	<param name="$" type="jQuery"></param>
    $.reportAddFroms = function(rF, p)
    {
    	
    	var go = {
    					
    	};
    	  	
    	var po = {
    		init:function(){
    			po.initBody();
    			po.initHeader();
    		},
    		initBody:function(){
    			var htmlArr = [];
    			htmlArr.push(' <table width="1265" border="0" cellspacing="1" cellpadding="0" bgcolor="#E7E7E7">');
    			htmlArr.push('</table>');
    		},
    		initHeader:function(){
    			
    		}
    	};
    	
    	
    	po.init();
    	if (rF.id == undefined) rF.id = "ReportForm_" + new Date().getTime();
    	ReportManagers[grid.id + "_Report"] = g;
    };
    
    $.reportSetParms = function(p, fixedP)
    {
        p = $.extend({}, $.reportDefaults.Sets,$.reportDefaults.ReportString , p || {});
        if (p.url && p.data)
        {
            p.dataType = "local";
        }
        else if (p.url && !p.data)
        {
            p.dataType = "server";
        }
        else if (!p.url && p.data)
        {
            p.dataType = "local";
        }
        else if (!p.url && !p.data)
        {
            p.dataType = "local";
            p.data = [];
        }
        if (p.dataType == "local")
            p.dataAction = "local";
        if (fixedP)
        {
            p = $.extend(p, fixedP);
        }
        return p;
    };

    $.fn.reportForms = function(p)
    {
        var fixedP = {};
        p = p || {};
        p =  $.reportSetParms(p, fixedP);
        this.each(function()
        {
            $.reportAddFroms (this, p);
        });
        if (this.length == 0) return null;
        if (this.length == 1) return $(this[0]).getReportManager();
        var managers = [];
        this.each(function()
        {
            managers.push($(this).getReportManager());
        });
        return managers;
    };    
}(jQuery);