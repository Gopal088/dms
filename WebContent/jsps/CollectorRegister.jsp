<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Collector Registration</title>
<script type="text/javascript">
	function matchPassword(form) {

		verifyUser();
		if(document.getElementById("message").innerText=="Existing mobile number.")
			return false;
		pass1 = form.password1.value;
		pass2 = form.password2.value;

		if (pass1 == '' || pass2 == '') {
			alert('Password can not be empty.');
			return false;
		} else if ((pass1 != '' && pass2 != '') && pass1 != pass2) {
			alert('Passwords did not match. Please retry.');
			return false;
		} else
			return true;
	}
	function verifyUser() {
		var flag = true;
		var xmlhttp = new XMLHttpRequest();
		var username = document.forms["mainForm"]["pre_pmobile"].value
				+ document.forms["mainForm"]["pmobile"].value;
		alert(username);
		var url = "../RegisterCollector?username=" + username;

		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				if (xmlhttp.responseText == "Existing mobile number.") {
				document.getElementById("message").style.color = "red";
					flag = false;
				} else {
					document.getElementById("message").style.color = "green";
					flag = true;
				}
				document.getElementById("message").innerText = xmlhttp.responseText;
				return flag;
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
<body>
	<form action="../RegisterCollector"
		onSubmit="return matchPassword(this)" method="POST" name="mainForm">
		<table>
			<tr>
				<td>First Name :</td>
				<td><select name="prefix">
						<option value="Mr.">Mr.</option>
						<option value="Mrs.">Mrs.</option>
						<option value="Miss.">Miss.</option>
				</select> <input type="text" name="fname" /></td>
				<td></td>
			</tr>
			<tr>
				<td>Middle Name :</td>
				<td><input type="text" name="mname" /></td>
				<td></td>
			</tr>
			<tr>
				<td>Last Name :</td>
				<td><input type="text" name="lname" /></td>
				<td></td>
			</tr>
			<tr>
				<td>Primary Mobile No :</td>
				<td><select name="pre_pmobile">
						<option value="+91">+91</option>
				</select> <input type="text" name="pmobile" maxlength="10" /></td>
				<td><span id="message" ></span></td>
			</tr>
			<tr>
				<td>Alternate Mobile No :</td>
				<td><select name="alt_amobile">
						<option value="+91">+91</option>
				</select> <input type="text" name="amobile" maxlength="10" /></td>
				<td></td>
			</tr>
			<tr>
				<td>Email name :</td>
				<td><input type="email" name="email" /></td>
				<td></td>
			</tr>
			<tr>
				<td>Enter Password :</td>
				<td><input type="password" name="password1" /></td>
				<td></td>
			</tr>
			<tr>
				<td>Confirm Password :</td>
				<td><input type="password" name="password2" /></td>
				<td></td>
			</tr>
			<tr>
				<td><input type="submit" value="Register" /></td>
				<td><input type="reset" value="clear" /></td>
				<td></td>
			</tr>
		</table>
	</form>
</body>
</html>