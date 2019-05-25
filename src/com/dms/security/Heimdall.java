package com.dms.security;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;
import java.util.Random;

import com.dms.db.Database;

public class Heimdall {

	String salt;
	String plainPassword;
	String encryptedPass;
	
	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getPlainPassword() {
		return plainPassword;
	}

	public void setPlainPassword(String plainPassword) {
		this.plainPassword = plainPassword;
	}

	public String getEncryptedPass() {
		return encryptedPass;
	}

	public void setEncryptedPass(String encryptedPass) {
		this.encryptedPass = encryptedPass;
	}

	public String generateSalt() {
		Random r = new SecureRandom();
		byte[] arrSalt = new byte[20];
		r.nextBytes(arrSalt);
		salt = arrSalt.toString();
		return salt;
	}

	public String encryptPass() {
		Base64.Encoder encoder = Base64.getEncoder();
		generateSalt();
		encryptedPass = encoder.encodeToString((plainPassword + salt).getBytes());
		System.out.println("Encoded string: " + encryptedPass);
		
		return encryptedPass;
	}

	public String decryptPassword(String encryptedPass, String salt)
	{
		Base64.Decoder decoder = Base64.getDecoder();
		String tinkeredPass = new String(decoder.decode(encryptedPass));
		System.out.println("tinkeredPass : "+tinkeredPass);
		String decryptedPass = tinkeredPass.substring(0,tinkeredPass.indexOf(salt));
		return decryptedPass;
	}
	
	public String decryptPass(String decryptStr, String userid) {
		Base64.Decoder decoder = Base64.getDecoder();
		String tinkeredPass = new String(decoder.decode(decryptStr));
		String salt = null;
		Database.loadDrivers();
		Connection conn = Database.getConnection();
		try {
			Statement stmt = conn.createStatement();
			String sql = "select salt from collector_mast where prim_mobile =\'" + userid+"\'";
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next())
				salt = rs.getString("salt");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (salt != null) {
			System.out.println("tinkeredPass "+tinkeredPass);
			System.out.println(" salt "+salt);
			System.out.println(" str.indexOf(salt) : "+tinkeredPass.indexOf(salt));
			System.out.println(" str.length : "+tinkeredPass.length());
			System.out.println(" substr : "+tinkeredPass.substring(tinkeredPass.indexOf(salt), tinkeredPass.length()));
			
			String password = tinkeredPass.substring(0,tinkeredPass.indexOf(salt));
			System.out.println("Decoded string: " + password);
			return password;
		}

		System.out.println("Password Decryption Failed. ");
		return null;
	}
}
