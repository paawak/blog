
package com.swayam.demo.mdb.plain.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSDestinationDefinition;
import javax.jms.JMSDestinationDefinitions;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@JMSDestinationDefinitions(value = { @JMSDestinationDefinition(name = "java:/queue/HELLOWORLDMDBQueue", interfaceName = "javax.jms.Queue", destinationName = "HELLOWORLDMDBQueue") })
@WebServlet("/rest/author")
public class HelloWorldMDBServletClient extends HttpServlet {

    private static final long serialVersionUID = -8314035702649252239L;

    private static final int MSG_COUNT = 5;

    @Resource(mappedName = "java:/ActiveMQConnectionFactory")
    private ConnectionFactory cf;

    @Resource(mappedName = "java:/queue/HELLOWORLDMDBQueue")
    private Queue queue;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	resp.setContentType("text/html");
	PrintWriter out = resp.getWriter();
	out.write("<h1>Quickstart: Example demonstrates the use of <strong>JMS 2.0</strong> and <strong>EJB 3.2 Message-Driven Bean</strong> in JBoss EAP.</h1>");
	try {

	    Connection connection = cf.createConnection();
	    Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
	    MessageProducer producer = session.createProducer(queue);

	    out.write("<p>Sending messages to <em>" + queue + "</em></p>");
	    out.write("<h2>The following messages will be sent to the destination:</h2>");
	    for (int i = 0; i < MSG_COUNT; i++) {
		String text = "This is message " + (i + 1);
		TextMessage message = session.createTextMessage(text);
		producer.send(message);
		out.write("Message (" + i + "): " + text + "</br>");
	    }
	    // session.commit();
	    session.close();
	    connection.close();
	    out.write("<p><i>Go to your JBoss EAP server console or server log to see the result of messages processing.</i></p>");
	} catch (JMSException e) {
	    e.printStackTrace();
	} finally {
	    if (out != null) {
		out.close();
	    }
	}
    }

}
