package com.swayam.demo.rmi.server.core.http;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.jini.io.MarshalOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.swayam.demo.rmi.api.dto.BankDetail;
import com.swayam.demo.rmi.api.dto.BankDetailGroups;
import com.swayam.demo.rmi.api.service.BankDetailService;

public class RmiServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final Logger LOG = LoggerFactory.getLogger(RmiServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int count = Integer.parseInt(request.getParameter("count"));

        LOG.info("processing request count: {}", count);

        if (count == 23) {
            LOG.info("reading input");
            try {
                readInput(request);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            writeOutput(request, response, count);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("%%%%%%%%%%%%%%%%%%%");
        try {
            readInput(request);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void readInput(HttpServletRequest request) throws IOException, ClassNotFoundException {
        try (ObjectInputStream mis = new ObjectInputStream(request.getInputStream());) {
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

            // MarshalInputStream is = new
            // MarshalInputStream(request.getInputStream(),
            // getClass().getClassLoader(), false, null,
            // Collections.emptyList());
            // try {
            // Object obj = is.readObject();
            // System.out.println("obj=" + obj);
            // } catch (ClassNotFoundException e) {
            // LOG.error("could not read object", e);
            // }

            // is.close();

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
