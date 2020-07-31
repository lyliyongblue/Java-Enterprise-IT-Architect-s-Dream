package com.liyong.asyn.servlet;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liyong.asyn.util.LongRunningProcess;

@WebServlet(value = "/async/hello", asyncSupported = true)
public class HelloAsyncServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private static ThreadPoolExecutor executor = new ThreadPoolExecutor(200, 400, 50000L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(10000));
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		AsyncContext context = req.startAsync();
		executor.execute(() -> {
			new LongRunningProcess().run();
			try {
				context.getResponse().getWriter().write("Hello World");
			} catch (IOException e) {
				e.printStackTrace();
			}
			context.complete();
		}); 
	}
	
}
