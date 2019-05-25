<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Product Operations</title>
<script type="text/javascript">
	var popup;

	function commandChanged(object) {
		switch (object.value) {
		case "Add":
			document.getElementById("pid").value = "";
			document.getElementById("prod_name").value = "";
			document.getElementById("price").value = "";
			document.getElementById("pick_pid").hidden = true;
			document.getElementById("pid").disabled = false;
			document.getElementById("prod_name").disabled = false;
			document.getElementById("date_eff").disabled = false;
			document.getElementById("price").disabled = false;
			document.getElementById("date_eff").value = "";
			break;
		case "Modify": {
			document.getElementById("pid").value = "";
			document.getElementById("prod_name").value = "";
			document.getElementById("price").value = "";
			document.getElementById("pick_pid").hidden = false;
			document.getElementById("pid").disabled = false;
			document.getElementById("prod_name").disabled = false;
			document.getElementById("date_eff").disabled = false;
			document.getElementById("price").disabled = false;
			document.getElementById("date_eff").value = "";
			break;
		}
		case "Delete": {
			document.getElementById("pid").value = "";
			document.getElementById("prod_name").value = "";
			document.getElementById("price").value = "";
			document.getElementById("pick_pid").hidden = false;
			document.getElementById("pid").disabled = false;
			document.getElementById("prod_name").disabled = true;
			document.getElementById("date_eff").disabled = false ;
			document.getElementById("price").disabled = true;
			document.getElementById("date_eff").value = "";
			break;
		}
		case "View": {
			document.getElementById("date_eff").disabled = false;
			document.getElementById("date_eff").value = "";
			document.getElementById("date_eff").disabled = true;
			document.getElementById("pick_pid").hidden = false;
			document.getElementById("pid").disabled = false;
			document.getElementById("prod_name").disabled = true;
			document.getElementById("price").disabled = true;
			document.getElementById("pid").value = "";
			document.getElementById("prod_name").value = "";
			document.getElementById("price").value = "";
			break;
		}

		}
	}

	function showProducts(parent) {
		parent.disabled = true;
		popup = window
				.open(
						'showProductPopUp.jsp',
						'_blank',
						'status=no,resizable=no,titlebar=no,toolbar=no,width=400,height=400,scrollbars=1');
		popup.focus();
	}
	function parentDisable() {

		if (popup && !popup.closed)
			popup.focus();
	}
	function productChanged() {
		var mode = document.getElementById("Oper").value;

		if (mode == "Add" || mode == "Modify") {
			document.getElementById("prod_name").disabled = false;
			document.getElementById("date_eff").disabled = false;
			document.getElementById("price").disabled = false;
		} else if (mode == "Delete") {
			document.getElementById("prod_name").enabled = false;
			document.getElementById("date_eff").enabled = false;
			document.getElementById("price").enabled = false;
		}
	}
	function handlePopupResult(result) {
		var prod = result.split("/");
		document.getElementById("pid").value = parseInt(prod[0]);
		document.getElementById("prod_name").value = prod[1];
		document.getElementById("date_eff").value = prod[2];
		document.getElementById("price").value = parseFloat(prod[3]);
	}
</script>
</head>
<body onFocus="parentDisable()" onClick="parentDisable()">
	<form action="../ProductOps" method="post" onload="setCommand()">
		<table>
			<tr>
				<td>Product ID</td>
				<td><input type="text" name="pid" id="pid"
					onchange="productChanged()" /> <input type="button" value=".."
					name="pick_pid" id="pick_pid" onclick="showProducts(window.self)" /></td>
			</tr>
			<tr>
				<td>Product Name</td>
				<td><input type="text" name="prod_name" id="prod_name"
					disabled="disabled" /></td>
			</tr>

			<tr>
				<td>Date effective</td>
				<td><input type="date" name="date_eff" id="date_eff"
					disabled="disabled" /></td>
			</tr>
			<tr>
				<td>Price/litre</td>
				<td><input type="text" name="price" id="price"
					disabled="disabled" /></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><input type="radio" name="Oper" id="AddOper" value="Add"
					onchange="commandChanged(this)">Add Product <input
					type="radio" name="Oper" id="ModOper" value="Modify"
					onchange="commandChanged(this)">Modify Product</td>
				<td><input type="radio" name="Oper" id="DelOper" value="Delete"
					onchange="commandChanged(this)">Delete Product <input
					type="radio" name="Oper" id="ViewOper" value="View"
					onchange="commandChanged(this)" >View Product</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><input type="submit" value="Go" /></td>
				<td><input type="reset" value="clear" /></td>
				<td></td>
			</tr>
		</table>
		<c:if test="${param.vstime eq 1 }">
			<div style="color: green">Operation Successful.</div>
		</c:if>
	</form>
</body>
</html>