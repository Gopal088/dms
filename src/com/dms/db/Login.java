package com.dms.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.dms.security.Heimdall;

public class Login {

	static Connection conn = null;

	public Login() {
		Database.loadDrivers();
		if (conn == null)
			conn = Database.getConnection();
	}

	public boolean authenticateUser(String username,String rcvdPassword,String user) {
		String salt = null, encryptedPass = null;
		try {
			Statement stmt = conn.createStatement();
			String sql;
			if(user.equals("Collector"))
				sql = "select password,salt from collector_mast where prim_mobile =\'" + username + "\'";
			else
				sql = "select password,salt from admin_mast where user_name =\'" + username + "\'";
			
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				encryptedPass = rs.getString("password");
				salt = rs.getString("salt");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(encryptedPass!=null && salt!=null)
		{
			Heimdall hd= new Heimdall();
			String storedPass = hd.decryptPassword(encryptedPass, salt);
			System.out.println("stored pass "+storedPass);
			System.out.println("Same pwd "+rcvdPassword.equals(storedPass));
			return rcvdPassword.equals(storedPass);
		}
		else
			return false;
	}
}
