
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
import com.swayam.eardemo.shared.model.Person;

/**
 *
 * @author paawak
 */
public class EjbInvoker extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(EjbInvoker.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {

            // String contextName =
            // "java:global/swayam-ear/swayam-ejb/MySessionBean";
            String contextName = "java:app/swayam-ejb/MySessionBean";

            LOGGER.info("looking up context with name: {}", contextName);

            MySessionBeanRemote remoteBean = InitialContext.doLookup(contextName);

            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");

            LOGGER.info("firstName: {}, lastName: {}", firstName, lastName);

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet EjbInvoker</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>" + remoteBean.sayHello() + "</h1>");

            if ((firstName != null) && (lastName != null)) {
                int result = remoteBean.savePerson(new Person(firstName, lastName));
                out.println("<h2>Saved FirstName: " + firstName + " and LastName: " + lastName
                        + ", got back: " + result + "</h2>");
            }

            out.println("<form method='post'>");
            out.println("<div>");
            out.println("<div>FirstName: <input type='text' name='firstName'/></div>");
            out.println("<div>LastName: <input type='text' name='lastName'/></div>");
            out.println("<div><input type='submit' name='submit' value='Submit'/></div>");
            out.println("</div>");
            out.println("</form>");
            out.println("</body>");
            out.println("</html>");

        } catch (NamingException ex) {
            ex.printStackTrace();
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
