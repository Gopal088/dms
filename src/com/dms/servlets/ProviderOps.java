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
import com.dms.entity.Provider;

/**
 * Servlet implementation class ProviderOps
 */
public class ProviderOps extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProviderOps() {
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
			String sql = "select * from provider_mast where prov_id =" + request.getParameter("provider").trim();
			System.out.println("executing : " + sql);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			System.out.println("first " + rs.first());

			if (rs.first()) {
				String resp = rs.getString("prov_name") + "/" + rs.getString("prim_mobile") + "/"
						+ rs.getString("alt_mobile") + "/" + rs.getString("aadhar_no") + "/" + rs.getString("bank_name")
						+ "/" + rs.getString("bank_ifsc") + "/" + rs.getString("account_no");
				response.getWriter().println(resp);
			} else
				response.getWriter().print("Invalid Provider.");
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
		String command = request.getParameter("Oper");
		Provider prov = new Provider();
		if (command.equals("Add") || command.equals("Modify")) {
			prov.setProviderID((Integer.parseInt(request.getParameter("pid"))));
			prov.setProviderName(request.getParameter("full_name"));
			prov.setPrim_mobile(request.getParameter("prim_mobile"));
			prov.setAlt_mobile(request.getParameter("alt_mobile"));
			prov.setAadhar(request.getParameter("aadhar"));
			prov.setBank_name(request.getParameter("bank_name"));
			prov.setIfsc(request.getParameter("ifsc"));
			prov.setAccount_no1(request.getParameter("account_no1"));
		} else if (command.equals("Delete")) {
			String prov_id = request.getParameter("pid").trim();
			prov.setProviderID(Integer.parseInt(prov_id));
		}
		Database.loadDrivers();
		Connection conn = Database.getConnection();
		switch (command) {
		case "Add":
			try {
				String sql = "insert into provider_mast values(" + prov.getProviderID() + ",'" + prov.getProviderName()
						+ "','" + prov.getPrim_mobile() + "','" + prov.getAlt_mobile() + "','" + prov.getAadhar()
						+ "','" + prov.getBank_name() + "','" + prov.getIfsc() + "','" + prov.getAccount_no1() + "')";

				PreparedStatement stmt = conn.prepareStatement(sql);
				System.out.println(stmt);
				int row = stmt.executeUpdate();
				if (row > 0) {
					response.sendRedirect("./jsps/ProviderOps.jsp?vstime=1");
					System.out.println("Successful insertion");
				}

			} catch (SQLException e) {
				e.printStackTrace();
				response.sendRedirect("./html/ErrorMessage.html");
			}

			break;
		case "Modify":
			try {
				String sql = "update provider_mast set prov_name='" + prov.getProviderName() + "',prim_mobile = '"
						+ prov.getPrim_mobile() + "',alt_mobile = '" + prov.getAlt_mobile() + "',aadhar_no = '"
						+ prov.getAadhar() + "',bank_name = '" + prov.getBank_name() + "',bank_ifsc = '"
						+ prov.getIfsc() + "',account_no = '" + prov.getAccount_no1() + "' where prov_id="
						+ prov.getProviderID();
				PreparedStatement stmt = conn.prepareStatement(sql);
				System.out.println(stmt);
				int row = stmt.executeUpdate();
				if (row > 0) {
					response.sendRedirect("./jsps/ProviderOps.jsp?vstime=1");
					System.out.println("Successful Updation");
				} else {
					response.sendRedirect("./html/ErrorMessage.html");
					System.out.println("Rate can only be modified on same day only.");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				response.sendRedirect("./html/ErrorMessage.html");
			}
			break;
		case "Delete":
			try {
				String sql = "delete from provider_mast where prov_id =" + prov.getProviderID();
				PreparedStatement stmt = conn.prepareStatement(sql);
				System.out.println(stmt);
				int row = stmt.executeUpdate();
				if (row > 0) {
					response.sendRedirect("./jsps/ProviderOps.jsp?vstime=1");
					System.out.println("Successful Deletion.");
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				response.sendRedirect("./html/ErrorMessage.html");
			}
			break;
		default:

		}
		if (conn != null) {
			Database.closeConnection();
		}
	}
}
