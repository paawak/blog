/*
 * ImsMessageBrokerConfigProcessor.java
 *
 * Created on Aug 23, 2009 10:44:39 PM
 *
 * Copyright (c) 2002 - 2009 : Swayam Inc.
 *
 * P R O P R I E T A R Y & C O N F I D E N T I A L
 *
 * The copyright of this document is vested in Swayam Inc. without
 * whose prior written permission its contents must not be published,
 * adapted or reproduced in any form or disclosed or
 * issued to any third party.
 */

package com.swayam.ims.webapp.flex;

import org.springframework.flex.config.MessageBrokerConfigProcessor;

import flex.messaging.MessageBroker;
import flex.messaging.services.RemotingService;

/**
 * 
 * @author paawak
 */
public class ImsMessageBrokerConfigProcessor implements
        MessageBrokerConfigProcessor {

    public MessageBroker processAfterStartup(MessageBroker broker) {
        RemotingService remotingService = (RemotingService) broker
                .getServiceByType(RemotingService.class.getName());
        if (remotingService.isStarted()) {
            System.out.println("The Remoting Service has been started with "
                    + remotingService.getDestinations().size()
                    + " Destinations.");
        }
        return broker;
    }

    public MessageBroker processBeforeStartup(MessageBroker broker) {
        return broker;
    }

}
