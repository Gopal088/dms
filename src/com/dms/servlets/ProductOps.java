package com.dms.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dms.db.Database;
import com.dms.entity.Product;

/**
 * Servlet implementation class ProductOps
 */
public class ProductOps extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProductOps() {
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
		String command = request.getParameter("Oper");
		response.getWriter().append(request.getParameter("date_eff"));
		Product prod = new Product();
		if (command.equals("Add") || command.equals("Modify")) {
			prod.setProductID(Integer.parseInt(request.getParameter("pid")));
			prod.setProductName(request.getParameter("prod_name"));
			prod.setDate(request.getParameter("date_eff"));
			prod.setRate(Float.parseFloat(request.getParameter("price")));
		} else if (command.equals("Delete")) {
			prod.setProductID(Integer.parseInt(request.getParameter("pid")));
			prod.setDate(request.getParameter("date_eff"));
		}
		Database.loadDrivers();
		Connection conn = Database.getConnection();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		switch (command) {
		case "Add":
			try {
				Date dateEff = new Date(sdf.parse(prod.getDate()).getTime());
				String sql = "insert into prod_mast values(" + prod.getProductID() + ",'" + prod.getProductName()
						+ "','" + dateEff + "'," + prod.getRate() + ")";
				PreparedStatement stmt = conn.prepareStatement(sql);
				System.out.println(stmt);
				int row = stmt.executeUpdate();
				if (row > 0) {
					response.sendRedirect("./jsps/ProductOps.jsp?vstime=1");
					System.out.println("Successful insertion");
				}

			} catch (ParseException e) {
				response.sendRedirect("./html/ErrorMessage.html");
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
				response.sendRedirect("./html/ErrorMessage.html");
			}

			break;
		case "Modify":
			try {
				String sql = "update prod_mast set rate=" + prod.getRate() + " where prod_id=" + prod.getProductID()
						+ " and date_eff like (select curdate() from dual)";
				PreparedStatement stmt = conn.prepareStatement(sql);
				System.out.println(stmt);
				int row = stmt.executeUpdate();
				if (row > 0) {
					response.sendRedirect("./jsps/ProductOps.jsp?vstime=1");
					System.out.println("Successful Updation");
				} else
				{	response.sendRedirect("./html/ErrorMessage.html");
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
				Date dateEff = new Date(sdf.parse(prod.getDate()).getTime());
				String sql = "delete from prod_mast where prod_id =" + prod.getProductID()
						+ " and date_eff= like select sysdate() from dual'";
				PreparedStatement stmt = conn.prepareStatement(sql);
				System.out.println(stmt);
				int row = stmt.executeUpdate();
				if (row > 0) {
					response.sendRedirect("./jsps/ProductOps.jsp?vstime=1");
					System.out.println("Successful Deletion.");
				}

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				response.sendRedirect("./html/ErrorMessage.html");
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
