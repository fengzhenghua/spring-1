<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@include file="../common/common.jsp"%>
  <%@ page import="com.tydic.unicom.util.DateUtil" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta charset="utf-8"/>
<meta name="viewport"
	content="width=device-width,minimum-scale=1.0,maximum-scale=1.0" />
<meta name="format-detection" content="telephone=no" />
<title>销售</title>
<script type="text/javascript" src="<%=fullPath%>js/jquery.jUploader-1.0.js"></script>
<script type="text/javascript" src="<%=fullPath%>js/ofrCodeConfig.js"></script>
<script type="text/javascript" src="<%=fullPath%>js/json2.js"></script>
<link href="<%=fullPath%>css/public_pc.css" rel="stylesheet" type="text/css"></link>

<script language="javascript" type="text/javascript">
	$(document).ready(function(){
	
		$("#ofr_id").click(function(){
		//$("tab_phone").bind("click",function(){
			document.getElementById("product").style.display  = "block";
			document.getElementById("product2").style.display  = "block";
			document.getElementById("authority").style.display  = "none";
			document.getElementById("oper_authority").style.display  = "none";
			$("#ofr_id").addClass("current");
			$("#roe_id").removeClass("current");
			$("#oper_role_id").removeClass("current");
		});	
		$("#roe_id").click(function(){
		//$("tab_phone").bind("click",function(){
			document.getElementById("authority").style.display  = "block";
			document.getElementById("product").style.display  = "none";
			document.getElementById("product2").style.display  = "none";
			document.getElementById("oper_authority").style.display  = "none";
			$("#roe_id").addClass("current");
			$("#ofr_id").removeClass("current");
			$("#oper_role_id").removeClass("current");
		});	
	
		  //限制图片上传类型
		  $.jUploader.setDefaults({
		            cancelable: true,
		            allowedExtensions: ['txt', 'gif','png'],
		            messages: {
		                upload: '上传',
		                cancel: '取消',
		                emptyFile: "{file} 为空，请选择一个文件.",
		                invalidExtension: "{file} 出错啦~上传照片支持{extensions}格式，核对一下图片格式再上传哈！",
		                onLeave: "文件正在上传，如果你现在离开，上传将会被取消。"
		            }
		        });
			function loadFile(){
			$('#loadFile').click(); 
			}
		
	});
			
		 
</script>




</head>

<body>

