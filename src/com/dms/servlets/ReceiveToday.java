package com.dms.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dms.db.Database;
import com.dms.entity.ReceiveProduct;

/**
 * Servlet implementation class ReceiveToday
 */
public class ReceiveToday extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ReceiveToday() {
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
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		Database.loadDrivers();
		Connection conn = Database.getConnection();
		try {
			int prodId, provId;
			float prodRate, quantity, amount;
			String prodName;
			Date date = new Date();
			java.sql.Date todayDate =  new java.sql.Date(date.getTime());

			provId = Integer.parseInt(request.getParameter("prov_id"));
			prodId = Integer.parseInt(request.getParameter("prod_id"));
			prodName = request.getParameter("prod_name");
			quantity = Float.parseFloat(request.getParameter("quantity").trim());
			amount = Float.parseFloat(request.getParameter("tot_amt").trim());
			prodRate = Float.parseFloat(request.getParameter("prod_rate").trim());
			
			ReceiveProduct rp = new ReceiveProduct();
			rp.setProvId(provId);
			rp.setProdId(prodId);
			rp.setProdName(prodName);
			rp.setQuantity(quantity);
			rp.setAmount(amount);
			rp.setProdRate(prodRate);
			rp.setTodayDate(todayDate);
			
			
			String sql = "insert into prod_collection values(" + rp.getProvId() + "," + rp.getProdId() + ",'" + rp.getProdName() + "'," + rp.getProdRate()
					+ "," + rp.getQuantity() + "," + rp.getAmount() + ",'" + rp.getTodayDate().toString() + "')";
			PreparedStatement stmt = conn.prepareStatement(sql);

			System.out.println("executing : " + stmt.toString());

			int row = stmt.executeUpdate(sql);
			stmt.close();
			if (row>0)
				response.sendRedirect("./jsps/ReceiveToday.jsp?vstime=1");
			else
				response.sendRedirect("./html/ErrorMessage.html");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.sendRedirect("./html/ErrorMessage.html");
		}

		finally {
			Database.closeConnection();
		}
	}

}
