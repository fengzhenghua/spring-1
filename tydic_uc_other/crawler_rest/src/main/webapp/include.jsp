
<script>
var oper_no = $UTIL.getUrlParam('oper_no');
var jsession_id = $UTIL.getUrlParam('jsession_id');
//var BASEURL = 'http://135.24.252.29:8104/crawler_rest';
var BASEURL = '/crawler_rest';
var TIMEOUT = 60000;
//1、判断是否登录，如果登录了就不需要显示 logindiv ，如果没有登录就显示
var myGrid;
var username=oper_no;
if(username==null)
	username="CF0540";
if (!window.console ){
    var names = ["log", "debug", "info", "warn", "error", "assert", "dir", "dirxml", "group", "groupEnd", "time", "timeEnd", "count", "trace", "profile", "profileEnd"];
    window.console = {};
    for (var i = 0; i < names.length; ++i)
        window.console[names[i]] = function() {}
}
</script>