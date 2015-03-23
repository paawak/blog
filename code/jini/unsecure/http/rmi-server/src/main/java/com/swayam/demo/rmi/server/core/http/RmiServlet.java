package com.swayam.demo.rmi.server.core.http;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RmiServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest requset, HttpServletResponse response) throws IOException {
        System.out.println("####################RmiServlet.doGet()");
        // Set response content type
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.println("<h1>Hello World</h1>");
    }

}
