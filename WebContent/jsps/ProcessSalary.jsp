<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Salary Processing</title>
<script type="text/javascript">
	function loadFormData() {
		if (confirm('Are you sure ?')) {
			alert('Yes');
		} else {
			alert('No');
		}

	}
	
	function goBack()
	{
		location='./CollectorHome.jsp';
	}
	
</script>
</head>
<body>
	<form action="../ProcessSalary" method="post" onload="loadFormData()">
	<div align="center">
	 <h1 >You are about to Generate Salary Excel.</h1>
	 <h2> Please confirm</h2>
	 <input type="submit" value="Confirm" />
	 <input type="button" value="Go Back" onclick="goBack()" />
	 </div>
	</form>
</body>
</html>