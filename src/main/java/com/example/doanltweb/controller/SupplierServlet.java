package com.example.doanltweb.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.example.doanltweb.dao.SupplierDao;
import com.example.doanltweb.dao.model.Supplier;
import com.google.gson.Gson;


public class SupplierServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		   	response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
			SupplierDao supplierDao = new SupplierDao();
	        List<Supplier> suppliers = supplierDao.getAllSuppliers();
	        
	        Gson gson = new Gson();
	        String json = gson.toJson(suppliers);
	        PrintWriter out = response.getWriter();
	        out.print(json);
	        out.flush();
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
