<%@page import="com.dms.entity.Provider"%>
<%@page import="com.dms.entity.Product"%>
<%@page import="com.dms.db.Database"%> 
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Receive product</title>
<script type="text/javascript">
	var key = new Array();

	function loadFormData() {
<%Database.loadDrivers();
			Connection conn = Database.getConnection();
			String sql = "select prod_id,prod_name,rate,max(date_eff) eff_date from prod_mast group by prod_id";
			PreparedStatement stmt = conn.prepareStatement(sql);
			List<Provider> provList = new ArrayList<Provider>();
			ResultSet rs = stmt.executeQuery();
			List<Product> prodList = new ArrayList<Product>();

			while (rs.next()) {
				prodList.add(new Product(rs.getInt("prod_id"), rs.getString("prod_name"), rs.getFloat("rate")));
			}

			sql = "select prov_id, prov_name from provider_mast ";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				provList.add(new Provider(rs.getInt("prov_id"), rs.getString("prov_name")));
			}

			Database.closeConnection();%>
			
	}
	function prodChanged() {
		var sel = document.getElementById("prod_id");
		var index = sel.options.selectedIndex;
		document.getElementById("prod_name").selectedIndex = index;

	}
	function IdChanged() {
		var sel = document.getElementById("prov_id");
		var index = sel.options.selectedIndex;
		document.getElementById("prov_name").selectedIndex = index;
		document.getElementById("prod_rate").selectedIndex = index;

	}
	function quantityChanged() {
		var rate = document.getElementById("prod_rate").value;
		var qty = document.getElementById("quantity").value;
		document.getElementById("tot_amt").value = rate * qty;

	}
</script>
</head>
<body>
	<form action="../ReceiveToday" method="post" onload="loadFormData()">
		<table>
			<tr>
				<td>Provider ID</td>
				<td><select name="prov_id" id="prov_id" onchange="IdChanged()">
						<%
							for (Provider prov : provList) {
						%>
						<option value="<%=prov.getProviderID()%>"><%=prov.getProviderID()%></option>
						<%
							}
						%>
				</select></td>
			</tr>
			<tr>
				<td>Provider Name</td>
				<td><select name="prov_name" id="prov_name" disabled="disabled">
						<%
							for (Provider prov : provList) {
						%>
						<option value="<%=prov.getProviderName()%>"><%=prov.getProviderName()%></option>
						<%
							}
						%>
				</select></td>
			</tr>
			<tr>
				<td>Product ID</td>
				<td><select name="prod_id" id="prod_id"
					onchange="prodChanged()">
						<%
							for (Product product : prodList) {
						%>
						<option value="<%=product.getProductID()%>"><%=product.getProductID()%></option>
						<%
							}
						%>
				</select></td>
			</tr>
			<tr>
				<td>Product Name</td>
				<td><select name="prod_name" id="prod_name" readonly="readonly">
						<%
							for (Product product : prodList) {
						%>
						<option value="<%=product.getProductName()%>"><%=product.getProductName()%></option>
						<%
							}
						%>
				</select></td>
			</tr>
			<tr>
				<td>Price/Liter</td>
				<td><select name="prod_rate" id="prod_rate" readonly="readonly">
						<%
							for (Product product : prodList) {
						%>
						<option value="<%=product.getRate()%>"><%=product.getRate()%></option>
						<%
							}
						%>
				</select></td>
			</tr>
			<tr>
				<td>Quantity</td>
				<td><input type="number" id="quantity" name="quantity"
					onchange="quantityChanged()" /></td>
			</tr>
			<tr>
				<td>Total Amount</td>
				<td><input type="text" id="tot_amt" name="tot_amt"
					readonly="readonly" /></td>
			</tr>
			<tr>
				<td><input type="submit" value="Submit" /></td>
				<td><input type="reset" value="clear" /></td>
				<td></td>
			</tr>
		</table>
	<c:if test="${param.vstime eq 1 }">
		<div style="color:green ">Information submitted Successfully.</div>
	</c:if>
	</form>
</body>
</html>