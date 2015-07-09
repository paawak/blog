/*
 * TimeWithZone.java
 *
 * Created on Sep 12, 2010 9:44:46 PM
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

package com.swayam.demo.oracle.hibernate;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

/**
 * 
 * @author paawak
 */
public class TimeWithZone implements UserType {

    private static final Logger LOG = Logger.getLogger(TimeWithZone.class);

    /**
     * Define the supported column types
     */
    @Override
    public int[] sqlTypes() {
        return new int[] { Types.VARCHAR };
    }

    @Override
    public Class<?> returnedClass() {
        return Calendar.class;
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {

        if (x == null || y == null) {
            return false;
        }

        return x.equals(y);
    }

    @Override
    public int hashCode(Object x) throws HibernateException {

        if (x != null) {
            return x.hashCode();
        }

        return 0;
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, Object owner)
            throws HibernateException, SQLException {

        Calendar cal = null;
        String timestampStr = rs.getString(names[0]);

        if (timestampStr != null) {
            cal = getTimeWithZone(timestampStr);
        }

        return cal;
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index)
            throws HibernateException, SQLException {

        if (value == null) {
            st.setNull(index, Types.VARCHAR);
        } else {

            doInstanceCheck(value);
            Calendar cal = (Calendar) value;
            st.setString(index, getTimeWithZone(cal));

        }

    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {

        Calendar clone = null;

        if (value != null) {

            doInstanceCheck(value);
            Calendar cal = (Calendar) value;

            // just copying the timezone and time
            clone = new GregorianCalendar();
            clone.setTimeInMillis(cal.getTimeInMillis());
            TimeZone tz = cal.getTimeZone();
            clone.setTimeZone(TimeZone.getTimeZone(tz.getID()));
        }

        return clone;
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {

        Calendar cal = null;

        if (value != null) {

            doInstanceCheck(value);
            cal = (Calendar) deepCopy(value);

        }

        return cal;
    }

    @Override
    public Object assemble(Serializable cached, Object owner)
            throws HibernateException {
        return disassemble(cached);
    }

    @Override
    public Object replace(Object original, Object target, Object owner)
            throws HibernateException {
        return disassemble(original);
    }

    protected void doInstanceCheck(Object value) {

        if ((value != null) && !(value instanceof Calendar)) {
            throw new UnsupportedOperationException(value.getClass()
                    + " not supported, expecting type "
                    + Calendar.class.getName());
        }

    }

    /**
     * Converts a String <em>2010-09-26 11:30:00 Australia/Adelaide</em> to
     * Calendar
     * 
     * @param timeWithZone
     * @return
     */
    private Calendar getTimeWithZone(String timeWithZone) {

        String timeZoneId = timeWithZone.split("\\s")[2];

        TimeZone tz = TimeZone.getTimeZone(timeZoneId);

        String format = "yyyy-MM-dd HH:mm:ss";
        DateFormat df = new SimpleDateFormat(format);
        df.setTimeZone(tz);

        try {
            df.parse(timeWithZone);
        } catch (ParseException e) {
            LOG.error("could not parse date string: " + timeWithZone, e);
        }

        return df.getCalendar();

    }

    private String getTimeWithZone(Calendar timeWithZone) {

        String format = "yyyy-MM-dd HH:mm:ss zzzz";
        DateFormat df = new SimpleDateFormat(format);
        df.setTimeZone(timeWithZone.getTimeZone());

        return df.format(timeWithZone.getTime());

    }

}
