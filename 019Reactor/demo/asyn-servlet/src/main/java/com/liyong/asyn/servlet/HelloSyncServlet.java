package com.liyong.asyn.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liyong.asyn.util.LongRunningProcess;

@WebServlet(value = "/sync/hello")
public class HelloSyncServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		new LongRunningProcess().run();
		try {
			resp.getWriter().write("Hello World");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
