/*
 * DateFormatTest.java
 *
 * Created on Oct 31, 2010 4:05:09 PM
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

package com.swayam.demo.oracle.hibernate.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import org.junit.Test;

/**
 * 
 * @author paawak
 */
public class DateFormatTest {

    @Test
    public void testToCalendar() throws ParseException {

        String dateString = "2010-09-26 11:30:00 Australia/Adelaide";

        String timeZoneId = dateString.split("\\s")[2];

        TimeZone tz = TimeZone.getTimeZone(timeZoneId);

        String format = "yyyy-MM-dd HH:mm:ss";
        DateFormat df = new SimpleDateFormat(format);
        df.setTimeZone(tz);
        df.parse(dateString);

        Calendar cal = df.getCalendar();

        assertNotNull(cal);
        assertEquals(2010, cal.get(Calendar.YEAR));
        assertEquals(8, cal.get(Calendar.MONTH));
        assertEquals(26, cal.get(Calendar.DATE));
        assertEquals(11, cal.get(Calendar.HOUR_OF_DAY));
        assertEquals(30, cal.get(Calendar.MINUTE));
        assertEquals(0, cal.get(Calendar.SECOND));

        assertEquals(timeZoneId, cal.getTimeZone().getID());

    }

    @Test
    public void testToString() {

        String timeZoneId = "Australia/Adelaide";
        TimeZone tz = TimeZone.getTimeZone(timeZoneId);

        Calendar cal = Calendar.getInstance(tz);

        String format = "yyyy-MM-dd HH:mm:ss zzzz";
        DateFormat df = new SimpleDateFormat(format);
        df.setTimeZone(cal.getTimeZone());
        String dateStr = df.format(cal.getTime());

        System.out.println(dateStr);

    }

}
