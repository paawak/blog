
package com.swayam.demo.mdb.plain.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swayam.demo.mdb.plain.web.dto.AuthorRequest;

@WebServlet("/rest/author")
public class HelloWorldMDBServletClient extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloWorldMDBServletClient.class);

    public static final String QUEUE_JNDI_NAME = "java:/queue/HELLOWORLDMDBQueue";

    @Resource(mappedName = "java:/ActiveMQConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(mappedName = HelloWorldMDBServletClient.QUEUE_JNDI_NAME)
    private Queue queue;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	    throws ServletException, IOException {

	AuthorRequest authorRequest = new AuthorRequest();
	authorRequest.setAuthorId(Long.valueOf(req.getParameter("authorId")));
	authorRequest.setAuthorFirstName(req.getParameter("authorFirstName"));
	authorRequest.setAuthorLastName(req.getParameter("authorLastName"));
	authorRequest.setGenreShortName(req.getParameter("genreShortName"));
	authorRequest.setGenreName(req.getParameter("genreName"));

	LOGGER.debug(" Received authorRequest: {}", authorRequest);

	resp.setContentType("text/html");
	PrintWriter out = resp.getWriter();
	out.write(
		"<h1>Quickstart: Example demonstrates the use of <strong>JMS 2.0</strong> and <strong>EJB 3.2 Message-Driven Bean</strong> in JBoss EAP.</h1>");
	try {

	    Connection connection = connectionFactory.createConnection();
	    Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
	    MessageProducer producer = session.createProducer(queue);

	    out.write("<p>Sending messages to <em>" + queue + "</em></p>");
	    out.write("<h2>The following message will be sent to the destination:</h2>");
	    String text = new ObjectMapper().writeValueAsString(authorRequest);
	    TextMessage message = session.createTextMessage(text);
	    producer.send(message);
	    out.write("Message: " + text + "</br>");
	    session.close();
	    connection.close();
	    out.write(
		    "<p><i>Go to your JBoss EAP server console or server log to see the result of messages processing.</i></p>");
	} catch (JMSException e) {
	    throw new RuntimeException(e);
	} finally {
	    if (out != null) {
		out.close();
	    }
	}
    }

}
