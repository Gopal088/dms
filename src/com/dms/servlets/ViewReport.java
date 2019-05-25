package com.dms.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dms.db.Database;
import com.dms.entity.ReceiveProduct;

/**
 * Servlet implementation class ViewReport
 */
public class ViewReport extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ViewReport() {
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
		int provId = Integer.parseInt(request.getParameter("prov_id"));
		Date fromDate = Date.valueOf(request.getParameter("from_date"));
		Date toDate = Date.valueOf(request.getParameter("to_date"));
		HttpSession session = request.getSession();

		Database.loadDrivers();
		Connection conn = Database.getConnection();
		try {
			String sql = "select * from prod_collection where prov_id =" + provId + " and rcvd_on between '" + fromDate
					+ "' and '" + toDate + "'";
			System.out.println("executing : " + sql);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			boolean condition = rs.next();
			List<ReceiveProduct> rcvdProdList;
			if (condition) {
				rcvdProdList = new ArrayList<ReceiveProduct>();
				ReceiveProduct rcvProd;
				while (condition) {
					rcvProd = new ReceiveProduct();
					rcvProd.setAmount(rs.getFloat("amount"));
					rcvProd.setProdId(rs.getInt("prod_id"));
					rcvProd.setProdName(rs.getString("prod_name"));
					rcvProd.setProdRate(rs.getFloat("prod_rate"));
					rcvProd.setProvId(provId);
					rcvProd.setQuantity(rs.getFloat("quantity"));
					rcvProd.setTodayDate(rs.getDate("rcvd_on"));

					rcvdProdList.add(rcvProd);
					condition = rs.next();
				}

				session.setAttribute("reportData", rcvdProdList);
				response.sendRedirect("./jsps/ViewReport.jsp");
			}
			else
			{
				session.setAttribute("reportData", null);
				response.sendRedirect("./jsps/ViewReport.jsp");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		}

		/*
		 * response.getWriter().println("<html><head><script type=\"text/javascript\"> "
		 * ); response.getWriter().
		 * println(" document.getElementById(\"reportData\").innerText = \"reportdata here\";"
		 * ); response.getWriter().println("</script> </head></html>");
		 */
		// response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		int provId = Integer.parseInt(request.getParameter("prov_id"));
		Date fromDate = Date.valueOf(request.getParameter("from_date"));
		Date toDate = Date.valueOf(request.getParameter("to_date"));

		/*
		 * response.getWriter().println("<html><head><script type=\"text/javascript\"> "
		 * ); response.getWriter().
		 * println(" document.getElementById(\"reportData\").innerText = \"reportdata here\";"
		 * ); response.getWriter().println("</script> </head></html>");
		 */
		response.sendRedirect("./jsps/ViewReport.jsp");

	}

}
