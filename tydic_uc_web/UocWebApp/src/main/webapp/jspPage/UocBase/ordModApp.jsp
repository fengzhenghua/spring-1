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
<table id="ordModAppTable"></table>
<div id='ordModAppTB' style="padding:5px;">
	   <table style="padding:0 0 0 7px;">
	     <tr>
	       <td> 模板编码:  </td>
	       <td> <input id="mod_code" class="easyui-textbox" style="width:70px">  </td>
	       <td> 模板用途:  </td>
	       <td> <input id="mod_used" class="easyui-textbox" style="width:70px">  </td>
	       <td> 对应业务:  </td>
	       <td> <input id="oper_code" class="easyui-textbox" style="width:70px">  </td>
	       <td> 环节:  </td>
	       <td> <input id="tache_id" class="easyui-textbox" style="width:70px">  </td>
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
	$(function(){
		$("#cc").combobox({
			data:[{
				label: 'java',
				value: 'Java'
			},
			{
				label: 'java',
				value: 'Java'
			}
			],
			valueField:"id",
			textField:"text"
			});
	});
	
	function getData(){
		$('#ordModAppTable').datagrid("loading");	
		var json_info = "{\"pageNo\":"+ currentPageNo+",\"pageSize\": "+currentPageSize +",\"param\": {\"1\":\"1\"";
		if($('#mod_code').val()!=null){
			json_info +=",\"mod_code\": \""+$('#mod_code').val()+"\"";
		}
		if($('#mod_used').val()!=null){
			json_info +=",\"mod_used\": \""+$('#mod_used').val()+"\"";
		}
		if($('#oper_code').val()!=null){
			json_info +=",\"oper_code\": \""+$('#oper_code').val()+"\"";
		}
		if($('#tache_id').val()!=null){
			json_info +=",\"tache_id\": \""+$('#tache_id').val()+"\"";
		}
		 json_info +="}}";
	 $.ajax({
         type: "post",
         url: URLS.URL_API_HOST+"rest/ordModServiceRest/getOrdModApp",
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
        			 if(data.args.json_info.ordModApp!=null&&data.args.json_info.ordModApp.length>0){
        	        		newcolumnsFlag=0;
        		     		$('#ordModAppTable').datagrid("loadData",data.args.json_info.ordModApp);	
        		     		$('#ordModAppTable').datagrid("loaded");	
        		     		totalResults =  data.args.json_info.totalNume;
        		     		 var pager = $("#ordModAppTable").datagrid("getPager"); 
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
        			 $('#ordModAppTable').datagrid("loaded");
        			 $.messager.alert('','无相关记录!','info');
        		 }
        	 }else{
        		 $('#ordModAppTable').datagrid("loaded");
    			 $.messager.alert('','无相关记录!','info');
        	 }
        	     
        },
         error:function(data)
         {
        	 $('#ordModAppTable').datagrid("loaded");
        	 $.messager.alert('','查询失败!','info');
         }
     });	
	}

	$(function(){
		$('#ordModAppTable').datagrid({
			width:1138,
			height:497,
			fitColumns:true,
			rownumbers:false,
			singleSelect:true,
			columns:[[
						/*{field:'action',title:'',width:5,align:'center'},*/
						{field:'province_code',title:'省份',sortable:"true",width:50,editor:'text'},    
						{field:'area_code',title:'地域',sortable:"true",width:50,editor:'text'},
						{field:'mod_code',title:'订单模板编码',sortable:"true",width:50,editor:'text'},
						/* {field:'mod_used',title:'模板用途',sortable:"true",width:50,editor:'text'}, */
						{field:'mod_used',title:'模板用途',sortable:"true",width:50,
						/* formatter:function(value,row){
							 return row.productname || value;
                        }, */
                        editor:{
                            type:'combobox',
                            options:{
                            	valueField: 'label',
                        		textField: 'value',
                                data:[{
                    				label: '100',
                    				value: '入参模板'
                    			},
                    			{
                    				label: '101',
                    				value: '出参模板'
                    			}
                    			],
                                required:true
                            }
                        }},
						{field:'used_system',title:'使用系统',sortable:"true",width:50,editor:'text'},
						{field:'used_service',title:'使用接口或服务',sortable:"true",width:50,editor:'text'},
						{field:'oper_code',title:'对应业务',sortable:"true",width:50,editor:'text'},
						{field:'tache_id',title:'环节',sortable:"true",width:50,editor:'text'},
						{field:'oper_limit_time',title:'处理时限',width:50,editor:'text'},					
						{field:'id',title:'id',hidden:'true'},
	
			]],
			onSelect:function(index,row){
				if(newcolumnsFlag==1){
					var rows = $('#ordModAppTable').datagrid('getRows');
					if(index!=rows.length-1){//选择某一行时，删除新增未保存的尾行
						$('#ordModAppTable').datagrid('deleteRow', rows.length-1);
						newcolumnsFlag = 0;
						
						 totalResults--;
                    	 /*删除后刷新分页数据*/
                    	 var pager = $("#ordModAppTable").datagrid("getPager"); 
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
				var rowAll = $('#ordModAppTable').datagrid('getRows');
				for(j=0;j<rowAll.length;j++){
					//rowAll[j]['action'] = '';	
					$('#ordModAppTable').datagrid('refreshRow', j);
				}
				if(newcolumnsFlag ==1){/*选择新增行时，初始化数据*/ 
					row['mod_code'] ="";
					row['mod_used'] ="";
					row['province_code'] ="";
					row['area_code'] ="";
					row['used_system'] ="";
					row['used_service'] ="";
					row['oper_code'] ="";
					row['tache_id'] ="";
					row['oper_limit_time'] ="";				
					row['id'] ="";				
				}
				/*row['action'] = '<a href="#" onclick="SaveRow()">保存</a>';*/	
				$('#ordModAppTable').datagrid('refreshRow', index);
				$('#ordModAppTable').datagrid('beginEdit', index);/*开启编辑模式*/
		    },
		  
			toolbar:'#ordModAppTB',
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
						$('#ordModAppTable').datagrid('appendRow',{});/*尾部新增一行*/
						
						 totalResults++;
                    	 /*新增后刷新分页数据*/
                    	 var pager = $("#ordModAppTable").datagrid("getPager"); 
         	     		      pager.pagination({
         	     			   pageNumber:currentPageNo,
         	     		        total:totalResults,
         	     		        onSelectPage:function (pageNo, pageSize) { 
         	     		         currentPageNo=pageNo;
         	     				 currentPageSize = pageSize;
         	     				 getData();	
         	     		        } 
         	     		      }); 
						
						var rows = $('#ordModAppTable').datagrid('getRows');
						$('#ordModAppTable').datagrid('selectRow', rows.length-1);/*直接选择新增行，触发onSelect事件，进入编辑模式*/
					}	
				},
				del:function(){					
					DelRow();
				}
				
		}
		
		
	});
	
	function SaveRow(){/*保存按钮事件*/
		var row = $('#ordModAppTable').datagrid('getSelected');
        if (row) {       
        	var rowIndex = $('#ordModAppTable').datagrid('getRowIndex', row);
            var oper_type="";
            var json_info ="";
            $('#ordModAppTable').datagrid('endEdit', rowIndex);
            //row['action'] = "";	/*隐藏保存按钮*/
            $('#ordModAppTable').datagrid('refreshRow', rowIndex);
            if(row['province_code'] == ""){
            	$.messager.alert('','省份为必填~','info');
            	return;
            }else if(row['area_code'] == ""){
            	$.messager.alert('','地域为必填~','info');
            	return;
            }else if(row['mod_code'] == ""){
            	$.messager.alert('','订单模板编码为必填~','info');
            	return;
            }else if(row['mod_used'] == ""){
            	$.messager.alert('','模板用途为必填~','info');
            	return;
            }else if(row['used_system'] == ""){
            	$.messager.alert('','使用系统为必填~','info');
            	return;
            }else if(row['used_service'] == ""){
            	$.messager.alert('','使用接口或服务为必填~','info');
            	return;
            }else if(row['oper_code'] == ""){
            	$.messager.alert('','对应业务为必填~','info');
            	return;
            }else if(row['oper_limit_time'] == ""){
            	$.messager.alert('','处理时限为必填~','info');
            	return;
            }
            
				if (newcolumnsFlag == 1) {/*新增数据*/
					newcolumnsFlag = 0;
					oper_type = "100";
					json_info = "{\"ordModApp\":[{\"mod_code\": \""
							+ row['mod_code'] + "\",\"mod_used\": \""
							+ row['mod_used'] + "\",\"province_code\": \""
							+ row['province_code'] + "\",\"area_code\": \""
							+ row['area_code'] + "\",\"used_system\": \""
							+ row['used_system'] + "\",\"used_service\": \""
							+ row['used_service'] + "\",\"oper_code\": \""
							+ row['oper_code'] + "\",\"tache_id\": \""
							+ row['tache_id'] + "\",\"oper_limit_time\": \""
							+ row['oper_limit_time'] + "\"}]}";
				} else {/*修改数据*/
					oper_type = "101";
					json_info = "{\"ordModApp\":[{\"id\": \"" + row['id']
							+ "\",\"mod_code\": \"" + row['mod_code']
							+ "\",\"mod_used\": \"" + row['mod_used']
							+ "\",\"province_code\": \"" + row['province_code']
							+ "\",\"area_code\": \"" + row['area_code']
							+ "\",\"used_system\": \"" + row['used_system']
							+ "\",\"used_service\": \"" + row['used_service']
							+ "\",\"oper_code\": \"" + row['oper_code']
							+ "\",\"tache_id\": \"" + row['tache_id']
							+ "\",\"oper_limit_time\": \""
							+ row['oper_limit_time'] + "\"}]}";
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
									+ "rest/ordModServiceRest/orderModAppMainten",
							contentType : "application/x-www-form-urlencoded; charset=utf-8",
							async : true,
							data : data,
							dataType : "json",
							success : function(data) {
								if (newcolumnsFlag == 1) {

									if (data.args.json_info.ordModApp != null
											&& data.args.json_info.ordModApp.length > 0) {/*需要保存返回的ID，用于修改跟删除*/
										var id = data.args.json_info.ordModApp[0].id;
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
								$('#ordModAppTable').datagrid('beginEdit',
										rowIndex);
								$.messager.alert('', '保存失败!', 'info');

							}
						});
			}

		}

		function DelRow() {/*删除一行*/
			var row = $('#ordModAppTable').datagrid('getSelected');
			if (row) {
				var rowIndex = $('#ordModAppTable')
						.datagrid('getRowIndex', row);
				var json_info = "{\"ordModApp\":[{\"id\": \"" + row['id']
						+ "\",\"mod_code\": \"" + row['mod_code']
						+ "\",\"mod_used\": \"" + row['mod_used']
						+ "\",\"province_code\": \"" + row['province_code']
						+ "\",\"area_code\": \"" + row['area_code']
						+ "\",\"used_system\": \"" + row['used_system']
						+ "\",\"used_service\": \"" + row['used_service']
						+ "\",\"oper_code\": \"" + row['oper_code']
						+ "\",\"tache_id\": \"" + row['tache_id']
						+ "\",\"oper_limit_time\": \"" + row['oper_limit_time']
						+ "\"}]}";
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
									+ "rest/ordModServiceRest/orderModAppMainten",
							contentType : "application/x-www-form-urlencoded; charset=utf-8",
							async : true,
							data : data,
							dataType : "json",
							success : function(data) {
								$('#ordModAppTable').datagrid('deleteRow',
										rowIndex);
								totalResults--;
								/*删除后刷新分页数据*/
								var pager = $("#ordModAppTable").datagrid(
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
								$('#ordModAppTable').datagrid('beginEdit',
										rowIndex);
								$.messager.alert('', '删除失败!', 'info');

							}
						});
			}
		}
	</script>
</body>
</html>