<div class="show" style="height:auto;">
    
    <div class="info">
    	<div class="detail_white">
        	<div class="title"></div>
            
            <div class="title_tab">        
                <span class="text_large24"></span>
            
                <div class="tab_menu">
                    <a href="###" class="current" id="ofr_id">产品配置</a>
                </div>
                <div class="tab_menu">
                    <a href="###" class="" id="roe_id">权限配置</a>
                </div>
                <!-- 新增PC段工号权限配置页面 -->
                <div class="tab_menu">
                    <a href="###" class="" id="oper_role_id">工号权限</a>
                </div>
            </div>   
            <div class="clear"></div> 
            <div class="line_red_top"></div>
        </div> 

    </div> 
    
    <!-- 产品配置 begin -->
    <div class="product" id="product" style="display:block;">
        <div class="info">
            <div class="title text_big">
                <span class="text_large24">1</span>查询
            </div>
            
            <ul class="detail">
                <div class="padding_blank10"></div>
                <div style="*padding-left:20px;">
                    <li class="list" style="*display:inline;width:910px;">
                        <div class="left">
                            <div class="left_lable">搜<span class="space24"></span>索：</div>
                            <input type="text"  value="请输入产品名称" onfocus="this.value=''" onblur="if(this.value==''){this.value='请输入产品名称'}" id="ofr_name" class="input_text width_18"></input>
                            <input type="text"  value="请输入产品ID" onfocus="this.value=''" onblur="if(this.value==''){this.value='请输入产品ID'}" id="product_id" class="input_text width_18"></input>
                        </div>
                        <!--  <input type="text" placeholder="订单编号" class="input_text width_15"></input>-->
                    </li>
                    <div class="padding_blank10"></div>
                    <li class="list" style="*display:inline;width:910px;">
                        <div class="left">
                            <div class="left_lable">操作时间： </div>
                            <div class="left">
                                <table width="60%" height="26" border="0" align="center" cellpadding="0" cellspacing="0">
                                    <tr>
                                        <td width="150"> <t:date id="date_begin" name="date_begin"  maxDateElId="date_end"  value=""  ></t:date></td>
                                        <td width="24" align="center">到</td>
                                        <td width="150"> <t:date id="date_end" name="date_end"   minDateElId="date_begin"  value=""  maxDate=""  ></t:date></td>
                                    </tr>
                                </table>
                            </div>
                        </div>
                        
                        <div class="left_data"><a href="#" id="query" class="search">查询</a></div>
                    </li>

                    <div class="padding_blank10"></div>
                    <li class="list" style="*display:inline;">
                        <div class="order_row">
                          <div class="order_cell" style="width:83px;">产品名称</div>
                            <div class="order_cell">产品描述</div>
                            <div class="order_cell" style="width:83px;">生效标识</div>                         
                            <div class="order_cell" style="width:83px;">产品类型</div>
                            <div class="order_cell" style="width:83px;">电信类型</div>
                            <div class="order_cell" style="width:83px;">BSS产品ID</div>                            
                            <div class="order_cell" style="width:83px;">生效时间</div>
                            <div class="order_cell" style="width:83px;">失效时间</div>
                            <div class="order_cell" style="width:83px;">产品ID</div>                          
                            <div class="order_cell" style="width:83px;">付费标识</div>
                        </div>
                    </li>
                    <div id="list">
                  
                    </div>
                    
                </div>
                
                <div class="padding_blank"></div>
                
            </ul>
            
        </div> 
        
        <div class="info" id="product2" >        
            <div class="title text_big">
                <span class="text_large24">2</span>批量处理
            </div>
            
            <ul class="detail">
                <div class="padding_blank10"></div>
                <div style="*padding-left:20px;">
                    <li class="list" style="*display:inline;">                   
                        <div class="left">
                          <div style="display:none;">                        
                           </div> 
                            <div class="left_lable"><input type="text" placeholder="选择大件" id="realpath" class="input_text width_20"></input></div>
                            <div class="left_data" id="uploadbutton">
                                <a href="javascript:void(0)" ><div class="input_button">读文件</div></a>
                            </div>
                             <div style="float:right;">
                                <a href="javascript:void(0)" onClick="insert();"><div class="input_button">批量处理</div></a>
                            </div>
                        </div>
                         <li class="list" style="*display:inline;">
                         <font color="red"> 导入文件说明：txt文档，导入字段内容跟展示顺序一致，字段间隔用空格，字段值不能包含有空格,多条数据用回车分隔，空字符串可以配置成null,失效时间和生效时间格式为：yyyy/MM/dd。</font>  
                         </li>
                       
                        </li>                     
                        
                        <li class="list" style="*display:inline;">
                        </li>
                       </div>
                       
                    <div class="padding_blank10"></div>
                    <li class="list" style="*display:inline;">
                        <div class="order_row">
                           <div class="order_cell" style="width:83px;">产品名称</div>
                            <div class="order_cell">产品描述</div>
                            <div class="order_cell" style="width:83px;">生效标识</div>                         
                            <div class="order_cell" style="width:83px;">产品类型</div>
                            <div class="order_cell" style="width:83px;">电信类型</div>
                            <div class="order_cell" style="width:83px;">BSS产品ID</div>                            
                            <div class="order_cell" style="width:83px;">生效时间</div>
                            <div class="order_cell" style="width:83px;">失效时间</div>
                            <div class="order_cell" style="width:83px;">产品ID</div>                          
                            <div class="order_cell" style="width:83px;">付费标识</div>
                        </div>
                    </li>
                     <div id="writelist">
                   
                    </div>               
                <div class="padding_blank"></div>
            </ul>
            
            <div class="padding_blank"></div>
        </div>
    </div>
    
    
    <!-- 产品配置 end -->
    
    <!-- 权限配置 begin -->
    <div class="authority" id="authority" style="display:none;">
        <div class="info">
            <div class="title text_big">
                <span class="text_large24">1</span>权限配置
            </div>
            
            <ul class="detail">
                <div class="padding_blank10"></div>
                <li class="list" style="line-height:28px;">
                    <div class="float_left">
                        <div class="left_lable">产品标识：</div>
                        <div class="left_data"><input  type="text" id="product_id2" placeholder="产品ID" class="input_text"></input></div>
                        <div class="left_lable">产品名称：</div>
                        <div class="left_data"><input  type="text" id="ofr_name2" placeholder="产品名称" class="input_text"></input></div>
                    </div>
                    
                    <div class="float_left">
                    	<span class="left_lable"></span>
                    	<div class="left_lable red">(*支持模糊查询*)<span class="space36"></span></div>
                    </div>
                    
                    <div class="float_left">
                    	<div class="left_data"><a href="#" id="query2" class="search">查询</a></div>
                        <div class="left_data"><span class=" space12"></span></div>
                        <!--  <div class="left_data"><a href="#" class="search_no_circle">取 消</a></div>-->
                    </div>
                </li>
                                
                <div class="padding_blank10"></div>
            </ul>
            
        </div> 
        
        <div class="info">
            <div class="title text_big">
                <span class="text_large24">2</span>查询结果显示
            </div>
            
            <ul class="detail">
            	<div class="padding_blank10"></div>
            	<div class="float_left" style="width:550px;padding-left:20px;*padding-left:20px;">
                    <li class="list" style="*display:inline;">
                        <div class="order_row" style="width:535px;">
                            <div class="order_cell gray_bgcolor" style="width:33px;">
                                <input type="checkbox" name="product_record" style="height:28px;"></input>
                            </div>
                            <div class="order_cell" style="width:99px;">
                                产品标识
                            </div>
                            <div class="order_cell">
                                产品主题
                            </div>
                            <div class="order_cell" style="width:266px;">
                                产品描述
                            </div>
                        </div>
                    </li>
              <div id="list2">      
   
                  </div>                     
                </div>
                
                <div class=" float_left" style="width:270px;">
                    <li class="list" style="width:268px;*display:inline;">
                        <div class="order_row" style="width:268px;">
                            <div class="order_cell gray_bgcolor" style="width:33px;">
                                <input type="checkbox" name="authority_record" style="height:28px;"></input>
                            </div>
                            <div class="order_cell" style="width:99px;">
                                角色标识
                            </div>
                            <div class="order_cell">
                                角色名
                            </div>
                        </div>
                    </li>
                    <li class="list" style="width:268px;*display:inline;">
                        <div class="order_row white_bgcolor" style="width:268px;">
                            <div class="order_cell gray_bgcolor" style="width:33px;">
                                <input type="checkbox" name="authority_record" value="1" style="height:28px;"></input>
                            </div>
                            <div class="order_cell gray_bgcolor_f8" style="width:99px;">
                                1
                            </div>
                            <div class="order_cell gray_bgcolor_f8">
                               系统管理员
                            </div>
                        </div>
                    </li>
                    <li class="list" style="width:268px;*display:inline;" >
                        <div class="order_row white_bgcolor" style="width:268px;">
                            <div class="order_cell gray_bgcolor" style="width:33px;">
                                <input type="checkbox" name="authority_record" value="2" style="height:28px;"></input>
                            </div>
                            <div class="order_cell white_bgcolor" style="width:99px;">
                                2
                            </div>
                            <div class="order_cell white_bgcolor">
                                稽核人员
                            </div>
                        </div>
                    </li>
                    <li class="list" style="width:268px;*display:inline;">
                        <div class="order_row white_bgcolor" style="width:268px;">
                            <div class="order_cell gray_bgcolor" style="width:33px;">
                                <input type="checkbox" name="authority_record"  value="3" style="height:28px;"></input>
                            </div>
                            <div class="order_cell gray_bgcolor_f8" style="width:99px;">
                                3
                            </div>
                            <div class="order_cell gray_bgcolor_f8">
                                营业厅经理
                            </div>
                        </div>
                    </li>
                </div>
    <div id="queryExistAuthority">           
     <div class="padding_blank"></div>
           <div class="title text_big">
                <span class="text_large24">3</span>已存在权限查询
            </div>
                <div class=" float_left" style="padding-left:20px;*padding-left:20px;">
                    <li class="list" style="width:436px;*display:inline;">
                        <div class="order_row" style="width:436px;">
                        	<div class="order_cell gray_bgcolor" style="width:33px;">
                                <input type="checkbox" name="" style="height:28px;"></input>
                            </div>
                            <div class="order_cell" style="">
                                产品标识
                            </div>
                            <div class="order_cell" style="">
                                角色标识
                            </div>
                            <div class="order_cell">
                                角色名
                            </div>
                        </div>
                    </li>
             <div id="list3">            

              </div>
                </div>
  			</div>
       
                <div class="padding_blank"></div>
            </ul>
            
            <div class="padding_blank"></div>
        </div>
   
    
     <div class="float_middle" style=" *padding-left:419px;">
                <a href="javascript:void(0)" id="addRule"><div class="input_button">分 配</div></a>
                <a href="javascript:void(0)" id="delRule"><div class="input_button">删 除</div></a>
                <a href="javascript:void(0)" id="queryRule"><div class="input_button">权限查询</div></a>
            </div>
     </div>
    
    
    <!-- 权限配置 end -->
    
    
    
    <!-- 工号权限配置 begin -->
    <div class="authority" id="oper_authority" style="display:none;">
        <div class="info">
            <div class="title text_big">
                <span class="text_large24">1</span>工号查询
            </div>
            
            <ul class="detail">
                <div class="padding_blank10"></div>
                <li class="list" style="line-height:28px;">
                    <div class="float_left">
                        <div class="left_lable">工号编码：</div>
                        <div class="left_data"><input type="text" id="oper_id" placeholder="请输入工号编码" class="input_text"></input></div>
                        <div class="left_lable">工号名称：</div>
                        <div class="left_data"><input type="text" id="oper_name" placeholder="请输入工号名称" class="input_text"></input></div>
                    </div>
                    
                    <div class="float_left">
                    	<span class="left_lable"></span>
                    	<div class="left_lable red">(*支持模糊查询*)<span class="space36"></span></div>
                    </div>
                    
                    <div class="float_left">
                    	<div class="left_data" id="query_oper_info"><a href="#" class="search">查询</a></div>
                        <div class="left_data"><span class=" space12"></span></div>
                        <!--  <div class="left_data"><a href="#" class="search_no_circle">取 消</a></div>-->
                    </div>
                </li>
                                
                <div class="padding_blank10"></div>
            </ul>  
        </div> 
        
        <div class="info">
            <div class="title text_big">
                <span class="text_large24">2</span>权限查询
            </div>
             <ul class="detail">
                <div class="padding_blank10"></div>
                <li class="list" style="line-height:28px;">
                    <div class="float_left">
                        <div class="left_lable">角色编码：</div>
                        <div class="left_data"><input type="text" id="role_id" placeholder="请输入角色编码" class="input_text"></input></div>
                        <div class="left_lable">角色名称：</div>
                        <div class="left_data"><input type="text" id="role_name" placeholder="请输入角色名称" class="input_text"></input></div>
                    </div>
                    
                    <div class="float_left">
                    	<span class="left_lable"></span>
                    	<div class="left_lable red">(*支持模糊查询*)<span class="space36"></span></div>
                    </div>
                    
                    <div class="float_left">
                    	<div class="left_data" id="query_role_info"><a href="#" class="search">查询</a></div>
                        <div class="left_data"><span class=" space12"></span></div>
                        <!--  <div class="left_data"><a href="#" class="search_no_circle">取 消</a></div>-->
                    </div>
                </li>
                                
                <div class="padding_blank10"></div>
            </ul>
            
            
            
        </div> 
        
        <div class="info">
            <div class="title text_big">
                <span class="text_large24">3</span>查询结果显示
            </div>
            
            <ul class="detail">
            	<div class="padding_blank10"></div>
            	<div class="float_left" style="width:628px;padding-left:20px;*padding-left:20px;">
                    <li class="list" style="*display:inline;">
                        <div class="order_row" style="width:600px;">
                            <div class="order_cell gray_bgcolor" style="width:33px;">
                                <input type="checkbox" name="product_record" style="height:28px;"></input>
                            </div>
                            <div class="order_cell" style="width:99px;">
                                工号编码
                            </div>
                            <div class="order_cell">
                                工号名称
                            </div>
                            <div class="order_cell" style="width:263px;">
                                所在部门
                            </div>
                            <div class="order_cell" style="width:67px;">
                                是否有效
                            </div>
                        </div>
                    </li>
                    
                    <!-- 详情展示 begin fhc-->
                    <div id="oper_info_list">
                    </div>
                    <!-- 详情展示 begin fhc-->
                </div>
                
                
              
                <div class=" float_left" style="width:270px;">
                    <li class="list" style="width:268px;*display:inline;">
                        <div class="order_row" style="width:268px;">
                            <div class="order_cell gray_bgcolor" style="width:33px;">
                                <input type="checkbox" name="authority_record" style="height:28px;"></input>
                            </div>
                            <div class="order_cell" style="width:99px;">
                                角色标识
                            </div>
                            <div class="order_cell">
                                角色名
                            </div>
                        </div>
                    </li>
                    <div id="role_info_list">                   
                </div>
                </div>
       
                <div class="padding_blank"></div>
            </ul>
            <div class="padding_blank"></div>
            
            <div class="float_middle" style=" *padding-left:419px;">
                <a href="javascript:void(0)"><div class="input_button" id="add_del_role">分 配</div></a>
                <a href="javascript:void(0)"><div class="input_button" id="del_role">删 除</div></a>
            </div>
            	
        </div>
    
    </div>
    <!-- 工号权限配置 end -->
    
    
    
    
  <div class="padding_blank"></div>
  <div class="clear"></div>

<div class="bg_mask" id="bg_mask">
	<iframe class="bg_mask_iframe"> </iframe>
</div>
<input type="hidden" name="jsonlist" id="jsonlist" ></input>
<input type="hidden" id="province_code"  value="${province_code}"></input>
</div>
</body>
</html>