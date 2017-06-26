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

<table id="ordModParaFieldRelationTable"></table>
<div id='ordModParaFieldRelationTB' style="padding:5px;">
	   <table style="padding:0 0 0 7px;">
	     <tr>	       
	       <td> 参数编码:  </td>
	       <td> <input id="attr_code" class="easyui-textbox" style="width:70px">  </td>	
	       <td> 对应表名:  </td>
	       <td> <input id="table_name" class="easyui-textbox" style="width:70px">  </td>
	       <td> 对应字段:  </td>
	       <td> <input id="field_name" class="easyui-textbox" style="width:70px">  </td>       
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
		$('#ordModParaFieldRelationTable').datagrid("loading");	
		var json_info = "{\"pageNo\":"+ currentPageNo+",\"pageSize\": "+currentPageSize +",\"ordModParaFieldRelation\": {\"1\":\"1\"";
		if($('#table_name').val()!=null){
			json_info +=",\"table_name\": \""+$('#table_name').val()+"\"";
		}
		if($('#attr_code').val()!=null){
			json_info +=",\"attr_code\": \""+$('#attr_code').val()+"\"";
		}
		if($('#field_name').val()!=null){
			json_info +=",\"field_name\": \""+$('#field_name').val()+"\"";
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
        	 //console.info("返回的结果集>>>>>>>>>>>>"+JSON.stringify(data));
        	 if (data.respCode=="0000") {
        		 if(data.args != null){
        			 if(data.args.json_info.ordModParaFieldRelation!=null&&data.args.json_info.ordModParaFieldRelation.length>0){
        	        		newcolumnsFlag=0;
        		     		$('#ordModParaFieldRelationTable').datagrid("loadData",data.args.json_info.ordModParaFieldRelation);	
        		     		$('#ordModParaFieldRelationTable').datagrid("loaded");	
        		     		totalResults =  data.args.json_info.totalNumeOrdModParaFieldRelation;
        		     		 var pager = $("#ordModParaFieldRelationTable").datagrid("getPager"); 
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
        			 $('#ordModParaFieldRelationTable').datagrid("loaded");
        			 $.messager.alert('','无相关记录!','info');
        		 }
        	 }else{
        		 $('#ordModParaFieldRelationTable').datagrid("loaded");
    			 $.messager.alert('','无相关记录!','info');
        	 }
        	     
        },
         error:function(data)
         {
        	 $('#ordModParaFieldRelationTable').datagrid("loaded");
        	 $.messager.alert('','查询失败!','info');
         }
     });	
	}

	$(function(){
		$('#ordModParaFieldRelationTable').datagrid({
			width:1138,
			height:497,
			fitColumns:true,
			rownumbers:false,
			singleSelect:true,
			columns:[[
						  
						{field:'attr_code',title:'参数编码',width:50,editor:'text'},
						{field:'mod_code',title:'订单模板编码',sortable:"true",width:50,editor:'text'},
						{field:'table_name',title:'参数名称',sortable:"true",width:50,editor:'text'},
						{field:'field_name',title:'参数类型',sortable:"true",width:50,editor:'text'},							
						{field:'id',title:'id',hidden:'true'},
	
			]],
			onSelect:function(index,row){
				if(newcolumnsFlag==1){
					var rows = $('#ordModParaFieldRelationTable').datagrid('getRows');
					if(index!=rows.length-1){//选择某一行时，删除新增未保存的尾行
						$('#ordModParaFieldRelationTable').datagrid('deleteRow', rows.length-1);
						newcolumnsFlag = 0;
						
						 totalResults--;
                    	 /*删除后刷新分页数据*/
                    	 var pager = $("#ordModParaFieldRelationTable").datagrid("getPager"); 
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
				var rowAll = $('#ordModParaFieldRelationTable').datagrid('getRows');
				for(j=0;j<rowAll.length;j++){
					//rowAll[j]['action'] = '';	
					$('#ordModParaFieldRelationTable').datagrid('refreshRow', j);
				}
				if(newcolumnsFlag ==1){/*选择新增行时，初始化数据*/ 
					row['mod_code'] ="";
					row['attr_code'] ="";
					row['table_name'] ="";
					row['field_name'] ="";					
					row['id'] ="";				
				}
				/*row['action'] = '<a href="#" onclick="SaveRow()">保存</a>';*/	
				$('#ordModParaFieldRelationTable').datagrid('refreshRow', index);
				$('#ordModParaFieldRelationTable').datagrid('beginEdit', index);/*开启编辑模式*/
		    },
		  
			toolbar:'#ordModParaFieldRelationTB',
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
						$('#ordModParaFieldRelationTable').datagrid('appendRow',{});/*尾部新增一行*/
						
						 totalResults++;
                    	 /*新增后刷新分页数据*/
                    	 var pager = $("#ordModParaFieldRelationTable").datagrid("getPager"); 
         	     		      pager.pagination({
         	     			   pageNumber:currentPageNo,
         	     		        total:totalResults,
         	     		        onSelectPage:function (pageNo, pageSize) { 
         	     		         currentPageNo=pageNo;
         	     				 currentPageSize = pageSize;
         	     				 getData();	
         	     		        } 
         	     		      }); 
						
						var rows = $('#ordModParaFieldRelationTable').datagrid('getRows');
						$('#ordModParaFieldRelationTable').datagrid('selectRow', rows.length-1);/*直接选择新增行，触发onSelect事件，进入编辑模式*/
					}	
				},
				del:function(){					
					DelRow();
				}
				
		}
		
		
	});
	
	function SaveRow(){/*保存按钮事件*/
		var row = $('#ordModParaFieldRelationTable').datagrid('getSelected');
        if (row) {       
        	var rowIndex = $('#ordModParaFieldRelationTable').datagrid('getRowIndex', row);
            var oper_type="";
            var json_info ="";
            $('#ordModParaFieldRelationTable').datagrid('endEdit', rowIndex);
            //row['action'] = "";	/*隐藏保存按钮*/
            $('#ordModParaFieldRelationTable').datagrid('refreshRow', rowIndex);
            if(row['mod_code'] == ""){
            	$.messager.alert('','订单模板编码为必填~','info');
            	return;
            }else if(row['attr_code'] == ""){
            	$.messager.alert('','参数编码为必填~','info');
            	return;
            }
            
				if (newcolumnsFlag == 1) {/*新增数据*/
					newcolumnsFlag = 0;
					oper_type = "100";
					json_info = "{\"ordModParaFieldRelation\":[{\"mod_code\": \""
							+ row['mod_code']
							+ "\",\"attr_code\": \""
							+ row['attr_code']
							+ "\",\"table_name\": \""
							+ row['table_name']
							+ "\",\"field_name\": \""
							+ row['field_name'] + "\"}]}";
				} else {/*修改数据*/
					oper_type = "101";
					json_info = "{\"ordModParaFieldRelation\":[{\"id\": \""
							+ row['id'] + "\",\"mod_code\": \""
							+ row['mod_code'] + "\",\"attr_code\": \""
							+ row['attr_code'] + "\",\"table_name\": \""
							+ row['table_name'] + "\",\"field_name\": \""
							+ row['field_name'] + "\"}]}";
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

									if (data.args.json_info.ordModParaFieldRelation != null
											&& data.args.json_info.ordModParaFieldRelation.length > 0) {/*需要保存返回的ID，用于修改跟删除*/
										var id = data.args.json_info.ordModParaFieldRelation[0].id;
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
								$('#ordModParaFieldRelationTable').datagrid(
										'beginEdit', rowIndex);
								$.messager.alert('', '保存失败!', 'info');

							}
						});
			}

		}

		function DelRow() {/*删除一行*/
			var row = $('#ordModParaFieldRelationTable')
					.datagrid('getSelected');
			if (row) {
				var rowIndex = $('#ordModParaFieldRelationTable').datagrid(
						'getRowIndex', row);
				var json_info = "{\"ordModParaFieldRelation\":[{\"id\": \""
						+ row['id'] + "\",\"mod_code\": \"" + row['mod_code']
						+ "\",\"attr_code\": \"" + row['attr_code']
						+ "\",\"table_name\": \"" + row['table_name']
						+ "\",\"field_name\": \"" + row['field_name'] + "\"}]}";
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
								$('#ordModParaFieldRelationTable').datagrid(
										'deleteRow', rowIndex);
								totalResults--;
								/*删除后刷新分页数据*/
								var pager = $("#ordModParaFieldRelationTable")
										.datagrid("getPager");
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
								$('#ordModParaFieldRelationTable').datagrid(
										'beginEdit', rowIndex);
								$.messager.alert('', '删除失败!', 'info');

							}
						});
			}
		}
	</script>
</body>
</html>