<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Collector Login</title>
</head>
<body>
	<form action="../CollectorLogin" method="post">
		<table>
			<tr>
				<td>User Name :</td>
				<td><input type="text" name="username" /></td>
			</tr>
			<tr>
				<td>Password :</td>
				<td><input type="password" name="password" /></td>
			</tr>
			<tr>
				<td><input type="submit" value="Login" /></td>
				<td><input type="reset" value="clear" /></td>
			</tr>
			<tr><td><input type="hidden" name="user" value="Collector" /></td></tr>
		</table>
	</form>
	Haven't account
	<a href="CollectorRegister.jsp">Register here</a>
</body>
</html>