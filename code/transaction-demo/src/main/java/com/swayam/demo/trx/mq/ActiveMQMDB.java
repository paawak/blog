package com.swayam.demo.trx.mq;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@MessageDriven(activationConfig = {

		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),

		@ActivationConfigProperty(propertyName = "destination", propertyValue = "java:jboss/exported/jms/queue/paawakTestQueue") })

public class ActiveMQMDB implements MessageListener {

	@Override
	public void onMessage(Message message) {

		try {

			if (message instanceof TextMessage) {

				System.out.println("Got Message "

						+ ((TextMessage) message).getText());

			}

		} catch (JMSException e) {

			e.printStackTrace();

		}

	}

}
