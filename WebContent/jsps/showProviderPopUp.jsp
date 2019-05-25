<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.dms.db.Database"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Select Provider</title>
<script type="text/javascript">
function rowData(obj)
{
	try{
	window.close();
	window.opener.handlePopupResult(obj.value);
	}
	catch(err)
	{
		window.close();
		return false;
	}
}
</script>
</head>
<body>
	<%
		Database.loadDrivers();
		Connection conn = Database.getConnection();
		Statement stmt = conn.createStatement();
		String sql = "Select prov_id,prov_name from provider_mast";
		ResultSet rs = stmt.executeQuery(sql);
	%>
	<table border="0">
		<tr>
			<th>Provider Id</th>
			<th>Full Name</th>
		</tr>
		<%!int provId, i = 0;
	String fullName;%>
		<%
			while (rs.next()) {
				provId = rs.getInt("prov_id");
				fullName = rs.getString("prov_name");
				i++;
		%>
		<tr>
			<td><a href="#" onclick="return rowData(row<%=i%>)"><%=provId%></a></td>
			<td><%=fullName%></td>
			<td><input type="hidden" id="row<%=i%>"
				value="<%=provId%>/<%=fullName%>" /></td>
		</tr>
		<%
			}
		%>
	</table>
	<%
		rs.close();
		Database.closeConnection();
	%>
</body>
</html>