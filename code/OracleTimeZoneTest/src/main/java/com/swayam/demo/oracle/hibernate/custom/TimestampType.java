/*
 * ZonedTimestamp.java
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

package com.swayam.demo.oracle.hibernate.custom;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

/**
 * 
 * @author paawak
 */
public class TimestampType implements UserType {

    // private static final Logger LOG = Logger.getLogger(TimestampType.class);

    @Override
    public int[] sqlTypes() {
        return new int[] { Types.TIMESTAMP };
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

        Calendar cal = new GregorianCalendar();
        Timestamp timestamp = rs.getTimestamp(names[0], cal);

        if (timestamp != null) {
            cal.setTime(timestamp);
        } else {
            cal = null;
        }

        return cal;
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index)
            throws HibernateException, SQLException {

        if (value == null) {
            st.setNull(index, Types.DATE);
        } else {

            doInstanceCheck(value);
            Calendar cal = (Calendar) value;
            Timestamp timestamp = new Timestamp(cal.getTimeInMillis());
            st.setTimestamp(index, timestamp, cal);

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

}
