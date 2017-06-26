<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<title>中台订单中心管理系统</title>
    <script type="text/javascript" src="../../js/jquery-easyui-1.3.5/jquery.min.js"></script>   
    <script type="text/javascript" src="../../js/jquery-easyui-1.3.5/jquery.easyui.min.js"></script>   
    <script type="text/javascript" src="../../js/jquery-easyui-1.3.5/locale/easyui-lang-zh_CN.js"></script>
    <link rel="stylesheet" type="text/css" href="../../js/jquery-easyui-1.3.5/themes/default/easyui.css" />   
    <link rel="stylesheet" type="text/css" href="../../js/jquery-easyui-1.3.5/themes/icon.css" />
    
    <link rel="stylesheet" type="text/css" href="../../style/css/public.css" />
</head>
<body style="padding:10px;">
	<div id="main_layout" class="easyui-layout"  style="width:100%; height:100%;">  
        <!-- 北方 -->  
        <div data-options="region:'north',border:false" style="height: 60px;background-color:#CD0000;">  
            <span style="margin-left:0.5%;">  
                <font size="3" style="line-height: 2.0em;" color="white" >  
                    <b>中台订单中心管理系统|重庆分公司 </b>  
                </font>  
            </span>  
              
            <span style="float:right;margin-top:10px;margin-right:20px;">  
                <font size="3" color="white">欢迎 :Admin</font>      
                <a href="##" > <font size="3" color="brak">退出</font> </a>  
            </span>  
        </div>  
          
        <!-- 西方 -->  
		<div data-options="region:'west',split:true" title="导航菜单" style="width: 200px;">  
            <div class="easyui-accordion"  data-options="fit:true,border:false">
				<div data-options="iconCls:'tu0501'" title="参数配置管理"  id="panel">
					<div class="easyui-panel" fit="true" border="false">
	                    <ul class="easyui-tree"> 
	                        <li><a href="#" icon="tu1201" onclick="openMenu('ordModStateRule.jsp','服务订单状态关系维护')">服务订单状态关系维护</a></li>                                                
	                        <li><a href="#" icon="tu1201" onclick="openMenu('ordModTacheRule.jsp','服务订单环节关系维护')">服务订单环节关系维护</a></li>                        
	                        <li><a href="#" icon="tu1201" onclick="openMenu('ordModApp.jsp','订单模板应用维护')">订单模板应用维护</a></li>                        
	                        <li><a href="#" icon="tu1201" onclick="openMenu('ordModAttrCheckRule.jsp','订单模板参数校验规则维护')">订单模板参数校验规则维护</a></li>                        
	                        <li><a href="#" icon="tu1201" onclick="openMenu('ordModCheckRule.jsp','订单模板校验规则维护')">订单模板校验规则维护</a></li>                        
	                        <li><a href="#" icon="tu1201" onclick="openMenu('ordModAttrDefine.jsp','模板参数定义维护')">模板参数定义维护</a></li>                        
	                        <li><a href="#" icon="tu1201" onclick="openMenu('ordModDefine.jsp','订单模板定义维护')">订单模板定义维护</a></li>                        
	                        <li><a href="#" icon="tu1201" onclick="openMenu('ordModParaFieldRelation.jsp','订单参数与数据库表字段维护')">订单参数与数据库表字段维护</a></li>                        
	                       
	                        <li><a href="#" icon="tu1201" onclick="openMenu('procModApp.jsp','流程应用维护')">流程应用维护</a></li>                        
	                        <li><a href="#" icon="tu1201" onclick="openMenu('procModTacheLogin.jsp','环节关联工号维护')">环节关联工号维护</a></li>                        
	                        <li><a href="#" icon="tu1201" onclick="openMenu('procModTacheService.jsp','环节关联服务维护')">环节关联服务维护</a></li>                        
	                        <li><a href="#" icon="tu1201" onclick="openMenu('procModTacheBtn.jsp','环节功能维护')">环节功能维护</a></li>                        
	                        <li><a href="#" icon="tu1201" onclick="openMenu('procModTache.jsp','环节配置维护')">环节配置维护</a></li>                        
	                        
	                        <li><a href="#" icon="tu1201" onclick="openMenu('springMvc.jsp','服务订单环节关系维护test')">服务订单环节关系维护test</a></li>
	                        <li><a href="#" icon="tu1201" onclick="openMenu('operRest.jsp','测试1')">测试1</a></li>
	                    </ul>
	                </div>
				</div>
	            <div data-options="iconCls:'tu0501'" title="订单管理">
	                <div class="easyui-panel" fit="true" border="false">
	                    <ul class="easyui-tree">
	                        <li><a href="#" icon="tu1201" onclick="openMenu('orderQuery_old.jsp','订单查询旧')">订单查询旧</a></li>
	                        <li><a href="#" icon="tu1201" onclick="openMenu('orderQuery.jsp','订单查询')">订单查询</a></li>
	                        <li><a href="#" icon="tu1201" rel="Category/List.htm">测试3</a></li>
	                    </ul>
	                </div>
	            </div>
            	<div data-options="iconCls:'tu0501'" title="任务管理">
	                <div class="easyui-panel" fit="true" border="false">
	                    <ul class="easyui-tree">
	                        <li><a href="#" icon="tu1201" onclick="openMenu('task.jsp','任务列表')">任务列表</a></li>
	                        <li><a href="#" icon="tu1201" onclick="openMenu('monitor.jsp','监控')">监控</a></li>
	                    </ul>
	                </div>
	            </div>
            </div>  
            
        </div>
        <!-- 中间 -->  
        <div data-options="region:'center',title:'',iconCls:'icon-ok'">  
            <div id="tTabs" class="easyui-tabs" data-options="fit:true,border:false,plain:true">  
                <div title="首页" data-options="closable:true"  
                     style="overflow: auto; padding: 20px;"> 
                     
             <table border="0">
             	<tr>
             		<td colspan=2>
             		   <h1>  
							<div id="p" class="easyui-panel" title="员工信息"  style="width:1005px;height:150px;padding:10px;background:#fafafa;"  data-options="iconCls:'icon-save',collapsible:true">
						    	<p>panel content.</p>
						    	<p>panel content.</p>
						    </div>
						</h1>
             		</td>
             	</tr>
             		<td >
             		   <h1 >  
							<div id="p" class="easyui-panel" title="我的任务"  style="width:500px;height:130px;padding:10px;background:#fafafa;"  data-options="iconCls:'icon-save',collapsible:true">
						    	<p>panel content.</p>
						    	<p>panel content.</p>
						    </div>
						</h1>
             		</td>
	             	<td>
	             	  <h1>  
							<div id="p" class="easyui-panel" title="附属功能"  style="width:500px;height:130px;padding:10px;background:#fafafa;"  data-options="iconCls:'icon-save',collapsible:true">
						    	<p>panel content.</p>
						    	<p>panel content.</p>
						    </div>
						</h1>
	             	</td>
	             <tr>
		             <td>
		               <h1>  
							<div id="p" class="easyui-panel" title="天天新闻"  style="width:500px;height:130px;padding:10px;background:#fafafa;"  data-options="iconCls:'icon-save',collapsible:true">
						    	<p>panel content.</p>
						    	<p>panel content.</p>
						    </div>
						</h1>
		             </td>
		             <td>
		               <h1>  
							<div id="p" class="easyui-panel" title="天天新闻"  style="width:500px;height:130px;padding:10px;background:#fafafa;"  data-options="iconCls:'icon-save',collapsible:true">
						    	<p>panel content.</p>
						    	<p>panel content.</p>
						    </div>
						</h1>
		             </td>
		      
	             </tr>
             </table>         
         
                </div> 
            </div>
        </div>  
        <!-- 南方 -->  
        <div data-options="region:'south',border:false" style="height:30%;background-color:#CD0000;">  
            <div style="" align="center">  
                <font size="" color="white"> 2016 深圳天源迪科信息技术股份有限公司 版权所有 </font>  
            </div>  
        </div>  
    </div>  
</body> 

<script type="text/javascript">
$(document).ready(function(){  
    var height1 = $(window).height()-20;  
    $("#main_layout").attr("style","width:100%;height:"+height1+"px");  
    $("#main_layout").layout("resize",{  
        width:"100%",  
        height:height1+"px"  
    });  
});  
  
  
$(window).resize(function(){  
    var height1 = $(window).height()-30;  
    $("#main_layout").attr("style","width:100%;height:"+height1+"px");  
    $("#main_layout").layout("resize",{  
        width:"100%",  
        height:height1+"px"  
    });  
});

$("#mainLayout").layout('add', {
    region:'north',
    title:'header',
    split:true,
    disabled:true,
    noheader:true ,
  height:'80px',

});


$(function(){
	$("#panel").panel("collapse");//关闭
});

function openMenu(url,title){
	if(!$('#tTabs').tabs('exists',title)){		
		$('#tTabs').tabs('add',{
			cache:true,
		    title:title,
		    closable:true,
		    content:'<iframe scrolling="yes" frameborder="0" src="'+url+'" style="width:100%;height:100%;"></iframe>'
			
		});
	}else{
		$('#tTabs').tabs('select',title);
	}
	
}
</script>
</html>