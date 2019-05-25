package com.dms.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dms.db.Database;
import com.dms.security.Heimdall;

/**
 * Servlet implementation class RegisterCollector
 */
public class RegisterCollector extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterCollector() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		Database.loadDrivers();
		Connection conn = Database.getConnection();

		try {
			String sql = "select * from collector_mast where prim_mobile =+" + request.getParameter("username").trim();
			System.out.println("executing : " + sql);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			System.out.println("first " + rs.first());
			if (rs.first())
				response.getWriter().print("Existing mobile number.");
			else
				response.getWriter().print("Valid Mobile number.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		Heimdall hd = new Heimdall();
		Database.loadDrivers();
		Connection conn = Database.getConnection();

		StringBuilder sb = new StringBuilder().append(request.getParameter("prefix"));
		sb.append(" " + request.getParameter("fname") + " ");
		sb.append(request.getParameter("mname") + " ");
		sb.append(request.getParameter("lname"));

		String fullName = sb.toString();

		sb.delete(0, sb.length());

		sb.append(request.getParameter("pre_pmobile") + request.getParameter("pmobile"));
		String primMobile = sb.toString();
		sb.delete(0, sb.length());

		sb.append(request.getParameter("alt_amobile") + request.getParameter("amobile"));
		String altMobile = sb.toString();

		String email = request.getParameter("email");
		String password = request.getParameter("password1");

		hd.setPlainPassword(password);
		String encryptedPassword = hd.encryptPass();
		String salt = hd.getSalt();

		System.out.println(
				fullName + " " + primMobile + " " + altMobile + " " + email + " " + encryptedPassword + " " + salt);

		try {
			PreparedStatement pstmt = conn.prepareStatement("insert into collector_mast values(?,?,?,?,?,?)");
			pstmt.setString(1, fullName.toUpperCase());
			pstmt.setString(2, primMobile);
			pstmt.setString(3, altMobile);
			pstmt.setString(4, email.toLowerCase());
			pstmt.setString(5, encryptedPassword);
			pstmt.setString(6, salt);

			int rowCount = pstmt.executeUpdate();
			System.out.println(rowCount + " Row Inserted.");

			String dpass = hd.decryptPass(encryptedPassword, primMobile);
			if (dpass.equals(password)) {
				System.out.println("Decryption Successful");
				response.getWriter().println("<script type=\"text/javascript\">");  
				response.getWriter().println("alert('Registration Successful. Your username is +'"+primMobile+"); ");  
				response.getWriter().println("location='./jsps/CollectorLogin.jsp';");
				response.getWriter().println("</script>");
			} else {
				response.sendRedirect("./html/ErrorMessage.html");
				request.setAttribute("message", "Something went wrong please try later.");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		}
	}

}
