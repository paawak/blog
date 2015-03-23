package com.swayam.demo.rmi.server.core.http;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        LOG.info("handing get");
        int count = Integer.parseInt(request.getParameter("count"));
        processRequest(request, response, count);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response, int count) throws IOException {
        LOG.info("processing request count: {}", count);

        if (count < 3) {
            OutputStream os = response.getOutputStream();
            os.write(0x01);
            os.flush();
            os.close();
            return;
        }

        try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("server-application.xml")) {
            BankDetailService bankDetailService = context.getBean("bankDetailServiceImpl", BankDetailService.class);
            Map<String, List<BankDetail>> result = bankDetailService.getBankDetails(BankDetailGroups.JOB);
            ObjectOutputStream oos = new ObjectOutputStream(response.getOutputStream());
            oos.writeObject(result);
            oos.flush();
            oos.close();
        }
    }
}
