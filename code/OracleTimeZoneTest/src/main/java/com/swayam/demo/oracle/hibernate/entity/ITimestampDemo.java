/*
 * ITimestampDemo.java
 *
 * Created on Nov 2, 2010 12:34:39 AM
 *
 * Copyright (c) 2002 - 2008 : Swayam Inc.
 *
 * P R O P R I E T A R Y & C O N F I D E N T I A L
 *
 * The copyright of this document is vested in Swayam Inc. without
 * whose prior written permission its contents must not be published,
 * adapted or reproduced in any form or disclosed or
 * issued to any third party.
 */

package com.swayam.demo.oracle.hibernate.entity;

import java.util.Calendar;

/**
 * 
 * @author paawak
 */
public interface ITimestampDemo {

    long getId();

    String getName();

    Calendar getTimeWithZone();

    Calendar getTimeWithZoneLocal();

    void setId(long id);

    void setName(String name);

    void setTimeWithZone(Calendar timeWithZone);

    void setTimeWithZoneLocal(Calendar timeWithZoneLocal);

}