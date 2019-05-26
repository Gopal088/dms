package com.dms.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;

import com.dms.entity.GenerateExcel;

/**
 * Servlet implementation class ProcessSalary
 */
public class ProcessSalary extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProcessSalary() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		GenerateExcel ge = new GenerateExcel();
		Workbook wb = ge.getSalaryExcel();
        response.setContentType("application/vnd.openxml");
        //response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String file = "DMS Salaries.xlsx";
        response.setHeader("Content-disposition", "attachment; filename=\"" + file + "\"" );
        ServletOutputStream out = response.getOutputStream();
        wb.write(out);  
        out.flush();
        out.close();
	}

}
