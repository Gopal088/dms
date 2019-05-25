<%@page import="com.dms.entity.ReceiveProduct"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.dms.entity.Provider"%>
<%@page import="com.dms.db.Database"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	session="true" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>View report</title>
<script type="text/javascript">
	function loadFormData() {
<%Database.loadDrivers();
			Connection conn = Database.getConnection();
			String sql = "select prov_id,prov_name from provider_mast ";
			PreparedStatement stmt = conn.prepareStatement(sql);
			List<Provider> provList = new ArrayList<Provider>();
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				provList.add(new Provider(rs.getInt("prov_id"), rs.getString("prov_name")));
			}

			String fromDate = request.getParameter("fdate");
			String toDate = request.getParameter("tdate");

			Database.closeConnection();%>
	}

	function verifyInput() {
		if (mainForm.from_date.value == "" || mainForm.to_date.value == ""
				|| mainForm.prov_id.value == "") {
			alert('Please enter valid data.');
			return false;
		} else if (mainForm.from_date.value > mainForm.to_date.value) {
			alert('Please check from and to dates');
			return false;
		}
	}
</script>
</head>
<body>
	<h3 align="center">View Report for Provider</h3>
	<div align="center">
		<form action="../ViewReport" id="mainForm" method="get"
			onload="loadFormData()">
			<table>
				<tr>
					<td>Select Provider <select name="prov_id" id="prov_id">
							<%
								for (Provider prov : provList) {
							%>
							<option value="<%=prov.getProviderID()%>"><%=prov.getProviderID()%></option>
							<%
								}
							%>
					</select>
					</td>
					<td>From <input type="date" name="from_date" id="from_date" /></td>
					<td>To <input type="date" name="to_date" id="to_date" /></td>
					<td><input type="submit" value="Show Report"
						onclick="return verifyInput()" /></td>
				</tr>
			</table>
		</form>
		<br /> <br />
		<div>
			<%
				List<ReceiveProduct> reportList = (List<ReceiveProduct>) session.getAttribute("reportData");

				if (reportList == null) {
					out.println("No Data Found.");
				}
				if (reportList != null) {
					float total = 0.0f;
			%>
			<table style="border-color: black; border-style: solid;">
				<tr style="background-color: activeborder;">
					<th>Product ID</th>
					<th>Provider ID</th>
					<th>Product Rate</th>
					<th>Quantity</th>
					<th>Amount</th>
					<th>Received On</th>
				</tr>
				<%
					for (ReceiveProduct rp : reportList) {
				%>
				<tr>
					<td><%=rp.getProdId()%></td>
					<td><%=rp.getProvId()%></td>
					<td><%=rp.getProdRate()%></td>
					<td><%=rp.getQuantity()%></td>
					<td><%=rp.getAmount()%></td>
					<td><%=rp.getTodayDate()%></td>
					<%
						total += rp.getAmount();
					%>
				</tr>
				<%
					}
				%>
				<tr style="background-color: white;">
					<td></td>
					<td></td>
					<td></td>
					<td>Total Amount :</td>
					<td><%=total%></td>
					<td></td>
				</tr>
			</table>
			<%
				}

				session.removeAttribute("reportList");
			%>
		</div>
	</div>
</body>
</html>