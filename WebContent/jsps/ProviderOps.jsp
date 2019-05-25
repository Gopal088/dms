<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Provider Operations</title>
<script type="text/javascript">
	var popup;

	function showProviders(parent) {
		parent.disabled = true;
		popup = window
				.open(
						'showProviderPopUp.jsp',
						'_blank',
						'status=no,resizable=no,titlebar=no,toolbar=no,width=400,height=400,scrollbars=1');
		popup.focus();
	}

	function commandChanged(object) {
		var flag = true;
		switch (object.value) {
		case "Add": {
			document.getElementById("pick_pid").hidden = true;
			flag = false;
		}
		case "Modify":
			if (flag)
				{document.getElementById("pick_pid").hidden = false;}

			document.getElementById("pid").value = "";
			document.getElementById("pid").disabled = false;
			document.getElementById("full_name").value = "";
			document.getElementById("full_name").disabled = false;
			document.getElementById("prim_mobile").value = "";
			document.getElementById("prim_mobile").disabled = false;
			document.getElementById("alt_mobile").value = "";
			document.getElementById("alt_mobile").disabled = false;
			document.getElementById("aadhar").value = "";
			document.getElementById("aadhar").disabled = false;
			document.getElementById("bank_name").value = "";
			document.getElementById("bank_name").disabled = false;
			document.getElementById("ifsc").value = "";
			document.getElementById("ifsc").disabled = false;
			document.getElementById("account_no1").value = "";
			document.getElementById("account_no1").disabled = false;
			document.getElementById("verifyAcct").hidden = false;
			document.getElementById("account_no2").disabled = false;
			document.getElementById("account_no2").hidden = false;
			break;
		case "Delete":
		case "View": {
			document.getElementById("pick_pid").hidden = false;
			document.getElementById("pid").value = "";
			document.getElementById("pid").editable = false;
			document.getElementById("full_name").value = "";
			document.getElementById("full_name").disabled = true;
			document.getElementById("prim_mobile").value = "";
			document.getElementById("prim_mobile").disabled = true;
			document.getElementById("alt_mobile").value = "";
			document.getElementById("alt_mobile").disabled = true;
			document.getElementById("aadhar").value = "";
			document.getElementById("aadhar").disabled = true;
			document.getElementById("bank_name").value = "";
			document.getElementById("bank_name").disabled = true;
			document.getElementById("ifsc").value = "";
			document.getElementById("ifsc").disabled = true;
			document.getElementById("account_no1").value = "";
			document.getElementById("account_no1").disabled = true;
			document.getElementById("verifyAcct").hidden = true;
			document.getElementById("account_no2").hidden = true;
			document.getElementById("account_no2").value = "";
			document.getElementById("account_no2").disabled = true;
			break;
		}
		default: {
			document.getElementById("pick_pid").hidden = false;
			document.getElementById("pid").value = "";
			document.getElementById("pid").disabled = false;
			document.getElementById("full_name").value = "";
			document.getElementById("full_name").disabled = false;
			document.getElementById("prim_mobile").value = "";
			document.getElementById("prim_mobile").disabled = "";
			document.getElementById("alt_mobile").value = "";
			document.getElementById("alt_mobile").disabled = false;
			document.getElementById("aadhar").value = "";
			document.getElementById("aadhar").disabled = false;
			document.getElementById("bank_name").value = "";
			document.getElementById("bank_name").disabled = false;
			document.getElementById("ifsc").value = "";
			document.getElementById("ifsc").disabled = false;
			document.getElementById("account_no1").value = "";
			document.getElementById("account_no1").disabled = false;
			document.getElementById("verifyAcct").hidden = false;
			document.getElementById("account_no2").hidden = false;
			document.getElementById("account_no2").value = "";
			document.getElementById("account_no2").disabled = false;
			break;
		}

		}
	}

	function parentDisable() {

		if (popup && !popup.closed)
			popup.focus();
	}

	function handlePopupResult(result) {
		var prod = result.split("/");
		document.getElementById("pid").value = parseInt(prod[0]);
		document.getElementById("full_name").value = prod[1];
		pidOnChange(parseInt(prod[0]));
	}

	function pidOnChange(objVal) {

		var mode = document.getElementById("AddOper").checked;

		if (mode)
			return;

		var xmlhttp = new XMLHttpRequest();
		var provider = objVal;
		var url = "../ProviderOps?provider=" + provider;

		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				if (xmlhttp.responseText == "Invalid Provider..") {
					response.sendRedirect("\html\ErrorPage.html")
				} else {
					var prod = xmlhttp.responseText.split("/");
					document.getElementById("prim_mobile").value = prod[1];
					document.getElementById("alt_mobile").value = prod[2];
					document.getElementById("aadhar").value = prod[3];
					document.getElementById("bank_name").value = prod[4];
					document.getElementById("ifsc").value = prod[5];
					document.getElementById("account_no1").value = prod[6];
				}
			}
		};
		try {
			xmlhttp.open("GET", url, false);
			xmlhttp.send();
		} catch (e) {
			alert("unable to connect to server");
		}
	}
</script>
</head>
<body onFocus="parentDisable()" onClick="parentDisable()">
	<form action="../ProviderOps" method="post">
		<table>
			<tr>
				<td>Provider ID</td>
				<td><input type="text" name="pid" id="pid"
					onchange="pidOnChange(this.val)" /> <input type="button"
					value=".." name="pick_pid" id="pick_pid"
					onclick="showProviders(window.self)" /></td>
			</tr>
			<tr>
				<td>Full Name</td>
				<td><input type="text" name="full_name" id="full_name"
					disabled="disabled" /></td>
			</tr>
			<tr>
				<td>Primary Mobile</td>
				<td><input type="text" name="prim_mobile" id="prim_mobile"
					disabled="disabled" maxlength="10" /></td>
			</tr>
			<tr>
				<td>Alternate Mobile</td>
				<td><input type="text" name="alt_mobile" id="alt_mobile"
					disabled="disabled" maxlength="10" /></td>
			</tr>
			<tr>
				<td>Aadhar No</td>
				<td><input type="text" name="aadhar" id="aadhar"
					disabled="disabled" maxlength="12" /></td>
			</tr>
			<tr>
				<td>Bank Name</td>
				<td><input type="text" name="bank_name" id="bank_name"
					disabled="disabled" /></td>
			</tr>
			<tr>
				<td>Bank IFSC</td>
				<td><input type="text" name="ifsc" id="ifsc" maxlength="12"
					disabled="disabled" /></td>
			</tr>
			<tr>
				<td>Account No</td>
				<td><input type="text" name="account_no1" id="account_no1"
					disabled="disabled" /></td>
			</tr>
			<tr>
				<td id="verifyAcct">Verify Account No</td>
				<td><input type="password" name="account_no2" id="account_no2"
					disabled="disabled" /></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><input type="radio" name="Oper" id="AddOper" value="Add"
					onchange="commandChanged(this)">Add Provider <input
					type="radio" name="Oper" id="ModOper" value="Modify"
					onchange="commandChanged(this)">Modify Provider</td>
				<td><input type="radio" name="Oper" id="DelOper" value="Delete"
					onchange="commandChanged(this)">Delete Provider <input
					type="radio" name="Oper" id="ViewOper" value="View"
					onchange="commandChanged(this)">View Provider</td>
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
	</form>
</body>
</html>