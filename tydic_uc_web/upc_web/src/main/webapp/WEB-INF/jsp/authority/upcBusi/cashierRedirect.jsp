
<html>
<head>

</head>
<body>
<input type="hidden" id="id" value="${id}" />
<input type="hidden" id="key" value="${key}" />
<input type="hidden" id="req_way" value="${req_way}" />
</body>


<script type="text/javascript">
	var id = document.getElementById("id").value;
	var key = document.getElementById("key").value;
	var req_way = document.getElementById("req_way").value;
	var params = "id="+id + "&key="+key+"&req_way="+req_way;
	
	window.location.href = "pay?"+params
</script>
</html>