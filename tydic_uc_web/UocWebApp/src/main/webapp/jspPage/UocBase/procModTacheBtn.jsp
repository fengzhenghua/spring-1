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

<table id="procModTacheBtnTable"></table>
<div id='procModTacheBtnTB' style="padding:5px;">
	   <table style="padding:0 0 0 7px;">
	     <tr>
	       <td> 环节编码:  </td>
	       <td> <input id="tache_code" class="easyui-textbox" style="width:70px">  </td>	       
	       <td> 省份编码:  </td>
	       <td> <input id="province_code" class="easyui-textbox" style="width:70px">  </td>
	       <td> 所属地域:  </td>
	       <td> <input id="area_code" class="easyui-textbox" style="width:70px">  </td>
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
	var currentPageSize=1;
	var currentPageNo=1;
	var totalResults;
	var jsession_id = "555555555555555555";
	function getData(){
		$('#procModTacheBtnTable').datagrid("loading");	
		var json_info = "{\"pageNo\":"+ currentPageNo+",\"pageSize\": "+currentPageSize +",\"procModTacheBtn\": {\"1\":\"1\"";
		if($('#tache_code').val()!=null){
			json_info +=",\"tache_code\": \""+$('#tache_code').val()+"\"";
		}		
	    if($('#province_code').val()!=null){
			json_info +=",\"province_code\": \""+$('#province_code').val()+"\"";
		}
		if($('#area_code').val()!=null){
			json_info +=",\"area_code\": \""+$('#area_code').val()+"\"";
		} 
		 json_info +="}}";
	 $.ajax({
         type: "post",
         url: URLS.URL_API_HOST+"rest/procModService/getTacheMod",
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
        			 if(data.args.json_info.procModTacheBtn!=null&&data.args.json_info.procModTacheBtn.length>0){
        	        		newcolumnsFlag=0;
        		     		$('#procModTacheBtnTable').datagrid("loadData",data.args.json_info.procModTacheBtn);	
        		     		$('#procModTacheBtnTable').datagrid("loaded");	
        		     		totalResults =  data.args.json_info.totalNumeProcModTacheBtn;
        		     		 var pager = $("#procModTacheBtnTable").datagrid("getPager"); 
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
        			 $('#procModTacheBtnTable').datagrid("loaded");
        			 $.messager.alert('','无相关记录!','info');
        		 }
        	 }else{
        		 $('#procModTacheBtnTable').datagrid("loaded");
    			 $.messager.alert('','无相关记录!','info');
        	 }
        	    
        },
         error:function(data)
         {
        	 $('#procModTacheBtnTable').datagrid("loaded");
        	 $.messager.alert('','查询失败!','info');
         }
     });	
	}

	$(function(){
		$('#procModTacheBtnTable').datagrid({
			width:1138,
			height:497,
			fitColumns:true,
			rownumbers:false,
			singleSelect:true,
			columns:[[												
						{field:'tache_code',title:'环节编码',sortable:"true",width:50,editor:'text'},
						{field:'btn_code',title:'BTN',sortable:"true",width:50,editor:'text'},	
						{field:'btn_desc',title:'BTN说明',width:50,editor:'text'},
						{field:'sort',title:'顺序',sortable:"true",width:50,editor:'text'},
						{field:'call_url',title:'对应调用URL',sortable:"true",width:50,editor:'text'},
						{field:'province_code',title:'省份',sortable:"true",width:50,editor:'text'},    
						{field:'area_code',title:'地域',width:50,editor:'text'},
						{field:'id',title:'id',hidden:'true'},	
			]],
			onSelect:function(index,row){
				if(newcolumnsFlag==1){
					var rows = $('#procModTacheBtnTable').datagrid('getRows');
					if(index!=rows.length-1){//选择某一行时，删除新增未保存的尾行
						$('#procModTacheBtnTable').datagrid('deleteRow', rows.length-1);
						newcolumnsFlag = 0;
						
						 totalResults--;
                    	 /*删除后刷新分页数据*/
                    	 var pager = $("#procModTacheBtnTable").datagrid("getPager"); 
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
				var rowAll = $('#procModTacheBtnTable').datagrid('getRows');
				for(j=0;j<rowAll.length;j++){
					//rowAll[j]['action'] = '';	
					$('#procModTacheBtnTable').datagrid('refreshRow', j);
				}
				if(newcolumnsFlag ==1){/*选择新增行时，初始化数据*/ 
					row['tache_code'] ="";
					row['btn_code'] ="";
					row['province_code'] ="";
					row['area_code'] ="";
					row['btn_desc'] ="";
					row['sort'] ="";
					row['call_url'] ="";
					row['id'] ="";				
				}
				/*row['action'] = '<a href="#" onclick="SaveRow()">保存</a>';*/	
				$('#procModTacheBtnTable').datagrid('refreshRow', index);
				$('#procModTacheBtnTable').datagrid('beginEdit', index);/*开启编辑模式*/
		    },
		  
			toolbar:'#procModTacheBtnTB',
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
						$('#procModTacheBtnTable').datagrid('appendRow',{});/*尾部新增一行*/
						
						 totalResults++;
                    	 /*新增后刷新分页数据*/
                    	 var pager = $("#procModTacheBtnTable").datagrid("getPager"); 
         	     		      pager.pagination({
         	     			   pageNumber:currentPageNo,
         	     		        total:totalResults,
         	     		        onSelectPage:function (pageNo, pageSize) { 
         	     		         currentPageNo=pageNo;
         	     				 currentPageSize = pageSize;
         	     				 getData();	
         	     		        } 
         	     		      }); 
						
						var rows = $('#procModTacheBtnTable').datagrid('getRows');
						$('#procModTacheBtnTable').datagrid('selectRow', rows.length-1);/*直接选择新增行，触发onSelect事件，进入编辑模式*/
					}	
				},
				del:function(){					
					DelRow();
				}
				
		}
		
		
	});
	
	function SaveRow(){/*保存按钮事件*/
		var row = $('#procModTacheBtnTable').datagrid('getSelected');
        if (row) {       
        	var rowIndex = $('#procModTacheBtnTable').datagrid('getRowIndex', row);
            var oper_type="";
            var json_info ="";
            $('#procModTacheBtnTable').datagrid('endEdit', rowIndex);
            //row['action'] = "";	/*隐藏保存按钮*/
            $('#procModTacheBtnTable').datagrid('refreshRow', rowIndex);
            if(row['province_code'] == ""){
            	$.messager.alert('','省份为必填~','info');
            	return;
            }else if(row['area_code'] == ""){
            	$.messager.alert('','地域为必填~','info');
            	return;
            }else if(row['tache_code'] == ""){
            	$.messager.alert('','环节为必填~','info');
            	return;
            }
           
				if (newcolumnsFlag == 1) {/*新增数据*/
					newcolumnsFlag = 0;
					oper_type = "100";
					json_info = "{\"procModTacheBtn\":[{\"tache_code\": \""
							+ row['tache_code'] + "\",\"btn_code\": \""
							+ row['btn_code'] + "\",\"province_code\": \""
							+ row['province_code'] + "\",\"area_code\": \""
							+ row['area_code'] + "\",\"btn_desc\": \""
							+ row['btn_desc'] + "\",\"sort\": \"" + row['sort']
							+ "\",\"call_url\": \"" + row['call_url'] + "\"}]}";
				} else {/*修改数据*/
					oper_type = "101";
					json_info = "{\"procModTacheBtn\":[{\"id\": \"" + row['id']
							+ "\",\"tache_code\": \"" + row['tache_code']
							+ "\",\"btn_code\": \"" + row['btn_code']
							+ "\",\"province_code\": \"" + row['province_code']
							+ "\",\"area_code\": \"" + row['area_code']
							+ "\",\"btn_desc\": \"" + row['btn_desc']
							+ "\",\"sort\": \"" + row['sort']
							+ "\",\"call_url\": \"" + row['call_url'] + "\"}]}";
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
									+ "rest/procModService/procModTacheMainten",
							contentType : "application/x-www-form-urlencoded; charset=utf-8",
							async : true,
							data : data,
							dataType : "json",
							success : function(data) {
								if (newcolumnsFlag == 1) {

									if (data.args.json_info.procModTacheBtn != null
											&& data.args.json_info.procModTacheBtn.length > 0) {/*需要保存返回的ID，用于修改跟删除*/
										var id = data.args.json_info.procModTacheBtn[0].id;
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
								$('#procModTacheBtnTable').datagrid(
										'beginEdit', rowIndex);
								$.messager.alert('', '保存失败!', 'info');

							}
						});
			}

		}

		function DelRow() {/*删除一行*/
			var row = $('#procModTacheBtnTable').datagrid('getSelected');
			if (row) {
				var rowIndex = $('#procModTacheBtnTable').datagrid(
						'getRowIndex', row);
				var json_info = "{\"procModTacheBtn\":[{\"id\": \"" + row['id']
						+ "\",\"tache_code\": \"" + row['tache_code']
						+ "\",\"btn_code\": \"" + row['btn_code']
						+ "\",\"province_code\": \"" + row['province_code']
						+ "\",\"area_code\": \"" + row['area_code']
						+ "\",\"btn_desc\": \"" + row['btn_desc']
						+ "\",\"sort\": \"" + row['sort']
						+ "\",\"call_url\": \"" + row['call_url'] + "\"}]}";
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
									+ "rest/procModService/procModTacheMainten",
							contentType : "application/x-www-form-urlencoded; charset=utf-8",
							async : true,
							data : data,
							dataType : "json",
							success : function(data) {
								$('#procModTacheBtnTable').datagrid(
										'deleteRow', rowIndex);
								totalResults--;
								/*删除后刷新分页数据*/
								var pager = $("#procModTacheBtnTable")
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
								$('#procModTacheBtnTable').datagrid(
										'beginEdit', rowIndex);
								$.messager.alert('', '删除失败!', 'info');

							}
						});
			}
		}
	</script>
</body>
</html>