<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
	<script type="text/javascript" src="../js/jquery-easyui-1.3.5/jquery.min.js"></script>   
    <script type="text/javascript" src="../js/jquery-easyui-1.3.5/jquery.easyui.min.js"></script>   
    <script type="text/javascript" src="../js/jquery-easyui-1.3.5/locale/easyui-lang-zh_CN.js"></script> 
    <script type="text/javascript" src="../js/common/WebUtil.js"></script> 
    <link rel="stylesheet" type="text/css" href="../js/jquery-easyui-1.3.5/themes/default/easyui.css" />   
    <link rel="stylesheet" type="text/css" href="../js/jquery-easyui-1.3.5/themes/icon.css" />
    <link rel="stylesheet" type="text/css" href="../js/jquery-easyui-1.3.5/demo/demo.css">
</head>
<body>

<table id="ordModDefineTable"></table>
<div id='ordModDefineTB' style="padding:5px;">
	   <table style="padding:0 0 0 7px;">
	     <tr>
	       <td> 模板编码:  </td>
	       <td> <input id="mod_code" class="easyui-textbox" style="width:70px">  </td>
	       <td> 模板名称:  </td>
	       <td> <input id="mod_name" class="easyui-textbox" style="width:70px">  </td>		              
	      <td> <a href="#" class="easyui-linkbutton" iconcls="icon-search" onclick="obj.qry();">查询</a>  </td>  
	       <td><a href="#" class="easyui-linkbutton" plain="true" iconcls="icon-add" onclick="obj.add();">增加</a></td>
	       <td><a href="#" class="easyui-linkbutton" plain="true" iconcls="icon-save" onclick="SaveRow();">保存</a></td>
	       <td><a href="#" class="easyui-linkbutton" plain="true"iconcls="icon-remove" onclick="obj.del();">删除</a></td>
	     </tr>   
	   </table>	
	</div>
	
	<script type="text/javascript">
	var newcolumnsFlag=0;
	var dataForDatagrid;
	var currentPageSize=5;
	var currentPageNo=1;
	var totalResults;
	var jsession_id = "555555555555555555";
	function getData(){
		$('#ordModDefineTable').datagrid("loading");	
		var json_info = "{\"pageNo\":"+ currentPageNo+",\"pageSize\": "+currentPageSize +",\"ordModDefine\": {\"1\":\"1\"";
		if($('#mod_code').val()!=null){
			json_info +=",\"mod_code\": \""+$('#mod_code').val()+"\"";
		}
		if($('#mod_name').val()!=null){
			json_info +=",\"mod_name\": \""+$('#mod_name').val()+"\"";
		}				
		json_info +="}}";
	 $.ajax({
         type: "post",
         url: URLS.URL_API_HOST+"rest/ordModServiceRest/getOrdMod",
         contentType: "application/x-www-form-urlencoded; charset=utf-8",
         async:true,
         data: {
        	 jsession_id:jsession_id,
        	 oper_type:"103",
        	 json_info: json_info
         },
         dataType: "json",
         success: function(data){
        	// console.info("返回的结果集>>>>>>>>>>>>"+JSON.stringify(data));
        	 if (data.respCode=="0000") {
        		 if(data.args != null){
        			 if(data.args.json_info.ordModDefine!=null&&data.args.json_info.ordModDefine.length>0){
        	        		newcolumnsFlag=0;
        		     		$('#ordModDefineTable').datagrid("loadData",data.args.json_info.ordModDefine);	
        		     		$('#ordModDefineTable').datagrid("loaded");	
        		     		totalResults =  data.args.json_info.totalNumeOrdModDefine;
        		     		 var pager = $("#ordModDefineTable").datagrid("getPager"); 
        		     		      pager.pagination({
        		     			   pageNumber:currentPageNo,
        		     		        total:totalResults,
        		     		        onSelectPage:function (pageNo, pageSize) { 
        		     		         currentPageNo=pageNo;
        		     				 currentPageSize = pageSize;
        		     				 getData();	
        		     		        } 
        		     		      }); 
        	        }
        		 }else{
        			 $('#ordModDefineTable').datagrid("loaded");
        			 $.messager.alert('','无相关记录!','info');
        		 }
        	 }else{
        		 $('#ordModDefineTable').datagrid("loaded");
    			 $.messager.alert('','无相关记录!','info');
        	 }
        	     
        },
         error:function(data)
         {
        	 $('#ordModDefineTable').datagrid("loaded");
        	 $.messager.alert('','查询失败!','info');
         }
     });	
	}

	$(function(){
		$('#ordModDefineTable').datagrid({
			width:1138,
			height:497,
			fitColumns:true,
			rownumbers:false,
			singleSelect:true,
			columns:[[	
						{field:'mod_code',title:'订单模板编码',sortable:"true",width:50,editor:'text'},
						{field:'mod_name',title:'模板名称',sortable:"true",width:50,editor:'text'},
						{field:'mod_desc',title:'模板描述',sortable:"true",width:50,editor:'text'},	
						{field:'province_code',title:'省份',width:50,editor:{
							type:'combobox',
							options:{
                            	valueField: 'label',
                        		textField: 'value',
                                data:[{
                    				label: '*',
                    				value: '*'
                    			}
                    			],
                                required:true
                            }
						}},
						{field:'area_code',title:'地域',sortable:"true",width:50,editor:{
							type:'combobox',
							options:{
                            	valueField: 'label',
                        		textField: 'value',
                                data:[{
                    				label: '*',
                    				value: '*'
                    			}
                    			],
                                required:true
                            }
						}},
						{field:'state',title:'状态',sortable:"true",width:50,editor:{
							type:'combobox',
							options:{
                            	valueField: 'label',
                        		textField: 'value',
                                data:[{
                    				label: '100',
                    				value: '创建,待审核'
                    			},
                    			{
                    				label: '101',
                    				value: '审核通过,待发布'
                    			},
                    			{
                    				label: '102',
                    				value: '已发布' 
                    			},
                    			{
                    				label: '0',
                    				value: '失效' 
                    			}
                    			],
                                required:true
                            }
						}},
						{field:'json_module',title:'JSON格式',sortable:"true",width:50,editor:'text'},										
						{field:'create_staff',title:'创建人',sortable:"true",width:50,editor:'text'},
						{field:'create_time',title:'创建时间',sortable:"true",width:50,editor:'text'},
						{field:'interface_type',title:'接口类型',sortable:"true",width:50,editor:'text'},
						{field:'interface_param_json',title:'接口参数json',sortable:"true",width:50,editor:'text'},
	
			]],
			onSelect:function(index,row){
				if(newcolumnsFlag==1){
					var rows = $('#ordModDefineTable').datagrid('getRows');
					if(index!=rows.length-1){//选择某一行时，删除新增未保存的尾行
						$('#ordModDefineTable').datagrid('deleteRow', rows.length-1);
						newcolumnsFlag = 0;
						
						 totalResults--;
                    	 /*删除后刷新分页数据*/
                    	 var pager = $("#ordModDefineTable").datagrid("getPager"); 
         	     		      pager.pagination({
         	     			   pageNumber:currentPageNo,
         	     		        total:totalResults,
         	     		        onSelectPage:function (pageNo, pageSize) { 
         	     		         currentPageNo=pageNo;
         	     				 currentPageSize = pageSize;
         	     				 getData();	
         	     		        } 
         	     		      }); 
					}
					
				}
				/*选择某一行时，其他行的保存按钮隐藏*/
				var rowAll = $('#ordModDefineTable').datagrid('getRows');
				for(j=0;j<rowAll.length;j++){
					//rowAll[j]['action'] = '';	
					$('#ordModDefineTable').datagrid('refreshRow', j);
				}
				if(newcolumnsFlag ==1){/*选择新增行时，初始化数据*/ 
					row['mod_code'] ="";
					row['mod_name'] ="";
					row['mod_desc'] ="";
					row['province_code'] ="";
					row['area_code'] ="";
					row['state'] ="";
					row['json_module'] ="";
					row['create_staff'] ="";								
					row['create_time'] ="";
					row['interface_type'] ="";	
					row['interface_param_json'] ="";
				}
				/*row['action'] = '<a href="#" onclick="SaveRow()">保存</a>';*/	
				$('#ordModDefineTable').datagrid('refreshRow', index);
				$('#ordModDefineTable').datagrid('beginEdit', index);/*开启编辑模式*/
		    },
		  
			toolbar:'#ordModDefineTB',
			pagination:true,
			pageSize:5,
			pageList :[5,10,20]
		});
		setTimeout("getData()",100);
	  	
		obj={
		
				qry:function(){
					getData();
				},
				add:function(){
					if(newcolumnsFlag==0){
						newcolumnsFlag = 1;
						$('#ordModDefineTable').datagrid('appendRow',{});/*尾部新增一行*/
						
						 totalResults++;
                    	 /*新增后刷新分页数据*/
                    	 var pager = $("#ordModDefineTable").datagrid("getPager"); 
         	     		      pager.pagination({
         	     			   pageNumber:currentPageNo,
         	     		        total:totalResults,
         	     		        onSelectPage:function (pageNo, pageSize) { 
         	     		         currentPageNo=pageNo;
         	     				 currentPageSize = pageSize;
         	     				 getData();	
         	     		        } 
         	     		      }); 
						
						var rows = $('#ordModDefineTable').datagrid('getRows');
						$('#ordModDefineTable').datagrid('selectRow', rows.length-1);/*直接选择新增行，触发onSelect事件，进入编辑模式*/
					}	
				},
				del:function(){					
					DelRow();
				}
				
		}
		
		
	});
	
	function SaveRow(){/*保存按钮事件*/
		var row = $('#ordModDefineTable').datagrid('getSelected');
        if (row) {       
        	var rowIndex = $('#ordModDefineTable').datagrid('getRowIndex', row);
            var oper_type="";
            var json_info ="";
            $('#ordModDefineTable').datagrid('endEdit', rowIndex);
            //row['action'] = "";	/*隐藏保存按钮*/
            $('#ordModDefineTable').datagrid('refreshRow', rowIndex);
            if(row['mod_code'] == ""){
            	$.messager.alert('','订单模板编码为必填~','info');
            	return;
            }else if(row['mod_name'] == ""){
            	$.messager.alert('','模板名称为必填~','info');
            	return;
            }else if(row['province_code'] == ""){
            	$.messager.alert('','省份为必填~','info');
            	return;
            }else if(row['area_code'] == ""){
            	$.messager.alert('','地域为必填~','info');
            	return;
            }else if(row['state'] == ""){
            	$.messager.alert('','状态为必填~','info');
            	return;
            }
           
		if (newcolumnsFlag == 1) {/*新增数据*/
					newcolumnsFlag = 0;
					oper_type = "100";
					json_info = "{\"ordModDefine\":[{\"mod_code\": \""
							+ row['mod_code'] + "\",\"mod_name\": \""
							+ row['mod_name'] + "\",\"mod_desc\": \""
							+ row['mod_desc'] + "\",\"province_code\": \""
							+ row['province_code'] + "\",\"area_code\": \""
							+ row['area_code'] + "\",\"state\": \""
							+ row['state'] + "\",\"json_module\": \""
							+ row['json_module'] + "\",\"create_staff\": \""
							+ row['create_staff'] + "\",\"create_time\": \""
							+ row['create_time'] + "\",\"interface_type\": \""
							+ row['interface_type']
							+ "\",\"interface_param_json\": \""
							+ row['interface_param_json'] + "\"}]}";
				} else {/*修改数据*/
					oper_type = "101";
					json_info = "{\"ordModDefine\":[{\"mod_code\": \""
							+ row['mod_code'] + "\",\"mod_name\": \""
							+ row['mod_name'] + "\",\"mod_desc\": \""
							+ row['mod_desc'] + "\",\"province_code\": \""
							+ row['province_code'] + "\",\"area_code\": \""
							+ row['area_code'] + "\",\"state\": \""
							+ row['state'] + "\",\"json_module\": \""
							+ row['json_module'] + "\",\"create_staff\": \""
							+ row['create_staff'] + "\",\"create_time\": \""
							+ row['create_time'] + "\",\"interface_type\": \""
							+ row['interface_type']
							+ "\",\"interface_param_json\": \""
							+ row['interface_param_json'] + "\"}]}";
				}

				var data = {
					jsession_id : jsession_id,
					oper_type : oper_type,
					json_info : json_info
				};
				var str = JSON.stringify(data);
				console.info("str=" + str);

				$
						.ajax({
							type : "post",
							url : URLS.URL_API_HOST
									+ "rest/ordModServiceRest/orderModMainten",
							contentType : "application/x-www-form-urlencoded; charset=utf-8",
							async : true,
							data : data,
							dataType : "json",
							success : function(data) {
								if (newcolumnsFlag == 1) {

									if (data.args.json_info.ordModDefine != null
											&& data.args.json_info.ordModDefine.length > 0) {/*需要保存返回的ID，用于修改跟删除*/
										var id = data.args.json_info.ordModDefine[0].id;
										row['id'] = id;
									}
								}
								$.messager.show({
									title : "系统消息",
									msg : "保存成功",
									timeout : 5000,
									showType : 'slide'
								});
							},
							error : function(data) {
								$('#ordModDefineTable').datagrid('beginEdit',
										rowIndex);
								$.messager.alert('', '保存失败!', 'info');

							}
						});
			}

		}

		function DelRow() {/*删除一行*/
			var row = $('#ordModDefineTable').datagrid('getSelected');
			if (row) {
				var rowIndex = $('#ordModDefineTable').datagrid('getRowIndex',
						row);
				var json_info = "{\"ordModDefine\":[{\"mod_code\": \""
						+ row['mod_code'] + "\",\"mod_name\": \""
						+ row['mod_name'] + "\",\"mod_desc\": \""
						+ row['mod_desc'] + "\",\"province_code\": \""
						+ row['province_code'] + "\",\"area_code\": \""
						+ row['area_code'] + "\",\"state\": \"" + row['state']
						+ "\",\"json_module\": \"" + row['json_module']
						+ "\",\"create_staff\": \"" + row['create_staff']
						+ "\",\"create_time\": \"" + row['create_time']
						+ "\",\"interface_type\": \"" + row['interface_type']
						+ "\",\"interface_param_json\": \""
						+ row['interface_param_json'] + "\"}]}";
				var data = {
					jsession_id : jsession_id,
					oper_type : "102",
					json_info : json_info
				};
				var str = JSON.stringify(data);
				console.info("str=" + str);

				$
						.ajax({
							type : "post",
							url : URLS.URL_API_HOST
									+ "rest/ordModServiceRest/orderModMainten",
							contentType : "application/x-www-form-urlencoded; charset=utf-8",
							async : true,
							data : data,
							dataType : "json",
							success : function(data) {
								$('#ordModDefineTable').datagrid('deleteRow',
										rowIndex);
								totalResults--;
								/*删除后刷新分页数据*/
								var pager = $("#ordModDefineTable").datagrid(
										"getPager");
								pager.pagination({
									pageNumber : currentPageNo,
									total : totalResults,
									onSelectPage : function(pageNo, pageSize) {
										currentPageNo = pageNo;
										currentPageSize = pageSize;
										getData();
									}
								});
								if (newcolumnsFlag == 1) {
									newcolumnsFlag = 0;
								}
								$.messager.show({
									title : "系统消息",
									msg : "删除成功",
									timeout : 5000,
									showType : 'slide'
								});
							},
							error : function(data) {
								$('#ordModDefineTable').datagrid('beginEdit',
										rowIndex);
								$.messager.alert('', '删除失败!', 'info');

							}
						});
			}
		}
	</script>
</body>
</html>