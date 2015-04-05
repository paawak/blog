package com.swayam.demo.rmi.server.core.jetty;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.jini.io.MarshalInputStream;
import net.jini.io.MarshalOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.swayam.demo.rmi.shared.api.dto.BankDetail;
import com.swayam.demo.rmi.shared.api.dto.BankDetailGroups;
import com.swayam.demo.rmi.shared.api.service.BankDetailService;

public class RmiServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final Logger LOG = LoggerFactory.getLogger(RmiServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOG.info("processing GET");
        int count = Integer.parseInt(request.getParameter("count"));

        LOG.info("processing request count: {}", count);
        writeOutput(request, response, count);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOG.info("processing POST");
        try {
            readInput(request);
        } catch (ClassNotFoundException e) {
            LOG.error("class not found", e);
        }
    }

    private void readInput(HttpServletRequest request) throws IOException, ClassNotFoundException {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        try (MarshalInputStream mis = new MarshalInputStream(request.getInputStream(), cl, false, cl, Collections.emptyList());) {
            String className = (String) mis.readObject();
            System.out.println("******* className: " + className);
            String method = (String) mis.readObject();
            System.out.println("******* method: " + method);
            Object[] args = (Object[]) mis.readObject();
            System.out.println("******* args: " + args);
        }
    }

    private void writeOutput(HttpServletRequest request, HttpServletResponse response, int count) throws IOException {

        if (count < 3) {
            OutputStream os = response.getOutputStream();
            os.write(0x01);
            os.flush();
            os.close();
            return;
        } else if (count == 3) {
            try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("server-application.xml")) {
                BankDetailService bankDetailService = context.getBean("bankDetailServiceImpl", BankDetailService.class);
                Map<String, List<BankDetail>> result = bankDetailService.getBankDetails(BankDetailGroups.JOB);

                MarshalOutputStream os = new MarshalOutputStream(response.getOutputStream(), Collections.emptyList());

                os.writeObject(result);
                os.flush();
                os.close();
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }
}
