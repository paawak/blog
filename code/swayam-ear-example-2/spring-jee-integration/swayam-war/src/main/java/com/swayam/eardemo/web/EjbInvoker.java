
package com.swayam.eardemo.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.swayam.eardemo.shared.api.MySessionBeanRemote;

/**
 *
 * @author paawak
 */
public class EjbInvoker extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(EjbInvoker.class);

    // private static final String CONTEXT_NAME =
    // "java:global/swayam-ear/swayam-ejb/MySessionBean";
    private static final String CONTEXT_NAME = "java:app/swayam-ejb/MySessionBean";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {

            LOGGER.info("looking up context with name: {}", CONTEXT_NAME);

            MySessionBeanRemote remoteBean = InitialContext.doLookup(CONTEXT_NAME);

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet EjbInvoker</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>This illustrates a Servlet calling an EJB</h1>");
            out.println("<h2>EJB Returned: " + remoteBean.sayHello() + "</h2>");

            out.println("</body>");
            out.println("</html>");

        } catch (NamingException ex) {
            LOGGER.error("naming exception", ex);
        } finally {
            out.close();
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        LOGGER.info("handling GET");
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        LOGGER.info("handling POST");
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
