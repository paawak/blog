package com.swayam.demo.trx.mq;

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

@WebServlet("/jms")
public class TestJMS extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Resource(lookup = "java:jboss/exported/jms/RemoteConnectionFactory")
	ConnectionFactory cf;

	@Resource(lookup = "java:jboss/exported/jms/topic/paawakTestTopic")
	private Queue queue;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			example();
			PrintWriter out = response.getWriter();
			out.println("Message sent!");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void example() throws Exception {

		Connection connection = null;
		try {
			connection = cf.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageProducer publisher = session.createProducer(queue);

			connection.start();

			TextMessage message = session.createTextMessage("Hello!");
			publisher.send(message);
		}

		finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (Exception e) {
					throw e;
				}

			}
			closeConnection(connection);
		}

	}

	private void closeConnection(Connection con) {
		try {

			if (con != null) {
				con.close();
			}

		}

		catch (JMSException jmse) {

			System.out.println("Could not close connection " + con + " exception was " + jmse);

		}

	}

}
