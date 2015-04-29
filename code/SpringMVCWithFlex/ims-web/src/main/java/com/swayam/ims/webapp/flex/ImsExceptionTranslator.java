/*
 * ImsExceptionTranslator.java
 *
 * Created on Aug 23, 2009 10:51:09 PM
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

import org.springframework.flex.core.ExceptionTranslator;

import flex.messaging.MessageException;

/**
 * 
 * @author paawak
 */
public class ImsExceptionTranslator implements ExceptionTranslator {

    public boolean handles(Class<?> clazz) {
        return true;
    }

    public MessageException translate(Throwable t) {

        return new MessageException(t);

    }

}
