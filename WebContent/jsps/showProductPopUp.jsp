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
<title>Pick Product</title>
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
		String sql = "Select prod_id,prod_name,date_eff,rate from prod_mast";
		ResultSet rs = stmt.executeQuery(sql);
	%>
	<table border="0">
		<tr>
			<th>Product Id</th>
			<th>product Name</th>
			<th>Date Effective</th>
			<th>Rate</th>
		</tr>
		<%!int prodId, i = 0;
	String prodName, dateEff;
	float rate;%>
		<%
			while (rs.next()) {
				prodId = rs.getInt("prod_id");
				prodName = rs.getString("prod_name");
				dateEff = rs.getString("date_eff");
				rate = rs.getFloat("rate");
				i++;
		%>
		<tr>
			<td><a href="#" onclick="return rowData(row<%=i%>)"><%=prodId%></a></td>
			<td><%=prodName%></td>
			<td><%=dateEff%></td>
			<td><%=rate%></td>
			<td><input type="hidden" id="row<%=i%>"
				value="<%=prodId%>/<%=prodName%>/<%=dateEff%>/<%=rate%>"></td>
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